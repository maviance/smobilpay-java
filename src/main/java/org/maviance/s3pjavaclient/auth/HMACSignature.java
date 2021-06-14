package org.maviance.s3pjavaclient.auth;

import org.maviance.s3pjavaclient.Pair;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.net.URLEncoder;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Valdese Kamdem
 * Updated By Florian Lowe
 * Email: <a href="mailto:valdese.kamdem@maviance.com">valdese.kamdem@maviance.com</a>
 * Date: 3/21/2018
 */
public class HMACSignature {

    private static final String HMAC_SHA1_ALGORITHM = "HmacSHA1";

    private final String method;

    private final String baseUrl;

    private final List<Pair> params;


    public HMACSignature(String method, String baseUrl, List<Pair> params) {
        this.method = method;
        this.baseUrl = baseUrl;
        this.params = params;
    }

    /**
     * This method generates the signature based on given parameters.
     *
     * @param accessSecret access secret used to encrypt the information
     * @return the signature result
     */
    public String generate(String accessSecret) {
        return calculateHMACInBase64(getBaseString(), accessSecret);
    }

    /**
     * Assembling the base string
     *
     * @return base string
     */
    public String getBaseString() {
        String glue = "&";
        return method.trim().toUpperCase() + glue + percentageEncode(baseUrl.trim()) + glue + percentageEncode(getParameterString());
    }

    /**
     * Prepares a string to be signed
     *
     * @return raw string
     */
    public String getParameterString() {
        List<String> paramsList = params.stream().sorted(Comparator.comparing(Pair::getName))
                .map(item -> item.getName().trim() + "=" + item.getValue().trim()).collect(Collectors.toList());
        return String.join("&", paramsList).trim();
    }

    //region ======================== HELPERS METHODS =================================

    private static String removeSpaces(String string) {
        StringBuilder stringBuilder = new StringBuilder();
        for (char c : string.toCharArray()) {
            if (c != ' ') {
                stringBuilder.append(c);
            }
        }
        return stringBuilder.toString();
    }

    public static List<Pair> buildSignatureData(List<Pair> queryParams, List<Pair> collectionQueryParams, Object body) {
        List<Pair> data = new ArrayList<>();
        data.addAll(queryParams);
        data.addAll(collectionQueryParams);
        if (body != null) data.addAll(bodyConverter(body));
        //if (formValues != null) formValues.forEach((key, value) -> data.add(new Pair(key, value.toString())));
        return data;
    }

    public static List<Pair> bodyConverter(Object body) {
        List<Pair> data = new ArrayList<>();
        for (Field field : body.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            try {
                Object value = field.get(body);
                if (value != null) {
                    data.add(new Pair(field.getName(), value.toString()));
                }
            } catch (IllegalAccessException e) {
                throw new RuntimeException("Error occurred while building the request signature data");
            }
        }
        return data;
    }

    /**
     * Encode a query string (application/x-www-form-urlencoded)
     *
     * @param s string to encode
     * @return the percentage encode value of s
     */
    private static String percentageEncode(String s) {
        try {
            return URLEncoder.encode(s, "UTF-8").replace("+","%20");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Encode a given string to with HMAC-SHA1 method and the given key.
     *
     * @param data The data to encode
     * @param key  The key to encode with
     * @return The encoded string result
     */
    private static String calculateHMACInBase64(String data, String key) {
        SecretKeySpec signingKey = new SecretKeySpec(key.getBytes(), HMAC_SHA1_ALGORITHM);
        Mac mac;
        try {
            mac = Mac.getInstance(HMAC_SHA1_ALGORITHM);
            mac.init(signingKey);
        } catch (NoSuchAlgorithmException | InvalidKeyException e) {
            throw new RuntimeException();
        }
        byte[] bytes = mac.doFinal(data.getBytes());
        return Base64.getEncoder().encodeToString(bytes);
    }

    //endregion
}
