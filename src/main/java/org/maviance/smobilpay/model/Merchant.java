package org.maviance.smobilpay.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * A merchant supported by the system. Every {@link Service} is assigned to a
 * merchant.
 *
 * @param merchant   unique merchant code (use as the {@code merchant} request parameter)
 * @param name       human-readable merchant name
 * @param description merchant description
 * @param country    ISO 3166-1 alpha-3 country code
 * @param status     merchant availability
 * @param logo       URL of the merchant logo (may be null)
 * @param logoHash   MD5 hash of the logo (changes when the logo is updated; may be null)
 * @param category   deprecated; will be removed in future versions — use categories on services instead
 */
public record Merchant(
        @JsonProperty("merchant") String merchant,
        @JsonProperty("name") String name,
        @JsonProperty("description") String description,
        @JsonProperty("country") String country,
        @JsonProperty("status") MerchantStatus status,
        @JsonProperty("logo") String logo,
        @JsonProperty("logoHash") String logoHash,
        @JsonProperty("category") @Deprecated String category
) {
    @JsonCreator
    public Merchant {}
}
