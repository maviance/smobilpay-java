package org.maviance.s3pjavaclient.examples;

import org.maviance.s3pjavaclient.ApiClient;
import org.maviance.s3pjavaclient.ApiException;
import org.maviance.s3pjavaclient.api.CollectionApi;

/**
 * Verifies that a service number is valid
 */
public class VerifyServiceId {

    public static void main(String[] args) {

        ApiClient apiClient = new ApiClient(AccessDetails.BASE_URL, AccessDetails.ACCESS_TOKEN, AccessDetails.ACCESS_SECRET);
        CollectionApi collectionApi = new CollectionApi(apiClient);

        try {
            String merchantCode = "CMMTN";
            Integer serviceId = 20051;
            String serviceNumber = "678451236";

            Boolean verify = collectionApi.verifyServiceNumberGet(merchantCode, serviceId, serviceNumber);
            String message = String.format("The service number: %s with merchant: %s for service: %s is %s\n", serviceNumber, merchantCode, serviceId,  (verify) ? "VALID" : "INVALID");
            System.out.println(message);
        } catch (ApiException e) {
            System.out.println("An error occurred: \n");
            System.out.println("Response Body: " + e.getResponseBody());
        }
    }
}
