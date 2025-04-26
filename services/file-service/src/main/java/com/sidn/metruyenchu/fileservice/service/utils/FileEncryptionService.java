package com.sidn.metruyenchu.fileservice.service.utils;

import org.springframework.stereotype.Service;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

@Service
public class FileEncryptionService {
    private static final String AES_ALGORITHM = "AES";
    private static final String SECRET_KEY = "your-256-bit-secret-key"; // Thay bằng khóa bạn đã tạo

    public byte[] encryptFile(byte[] fileData) throws Exception {
        SecretKeySpec secretKeySpec = new SecretKeySpec(Base64.getDecoder().decode(SECRET_KEY), AES_ALGORITHM);
        Cipher cipher = Cipher.getInstance(AES_ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);
        return cipher.doFinal(fileData);
    }
}
