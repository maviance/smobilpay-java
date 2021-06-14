/*
 * Smobilpay S3P API STANDARD
 * Smobilpay Third Party STANDARD API FOR PAYMENT COLLECTIONS
 *
 * OpenAPI spec version: 3.0.0
 * 
 *
 * NOTE: This class is auto generated by the swagger code generator program.
 * https://github.com/swagger-api/swagger-codegen.git
 * Do not edit the class manually.
 */

package org.maviance.s3pjavaclient.model;

import com.google.gson.annotations.SerializedName;
import io.swagger.annotations.ApiModelProperty;

import java.util.Objects;

/**
 * QuoteRequest
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaClientCodegen", date = "2018-03-20T16:37:47.602Z")
public class QuoteRequest {
	@SerializedName("amount")
	private Float amount = null;

	@SerializedName("payItemId")
	private String payItemId = null;

	public QuoteRequest amount(Float amount) {
		this.amount = amount;
		return this;
	}

	/**
	 * Amount to be collected during the payment (in local currency of the payment
	 * item). Depends on the payment items amountType.
	 * 
	 * @return amount
	 **/
	@ApiModelProperty(required = true, value = "Amount to be collected during the payment (in local currency of the payment item). Depends on the payment items amountType.")
	public Float getAmount() {
		return amount;
	}

	public void setAmount(Float amount) {
		this.amount = amount;
	}

	public QuoteRequest payItemId(String payItemId) {
		this.payItemId = payItemId;
		return this;
	}

	/**
	 * Unique Payment Item ID identifying the item to request the quote for
	 * 
	 * @return payItemId
	 **/
	@ApiModelProperty(required = true, value = "Unique  Payment Item ID identifying the item to request the quote for")
	public String getPayItemId() {
		return payItemId;
	}

	public void setPayItemId(String payItemId) {
		this.payItemId = payItemId;
	}

	@Override
	public boolean equals(java.lang.Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		QuoteRequest quoteRequest = (QuoteRequest) o;
		return Objects.equals(this.amount, quoteRequest.amount)
				&& Objects.equals(this.payItemId, quoteRequest.payItemId);
	}

	@Override
	public int hashCode() {
		return Objects.hash(amount, payItemId);
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("class QuoteRequest {\n");

		sb.append("    amount: ").append(toIndentedString(amount)).append("\n");
		sb.append("    payItemId: ").append(toIndentedString(payItemId)).append("\n");
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
