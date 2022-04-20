# VerifyApi

All URIs are relative to */v2*

Method | HTTP request | Description
------------- | ------------- | -------------
[**historystdGet**](VerifyApi.md#historystdGet) | **GET** /historystd | Retrieve list of historic payment collection.
[**verifytxGet**](VerifyApi.md#verifytxGet) | **GET** /verifytx | Get the current payment collection status

<a name="historystdGet"></a>
# **historystdGet**
> List&lt;PaymentStatus&gt; historystdGet(xApiVersion, timestampFrom, timestampTo)

Retrieve list of historic payment collection.

This endpoint allows the search for historic payment collection records by time that was provided during payment collection. Both parameters have to be provided!

### Example
```java
// Import classes:
//import org.maviance.s3pjavaclient.ApiException;
//import org.maviance.s3pjavaclient.api.VerifyApi;


VerifyApi apiInstance = new VerifyApi();
String xApiVersion = "3.0.0"; // String | api version info
LocalDate timestampFrom = new LocalDate(); // LocalDate | Start date of transactions in result set (ISO 8601)
LocalDate timestampTo = new LocalDate(); // LocalDate | End date of transactions in result set (ISO 8601)
try {
    List<PaymentStatus> result = apiInstance.historystdGet(xApiVersion, timestampFrom, timestampTo);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling VerifyApi#historystdGet");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **xApiVersion** | **String**| api version info | [default to 3.0.0]
 **timestampFrom** | **LocalDate**| Start date of transactions in result set (ISO 8601) | [optional]
 **timestampTo** | **LocalDate**| End date of transactions in result set (ISO 8601) | [optional]

### Return type

[**List&lt;PaymentStatus&gt;**](PaymentStatus.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="verifytxGet"></a>
# **verifytxGet**
> List&lt;PaymentStatus&gt; verifytxGet(xApiVersion, ptn, trid)

Get the current payment collection status

Call this endpoint to retrieve the current payment status by either transaction number (PTN) or the custom transaction reference (TRID) that was provided during payment collection. At least one of these parameters has to be provided!

### Example
```java
// Import classes:
//import org.maviance.s3pjavaclient.ApiException;
//import org.maviance.s3pjavaclient.api.VerifyApi;


VerifyApi apiInstance = new VerifyApi();
String xApiVersion = "3.0.0"; // String | api version info
String ptn = "ptn_example"; // String | Unique payment collection transaction number
String trid = "trid_example"; // String | custom external transaction reference provided during payment collection
try {
    List<PaymentStatus> result = apiInstance.verifytxGet(xApiVersion, ptn, trid);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling VerifyApi#verifytxGet");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **xApiVersion** | **String**| api version info | [default to 3.0.0]
 **ptn** | **String**| Unique payment collection transaction number | [optional]
 **trid** | **String**| custom external transaction reference provided during payment collection | [optional]

### Return type

[**List&lt;PaymentStatus&gt;**](PaymentStatus.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

