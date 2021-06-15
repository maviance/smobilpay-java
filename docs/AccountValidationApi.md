# AccountValidationApi

All URIs are relative to *https://localhost/v2*

Method | HTTP request | Description
------------- | ------------- | -------------
[**verifyGet**](AccountValidationApi.md#verifyGet) | **GET** /verify | Verify service number


<a name="verifyGet"></a>
# **verifyGet**
> Boolean verifyGet(xApiVersion, merchant, serviceid, serviceNumber)

Verify service number

For services that support verification (indicated by the \&quot;isVerifiable\&quot; flag) the service number can be provided to this endpoint. The system will verify wether or not the service number is valid with the selected service. 

### Example
```java
// Import classes:
//import org.maviance.s3pjavaclient.ApiException;
//import org.maviance.s3pjavaclient.api.AccountValidationApi;


AccountValidationApi apiInstance = new AccountValidationApi();
String xApiVersion = "3.0.0"; // String | api version info
String merchant = "merchant_example"; // String | Unique  merchant code
Integer serviceid = 56; // Integer | Unique  service Identifier
String serviceNumber = "serviceNumber_example"; // String | Service number with merchant (e.g. meter number in bills from a utility provider) for which to perform the bill payment
try {
    Boolean result = apiInstance.verifyGet(xApiVersion, merchant, serviceid, serviceNumber);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling AccountValidationApi#verifyGet");
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

