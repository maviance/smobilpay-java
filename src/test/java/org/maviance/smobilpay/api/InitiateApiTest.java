package org.maviance.smobilpay.api;

import org.junit.jupiter.api.Test;
import org.maviance.smobilpay.SmobilpayApiException;
import org.maviance.smobilpay.WireMockTestBase;
import org.maviance.smobilpay.model.Bill;
import org.maviance.smobilpay.model.BillType;
import org.maviance.smobilpay.model.QuoteRequest;
import org.maviance.smobilpay.model.QuoteResponse;
import org.maviance.smobilpay.model.Subscription;

import java.util.List;
import java.util.UUID;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.equalTo;
import static com.github.tomakehurst.wiremock.client.WireMock.equalToJson;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.getRequestedFor;
import static com.github.tomakehurst.wiremock.client.WireMock.post;
import static com.github.tomakehurst.wiremock.client.WireMock.postRequestedFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static com.github.tomakehurst.wiremock.client.WireMock.urlPathEqualTo;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class InitiateApiTest extends WireMockTestBase {

    @Test
    void billsParsesResponse() {
        wireMock.stubFor(get(urlEqualTo("/v2/bill?merchant=CDE&serviceid=4321&serviceNumber=METER-001"))
                .willReturn(aResponse().withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody("[{\"serviceid\":4321,\"merchant\":\"CDE\","
                                + "\"payItemId\":\"PI-BILL-001\",\"amountType\":\"FIXED\","
                                + "\"localCur\":\"XAF\",\"name\":\"April invoice\","
                                + "\"amountLocalCur\":12500.0,\"billType\":\"REGULAR\","
                                + "\"penaltyAmount\":0.0,\"payOrder\":1,\"serviceNumber\":\"METER-001\"}]")));

        List<Bill> bills = client.initiate().bills("CDE", 4321, "METER-001");

        assertThat(bills).hasSize(1);
        Bill bill = bills.get(0);
        assertThat(bill.billType()).isEqualTo(BillType.REGULAR);
        assertThat(bill.payOrder()).isEqualTo(1);
        assertThat(bill.amountLocalCur()).isEqualTo(12500.0f);
        assertThat(bill.serviceNumber()).isEqualTo("METER-001");
    }

    @Test
    void subscriptionsRequiresEitherServiceOrCustomerNumber() {
        assertThatThrownBy(() -> client.initiate().subscriptions("CDE", 4321L, null, null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("serviceNumber");
    }

    @Test
    void subscriptionsPassesProvidedQueryParams() {
        wireMock.stubFor(get(urlEqualTo("/v2/subscription?merchant=CDE&serviceid=4321&serviceNumber=POL-001"))
                .willReturn(aResponse().withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody("[]")));

        List<Subscription> subs = client.initiate().subscriptions("CDE", 4321L, "POL-001", null);

        assertThat(subs).isEmpty();
        wireMock.verify(getRequestedFor(urlEqualTo("/v2/subscription?merchant=CDE&serviceid=4321&serviceNumber=POL-001")));
    }

    @Test
    void quoteSendsJsonBodyAndParsesResponse() {
        wireMock.stubFor(post(urlPathEqualTo("/v2/quotestd"))
                .willReturn(aResponse().withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody("{\"quoteId\":\"0e1f7f4a-3b2c-4a8d-9d1f-1f5d2c3a4b6e\","
                                + "\"expiresAt\":\"2026-05-02T08:35:00Z\","
                                + "\"payItemId\":\"PI-ENEO-PREPAID-001\","
                                + "\"amountLocalCur\":5000,\"priceLocalCur\":5050,\"priceSystemCur\":8.31,"
                                + "\"localCur\":\"XAF\",\"systemCur\":\"EUR\",\"promotion\":null}")));

        QuoteResponse quote = client.initiate().quote(new QuoteRequest(5000, "PI-ENEO-PREPAID-001"));

        assertThat(quote.quoteId()).isEqualTo(UUID.fromString("0e1f7f4a-3b2c-4a8d-9d1f-1f5d2c3a4b6e"));
        assertThat(quote.payItemId()).isEqualTo("PI-ENEO-PREPAID-001");
        assertThat(quote.amountLocalCur()).isEqualTo(5000.0f);
        assertThat(quote.priceLocalCur()).isEqualTo(5050.0f);
        assertThat(quote.localCur()).isEqualTo("XAF");
        assertThat(quote.promotion()).isNull();

        wireMock.verify(postRequestedFor(urlEqualTo("/v2/quotestd"))
                .withHeader("Content-Type", equalTo("application/json"))
                .withHeader("Authorization", equalTo("Bearer " + FAKE_TOKEN))
                .withHeader("x-api-version", equalTo("3.0.0"))
                .withRequestBody(equalToJson("{\"amount\":5000,\"payItemId\":\"PI-ENEO-PREPAID-001\"}")));
    }

    @Test
    void quoteThrowsSmobilpayApiExceptionOnErrorEnvelope() {
        wireMock.stubFor(post(urlPathEqualTo("/v2/quotestd"))
                .willReturn(aResponse().withStatus(400)
                        .withHeader("Content-Type", "application/json")
                        .withBody("{\"respCode\":1001,\"devMsg\":\"Unknown payItemId\","
                                + "\"usrMsg\":\"Please try again.\","
                                + "\"link\":\"https://docs.example.invalid/errors/1001\"}")));

        assertThatThrownBy(() -> client.initiate().quote(new QuoteRequest(5000, "PI-MISSING")))
                .isInstanceOf(SmobilpayApiException.class)
                .satisfies(t -> {
                    SmobilpayApiException e = (SmobilpayApiException) t;
                    assertThat(e.httpStatus()).isEqualTo(400);
                    assertThat(e.error()).isPresent();
                    assertThat(e.error().get().respCode()).isEqualTo(1001);
                    assertThat(e.error().get().devMsg()).isEqualTo("Unknown payItemId");
                });
    }
}
