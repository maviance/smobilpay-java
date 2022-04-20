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

package org.maviance.s3pjavaclient.api.functional;

import org.maviance.s3pjavaclient.ApiException;
import org.maviance.s3pjavaclient.api.HealthcheckApi;
import org.maviance.s3pjavaclient.model.Error;
import org.maviance.s3pjavaclient.model.Ping;
import org.junit.Test;
import org.junit.Ignore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * API tests for HealthcheckApi
 */
@Ignore
public class HealthcheckApiTest {

    private final HealthcheckApi api = new HealthcheckApi();

    /**
     * Check on the availability of the api
     *
     * This endpoint simply checks the existence and validity of the request on the server by returning a valid response object or an error message. Its primary purpose is to provide a feedback on whether or not the API is available. It also provides the current server time and timezone
     *
     * @throws ApiException
     *          if the Api call fails
     */
    @Test
    public void pingGetTest() throws ApiException {
        String xApiVersion = null;
        Ping response = api.pingGet(xApiVersion);

        // TODO: test validations
    }
}
