# CollectionApi

All URIs are relative to *https://localhost/v2*

Method | HTTP request | Description
------------- | ------------- | -------------
[**billGet**](CollectionApi.md#billGet) | **GET** /bill | Get bill payment handler
[**cashinGet**](CollectionApi.md#cashinGet) | **GET** /cashin | Retrieve available cashin packages
[**cashoutGet**](CollectionApi.md#cashoutGet) | **GET** /cashout | Retrieves available cashout packages
[**collectstdPost**](CollectionApi.md#collectstdPost) | **POST** /collectstd | Execute payment collection
[**productGet**](CollectionApi.md#productGet) | **GET** /product | Retrieve list of available products
[**quotestdPost**](CollectionApi.md#quotestdPost) | **POST** /quotestd | Request quote with price details about the payment
[**subscriptionGet**](CollectionApi.md#subscriptionGet) | **GET** /subscription | Get subscription payment handler
[**topupGet**](CollectionApi.md#topupGet) | **GET** /topup | Retrieve available topup packages
[**verifyGet**](CollectionApi.md#verifyGet) | **GET** /verify | Verify service number
[**voucherGet**](CollectionApi.md#voucherGet) | **GET** /voucher | Retrieve list of available vouchers to purchase


<a name="billGet"></a>
# **billGet**
> List&lt;Bill&gt; billGet(xApiVersion, merchant, serviceid, serviceNumber)

Get bill payment handler

A request to this endpoint returns bill payment handler records for a service by a service number and retrieves its details if available. Bill payments come in 2 flavors – which are determined by the related service’s type:  1.  **SEARCHABLE_BILL** – When calling the endpoint for searchable bills, the result set will contain a list of all open bills for the selected service number. Each bill has its own Payment Item Identifier. 2.  **NON_SEARCHABLE_BILL** – When calling the endpoint for non-searchable bills, the result set will always contain a single bill item with a Payment Item ID to perform the collection for the provided service number. 

### Example
```java
// Import classes:
//import io.swagger.client.ApiException;
//import io.swagger.client.api.CollectionApi;


CollectionApi apiInstance = new CollectionApi();
String xApiVersion = "3.0.0"; // String | api version info
String merchant = "merchant_example"; // String | Unique  merchant code
Integer serviceid = 56; // Integer | Unique  service Identifier
String serviceNumber = "serviceNumber_example"; // String | Service number with merchant (e.g. meter number in bills from a utility provider) for which to perform the bill payment
try {
    List<Bill> result = apiInstance.billGet(xApiVersion, merchant, serviceid, serviceNumber);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling CollectionApi#billGet");
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

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="cashinGet"></a>
# **cashinGet**
> List&lt;Cashin&gt; cashinGet(xApiVersion, serviceid)

Retrieve available cashin packages

This service provides available cashin packages to be made to the system.

### Example
```java
// Import classes:
//import io.swagger.client.ApiException;
//import io.swagger.client.api.CollectionApi;


CollectionApi apiInstance = new CollectionApi();
String xApiVersion = "3.0.0"; // String | api version info
Integer serviceid = 56; // Integer | Filter cashin packages for only the selected service
try {
    List<Cashin> result = apiInstance.cashinGet(xApiVersion, serviceid);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling CollectionApi#cashinGet");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **xApiVersion** | **String**| api version info | [default to 3.0.0]
 **serviceid** | **Integer**| Filter cashin packages for only the selected service | [optional]

### Return type

[**List&lt;Cashin&gt;**](Cashin.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="cashoutGet"></a>
# **cashoutGet**
> List&lt;Cashout&gt; cashoutGet(xApiVersion, serviceid)

Retrieves available cashout packages

This service provides available cashout packages to be made to the system.

### Example
```java
// Import classes:
//import io.swagger.client.ApiException;
//import io.swagger.client.api.CollectionApi;


CollectionApi apiInstance = new CollectionApi();
String xApiVersion = "3.0.0"; // String | api version info
Integer serviceid = 56; // Integer | Filter cashout packages for only the selected service
try {
    List<Cashout> result = apiInstance.cashoutGet(xApiVersion, serviceid);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling CollectionApi#cashoutGet");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **xApiVersion** | **String**| api version info | [default to 3.0.0]
 **serviceid** | **Integer**| Filter cashout packages for only the selected service | [optional]

### Return type

[**List&lt;Cashout&gt;**](Cashout.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="collectstdPost"></a>
# **collectstdPost**
> Collectionstd collectstdPost(xApiVersion, body)

Execute payment collection

This endpoint executes a payment collection. Any collection will reduce the agent balance by service amount plus the service fee. Each collection must include a reference to corresponding quote and payment authorization token. Whether or not fields are mandatory depends on the service configuration

### Example
```java
// Import classes:
//import io.swagger.client.ApiException;
//import io.swagger.client.api.CollectionApi;


CollectionApi apiInstance = new CollectionApi();
String xApiVersion = "3.0.0"; // String | api version info
CollectionstdRequest body = new CollectionstdRequest(); // CollectionstdRequest | Collection Request
try {
    Collectionstd result = apiInstance.collectstdPost(xApiVersion, body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling CollectionApi#collectstdPost");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **xApiVersion** | **String**| api version info | [default to 3.0.0]
 **body** | [**CollectionstdRequest**](CollectionstdRequest.md)| Collection Request | [optional]

### Return type

[**Collectionstd**](Collectionstd.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="productGet"></a>
# **productGet**
> List&lt;Product&gt; productGet(xApiVersion, serviceid)

Retrieve list of available products

This service provides a list of all available products for all services.

### Example
```java
// Import classes:
//import io.swagger.client.ApiException;
//import io.swagger.client.api.CollectionApi;


CollectionApi apiInstance = new CollectionApi();
String xApiVersion = "3.0.0"; // String | api version info
Integer serviceid = 56; // Integer | Filter products to only the selected service
try {
    List<Product> result = apiInstance.productGet(xApiVersion, serviceid);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling CollectionApi#productGet");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **xApiVersion** | **String**| api version info | [default to 3.0.0]
 **serviceid** | **Integer**| Filter products to only the selected service | [optional]

### Return type

[**List&lt;Product&gt;**](Product.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="quotestdPost"></a>
# **quotestdPost**
> Quotestd quotestdPost(xApiVersion, body)

Request quote with price details about the payment

Calling this web-service requests a quote from the system for the payment collection of the selected payment item and the specified payment amount in the system. The amount is to be chosen based on the services amountType, so can either be fixed or a custom entered value. The third parameter specifies the payment method that the customer has chosen in order to pay for the collection, as there may be additional charges depending on the selected method. A quote will only remain available for short time (a few minutes) and will expire. A quote will return the actual costs involved in collecting the payment. A quote always needs to be requested before making a collection.\&quot; 

### Example
```java
// Import classes:
//import io.swagger.client.ApiException;
//import io.swagger.client.api.CollectionApi;


CollectionApi apiInstance = new CollectionApi();
String xApiVersion = "3.0.0"; // String | api version info
QuoteRequest body = new QuoteRequest(); // QuoteRequest | Quote Request
try {
    Quotestd result = apiInstance.quotestdPost(xApiVersion, body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling CollectionApi#quotestdPost");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **xApiVersion** | **String**| api version info | [default to 3.0.0]
 **body** | [**QuoteRequest**](QuoteRequest.md)| Quote Request | [optional]

### Return type

[**Quotestd**](Quotestd.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="subscriptionGet"></a>
# **subscriptionGet**
> List&lt;Subscription&gt; subscriptionGet(xApiVersion, merchant, serviceid, serviceNumber)

Get subscription payment handler

A request to this endpoint looks up a subscription record for a service by service number and retrieves its details if available. When calling the endpoint the result set will contain a list of all available subscriptions registered under the provided service number. Each subscription has its own Payment Item Identifier. 

### Example
```java
// Import classes:
//import io.swagger.client.ApiException;
//import io.swagger.client.api.CollectionApi;


CollectionApi apiInstance = new CollectionApi();
String xApiVersion = "3.0.0"; // String | api version info
String merchant = "merchant_example"; // String | Unique merchant code
String serviceid = "serviceid_example"; // String | Unique service Identifier
String serviceNumber = "serviceNumber_example"; // String | service number with merchant (e.g. policy number with an insurance company or tax number for a governmental institution)
try {
    List<Subscription> result = apiInstance.subscriptionGet(xApiVersion, merchant, serviceid, serviceNumber);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling CollectionApi#subscriptionGet");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **xApiVersion** | **String**| api version info | [default to 3.0.0]
 **merchant** | **String**| Unique merchant code |
 **serviceid** | **String**| Unique service Identifier |
 **serviceNumber** | **String**| service number with merchant (e.g. policy number with an insurance company or tax number for a governmental institution) |

### Return type

[**List&lt;Subscription&gt;**](Subscription.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="topupGet"></a>
# **topupGet**
> List&lt;Topup&gt; topupGet(xApiVersion, serviceid)

Retrieve available topup packages

This service provides a list of all available topup packages. DEPRECTATED: Some providers will return a digital code for manual redeeming. This code will be provided in the response object of a successful collection. This functionality has been moved into the /voucher endpoint and will be removed in the next version of this API

### Example
```java
// Import classes:
//import io.swagger.client.ApiException;
//import io.swagger.client.api.CollectionApi;


CollectionApi apiInstance = new CollectionApi();
String xApiVersion = "3.0.0"; // String | api version info
Integer serviceid = 56; // Integer | Filter topups to only the selected service
try {
    List<Topup> result = apiInstance.topupGet(xApiVersion, serviceid);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling CollectionApi#topupGet");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **xApiVersion** | **String**| api version info | [default to 3.0.0]
 **serviceid** | **Integer**| Filter topups to only the selected service | [optional]

### Return type

[**List&lt;Topup&gt;**](Topup.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="verifyGet"></a>
# **verifyGet**
> Boolean verifyGet(xApiVersion, merchant, serviceid, serviceNumber)

Verify service number

For services that support verification (indicated by the \&quot;isVerifiable\&quot; flag) the service number can be provided to this endpoint. The system will verify wether or not the service number is valid with the selected service. 

### Example
```java
// Import classes:
//import io.swagger.client.ApiException;
//import io.swagger.client.api.CollectionApi;


CollectionApi apiInstance = new CollectionApi();
String xApiVersion = "3.0.0"; // String | api version info
String merchant = "merchant_example"; // String | Unique  merchant code
Integer serviceid = 56; // Integer | Unique  service Identifier
String serviceNumber = "serviceNumber_example"; // String | Service number with merchant (e.g. meter number in bills from a utility provider) for which to perform the bill payment
try {
    Boolean result = apiInstance.verifyGet(xApiVersion, merchant, serviceid, serviceNumber);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling CollectionApi#verifyGet");
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

**Boolean**

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="voucherGet"></a>
# **voucherGet**
> List&lt;Product&gt; voucherGet(xApiVersion, serviceid)

Retrieve list of available vouchers to purchase

This service provides a list of all available vouchers for all services. A purchase of a voucher will return a digital code for manual redeeming. This code will be provided in the response object of a successful collection.

### Example
```java
// Import classes:
//import io.swagger.client.ApiException;
//import io.swagger.client.api.CollectionApi;


CollectionApi apiInstance = new CollectionApi();
String xApiVersion = "3.0.0"; // String | api version info
Integer serviceid = 56; // Integer | Filter products to only the selected service
try {
    List<Product> result = apiInstance.voucherGet(xApiVersion, serviceid);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling CollectionApi#voucherGet");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **xApiVersion** | **String**| api version info | [default to 3.0.0]
 **serviceid** | **Integer**| Filter products to only the selected service | [optional]

### Return type

[**List&lt;Product&gt;**](Product.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json
