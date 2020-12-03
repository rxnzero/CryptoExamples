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
	    // 70000�� �ؽ��Ͽ� 256 bit ������ Ű�� �����.
	    PBEKeySpec spec = new PBEKeySpec(key.toCharArray(), saltBytes, 70000, 256);

	    SecretKey secretKey = factory.generateSecret(spec);
	    SecretKeySpec secret = new SecretKeySpec(secretKey.getEncoded(), "AES");

	    // �˰���/���/�е�
	    // CBC : Cipher Block Chaining Mode
	    Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
	    cipher.init(Cipher.ENCRYPT_MODE, secret);
	    AlgorithmParameters params = cipher.getParameters();
	    // Initial Vector(1�ܰ� ��ȣȭ ��Ͽ�)

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
//	JDK 8u161 ���� ������ ������̶�� AES-256 ��ȣȭ �۾��߿� ���� ���ܰ� �߻��� ���Դϴ�.
//	java.security.InvalidKeyException: Illegal key size or default parameters
//	���� ���������� Ű ���̿� ������ �ɷ� �־����ϴ�. JDK 8u161 ������Ʈ ������ ���� ���� ���� �ʴ°��� �⺻������ �����Ǿ� �ֽ��ϴ�.
//	���� ������ JDK �� ����� ���� AES-256�� ����ϱ� ���ؼ� jce policy ������ ��ġ�ؾ� �մϴ�.
//	����Ŭ ����Ʈ���� Java SE �ٿ�ε� ���������� Java Cryptography Extension (JCE) Unlimited Strength Jurisdiction Policy ���ϵ��� �ٿ�ε� �޾Ƽ� jre/lib/security/ �Ʒ��� local_policy.jar, US_export_policy.jar ������ ��ü�� �ָ� �˴ϴ�.
}
