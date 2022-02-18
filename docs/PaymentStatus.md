# PaymentStatus

## Properties
Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**ptn** | **String** | Unique payment collection transaction number | 
**serviceid** | **String** | Unique  service Identifier | 
**merchant** | **String** | Merchant code | 
**timestamp** | [**LocalDate**](LocalDate.md) | Timestamp of processing in  System (ISO 8601) | 
**receiptNumber** | **String** | Receipt number - alternative identifier of payment - bound to agent context and is NOT unique | 
**veriCode** | **String** | Verification code for receipt number | 
**clearingDate** | [**LocalDate**](LocalDate.md) | Date on information of payment information has been sent to merchant – if supported (ISO 8601) | 
**trid** | **String** | custom external transaction reference provided during payment collection | 
**priceLocalCur** | **Float** | Price paid in local currency | 
**priceSystemCur** | **Float** | Price paid in system currency | 
**localCur** | **String** | Local currency of service. (e.g. XAF) (Format: ISO 4217) | 
**systemCur** | **String** | Currency of billing by  system (Format: ISO 4217) | 
**pin** | **String** | Digital Code to display to customer - if supplied by service |  [optional]
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
