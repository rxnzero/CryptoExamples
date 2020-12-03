package com.dhlee.hmac;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.InvalidKeyException;
 
public class Hmac {
    // hash �˰��� ����
    private static final String ALGOLISM = "HmacSHA256";
    
    public static String hget(String message, String key) {
        try {
            // hash �˰���� ��ȣȭ key ����
            Mac hasher = Mac.getInstance(ALGOLISM);
            hasher.init(new SecretKeySpec(key.getBytes(), ALGOLISM));
            // messages�� ��ȣȭ ���� �� byte �迭 ������ ��� ����
            byte[] hash = hasher.doFinal(message.getBytes());
            return byteToString(hash);
        }
        catch (NoSuchAlgorithmException e){
            e.printStackTrace();
        }
        catch (InvalidKeyException e){
            e.printStackTrace();
        }
        return "";
    }
 
    // byte[]�� ���� 16���� ������ ���ڷ� ��ȯ�ϴ� �Լ�
    private static String byteToString(byte[] hash) {
        StringBuffer buffer = new StringBuffer();
        for (int i = 0; i < hash.length; i++) {
            int d = hash[i];
            d += (d < 0)? 256 : 0;
            if (d < 16) {
                buffer.append("0");
            }
            buffer.append(Integer.toString(d, 16));
        }
        return buffer.toString();
    }
}
