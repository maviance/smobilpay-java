package org.maviance.smobilpay.samples;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Configuration record for {@link SmokeTest}, loaded from a single JSON
 * file. All per-flow blocks are optional: a block that is absent or
 * {@code null} causes the corresponding scenario to be skipped.
 *
 * <p>See {@code smoke-test.example.json} at the repo root for a fully
 * populated template.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public record SmokeTestConfig(
        @JsonProperty("baseUrl") String baseUrl,
        @JsonProperty("publicKey") String publicKey,
        @JsonProperty("secretKey") String secretKey,
        @JsonProperty("apiVersion") String apiVersion,
        @JsonProperty("cashin") CashinCfg cashin,
        @JsonProperty("bill") BillCfg bill,
        @JsonProperty("topup") TopupCfg topup,
        @JsonProperty("voucher") VoucherCfg voucher,
        @JsonProperty("product") ProductCfg product,
        @JsonProperty("subscription") SubscriptionCfg subscription,
        @JsonProperty("cashout") CashoutCfg cashout,
        @JsonProperty("verify") VerifyCfg verify,
        @JsonProperty("validate") ValidateCfg validate
) {

    @JsonIgnoreProperties(ignoreUnknown = true)
    public record CashinCfg(
            @JsonProperty("serviceId") long serviceId,
            @JsonProperty("amount") int amount,
            @JsonProperty("collect") boolean collect,
            @JsonProperty("customerPhonenumber") String customerPhonenumber,
            @JsonProperty("customerEmailaddress") String customerEmailaddress,
            @JsonProperty("serviceNumber") String serviceNumber
    ) {}

    @JsonIgnoreProperties(ignoreUnknown = true)
    public record BillCfg(
            @JsonProperty("merchant") String merchant,
            @JsonProperty("serviceId") long serviceId,
            @JsonProperty("serviceNumber") String serviceNumber
    ) {}

    @JsonIgnoreProperties(ignoreUnknown = true)
    public record TopupCfg(
            @JsonProperty("serviceId") long serviceId,
            @JsonProperty("amount") int amount
    ) {}

    @JsonIgnoreProperties(ignoreUnknown = true)
    public record VoucherCfg(
            @JsonProperty("serviceId") long serviceId,
            @JsonProperty("amount") Integer amount
    ) {}

    @JsonIgnoreProperties(ignoreUnknown = true)
    public record ProductCfg(
            @JsonProperty("serviceId") long serviceId,
            @JsonProperty("amount") Integer amount
    ) {}

    @JsonIgnoreProperties(ignoreUnknown = true)
    public record SubscriptionCfg(
            @JsonProperty("merchant") String merchant,
            @JsonProperty("serviceId") long serviceId,
            @JsonProperty("serviceNumber") String serviceNumber,
            @JsonProperty("customerNumber") String customerNumber,
            @JsonProperty("amount") Integer amount
    ) {}

    @JsonIgnoreProperties(ignoreUnknown = true)
    public record CashoutCfg(
            @JsonProperty("serviceId") long serviceId,
            @JsonProperty("amount") int amount
    ) {}

    @JsonIgnoreProperties(ignoreUnknown = true)
    public record VerifyCfg(
            @JsonProperty("merchant") String merchant,
            @JsonProperty("serviceId") long serviceId,
            @JsonProperty("serviceNumber") String serviceNumber
    ) {}

    @JsonIgnoreProperties(ignoreUnknown = true)
    public record ValidateCfg(
            @JsonProperty("destination") String destination,
            @JsonProperty("serviceId") long serviceId
    ) {}
}
