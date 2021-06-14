
# Bill

## Properties
Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**billType** | [**BillTypeEnum**](#BillTypeEnum) | &#39;Type classification of the bill&#39; &#39;\&quot;REGULAR\&quot; -&gt; Regular Bill&#39; &#39;\&quot;OVERDUE\&quot; -&gt; Late bill that is overdue&#39;  | 
**penaltyAmount** | **Double** | Late payment penalty amount in local currency. | 
**payOrder** | **BigDecimal** | Payment order. The bill with the lowest number has to be paid first, starting with 1. If no payment order is enforced, all bills have the order 0. | 
**payItemId** | **String** | Unique  Payment Item ID for payment item identification | 
**payItemDescr** | **String** | Contains optional description about payment details (e.g. merchant provided bill types) |  [optional]
**serviceNumber** | **String** | service number with merchant (e.g. meter number in bills from a utility provider or a phone number for a mobile operator) | 
**serviceid** | **Integer** | Unique  Service Identifier | 
**merchant** | **String** | Unique  merchant code | 
**amountType** | [**AmountTypeEnum**](#AmountTypeEnum) | &#39;Supported amount type for the payment of this bill:&#39; &#39;\&quot;FIXED\&quot; -&gt; Bill needs to be paid in full, (Payment amount &#x3D; bill amount provided in \&quot;amountLocalCur\&quot;)&#39; &#39;\&quot;PARTIAL\&quot; -&gt; Partial bill amount can be paid. (Payment amount &lt; bill amount provided in \&quot;amountLocalCur\&quot;))&#39; &#39;\&quot;OVERPAY\&quot; -&gt; More than the actual bill amount owed can be paid. (Payment amount &gt; bill amount provided in \&quot;amountLocalCur\&quot;). Overpayments are subject to country specific regulations and may be limited to a certain threshold. &#39;  &#39;\&quot;CUSTOM\&quot; -&gt; Amount can be freely entered, independent of bill amount provided in \&quot;amountLocalCur\&quot;&#39;  | 
**localCur** | **String** | Local currency of service.(eg: XAF) (Format: ISO 4217) | 
**amountLocalCur** | **Float** | Open bill amount in local currency – (only searchable bills). |  [optional]
**billNumber** | **String** | Unique bill number in selected merchant service |  [optional]
**customerNumber** | **String** | Customer number with merchant |  [optional]
**billMonth** | **String** | Month of bill generation. Format: MM e.g. \&quot;03\&quot; for March |  [optional]
**billYear** | **String** | Year of bill generation. Format: YYYY e.g. \&quot;2016\&quot; |  [optional]
**billDate** | **LocalDate**| Exact date of bill generation (Format: ISO 8601) |  [optional]
**billDueDate** | **LocalDate** | Bill due date (Format: ISO 8601) |  [optional]
**optStrg** | **String** | Optional string field |  [optional]
**optNmb** | **BigDecimal** | Optional number field |  [optional]


<a name="BillTypeEnum"></a>
## Enum: BillTypeEnum
Name | Value
---- | -----
REGULAR | &quot;REGULAR&quot;
OVERDUE | &quot;OVERDUE&quot;


<a name="AmountTypeEnum"></a>
## Enum: AmountTypeEnum
Name | Value
---- | -----
FIXED | &quot;FIXED&quot;
CUSTOM | &quot;CUSTOM&quot;
PARTIAL | &quot;PARTIAL&quot;
OVERPAY | &quot;OVERPAY&quot;