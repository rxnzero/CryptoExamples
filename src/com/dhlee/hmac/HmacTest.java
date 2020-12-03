package com.dhlee.hmac;

public class HmacTest {

	public HmacTest() {
		
	}
	
	public static void main(String[] argv) {
		String key = "testkey";
		String hmacData = "data ¿‘¥œ¥Ÿ.";
		String hash = Hmac.hget(hmacData, key);
		System.out.println("Hash size = " + hash.length());
		System.out.println("Hash value [" + hash+"]");
	}
}
