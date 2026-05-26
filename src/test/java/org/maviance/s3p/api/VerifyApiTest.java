package org.maviance.s3p.api;

import org.junit.jupiter.api.Test;
import org.maviance.s3p.WireMockTestBase;
import org.maviance.s3p.model.Account;
import org.maviance.s3p.model.PaymentStatus;
import org.maviance.s3p.model.PaymentStatusType;
import org.maviance.s3p.model.Ping;

import java.time.LocalDate;
import java.util.List;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.getRequestedFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static com.github.tomakehurst.wiremock.client.WireMock.urlPathEqualTo;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class VerifyApiTest extends WireMockTestBase {

    @Test
    void pingParsesProbeResponse() {
        wireMock.stubFor(get(urlEqualTo("/v2/ping"))
                .willReturn(aResponse().withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody("{\"time\":\"2026-05-02T08:30:00+00:00\","
                                + "\"version\":\"3.0.0\","
                                + "\"nonce\":\"0fbb3c52e7af4b66\","
                                + "\"key\":\"ABCDEF1234567890\"}")));

        Ping pong = client.verify().ping();

        assertThat(pong.version()).isEqualTo("3.0.0");
        assertThat(pong.nonce()).isEqualTo("0fbb3c52e7af4b66");
        assertThat(pong.key()).isEqualTo("ABCDEF1234567890");
        assertThat(pong.time()).isNotNull();
    }

    @Test
    void accountParsesProfile() {
        wireMock.stubFor(get(urlEqualTo("/v2/account"))
                .willReturn(aResponse().withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody("{\"balance\":1500.0,\"currency\":\"XAF\","
                                + "\"key\":\"ABCDEF1234567890\","
                                + "\"agentId\":\"AG-001\",\"agentName\":\"Alice\","
                                + "\"agentAddress\":\"Yaoundé\","
                                + "\"agentPhonenumber\":\"237699999999\","
                                + "\"companyName\":\"Maviance\","
                                + "\"companyAddress\":\"HQ\","
                                + "\"companyPhonenumber\":\"237699111111\","
                                + "\"limitMax\":100000.0,\"limitRemaining\":50000.0}")));

        Account account = client.verify().account();

        assertThat(account.balance()).isEqualTo(1500.0f);
        assertThat(account.currency()).isEqualTo("XAF");
        assertThat(account.agentId()).isEqualTo("AG-001");
        assertThat(account.limitMax()).isEqualTo(100000.0f);
        assertThat(account.limitRemaining()).isEqualTo(50000.0f);
    }

    @Test
    void verifyTransactionByPtn() {
        wireMock.stubFor(get(urlEqualTo("/v2/verifytx?ptn=PTN-1"))
                .willReturn(aResponse().withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody(paymentStatusJson("PTN-1", "SUCCESS"))));

        List<PaymentStatus> result = client.verify().verifyTransaction("PTN-1", null);

        assertThat(result).hasSize(1);
        assertThat(result.get(0).ptn()).isEqualTo("PTN-1");
        assertThat(result.get(0).status()).isEqualTo(PaymentStatusType.SUCCESS);
        wireMock.verify(getRequestedFor(urlEqualTo("/v2/verifytx?ptn=PTN-1")));
    }

    @Test
    void verifyTransactionByTrid() {
        wireMock.stubFor(get(urlEqualTo("/v2/verifytx?trid=ORDER-1"))
                .willReturn(aResponse().withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody("[]")));

        List<PaymentStatus> result = client.verify().verifyTransaction(null, "ORDER-1");

        assertThat(result).isEmpty();
    }

    @Test
    void verifyTransactionRejectsBothNull() {
        assertThatThrownBy(() -> client.verify().verifyTransaction(null, null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("ptn or trid");
    }

    @Test
    void historyByPtn() {
        wireMock.stubFor(get(urlEqualTo("/v2/historystd?ptn=PTN-9"))
                .willReturn(aResponse().withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody(paymentStatusJson("PTN-9", "PENDING"))));

        List<PaymentStatus> result = client.verify().historyByPtn("PTN-9");

        assertThat(result).hasSize(1);
        assertThat(result.get(0).status()).isEqualTo(PaymentStatusType.PENDING);
    }

    @Test
    void historyByTridRejectsNull() {
        assertThatThrownBy(() -> client.verify().historyByTrid(null))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void historyByDateRangeFormatsDates() {
        wireMock.stubFor(get(urlPathEqualTo("/v2/historystd"))
                .willReturn(aResponse().withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody("[]")));

        client.verify().historyByDateRange(LocalDate.parse("2026-05-01"), LocalDate.parse("2026-05-31"));

        wireMock.verify(getRequestedFor(urlEqualTo("/v2/historystd?timestamp_from=2026-05-01&timestamp_to=2026-05-31")));
    }

    @Test
    void historyByDateRangeRejectsInverted() {
        assertThatThrownBy(() -> client.verify().historyByDateRange(
                LocalDate.parse("2026-05-31"), LocalDate.parse("2026-05-01")))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("before");
    }

    @Test
    void historyByDateRangeRejectsNullDates() {
        assertThatThrownBy(() -> client.verify().historyByDateRange(null, LocalDate.now()))
                .isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> client.verify().historyByDateRange(LocalDate.now(), null))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void historyByPtnRejectsNull() {
        assertThatThrownBy(() -> client.verify().historyByPtn(null))
                .isInstanceOf(IllegalArgumentException.class);
    }

    private static String paymentStatusJson(String ptn, String status) {
        return "[{\"ptn\":\"" + ptn + "\",\"serviceid\":\"1234\",\"merchant\":\"ENEO\","
                + "\"timestamp\":\"2026-05-02T08:34:12Z\","
                + "\"receiptNumber\":\"R1\",\"veriCode\":\"V1\","
                + "\"clearingDate\":null,\"trid\":\"ORDER-1\","
                + "\"priceLocalCur\":5050,\"priceSystemCur\":8.31,"
                + "\"localCur\":\"XAF\",\"systemCur\":\"EUR\","
                + "\"pin\":null,\"status\":\"" + status + "\","
                + "\"payItemId\":\"PI-1\",\"payItemDescr\":\"d\","
                + "\"errorCode\":0}]";
    }
}
