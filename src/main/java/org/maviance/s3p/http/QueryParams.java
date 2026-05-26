package org.maviance.s3p.http;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Tiny builder for URL-encoded query strings. Skips {@code null} values so
 * optional parameters can be added unconditionally.
 *
 * <p>Insertion order is preserved in the encoded output for stable, testable
 * URLs.
 */
public final class QueryParams {

    private final List<Entry> entries = new ArrayList<>();

    private QueryParams() {}

    public static QueryParams of() {
        return new QueryParams();
    }

    /** Adds a parameter unless {@code value} is {@code null}. */
    public QueryParams add(String name, Object value) {
        Objects.requireNonNull(name, "name");
        if (value != null) {
            entries.add(new Entry(name, value.toString()));
        }
        return this;
    }

    public boolean isEmpty() {
        return entries.isEmpty();
    }

    /** Returns the encoded query string, without a leading {@code ?}. */
    public String encode() {
        if (entries.isEmpty()) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < entries.size(); i++) {
            Entry e = entries.get(i);
            if (i > 0) {
                sb.append('&');
            }
            sb.append(URLEncoder.encode(e.name, StandardCharsets.UTF_8))
                    .append('=')
                    .append(URLEncoder.encode(e.value, StandardCharsets.UTF_8));
        }
        return sb.toString();
    }

    private record Entry(String name, String value) {}
}
