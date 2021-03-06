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


package org.maviance.s3pjavaclient.api.unit;

import org.maviance.s3pjavaclient.ApiException;
import org.maviance.s3pjavaclient.api.VerifyApi;
import org.maviance.s3pjavaclient.model.Error;
import org.threeten.bp.LocalDate;
import org.maviance.s3pjavaclient.model.PaymentStatus;
import org.junit.Test;
import org.junit.Ignore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * API tests for VerifyApi
 */
@Ignore
public class VerifyApiTest {

    private final VerifyApi api = new VerifyApi();

    
    /**
     * Retrieve list of historic payment collection.
     *
     * This endpoint allows the search for historic payment collection records by time that was provided during payment collection. Both parameters have to be provided!
     *
     * @throws ApiException
     *          if the Api call fails
     */
    @Test
    public void historystdGetTest() throws ApiException {
        String xApiVersion = null;
        LocalDate timestampFrom = null;
        LocalDate timestampTo = null;
        List<PaymentStatus> response = api.historystdGet(xApiVersion, timestampFrom, timestampTo);

        // TODO: test validations
    }
    
    /**
     * Get the current payment collection status
     *
     * Call this endpoint to retrieve the current payment status by either transaction number (PTN) or the custom transaction reference (TRID) that was provided during payment collection. At least one of these parameters has to be provided!
     *
     * @throws ApiException
     *          if the Api call fails
     */
    @Test
    public void verifytxGetTest() throws ApiException {
        String xApiVersion = null;
        String ptn = null;
        String trid = null;
        List<PaymentStatus> response = api.verifytxGet(xApiVersion, ptn, trid);

        // TODO: test validations
    }
    
}
