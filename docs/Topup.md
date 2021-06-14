
# Topup

## Properties
Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**serviceid** | **Integer** | Unique  service Identifier. Idenfies the service this topup package belongs to. Use this value whenever “serviceid” is required in request parameters. | 
**merchant** | **String** | Unique  merchant code identifying the merchant this topup belongs to. | 
**payItemId** | **String** | Unique  Payment Item ID identifying the topup package to be purchased | 
**payItemDescr** | **String** | Contains optional description about payment details (e.g. merchant provided bill types) |  [optional]
**amountType** | [**AmountTypeEnum**](#AmountTypeEnum) | &#39;Supported amount type for the payment of this product:&#39; &#39;\&quot;FIXED\&quot; -&gt; Topup needs to be paid in full by the amount provided in \&quot;amount\&quot;&#39; &#39;\&quot;CUSTOM\&quot; -&gt; Amount must be freely entered&#39;  | 
**localCur** | **String** | Local currency of service. (Format: ISO 4217) | 
**name** | **String** | Customer friendly name for topup package to be displayed | 
**amountLocalCur** | **Float** | Cost of topup package in local currency – only set for FIXED amounts.Otherwise null . |  [optional]
**description** | **String** | Optional description of topup package |  [optional]
**optStrg** | **String** | Optional string field |  [optional]
**optNmb** | **BigDecimal** | Optional number field |  [optional]


<a name="AmountTypeEnum"></a>
## Enum: AmountTypeEnum
Name | Value
---- | -----
FIXED | &quot;FIXED&quot;
CUSTOM | &quot;CUSTOM&quot;