package org.maviance.s3p.http;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.maviance.s3p.S3pApiException;
import org.maviance.s3p.S3pConfig;
import org.maviance.s3p.S3pException;
import org.maviance.s3p.auth.OAuth2TokenManager;
import org.maviance.s3p.model.ApiError;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

/**
 * Request/response engine for the S3P client.
 *
 * <p>Wraps the JDK {@link HttpClient}. Each call:
 * <ol>
 *   <li>Resolves the request URI from {@code baseUrl + path + query string}.</li>
 *   <li>Asks the {@link OAuth2TokenManager} for a fresh bearer token (which it
 *       caches between calls).</li>
 *   <li>Attaches {@code Authorization: Bearer}, {@code x-api-version}, and
 *       {@code Accept: application/json} headers.</li>
 *   <li>Dispatches the request, applies the configured timeout, and parses
 *       the response body into the requested type, or throws
 *       {@link S3pApiException} on non-2xx.</li>
 * </ol>
 */
public final class HttpTransport {

    private static final String HDR_API_VERSION = "x-api-version";
    private static final String HDR_AUTHORIZATION = "Authorization";
    private static final String HDR_ACCEPT = "Accept";
    private static final String HDR_CONTENT_TYPE = "Content-Type";
    private static final String CT_JSON = "application/json";

    private final HttpClient httpClient;
    private final ObjectMapper jsonMapper;
    private final S3pConfig config;
    private final OAuth2TokenManager tokenManager;

    public HttpTransport(HttpClient httpClient,
                         ObjectMapper jsonMapper,
                         S3pConfig config,
                         OAuth2TokenManager tokenManager) {
        this.httpClient = httpClient;
        this.jsonMapper = jsonMapper;
        this.config = config;
        this.tokenManager = tokenManager;
    }

    public <T> T get(String path, QueryParams query, Class<T> type) {
        HttpRequest req = authedRequestBuilder(path, query)
                .GET()
                .build();
        return execute(req, type);
    }

    public <T> T get(String path, QueryParams query, TypeReference<T> type) {
        HttpRequest req = authedRequestBuilder(path, query)
                .GET()
                .build();
        return execute(req, type);
    }

    public <T> T post(String path, Object body, Class<T> type) {
        String json = writeJson(body);
        HttpRequest req = authedRequestBuilder(path, QueryParams.of())
                .header(HDR_CONTENT_TYPE, CT_JSON)
                .POST(HttpRequest.BodyPublishers.ofString(json))
                .build();
        return execute(req, type);
    }

    /** Raw send (no auth header). Used by the OAuth token manager. */
    public HttpResponse<String> sendRaw(HttpRequest request) {
        try {
            return httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException e) {
            throw new S3pException("HTTP transport error: " + e.getMessage(), e);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new S3pException("Interrupted while sending HTTP request", e);
        }
    }

    public Duration requestTimeout() {
        return config.requestTimeout();
    }

    private HttpRequest.Builder authedRequestBuilder(String path, QueryParams query) {
        URI uri = resolveUri(path, query);
        String bearer = tokenManager.accessToken();
        return HttpRequest.newBuilder()
                .uri(uri)
                .timeout(config.requestTimeout())
                .header(HDR_AUTHORIZATION, "Bearer " + bearer)
                .header(HDR_API_VERSION, config.apiVersion())
                .header(HDR_ACCEPT, CT_JSON);
    }

    private URI resolveUri(String path, QueryParams query) {
        String base = config.baseUrl().toString();
        if (base.endsWith("/")) {
            base = base.substring(0, base.length() - 1);
        }
        String p = path.startsWith("/") ? path : "/" + path;
        String full = base + p;
        if (query != null && !query.isEmpty()) {
            full += "?" + query.encode();
        }
        try {
            return new URI(full);
        } catch (URISyntaxException e) {
            throw new S3pException("Invalid URI: " + full, e);
        }
    }

    private <T> T execute(HttpRequest req, Class<T> type) {
        HttpResponse<String> resp = sendRaw(req);
        checkSuccess(resp);
        return readJson(resp.body(), type);
    }

    private <T> T execute(HttpRequest req, TypeReference<T> type) {
        HttpResponse<String> resp = sendRaw(req);
        checkSuccess(resp);
        return readJsonRef(resp.body(), type);
    }

    private void checkSuccess(HttpResponse<String> resp) {
        int status = resp.statusCode();
        if (status >= 200 && status < 300) {
            return;
        }
        String body = resp.body();
        ApiError error = null;
        if (body != null && !body.isBlank()) {
            try {
                error = jsonMapper.readValue(body, ApiError.class);
            } catch (IOException ignored) {
                // body wasn't an Error envelope — keep raw for diagnostics
            }
        }
        throw new S3pApiException(status, error, body);
    }

    private <T> T readJson(String body, Class<T> type) {
        if (type == Void.class || type == void.class) {
            return null;
        }
        try {
            return jsonMapper.readValue(body, type);
        } catch (IOException e) {
            throw new S3pException("Failed to parse response body: " + e.getMessage(), e);
        }
    }

    private <T> T readJsonRef(String body, TypeReference<T> type) {
        try {
            return jsonMapper.readValue(body, type);
        } catch (IOException e) {
            throw new S3pException("Failed to parse response body: " + e.getMessage(), e);
        }
    }

    private String writeJson(Object body) {
        try {
            return jsonMapper.writeValueAsString(body);
        } catch (IOException e) {
            throw new S3pException("Failed to serialize request body: " + e.getMessage(), e);
        }
    }
}
