package com.dhlee.hmac;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
 
public class Hmac {
    private static final String ALGORISM = "HmacSHA256";
    
    public static String generate(String message, String key) throws Exception {
        try {
            Mac hasher = Mac.getInstance(ALGORISM);
            SecretKeySpec secretSpec = new SecretKeySpec(key.getBytes("UTF-8"), ALGORISM);
            hasher.init(secretSpec);
            
            String hashBody = key + message;
            
            byte[] hash = hasher.doFinal(hashBody.getBytes("UTF-8"));
            return byteToString(hash);
        }
        catch (NoSuchAlgorithmException e){
            e.printStackTrace();
            throw e;
        }
        catch (InvalidKeyException e){
            e.printStackTrace();
            throw e;
        }
        catch (Exception e){
            e.printStackTrace();
            throw e;
        }
    }
 
    private static String byteToString(byte[] bytes) {
    	return byteToString2(bytes);
    }
    
    private static String byteToString1(byte[] bytes) {
        StringBuffer buffer = new StringBuffer();
        for (int i = 0; i < bytes.length; i++) {
            int d = bytes[i];
            d += (d < 0)? 256 : 0;
            if (d < 16) {
                buffer.append("0");
            }
            buffer.append(Integer.toString(d, 16));
        }
        return buffer.toString();
    }
    
    private static String byteToString2(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for(byte b: bytes) {
        	sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }
}
