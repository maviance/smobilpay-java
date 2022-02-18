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
import org.threeten.bp.LocalDate;
/**
 * CollectionResponse
 */

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.JavaClientCodegen", date = "2022-02-18T05:58:05.742+01:00[Africa/Douala]")
public class CollectionResponse {
  @SerializedName("ptn")
  private String ptn = null;

  @SerializedName("timestamp")
  private LocalDate timestamp = null;

  @SerializedName("agentBalance")
  private Float agentBalance = null;

  @SerializedName("receiptNumber")
  private String receiptNumber = null;

  @SerializedName("veriCode")
  private String veriCode = null;

  @SerializedName("priceLocalCur")
  private Float priceLocalCur = null;

  @SerializedName("priceSystemCur")
  private Float priceSystemCur = null;

  @SerializedName("localCur")
  private String localCur = null;

  @SerializedName("systemCur")
  private String systemCur = null;

  @SerializedName("trid")
  private String trid = null;

  @SerializedName("pin")
  private String pin = null;

  /**
   * payment processing status
   */
  @JsonAdapter(StatusEnum.Adapter.class)
  public enum StatusEnum {
    REVERSED("REVERSED"),
    PENDING("PENDING"),
    ERRORED("ERRORED"),
    UNDERINVESTIGATION("UNDERINVESTIGATION"),
    SUCCESS("SUCCESS");

    private String value;

    StatusEnum(String value) {
      this.value = value;
    }
    public String getValue() {
      return value;
    }

    @Override
    public String toString() {
      return String.valueOf(value);
    }
    public static StatusEnum fromValue(String text) {
      for (StatusEnum b : StatusEnum.values()) {
        if (String.valueOf(b.value).equals(text)) {
          return b;
        }
      }
      return null;
    }
    public static class Adapter extends TypeAdapter<StatusEnum> {
      @Override
      public void write(final JsonWriter jsonWriter, final StatusEnum enumeration) throws IOException {
        jsonWriter.value(enumeration.getValue());
      }

      @Override
      public StatusEnum read(final JsonReader jsonReader) throws IOException {
        Object value = jsonReader.nextObject();
        return StatusEnum.fromValue(String.valueOf(value));
      }
    }
  }  @SerializedName("status")
  private StatusEnum status = null;

  @SerializedName("payItemId")
  private String payItemId = null;

  @SerializedName("payItemDescr")
  private String payItemDescr = null;

  public CollectionResponse ptn(String ptn) {
    this.ptn = ptn;
    return this;
  }

   /**
   * Unique payment collection transaction number
   * @return ptn
  **/
  @Schema(required = true, description = "Unique payment collection transaction number")
  public String getPtn() {
    return ptn;
  }

  public void setPtn(String ptn) {
    this.ptn = ptn;
  }

  public CollectionResponse timestamp(LocalDate timestamp) {
    this.timestamp = timestamp;
    return this;
  }

   /**
   * Timestamp of processing in  System (ISO 8601)
   * @return timestamp
  **/
  @Schema(required = true, description = "Timestamp of processing in  System (ISO 8601)")
  public LocalDate getTimestamp() {
    return timestamp;
  }

  public void setTimestamp(LocalDate timestamp) {
    this.timestamp = timestamp;
  }

  public CollectionResponse agentBalance(Float agentBalance) {
    this.agentBalance = agentBalance;
    return this;
  }

   /**
   * Current Balance of agent account AFTER collection in system currency
   * @return agentBalance
  **/
  @Schema(required = true, description = "Current Balance of agent account AFTER collection in system currency")
  public Float getAgentBalance() {
    return agentBalance;
  }

  public void setAgentBalance(Float agentBalance) {
    this.agentBalance = agentBalance;
  }

  public CollectionResponse receiptNumber(String receiptNumber) {
    this.receiptNumber = receiptNumber;
    return this;
  }

   /**
   * Receipt number - alternative identifier of payment - bound to agent context and is NOT unique
   * @return receiptNumber
  **/
  @Schema(required = true, description = "Receipt number - alternative identifier of payment - bound to agent context and is NOT unique")
  public String getReceiptNumber() {
    return receiptNumber;
  }

  public void setReceiptNumber(String receiptNumber) {
    this.receiptNumber = receiptNumber;
  }

  public CollectionResponse veriCode(String veriCode) {
    this.veriCode = veriCode;
    return this;
  }

   /**
   * Verification code for receipt number
   * @return veriCode
  **/
  @Schema(required = true, description = "Verification code for receipt number")
  public String getVeriCode() {
    return veriCode;
  }

  public void setVeriCode(String veriCode) {
    this.veriCode = veriCode;
  }

  public CollectionResponse priceLocalCur(Float priceLocalCur) {
    this.priceLocalCur = priceLocalCur;
    return this;
  }

   /**
   * Price paid in local currency
   * @return priceLocalCur
  **/
  @Schema(required = true, description = "Price paid in local currency")
  public Float getPriceLocalCur() {
    return priceLocalCur;
  }

  public void setPriceLocalCur(Float priceLocalCur) {
    this.priceLocalCur = priceLocalCur;
  }

