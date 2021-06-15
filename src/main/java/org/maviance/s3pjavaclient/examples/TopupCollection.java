package org.maviance.s3pjavaclient.examples;

import org.maviance.s3pjavaclient.ApiClient;
import org.maviance.s3pjavaclient.ApiException;
import org.maviance.s3pjavaclient.api.ConfirmApi;
import org.maviance.s3pjavaclient.api.InitiateApi;
import org.maviance.s3pjavaclient.api.VerifyApi;
import org.maviance.s3pjavaclient.api.MasterdataApi;
import org.maviance.s3pjavaclient.model.*;

import java.util.List;

public class TopupCollection {
    private static String separator = "  --  ";

    private static int serviceId = 20062;

    // Cash In service number
    private static String serviceNumber = "698223844";

    // Customer details
    private static int phone = 698223844;
    private static String email = "name@example.com";

    public static void main(String[] args) {
        ApiClient apiClient = new ApiClient(AccessDetails.BASE_URL, AccessDetails.ACCESS_TOKEN, AccessDetails.ACCESS_SECRET);
        apiClient.setDebugging(false);
        ConfirmApi confirmApi = new ConfirmApi(apiClient);
        MasterdataApi masterdataApi = new MasterdataApi(apiClient);
        InitiateApi initiateApi = new InitiateApi(apiClient);

        try {

            List<Topup> topups = masterdataApi.topupGet(AccessDetails.VERSION, serviceId);
            topups.forEach(topup -> System.out.println(topup.getServiceid() + separator + topup.getName()));

            //to be updated with the particular type of topup to carry out
            int indexOfTopup = 0;
            Topup topup = topups.get(indexOfTopup);
            if (topup.getAmountType() == Topup.AmountTypeEnum.CUSTOM) {
                //you must set the amount;
                final float topUpAmount = 100.0f;
                topup.setAmountLocalCur(topUpAmount);
            }
            System.out.println("Topup Service: " + topup.getServiceid());
            System.out.println("Topup Description: " + topup.getDescription());
            System.out.println("Topup Amount: " + topup.getAmountLocalCur());
            System.out.println("Topup Payment Item Id: " + topup.getPayItemId());

            QuoteRequest quote = new QuoteRequest();
            quote.setAmount(topup.getAmountLocalCur());
            quote.setPayItemId(topup.getPayItemId());
            Quote offer = initiateApi.quotestdPost(AccessDetails.VERSION, quote);
            System.out.println("Quote ID: " + offer.getQuoteId());

            // Execute the collection
            CollectionRequest collection = new CollectionRequest();
            collection.setCustomerPhonenumber(phone);
            collection.setCustomerEmailaddress(email);
            collection.setQuoteId(offer.getQuoteId());
            collection.setServiceNumber(""+serviceNumber);
            collection.setCustomerName("Lowe Florian");
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
