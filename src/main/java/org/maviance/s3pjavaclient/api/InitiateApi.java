/*
 * Smobilpay S3P API
 * Smobilpay Third Party API FOR PAYMENT COLLECTIONS
 *
 * OpenAPI spec version: 3.0.2
 * 
 *
 * NOTE: This class is auto generated by the swagger code generator program.
 * https://github.com/swagger-api/swagger-codegen.git
 * Do not edit the class manually.
 */


package org.maviance.s3pjavaclient.api;

import org.maviance.s3pjavaclient.ApiCallback;
import org.maviance.s3pjavaclient.ApiClient;
import org.maviance.s3pjavaclient.ApiException;
import org.maviance.s3pjavaclient.ApiResponse;
import org.maviance.s3pjavaclient.Configuration;
import org.maviance.s3pjavaclient.Pair;
import org.maviance.s3pjavaclient.ProgressRequestBody;
import org.maviance.s3pjavaclient.ProgressResponseBody;

import com.google.gson.reflect.TypeToken;

import java.io.IOException;


import org.maviance.s3pjavaclient.model.Bill;
import org.maviance.s3pjavaclient.model.Error;
import org.maviance.s3pjavaclient.model.Quote;
import org.maviance.s3pjavaclient.model.QuoteRequest;
import org.maviance.s3pjavaclient.model.Subscription;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InitiateApi {
    private ApiClient apiClient;

    public InitiateApi() {
        this(Configuration.getDefaultApiClient());
    }

    public InitiateApi(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public ApiClient getApiClient() {
        return apiClient;
    }

    public void setApiClient(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    /**
     * Build call for billGet
     * @param xApiVersion api version info (required)
     * @param merchant Unique  merchant code (required)
     * @param serviceid Unique  service Identifier (required)
     * @param serviceNumber Service number with merchant (e.g. meter number in bills from a utility provider) for which to perform the bill payment (required)
     * @param progressListener Progress listener
     * @param progressRequestListener Progress request listener
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     */
    public com.squareup.okhttp.Call billGetCall(String xApiVersion, String merchant, Integer serviceid, String serviceNumber, final ProgressResponseBody.ProgressListener progressListener, final ProgressRequestBody.ProgressRequestListener progressRequestListener) throws ApiException {
        Object localVarPostBody = null;

        // create path and map variables
        String localVarPath = "/bill";

        List<Pair> localVarQueryParams = new ArrayList<Pair>();
        List<Pair> localVarCollectionQueryParams = new ArrayList<Pair>();
        if (merchant != null)
        localVarQueryParams.addAll(apiClient.parameterToPair("merchant", merchant));
        if (serviceid != null)
        localVarQueryParams.addAll(apiClient.parameterToPair("serviceid", serviceid));
        if (serviceNumber != null)
        localVarQueryParams.addAll(apiClient.parameterToPair("serviceNumber", serviceNumber));

        Map<String, String> localVarHeaderParams = new HashMap<String, String>();
        if (xApiVersion != null)
        localVarHeaderParams.put("x-api-version", apiClient.parameterToString(xApiVersion));

        Map<String, Object> localVarFormParams = new HashMap<String, Object>();

        final String[] localVarAccepts = {
            "application/json"
        };
        final String localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        if (localVarAccept != null) localVarHeaderParams.put("Accept", localVarAccept);

        final String[] localVarContentTypes = {
            "application/json"
        };
        final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);
        localVarHeaderParams.put("Content-Type", localVarContentType);

        if(progressListener != null) {
            apiClient.getHttpClient().networkInterceptors().add(new com.squareup.okhttp.Interceptor() {
                @Override
                public com.squareup.okhttp.Response intercept(com.squareup.okhttp.Interceptor.Chain chain) throws IOException {
                    com.squareup.okhttp.Response originalResponse = chain.proceed(chain.request());
                    return originalResponse.newBuilder()
                    .body(new ProgressResponseBody(originalResponse.body(), progressListener))
                    .build();
                }
            });
        }

        String[] localVarAuthNames = new String[] {  };
        return apiClient.buildCall(localVarPath, "GET", localVarQueryParams, localVarCollectionQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAuthNames, progressRequestListener);
    }

    @SuppressWarnings("rawtypes")
    private com.squareup.okhttp.Call billGetValidateBeforeCall(String xApiVersion, String merchant, Integer serviceid, String serviceNumber, final ProgressResponseBody.ProgressListener progressListener, final ProgressRequestBody.ProgressRequestListener progressRequestListener) throws ApiException {
        
        // verify the required parameter 'xApiVersion' is set
        if (xApiVersion == null) {
            throw new ApiException("Missing the required parameter 'xApiVersion' when calling billGet(Async)");
        }
        
        // verify the required parameter 'merchant' is set
        if (merchant == null) {
            throw new ApiException("Missing the required parameter 'merchant' when calling billGet(Async)");
        }
        
        // verify the required parameter 'serviceid' is set
        if (serviceid == null) {
            throw new ApiException("Missing the required parameter 'serviceid' when calling billGet(Async)");
        }
        
        // verify the required parameter 'serviceNumber' is set
        if (serviceNumber == null) {
            throw new ApiException("Missing the required parameter 'serviceNumber' when calling billGet(Async)");
        }
        

        com.squareup.okhttp.Call call = billGetCall(xApiVersion, merchant, serviceid, serviceNumber, progressListener, progressRequestListener);
        return call;

    }

    /**
     * Get bill payment handler
     * A request to this endpoint returns bill payment handler records for a service by a service number and retrieves its details if available. Bill payments come in 2 flavors – which are determined by the related service’s type: 1.  **SEARCHABLE_BILL** – When calling the endpoint for searchable bills, the result set will contain a list of all open bills for the selected service number. Each bill has its own Payment Item Identifier. 2.  **NON_SEARCHABLE_BILL** – When calling the endpoint for non-searchable bills, the result set will always contain a single bill item with a Payment Item ID to perform the collection for the provided service number. 
     * @param xApiVersion api version info (required)
     * @param merchant Unique  merchant code (required)
     * @param serviceid Unique  service Identifier (required)
     * @param serviceNumber Service number with merchant (e.g. meter number in bills from a utility provider) for which to perform the bill payment (required)
     * @return List&lt;Bill&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     */
    public List<Bill> billGet(String xApiVersion, String merchant, Integer serviceid, String serviceNumber) throws ApiException {
        ApiResponse<List<Bill>> resp = billGetWithHttpInfo(xApiVersion, merchant, serviceid, serviceNumber);
        return resp.getData();
    }

    /**
     * Get bill payment handler
     * A request to this endpoint returns bill payment handler records for a service by a service number and retrieves its details if available. Bill payments come in 2 flavors – which are determined by the related service’s type: 1.  **SEARCHABLE_BILL** – When calling the endpoint for searchable bills, the result set will contain a list of all open bills for the selected service number. Each bill has its own Payment Item Identifier. 2.  **NON_SEARCHABLE_BILL** – When calling the endpoint for non-searchable bills, the result set will always contain a single bill item with a Payment Item ID to perform the collection for the provided service number. 
     * @param xApiVersion api version info (required)
     * @param merchant Unique  merchant code (required)
     * @param serviceid Unique  service Identifier (required)
     * @param serviceNumber Service number with merchant (e.g. meter number in bills from a utility provider) for which to perform the bill payment (required)
     * @return ApiResponse&lt;List&lt;Bill&gt;&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     */
    public ApiResponse<List<Bill>> billGetWithHttpInfo(String xApiVersion, String merchant, Integer serviceid, String serviceNumber) throws ApiException {
        com.squareup.okhttp.Call call = billGetValidateBeforeCall(xApiVersion, merchant, serviceid, serviceNumber, null, null);
        Type localVarReturnType = new TypeToken<List<Bill>>(){}.getType();
        return apiClient.execute(call, localVarReturnType);
    }

    /**
     * Get bill payment handler (asynchronously)
     * A request to this endpoint returns bill payment handler records for a service by a service number and retrieves its details if available. Bill payments come in 2 flavors – which are determined by the related service’s type: 1.  **SEARCHABLE_BILL** – When calling the endpoint for searchable bills, the result set will contain a list of all open bills for the selected service number. Each bill has its own Payment Item Identifier. 2.  **NON_SEARCHABLE_BILL** – When calling the endpoint for non-searchable bills, the result set will always contain a single bill item with a Payment Item ID to perform the collection for the provided service number. 
     * @param xApiVersion api version info (required)
     * @param merchant Unique  merchant code (required)
     * @param serviceid Unique  service Identifier (required)
     * @param serviceNumber Service number with merchant (e.g. meter number in bills from a utility provider) for which to perform the bill payment (required)
     * @param callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     */
    public com.squareup.okhttp.Call billGetAsync(String xApiVersion, String merchant, Integer serviceid, String serviceNumber, final ApiCallback<List<Bill>> callback) throws ApiException {

        ProgressResponseBody.ProgressListener progressListener = null;
        ProgressRequestBody.ProgressRequestListener progressRequestListener = null;

        if (callback != null) {
            progressListener = new ProgressResponseBody.ProgressListener() {
                @Override
                public void update(long bytesRead, long contentLength, boolean done) {
                    callback.onDownloadProgress(bytesRead, contentLength, done);
                }
            };

            progressRequestListener = new ProgressRequestBody.ProgressRequestListener() {
                @Override
                public void onRequestProgress(long bytesWritten, long contentLength, boolean done) {
                    callback.onUploadProgress(bytesWritten, contentLength, done);
                }
            };
        }

        com.squareup.okhttp.Call call = billGetValidateBeforeCall(xApiVersion, merchant, serviceid, serviceNumber, progressListener, progressRequestListener);
        Type localVarReturnType = new TypeToken<List<Bill>>(){}.getType();
        apiClient.executeAsync(call, localVarReturnType, callback);
        return call;
    }
    /**
     * Build call for quotestdPost
     * @param xApiVersion api version info (required)
     * @param body Quote Request (optional)
     * @param progressListener Progress listener
     * @param progressRequestListener Progress request listener
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     */
    public com.squareup.okhttp.Call quotestdPostCall(String xApiVersion, QuoteRequest body, final ProgressResponseBody.ProgressListener progressListener, final ProgressRequestBody.ProgressRequestListener progressRequestListener) throws ApiException {
        Object localVarPostBody = body;

        // create path and map variables
        String localVarPath = "/quotestd";

        List<Pair> localVarQueryParams = new ArrayList<Pair>();
        List<Pair> localVarCollectionQueryParams = new ArrayList<Pair>();

        Map<String, String> localVarHeaderParams = new HashMap<String, String>();
        if (xApiVersion != null)
        localVarHeaderParams.put("x-api-version", apiClient.parameterToString(xApiVersion));

        Map<String, Object> localVarFormParams = new HashMap<String, Object>();

        final String[] localVarAccepts = {
            "application/json"
        };
        final String localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        if (localVarAccept != null) localVarHeaderParams.put("Accept", localVarAccept);

        final String[] localVarContentTypes = {
            "application/json"
        };
        final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);
        localVarHeaderParams.put("Content-Type", localVarContentType);

        if(progressListener != null) {
            apiClient.getHttpClient().networkInterceptors().add(new com.squareup.okhttp.Interceptor() {
                @Override
                public com.squareup.okhttp.Response intercept(com.squareup.okhttp.Interceptor.Chain chain) throws IOException {
                    com.squareup.okhttp.Response originalResponse = chain.proceed(chain.request());
                    return originalResponse.newBuilder()
                    .body(new ProgressResponseBody(originalResponse.body(), progressListener))
                    .build();
                }
            });
        }

        String[] localVarAuthNames = new String[] {  };
        return apiClient.buildCall(localVarPath, "POST", localVarQueryParams, localVarCollectionQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAuthNames, progressRequestListener);
    }

    @SuppressWarnings("rawtypes")
    private com.squareup.okhttp.Call quotestdPostValidateBeforeCall(String xApiVersion, QuoteRequest body, final ProgressResponseBody.ProgressListener progressListener, final ProgressRequestBody.ProgressRequestListener progressRequestListener) throws ApiException {
        
        // verify the required parameter 'xApiVersion' is set
        if (xApiVersion == null) {
            throw new ApiException("Missing the required parameter 'xApiVersion' when calling quotestdPost(Async)");
        }
        

        com.squareup.okhttp.Call call = quotestdPostCall(xApiVersion, body, progressListener, progressRequestListener);
        return call;

    }

    /**
     * Request quote with price details about the payment
     * Calling this web-service requests a quote from the system for the payment collection of the selected payment item and the specified payment amount in the system. The amount is to be chosen based on the services amountType, so can either be fixed or a custom entered value. The third parameter specifies the payment method that the customer has chosen in order to pay for the collection, as there may be additional charges depending on the selected method. A quote will only remain available for short time (a few minutes) and will expire. A quote will return the actual costs involved in collecting the payment. A quote always needs to be requested before making a collection.\&quot; 
     * @param xApiVersion api version info (required)
     * @param body Quote Request (optional)
     * @return Quote
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     */
    public Quote quotestdPost(String xApiVersion, QuoteRequest body) throws ApiException {
        ApiResponse<Quote> resp = quotestdPostWithHttpInfo(xApiVersion, body);
        return resp.getData();
    }

    /**
     * Request quote with price details about the payment
     * Calling this web-service requests a quote from the system for the payment collection of the selected payment item and the specified payment amount in the system. The amount is to be chosen based on the services amountType, so can either be fixed or a custom entered value. The third parameter specifies the payment method that the customer has chosen in order to pay for the collection, as there may be additional charges depending on the selected method. A quote will only remain available for short time (a few minutes) and will expire. A quote will return the actual costs involved in collecting the payment. A quote always needs to be requested before making a collection.\&quot; 
     * @param xApiVersion api version info (required)
     * @param body Quote Request (optional)
     * @return ApiResponse&lt;Quote&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     */
    public ApiResponse<Quote> quotestdPostWithHttpInfo(String xApiVersion, QuoteRequest body) throws ApiException {
        com.squareup.okhttp.Call call = quotestdPostValidateBeforeCall(xApiVersion, body, null, null);
        Type localVarReturnType = new TypeToken<Quote>(){}.getType();
        return apiClient.execute(call, localVarReturnType);
    }

    /**
     * Request quote with price details about the payment (asynchronously)
     * Calling this web-service requests a quote from the system for the payment collection of the selected payment item and the specified payment amount in the system. The amount is to be chosen based on the services amountType, so can either be fixed or a custom entered value. The third parameter specifies the payment method that the customer has chosen in order to pay for the collection, as there may be additional charges depending on the selected method. A quote will only remain available for short time (a few minutes) and will expire. A quote will return the actual costs involved in collecting the payment. A quote always needs to be requested before making a collection.\&quot; 
     * @param xApiVersion api version info (required)
     * @param body Quote Request (optional)
     * @param callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     */
    public com.squareup.okhttp.Call quotestdPostAsync(String xApiVersion, QuoteRequest body, final ApiCallback<Quote> callback) throws ApiException {

        ProgressResponseBody.ProgressListener progressListener = null;
        ProgressRequestBody.ProgressRequestListener progressRequestListener = null;

        if (callback != null) {
            progressListener = new ProgressResponseBody.ProgressListener() {
                @Override
                public void update(long bytesRead, long contentLength, boolean done) {
                    callback.onDownloadProgress(bytesRead, contentLength, done);
                }
            };

            progressRequestListener = new ProgressRequestBody.ProgressRequestListener() {
                @Override
                public void onRequestProgress(long bytesWritten, long contentLength, boolean done) {
                    callback.onUploadProgress(bytesWritten, contentLength, done);
                }
            };
        }

        com.squareup.okhttp.Call call = quotestdPostValidateBeforeCall(xApiVersion, body, progressListener, progressRequestListener);
        Type localVarReturnType = new TypeToken<Quote>(){}.getType();
        apiClient.executeAsync(call, localVarReturnType, callback);
        return call;
    }
    /**
     * Build call for subscriptionGet
     * @param xApiVersion api version info (required)
     * @param merchant Unique merchant code (required)
     * @param serviceid Unique service Identifier (required)
     * @param serviceNumber service number with merchant (e.g. policy number with an insurance company or tax number for a governmental institution) (optional)
     * @param customerNumber customer number with merchant (e.g. customer number with an insurance company or account number for a governmental institution) (optional)
     * @param progressListener Progress listener
     * @param progressRequestListener Progress request listener
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     */
    public com.squareup.okhttp.Call subscriptionGetCall(String xApiVersion, String merchant, String serviceid, String serviceNumber, String customerNumber, final ProgressResponseBody.ProgressListener progressListener, final ProgressRequestBody.ProgressRequestListener progressRequestListener) throws ApiException {
        Object localVarPostBody = null;

        // create path and map variables
        String localVarPath = "/subscription";

        List<Pair> localVarQueryParams = new ArrayList<Pair>();
        List<Pair> localVarCollectionQueryParams = new ArrayList<Pair>();
        if (merchant != null)
        localVarQueryParams.addAll(apiClient.parameterToPair("merchant", merchant));
        if (serviceid != null)
        localVarQueryParams.addAll(apiClient.parameterToPair("serviceid", serviceid));
        if (serviceNumber != null)
        localVarQueryParams.addAll(apiClient.parameterToPair("serviceNumber", serviceNumber));
        if (customerNumber != null)
        localVarQueryParams.addAll(apiClient.parameterToPair("customerNumber", customerNumber));

        Map<String, String> localVarHeaderParams = new HashMap<String, String>();
        if (xApiVersion != null)
        localVarHeaderParams.put("x-api-version", apiClient.parameterToString(xApiVersion));

        Map<String, Object> localVarFormParams = new HashMap<String, Object>();

        final String[] localVarAccepts = {
            "application/json"
        };
        final String localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        if (localVarAccept != null) localVarHeaderParams.put("Accept", localVarAccept);

        final String[] localVarContentTypes = {
            "application/json"
        };
        final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);
        localVarHeaderParams.put("Content-Type", localVarContentType);

        if(progressListener != null) {
            apiClient.getHttpClient().networkInterceptors().add(new com.squareup.okhttp.Interceptor() {
                @Override
                public com.squareup.okhttp.Response intercept(com.squareup.okhttp.Interceptor.Chain chain) throws IOException {
                    com.squareup.okhttp.Response originalResponse = chain.proceed(chain.request());
                    return originalResponse.newBuilder()
                    .body(new ProgressResponseBody(originalResponse.body(), progressListener))
                    .build();
                }
            });
        }

        String[] localVarAuthNames = new String[] {  };
        return apiClient.buildCall(localVarPath, "GET", localVarQueryParams, localVarCollectionQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAuthNames, progressRequestListener);
    }

    @SuppressWarnings("rawtypes")
    private com.squareup.okhttp.Call subscriptionGetValidateBeforeCall(String xApiVersion, String merchant, String serviceid, String serviceNumber, String customerNumber, final ProgressResponseBody.ProgressListener progressListener, final ProgressRequestBody.ProgressRequestListener progressRequestListener) throws ApiException {
        
        // verify the required parameter 'xApiVersion' is set
        if (xApiVersion == null) {
            throw new ApiException("Missing the required parameter 'xApiVersion' when calling subscriptionGet(Async)");
        }
        
        // verify the required parameter 'merchant' is set
        if (merchant == null) {
            throw new ApiException("Missing the required parameter 'merchant' when calling subscriptionGet(Async)");
        }
        
        // verify the required parameter 'serviceid' is set
        if (serviceid == null) {
            throw new ApiException("Missing the required parameter 'serviceid' when calling subscriptionGet(Async)");
        }
        

        com.squareup.okhttp.Call call = subscriptionGetCall(xApiVersion, merchant, serviceid, serviceNumber, customerNumber, progressListener, progressRequestListener);
        return call;

    }

    /**
     * Get subscription payment handler
     * A request to this endpoint looks up a subscription record for a service by either service number or customer number and retrieves its details if available. When calling the endpoint the result set will contain a list of all available subscriptions found under the provided search criteria. Each subscription has its own Payment Item Identifier. 
     * @param xApiVersion api version info (required)
     * @param merchant Unique merchant code (required)
     * @param serviceid Unique service Identifier (required)
     * @param serviceNumber service number with merchant (e.g. policy number with an insurance company or tax number for a governmental institution) (optional)
     * @param customerNumber customer number with merchant (e.g. customer number with an insurance company or account number for a governmental institution) (optional)
     * @return List&lt;Subscription&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     */
    public List<Subscription> subscriptionGet(String xApiVersion, String merchant, String serviceid, String serviceNumber, String customerNumber) throws ApiException {
        ApiResponse<List<Subscription>> resp = subscriptionGetWithHttpInfo(xApiVersion, merchant, serviceid, serviceNumber, customerNumber);
        return resp.getData();
    }

    /**
     * Get subscription payment handler
     * A request to this endpoint looks up a subscription record for a service by either service number or customer number and retrieves its details if available. When calling the endpoint the result set will contain a list of all available subscriptions found under the provided search criteria. Each subscription has its own Payment Item Identifier. 
     * @param xApiVersion api version info (required)
     * @param merchant Unique merchant code (required)
     * @param serviceid Unique service Identifier (required)
     * @param serviceNumber service number with merchant (e.g. policy number with an insurance company or tax number for a governmental institution) (optional)
     * @param customerNumber customer number with merchant (e.g. customer number with an insurance company or account number for a governmental institution) (optional)
     * @return ApiResponse&lt;List&lt;Subscription&gt;&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     */
    public ApiResponse<List<Subscription>> subscriptionGetWithHttpInfo(String xApiVersion, String merchant, String serviceid, String serviceNumber, String customerNumber) throws ApiException {
        com.squareup.okhttp.Call call = subscriptionGetValidateBeforeCall(xApiVersion, merchant, serviceid, serviceNumber, customerNumber, null, null);
        Type localVarReturnType = new TypeToken<List<Subscription>>(){}.getType();
        return apiClient.execute(call, localVarReturnType);
    }

    /**
     * Get subscription payment handler (asynchronously)
     * A request to this endpoint looks up a subscription record for a service by either service number or customer number and retrieves its details if available. When calling the endpoint the result set will contain a list of all available subscriptions found under the provided search criteria. Each subscription has its own Payment Item Identifier. 
     * @param xApiVersion api version info (required)
     * @param merchant Unique merchant code (required)
     * @param serviceid Unique service Identifier (required)
     * @param serviceNumber service number with merchant (e.g. policy number with an insurance company or tax number for a governmental institution) (optional)
     * @param customerNumber customer number with merchant (e.g. customer number with an insurance company or account number for a governmental institution) (optional)
     * @param callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     */
    public com.squareup.okhttp.Call subscriptionGetAsync(String xApiVersion, String merchant, String serviceid, String serviceNumber, String customerNumber, final ApiCallback<List<Subscription>> callback) throws ApiException {

        ProgressResponseBody.ProgressListener progressListener = null;
        ProgressRequestBody.ProgressRequestListener progressRequestListener = null;

        if (callback != null) {
            progressListener = new ProgressResponseBody.ProgressListener() {
                @Override
                public void update(long bytesRead, long contentLength, boolean done) {
                    callback.onDownloadProgress(bytesRead, contentLength, done);
                }
            };

            progressRequestListener = new ProgressRequestBody.ProgressRequestListener() {
                @Override
                public void onRequestProgress(long bytesWritten, long contentLength, boolean done) {
                    callback.onUploadProgress(bytesWritten, contentLength, done);
                }
            };
        }

        com.squareup.okhttp.Call call = subscriptionGetValidateBeforeCall(xApiVersion, merchant, serviceid, serviceNumber, customerNumber, progressListener, progressRequestListener);
        Type localVarReturnType = new TypeToken<List<Subscription>>(){}.getType();
        apiClient.executeAsync(call, localVarReturnType, callback);
        return call;
    }
}
