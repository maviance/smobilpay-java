/*
 * Smobilpay S3P API
 * Smobilpay Third Party API FOR PAYMENT COLLECTIONS
 *
 * OpenAPI spec version: 3.0.3
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


import org.maviance.s3pjavaclient.model.Account;
import org.maviance.s3pjavaclient.model.Error;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AccountApi {
    private ApiClient apiClient;

    public AccountApi() {
        this(Configuration.getDefaultApiClient());
    }

    public AccountApi(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public ApiClient getApiClient() {
        return apiClient;
    }

    public void setApiClient(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    /**
     * Build call for accountGet
     * @param xApiVersion api version info (required)
     * @param progressListener Progress listener
     * @param progressRequestListener Progress request listener
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     */
    public com.squareup.okhttp.Call accountGetCall(String xApiVersion, final ProgressResponseBody.ProgressListener progressListener, final ProgressRequestBody.ProgressRequestListener progressRequestListener) throws ApiException {
        Object localVarPostBody = null;
        
        // create path and map variables
        String localVarPath = "/account";

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
    private com.squareup.okhttp.Call accountGetValidateBeforeCall(String xApiVersion, final ProgressResponseBody.ProgressListener progressListener, final ProgressRequestBody.ProgressRequestListener progressRequestListener) throws ApiException {
        // verify the required parameter 'xApiVersion' is set
        if (xApiVersion == null) {
            throw new ApiException("Missing the required parameter 'xApiVersion' when calling accountGet(Async)");
        }
        
        com.squareup.okhttp.Call call = accountGetCall(xApiVersion, progressListener, progressRequestListener);
        return call;

        
        
        
        
    }

    /**
     * Retrieve account information and remaining account balance
     * This endpoint returns the user???s account information ??? most notably the current balance of the user. Calling this service before and after **each** collection in order to retrieve the current limits and/or balance is **highly discouraged**. The recommended approach is as follows:   1. Only a successful payment collection transaction will affect the account balance. The corresponding endpoint will also return the current account balance after the collection in its result payload.   2. For unsuccessful payment transactions, the account balance will not be affected. The error message returns a verbose message as to why the transaction failed. There is no need to recheck the account after each error. 
     * @param xApiVersion api version info (required)
     * @return Account
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     */
    public Account accountGet(String xApiVersion) throws ApiException {
        ApiResponse<Account> resp = accountGetWithHttpInfo(xApiVersion);
        return resp.getData();
    }

    /**
     * Retrieve account information and remaining account balance
     * This endpoint returns the user???s account information ??? most notably the current balance of the user. Calling this service before and after **each** collection in order to retrieve the current limits and/or balance is **highly discouraged**. The recommended approach is as follows:   1. Only a successful payment collection transaction will affect the account balance. The corresponding endpoint will also return the current account balance after the collection in its result payload.   2. For unsuccessful payment transactions, the account balance will not be affected. The error message returns a verbose message as to why the transaction failed. There is no need to recheck the account after each error. 
     * @param xApiVersion api version info (required)
     * @return ApiResponse&lt;Account&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     */
    public ApiResponse<Account> accountGetWithHttpInfo(String xApiVersion) throws ApiException {
        com.squareup.okhttp.Call call = accountGetValidateBeforeCall(xApiVersion, null, null);
        Type localVarReturnType = new TypeToken<Account>(){}.getType();
        return apiClient.execute(call, localVarReturnType);
    }

    /**
     * Retrieve account information and remaining account balance (asynchronously)
     * This endpoint returns the user???s account information ??? most notably the current balance of the user. Calling this service before and after **each** collection in order to retrieve the current limits and/or balance is **highly discouraged**. The recommended approach is as follows:   1. Only a successful payment collection transaction will affect the account balance. The corresponding endpoint will also return the current account balance after the collection in its result payload.   2. For unsuccessful payment transactions, the account balance will not be affected. The error message returns a verbose message as to why the transaction failed. There is no need to recheck the account after each error. 
     * @param xApiVersion api version info (required)
     * @param callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     */
    public com.squareup.okhttp.Call accountGetAsync(String xApiVersion, final ApiCallback<Account> callback) throws ApiException {

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

        com.squareup.okhttp.Call call = accountGetValidateBeforeCall(xApiVersion, progressListener, progressRequestListener);
        Type localVarReturnType = new TypeToken<Account>(){}.getType();
        apiClient.executeAsync(call, localVarReturnType, callback);
        return call;
    }
}
