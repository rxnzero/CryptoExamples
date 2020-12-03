package com.dhlee.example;

import egovframework.rte.fdl.cryptography.impl.ARIACipher;

public class ARIATest {

	public ARIATest() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) {
		String password = "1234567890";
		String data = "hello ARIA - 테스트입니다.";
		
		System.out.println("# data\n" + data);
		
		ARIACipher cipher = new ARIACipher();
		cipher.setPassword(password);
		byte[] encryptedData = cipher.encrypt(data.getBytes());
		
		System.out.println("# encryptedData\n" + new String(encryptedData));
		byte[] dec = cipher.decrypt(encryptedData);
		
		System.out.println("# dec\n" + new String(dec));
	}

}
