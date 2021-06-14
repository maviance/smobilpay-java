package org.maviance.s3pjavaclient;

import org.junit.Test;
import org.maviance.s3pjavaclient.auth.HMACSignature;
import org.maviance.s3pjavaclient.model.QuoteRequest;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;

/**
 * Created by Valdese Kamdem
 * Email: <a href="mailto:valdese.kamdem@maviance.com">valdese.kamdem@maviance.com</a>
 * Date: 3/22/2018
 */
public class HMACSignatureTest {

    private String authTokenKey = "s3pAuth_token", authNonceKey = "s3pAuth_nonce",
            authSignatureMethodKey = "s3pAuth_signature_method", authTimeStampKey = "s3pAuth_timestamp";

    @Test
    public void getParameterStringTestCase1() {
        String expectedStr = "amount=1000.00&payItemId=SPAY-DEV-958-AES-100013333-10010&s3pAuth_nonce=634968823463411609&s3pAuth_signature_method=HMAC-SHA1&s3pAuth_timestamp=1361281946&s3pAuth_token=xvz1evFS4wEEPTGEFPHBog";

        List<Pair> data = new ArrayList<>();
        String method = "POST";
        String url = "https://dev.smobilpay.com/s3p/v2/quotestd";
        data.add(new Pair(authNonceKey, "634968823463411609"));
        data.add(new Pair(authSignatureMethodKey, "HMAC-SHA1"));
        data.add(new Pair(authTimeStampKey, "1361281946"));
        data.add(new Pair(authTokenKey, "xvz1evFS4wEEPTGEFPHBog"));
        data.add(new Pair("payItemId", "SPAY-DEV-958-AES-100013333-10010"));
        data.add(new Pair("amount", "1000.00"));

        HMACSignature hmacSignature = new HMACSignature(method, url, data);
        String parameterString = hmacSignature.getParameterString();
        assertEquals(expectedStr, parameterString);
    }

    @Test
    public void getParameterStringTestCase() {
        String expectedStr = "merchant=TESTMERC&s3pAuth_nonce=634968823463411609&s3pAuth_signature_method=HMAC-SHA1&s3pAuth_timestamp=1361281946&s3pAuth_token=xvz1evFS4wEEPTGEFPHBog&serviceNumber=TestId&serviceid=99999";

        List<Pair> data = new ArrayList<>();
        String method = "GET";
        String url = "https://dev.smobilpay.com/s3p/v2/bill";
        data.add(new Pair(authNonceKey, "634968823463411609"));
        data.add(new Pair(authSignatureMethodKey, "HMAC-SHA1"));
        data.add(new Pair(authTimeStampKey, "1361281946"));
        data.add(new Pair(authTokenKey, "xvz1evFS4wEEPTGEFPHBog"));
        data.add(new Pair("merchant", "TESTMERC"));
        data.add(new Pair("serviceNumber", "TestId"));
        data.add(new Pair("serviceid", "99999"));

        HMACSignature hmacSignature = new HMACSignature(method, url, data);
        String parameterString = hmacSignature.getParameterString();
        assertEquals(expectedStr, parameterString);
    }

    @Test
    public void getBaseStringTestCase1() {
        String expectedStr = "POST&https%3A%2F%2Fdev.smobilpay.com%2Fs3p%2Fv2%2Fquotestd&amount%3D1000.00%26payItemId%3DSPAY-DEV-958-AES-100013333-10010%26s3pAuth_nonce%3D634968823463411609%26s3pAuth_signature_method%3DHMAC-SHA1%26s3pAuth_timestamp%3D1361281946%26s3pAuth_token%3Dxvz1evFS4wEEPTGEFPHBog";

        List<Pair> data = new ArrayList<>();
        String method = "POST";
        String url = "https://dev.smobilpay.com/s3p/v2/quotestd";
        data.add(new Pair(authNonceKey, "634968823463411609"));
        data.add(new Pair(authSignatureMethodKey, "HMAC-SHA1"));
        data.add(new Pair(authTimeStampKey, "1361281946"));
        data.add(new Pair(authTokenKey, "xvz1evFS4wEEPTGEFPHBog"));
        data.add(new Pair("payItemId", "SPAY-DEV-958-AES-100013333-10010"));
        data.add(new Pair("amount", "1000.00"));

        HMACSignature hmacSignature = new HMACSignature(method, url, data);
        String baseString = hmacSignature.getBaseString();
        assertEquals(expectedStr, baseString);
    }

