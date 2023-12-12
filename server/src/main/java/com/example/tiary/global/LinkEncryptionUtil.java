package com.example.tiary.global;

import java.util.Base64;

public class LinkEncryptionUtil {

	public static String generateEncodedKey(String key) {
		byte[] encodedBytes = Base64.getEncoder().encode(key.getBytes());
		return new String(encodedBytes);
	}

	public static String decodeKey(String encodedLink) {
		byte[] decodedBytes = Base64.getDecoder().decode(encodedLink.getBytes());
		return new String(decodedBytes);
	}
}
