package com.dhlee.example;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

public class PasswordUtils {

    // ��Ʈ ����
    public static String generateSalt() {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        return Base64.getEncoder().encodeToString(salt);
//    	return "llHy6MNEAIlzs/FHa0y9tg==";
    }

    // ��й�ȣ �ؽ�
    public static String hashPassword(String password, String salt) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        md.update(Base64.getDecoder().decode(salt));
        byte[] hashedPassword = md.digest(password.getBytes());
        return Base64.getEncoder().encodeToString(hashedPassword);
    }

    // ��й�ȣ ����
    public static boolean verifyPassword(String password, String salt, String storedHash) throws NoSuchAlgorithmException {
        String hashedPassword = hashPassword(password, salt);
        return storedHash.equals(hashedPassword);
    }

    public static void main(String[] args) {
        try {
            String password = "mySecurePassword";
            String salt = generateSalt();
            String hashedPassword = hashPassword(password, salt);

            System.out.println("Salt: " + salt);
            System.out.println("Hashed Password: " + hashedPassword);

            // ��й�ȣ ���� �׽�Ʈ
            boolean isPasswordValid = verifyPassword("mySecurePassword", salt, hashedPassword);
            System.out.println("Is password valid? " + isPasswordValid);

            // �߸��� ��й�ȣ ���� �׽�Ʈ
            boolean isWrongPasswordValid = verifyPassword("wrongPassword", salt, hashedPassword);
            System.out.println("Is wrong password valid? " + isWrongPasswordValid);

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }
}
