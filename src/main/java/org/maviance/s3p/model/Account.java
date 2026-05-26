package org.maviance.s3p.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * The authenticated agent's account profile. Returned by {@code /v2/account}.
 *
 * @param balance         remaining amount in account
 * @param currency        system currency (ISO 4217)
 * @param key             public access key for the agent
 * @param agentId         unique agent identifier
 * @param agentName       agent full name
 * @param agentAddress    agent full address
 * @param agentPhonenumber agent phone number
 * @param companyName     collector company name
 * @param companyAddress  collector company address
 * @param companyPhonenumber collector company phone number
 * @param limitMax        daily collection limit
 * @param limitRemaining  collection limit remaining for the day (resets at 00:00 UTC)
 */
public record Account(
        @JsonProperty("balance") float balance,
        @JsonProperty("currency") String currency,
        @JsonProperty("key") String key,
        @JsonProperty("agentId") String agentId,
        @JsonProperty("agentName") String agentName,
        @JsonProperty("agentAddress") String agentAddress,
        @JsonProperty("agentPhonenumber") String agentPhonenumber,
        @JsonProperty("companyName") String companyName,
        @JsonProperty("companyAddress") String companyAddress,
        @JsonProperty("companyPhonenumber") String companyPhonenumber,
        @JsonProperty("limitMax") float limitMax,
        @JsonProperty("limitRemaining") float limitRemaining
) {
    @JsonCreator
    public Account {}
}