  public CollectionResponse priceSystemCur(Float priceSystemCur) {
    this.priceSystemCur = priceSystemCur;
    return this;
  }

   /**
   * Price paid in system currency
   * @return priceSystemCur
  **/
  @Schema(required = true, description = "Price paid in system currency")
  public Float getPriceSystemCur() {
    return priceSystemCur;
  }

  public void setPriceSystemCur(Float priceSystemCur) {
    this.priceSystemCur = priceSystemCur;
  }

  public CollectionResponse localCur(String localCur) {
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

  public CollectionResponse systemCur(String systemCur) {
    this.systemCur = systemCur;
    return this;
  }

   /**
   * Currency of billing by  system (Format: ISO 4217)
   * @return systemCur
  **/
  @Schema(required = true, description = "Currency of billing by  system (Format: ISO 4217)")
  public String getSystemCur() {
    return systemCur;
  }

  public void setSystemCur(String systemCur) {
    this.systemCur = systemCur;
  }

  public CollectionResponse trid(String trid) {
    this.trid = trid;
    return this;
  }

   /**
   * custom external transaction reference provided during payment collection
   * @return trid
  **/
  @Schema(description = "custom external transaction reference provided during payment collection")
  public String getTrid() {
    return trid;
  }

  public void setTrid(String trid) {
    this.trid = trid;
  }

  public CollectionResponse pin(String pin) {
    this.pin = pin;
    return this;
  }

   /**
   * Only for VOUCHER services - field returning a PIN or digital code. Will return “null” otherwise.
   * @return pin
  **/
  @Schema(description = "Only for VOUCHER services - field returning a PIN or digital code. Will return “null” otherwise.")
  public String getPin() {
    return pin;
  }

  public void setPin(String pin) {
    this.pin = pin;
  }

  public CollectionResponse status(StatusEnum status) {
    this.status = status;
    return this;
  }

   /**
   * payment processing status
   * @return status
  **/
  @Schema(required = true, description = "payment processing status")
  public StatusEnum getStatus() {
    return status;
  }

  public void setStatus(StatusEnum status) {
    this.status = status;
  }

  public CollectionResponse payItemId(String payItemId) {
    this.payItemId = payItemId;
    return this;
  }

   /**
   * Unique  Payment Item ID for payment item identification
   * @return payItemId
  **/
  @Schema(description = "Unique  Payment Item ID for payment item identification")
  public String getPayItemId() {
    return payItemId;
  }

  public void setPayItemId(String payItemId) {
    this.payItemId = payItemId;
  }

  public CollectionResponse payItemDescr(String payItemDescr) {
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


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    CollectionResponse collectionResponse = (CollectionResponse) o;
    return Objects.equals(this.ptn, collectionResponse.ptn) &&
        Objects.equals(this.timestamp, collectionResponse.timestamp) &&
        Objects.equals(this.agentBalance, collectionResponse.agentBalance) &&
        Objects.equals(this.receiptNumber, collectionResponse.receiptNumber) &&
        Objects.equals(this.veriCode, collectionResponse.veriCode) &&
        Objects.equals(this.priceLocalCur, collectionResponse.priceLocalCur) &&
        Objects.equals(this.priceSystemCur, collectionResponse.priceSystemCur) &&
        Objects.equals(this.localCur, collectionResponse.localCur) &&
        Objects.equals(this.systemCur, collectionResponse.systemCur) &&
        Objects.equals(this.trid, collectionResponse.trid) &&
        Objects.equals(this.pin, collectionResponse.pin) &&
        Objects.equals(this.status, collectionResponse.status) &&
        Objects.equals(this.payItemId, collectionResponse.payItemId) &&
        Objects.equals(this.payItemDescr, collectionResponse.payItemDescr);
  }

  @Override
  public int hashCode() {
    return Objects.hash(ptn, timestamp, agentBalance, receiptNumber, veriCode, priceLocalCur, priceSystemCur, localCur, systemCur, trid, pin, status, payItemId, payItemDescr);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class CollectionResponse {\n");
    
    sb.append("    ptn: ").append(toIndentedString(ptn)).append("\n");
    sb.append("    timestamp: ").append(toIndentedString(timestamp)).append("\n");
    sb.append("    agentBalance: ").append(toIndentedString(agentBalance)).append("\n");
    sb.append("    receiptNumber: ").append(toIndentedString(receiptNumber)).append("\n");
    sb.append("    veriCode: ").append(toIndentedString(veriCode)).append("\n");
    sb.append("    priceLocalCur: ").append(toIndentedString(priceLocalCur)).append("\n");
    sb.append("    priceSystemCur: ").append(toIndentedString(priceSystemCur)).append("\n");
    sb.append("    localCur: ").append(toIndentedString(localCur)).append("\n");
    sb.append("    systemCur: ").append(toIndentedString(systemCur)).append("\n");
    sb.append("    trid: ").append(toIndentedString(trid)).append("\n");
    sb.append("    pin: ").append(toIndentedString(pin)).append("\n");
    sb.append("    status: ").append(toIndentedString(status)).append("\n");
    sb.append("    payItemId: ").append(toIndentedString(payItemId)).append("\n");
    sb.append("    payItemDescr: ").append(toIndentedString(payItemDescr)).append("\n");
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
