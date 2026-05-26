package org.maviance.s3p.api;

import org.junit.jupiter.api.Test;
import org.maviance.s3p.S3pApiException;
import org.maviance.s3p.WireMockTestBase;
import org.maviance.s3p.model.CollectionRequest;
import org.maviance.s3p.model.CollectionResponse;
import org.maviance.s3p.model.PaymentStatusType;

import java.util.UUID;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.equalToJson;
import static com.github.tomakehurst.wiremock.client.WireMock.post;
import static com.github.tomakehurst.wiremock.client.WireMock.postRequestedFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static com.github.tomakehurst.wiremock.client.WireMock.urlPathEqualTo;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ConfirmApiTest extends WireMockTestBase {

    @Test
    void collectSendsCollectionRequestAndParsesResponse() {
        UUID quoteId = UUID.fromString("0e1f7f4a-3b2c-4a8d-9d1f-1f5d2c3a4b6e");
        wireMock.stubFor(post(urlPathEqualTo("/v2/collectstd"))
                .withRequestBody(equalToJson("{\"quoteId\":\"0e1f7f4a-3b2c-4a8d-9d1f-1f5d2c3a4b6e\","
                        + "\"customerPhonenumber\":\"237699999999\","
                        + "\"customerEmailaddress\":\"customer@example.com\","
                        + "\"customerName\":\"Jane Doe\","
                        + "\"trid\":\"ORDER-2026-05-02-0001\","
                        + "\"tag\":\"retail-front-desk\"}"))
                .willReturn(aResponse().withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody("{\"ptn\":\"PTN-202605020800001\","
                                + "\"timestamp\":\"2026-05-02T08:34:12Z\","
                                + "\"agentBalance\":1245.32,"
                                + "\"receiptNumber\":\"RCPT-0001-001234\","
                                + "\"veriCode\":\"X9F2\","
                                + "\"priceLocalCur\":5050,\"priceSystemCur\":8.31,"
                                + "\"localCur\":\"XAF\",\"systemCur\":\"EUR\","
                                + "\"trid\":\"ORDER-2026-05-02-0001\","
                                + "\"pin\":null,\"status\":\"PENDING\","
                                + "\"payItemId\":\"PI-ENEO-PREPAID-001\","
                                + "\"payItemDescr\":\"Eneo prepaid token\","
                                + "\"tag\":\"retail-front-desk\"}")));

        CollectionRequest request = CollectionRequest.builder(quoteId, "237699999999", "customer@example.com")
                .customerName("Jane Doe")
                .trid("ORDER-2026-05-02-0001")
                .tag("retail-front-desk")
                .build();

        CollectionResponse response = client.confirm().collect(request);

        assertThat(response.ptn()).isEqualTo("PTN-202605020800001");
        assertThat(response.status()).isEqualTo(PaymentStatusType.PENDING);
        assertThat(response.trid()).isEqualTo("ORDER-2026-05-02-0001");
        assertThat(response.tag()).isEqualTo("retail-front-desk");
        assertThat(response.agentBalance()).isEqualTo(1245.32f);
        assertThat(response.pin()).isNull();

        wireMock.verify(postRequestedFor(urlEqualTo("/v2/collectstd")));
    }

    @Test
    void collectThrowsOnQuoteExpired() {
        UUID quoteId = UUID.randomUUID();
        wireMock.stubFor(post(urlPathEqualTo("/v2/collectstd"))
                .willReturn(aResponse().withStatus(498)));

        CollectionRequest request = CollectionRequest.builder(quoteId, "237699999999", "customer@example.com").build();

        assertThatThrownBy(() -> client.confirm().collect(request))
                .isInstanceOf(S3pApiException.class)
                .satisfies(t -> assertThat(((S3pApiException) t).httpStatus()).isEqualTo(498));
    }
}
