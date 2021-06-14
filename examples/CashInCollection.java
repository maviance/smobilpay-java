import org.maviance.s3pjavaclient.ApiClient;
import org.maviance.s3pjavaclient.ApiException;
import org.maviance.s3pjavaclient.StringUtil;
import org.maviance.s3pjavaclient.api.CollectionApi;
import org.maviance.s3pjavaclient.api.ChecksApi;
import org.maviance.s3pjavaclient.api.HistoryApi;
import org.maviance.s3pjavaclient.model.*;
import java.util.List;

class CashInCollection {
    private static String separator = "  --  ";

    // Cash In service number
    private static String serviceNumber = "";
    private static int serviceId = 20052;

    // Customer details
    private static String phone = "6123465798";
    private static String email = "name@example.com";

    public static void main(String[] args) {
        ApiClient apiClient = new ApiClient(AccessDetails.BASE_URL, AccessDetails.ACCESS_TOKEN, AccessDetails.ACCESS_SECRET);
        apiClient.setDebugging(true);
        CollectionApi collectionApi = new CollectionApi(apiClient);

        try {
            List<Cashin> packages = collectionApi.cashinGet(serviceId);
            packages.forEach(item -> System.out.println(item.getServiceid() + separator + item.getName()));

            Cashin cashin = packages.get(0);

            System.out.println("Cash-In Service: " + cashin.getServiceid());
            System.out.println("Cash-In Description: " + cashin.getDescription());
            System.out.println("Cash-In Amount: " + cashin.getAmountLocalCur());
            System.out.println("Cash-In Payment Item Id: " + cashin.getPayItemId());

            QuoteRequest quote = new QuoteRequest();
            quote.setAmount(100.0f);
            quote.setPayItemId(cashin.getPayItemId());
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
