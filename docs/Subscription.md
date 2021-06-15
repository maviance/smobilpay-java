
# Subscription

## Properties
Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**serviceNumber** | **String** | service number with merchant (e.g. policy number with an insurance company or tax number for a governmental institution) | 
**serviceid** | **String** | Unique Service Identifier | 
**merchant** | **String** | Unique merchant code | 
**payItemId** | **String** | Unique Payment Item ID identifying the subscription | 
**payItemDescr** | **String** | Optional description about payment details |  [optional]
**amountType** | [**AmountTypeEnum**](#AmountTypeEnum) | &#39;Supported amount type for the payment of this subscription:&#39; &#39;\&quot;FIXED\&quot; -&gt; subscription needs to be paid in full, (Payment amount &#x3D; subscription amount provided in \&quot;amount\&quot;)&#39; &#39;\&quot;PARTIAL\&quot; -&gt; Partial subscription amount can be paid. (Payment amount &lt; subscription amount provided in \&quot;amount\&quot;))&#39; &#39;\&quot;OVERPAY\&quot; -&gt; More than the actual subscription amount owed can be paid. (Payment amount &gt; subscription amount provided in \&quot;amount\&quot;). Overpayments are subject to country specific regulations and may be limited to a certain threshold. &#39; &#39;\&quot;CUSTOM\&quot; -&gt; Amount can be freely entered, independent of subscription amount provided in \&quot;amount\&quot;&#39;  | 
**name** | **String** | Title/Name of subscription | 
**localCur** | **String** | Local currency of service. (Format: ISO 4217) | 
**amountLocalCur** | **Float** | Payable amount in local currency | 
**customerReference** | **String** | Optional Customer reference |  [optional]
**customerName** | **String** | Customer Name |  [optional]
**customerNumber** | **String** | Optional Customer number with merchant |  [optional]
**startDate** | [**LocalDate**](LocalDate.md) | Optional start date (Format: ISO 8601) |  [optional]
**dueDate** | [**LocalDate**](LocalDate.md) | Optional due due date (Format: ISO 8601) |  [optional]
**endDate** | [**LocalDate**](LocalDate.md) | Optional end date (Format: ISO 8601) |  [optional]
**optStrg** | **String** | Optional string field to carry additional information |  [optional]
**optNmb** | [**BigDecimal**](BigDecimal.md) | Optional number field to carry additional information |  [optional]


<a name="AmountTypeEnum"></a>
## Enum: AmountTypeEnum
Name | Value
---- | -----
FIXED | &quot;FIXED&quot;
CUSTOM | &quot;CUSTOM&quot;
PARTIAL | &quot;PARTIAL&quot;
OVERPAY | &quot;OVERPAY&quot;



