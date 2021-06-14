package org.maviance.s3pjavaclient.examples;

import org.maviance.s3pjavaclient.ApiClient;
import org.maviance.s3pjavaclient.ApiException;
import org.maviance.s3pjavaclient.api.CollectionApi;
import org.maviance.s3pjavaclient.api.HistoryApi;
import org.maviance.s3pjavaclient.api.VerifyApi;
import org.maviance.s3pjavaclient.model.*;

import java.util.List;

public class ProductCollection {
    private static String separator = "  --  ";

    // Number to buy product for
    private static String serviceNumber = "2577631317";
    // Cash In service number
    private static int serviceId = 20021;

    // Customer details
    private static String phone = "6123465798";
    private static String email = "name@example.com";

    public static void main(String[] args) {
        ApiClient apiClient = new ApiClient(AccessDetails.BASE_URL, AccessDetails.ACCESS_TOKEN, AccessDetails.ACCESS_SECRET);
        apiClient.setDebugging(false);
        CollectionApi collectionApi = new CollectionApi(apiClient);

        try {
            List<Product> products = collectionApi.productGet(serviceId);
            System.out.println("===================================PRODUCTS===========================================");
            products.forEach(product -> System.out.println(product.getServiceid() + separator + product.getName()));
            System.out.println("=======================================================================================");
            Product product = products.get(0);

            System.out.println("Product Service: " + product.getServiceid());
            System.out.println("Product Description: " + product.getDescription());
            System.out.println("Product Amount: " + product.getAmountLocalCur());
            System.out.println("Product Payment Item Id: " + product.getPayItemId());

            QuoteRequest quote = new QuoteRequest();
            quote.setAmount(product.getAmountLocalCur());
            quote.setPayItemId(product.getPayItemId());

            Quotestd offer = collectionApi.quotestdPost(quote);
            System.out.println("Quote ID: " + offer.getQuoteId());
            System.out.println(offer);

            // Execute the collection
            CollectionstdRequest collection = new CollectionstdRequest();
            collection.setCustomerPhonenumber(phone);
            collection.setCustomerEmailaddress(email);
            collection.setQuoteId(offer.getQuoteId());
            collection.setServiceNumber(String.valueOf(serviceNumber));
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
