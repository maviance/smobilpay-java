/*
 * Smobilpay S3P API
 * Smobilpay Third Party API FOR PAYMENT COLLECTIONS
 *
 * OpenAPI spec version: 3.0.3
 * 
 *
 * NOTE: This class is auto generated by the swagger code generator program.
 * https://github.com/swagger-api/swagger-codegen.git
 * Do not edit the class manually.
 */

package org.maviance.s3pjavaclient.model;

import java.util.Objects;
import java.util.Arrays;
import com.google.gson.TypeAdapter;
import com.google.gson.annotations.JsonAdapter;
import com.google.gson.annotations.SerializedName;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import io.swagger.v3.oas.annotations.media.Schema;
import java.io.IOException;
import java.math.BigDecimal;
/**
 * Product
 */

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.JavaClientCodegen", date = "2022-02-18T05:58:05.742+01:00[Africa/Douala]")
public class Product {
  @SerializedName("serviceid")
  private Integer serviceid = null;

  @SerializedName("merchant")
  private String merchant = null;

  @SerializedName("payItemId")
  private String payItemId = null;

  @SerializedName("payItemDescr")
  private String payItemDescr = null;

  /**
   * &#x27;Supported amount type for the payment of this product:&#x27; &#x27;\&quot;FIXED\&quot; -&gt; Product needs to be paid in full by the amount provided in “amount”&#x27; &#x27;\&quot;CUSTOM\&quot; -&gt; Amount must be freely entered&#x27; 
   */
  @JsonAdapter(AmountTypeEnum.Adapter.class)
  public enum AmountTypeEnum {
    FIXED("FIXED"),
    CUSTOM("CUSTOM");

    private String value;

    AmountTypeEnum(String value) {
      this.value = value;
    }
    public String getValue() {
      return value;
    }

    @Override
    public String toString() {
      return String.valueOf(value);
    }
    public static AmountTypeEnum fromValue(String text) {
      for (AmountTypeEnum b : AmountTypeEnum.values()) {
        if (String.valueOf(b.value).equals(text)) {
          return b;
        }
      }
      return null;
    }
    public static class Adapter extends TypeAdapter<AmountTypeEnum> {
      @Override
      public void write(final JsonWriter jsonWriter, final AmountTypeEnum enumeration) throws IOException {
        jsonWriter.value(enumeration.getValue());
      }

      @Override
      public AmountTypeEnum read(final JsonReader jsonReader) throws IOException {
        return AmountTypeEnum.fromValue(jsonReader.nextString());
      }
    }
  }  @SerializedName("amountType")
  private AmountTypeEnum amountType = null;

  @SerializedName("localCur")
  private String localCur = null;

  @SerializedName("name")
  private String name = null;

  @SerializedName("amountLocalCur")
  private Float amountLocalCur = null;

  @SerializedName("description")
  private String description = null;

  @SerializedName("optStrg")
  private String optStrg = null;

  @SerializedName("optNmb")
  private BigDecimal optNmb = null;

  public Product serviceid(Integer serviceid) {
    this.serviceid = serviceid;
    return this;
  }

   /**
   * Unique  service Identifier. Identifies the service this product belongs to.
   * @return serviceid
  **/
  @Schema(required = true, description = "Unique  service Identifier. Identifies the service this product belongs to.")
  public Integer getServiceid() {
    return serviceid;
  }

  public void setServiceid(Integer serviceid) {
    this.serviceid = serviceid;
  }

  public Product merchant(String merchant) {
    this.merchant = merchant;
    return this;
  }

   /**
   * Unique  merchant code of associated merchant
   * @return merchant
  **/
  @Schema(required = true, description = "Unique  merchant code of associated merchant")
  public String getMerchant() {
    return merchant;
  }

  public void setMerchant(String merchant) {
    this.merchant = merchant;
  }

  public Product payItemId(String payItemId) {
    this.payItemId = payItemId;
    return this;
  }

   /**
   * Unique  Payment Item ID identifying the product to be purchased
   * @return payItemId
  **/
  @Schema(required = true, description = "Unique  Payment Item ID identifying the product to be purchased")
  public String getPayItemId() {
    return payItemId;
  }

  public void setPayItemId(String payItemId) {
    this.payItemId = payItemId;
  }

  public Product payItemDescr(String payItemDescr) {
    this.payItemDescr = payItemDescr;
    return this;
  }

   /**
   * Contains optional description about payment details (e.g. merchant provided bill types)
   * @return payItemDescr
  **/
  @Schema(description = "Contains optional description about payment details (e.g. merchant provided bill types)")
  public String getPayItemDescr() {
    return payItemDescr;
  }

  public void setPayItemDescr(String payItemDescr) {
    this.payItemDescr = payItemDescr;
  }

