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
import java.util.ArrayList;
import java.util.List;
import org.maviance.s3pjavaclient.model.I18nText;

/**
 * Service
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaClientCodegen", date = "2021-06-15T16:12:17.518+01:00")
public class Service {
  @SerializedName("serviceid")
  private Integer serviceid = null;

  @SerializedName("merchant")
  private String merchant = null;

  @SerializedName("title")
  private String title = null;

  @SerializedName("description")
  private String description = null;

  @SerializedName("category")
  private String category = null;

  @SerializedName("country")
  private String country = null;

  @SerializedName("localCur")
  private String localCur = null;

  /**
   * Type of service. This API will only provide services of the type
   */
  @JsonAdapter(TypeEnum.Adapter.class)
  public enum TypeEnum {
    SEARCHABLE_BILL("SEARCHABLE_BILL"),
    
    NON_SEARCHABLE_BILL("NON_SEARCHABLE_BILL"),
    
    PRODUCT("PRODUCT"),
    
    TOPUP("TOPUP"),
    
    SUBSCRIPTION("SUBSCRIPTION"),
    
    CASHIN("CASHIN"),
    
    CASHOUT("CASHOUT"),
    
    VOUCHER("VOUCHER");

    private String value;

    TypeEnum(String value) {
      this.value = value;
    }

    public String getValue() {
      return value;
    }

    @Override
    public String toString() {
      return String.valueOf(value);
    }

    public static TypeEnum fromValue(String text) {
      for (TypeEnum b : TypeEnum.values()) {
        if (String.valueOf(b.value).equals(text)) {
          return b;
        }
      }
      return null;
    }

    public static class Adapter extends TypeAdapter<TypeEnum> {
      @Override
      public void write(final JsonWriter jsonWriter, final TypeEnum enumeration) throws IOException {
        jsonWriter.value(enumeration.getValue());
      }

      @Override
      public TypeEnum read(final JsonReader jsonReader) throws IOException {
        String value = jsonReader.nextString();
        return TypeEnum.fromValue(String.valueOf(value));
      }
    }
  }

  @SerializedName("type")
  private TypeEnum type = null;

  /**
   * Service availability status
   */
  @JsonAdapter(StatusEnum.Adapter.class)
  public enum StatusEnum {
    ACTIVE("Active"),
    
    INACTIVE("Inactive");

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
        String value = jsonReader.nextString();
        return StatusEnum.fromValue(String.valueOf(value));
      }
    }
  }

  @SerializedName("status")
  private StatusEnum status = null;

  @SerializedName("isReqCustomerName")
  private Integer isReqCustomerName = null;

  @SerializedName("isReqCustomerAddress")
  private Integer isReqCustomerAddress = null;

  @SerializedName("isReqCustomerNumber")
  private Integer isReqCustomerNumber = null;

  @SerializedName("isReqServiceNumber")
  private Integer isReqServiceNumber = null;

  @SerializedName("labelCustomerNumber")
  private List<I18nText> labelCustomerNumber = null;

  @SerializedName("labelServiceNumber")
  private List<I18nText> labelServiceNumber = null;

  @SerializedName("isVerifiable")
  private Boolean isVerifiable = null;

  @SerializedName("validationMask")
  private String validationMask = null;

  @SerializedName("hint")
  private List<I18nText> hint = null;

  public Service serviceid(Integer serviceid) {
    this.serviceid = serviceid;
    return this;
  }

   /**
   * Unique  service Identifier. Use this value whenever “serviceid” is required in request parameters
   * @return serviceid
  **/
  @ApiModelProperty(required = true, value = "Unique  service Identifier. Use this value whenever “serviceid” is required in request parameters")
  public Integer getServiceid() {
    return serviceid;
  }

  public void setServiceid(Integer serviceid) {
    this.serviceid = serviceid;
  }

  public Service merchant(String merchant) {
    this.merchant = merchant;
    return this;
  }

   /**
   * Unique  merchant code
   * @return merchant
  **/
  @ApiModelProperty(required = true, value = "Unique  merchant code")
  public String getMerchant() {
    return merchant;
  }

  public void setMerchant(String merchant) {
    this.merchant = merchant;
  }

  public Service title(String title) {
    this.title = title;
    return this;
  }

   /**
   * Public name of service
   * @return title
  **/
  @ApiModelProperty(required = true, value = "Public name of service")
  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public Service description(String description) {
    this.description = description;
    return this;
  }

   /**
   * Service description
   * @return description
  **/
  @ApiModelProperty(required = true, value = "Service description")
  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public Service category(String category) {
    this.category = category;
    return this;
  }

   /**
   * Category of service
   * @return category
  **/
  @ApiModelProperty(required = true, value = "Category of service")
  public String getCategory() {
    return category;
  }

  public void setCategory(String category) {
    this.category = category;
  }

  public Service country(String country) {
    this.country = country;
    return this;
  }

   /**
   * Country of operation (ISO 3166-1 alpha-3)
   * @return country
  **/
  @ApiModelProperty(required = true, value = "Country of operation (ISO 3166-1 alpha-3)")
  public String getCountry() {
    return country;
  }

  public void setCountry(String country) {
    this.country = country;
  }

  public Service localCur(String localCur) {
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

  public Service type(TypeEnum type) {
    this.type = type;
    return this;
  }

   /**
   * Type of service. This API will only provide services of the type
   * @return type
  **/
  @ApiModelProperty(required = true, value = "Type of service. This API will only provide services of the type")
  public TypeEnum getType() {
    return type;
  }

  public void setType(TypeEnum type) {
    this.type = type;
  }

  public Service status(StatusEnum status) {
    this.status = status;
    return this;
  }

   /**
   * Service availability status
   * @return status
  **/
  @ApiModelProperty(required = true, value = "Service availability status")
  public StatusEnum getStatus() {
    return status;
  }

  public void setStatus(StatusEnum status) {
    this.status = status;
  }

  public Service isReqCustomerName(Integer isReqCustomerName) {
    this.isReqCustomerName = isReqCustomerName;
    return this;
  }

   /**
   * If set to true (1), the customers full name needs to be provided in the payment collection request.
   * @return isReqCustomerName
  **/
  @ApiModelProperty(required = true, value = "If set to true (1), the customers full name needs to be provided in the payment collection request.")
  public Integer isIsReqCustomerName() {
    return isReqCustomerName;
  }

  public void setIsReqCustomerName(Integer isReqCustomerName) {
    this.isReqCustomerName = isReqCustomerName;
  }

  public Service isReqCustomerAddress(Integer isReqCustomerAddress) {
    this.isReqCustomerAddress = isReqCustomerAddress;
    return this;
  }

   /**
   * If set to true (1), the customers address needs to be provided in the payment collection request.
   * @return isReqCustomerAddress
  **/
  @ApiModelProperty(required = true, value = "If set to true (1), the customers address needs to be provided in the payment collection request.")
  public Integer isIsReqCustomerAddress() {
    return isReqCustomerAddress;
  }

  public void setIsReqCustomerAddress(Integer isReqCustomerAddress) {
    this.isReqCustomerAddress = isReqCustomerAddress;
  }

  public Service isReqCustomerNumber(Integer isReqCustomerNumber) {
    this.isReqCustomerNumber = isReqCustomerNumber;
    return this;
  }

   /**
   * If set to true (1), a customer number needs to be provided in the payment collection request. Customer number meaning is different for each service.
   * @return isReqCustomerNumber
  **/
  @ApiModelProperty(required = true, value = "If set to true (1), a customer number needs to be provided in the payment collection request. Customer number meaning is different for each service.")
  public Integer isIsReqCustomerNumber() {
    return isReqCustomerNumber;
  }

  public void setIsReqCustomerNumber(Integer isReqCustomerNumber) {
    this.isReqCustomerNumber = isReqCustomerNumber;
  }

  public Service isReqServiceNumber(Integer isReqServiceNumber) {
    this.isReqServiceNumber = isReqServiceNumber;
    return this;
  }

   /**
   * If set to true (1), a service number needs to be provided in the payment collection request. Service number meaning is different for each service.
   * @return isReqServiceNumber
  **/
  @ApiModelProperty(required = true, value = "If set to true (1), a service number needs to be provided in the payment collection request. Service number meaning is different for each service.")
  public Integer isIsReqServiceNumber() {
    return isReqServiceNumber;
  }

  public void setIsReqServiceNumber(Integer isReqServiceNumber) {
    this.isReqServiceNumber = isReqServiceNumber;
  }

  public Service labelCustomerNumber(List<I18nText> labelCustomerNumber) {
    this.labelCustomerNumber = labelCustomerNumber;
    return this;
  }

  public Service addLabelCustomerNumberItem(I18nText labelCustomerNumberItem) {
    if (this.labelCustomerNumber == null) {
      this.labelCustomerNumber = new ArrayList<I18nText>();
    }
    this.labelCustomerNumber.add(labelCustomerNumberItem);
    return this;
  }

   /**
   * Label for customer number in multiple languages (if available) for this service.
   * @return labelCustomerNumber
  **/
  @ApiModelProperty(value = "Label for customer number in multiple languages (if available) for this service.")
  public List<I18nText> getLabelCustomerNumber() {
    return labelCustomerNumber;
  }

  public void setLabelCustomerNumber(List<I18nText> labelCustomerNumber) {
    this.labelCustomerNumber = labelCustomerNumber;
  }

  public Service labelServiceNumber(List<I18nText> labelServiceNumber) {
    this.labelServiceNumber = labelServiceNumber;
    return this;
  }

  public Service addLabelServiceNumberItem(I18nText labelServiceNumberItem) {
    if (this.labelServiceNumber == null) {
      this.labelServiceNumber = new ArrayList<I18nText>();
    }
    this.labelServiceNumber.add(labelServiceNumberItem);
    return this;
  }

   /**
   * Label for service number in multiple languages (if available) for this service.
   * @return labelServiceNumber
  **/
  @ApiModelProperty(value = "Label for service number in multiple languages (if available) for this service.")
  public List<I18nText> getLabelServiceNumber() {
    return labelServiceNumber;
  }

  public void setLabelServiceNumber(List<I18nText> labelServiceNumber) {
    this.labelServiceNumber = labelServiceNumber;
  }

  public Service isVerifiable(Boolean isVerifiable) {
    this.isVerifiable = isVerifiable;
    return this;
  }

   /**
   * If set to true (1), then the service number provided for this service can be verified before making a payment request
   * @return isVerifiable
  **/
  @ApiModelProperty(required = true, value = "If set to true (1), then the service number provided for this service can be verified before making a payment request")
  public Boolean isIsVerifiable() {
    return isVerifiable;
  }

  public void setIsVerifiable(Boolean isVerifiable) {
    this.isVerifiable = isVerifiable;
  }

  public Service validationMask(String validationMask) {
    this.validationMask = validationMask;
    return this;
  }

   /**
   * Optional mask for the service number entered during a payment for client side validations. All service numbers must comply to the mask in order to pass. The mask is a PCRE regular expression
   * @return validationMask
  **/
  @ApiModelProperty(value = "Optional mask for the service number entered during a payment for client side validations. All service numbers must comply to the mask in order to pass. The mask is a PCRE regular expression")
  public String getValidationMask() {
    return validationMask;
  }

  public void setValidationMask(String validationMask) {
    this.validationMask = validationMask;
  }

  public Service hint(List<I18nText> hint) {
    this.hint = hint;
    return this;
  }

  public Service addHintItem(I18nText hintItem) {
    if (this.hint == null) {
      this.hint = new ArrayList<I18nText>();
    }
    this.hint.add(hintItem);
    return this;
  }

   /**
   * Translation texts for the hint notes to be displayed to the customer for this service.
   * @return hint
  **/
  @ApiModelProperty(value = "Translation texts for the hint notes to be displayed to the customer for this service.")
  public List<I18nText> getHint() {
    return hint;
  }

  public void setHint(List<I18nText> hint) {
    this.hint = hint;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Service service = (Service) o;
    return Objects.equals(this.serviceid, service.serviceid) &&
        Objects.equals(this.merchant, service.merchant) &&
        Objects.equals(this.title, service.title) &&
        Objects.equals(this.description, service.description) &&
        Objects.equals(this.category, service.category) &&
        Objects.equals(this.country, service.country) &&
        Objects.equals(this.localCur, service.localCur) &&
        Objects.equals(this.type, service.type) &&
        Objects.equals(this.status, service.status) &&
        Objects.equals(this.isReqCustomerName, service.isReqCustomerName) &&
        Objects.equals(this.isReqCustomerAddress, service.isReqCustomerAddress) &&
        Objects.equals(this.isReqCustomerNumber, service.isReqCustomerNumber) &&
        Objects.equals(this.isReqServiceNumber, service.isReqServiceNumber) &&
        Objects.equals(this.labelCustomerNumber, service.labelCustomerNumber) &&
        Objects.equals(this.labelServiceNumber, service.labelServiceNumber) &&
        Objects.equals(this.isVerifiable, service.isVerifiable) &&
        Objects.equals(this.validationMask, service.validationMask) &&
        Objects.equals(this.hint, service.hint);
  }

  @Override
  public int hashCode() {
    return Objects.hash(serviceid, merchant, title, description, category, country, localCur, type, status, isReqCustomerName, isReqCustomerAddress, isReqCustomerNumber, isReqServiceNumber, labelCustomerNumber, labelServiceNumber, isVerifiable, validationMask, hint);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Service {\n");
    
    sb.append("    serviceid: ").append(toIndentedString(serviceid)).append("\n");
    sb.append("    merchant: ").append(toIndentedString(merchant)).append("\n");
    sb.append("    title: ").append(toIndentedString(title)).append("\n");
    sb.append("    description: ").append(toIndentedString(description)).append("\n");
    sb.append("    category: ").append(toIndentedString(category)).append("\n");
    sb.append("    country: ").append(toIndentedString(country)).append("\n");
    sb.append("    localCur: ").append(toIndentedString(localCur)).append("\n");
    sb.append("    type: ").append(toIndentedString(type)).append("\n");
    sb.append("    status: ").append(toIndentedString(status)).append("\n");
    sb.append("    isReqCustomerName: ").append(toIndentedString(isReqCustomerName)).append("\n");
    sb.append("    isReqCustomerAddress: ").append(toIndentedString(isReqCustomerAddress)).append("\n");
    sb.append("    isReqCustomerNumber: ").append(toIndentedString(isReqCustomerNumber)).append("\n");
    sb.append("    isReqServiceNumber: ").append(toIndentedString(isReqServiceNumber)).append("\n");
    sb.append("    labelCustomerNumber: ").append(toIndentedString(labelCustomerNumber)).append("\n");
    sb.append("    labelServiceNumber: ").append(toIndentedString(labelServiceNumber)).append("\n");
    sb.append("    isVerifiable: ").append(toIndentedString(isVerifiable)).append("\n");
    sb.append("    validationMask: ").append(toIndentedString(validationMask)).append("\n");
    sb.append("    hint: ").append(toIndentedString(hint)).append("\n");
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

