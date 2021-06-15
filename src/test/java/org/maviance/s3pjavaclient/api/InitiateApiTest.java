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

import org.maviance.s3pjavaclient.ApiException;
import org.maviance.s3pjavaclient.model.Bill;
import org.maviance.s3pjavaclient.model.Error;
import org.maviance.s3pjavaclient.model.Quote;
import org.maviance.s3pjavaclient.model.QuoteRequest;
import org.maviance.s3pjavaclient.model.Subscription;
import org.junit.Test;
import org.junit.Ignore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * API tests for InitiateApi
 */
@Ignore
public class InitiateApiTest {

    private final InitiateApi api = new InitiateApi();

    
    /**
     * Get bill payment handler
     *
     * A request to this endpoint returns bill payment handler records for a service by a service number and retrieves its details if available. Bill payments come in 2 flavors – which are determined by the related service’s type: 1.  **SEARCHABLE_BILL** – When calling the endpoint for searchable bills, the result set will contain a list of all open bills for the selected service number. Each bill has its own Payment Item Identifier. 2.  **NON_SEARCHABLE_BILL** – When calling the endpoint for non-searchable bills, the result set will always contain a single bill item with a Payment Item ID to perform the collection for the provided service number. 
     *
     * @throws ApiException
     *          if the Api call fails
     */
    @Test
    public void billGetTest() throws ApiException {
        String xApiVersion = null;
        String merchant = null;
        Integer serviceid = null;
        String serviceNumber = null;
        List<Bill> response = api.billGet(xApiVersion, merchant, serviceid, serviceNumber);

        // TODO: test validations
    }
    
    /**
     * Request quote with price details about the payment
     *
     * Calling this web-service requests a quote from the system for the payment collection of the selected payment item and the specified payment amount in the system. The amount is to be chosen based on the services amountType, so can either be fixed or a custom entered value. The third parameter specifies the payment method that the customer has chosen in order to pay for the collection, as there may be additional charges depending on the selected method. A quote will only remain available for short time (a few minutes) and will expire. A quote will return the actual costs involved in collecting the payment. A quote always needs to be requested before making a collection.\&quot; 
     *
     * @throws ApiException
     *          if the Api call fails
     */
    @Test
    public void quotestdPostTest() throws ApiException {
        String xApiVersion = null;
        QuoteRequest body = null;
        Quote response = api.quotestdPost(xApiVersion, body);

        // TODO: test validations
    }
    
    /**
     * Get subscription payment handler
     *
     * A request to this endpoint looks up a subscription record for a service by either service number or customer number and retrieves its details if available. When calling the endpoint the result set will contain a list of all available subscriptions found under the provided search criteria. Each subscription has its own Payment Item Identifier. 
     *
     * @throws ApiException
     *          if the Api call fails
     */
    @Test
    public void subscriptionGetTest() throws ApiException {
        String xApiVersion = null;
        String merchant = null;
        String serviceid = null;
        String serviceNumber = null;
        String customerNumber = null;
        List<Subscription> response = api.subscriptionGet(xApiVersion, merchant, serviceid, serviceNumber, customerNumber);

        // TODO: test validations
    }
    
}
