package com.dhlee.example;

import java.security.NoSuchAlgorithmException;

import egovframework.rte.fdl.cryptography.impl.ARIACipher;

public class ARIATest {

	public ARIATest() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) {
		String password = "1234567890";
		String data = "hello ARIA - 테스트입니다.";
		
		System.out.println("# password = " + password);
		
		try {
			password = HashGenerator.getHash(password, "SHA-384");
			System.out.println("# SHA-384(password) = " + password);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		
		System.out.println("# data\n" + data);
		
		ARIACipher cipher = new ARIACipher();
		cipher.setPassword(password);
		byte[] encryptedData = cipher.encrypt(data.getBytes());
		
		String encHexa = bytes2Hex(encryptedData);
		System.out.println("# encryptedData\n" + encHexa);
		byte[] dec = cipher.decrypt(hex2Bytes(encHexa));
		
		System.out.println("# dec\n" + new String(dec));
	}
	
	public static byte[] hex2Bytes(String hexStr) {
        byte retByte[] = new byte[hexStr.length() / 2];
        for(int j = 0; j < retByte.length; j++)
            retByte[j] = (byte)Integer.parseInt(hexStr.substring(2 * j, 2 * j + 2), 16);
        return retByte;        
    }


	public static String bytes2Hex(byte inBytes[]) {
        String s = "";
        for(int i = 0; i < inBytes.length; i++)
            s = s + Integer.toHexString((inBytes[i] & 0xf0) >> 4) + Integer.toHexString(inBytes[i] & 0xf);

        return s.toUpperCase();
    }

}