    @Test
    public void getBaseStringTestCase2() {
        String expectedStr = "GET&https%3A%2F%2Fdev.smobilpay.com%2Fs3p%2Fv2%2Fbill&merchant%3DTESTMERC%26s3pAuth_nonce%3D634968823463411609%26s3pAuth_signature_method%3DHMAC-SHA1%26s3pAuth_timestamp%3D1361281946%26s3pAuth_token%3Dxvz1evFS4wEEPTGEFPHBog%26serviceNumber%3DTestId%26serviceid%3D99999";

        List<Pair> data = new ArrayList<>();
        String method = "GET";
        String url = "https://dev.smobilpay.com/s3p/v2/bill";
        data.add(new Pair(authNonceKey, "634968823463411609"));
        data.add(new Pair(authSignatureMethodKey, "HMAC-SHA1"));
        data.add(new Pair(authTimeStampKey, "1361281946"));
        data.add(new Pair(authTokenKey, "xvz1evFS4wEEPTGEFPHBog"));
        data.add(new Pair("merchant", "TESTMERC"));
        data.add(new Pair("serviceNumber", "TestId"));
        data.add(new Pair("serviceid", "99999"));

        HMACSignature hmacSignature = new HMACSignature(method, url, data);
        String baseString = hmacSignature.getBaseString();
        assertEquals(expectedStr, baseString);
    }

    @Test
    public void generateTestCase1() {
        String expectedStr = "jwa3aeTy5XTEuZwlsN2umZvyfBc=";

        List<Pair> data = new ArrayList<>();
        String method = "POST";
        String url = "https://dev.smobilpay.com/s3p/v2/quotestd";
        data.add(new Pair(authNonceKey, "634968823463411609"));
        data.add(new Pair(authSignatureMethodKey, "HMAC-SHA1"));
        data.add(new Pair(authTimeStampKey, "1361281946"));
        data.add(new Pair(authTokenKey, "xvz1evFS4wEEPTGEFPHBog"));
        data.add(new Pair("payItemId", "SPAY-DEV-958-AES-100013333-10010"));
        data.add(new Pair("amount", "1000.00"));

        HMACSignature hmacSignature = new HMACSignature(method, url, data);
        String signature = hmacSignature.generate("MySecretKey");
        assertEquals(expectedStr, signature);
    }

    @Test
    public void generateTestCase2() {
        String expectedStr = "zs4pMNkZqxIXVNNFcH5rxbUZIdw=";

        List<Pair> data = new ArrayList<>();
        String method = "GET";
        String url = "https://dev.smobilpay.com/s3p/v2/bill";
        data.add(new Pair(authNonceKey, "634968823463411609"));
        data.add(new Pair(authSignatureMethodKey, "HMAC-SHA1"));
        data.add(new Pair(authTimeStampKey, "1361281946"));
        data.add(new Pair(authTokenKey, "xvz1evFS4wEEPTGEFPHBog"));
        data.add(new Pair("merchant", "TESTMERC"));
        data.add(new Pair("serviceNumber", "TestId"));
        data.add(new Pair("serviceid", "99999"));

        HMACSignature hmacSignature = new HMACSignature(method, url, data);
        String signature = hmacSignature.generate("MySecretKey");
        assertEquals(expectedStr, signature);
    }

    @Test
    public void buildSignatureData() {
        QuoteRequest request = new QuoteRequest();
        request.setPayItemId("S-611-951-CMMTN-20051-CM_MTN_VTU_250-1");
        request.setAmount(100.0f);
        List<Pair> pairs = HMACSignature.buildSignatureData(Collections.emptyList(), Collections.emptyList(), request);
        System.out.println(pairs.toString());

        // Apply filter to remove the synthetic field added by ($jacocoData) added by JACOCO while running tests
        pairs = pairs.stream().filter(item -> !item.getName().startsWith("$")).collect(Collectors.toList());
        assertEquals(2, pairs.size());
    }
}
