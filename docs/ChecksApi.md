# ChecksApi

All URIs are relative to *https://localhost/v2*

Method | HTTP request | Description
------------- | ------------- | -------------
[**pingGet**](ChecksApi.md#pingGet) | **GET** /ping | Run a ping to check on the availability of the api


<a id=pinGet name=pinGet></a>
#pingGet
> Ping pingGet(xApiVersion)

Run a ping to check on the availability of the api

This endpoint simply checks the existence and validity of the request on the server by returning a valid response object or an error message. Its primary purpose is to provide a feedback on whether or not the API is available. It also provides the current server time and timezone

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