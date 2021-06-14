# HistoryApi

All URIs are relative to *https://localhost/v2*

Method | HTTP request | Description
------------- | ------------- | -------------
[**historystdGet**](HistoryApi.md#historystdGet) | **GET** /historystd | Retrieve list of historic payment collection.


<a name="historystdGet"></a>
# **historystdGet**
> List&lt;Historystd&gt; historystdGet(xApiVersion, ptn, trid, timestampFrom, timestampTo)

Retrieve list of historic payment collection.

This endpoint allows the search for historic payment collection records by time, payment transaction number (PTN) or a custom transaction reference (TRID) that was provided during payment collection. At least one of these parameters has to be provided!

### Example
```java
// Import classes:
//import io.swagger.client.ApiException;
//import io.swagger.client.api.HistoryApi;


HistoryApi apiInstance = new HistoryApi();
String xApiVersion = "3.0.0"; // String | api version info
String ptn = "ptn_example"; // String | Unique payment collection transaction number
String trid = "trid_example"; // String | custom external transaction reference provided during payment collection
LocalDate timestampFrom = LocalDate.now(); // LocalDate | Start date of transactions in result set (ISO 8601)
LocalDate timestampTo = LocalDate.now(); // LocalDate | End date of transactions in result set (ISO 8601)
try {
    List<Historystd> result = apiInstance.historystdGet(xApiVersion, ptn, trid, timestampFrom, timestampTo);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling HistoryApi#historystdGet");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **xApiVersion** | **String**| api version info | [default to 3.0.0]
 **ptn** | **String**| Unique payment collection transaction number | [optional]
 **trid** | **String**| custom external transaction reference provided during payment collection | [optional]
 **timestampFrom** | **LocalDate**| Start date of transactions in result set (ISO 8601) | [optional]
 **timestampTo** | **LocalDate**| End date of transactions in result set (ISO 8601) | [optional]

### Return type

[**List&lt;Historystd&gt;**](Historystd.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json
