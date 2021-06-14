package org.maviance.s3pjavaclient;

/**
 * Created by Valdese Kamdem
 * Email: <a href="mailto:valdese.kamdem@maviance.com">valdese.kamdem@maviance.com</a>
 * Date: 3/21/2018
 */
public class S3PAuthException extends RuntimeException {
    public S3PAuthException() {
        super("No S3PAuth authentication configured!");
    }
}
