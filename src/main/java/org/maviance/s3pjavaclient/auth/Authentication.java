/*
 * Smobilpay S3P API STANDARD
 * Smobilpay Third Party STANDARD API FOR PAYMENT COLLECTIONS
 *
 * OpenAPI spec version: 3.0.0
 * 
 *
 * NOTE: This class is auto generated by the swagger code generator program.
 * https://github.com/swagger-api/swagger-codegen.git
 * Do not edit the class manually.
 */

package org.maviance.s3pjavaclient.auth;

import org.maviance.s3pjavaclient.Pair;

import java.util.List;
import java.util.Map;

public interface Authentication {
	/**
	 * Apply authentication settings to header and query params.
	 *
	 * @param queryParams  List of query parameters
	 * @param headerParams Map of header parameters
	 */
	void applyToParams(List<Pair> queryParams, Map<String, String> headerParams);
}
