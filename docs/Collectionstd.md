
# Collectionstd

## Properties
Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**ptn** | **String** | Unique payment collection transaction number | 
**timestamp** | **LocalDate** | Timestamp of processing in  System (ISO 8601) | 
**agentBalance** | **Float** | Current Balance of agent account AFTER collection in system currency | 
**receiptNumber** | **String** | Unique receipt number | 
**veriCode** | **String** | Verification code for receipt number | 
**priceLocalCur** | **Float** | Price paid in local currency | 
**priceSystemCur** | **Float** | Price paid in system currency | 
**localCur** | **String** | Local currency of service. (Format: ISO 4217) | 
**systemCur** | **String** | Currency of billing by  system (Format: ISO 4217) | 
**trid** | **String** | custom external transaction reference provided during payment collection |  [optional]
**pin** | **String** | Only for VOUCHER services - field returning a PIN or digital code. Will return “null” otherwise. |  [optional]
**status** | [**StatusEnum**](#StatusEnum) | payment processing status | 
**payItemId** | **String** | Unique  Payment Item ID for payment item identification |  [optional]
**payItemDescr** | **String** | Contains optional description about payment details (e.g. merchant provided bill types) |  [optional]


<a name="StatusEnum"></a>
## Enum: StatusEnum
Name | Value
---- | -----
REVERSED | &quot;REVERSED&quot;
DEBITED | &quot;DEBITED&quot;
PENDING | &quot;PENDING&quot;
INPROCESS | &quot;INPROCESS&quot;
ERRORED | &quot;ERRORED&quot;
UNDERINVESTIGATION | &quot;UNDERINVESTIGATION&quot;
ERROREDREFUNDED | &quot;ERROREDREFUNDED&quot;
SUCCESS | &quot;SUCCESS&quot;