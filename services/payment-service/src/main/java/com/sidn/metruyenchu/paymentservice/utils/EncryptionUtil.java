package com.sidn.metruyenchu.paymentservice.utils;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.util.Base64;

public class EncryptionUtil {


    public static String encryptChapterContent(String filePath, String key) throws Exception {
        // Đọc nội dung file
        Path path = Paths.get(filePath);
        String content = new String(Files.readAllBytes(path));

        // Tạo key từ SHA-256
        MessageDigest sha = MessageDigest.getInstance("SHA-256");
        byte[] keyBytes = sha.digest(key.getBytes("UTF-8"));
        SecretKey secretKey = new SecretKeySpec(keyBytes, "AES");

        // Khởi tạo AES Cipher
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);

        // Mã hóa nội dung
        byte[] encryptedBytes = cipher.doFinal(content.getBytes("UTF-8"));
        return Base64.getEncoder().encodeToString(encryptedBytes);
    }

    public static String decryptChapterContent(String encryptedContent, String key) throws Exception {
        // Tạo key từ SHA-256
        MessageDigest sha = MessageDigest.getInstance("SHA-256");
        byte[] keyBytes = sha.digest(key.getBytes("UTF-8"));
        SecretKey secretKey = new SecretKeySpec(keyBytes, "AES");

        // Khởi tạo AES Cipher
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.DECRYPT_MODE, secretKey);

        // Giải mã nội dung
        byte[] decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(encryptedContent));
        return new String(decryptedBytes, "UTF-8");
    }
}
