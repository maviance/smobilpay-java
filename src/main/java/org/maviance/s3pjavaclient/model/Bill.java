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
import org.threeten.bp.LocalDate;

/**
 * Bill
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaClientCodegen", date = "2021-06-15T16:12:17.518+01:00")
public class Bill {
  /**
   * &#39;Type classification of the bill&#39; &#39;\&quot;REGULAR\&quot; -&gt; Regular Bill&#39; &#39;\&quot;OVERDUE\&quot; -&gt; Late bill that is overdue&#39; 
   */
  @JsonAdapter(BillTypeEnum.Adapter.class)
  public enum BillTypeEnum {
    REGULAR("REGULAR"),
    
    OVERDUE("OVERDUE");

    private String value;

    BillTypeEnum(String value) {
      this.value = value;
    }

    public String getValue() {
      return value;
    }

    @Override
    public String toString() {
      return String.valueOf(value);
    }

    public static BillTypeEnum fromValue(String text) {
      for (BillTypeEnum b : BillTypeEnum.values()) {
        if (String.valueOf(b.value).equals(text)) {
          return b;
        }
      }
      return null;
    }

    public static class Adapter extends TypeAdapter<BillTypeEnum> {
      @Override
      public void write(final JsonWriter jsonWriter, final BillTypeEnum enumeration) throws IOException {
        jsonWriter.value(enumeration.getValue());
      }

      @Override
      public BillTypeEnum read(final JsonReader jsonReader) throws IOException {
        String value = jsonReader.nextString();
        return BillTypeEnum.fromValue(String.valueOf(value));
      }
    }
  }

  @SerializedName("billType")
  private BillTypeEnum billType = null;

  @SerializedName("penaltyAmount")
  private Double penaltyAmount = null;

  @SerializedName("payOrder")
  private BigDecimal payOrder = null;

  @SerializedName("payItemId")
  private String payItemId = null;

  @SerializedName("payItemDescr")
  private String payItemDescr = null;

  @SerializedName("serviceNumber")
  private String serviceNumber = null;

  @SerializedName("serviceid")
  private Integer serviceid = null;

  @SerializedName("merchant")
  private String merchant = null;

  /**
   * &#39;Supported amount type for the payment of this bill:&#39; &#39;\&quot;FIXED\&quot; -&gt; Bill needs to be paid in full, (Payment amount &#x3D; bill amount provided in \&quot;amountLocalCur\&quot;)&#39; &#39;\&quot;PARTIAL\&quot; -&gt; Partial bill amount can be paid. (Payment amount &lt; bill amount provided in \&quot;amountLocalCur\&quot;))&#39; &#39;\&quot;OVERPAY\&quot; -&gt; More than the actual bill amount owed can be paid. (Payment amount &gt; bill amount provided in \&quot;amountLocalCur\&quot;). Overpayments are subject to country specific regulations and may be limited to a certain threshold. &#39; &#39;\&quot;CUSTOM\&quot; -&gt; Amount can be freely entered, independent of bill amount provided in \&quot;amountLocalCur\&quot;&#39; 
   */
  @JsonAdapter(AmountTypeEnum.Adapter.class)
  public enum AmountTypeEnum {
    FIXED("FIXED"),
    
    CUSTOM("CUSTOM"),
    
    PARTIAL("PARTIAL"),
    
    OVERPAY("OVERPAY");

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

  @SerializedName("amountLocalCur")
  private Float amountLocalCur = null;

  @SerializedName("billNumber")
  private String billNumber = null;

  @SerializedName("customerNumber")
  private String customerNumber = null;

  @SerializedName("billMonth")
  private String billMonth = null;

  @SerializedName("billYear")
  private String billYear = null;

  @SerializedName("billDate")
  private LocalDate billDate = null;

  @SerializedName("billDueDate")
  private LocalDate billDueDate = null;

  @SerializedName("optStrg")
  private String optStrg = null;

  @SerializedName("optNmb")
  private BigDecimal optNmb = null;

  public Bill billType(BillTypeEnum billType) {
    this.billType = billType;
    return this;
  }

   /**
   * &#39;Type classification of the bill&#39; &#39;\&quot;REGULAR\&quot; -&gt; Regular Bill&#39; &#39;\&quot;OVERDUE\&quot; -&gt; Late bill that is overdue&#39; 
   * @return billType
  **/
  @ApiModelProperty(required = true, value = "'Type classification of the bill' '\"REGULAR\" -> Regular Bill' '\"OVERDUE\" -> Late bill that is overdue' ")
  public BillTypeEnum getBillType() {
    return billType;
  }

  public void setBillType(BillTypeEnum billType) {
    this.billType = billType;
  }

  public Bill penaltyAmount(Double penaltyAmount) {
    this.penaltyAmount = penaltyAmount;
    return this;
  }

   /**
   * Late payment penalty amount in local currency.
   * @return penaltyAmount
  **/
  @ApiModelProperty(required = true, value = "Late payment penalty amount in local currency.")
  public Double getPenaltyAmount() {
    return penaltyAmount;
  }

  public void setPenaltyAmount(Double penaltyAmount) {
    this.penaltyAmount = penaltyAmount;
  }

  public Bill payOrder(BigDecimal payOrder) {
    this.payOrder = payOrder;
    return this;
  }

   /**
   * Payment order. The bill with the lowest number has to be paid first, starting with 1. If no payment order is enforced, all bills have the order 0.
   * minimum: 0
   * maximum: 255
   * @return payOrder
  **/
  @ApiModelProperty(required = true, value = "Payment order. The bill with the lowest number has to be paid first, starting with 1. If no payment order is enforced, all bills have the order 0.")
  public BigDecimal getPayOrder() {
    return payOrder;
  }

  public void setPayOrder(BigDecimal payOrder) {
    this.payOrder = payOrder;
  }

  public Bill payItemId(String payItemId) {
    this.payItemId = payItemId;
    return this;
  }

   /**
   * Unique  Payment Item ID for payment item identification
   * @return payItemId
  **/
  @ApiModelProperty(required = true, value = "Unique  Payment Item ID for payment item identification")
  public String getPayItemId() {
    return payItemId;
  }

  public void setPayItemId(String payItemId) {
    this.payItemId = payItemId;
  }

  public Bill payItemDescr(String payItemDescr) {
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

  public Bill serviceNumber(String serviceNumber) {
    this.serviceNumber = serviceNumber;
    return this;
  }

   /**
   * service number with merchant (e.g. meter number in bills from a utility provider or a phone number for a mobile operator)
   * @return serviceNumber
  **/
  @ApiModelProperty(required = true, value = "service number with merchant (e.g. meter number in bills from a utility provider or a phone number for a mobile operator)")
  public String getServiceNumber() {
    return serviceNumber;
  }

  public void setServiceNumber(String serviceNumber) {
    this.serviceNumber = serviceNumber;
  }

  public Bill serviceid(Integer serviceid) {
    this.serviceid = serviceid;
    return this;
  }

   /**
   * Unique  Service Identifier
   * @return serviceid
  **/
  @ApiModelProperty(required = true, value = "Unique  Service Identifier")
  public Integer getServiceid() {
    return serviceid;
  }

  public void setServiceid(Integer serviceid) {
    this.serviceid = serviceid;
  }

  public Bill merchant(String merchant) {
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

  public Bill amountType(AmountTypeEnum amountType) {
    this.amountType = amountType;
    return this;
  }

   /**
   * &#39;Supported amount type for the payment of this bill:&#39; &#39;\&quot;FIXED\&quot; -&gt; Bill needs to be paid in full, (Payment amount &#x3D; bill amount provided in \&quot;amountLocalCur\&quot;)&#39; &#39;\&quot;PARTIAL\&quot; -&gt; Partial bill amount can be paid. (Payment amount &lt; bill amount provided in \&quot;amountLocalCur\&quot;))&#39; &#39;\&quot;OVERPAY\&quot; -&gt; More than the actual bill amount owed can be paid. (Payment amount &gt; bill amount provided in \&quot;amountLocalCur\&quot;). Overpayments are subject to country specific regulations and may be limited to a certain threshold. &#39; &#39;\&quot;CUSTOM\&quot; -&gt; Amount can be freely entered, independent of bill amount provided in \&quot;amountLocalCur\&quot;&#39; 
   * @return amountType
  **/
  @ApiModelProperty(required = true, value = "'Supported amount type for the payment of this bill:' '\"FIXED\" -> Bill needs to be paid in full, (Payment amount = bill amount provided in \"amountLocalCur\")' '\"PARTIAL\" -> Partial bill amount can be paid. (Payment amount < bill amount provided in \"amountLocalCur\"))' '\"OVERPAY\" -> More than the actual bill amount owed can be paid. (Payment amount > bill amount provided in \"amountLocalCur\"). Overpayments are subject to country specific regulations and may be limited to a certain threshold. ' '\"CUSTOM\" -> Amount can be freely entered, independent of bill amount provided in \"amountLocalCur\"' ")
  public AmountTypeEnum getAmountType() {
    return amountType;
  }

  public void setAmountType(AmountTypeEnum amountType) {
    this.amountType = amountType;
  }

  public Bill localCur(String localCur) {
    this.localCur = localCur;
    return this;
  }

   /**
   * Local currency of service.(eg: XAF) (Format: ISO 4217)
   * @return localCur
  **/
  @ApiModelProperty(required = true, value = "Local currency of service.(eg: XAF) (Format: ISO 4217)")
  public String getLocalCur() {
    return localCur;
  }

  public void setLocalCur(String localCur) {
    this.localCur = localCur;
  }

  public Bill amountLocalCur(Float amountLocalCur) {
    this.amountLocalCur = amountLocalCur;
    return this;
  }

   /**
   * Open bill amount in local currency – (only searchable bills).
   * @return amountLocalCur
  **/
  @ApiModelProperty(value = "Open bill amount in local currency – (only searchable bills).")
  public Float getAmountLocalCur() {
    return amountLocalCur;
  }

  public void setAmountLocalCur(Float amountLocalCur) {
    this.amountLocalCur = amountLocalCur;
  }

  public Bill billNumber(String billNumber) {
    this.billNumber = billNumber;
    return this;
  }

   /**
   * Unique bill number in selected merchant service
   * @return billNumber
  **/
  @ApiModelProperty(value = "Unique bill number in selected merchant service")
  public String getBillNumber() {
    return billNumber;
  }

  public void setBillNumber(String billNumber) {
    this.billNumber = billNumber;
  }

  public Bill customerNumber(String customerNumber) {
    this.customerNumber = customerNumber;
    return this;
  }

   /**
   * Customer number with merchant
   * @return customerNumber
  **/
  @ApiModelProperty(value = "Customer number with merchant")
  public String getCustomerNumber() {
    return customerNumber;
  }

  public void setCustomerNumber(String customerNumber) {
    this.customerNumber = customerNumber;
  }

  public Bill billMonth(String billMonth) {
    this.billMonth = billMonth;
    return this;
  }

   /**
   * Month of bill generation. Format: MM e.g. \&quot;03\&quot; for March
   * @return billMonth
  **/
  @ApiModelProperty(value = "Month of bill generation. Format: MM e.g. \"03\" for March")
  public String getBillMonth() {
    return billMonth;
  }

  public void setBillMonth(String billMonth) {
    this.billMonth = billMonth;
  }

  public Bill billYear(String billYear) {
    this.billYear = billYear;
    return this;
  }

   /**
   * Year of bill generation. Format: YYYY e.g. \&quot;2016\&quot;
   * @return billYear
  **/
  @ApiModelProperty(value = "Year of bill generation. Format: YYYY e.g. \"2016\"")
  public String getBillYear() {
    return billYear;
  }

  public void setBillYear(String billYear) {
    this.billYear = billYear;
  }

  public Bill billDate(LocalDate billDate) {
    this.billDate = billDate;
    return this;
  }

   /**
   * Exact date of bill generation (Format: ISO 8601)
   * @return billDate
  **/
  @ApiModelProperty(value = "Exact date of bill generation (Format: ISO 8601)")
  public LocalDate getBillDate() {
    return billDate;
  }

  public void setBillDate(LocalDate billDate) {
    this.billDate = billDate;
  }

  public Bill billDueDate(LocalDate billDueDate) {
    this.billDueDate = billDueDate;
    return this;
  }

   /**
   * Bill due date (Format: ISO 8601)
   * @return billDueDate
  **/
  @ApiModelProperty(value = "Bill due date (Format: ISO 8601)")
  public LocalDate getBillDueDate() {
    return billDueDate;
  }

  public void setBillDueDate(LocalDate billDueDate) {
    this.billDueDate = billDueDate;
  }

  public Bill optStrg(String optStrg) {
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

  public Bill optNmb(BigDecimal optNmb) {
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
    Bill bill = (Bill) o;
    return Objects.equals(this.billType, bill.billType) &&
        Objects.equals(this.penaltyAmount, bill.penaltyAmount) &&
        Objects.equals(this.payOrder, bill.payOrder) &&
        Objects.equals(this.payItemId, bill.payItemId) &&
        Objects.equals(this.payItemDescr, bill.payItemDescr) &&
        Objects.equals(this.serviceNumber, bill.serviceNumber) &&
        Objects.equals(this.serviceid, bill.serviceid) &&
        Objects.equals(this.merchant, bill.merchant) &&
        Objects.equals(this.amountType, bill.amountType) &&
        Objects.equals(this.localCur, bill.localCur) &&
        Objects.equals(this.amountLocalCur, bill.amountLocalCur) &&
        Objects.equals(this.billNumber, bill.billNumber) &&
        Objects.equals(this.customerNumber, bill.customerNumber) &&
        Objects.equals(this.billMonth, bill.billMonth) &&
        Objects.equals(this.billYear, bill.billYear) &&
        Objects.equals(this.billDate, bill.billDate) &&
        Objects.equals(this.billDueDate, bill.billDueDate) &&
        Objects.equals(this.optStrg, bill.optStrg) &&
        Objects.equals(this.optNmb, bill.optNmb);
  }

  @Override
  public int hashCode() {
    return Objects.hash(billType, penaltyAmount, payOrder, payItemId, payItemDescr, serviceNumber, serviceid, merchant, amountType, localCur, amountLocalCur, billNumber, customerNumber, billMonth, billYear, billDate, billDueDate, optStrg, optNmb);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Bill {\n");
    
    sb.append("    billType: ").append(toIndentedString(billType)).append("\n");
    sb.append("    penaltyAmount: ").append(toIndentedString(penaltyAmount)).append("\n");
    sb.append("    payOrder: ").append(toIndentedString(payOrder)).append("\n");
    sb.append("    payItemId: ").append(toIndentedString(payItemId)).append("\n");
    sb.append("    payItemDescr: ").append(toIndentedString(payItemDescr)).append("\n");
    sb.append("    serviceNumber: ").append(toIndentedString(serviceNumber)).append("\n");
    sb.append("    serviceid: ").append(toIndentedString(serviceid)).append("\n");
    sb.append("    merchant: ").append(toIndentedString(merchant)).append("\n");
    sb.append("    amountType: ").append(toIndentedString(amountType)).append("\n");
    sb.append("    localCur: ").append(toIndentedString(localCur)).append("\n");
    sb.append("    amountLocalCur: ").append(toIndentedString(amountLocalCur)).append("\n");
    sb.append("    billNumber: ").append(toIndentedString(billNumber)).append("\n");
    sb.append("    customerNumber: ").append(toIndentedString(customerNumber)).append("\n");
    sb.append("    billMonth: ").append(toIndentedString(billMonth)).append("\n");
    sb.append("    billYear: ").append(toIndentedString(billYear)).append("\n");
    sb.append("    billDate: ").append(toIndentedString(billDate)).append("\n");
    sb.append("    billDueDate: ").append(toIndentedString(billDueDate)).append("\n");
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

