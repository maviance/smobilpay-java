package org.maviance.s3pjavaclient.api;

import com.google.gson.reflect.TypeToken;
import org.maviance.s3pjavaclient.*;
import org.maviance.s3pjavaclient.model.Historystd;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class VerifyApi extends BaseApi {
    public VerifyApi() {
        super();
    }

    public VerifyApi(ApiClient apiClient) {
        super(apiClient);
    }

    /**
     * Build call for verifytxGet
     *
     * @param ptn                     Unique payment collection transaction number (optional)
     * @param trid                    custom external transaction reference provided during payment collection (optional)
     * @param progressListener        Progress listener
     * @param progressRequestListener Progress request listener
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     */
    public com.squareup.okhttp.Call verifytxGetCall(String ptn, String trid, final ProgressResponseBody.ProgressListener progressListener, final ProgressRequestBody.ProgressRequestListener progressRequestListener) throws ApiException {
        Object localVarPostBody = null;

        // create path and map variables
        String localVarPath = "/verifytx";

        List<Pair> localVarQueryParams = new ArrayList<>();
        if (ptn != null)
            localVarQueryParams.addAll(apiClient.parameterToPair("ptn", ptn));
        if (trid != null)
            localVarQueryParams.addAll(apiClient.parameterToPair("trid", trid));

        return getCall(progressListener, progressRequestListener, localVarPath, localVarQueryParams);
    }

    private com.squareup.okhttp.Call verifytxGetValidateBeforeCall(String ptn, String trid, final ProgressResponseBody.ProgressListener progressListener, final ProgressRequestBody.ProgressRequestListener progressRequestListener) throws ApiException {
        return verifytxGetCall(ptn, trid, progressListener, progressRequestListener);

    }

    /**
     * Get the current payment collection status
     * Call this endpoint to retrieve the current payment status by either transaction number (PTN) or the custom transaction reference (TRID) that was provided during payment collection. At least one of these parameters has to be provided!
     *
     * @param ptn         Unique payment collection transaction number (optional)
     * @param trid        custom external transaction reference provided during payment collection (optional)
     * @return List&lt;Historystd&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     */
    public List<Historystd> verifytxGet(String ptn, String trid) throws ApiException {
        ApiResponse<List<Historystd>> resp = verifytxGetWithHttpInfo(ptn, trid);
        return resp.getData();
    }

    /**
     * Get the current payment collection status
     * Call this endpoint to retrieve the current payment status by either transaction number (PTN) or the custom transaction reference (TRID) that was provided during payment collection. At least one of these parameters has to be provided!
     *
     * @param ptn  Unique payment collection transaction number (optional)
     * @param trid custom external transaction reference provided during payment collection (optional)
     * @return ApiResponse&lt;List&lt;Historystd&gt;&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     */
    public ApiResponse<List<Historystd>> verifytxGetWithHttpInfo(String ptn, String trid) throws ApiException {
        com.squareup.okhttp.Call call = verifytxGetValidateBeforeCall(ptn, trid, null, null);
        Type localVarReturnType = new TypeToken<List<Historystd>>() {
        }.getType();
        return apiClient.execute(call, localVarReturnType);
    }

    /**
     * Get the current payment collection status (asynchronously)
     * Call this endpoint to retrieve the current payment status by either transaction number (PTN) or the custom transaction reference (TRID) that was provided during payment collection. At least one of these parameters has to be provided!
     *
     * @param ptn         Unique payment collection transaction number (optional)
     * @param trid        custom external transaction reference provided during payment collection (optional)
     * @param callback    The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     */
    public com.squareup.okhttp.Call verifytxGetAsync( String ptn, String trid, final ApiCallback<List<Historystd>> callback) throws ApiException {

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

        com.squareup.okhttp.Call call = verifytxGetValidateBeforeCall(ptn, trid, progressListener, progressRequestListener);
        Type localVarReturnType = new TypeToken<List<Historystd>>() {
        }.getType();
        apiClient.executeAsync(call, localVarReturnType, callback);
        return call;
    }
}
