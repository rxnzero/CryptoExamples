package com.dhlee.example;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

public class PasswordUtils {

    // 솔트 생성
    public static String generateSalt() {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        return Base64.getEncoder().encodeToString(salt);
//    	return "llHy6MNEAIlzs/FHa0y9tg==";
    }

    // 비밀번호 해싱
    public static String hashPassword(String password, String salt) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        md.update(Base64.getDecoder().decode(salt));
        byte[] hashedPassword = md.digest(password.getBytes());
        return Base64.getEncoder().encodeToString(hashedPassword);
    }

    // 비밀번호 검증
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

            // 비밀번호 검증 테스트
            boolean isPasswordValid = verifyPassword("mySecurePassword", salt, hashedPassword);
            System.out.println("Is password valid? " + isPasswordValid);

            // 잘못된 비밀번호 검증 테스트
            boolean isWrongPasswordValid = verifyPassword("wrongPassword", salt, hashedPassword);
            System.out.println("Is wrong password valid? " + isWrongPasswordValid);

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }
}
