package org.maviance.s3pjavaclient.examples;

import org.maviance.s3pjavaclient.ApiClient;
import org.maviance.s3pjavaclient.ApiException;
import org.maviance.s3pjavaclient.api.ConfirmApi;
import org.maviance.s3pjavaclient.api.InitiateApi;
import org.maviance.s3pjavaclient.api.VerifyApi;
import org.maviance.s3pjavaclient.model.*;

import java.util.Collection;
import java.util.List;

public class NonSearchableBillCollection {

    private static String merchantCode = "ENEO";
    private static int serviceId = 1001;
    // Cash In service number
    private static String serviceNumber = "";
    private static float amount = 100f;

    // Customer details
    private static int phone = 653754334;
    private static String email = "name@example.com";
    private static String address = "My Street N33";
    private static String name = "John Doe";

    public static void main(String[] args) {
        ApiClient apiClient = new ApiClient(AccessDetails.BASE_URL, AccessDetails.ACCESS_TOKEN, AccessDetails.ACCESS_SECRET);
        apiClient.setDebugging(true);
        ConfirmApi confirmApi = new ConfirmApi(apiClient);
        InitiateApi initiateApi = new InitiateApi(apiClient);

        try {
            List<Bill> bills = initiateApi.billGet(AccessDetails.VERSION, merchantCode, serviceId, serviceNumber);
            if (bills.isEmpty()) {
                System.out.println("No matching open bills found");
                System.exit(0);
            }

            // Take the first bill in the list and request quote
            Bill bill = bills.get(0);
            System.out.println("Bill Payment Item Id: " + bill.getPayItemId());

            QuoteRequest quote = new QuoteRequest();
            quote.setAmount(amount);
            quote.setPayItemId(bill.getPayItemId());
            Quote offer = initiateApi.quotestdPost(AccessDetails.VERSION, quote);
            System.out.println("Quote ID: " + offer.getQuoteId());

            // Execute the collection
            CollectionRequest collection = new CollectionRequest();
            collection.setCustomerPhonenumber(phone);
            collection.setCustomerEmailaddress(email);
            collection.setQuoteId(offer.getQuoteId());
            collection.setServiceNumber(""+serviceNumber);
            collection.setCustomerAddress(address);
            collection.setCustomerName(name);
            CollectionResponse payment = confirmApi.collectstdPost(AccessDetails.VERSION, collection);
            System.out.println("Collection Payment TX Number:" + payment.getPtn());

            // Lookup record in Smobilpay by PTN
            VerifyApi verifyApi = new VerifyApi(apiClient);
            List<PaymentStatus> historystds =  verifyApi.verifytxGet(AccessDetails.VERSION, payment.getPtn(), null);
            if (historystds.size() != 1) {
                System.out.println("Should have found exactly one record.");
                System.exit(0);
            }
            System.out.println("History Result (Status): " + historystds.get(0).getStatus());
        } catch (ApiException e) {
            System.out.println("An error occurred: \n");
            System.out.println(e.getResponseBody());
        }

    }
}
