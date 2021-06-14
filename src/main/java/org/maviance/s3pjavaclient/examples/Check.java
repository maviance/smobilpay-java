package org.maviance.s3pjavaclient.examples;

import org.maviance.s3pjavaclient.ApiClient;
import org.maviance.s3pjavaclient.ApiException;
import org.maviance.s3pjavaclient.api.ChecksApi;
import org.maviance.s3pjavaclient.model.Ping;


class Check {

    public static void main(String[] args) {
        ApiClient apiClient = new ApiClient(AccessDetails.BASE_URL, AccessDetails.ACCESS_TOKEN, AccessDetails.ACCESS_SECRET);
        ChecksApi checksApi = new ChecksApi(apiClient);

        try {
            Ping ping = checksApi.pingGet();
            System.out.println(ping);
        } catch (ApiException e) {
            System.out.println("An error occurred: \n");
            System.out.println(e.getResponseBody());
        }
    }
}