package com.dhlee.hmac;

public class HmacTest {

	public HmacTest() {
		
	}
	
	public static void main(String[] argv) {
		test("testkey1", "data 입니다.");
		test("testkey1", "data 입니다");
		test("testkey2", "data 입니다.");
		test("testkey2", "data 입니다");
	}
	
	public static void test(String key, String message) {
		String hash;
		try {
			hash = Hmac.generate(message, key);
			System.out.println("Hash size = " + hash.length());
			System.out.println("Hash value [" + hash+"]");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
