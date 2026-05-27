package org.maviance.smobilpay.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * A service offered by a merchant. The {@code isReq*} flags drive which
 * fields a {@link CollectionRequest} must include for this service. The
 * {@link #type()} determines which masterdata endpoint produces the
 * matching payment items.
 */
public record Service(
        @JsonProperty("serviceid") long serviceid,
        @JsonProperty("merchant") String merchant,
        @JsonProperty("title") String title,
        @JsonProperty("description") String description,
        @JsonProperty("category") String category,
        @JsonProperty("country") String country,
        @JsonProperty("localCur") String localCur,
        @JsonProperty("type") ServiceType type,
        @JsonProperty("status") ServiceStatus status,
        @JsonProperty("isReqCustomerName") boolean isReqCustomerName,
        @JsonProperty("isReqCustomerAddress") boolean isReqCustomerAddress,
        @JsonProperty("isReqCustomerNumber") boolean isReqCustomerNumber,
        @JsonProperty("isReqServiceNumber") boolean isReqServiceNumber,
        @JsonProperty("isVerifiable") boolean isVerifiable,
        @JsonProperty("labelCustomerNumber") List<I18nText> labelCustomerNumber,
        @JsonProperty("labelServiceNumber") List<I18nText> labelServiceNumber,
        @JsonProperty("hint") List<I18nText> hint,
        @JsonProperty("validationMask") String validationMask,
        @JsonProperty("denomination") Integer denomination
) {
    @JsonCreator
    public Service {}
}
