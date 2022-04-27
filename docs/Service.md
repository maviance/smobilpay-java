# Service

## Properties
Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**serviceid** | **Integer** | Unique  service Identifier. Use this value whenever “serviceid” is required in request parameters | 
**merchant** | **String** | Unique  merchant code | 
**title** | **String** | Public name of service | 
**description** | **String** | Service description | 
**category** | **String** | Category of service | 
**country** | **String** | Country of operation (ISO 3166-1 alpha-3) | 
**localCur** | **String** | Local currency of service. (Format: ISO 4217) | 
**type** | [**TypeEnum**](#TypeEnum) | Type of service. This API will only provide services of the type | 
**status** | [**StatusEnum**](#StatusEnum) | Service availability status | 
**isReqCustomerName** | **Boolean** | If set to true (1), the customers full name needs to be provided in the payment collection request. | 
**isReqCustomerAddress** | **Boolean** | If set to true (1), the customers address needs to be provided in the payment collection request. | 
**isReqCustomerNumber** | **Boolean** | If set to true (1), a customer number needs to be provided in the payment collection request. Customer number meaning is different for each service. | 
**isReqServiceNumber** | **Boolean** | If set to true (1), a service number needs to be provided in the payment collection request. Service number meaning is different for each service. | 
**labelCustomerNumber** | [**List&lt;I18nText&gt;**](I18nText.md) | Label for customer number in multiple languages (if available) for this service. |  [optional]
**labelServiceNumber** | [**List&lt;I18nText&gt;**](I18nText.md) | Label for service number in multiple languages (if available) for this service. |  [optional]
**isVerifiable** | **Boolean** | If set to true (1), then the service number provided for this service can be verified before making a payment request | 
**validationMask** | **String** | Optional mask for the service number entered during a payment for client side validations. All service numbers must comply to the mask in order to pass. The mask is a PCRE regular expression |  [optional]
**hint** | [**List&lt;I18nText&gt;**](I18nText.md) | Translation texts for the hint notes to be displayed to the customer for this service. |  [optional]
**denomination** | **Integer** | Service payment denomination. The payment amount must be a multiple of the denomination value. Example:  |Denomination|Amount|Valid| |-----|-----|-------| |1|100|true| |5|100|true| |200|100|false| |50|60|false|  |  [optional]

<a name="TypeEnum"></a>
## Enum: TypeEnum
Name | Value
---- | -----
SEARCHABLE_BILL | &quot;SEARCHABLE_BILL&quot;
NON_SEARCHABLE_BILL | &quot;NON_SEARCHABLE_BILL&quot;
PRODUCT | &quot;PRODUCT&quot;
TOPUP | &quot;TOPUP&quot;
SUBSCRIPTION | &quot;SUBSCRIPTION&quot;
CASHIN | &quot;CASHIN&quot;
CASHOUT | &quot;CASHOUT&quot;
VOUCHER | &quot;VOUCHER&quot;

<a name="StatusEnum"></a>
## Enum: StatusEnum
Name | Value
---- | -----
ACTIVE | &quot;Active&quot;
INACTIVE | &quot;Inactive&quot;
