package org.maviance.s3pjavaclient.examples;

import org.maviance.s3pjavaclient.ApiClient;
import org.maviance.s3pjavaclient.ApiException;
import org.maviance.s3pjavaclient.api.AccountApi;
import org.maviance.s3pjavaclient.api.HealthcheckApi;
import org.maviance.s3pjavaclient.api.InitiateApi;
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
        MasterdataApi masterdataApi = new MasterdataApi(apiClient);
        HealthcheckApi healthcheckApi = new HealthcheckApi(apiClient);
        InitiateApi initiateApi = new InitiateApi(apiClient);
        AccountApi accountApi = new AccountApi(apiClient);

        try {
            Ping ping = healthcheckApi.pingGet(AccessDetails.VERSION);
            System.out.println("API Version: " + ping.getVersion());

            Account account = accountApi.accountGet(AccessDetails.VERSION);
            System.out.println("Company:       " + account.getCompanyName());
            System.out.println("Agent:         " + account.getAgentName());
            System.out.println("Agent Balance: " + account.getBalance());

            System.out.println("\n============================== MERCHANTS ============================");
            List<Merchant> merchants = masterdataApi.merchantGet(AccessDetails.VERSION);
            merchants.forEach(item -> System.out.println("Code: " + item.getMerchant() + separator + "Name: " + item.getName()));

            System.out.println("\n============================== SERVICES ============================");
            List<Service> services = masterdataApi.serviceGet(AccessDetails.VERSION);
            services.forEach(item -> System.out.println("ID: " + item.getServiceid() + separator + "Title: " + item.getTitle() + separator + "Type: " + item.getType()));

        } catch (ApiException e) {
            System.out.println("An error occurred: \n");
            System.out.println(e.getResponseBody());
        }

    }
}
