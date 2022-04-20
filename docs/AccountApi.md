# AccountApi

All URIs are relative to */v2*

Method | HTTP request | Description
------------- | ------------- | -------------
[**accountGet**](AccountApi.md#accountGet) | **GET** /account | Retrieve account information and remaining account balance

<a name="accountGet"></a>
# **accountGet**
> Account accountGet(xApiVersion)

Retrieve account information and remaining account balance

This endpoint returns the user’s account information – most notably the current balance of the user. Calling this service before and after **each** collection in order to retrieve the current limits and/or balance is **highly discouraged**. The recommended approach is as follows:   1. Only a successful payment collection transaction will affect the account balance. The corresponding endpoint will also return the current account balance after the collection in its result payload.   2. For unsuccessful payment transactions, the account balance will not be affected. The error message returns a verbose message as to why the transaction failed. There is no need to recheck the account after each error. 

### Example
```java
// Import classes:
//import org.maviance.s3pjavaclient.ApiException;
//import org.maviance.s3pjavaclient.api.AccountApi;


AccountApi apiInstance = new AccountApi();
String xApiVersion = "3.0.0"; // String | api version info
try {
    Account result = apiInstance.accountGet(xApiVersion);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling AccountApi#accountGet");
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

 - **Content-Type**: Not defined
 - **Accept**: application/json

