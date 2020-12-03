package com.dhlee.example;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class HashGenerator {
	
	  public static void main (String[] args) throws NoSuchAlgorithmException {
	    
		  String code = "testcode";
	    String md2 = getHash(code, "MD2");
	    String md5 = getHash(code, "MD5");
	    String sha1 = getHash(code, "SHA1");
	    String sha256 = getHash(code, "SHA-256");
	    String sha384 = getHash(code, "SHA-384");
	    String sha512 = getHash(code, "SHA-512");

	    // ¡°MD2¡È, ¡°MD5¡È, ¡°SHA1¡È, ¡°SHA-256¡È, ¡°SHA-384¡È, ¡°SHA-512¡È
	    System.out.println("MD2     : [" + md2 + "](" + md2.length() + ")");
	    System.out.println("MD5     : [" + md5 + "](" + md5.length() + ")");
	    System.out.println("SHA1    : [" + sha1 + "](" + sha1.length() + ")");
	    System.out.println("SHA-256 : [" + sha256 + "](" + sha256.length() + ")");
	    System.out.println("SHA-384 : [" + sha384 + "](" + sha384.length() + ")");
	    System.out.println("SHA-512 : [" + sha512 + "](" + sha512.length() + ")");
	  }

	  public static String getHash(String message, String algorithm)
	    throws NoSuchAlgorithmException {
	    
	    try {
	      byte[] buffer = message.getBytes();
	      MessageDigest md = MessageDigest.getInstance(algorithm);
	      md.update(buffer);
	      byte[] digest = md.digest();
	      String hex = "";
	      
	      for(int i = 0 ; i < digest.length ; i++) {
	        int b = digest[i] & 0xff;
	        if (Integer.toHexString(b).length() == 1) hex = hex + "0";
	        hex  = hex + Integer.toHexString(b);
	      }
	      
	      return hex;
	    } catch(NoSuchAlgorithmException e) {
	      e.printStackTrace();
	    }
	    
	    return null;
	  }
	}