package org.maviance.s3p.api;

import org.junit.jupiter.api.Test;
import org.maviance.s3p.WireMockTestBase;
import org.maviance.s3p.model.Cashin;
import org.maviance.s3p.model.Cashout;
import org.maviance.s3p.model.Merchant;
import org.maviance.s3p.model.MerchantStatus;
import org.maviance.s3p.model.Product;
import org.maviance.s3p.model.Service;
import org.maviance.s3p.model.ServiceStatus;
import org.maviance.s3p.model.ServiceType;
import org.maviance.s3p.model.Topup;

import java.util.List;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.equalTo;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.getRequestedFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static com.github.tomakehurst.wiremock.client.WireMock.urlPathEqualTo;
import static org.assertj.core.api.Assertions.assertThat;

class MasterdataApiTest extends WireMockTestBase {

    @Test
    void merchantsParsesListAndSendsBearerAndApiVersion() {
        wireMock.stubFor(get(urlEqualTo("/v2/merchant"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody("[{\"merchant\":\"ENEO\",\"name\":\"Eneo Cameroon\","
                                + "\"description\":\"National electricity utility\","
                                + "\"country\":\"CMR\",\"status\":\"Active\"},"
                                + "{\"merchant\":\"CDE\",\"name\":\"Camwater\","
                                + "\"description\":\"National water utility\","
                                + "\"country\":\"CMR\",\"status\":\"Active\"}]")));

        List<Merchant> merchants = client.masterdata().merchants();

        assertThat(merchants).hasSize(2);
        assertThat(merchants.get(0).merchant()).isEqualTo("ENEO");
        assertThat(merchants.get(0).status()).isEqualTo(MerchantStatus.Active);
        assertThat(merchants.get(1).name()).isEqualTo("Camwater");

        wireMock.verify(getRequestedFor(urlEqualTo("/v2/merchant"))
                .withHeader("Authorization", equalTo("Bearer " + FAKE_TOKEN))
                .withHeader("x-api-version", equalTo("3.0.0"))
                .withHeader("Accept", equalTo("application/json")));
    }

    @Test
    void servicesParsesEnumsAndOptionalFields() {
        wireMock.stubFor(get(urlEqualTo("/v2/service"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody("[{\"serviceid\":1234,\"merchant\":\"ENEO\","
                                + "\"title\":\"Prepaid token\",\"description\":\"Buy a prepaid electricity token\","
                                + "\"category\":\"Utilities\",\"country\":\"CMR\",\"localCur\":\"XAF\","
                                + "\"type\":\"PRODUCT\",\"status\":\"Active\","
                                + "\"isReqCustomerName\":true,\"isReqCustomerAddress\":false,"
                                + "\"isReqCustomerNumber\":false,\"isReqServiceNumber\":true,"
                                + "\"isVerifiable\":true,\"denomination\":50}]")));

        List<Service> services = client.masterdata().services();

        assertThat(services).hasSize(1);
        Service s = services.get(0);
        assertThat(s.serviceid()).isEqualTo(1234);
        assertThat(s.type()).isEqualTo(ServiceType.PRODUCT);
        assertThat(s.status()).isEqualTo(ServiceStatus.Active);
        assertThat(s.isReqCustomerName()).isTrue();
        assertThat(s.isReqServiceNumber()).isTrue();
        assertThat(s.isVerifiable()).isTrue();
        assertThat(s.denomination()).isEqualTo(50);
    }

    @Test
    void productsPassesServiceIdAsQueryParam() {
        wireMock.stubFor(get(urlPathEqualTo("/v2/product"))
                .willReturn(aResponse().withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody("[]")));

        client.masterdata().products(42);

        wireMock.verify(getRequestedFor(urlEqualTo("/v2/product?serviceid=42")));
    }

    @Test
    void productsOmitsQueryWhenServiceIdNull() {
        wireMock.stubFor(get(urlEqualTo("/v2/product"))
                .willReturn(aResponse().withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody("[]")));

        List<Product> empty = client.masterdata().products(null);

        assertThat(empty).isEmpty();
        wireMock.verify(getRequestedFor(urlEqualTo("/v2/product")));
    }

    @Test
    void vouchersHitsVoucherPath() {
        wireMock.stubFor(get(urlEqualTo("/v2/voucher"))
                .willReturn(aResponse().withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody("[]")));

        client.masterdata().vouchers(null);

        wireMock.verify(getRequestedFor(urlEqualTo("/v2/voucher")));
    }

    @Test
    void topupsHitsTopupPath() {
        wireMock.stubFor(get(urlEqualTo("/v2/topup"))
                .willReturn(aResponse().withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody("[]")));

        List<Topup> topups = client.masterdata().topups(null);

        assertThat(topups).isEmpty();
        wireMock.verify(getRequestedFor(urlEqualTo("/v2/topup")));
    }

    @Test
    void cashinsHitsCashinPath() {
        wireMock.stubFor(get(urlEqualTo("/v2/cashin"))
                .willReturn(aResponse().withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody("[]")));

        List<Cashin> cashins = client.masterdata().cashins(null);

        assertThat(cashins).isEmpty();
        wireMock.verify(getRequestedFor(urlEqualTo("/v2/cashin")));
    }

    @Test
    void cashoutsHitsCashoutPath() {
        wireMock.stubFor(get(urlEqualTo("/v2/cashout"))
                .willReturn(aResponse().withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody("[]")));

        List<Cashout> cashouts = client.masterdata().cashouts(null);

        assertThat(cashouts).isEmpty();
        wireMock.verify(getRequestedFor(urlEqualTo("/v2/cashout")));
    }
}
