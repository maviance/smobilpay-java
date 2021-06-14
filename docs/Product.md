
# Product

## Properties
Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**serviceid** | **Integer** | Unique  service Identifier. Identifies the service this product belongs to. | 
**merchant** | **String** | Unique  merchant code of associated merchant | 
**payItemId** | **String** | Unique  Payment Item ID identifying the product to be purchased | 
**payItemDescr** | **String** | Contains optional description about payment details (e.g. merchant provided bill types) |  [optional]
**amountType** | [**AmountTypeEnum**](#AmountTypeEnum) | &#39;Supported amount type for the payment of this product:&#39; &#39;\&quot;FIXED\&quot; -&gt; Product needs to be paid in full by the amount provided in “amount”&#39; &#39;\&quot;CUSTOM\&quot; -&gt; Amount must be freely entered&#39;  | 
**localCur** | **String** | Local currency of service. (Format: ISO 4217) | 
**name** | **String** | Customer friendly name for product to used for presentation | 
**amountLocalCur** | **Float** | Cost of product in local currency – only set for FIXED amounts. |  [optional]
**description** | **String** | Optional description of product |  [optional]
**optStrg** | **String** | Optional string field |  [optional]
**optNmb** | **BigDecimal** | Optional number field |  [optional]


<a name="AmountTypeEnum"></a>
## Enum: AmountTypeEnum
Name | Value
---- | -----
FIXED | &quot;FIXED&quot;
CUSTOM | &quot;CUSTOM&quot;