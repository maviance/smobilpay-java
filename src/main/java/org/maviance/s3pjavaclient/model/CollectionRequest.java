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

/**
 * CollectionRequest
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaClientCodegen", date = "2021-06-15T16:12:17.518+01:00")
public class CollectionRequest {
  @SerializedName("quoteId")
  private String quoteId = null;

  @SerializedName("customerPhonenumber")
  private Integer customerPhonenumber = null;

  @SerializedName("customerEmailaddress")
  private String customerEmailaddress = null;

  @SerializedName("customerName")
  private String customerName = null;

  @SerializedName("customerAddress")
  private String customerAddress = null;

  @SerializedName("customerNumber")
  private String customerNumber = null;

  @SerializedName("serviceNumber")
  private String serviceNumber = null;

  @SerializedName("trid")
  private String trid = null;

  public CollectionRequest quoteId(String quoteId) {
    this.quoteId = quoteId;
    return this;
  }

   /**
   * Quote Number of the related quote that was previously requested.
   * @return quoteId
  **/
  @ApiModelProperty(required = true, value = "Quote Number of the related quote that was previously requested.")
  public String getQuoteId() {
    return quoteId;
  }

  public void setQuoteId(String quoteId) {
    this.quoteId = quoteId;
  }

  public CollectionRequest customerPhonenumber(Integer customerPhonenumber) {
    this.customerPhonenumber = customerPhonenumber;
    return this;
  }

   /**
   * Customer Phonenumber for regulatory compliance – international format with leading country code. E.g. “237699999999” for a fictional phone number 699999999 in Cameroon (237).
   * @return customerPhonenumber
  **/
  @ApiModelProperty(required = true, value = "Customer Phonenumber for regulatory compliance – international format with leading country code. E.g. “237699999999” for a fictional phone number 699999999 in Cameroon (237).")
  public Integer getCustomerPhonenumber() {
    return customerPhonenumber;
  }

  public void setCustomerPhonenumber(Integer customerPhonenumber) {
    this.customerPhonenumber = customerPhonenumber;
  }

  public CollectionRequest customerEmailaddress(String customerEmailaddress) {
    this.customerEmailaddress = customerEmailaddress;
    return this;
  }

   /**
   * Customer Email address for regulatory compliance.
   * @return customerEmailaddress
  **/
  @ApiModelProperty(required = true, value = "Customer Email address for regulatory compliance.")
  public String getCustomerEmailaddress() {
    return customerEmailaddress;
  }

  public void setCustomerEmailaddress(String customerEmailaddress) {
    this.customerEmailaddress = customerEmailaddress;
  }

  public CollectionRequest customerName(String customerName) {
    this.customerName = customerName;
    return this;
  }

   /**
   * Customer Name for regulatory compliance - only mandatory if &lt;&lt;service.isReqCustomerName &#x3D; 1&gt;&gt;
   * @return customerName
  **/
  @ApiModelProperty(value = "Customer Name for regulatory compliance - only mandatory if <<service.isReqCustomerName = 1>>")
  public String getCustomerName() {
    return customerName;
  }

  public void setCustomerName(String customerName) {
    this.customerName = customerName;
  }

  public CollectionRequest customerAddress(String customerAddress) {
    this.customerAddress = customerAddress;
    return this;
  }

   /**
   * Customer Address for regulatory compliance - only mandatory if &lt;&lt;service.isReqCustomerAddress &#x3D; 1&gt;&gt;
   * @return customerAddress
  **/
  @ApiModelProperty(value = "Customer Address for regulatory compliance - only mandatory if <<service.isReqCustomerAddress = 1>>")
  public String getCustomerAddress() {
    return customerAddress;
  }

  public void setCustomerAddress(String customerAddress) {
    this.customerAddress = customerAddress;
  }

  public CollectionRequest customerNumber(String customerNumber) {
    this.customerNumber = customerNumber;
    return this;
  }

   /**
   * Customer number - only mandatory if &lt;&lt;service.isReqCustomerNumber &#x3D; 1&gt;&gt;
   * @return customerNumber
  **/
  @ApiModelProperty(value = "Customer number - only mandatory if <<service.isReqCustomerNumber = 1>>")
  public String getCustomerNumber() {
    return customerNumber;
  }

  public void setCustomerNumber(String customerNumber) {
    this.customerNumber = customerNumber;
  }

  public CollectionRequest serviceNumber(String serviceNumber) {
    this.serviceNumber = serviceNumber;
    return this;
  }

   /**
   * Service number – only mandatory if &lt;&lt;service.isReqServiceNumber &#x3D; 1&gt;&gt;. Usually contains the target of a payment collection.
   * @return serviceNumber
  **/
  @ApiModelProperty(value = "Service number – only mandatory if <<service.isReqServiceNumber = 1>>. Usually contains the target of a payment collection.")
  public String getServiceNumber() {
    return serviceNumber;
  }

  public void setServiceNumber(String serviceNumber) {
    this.serviceNumber = serviceNumber;
  }

  public CollectionRequest trid(String trid) {
    this.trid = trid;
    return this;
  }

   /**
   * custom external transaction reference - custom field to be freely used for internal payment collection referencing. Should be unique. **NOTE:** The API does not manage transaction references (e.g. run unique validation) – this value needs to be managed by the client’s system.
   * @return trid
  **/
  @ApiModelProperty(value = "custom external transaction reference - custom field to be freely used for internal payment collection referencing. Should be unique. **NOTE:** The API does not manage transaction references (e.g. run unique validation) – this value needs to be managed by the client’s system.")
  public String getTrid() {
    return trid;
  }

  public void setTrid(String trid) {
    this.trid = trid;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    CollectionRequest collectionRequest = (CollectionRequest) o;
    return Objects.equals(this.quoteId, collectionRequest.quoteId) &&
        Objects.equals(this.customerPhonenumber, collectionRequest.customerPhonenumber) &&
        Objects.equals(this.customerEmailaddress, collectionRequest.customerEmailaddress) &&
        Objects.equals(this.customerName, collectionRequest.customerName) &&
        Objects.equals(this.customerAddress, collectionRequest.customerAddress) &&
        Objects.equals(this.customerNumber, collectionRequest.customerNumber) &&
        Objects.equals(this.serviceNumber, collectionRequest.serviceNumber) &&
        Objects.equals(this.trid, collectionRequest.trid);
  }

  @Override
  public int hashCode() {
    return Objects.hash(quoteId, customerPhonenumber, customerEmailaddress, customerName, customerAddress, customerNumber, serviceNumber, trid);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class CollectionRequest {\n");
    
    sb.append("    quoteId: ").append(toIndentedString(quoteId)).append("\n");
    sb.append("    customerPhonenumber: ").append(toIndentedString(customerPhonenumber)).append("\n");
    sb.append("    customerEmailaddress: ").append(toIndentedString(customerEmailaddress)).append("\n");
    sb.append("    customerName: ").append(toIndentedString(customerName)).append("\n");
    sb.append("    customerAddress: ").append(toIndentedString(customerAddress)).append("\n");
    sb.append("    customerNumber: ").append(toIndentedString(customerNumber)).append("\n");
    sb.append("    serviceNumber: ").append(toIndentedString(serviceNumber)).append("\n");
    sb.append("    trid: ").append(toIndentedString(trid)).append("\n");
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
