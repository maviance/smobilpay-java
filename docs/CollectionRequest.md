# CollectionRequest

## Properties
Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**quoteId** | **String** | Quote Number of the related quote that was previously requested. | 
**customerPhonenumber** | **String** | Customer Phonenumber for regulatory compliance – international format with leading country code. E.g. “237699999999” for a fictional phone number 699999999 in Cameroon (237). | 
**customerEmailaddress** | **String** | Customer Email address for regulatory compliance. | 
**customerName** | **String** | Customer Name for regulatory compliance - only mandatory if &lt;&lt;service.isReqCustomerName &#x3D; 1&gt;&gt; |  [optional]
**customerAddress** | **String** | Customer Address for regulatory compliance - only mandatory if &lt;&lt;service.isReqCustomerAddress &#x3D; 1&gt;&gt; |  [optional]
**customerNumber** | **String** | Customer number - only mandatory if &lt;&lt;service.isReqCustomerNumber &#x3D; 1&gt;&gt; |  [optional]
**serviceNumber** | **String** | Service number – only mandatory if &lt;&lt;service.isReqServiceNumber &#x3D; 1&gt;&gt;. Usually contains the target of a payment collection. |  [optional]
**trid** | **String** | custom external transaction reference - custom field to be freely used for internal payment collection referencing. Should be unique. **NOTE:** The API does not manage transaction references (e.g. run unique validation) – this value needs to be managed by the client’s system. |  [optional]
