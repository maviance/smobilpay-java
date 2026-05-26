package org.maviance.s3p;

import org.junit.jupiter.api.Test;

import java.net.URI;
import java.time.Duration;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class S3pConfigTest {

    @Test
    void requiresBaseUrl() {
        assertThatThrownBy(() -> S3pConfig.builder()
                .credentials("pk", "sk")
                .build())
                .isInstanceOf(NullPointerException.class)
                .hasMessageContaining("baseUrl");
    }

    @Test
    void requiresCredentials() {
        assertThatThrownBy(() -> S3pConfig.builder()
                .baseUrl("https://api.example.invalid")
                .build())
                .isInstanceOf(NullPointerException.class)
                .hasMessageContaining("publicKey");
    }

    @Test
    void defaultsApiVersionAndTimeouts() {
        S3pConfig c = S3pConfig.builder()
                .baseUrl("https://api.example.invalid")
                .credentials("pk", "sk")
                .build();

        assertThat(c.apiVersion()).isEqualTo("3.0.0");
        assertThat(c.requestTimeout()).isEqualTo(Duration.ofSeconds(30));
        assertThat(c.tokenRefreshSkew()).isEqualTo(Duration.ofSeconds(30));
        assertThat(c.baseUrl()).isEqualTo(URI.create("https://api.example.invalid"));
    }

    @Test
    void overridesAreRespected() {
        S3pConfig c = S3pConfig.builder()
                .baseUrl(URI.create("https://custom.example.invalid"))
                .credentials("pk", "sk")
                .apiVersion("3.1.0")
                .requestTimeout(Duration.ofSeconds(10))
                .tokenRefreshSkew(Duration.ofSeconds(60))
                .build();

        assertThat(c.publicKey()).isEqualTo("pk");
        assertThat(c.secretKey()).isEqualTo("sk");
        assertThat(c.apiVersion()).isEqualTo("3.1.0");
        assertThat(c.requestTimeout()).isEqualTo(Duration.ofSeconds(10));
        assertThat(c.tokenRefreshSkew()).isEqualTo(Duration.ofSeconds(60));
    }
}
