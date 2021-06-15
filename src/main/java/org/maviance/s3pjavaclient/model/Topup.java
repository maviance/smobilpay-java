/*
 * Smobilpay S3P API
 * Smobilpay Third Party API FOR PAYMENT COLLECTIONS
 *
 * OpenAPI spec version: 3.0.2
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
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.IOException;
import java.math.BigDecimal;

/**
 * Topup
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaClientCodegen", date = "2021-06-15T16:12:17.518+01:00")
public class Topup {
  @SerializedName("serviceid")
  private Integer serviceid = null;

  @SerializedName("merchant")
  private String merchant = null;

  @SerializedName("payItemId")
  private String payItemId = null;

  @SerializedName("payItemDescr")
  private String payItemDescr = null;

  /**
   * &#39;Supported amount type for the payment of this product:&#39; &#39;\&quot;FIXED\&quot; -&gt; Topup needs to be paid in full by the amount provided in \&quot;amount\&quot;&#39; &#39;\&quot;CUSTOM\&quot; -&gt; Amount must be freely entered&#39; 
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
        String value = jsonReader.nextString();
        return AmountTypeEnum.fromValue(String.valueOf(value));
      }
    }
  }

  @SerializedName("amountType")
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

  public Topup serviceid(Integer serviceid) {
    this.serviceid = serviceid;
    return this;
  }

   /**
   * Unique  service Identifier. Idenfies the service this topup package belongs to. Use this value whenever “serviceid” is required in request parameters.
   * @return serviceid
  **/
  @ApiModelProperty(required = true, value = "Unique  service Identifier. Idenfies the service this topup package belongs to. Use this value whenever “serviceid” is required in request parameters.")
  public Integer getServiceid() {
    return serviceid;
  }

  public void setServiceid(Integer serviceid) {
    this.serviceid = serviceid;
  }

  public Topup merchant(String merchant) {
    this.merchant = merchant;
    return this;
  }

   /**
   * Unique  merchant code identifying the merchant this topup belongs to.
   * @return merchant
  **/
  @ApiModelProperty(required = true, value = "Unique  merchant code identifying the merchant this topup belongs to.")
  public String getMerchant() {
    return merchant;
  }

  public void setMerchant(String merchant) {
    this.merchant = merchant;
  }

  public Topup payItemId(String payItemId) {
    this.payItemId = payItemId;
    return this;
  }

   /**
   * Unique  Payment Item ID identifying the topup package to be purchased
   * @return payItemId
  **/
  @ApiModelProperty(required = true, value = "Unique  Payment Item ID identifying the topup package to be purchased")
  public String getPayItemId() {
    return payItemId;
  }

  public void setPayItemId(String payItemId) {
    this.payItemId = payItemId;
  }

  public Topup payItemDescr(String payItemDescr) {
    this.payItemDescr = payItemDescr;
    return this;
  }

   /**
   * Contains optional description about payment details (e.g. merchant provided bill types)
   * @return payItemDescr
  **/
  @ApiModelProperty(value = "Contains optional description about payment details (e.g. merchant provided bill types)")
  public String getPayItemDescr() {
    return payItemDescr;
  }

  public void setPayItemDescr(String payItemDescr) {
    this.payItemDescr = payItemDescr;
  }

  public Topup amountType(AmountTypeEnum amountType) {
    this.amountType = amountType;
    return this;
  }

   /**
   * &#39;Supported amount type for the payment of this product:&#39; &#39;\&quot;FIXED\&quot; -&gt; Topup needs to be paid in full by the amount provided in \&quot;amount\&quot;&#39; &#39;\&quot;CUSTOM\&quot; -&gt; Amount must be freely entered&#39; 
   * @return amountType
  **/
  @ApiModelProperty(required = true, value = "'Supported amount type for the payment of this product:' '\"FIXED\" -> Topup needs to be paid in full by the amount provided in \"amount\"' '\"CUSTOM\" -> Amount must be freely entered' ")
  public AmountTypeEnum getAmountType() {
    return amountType;
  }

  public void setAmountType(AmountTypeEnum amountType) {
    this.amountType = amountType;
  }

  public Topup localCur(String localCur) {
    this.localCur = localCur;
    return this;
  }

   /**
   * Local currency of service. (Format: ISO 4217)
   * @return localCur
  **/
  @ApiModelProperty(required = true, value = "Local currency of service. (Format: ISO 4217)")
  public String getLocalCur() {
    return localCur;
  }

  public void setLocalCur(String localCur) {
    this.localCur = localCur;
  }

  public Topup name(String name) {
    this.name = name;
    return this;
  }

   /**
   * Customer friendly name for topup package to be displayed
   * @return name
  **/
  @ApiModelProperty(required = true, value = "Customer friendly name for topup package to be displayed")
  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Topup amountLocalCur(Float amountLocalCur) {
    this.amountLocalCur = amountLocalCur;
    return this;
  }

   /**
   * Cost of topup package in local currency – only set for FIXED amounts.Otherwise null .
   * @return amountLocalCur
  **/
  @ApiModelProperty(value = "Cost of topup package in local currency – only set for FIXED amounts.Otherwise null .")
  public Float getAmountLocalCur() {
    return amountLocalCur;
  }

  public void setAmountLocalCur(Float amountLocalCur) {
    this.amountLocalCur = amountLocalCur;
  }

  public Topup description(String description) {
    this.description = description;
    return this;
  }

   /**
   * Optional description of topup package
   * @return description
  **/
  @ApiModelProperty(value = "Optional description of topup package")
  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public Topup optStrg(String optStrg) {
    this.optStrg = optStrg;
    return this;
  }

   /**
   * Optional string field
   * @return optStrg
  **/
  @ApiModelProperty(value = "Optional string field")
  public String getOptStrg() {
    return optStrg;
  }

  public void setOptStrg(String optStrg) {
    this.optStrg = optStrg;
  }

  public Topup optNmb(BigDecimal optNmb) {
    this.optNmb = optNmb;
    return this;
  }

   /**
   * Optional number field
   * @return optNmb
  **/
  @ApiModelProperty(value = "Optional number field")
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
    Topup topup = (Topup) o;
    return Objects.equals(this.serviceid, topup.serviceid) &&
        Objects.equals(this.merchant, topup.merchant) &&
        Objects.equals(this.payItemId, topup.payItemId) &&
        Objects.equals(this.payItemDescr, topup.payItemDescr) &&
        Objects.equals(this.amountType, topup.amountType) &&
        Objects.equals(this.localCur, topup.localCur) &&
        Objects.equals(this.name, topup.name) &&
        Objects.equals(this.amountLocalCur, topup.amountLocalCur) &&
        Objects.equals(this.description, topup.description) &&
        Objects.equals(this.optStrg, topup.optStrg) &&
        Objects.equals(this.optNmb, topup.optNmb);
  }

  @Override
  public int hashCode() {
    return Objects.hash(serviceid, merchant, payItemId, payItemDescr, amountType, localCur, name, amountLocalCur, description, optStrg, optNmb);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Topup {\n");
    
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

