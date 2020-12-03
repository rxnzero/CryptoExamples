package com.dhlee.example;

import java.security.SignatureException;

import javax.crypto.Mac;

import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;

public class Signature

{

	private final static String HMAC_SHA1_ALGORITHM = "HmacSHA1";

	/**
	 * 
	 * Computes RFC 2104-compliant HMAC signature.
	 * 
	 * @param data The data to be signed.
	 * 
	 * @param key  The signing key.
	 * 
	 * @return The Base64-encoded RFC 2104-compliant HMAC signature.
	 * 
	 * @throws java.security.SignatureException when signature generation fails
	 * 
	 */
	public static String calculateRFC2104HMAC(String data, byte[] key) throws java.security.SignatureException {
		String result;
		try {
			// get an hmac_sha1 key from the raw key bytes
			SecretKeySpec signingKey = new SecretKeySpec(key, HMAC_SHA1_ALGORITHM);
			// get an hmac_sha1 Mac instance and initialize with the signing key
			Mac mac = Mac.getInstance(HMAC_SHA1_ALGORITHM);
			mac.init(signingKey);
			byte[] rawHmac = mac.doFinal(data.getBytes());
			result = Base64.encodeBase64String(rawHmac);
		} catch (Exception e) {
			throw new SignatureException("Failed to generate HMAC : " + e.getMessage());
		}
		return result;
	}

	public static void main(String[] args) throws Exception {
		byte[] key = { 0x21, 0x12, 0x15, 0x33, 0x66, 0x22, 0x45, 0x22, 0x42, 0x53, 0x21, 0x11, 0x01, 0x00, 0x22, 0x63 };
		String data = Signature.calculateRFC2104HMAC("가나다라마바사아아아아앙아아아아아아아아앙123!@#$%#^&*", key);
		System.out.println(data);
	}
}
