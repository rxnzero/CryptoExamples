package com.dhlee.example;

import java.nio.ByteBuffer;
import java.security.AlgorithmParameters;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

public class AES256 {

	public AES256() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) throws Exception {
        String plainText = "Hello, World!";

        String key = "secret key";
        System.out.println("MD5 : " + plainText + " - " + AES256.md5(plainText));
        System.out.println("SHA-256 : " + plainText + " - " + AES256.sha256(plainText));

        String encrypted = AES256.encryptAES256("Hello, World!", key);
        System.out.println("AES-256 : enc - " + encrypted);
        System.out.println("AES-256 : dec - " + AES256.decryptAES256(encrypted, key));

	}
	
	public static String md5(String msg) throws NoSuchAlgorithmException {
	    MessageDigest md = MessageDigest.getInstance("MD5");
	    md.update(msg.getBytes());
	    return AES256.byteToHexString(md.digest());
	}

	public static String byteToHexString(byte[] data) {
	    StringBuilder sb = new StringBuilder();
	    for(byte b : data) {
	        sb.append(Integer.toString((b & 0xff) + 0x100, 16).substring(1));
	    }
	    return sb.toString();
	}

	
	public static String sha256(String msg)  throws NoSuchAlgorithmException {
	    MessageDigest md = MessageDigest.getInstance("SHA-256");
	    md.update(msg.getBytes());
	    return AES256.byteToHexString(md.digest());
	}

	public static String encryptAES256(String msg, String key) throws Exception {
	    SecureRandom random = new SecureRandom();
	    byte bytes[] = new byte[20];
	    random.nextBytes(bytes);
	    byte[] saltBytes = bytes;

	    // Password-Based Key Derivation function 2
	    SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
	    // 70000번 해시하여 256 bit 길이의 키를 만든다.
	    PBEKeySpec spec = new PBEKeySpec(key.toCharArray(), saltBytes, 70000, 256);

	    SecretKey secretKey = factory.generateSecret(spec);
	    SecretKeySpec secret = new SecretKeySpec(secretKey.getEncoded(), "AES");

	    // 알고리즘/모드/패딩
	    // CBC : Cipher Block Chaining Mode
	    Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
	    cipher.init(Cipher.ENCRYPT_MODE, secret);
	    AlgorithmParameters params = cipher.getParameters();
	    // Initial Vector(1단계 암호화 블록용)

	    byte[] ivBytes = params.getParameterSpec(IvParameterSpec.class).getIV();
	    byte[] encryptedTextBytes = cipher.doFinal(msg.getBytes("UTF-8"));
	    byte[] buffer = new byte[saltBytes.length + ivBytes.length + encryptedTextBytes.length];
	    System.arraycopy(saltBytes, 0, buffer, 0, saltBytes.length);
	    System.arraycopy(ivBytes, 0, buffer, saltBytes.length, ivBytes.length);
	    System.arraycopy(encryptedTextBytes, 0, buffer, saltBytes.length + ivBytes.length, encryptedTextBytes.length);
	    return Base64.getEncoder().encodeToString(buffer);
	}
	
	public static String decryptAES256(String msg, String key) throws Exception {

	    Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");

	    ByteBuffer buffer = ByteBuffer.wrap(Base64.getDecoder().decode(msg));

	    byte[] saltBytes = new byte[20];
	    buffer.get(saltBytes, 0, saltBytes.length);
	    byte[] ivBytes = new byte[cipher.getBlockSize()];
	    buffer.get(ivBytes, 0, ivBytes.length);
	    byte[] encryoptedTextBytes = new byte[buffer.capacity() - saltBytes.length - ivBytes.length];
	    buffer.get(encryoptedTextBytes);

	    SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
	    PBEKeySpec spec = new PBEKeySpec(key.toCharArray(), saltBytes, 70000, 256);
	    SecretKey secretKey = factory.generateSecret(spec);
	    SecretKeySpec secret = new SecretKeySpec(secretKey.getEncoded(), "AES");
	    cipher.init(Cipher.DECRYPT_MODE, secret, new IvParameterSpec(ivBytes));
	    byte[] decryptedTextBytes = cipher.doFinal(encryoptedTextBytes);
	    return new String(decryptedTextBytes);

	}
//	JDK 8u161 이전 버전을 사용중이라면 AES-256 암호화 작업중에 다음 예외가 발생할 것입니다.
//	java.security.InvalidKeyException: Illegal key size or default parameters
//	이전 버전에서는 키 길이에 제한이 걸려 있었습니다. JDK 8u161 업데이트 릴리즈 부터 제한 하지 않는것이 기본값으로 설정되어 있습니다.
//	이전 버전의 JDK 를 사용할 때는 AES-256을 사용하기 위해서 jce policy 파일을 패치해야 합니다.
//	오라클 사이트에서 Java SE 다운로드 페이지에서 Java Cryptography Extension (JCE) Unlimited Strength Jurisdiction Policy 파일들을 다운로드 받아서 jre/lib/security/ 아래에 local_policy.jar, US_export_policy.jar 파일을 교체해 주면 됩니다.
}
