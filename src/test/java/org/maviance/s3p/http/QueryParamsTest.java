package org.maviance.s3p.http;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class QueryParamsTest {

    @Test
    void emptyByDefault() {
        QueryParams q = QueryParams.of();
        assertThat(q.isEmpty()).isTrue();
        assertThat(q.encode()).isEmpty();
    }

    @Test
    void skipsNullValues() {
        String encoded = QueryParams.of()
                .add("a", "1")
                .add("b", null)
                .add("c", "3")
                .encode();
        assertThat(encoded).isEqualTo("a=1&c=3");
    }

    @Test
    void encodesReservedCharacters() {
        String encoded = QueryParams.of()
                .add("q", "value with space&special=chars")
                .encode();
        assertThat(encoded).isEqualTo("q=value+with+space%26special%3Dchars");
    }

    @Test
    void preservesInsertionOrder() {
        String encoded = QueryParams.of()
                .add("z", "1")
                .add("a", "2")
                .add("m", "3")
                .encode();
        assertThat(encoded).isEqualTo("z=1&a=2&m=3");
    }
}
