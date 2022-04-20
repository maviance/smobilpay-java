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
import org.maviance.s3pjavaclient.api.ConfirmApi;
import org.maviance.s3pjavaclient.model.CollectionRequest;
import org.maviance.s3pjavaclient.model.CollectionResponse;
import org.maviance.s3pjavaclient.model.Error;
import org.junit.Test;
import org.junit.Ignore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * API tests for ConfirmApi
 */
@Ignore
public class ConfirmApiTest {

    private final ConfirmApi api = new ConfirmApi();

    
    /**
     * Execute payment collection
     *
     * This endpoint executes a payment collection. Any collection will reduce the agent balance by service amount plus the service fee. Each collection must include a reference to corresponding quote and payment authorization token. Whether or not fields are mandatory depends on the service configuration
     *
     * @throws ApiException
     *          if the Api call fails
     */
    @Test
    public void collectstdPostTest() throws ApiException {
        String xApiVersion = null;
        CollectionRequest body = null;
        CollectionResponse response = api.collectstdPost(xApiVersion, body);

        // TODO: test validations
    }
    
}