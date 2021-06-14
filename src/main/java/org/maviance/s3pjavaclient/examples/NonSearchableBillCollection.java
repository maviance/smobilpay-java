package org.maviance.s3pjavaclient.examples;

import org.maviance.s3pjavaclient.ApiClient;
import org.maviance.s3pjavaclient.ApiException;
import org.maviance.s3pjavaclient.api.CollectionApi;
import org.maviance.s3pjavaclient.api.HistoryApi;
import org.maviance.s3pjavaclient.api.VerifyApi;
import org.maviance.s3pjavaclient.model.*;

import java.util.List;

public class NonSearchableBillCollection {

    private static String merchantCode = "ENEO";
    private static int serviceId = 1001;
    // Cash In service number
    private static String serviceNumber = "";
    private static float amount = 100f;

    // Customer details
    private static String phone = "6123465798";
    private static String email = "name@example.com";
    private static String address = "My Street N33";
    private static String name = "John Doe";

    public static void main(String[] args) {
        ApiClient apiClient = new ApiClient(AccessDetails.BASE_URL, AccessDetails.ACCESS_TOKEN, AccessDetails.ACCESS_SECRET);
        apiClient.setDebugging(true);
        CollectionApi collectionApi = new CollectionApi(apiClient);

        try {
            List<Bill> bills = collectionApi.billGet(merchantCode, serviceId, serviceNumber);
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
            Quotestd offer = collectionApi.quotestdPost(quote);
            System.out.println("Quote ID: " + offer.getQuoteId());

            // Execute the collection
            CollectionstdRequest collection = new CollectionstdRequest();
            collection.setCustomerPhonenumber(phone);
            collection.setCustomerEmailaddress(email);
            collection.setQuoteId(offer.getQuoteId());
            collection.setServiceNumber(""+serviceNumber);
            collection.setCustomerAddress(address);
            collection.setCustomerName(name);
            Collectionstd payment = collectionApi.collectstdPost(collection);
            System.out.println("Collection Payment TX Number:" + payment.getPtn());

            // Lookup record in Smobilpay by PTN
            VerifyApi verifyApi = new VerifyApi(apiClient);
            List<Historystd> historystds =  verifyApi.verifytxGet(payment.getPtn(), null);
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
