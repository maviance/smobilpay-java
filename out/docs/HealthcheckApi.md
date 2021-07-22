# HealthcheckApi

All URIs are relative to *https://localhost/v2*

Method | HTTP request | Description
------------- | ------------- | -------------
[**pingGet**](HealthcheckApi.md#pingGet) | **GET** /ping | Check on the availability of the api


<a name="pingGet"></a>
# **pingGet**
> Ping pingGet(xApiVersion)

Check on the availability of the api

This endpoint simply checks the existence and validity of the request on the server by returning a valid response object or an error message. Its primary purpose is to provide a feedback on whether or not the API is available. It also provides the current server time and timezone

### Example
```java
// Import classes:
//import org.maviance.s3pjavaclient.ApiException;
//import org.maviance.s3pjavaclient.api.HealthcheckApi;


HealthcheckApi apiInstance = new HealthcheckApi();
String xApiVersion = "3.0.0"; // String | api version info
try {
    Ping result = apiInstance.pingGet(xApiVersion);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling HealthcheckApi#pingGet");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **xApiVersion** | **String**| api version info | [default to 3.0.0]

### Return type

[**Ping**](Ping.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

