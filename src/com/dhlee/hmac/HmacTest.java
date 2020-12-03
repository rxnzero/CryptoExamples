package com.dhlee.hmac;

public class HmacTest {

	public HmacTest() {
		
	}
	
	public static void main(String[] argv) {
		String key = "testkey";
		String hmacData = "data �Դϴ�.";
		String hash;
		try {
			hash = Hmac.generate(hmacData, key);
			System.out.println("Hash size = " + hash.length());
			System.out.println("Hash value [" + hash+"]");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
