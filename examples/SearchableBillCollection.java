import org.maviance.s3pjavaclient.ApiClient;
import org.maviance.s3pjavaclient.ApiException;
import org.maviance.s3pjavaclient.api.CollectionApi;
import org.maviance.s3pjavaclient.api.HistoryApi;
import org.maviance.s3pjavaclient.model.*;

import java.util.List;

public class SearchableBillCollection {

    private static String merchantCode = "ENEO";
    private static int serviceId = 1001;
    // Cash In service number
    private static String serviceNumber = "4038";

    // Customer details
    private static String phone = "6123465798";
    private static String email = "name@example.com";

    public static void main(String[] args) {
        ApiClient apiClient = new ApiClient(AccessDetails.BASE_URL, AccessDetails.ACCESS_TOKEN, AccessDetails.ACCESS_SECRET);
        apiClient.setDebugging(false);
        CollectionApi collectionApi = new CollectionApi(apiClient);

        try {
            List<Bill> bills = collectionApi.billGet(merchantCode, serviceId, serviceNumber);
            if (bills.isEmpty()) {
                System.out.println("No matching open bills found");
                System.exit(0);
            }

            Bill bill = bills.get(0);
            System.out.println("Bill Payment Item Id: " + bill.getPayItemId());

            QuoteRequest quote = new QuoteRequest();
            quote.setAmount(bill.getAmountLocalCur());
            quote.setPayItemId(bill.getPayItemId());
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

            // Lookup record in Smobilpay by PTN
            HistoryApi historyApi = new HistoryApi(apiClient);
            List<Historystd> historystds = historyApi.historystdGet(payment.getPtn(), null, null, null);
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
