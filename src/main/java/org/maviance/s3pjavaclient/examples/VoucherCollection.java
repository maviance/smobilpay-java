package org.maviance.s3pjavaclient.examples;

import org.maviance.s3pjavaclient.ApiClient;
import org.maviance.s3pjavaclient.ApiException;
import org.maviance.s3pjavaclient.api.ConfirmApi;
import org.maviance.s3pjavaclient.api.InitiateApi;
import org.maviance.s3pjavaclient.api.MasterdataApi;
import org.maviance.s3pjavaclient.api.VerifyApi;
import org.maviance.s3pjavaclient.model.*;

import java.util.Collection;
import java.util.List;

public class VoucherCollection {
    private static String separator = "  --  ";

    private static int serviceId = 2000;

    // Cash In service number
    private static String serviceNumber = "014375112886";

    // Customer details.This refers to the information of the smobilpay account owner
    private static int phone = 653754334;
    private static String email = "name@example.com";

    public static void main(String[] args) {
        ApiClient apiClient = new ApiClient(AccessDetails.BASE_URL, AccessDetails.ACCESS_TOKEN, AccessDetails.ACCESS_SECRET);
        apiClient.setDebugging(true);
        apiClient.setDebugging(false);
        ConfirmApi confirmApi = new ConfirmApi(apiClient);
        MasterdataApi masterdataApi = new MasterdataApi(apiClient);
        InitiateApi initiateApi = new InitiateApi(apiClient);

        try {

            List<Product> products = masterdataApi.voucherGet(AccessDetails.VERSION, serviceId);

            products.forEach(product -> System.out.println(product.getServiceid() + separator + product.getName()));

            Product voucher = products.get(0);
            //set the voucher amount.
            voucher.setAmountLocalCur(1000.0f);

            System.out.println("Voucher Service: " + voucher.getServiceid());
            System.out.println("Voucher Description: " + voucher.getDescription());
            System.out.println("Voucher Amount: " + voucher.getAmountLocalCur());
            System.out.println("Voucher Payment Item Id: " + voucher.getPayItemId());

            QuoteRequest quote = new QuoteRequest();
            quote.setAmount(voucher.getAmountLocalCur());
            quote.setPayItemId(voucher.getPayItemId());
            Quote offer = initiateApi.quotestdPost(AccessDetails.VERSION, quote);
            System.out.println("Quote ID: " + offer.getQuoteId());

            // Execute the collection
            CollectionRequest collection = new CollectionRequest();
            collection.setCustomerPhonenumber(phone);
            collection.setCustomerEmailaddress(email);
            collection.setQuoteId(offer.getQuoteId());
            collection.setServiceNumber(""+serviceNumber);

            CollectionResponse payment = confirmApi.collectstdPost(AccessDetails.VERSION, collection);
            System.out.println("Collection Payment TX Number:" + payment.getPtn());
            System.out.println("Voucher PIN: " + payment.getPin());

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
