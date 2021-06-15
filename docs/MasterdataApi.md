# MasterdataApi

All URIs are relative to *https://localhost/v2*

Method | HTTP request | Description
------------- | ------------- | -------------
[**cashinGet**](MasterdataApi.md#cashinGet) | **GET** /cashin | Retrieve available cashin packages
[**cashoutGet**](MasterdataApi.md#cashoutGet) | **GET** /cashout | Retrieves available cashout packages
[**merchantGet**](MasterdataApi.md#merchantGet) | **GET** /merchant | Retrieve list of merchants supported by the system.
[**productGet**](MasterdataApi.md#productGet) | **GET** /product | Retrieve list of available products
[**serviceGet**](MasterdataApi.md#serviceGet) | **GET** /service | Retrieve list of services supported by the system.
[**serviceIdGet**](MasterdataApi.md#serviceIdGet) | **GET** /service/{id} | Retrieve single service
[**topupGet**](MasterdataApi.md#topupGet) | **GET** /topup | Retrieve available topup packages
[**voucherGet**](MasterdataApi.md#voucherGet) | **GET** /voucher | Retrieve list of available vouchers to purchase


<a name="cashinGet"></a>
# **cashinGet**
> List&lt;Cashin&gt; cashinGet(xApiVersion, serviceid)

Retrieve available cashin packages

This service provides available cashin packages to be made to the system.

### Example
```java
// Import classes:
//import org.maviance.s3pjavaclient.ApiException;
//import org.maviance.s3pjavaclient.api.MasterdataApi;


MasterdataApi apiInstance = new MasterdataApi();
String xApiVersion = "3.0.0"; // String | api version info
Integer serviceid = 56; // Integer | Filter cashin packages for only the selected service
try {
    List<Cashin> result = apiInstance.cashinGet(xApiVersion, serviceid);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling MasterdataApi#cashinGet");
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
//import org.maviance.s3pjavaclient.ApiException;
//import org.maviance.s3pjavaclient.api.MasterdataApi;


MasterdataApi apiInstance = new MasterdataApi();
String xApiVersion = "3.0.0"; // String | api version info
Integer serviceid = 56; // Integer | Filter cashout packages for only the selected service
try {
    List<Cashout> result = apiInstance.cashoutGet(xApiVersion, serviceid);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling MasterdataApi#cashoutGet");
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

<a name="merchantGet"></a>
# **merchantGet**
> List&lt;Merchant&gt; merchantGet(xApiVersion)

Retrieve list of merchants supported by the system.

Provides merchants supported by the system. Every service is assigned to a merchant.

### Example
```java
// Import classes:
//import org.maviance.s3pjavaclient.ApiException;
//import org.maviance.s3pjavaclient.api.MasterdataApi;


MasterdataApi apiInstance = new MasterdataApi();
String xApiVersion = "3.0.0"; // String | api version info
try {
    List<Merchant> result = apiInstance.merchantGet(xApiVersion);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling MasterdataApi#merchantGet");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **xApiVersion** | **String**| api version info | [default to 3.0.0]

### Return type

[**List&lt;Merchant&gt;**](Merchant.md)

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
//import org.maviance.s3pjavaclient.ApiException;
//import org.maviance.s3pjavaclient.api.MasterdataApi;


MasterdataApi apiInstance = new MasterdataApi();
String xApiVersion = "3.0.0"; // String | api version info
Integer serviceid = 56; // Integer | Filter products to only the selected service
try {
    List<Product> result = apiInstance.productGet(xApiVersion, serviceid);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling MasterdataApi#productGet");
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

<a name="serviceGet"></a>
# **serviceGet**
> List&lt;Service&gt; serviceGet(xApiVersion)

Retrieve list of services supported by the system.

This service endpoint provides information about the services supported by . Each service has its own set of required input parameters which need to be provided during the collection request - starting with the prefix “isReq”. It is recommended that the application UI is configured based on the response values provided here. The service response will also specify the type of the service and thus detail how the related payment items can be retrieved and collected. 

### Example
```java
// Import classes:
//import org.maviance.s3pjavaclient.ApiException;
//import org.maviance.s3pjavaclient.api.MasterdataApi;


MasterdataApi apiInstance = new MasterdataApi();
String xApiVersion = "3.0.0"; // String | api version info
try {
    List<Service> result = apiInstance.serviceGet(xApiVersion);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling MasterdataApi#serviceGet");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **xApiVersion** | **String**| api version info | [default to 3.0.0]

### Return type

[**List&lt;Service&gt;**](Service.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="serviceIdGet"></a>
# **serviceIdGet**
> Service serviceIdGet(xApiVersion, id)

Retrieve single service

This service endpoint provides information about the selected service. Each service has its own set of required input parameters which need to be provided during the collection request - starting with the prefix “isReq”. It is recommended that the application UI is configured based on the response values provided here. The service response will also specify the type of the service and thus detail how the related payment items can be retrieved and collected.

### Example
```java
// Import classes:
//import org.maviance.s3pjavaclient.ApiException;
//import org.maviance.s3pjavaclient.api.MasterdataApi;


MasterdataApi apiInstance = new MasterdataApi();
String xApiVersion = "3.0.0"; // String | api version info
Integer id = 56; // Integer | Unique  service Identifier.
try {
    Service result = apiInstance.serviceIdGet(xApiVersion, id);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling MasterdataApi#serviceIdGet");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **xApiVersion** | **String**| api version info | [default to 3.0.0]
 **id** | **Integer**| Unique  service Identifier. |

### Return type

[**Service**](Service.md)

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
//import org.maviance.s3pjavaclient.ApiException;
//import org.maviance.s3pjavaclient.api.MasterdataApi;


MasterdataApi apiInstance = new MasterdataApi();
String xApiVersion = "3.0.0"; // String | api version info
Integer serviceid = 56; // Integer | Filter topups to only the selected service
try {
    List<Topup> result = apiInstance.topupGet(xApiVersion, serviceid);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling MasterdataApi#topupGet");
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

<a name="voucherGet"></a>
# **voucherGet**
> List&lt;Product&gt; voucherGet(xApiVersion, serviceid)

Retrieve list of available vouchers to purchase

This service provides a list of all available vouchers for all services. A purchase of a voucher will return a digital code for manual redeeming. This code will be provided in the response object of a successful collection.

### Example
```java
// Import classes:
//import org.maviance.s3pjavaclient.ApiException;
//import org.maviance.s3pjavaclient.api.MasterdataApi;


MasterdataApi apiInstance = new MasterdataApi();
String xApiVersion = "3.0.0"; // String | api version info
Integer serviceid = 56; // Integer | Filter products to only the selected service
try {
    List<Product> result = apiInstance.voucherGet(xApiVersion, serviceid);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling MasterdataApi#voucherGet");
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

