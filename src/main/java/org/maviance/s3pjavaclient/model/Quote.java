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
import org.threeten.bp.LocalDate;

/**
 * Quote
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaClientCodegen", date = "2021-06-15T16:12:17.518+01:00")
public class Quote {
  @SerializedName("quoteId")
  private String quoteId = null;

  @SerializedName("expiresAt")
  private LocalDate expiresAt = null;

  @SerializedName("payItemId")
  private String payItemId = null;

  @SerializedName("amountLocalCur")
  private Float amountLocalCur = null;

  @SerializedName("priceLocalCur")
  private Float priceLocalCur = null;

  @SerializedName("priceSystemCur")
  private Float priceSystemCur = null;

  @SerializedName("localCur")
  private String localCur = null;

  @SerializedName("systemCur")
  private String systemCur = null;

  @SerializedName("promotion")
  private String promotion = null;

  public Quote quoteId(String quoteId) {
    this.quoteId = quoteId;
    return this;
  }

   /**
   * Unique quote number (UUID)
   * @return quoteId
  **/
  @ApiModelProperty(required = true, value = "Unique quote number (UUID)")
  public String getQuoteId() {
    return quoteId;
  }

  public void setQuoteId(String quoteId) {
    this.quoteId = quoteId;
  }

  public Quote expiresAt(LocalDate expiresAt) {
    this.expiresAt = expiresAt;
    return this;
  }

   /**
   * Expiration timestamp. The quote will only stay active the expiration time. (Format: ISO 8601)
   * @return expiresAt
  **/
  @ApiModelProperty(required = true, value = "Expiration timestamp. The quote will only stay active the expiration time. (Format: ISO 8601)")
  public LocalDate getExpiresAt() {
    return expiresAt;
  }

  public void setExpiresAt(LocalDate expiresAt) {
    this.expiresAt = expiresAt;
  }

  public Quote payItemId(String payItemId) {
    this.payItemId = payItemId;
    return this;
  }

   /**
   * Unique  Payment Item ID identifying the item to request the quote for
   * @return payItemId
  **/
  @ApiModelProperty(required = true, value = "Unique  Payment Item ID identifying the item to request the quote for")
  public String getPayItemId() {
    return payItemId;
  }

  public void setPayItemId(String payItemId) {
    this.payItemId = payItemId;
  }

  public Quote amountLocalCur(Float amountLocalCur) {
    this.amountLocalCur = amountLocalCur;
    return this;
  }

   /**
   * Service amount in local currency
   * @return amountLocalCur
  **/
  @ApiModelProperty(required = true, value = "Service amount in local currency")
  public Float getAmountLocalCur() {
    return amountLocalCur;
  }

  public void setAmountLocalCur(Float amountLocalCur) {
    this.amountLocalCur = amountLocalCur;
  }

  public Quote priceLocalCur(Float priceLocalCur) {
    this.priceLocalCur = priceLocalCur;
    return this;
  }

   /**
   * Price of payment in local currency
   * @return priceLocalCur
  **/
  @ApiModelProperty(required = true, value = "Price of payment in local currency")
  public Float getPriceLocalCur() {
    return priceLocalCur;
  }

  public void setPriceLocalCur(Float priceLocalCur) {
    this.priceLocalCur = priceLocalCur;
  }

  public Quote priceSystemCur(Float priceSystemCur) {
    this.priceSystemCur = priceSystemCur;
    return this;
  }

   /**
   * Price of payment in system currency
   * @return priceSystemCur
  **/
  @ApiModelProperty(required = true, value = "Price of payment in system currency")
  public Float getPriceSystemCur() {
    return priceSystemCur;
  }

  public void setPriceSystemCur(Float priceSystemCur) {
    this.priceSystemCur = priceSystemCur;
  }

  public Quote localCur(String localCur) {
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

  public Quote systemCur(String systemCur) {
    this.systemCur = systemCur;
    return this;
  }

   /**
   * Currency of billing by  system. (Format: ISO 4217)
   * @return systemCur
  **/
  @ApiModelProperty(required = true, value = "Currency of billing by  system. (Format: ISO 4217)")
  public String getSystemCur() {
    return systemCur;
  }

  public void setSystemCur(String systemCur) {
    this.systemCur = systemCur;
  }

  public Quote promotion(String promotion) {
    this.promotion = promotion;
    return this;
  }

   /**
   * Optional comma seperated list of current or upcoming promotions offered by the quoted service
   * @return promotion
  **/
  @ApiModelProperty(value = "Optional comma seperated list of current or upcoming promotions offered by the quoted service")
  public String getPromotion() {
    return promotion;
  }

  public void setPromotion(String promotion) {
    this.promotion = promotion;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Quote quote = (Quote) o;
    return Objects.equals(this.quoteId, quote.quoteId) &&
        Objects.equals(this.expiresAt, quote.expiresAt) &&
        Objects.equals(this.payItemId, quote.payItemId) &&
        Objects.equals(this.amountLocalCur, quote.amountLocalCur) &&
        Objects.equals(this.priceLocalCur, quote.priceLocalCur) &&
        Objects.equals(this.priceSystemCur, quote.priceSystemCur) &&
        Objects.equals(this.localCur, quote.localCur) &&
        Objects.equals(this.systemCur, quote.systemCur) &&
        Objects.equals(this.promotion, quote.promotion);
  }

  @Override
  public int hashCode() {
    return Objects.hash(quoteId, expiresAt, payItemId, amountLocalCur, priceLocalCur, priceSystemCur, localCur, systemCur, promotion);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Quote {\n");
    
    sb.append("    quoteId: ").append(toIndentedString(quoteId)).append("\n");
    sb.append("    expiresAt: ").append(toIndentedString(expiresAt)).append("\n");
    sb.append("    payItemId: ").append(toIndentedString(payItemId)).append("\n");
    sb.append("    amountLocalCur: ").append(toIndentedString(amountLocalCur)).append("\n");
    sb.append("    priceLocalCur: ").append(toIndentedString(priceLocalCur)).append("\n");
    sb.append("    priceSystemCur: ").append(toIndentedString(priceSystemCur)).append("\n");
    sb.append("    localCur: ").append(toIndentedString(localCur)).append("\n");
    sb.append("    systemCur: ").append(toIndentedString(systemCur)).append("\n");
    sb.append("    promotion: ").append(toIndentedString(promotion)).append("\n");
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