  public Product amountType(AmountTypeEnum amountType) {
    this.amountType = amountType;
    return this;
  }

   /**
   * &#x27;Supported amount type for the payment of this product:&#x27; &#x27;\&quot;FIXED\&quot; -&gt; Product needs to be paid in full by the amount provided in “amount”&#x27; &#x27;\&quot;CUSTOM\&quot; -&gt; Amount must be freely entered&#x27; 
   * @return amountType
  **/
  @Schema(required = true, description = "'Supported amount type for the payment of this product:' '\"FIXED\" -> Product needs to be paid in full by the amount provided in “amount”' '\"CUSTOM\" -> Amount must be freely entered' ")
  public AmountTypeEnum getAmountType() {
    return amountType;
  }

  public void setAmountType(AmountTypeEnum amountType) {
    this.amountType = amountType;
  }

  public Product localCur(String localCur) {
    this.localCur = localCur;
    return this;
  }

   /**
   * Local currency of service. (Format: ISO 4217)
   * @return localCur
  **/
  @Schema(required = true, description = "Local currency of service. (Format: ISO 4217)")
  public String getLocalCur() {
    return localCur;
  }

  public void setLocalCur(String localCur) {
    this.localCur = localCur;
  }

  public Product name(String name) {
    this.name = name;
    return this;
  }

   /**
   * Customer friendly name for product to used for presentation
   * @return name
  **/
  @Schema(required = true, description = "Customer friendly name for product to used for presentation")
  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Product amountLocalCur(Float amountLocalCur) {
    this.amountLocalCur = amountLocalCur;
    return this;
  }

   /**
   * Cost of product in local currency – only set for FIXED amounts.
   * @return amountLocalCur
  **/
  @Schema(description = "Cost of product in local currency – only set for FIXED amounts.")
  public Float getAmountLocalCur() {
    return amountLocalCur;
  }

  public void setAmountLocalCur(Float amountLocalCur) {
    this.amountLocalCur = amountLocalCur;
  }

  public Product description(String description) {
    this.description = description;
    return this;
  }

   /**
   * Optional description of product
   * @return description
  **/
  @Schema(description = "Optional description of product")
  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public Product optStrg(String optStrg) {
    this.optStrg = optStrg;
    return this;
  }

   /**
   * Optional string field
   * @return optStrg
  **/
  @Schema(description = "Optional string field")
  public String getOptStrg() {
    return optStrg;
  }

  public void setOptStrg(String optStrg) {
    this.optStrg = optStrg;
  }

  public Product optNmb(BigDecimal optNmb) {
    this.optNmb = optNmb;
    return this;
  }

   /**
   * Optional number field
   * @return optNmb
  **/
  @Schema(description = "Optional number field")
  public BigDecimal getOptNmb() {
    return optNmb;
  }

  public void setOptNmb(BigDecimal optNmb) {
    this.optNmb = optNmb;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Product product = (Product) o;
    return Objects.equals(this.serviceid, product.serviceid) &&
        Objects.equals(this.merchant, product.merchant) &&
        Objects.equals(this.payItemId, product.payItemId) &&
        Objects.equals(this.payItemDescr, product.payItemDescr) &&
        Objects.equals(this.amountType, product.amountType) &&
        Objects.equals(this.localCur, product.localCur) &&
        Objects.equals(this.name, product.name) &&
        Objects.equals(this.amountLocalCur, product.amountLocalCur) &&
        Objects.equals(this.description, product.description) &&
        Objects.equals(this.optStrg, product.optStrg) &&
        Objects.equals(this.optNmb, product.optNmb);
  }

  @Override
  public int hashCode() {
    return Objects.hash(serviceid, merchant, payItemId, payItemDescr, amountType, localCur, name, amountLocalCur, description, optStrg, optNmb);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Product {\n");
    
    sb.append("    serviceid: ").append(toIndentedString(serviceid)).append("\n");
    sb.append("    merchant: ").append(toIndentedString(merchant)).append("\n");
    sb.append("    payItemId: ").append(toIndentedString(payItemId)).append("\n");
    sb.append("    payItemDescr: ").append(toIndentedString(payItemDescr)).append("\n");
    sb.append("    amountType: ").append(toIndentedString(amountType)).append("\n");
    sb.append("    localCur: ").append(toIndentedString(localCur)).append("\n");
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    amountLocalCur: ").append(toIndentedString(amountLocalCur)).append("\n");
    sb.append("    description: ").append(toIndentedString(description)).append("\n");
    sb.append("    optStrg: ").append(toIndentedString(optStrg)).append("\n");
    sb.append("    optNmb: ").append(toIndentedString(optNmb)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(java.lang.Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }

}
