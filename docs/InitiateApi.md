# InitiateApi

All URIs are relative to */v2*

Method | HTTP request | Description
------------- | ------------- | -------------
[**billGet**](InitiateApi.md#billGet) | **GET** /bill | Get bill payment handler
[**quotestdPost**](InitiateApi.md#quotestdPost) | **POST** /quotestd | Request quote with price details about the payment
[**subscriptionGet**](InitiateApi.md#subscriptionGet) | **GET** /subscription | Get subscription payment handler

<a name="billGet"></a>
# **billGet**
> List&lt;Bill&gt; billGet(xApiVersion, merchant, serviceid, serviceNumber)

Get bill payment handler

A request to this endpoint returns bill payment handler records for a service by a service number and retrieves its details if available. Bill payments come in 2 flavors – which are determined by the related service’s type: 1.  **SEARCHABLE_BILL** – When calling the endpoint for searchable bills, the result set will contain a list of all open bills for the selected service number. Each bill has its own Payment Item Identifier. 2.  **NON_SEARCHABLE_BILL** – When calling the endpoint for non-searchable bills, the result set will always contain a single bill item with a Payment Item ID to perform the collection for the provided service number. 

### Example
```java
// Import classes:
//import org.maviance.s3pjavaclient.ApiException;
//import org.maviance.s3pjavaclient.api.InitiateApi;


InitiateApi apiInstance = new InitiateApi();
String xApiVersion = "3.0.0"; // String | api version info
String merchant = "merchant_example"; // String | Unique  merchant code
Integer serviceid = 56; // Integer | Unique  service Identifier
String serviceNumber = "serviceNumber_example"; // String | Service number with merchant (e.g. meter number in bills from a utility provider) for which to perform the bill payment
try {
    List<Bill> result = apiInstance.billGet(xApiVersion, merchant, serviceid, serviceNumber);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling InitiateApi#billGet");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **xApiVersion** | **String**| api version info | [default to 3.0.0]
 **merchant** | **String**| Unique  merchant code |
 **serviceid** | **Integer**| Unique  service Identifier |
 **serviceNumber** | **String**| Service number with merchant (e.g. meter number in bills from a utility provider) for which to perform the bill payment |

### Return type

[**List&lt;Bill&gt;**](Bill.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="quotestdPost"></a>
# **quotestdPost**
> Quote quotestdPost(xApiVersion, body)

Request quote with price details about the payment

Calling this web-service requests a quote from the system for the payment collection of the selected payment item and the specified payment amount in the system. The amount is to be chosen based on the services amountType, so can either be fixed or a custom entered value. The third parameter specifies the payment method that the customer has chosen in order to pay for the collection, as there may be additional charges depending on the selected method. A quote will only remain available for short time (a few minutes) and will expire. A quote will return the actual costs involved in collecting the payment. A quote always needs to be requested before making a collection.\&quot; 

### Example
```java
// Import classes:
//import org.maviance.s3pjavaclient.ApiException;
//import org.maviance.s3pjavaclient.api.InitiateApi;


InitiateApi apiInstance = new InitiateApi();
String xApiVersion = "3.0.0"; // String | api version info
QuoteRequest body = new QuoteRequest(); // QuoteRequest | Quote Request
try {
    Quote result = apiInstance.quotestdPost(xApiVersion, body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling InitiateApi#quotestdPost");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **xApiVersion** | **String**| api version info | [default to 3.0.0]
 **body** | [**QuoteRequest**](QuoteRequest.md)| Quote Request | [optional]

### Return type

[**Quote**](Quote.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="subscriptionGet"></a>
# **subscriptionGet**
> List&lt;Subscription&gt; subscriptionGet(xApiVersion, merchant, serviceid, serviceNumber, customerNumber)

Get subscription payment handler

A request to this endpoint looks up a subscription record for a service by either service number or customer number and retrieves its details if available. When calling the endpoint the result set will contain a list of all available subscriptions found under the provided search criteria. Each subscription has its own Payment Item Identifier. 

### Example
```java
// Import classes:
//import org.maviance.s3pjavaclient.ApiException;
//import org.maviance.s3pjavaclient.api.InitiateApi;


InitiateApi apiInstance = new InitiateApi();
String xApiVersion = "3.0.0"; // String | api version info
String merchant = "merchant_example"; // String | Unique merchant code
String serviceid = "serviceid_example"; // String | Unique service Identifier
String serviceNumber = "serviceNumber_example"; // String | service number with merchant (e.g. policy number with an insurance company or tax number for a governmental institution)
String customerNumber = "customerNumber_example"; // String | customer number with merchant (e.g. customer number with an insurance company or account number for a governmental institution)
try {
    List<Subscription> result = apiInstance.subscriptionGet(xApiVersion, merchant, serviceid, serviceNumber, customerNumber);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling InitiateApi#subscriptionGet");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **xApiVersion** | **String**| api version info | [default to 3.0.0]
 **merchant** | **String**| Unique merchant code |
 **serviceid** | **String**| Unique service Identifier |
 **serviceNumber** | **String**| service number with merchant (e.g. policy number with an insurance company or tax number for a governmental institution) | [optional]
 **customerNumber** | **String**| customer number with merchant (e.g. customer number with an insurance company or account number for a governmental institution) | [optional]

### Return type

[**List&lt;Subscription&gt;**](Subscription.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

