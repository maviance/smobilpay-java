package org.maviance.s3pjavaclient.examples;

import org.maviance.s3pjavaclient.ApiClient;
import org.maviance.s3pjavaclient.ApiException;
import org.maviance.s3pjavaclient.api.CollectionApi;
import org.maviance.s3pjavaclient.api.HistoryApi;
import org.maviance.s3pjavaclient.api.VerifyApi;
import org.maviance.s3pjavaclient.model.*;

import java.util.List;

public class CashOutCollection {
    private static final String separator = "  --  ";

    // Cash In service number
    private static final String serviceNumber = "237674827066";
    private static final int serviceId = 20053;

    // Customer details
    private static final String phone = "698223844";
    private static final String email = "name@example.com";

    public static void main(String[] args) {
        ApiClient apiClient = new ApiClient(AccessDetails.BASE_URL, AccessDetails.ACCESS_TOKEN, AccessDetails.ACCESS_SECRET);
        apiClient.setDebugging(false);
        CollectionApi collectionApi = new CollectionApi(apiClient);

        try {
            List<Cashout> packages = collectionApi.cashoutGet(serviceId);
            System.out.println("==========================PACKAGES========================================");
            packages.forEach(item -> System.out.println(item.getServiceid() + separator + item.getName()));
            System.out.println("===========================================================================");
            Cashout cashout = packages.get(0);

            System.out.println("Cash-In Service: " + cashout.getServiceid());
            System.out.println("Cash-In Description: " + cashout.getDescription());
            System.out.println("Cash-In Amount: " + cashout.getAmountLocalCur());
            System.out.println("Cash-In Payment Item Id: " + cashout.getPayItemId());

            QuoteRequest quote = new QuoteRequest();
            quote.setAmount(2000f);
            quote.setPayItemId(cashout.getPayItemId());
            Quotestd offer = collectionApi.quotestdPost(quote);
            System.out.println("Quote ID: " + offer.getQuoteId());
            System.out.println(offer);

            // Execute the collection
            CollectionstdRequest collection = new CollectionstdRequest();
            collection.setCustomerPhonenumber(phone);
            collection.setCustomerEmailaddress(email);
            collection.setQuoteId(offer.getQuoteId());
            collection.setServiceNumber(""+serviceNumber);
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
