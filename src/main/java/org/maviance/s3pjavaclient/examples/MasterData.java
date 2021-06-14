package org.maviance.s3pjavaclient.examples;

import org.maviance.s3pjavaclient.ApiClient;
import org.maviance.s3pjavaclient.ApiException;
import org.maviance.s3pjavaclient.api.ChecksApi;
import org.maviance.s3pjavaclient.api.MasterdataApi;
import org.maviance.s3pjavaclient.model.Account;
import org.maviance.s3pjavaclient.model.Merchant;
import org.maviance.s3pjavaclient.model.Ping;
import org.maviance.s3pjavaclient.model.Service;

import java.util.List;

/**
 * Show Some general data such as: The company and agent information, API Version.
 */
class MasterData {
    private static String separator = "  --  ";
    //set the version of
    public static void main(String[] args) {
        ApiClient apiClient = new ApiClient(AccessDetails.BASE_URL, AccessDetails.ACCESS_TOKEN, AccessDetails.ACCESS_SECRET);
        ChecksApi checksApi = new ChecksApi(apiClient);
        MasterdataApi masterdataApi = new MasterdataApi(apiClient);

        try {
            Ping ping = checksApi.pingGet();
            System.out.println("API Version: " + ping.getVersion());

            Account account = masterdataApi.accountGet();
            System.out.println("Company:       " + account.getCompanyName());
            System.out.println("Agent:         " + account.getAgentName());
            System.out.println("Agent Balance: " + account.getBalance());

            System.out.println("\n============================== MERCHANTS ============================");
            List<Merchant> merchants = masterdataApi.merchantGet();
            merchants.forEach(item -> System.out.println("Code: " + item.getMerchant() + separator + "Name: " + item.getName()));

            System.out.println("\n============================== SERVICES ============================");
            List<Service> services = masterdataApi.serviceGet();
            services.forEach(item -> System.out.println("ID: " + item.getServiceid() + separator + "Title: " + item.getTitle() + separator + "Type: " + item.getType()));

        } catch (ApiException e) {
            System.out.println("An error occurred: \n");
            System.out.println(e.getResponseBody());
        }

    }
}
