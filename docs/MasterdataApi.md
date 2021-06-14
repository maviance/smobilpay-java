# MasterdataApi

All URIs are relative to *https://localhost/v2*

Method | HTTP request | Description
------------- | ------------- | -------------
[**accountGet**](MasterdataApi.md#accountGet) | **GET** /account | Retrieve account information and remaining account balance
[**merchantGet**](MasterdataApi.md#merchantGet) | **GET** /merchant | Retrieve list of merchants supported by the system.
[**serviceGet**](MasterdataApi.md#serviceGet) | **GET** /service | Retrieve list of services supported by the system.
[**serviceIdGet**](MasterdataApi.md#serviceIdGet) | **GET** /service/{id} | Retrieve single service


<a name="accountGet"></a>
# **accountGet**
> Account accountGet(xApiVersion)

Retrieve account information and remaining account balance

This endpoint returns the user’s account information – most notably the current balance of the user. Calling this service before and after **each** collection in order to retrieve the current limits and/or balance is **highly discouraged**. The recommended approach is as follows:   1. Only a successful payment collection transaction will affect the account balance. The corresponding endpoint will also return the current account balance after the collection in its result payload.   2. For unsuccessful payment transactions, the account balance will not be affected. The error message returns a verbose message as to why the transaction failed. There is no need to recheck the account after each error. 

### Example
```java
// Import classes:
//import io.swagger.client.ApiException;
//import io.swagger.client.api.MasterdataApi;


MasterdataApi apiInstance = new MasterdataApi();
String xApiVersion = "3.0.0"; // String | api version info
try {
    Account result = apiInstance.accountGet(xApiVersion);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling MasterdataApi#accountGet");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **xApiVersion** | **String**| api version info | [default to 3.0.0]

### Return type

[**Account**](Account.md)

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
//import io.swagger.client.ApiException;
//import io.swagger.client.api.MasterdataApi;


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

<a name="serviceGet"></a>
# **serviceGet**
> List&lt;Service&gt; serviceGet(xApiVersion)

Retrieve list of services supported by the system.

This service endpoint provides information about the services supported by . Each service has its own set of required input parameters which need to be provided during the collection request - starting with the prefix “isReq”. It is recommended that the application UI is configured based on the response values provided here. The service response will also specify the type of the service and thus detail how the related payment items can be retrieved and collected. 

### Example
```java
// Import classes:
//import io.swagger.client.ApiException;
//import io.swagger.client.api.MasterdataApi;


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
//import io.swagger.client.ApiException;
//import io.swagger.client.api.MasterdataApi;


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
