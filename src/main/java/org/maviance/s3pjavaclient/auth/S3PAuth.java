package org.maviance.s3pjavaclient.auth;

import org.maviance.s3pjavaclient.Pair;

import java.util.List;
import java.util.Map;

/**
 * Created by Valdese Kamdem
 * Email: <a href="mailto:valdese.kamdem@maviance.com">valdese.kamdem@maviance.com</a>
 * Date: 3/21/2018
 */
public class S3PAuth implements Authentication {
    /**
     * A string with encoded and comma separated authorization header parameters.
     */
    private String s3pAuthorizationString;

    public String getS3pAuthorizationString() {
        return s3pAuthorizationString;
    }

    public void setS3pAuthorizationString(String s3pAuthorizationString) {
        this.s3pAuthorizationString = s3pAuthorizationString;
    }

    @Override
    public void applyToParams(List<Pair> queryParams, Map<String, String> headerParams) {
        if (s3pAuthorizationString != null) {
            headerParams.put("Authorization", s3pAuthorizationString);
        }
    }
}
