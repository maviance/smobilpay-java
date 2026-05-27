package org.maviance.smobilpay.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;
import java.util.UUID;

/**
 * Request body for {@code POST /v2/collectstd}.
 *
 * <p>Use {@link #builder(UUID, String, String)} for the fluent API — only
 * {@code quoteId}, {@code customerPhonenumber}, and
 * {@code customerEmailaddress} are required. The other fields are required
 * only when the chosen {@link Service} sets the corresponding {@code isReq*}
 * flag.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public final class CollectionRequest {

    @JsonProperty("quoteId") private final UUID quoteId;
    @JsonProperty("customerPhonenumber") private final String customerPhonenumber;
    @JsonProperty("customerEmailaddress") private final String customerEmailaddress;
    @JsonProperty("customerName") private final String customerName;
    @JsonProperty("customerAddress") private final String customerAddress;
    @JsonProperty("customerNumber") private final String customerNumber;
    @JsonProperty("serviceNumber") private final String serviceNumber;
    @JsonProperty("trid") private final String trid;
    @JsonProperty("tag") private final String tag;
    @JsonProperty("callbackUrl") private final String callbackUrl;
    @JsonProperty("cdata") private final String cdata;

    private CollectionRequest(Builder b) {
        this.quoteId = Objects.requireNonNull(b.quoteId, "quoteId");
        this.customerPhonenumber = Objects.requireNonNull(b.customerPhonenumber, "customerPhonenumber");
        this.customerEmailaddress = Objects.requireNonNull(b.customerEmailaddress, "customerEmailaddress");
        this.customerName = b.customerName;
        this.customerAddress = b.customerAddress;
        this.customerNumber = b.customerNumber;
        this.serviceNumber = b.serviceNumber;
        this.trid = b.trid;
        this.tag = validateTag(b.tag);
        this.callbackUrl = validateCallbackUrl(b.callbackUrl);
        this.cdata = b.cdata;
    }

    public UUID quoteId() { return quoteId; }
    public String customerPhonenumber() { return customerPhonenumber; }
    public String customerEmailaddress() { return customerEmailaddress; }
    public String customerName() { return customerName; }
    public String customerAddress() { return customerAddress; }
    public String customerNumber() { return customerNumber; }
    public String serviceNumber() { return serviceNumber; }
    public String trid() { return trid; }
    public String tag() { return tag; }
    public String callbackUrl() { return callbackUrl; }
    public String cdata() { return cdata; }

    public static Builder builder(UUID quoteId, String customerPhonenumber, String customerEmailaddress) {
        return new Builder(quoteId, customerPhonenumber, customerEmailaddress);
    }

    private static String validateTag(String tag) {
        if (tag != null && tag.length() > 50) {
            throw new IllegalArgumentException("tag exceeds 50-character limit: length=" + tag.length());
        }
        return tag;
    }

    private static String validateCallbackUrl(String url) {
        if (url != null && url.length() > 255) {
            throw new IllegalArgumentException("callbackUrl exceeds 255-character limit: length=" + url.length());
        }
        return url;
    }

    public static final class Builder {
        private final UUID quoteId;
        private final String customerPhonenumber;
        private final String customerEmailaddress;
        private String customerName;
        private String customerAddress;
        private String customerNumber;
        private String serviceNumber;
        private String trid;
        private String tag;
        private String callbackUrl;
        private String cdata;

        private Builder(UUID quoteId, String customerPhonenumber, String customerEmailaddress) {
            this.quoteId = quoteId;
            this.customerPhonenumber = customerPhonenumber;
            this.customerEmailaddress = customerEmailaddress;
        }

        public Builder customerName(String customerName) { this.customerName = customerName; return this; }
        public Builder customerAddress(String customerAddress) { this.customerAddress = customerAddress; return this; }
        public Builder customerNumber(String customerNumber) { this.customerNumber = customerNumber; return this; }
        public Builder serviceNumber(String serviceNumber) { this.serviceNumber = serviceNumber; return this; }
        public Builder trid(String trid) { this.trid = trid; return this; }
        public Builder tag(String tag) { this.tag = tag; return this; }
        public Builder callbackUrl(String callbackUrl) { this.callbackUrl = callbackUrl; return this; }
        public Builder cdata(String cdata) { this.cdata = cdata; return this; }

        public CollectionRequest build() {
            return new CollectionRequest(this);
        }
    }
}
