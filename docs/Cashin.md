# Cashin

## Properties
Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**serviceid** | **Integer** | Unique  service Identifier. Idenfies the service this cashin package belongs to. Use this value whenever “serviceid” is required in request parameters. | 
**merchant** | **String** | Unique  merchant code identifying the merchant this cashin belongs to. | 
**payItemId** | **String** | Unique  Payment Item ID identifying the cashin package to use for the mobile wallet deposit | 
**payItemDescr** | **String** | Contains optional description about payment details |  [optional]
**amountType** | [**AmountTypeEnum**](#AmountTypeEnum) | &#x27;Supported amount type for the payment of this product:&#x27; &#x27;\&quot;FIXED\&quot; -&gt; Cashin amount is fixed to the amount listed in the field \&quot;amount\&quot;&#x27; &#x27;\&quot;CUSTOM\&quot; -&gt; A custom amount can be entered&#x27;  | 
**localCur** | **String** | Local currency of service. (Format: ISO 4217) | 
**name** | **String** | Customer friendly name for cashin package to be displayed | 
**amountLocalCur** | **Float** | Cost of cash-in operation in local currency – only set for FIXED amounts.Otherwise null . |  [optional]
**description** | **String** | Optional description of cashin package |  [optional]
**optStrg** | **String** | Optional string field |  [optional]
**optNmb** | [**BigDecimal**](BigDecimal.md) | Optional number field |  [optional]

<a name="AmountTypeEnum"></a>
## Enum: AmountTypeEnum
Name | Value
---- | -----
FIXED | &quot;FIXED&quot;
CUSTOM | &quot;CUSTOM&quot;
