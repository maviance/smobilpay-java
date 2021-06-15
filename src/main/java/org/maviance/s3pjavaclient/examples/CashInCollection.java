package org.maviance.s3pjavaclient.examples;

import org.maviance.s3pjavaclient.ApiClient;
import org.maviance.s3pjavaclient.ApiException;
import org.maviance.s3pjavaclient.api.ConfirmApi;
import org.maviance.s3pjavaclient.api.MasterdataApi;
import org.maviance.s3pjavaclient.api.InitiateApi;
import org.maviance.s3pjavaclient.api.VerifyApi;
import org.maviance.s3pjavaclient.model.*;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

class CashInCollection {
    private static final String separator = "  --  ";

    // Cash In service number
    private static final String serviceNumber = "237674827066";
    private static final int serviceId = 584521;

    // Customer details
    private static final int phone = 698223844;
    private static final String email = "name@example.com";

    public static void main(String[] args) {
        ApiClient apiClient = new ApiClient(AccessDetails.BASE_URL, AccessDetails.ACCESS_TOKEN, AccessDetails.ACCESS_SECRET);
        apiClient.setDebugging(false);
        MasterdataApi masterdataApi = new MasterdataApi(apiClient);

        try {
            List<Cashin> packages = masterdataApi.cashinGet(AccessDetails.VERSION, serviceId);
            System.out.println("==========================PACKAGES========================================");
            packages.forEach(item -> System.out.println(item.getServiceid() + separator + item.getName()));
            System.out.println("===========================================================================");
            Cashin cashin = packages.get(0);

            System.out.println("Cash-In Service: " + cashin.getServiceid());
            System.out.println("Cash-In Description: " + cashin.getDescription());
            System.out.println("Cash-In Amount: " + cashin.getAmountLocalCur());
            System.out.println("Cash-In Payment Item Id: " + cashin.getPayItemId());

            QuoteRequest quote = new QuoteRequest();
            quote.setAmount(500f);
            quote.setPayItemId(cashin.getPayItemId());
            InitiateApi initiateApi = new InitiateApi(apiClient);
            Quote offer = initiateApi.quotestdPost(AccessDetails.VERSION, quote);
            System.out.println("Quote ID: " + offer.getQuoteId());
            System.out.println(offer);
            ConfirmApi confirmApi = new ConfirmApi(apiClient);
            // Execute the collection
            CollectionRequest collection = new CollectionRequest();
            collection.setCustomerPhonenumber(phone);
            collection.setCustomerEmailaddress(email);
            collection.setQuoteId(offer.getQuoteId());
            collection.setServiceNumber(""+serviceNumber);
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
