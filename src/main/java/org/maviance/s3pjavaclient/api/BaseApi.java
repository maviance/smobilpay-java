package org.maviance.s3pjavaclient.api;

import com.squareup.okhttp.Call;
import org.maviance.s3pjavaclient.*;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 * <p>This Base Class for all the APIs.
 * It contains the fields and methods common to each of them</p>
 *
 * @author Valdese Kamdem
 * @author Florian Lowe
 *
 * */
public abstract class BaseApi {

    protected ApiClient apiClient;
    protected final String xApiVersion = "3.0.0";

    public BaseApi() {
        this(Configuration.getDefaultApiClient());
    }

    public BaseApi(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public ApiClient getApiClient() {
        return apiClient;
    }

    public void setApiClient(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public String getxApiVersion() {
        return xApiVersion;
    }

    protected Call getCall(final ProgressResponseBody.ProgressListener progressListener,
            final ProgressRequestBody.ProgressRequestListener progressRequestListener, String localVarPath,
            List<Pair> localVarQueryParams) throws ApiException {

        Map<String, String> localVarHeaderParams = buildHeaderParams(progressListener);
        return apiClient.buildCall(localVarPath, "GET", localVarQueryParams, new ArrayList<Pair>(), null,
                localVarHeaderParams, new HashMap<String, Object>(), new String[] {}, progressRequestListener);
    }

    protected Call postCall(Object body,
            final ProgressResponseBody.ProgressListener progressListener,
            final ProgressRequestBody.ProgressRequestListener progressRequestListener, String localVarPath)
            throws ApiException {

        Map<String, String> localVarHeaderParams = buildHeaderParams(progressListener);
        final Map<String, Object> formParams = convertBodyToFormData(body);

        return apiClient.buildCall(localVarPath, "POST", new ArrayList<>(), new ArrayList<>(), body,
                localVarHeaderParams, formParams, new String[] {}, progressRequestListener);
    }

    private Map<String, Object> convertBodyToFormData(Object body) {
        Map<String, Object> map = new HashMap<>();
        for (Field field : body.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            try {
                Object value = field.get(body);
                if (value != null) {
                    map.put(field.getName(), value.toString());
                }
            } catch (IllegalAccessException e) {
                throw new RuntimeException("Error occurred while building the request signature data");
            }
        }

        return map;
    }


    private Map<String, String> buildHeaderParams(final ProgressResponseBody.ProgressListener progressListener) {
        Map<String, String> localVarHeaderParams = new HashMap<>();
        localVarHeaderParams.put("x-api-version", this.xApiVersion);

        final String[] localVarAccepts = { "application/json" };
        final String localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        if (localVarAccept != null)
            localVarHeaderParams.put("Accept", localVarAccept);

        final String[] localVarContentTypes = { "multipart/form-data" };
        final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);
        localVarHeaderParams.put("Content-Type", localVarContentType);

        if (progressListener != null) {
            apiClient.getHttpClient()
                    .networkInterceptors()
                    .add(new com.squareup.okhttp.Interceptor() {
                        @Override
                        public com.squareup.okhttp.Response intercept(com.squareup.okhttp.Interceptor.Chain chain)
                                throws IOException {
                            com.squareup.okhttp.Response originalResponse = chain.proceed(chain.request());
                            return originalResponse.newBuilder()
                                    .body(new ProgressResponseBody(originalResponse.body(), progressListener))
                                    .build();
                        }
                    });
        }
        return localVarHeaderParams;
    }

}
