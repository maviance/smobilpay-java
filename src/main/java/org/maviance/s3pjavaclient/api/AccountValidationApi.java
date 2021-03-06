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


import org.maviance.s3pjavaclient.model.Error;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AccountValidationApi {
    private ApiClient apiClient;

    public AccountValidationApi() {
        this(Configuration.getDefaultApiClient());
    }

    public AccountValidationApi(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public ApiClient getApiClient() {
        return apiClient;
    }

    public void setApiClient(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    /**
     * Build call for verifyGet
     * @param xApiVersion api version info (required)
     * @param merchant Unique  merchant code (required)
     * @param serviceid Unique  service Identifier (required)
     * @param serviceNumber Service number with merchant (e.g. meter number in bills from a utility provider) for which to perform the bill payment (required)
     * @param progressListener Progress listener
     * @param progressRequestListener Progress request listener
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     */
    public com.squareup.okhttp.Call verifyGetCall(String xApiVersion, String merchant, Integer serviceid, String serviceNumber, final ProgressResponseBody.ProgressListener progressListener, final ProgressRequestBody.ProgressRequestListener progressRequestListener) throws ApiException {
        Object localVarPostBody = null;
        
        // create path and map variables
        String localVarPath = "/verify";

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
    private com.squareup.okhttp.Call verifyGetValidateBeforeCall(String xApiVersion, String merchant, Integer serviceid, String serviceNumber, final ProgressResponseBody.ProgressListener progressListener, final ProgressRequestBody.ProgressRequestListener progressRequestListener) throws ApiException {
        // verify the required parameter 'xApiVersion' is set
        if (xApiVersion == null) {
            throw new ApiException("Missing the required parameter 'xApiVersion' when calling verifyGet(Async)");
        }
        // verify the required parameter 'merchant' is set
        if (merchant == null) {
            throw new ApiException("Missing the required parameter 'merchant' when calling verifyGet(Async)");
        }
        // verify the required parameter 'serviceid' is set
        if (serviceid == null) {
            throw new ApiException("Missing the required parameter 'serviceid' when calling verifyGet(Async)");
        }
        // verify the required parameter 'serviceNumber' is set
        if (serviceNumber == null) {
            throw new ApiException("Missing the required parameter 'serviceNumber' when calling verifyGet(Async)");
        }
        
        com.squareup.okhttp.Call call = verifyGetCall(xApiVersion, merchant, serviceid, serviceNumber, progressListener, progressRequestListener);
        return call;

        
        
        
        
    }

    /**
     * Verify service number
     * For services that support verification (indicated by the \&quot;isVerifiable\&quot; flag) the service number can be provided to this endpoint. The system will verify wether or not the service number is valid with the selected service. 
     * @param xApiVersion api version info (required)
     * @param merchant Unique  merchant code (required)
     * @param serviceid Unique  service Identifier (required)
     * @param serviceNumber Service number with merchant (e.g. meter number in bills from a utility provider) for which to perform the bill payment (required)
     * @return Boolean
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     */
    public Boolean verifyGet(String xApiVersion, String merchant, Integer serviceid, String serviceNumber) throws ApiException {
        ApiResponse<Boolean> resp = verifyGetWithHttpInfo(xApiVersion, merchant, serviceid, serviceNumber);
        return resp.getData();
    }

    /**
     * Verify service number
     * For services that support verification (indicated by the \&quot;isVerifiable\&quot; flag) the service number can be provided to this endpoint. The system will verify wether or not the service number is valid with the selected service. 
     * @param xApiVersion api version info (required)
     * @param merchant Unique  merchant code (required)
     * @param serviceid Unique  service Identifier (required)
     * @param serviceNumber Service number with merchant (e.g. meter number in bills from a utility provider) for which to perform the bill payment (required)
     * @return ApiResponse&lt;Boolean&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     */
    public ApiResponse<Boolean> verifyGetWithHttpInfo(String xApiVersion, String merchant, Integer serviceid, String serviceNumber) throws ApiException {
        com.squareup.okhttp.Call call = verifyGetValidateBeforeCall(xApiVersion, merchant, serviceid, serviceNumber, null, null);
        Type localVarReturnType = new TypeToken<Boolean>(){}.getType();
        return apiClient.execute(call, localVarReturnType);
    }

    /**
     * Verify service number (asynchronously)
     * For services that support verification (indicated by the \&quot;isVerifiable\&quot; flag) the service number can be provided to this endpoint. The system will verify wether or not the service number is valid with the selected service. 
     * @param xApiVersion api version info (required)
     * @param merchant Unique  merchant code (required)
     * @param serviceid Unique  service Identifier (required)
     * @param serviceNumber Service number with merchant (e.g. meter number in bills from a utility provider) for which to perform the bill payment (required)
     * @param callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     */
    public com.squareup.okhttp.Call verifyGetAsync(String xApiVersion, String merchant, Integer serviceid, String serviceNumber, final ApiCallback<Boolean> callback) throws ApiException {

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

        com.squareup.okhttp.Call call = verifyGetValidateBeforeCall(xApiVersion, merchant, serviceid, serviceNumber, progressListener, progressRequestListener);
        Type localVarReturnType = new TypeToken<Boolean>(){}.getType();
        apiClient.executeAsync(call, localVarReturnType, callback);
        return call;
    }
}
