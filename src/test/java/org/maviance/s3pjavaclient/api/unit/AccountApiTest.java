/*
 * Smobilpay S3P API
 * Smobilpay Third Party API FOR PAYMENT COLLECTIONS
 *
 * OpenAPI spec version: 3.0.3
 * 
 *
 * NOTE: This class is auto generated by the swagger code generator program.
 * https://github.com/swagger-api/swagger-codegen.git
 * Do not edit the class manually.
 */

package org.maviance.s3pjavaclient.api.unit;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.maviance.s3pjavaclient.ApiClient;
import org.maviance.s3pjavaclient.ApiException;
import org.maviance.s3pjavaclient.ApiResponse;
import org.maviance.s3pjavaclient.api.AccountApi;
import org.maviance.s3pjavaclient.model.Account;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;
import static org.mockito.ArgumentMatchers.*;

/**
 * API tests for AccountApi against mocks
 */
@RunWith(MockitoJUnitRunner.class)
public class AccountApiTest {

    @Mock
    private ApiClient apiClient;

    @InjectMocks
    private AccountApi api;

    @Before
    public void setUp() throws Exception {
        Mockito.when(apiClient.execute(any(), any())).thenAnswer((Answer<ApiResponse<Account>>) invocation -> {
            Account data = new Account();
            data.setAgentName("Account agent name");
            data.setBalance(1000F);
            ApiResponse<Account> response =  new ApiResponse<Account>(200, null, data);
            return response;
        });
    }

    /**
     * Retrieve account information and remaining account balance
     *
     * This endpoint returns the user’s account information – most notably the current balance of the user.
     * Calling this service before and after **each** collection in order to retrieve the current limits and/or balance is **highly discouraged**.
     * The recommended approach is as follows:
     * 1. Only a successful payment collection transaction will affect the account balance.
     * The corresponding endpoint will also return the current account balance after the collection in its result payload.
     * 2. For unsuccessful payment transactions, the account balance will not be affected.
     * The error message returns a verbose message as to why the transaction failed.
     * There is no need to recheck the account after each error.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void accountGetTest() throws ApiException {
        String xApiVersion = "1.0.0";
        Account account = api.accountGet(xApiVersion);
        Assert.assertNotNull("Account response null", account);
        Assert.assertEquals("Account agent name", account.getAgentName());
        Assert.assertEquals(1000F, account.getBalance(), 0);
    }
}