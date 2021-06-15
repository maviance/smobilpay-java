package org.maviance.s3pjavaclient.examples;

import org.maviance.s3pjavaclient.ApiClient;
import org.maviance.s3pjavaclient.ApiException;
import org.maviance.s3pjavaclient.api.MasterdataApi;
import org.maviance.s3pjavaclient.api.ConfirmApi;
import org.maviance.s3pjavaclient.api.InitiateApi;
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
    private static int phone = 653754334;
    private static String email = "name@example.com";

    public static void main(String[] args) {
        ApiClient apiClient = new ApiClient(AccessDetails.BASE_URL, AccessDetails.ACCESS_TOKEN, AccessDetails.ACCESS_SECRET);
        apiClient.setDebugging(false);
        ConfirmApi confirmApi = new ConfirmApi(apiClient);
        InitiateApi initiateApi = new InitiateApi(apiClient);
        MasterdataApi masterDataApi = new MasterdataApi(apiClient);

        try {
            List<Product> products = masterDataApi.productGet(AccessDetails.VERSION, serviceId);
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

            Quote offer = initiateApi.quotestdPost(AccessDetails.VERSION, quote);
            System.out.println("Quote ID: " + offer.getQuoteId());
            System.out.println(offer);

            // Execute the collection
            CollectionRequest collection = new CollectionRequest();
            collection.setCustomerPhonenumber(phone);
            collection.setCustomerEmailaddress(email);
            collection.setQuoteId(offer.getQuoteId());
            collection.setServiceNumber(String.valueOf(serviceNumber));
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
