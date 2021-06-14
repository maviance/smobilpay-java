package org.maviance.s3pjavaclient.examples;

import org.maviance.s3pjavaclient.ApiClient;
import org.maviance.s3pjavaclient.ApiException;
import org.maviance.s3pjavaclient.api.CollectionApi;
import org.maviance.s3pjavaclient.api.HistoryApi;
import org.maviance.s3pjavaclient.api.VerifyApi;
import org.maviance.s3pjavaclient.model.*;

import java.util.List;

public class VoucherCollection {
    private static String separator = "  --  ";

    private static int serviceId = 2000;

    // Cash In service number
    private static String serviceNumber = "014375112886";

    // Customer details.This refers to the information of the smobilpay account owner
    private static String phone = "6123465798";
    private static String email = "name@example.com";

    public static void main(String[] args) {
        ApiClient apiClient = new ApiClient(AccessDetails.BASE_URL, AccessDetails.ACCESS_TOKEN, AccessDetails.ACCESS_SECRET);
        apiClient.setDebugging(true);
        CollectionApi collectionApi = new CollectionApi(apiClient);

        try {

            List<Topup> topups = collectionApi.voucherGet(serviceId);

            topups.forEach(topup -> System.out.println(topup.getServiceid() + separator + topup.getName()));

            Topup voucher = topups.get(0);
            //set the voucher amount.
            voucher.setAmountLocalCur(1000.0f);

            System.out.println("Voucher Service: " + voucher.getServiceid());
            System.out.println("Voucher Description: " + voucher.getDescription());
            System.out.println("Voucher Amount: " + voucher.getAmountLocalCur());
            System.out.println("Voucher Payment Item Id: " + voucher.getPayItemId());

            QuoteRequest quote = new QuoteRequest();
            quote.setAmount(voucher.getAmountLocalCur());
            quote.setPayItemId(voucher.getPayItemId());
            Quotestd offer = collectionApi.quotestdPost(quote);
            System.out.println("Quote ID: " + offer.getQuoteId());

            // Execute the collection
            CollectionstdRequest collection = new CollectionstdRequest();
            collection.setCustomerPhonenumber(phone);
            collection.setCustomerEmailaddress(email);
            collection.setQuoteId(offer.getQuoteId());
            collection.setServiceNumber(""+serviceNumber);
            Collectionstd payment = collectionApi.collectstdPost(collection);
            System.out.println("Collection Payment TX Number:" + payment.getPtn());
            System.out.println("Voucher PIN: " + payment.getPin());

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
