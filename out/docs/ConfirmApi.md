# ConfirmApi

All URIs are relative to *https://localhost/v2*

Method | HTTP request | Description
------------- | ------------- | -------------
[**collectstdPost**](ConfirmApi.md#collectstdPost) | **POST** /collectstd | Execute payment collection


<a name="collectstdPost"></a>
# **collectstdPost**
> CollectionResponse collectstdPost(xApiVersion, body)

Execute payment collection

This endpoint executes a payment collection. Any collection will reduce the agent balance by service amount plus the service fee. Each collection must include a reference to corresponding quote and payment authorization token. Whether or not fields are mandatory depends on the service configuration

### Example
```java
// Import classes:
//import org.maviance.s3pjavaclient.ApiException;
//import org.maviance.s3pjavaclient.api.ConfirmApi;


ConfirmApi apiInstance = new ConfirmApi();
String xApiVersion = "3.0.0"; // String | api version info
CollectionRequest body = new CollectionRequest(); // CollectionRequest | Collection Request
try {
    CollectionResponse result = apiInstance.collectstdPost(xApiVersion, body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling ConfirmApi#collectstdPost");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **xApiVersion** | **String**| api version info | [default to 3.0.0]
 **body** | [**CollectionRequest**](CollectionRequest.md)| Collection Request | [optional]

### Return type

[**CollectionResponse**](CollectionResponse.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

