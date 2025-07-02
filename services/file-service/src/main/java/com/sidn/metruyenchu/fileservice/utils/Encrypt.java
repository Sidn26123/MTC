package com.sidn.metruyenchu.fileservice.utils;

import org.springframework.web.multipart.MultipartFile;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.io.IOException;
import java.security.SecureRandom;
import java.util.Base64;

public class Encrypt {
    private static byte[] encryptAES(byte[] data, SecretKey secretKey) throws IOException {
        try {
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            return cipher.doFinal(data);
        } catch (Exception e) {
            throw new IOException("Lỗi mã hóa file", e);
        }
    }

    private static SecretKey generateAESKey() throws IOException {
        try {
            KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
            keyGenerator.init(256, new SecureRandom()); // 256-bit key
            return keyGenerator.generateKey();
        } catch (Exception e) {
            throw new IOException("Lỗi tạo khóa AES", e);
        }
    }
//    public static MultipartFile encryptFile(MultipartFile file) throws IOException {
//        try {
//            // Tạo khóa AES-256
//            SecretKey secretKey = generateAESKey();
//
//            // Mã hóa nội dung file
//            byte[] fileBytes = file.getBytes();
//            byte[] encryptedBytes = encryptAES(fileBytes, secretKey);
//
//            // Mã hóa key thành chuỗi Base64 để lưu trữ
//            String encodedKey = Base64.getEncoder().encodeToString(secretKey.getEncoded());
//
//            // Tạo MultipartFile mới từ nội dung đã mã hóa
//            return new MockMultipartFile(
//                    file.getName(),                      // Tên field
//                    file.getOriginalFilename(),          // Tên file gốc
//                    file.getContentType(),               // Loại MIME
//                    encryptedBytes                       // Nội dung file mã hóa
//            );
//        } catch (Exception e) {
//            throw new IOException("Lỗi mã hóa file", e);
//        }
//    }

    private byte[] decryptAES(byte[] data, SecretKey secretKey) throws IOException {
        try {
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            return cipher.doFinal(data);
        } catch (Exception e) {
            throw new IOException("Lỗi giải mã file", e);
        }
    }



}
