package model.sig;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.*;
import java.util.Base64;

public class SignatureModel {

    // Hàm tạo chữ ký số cho file
    public static byte[] signFile(byte[] fileData, PrivateKey privateKey) throws Exception {
        // Băm dữ liệu file
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] hashedData = digest.digest(fileData);

        Signature signature = Signature.getInstance("SHA256withRSA");
        signature.initSign(privateKey);
        signature.update(hashedData);
        return signature.sign();
    }

    // Hàm xác minh chữ ký của file
    public static boolean verifyFile(byte[] fileData, byte[] digitalSignature, PublicKey publicKey) throws Exception {
        // Băm dữ liệu file
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] hashedData = digest.digest(fileData);

        // Xác minh chữ ký
        Signature signature = Signature.getInstance("SHA256withRSA");
        signature.initVerify(publicKey);
        signature.update(hashedData);
        return signature.verify(digitalSignature);
    }

    // Hàm đọc dữ liệu từ file
    public static byte[] readFile(String filePath) throws IOException {
        try (FileInputStream fis = new FileInputStream(filePath)) {
            return fis.readAllBytes();
        }
    }

    // Hàm lưu chữ ký vào file
    public static void saveSignature(String signaturePath, byte[] digitalSignature) throws IOException {
        try (FileOutputStream fos = new FileOutputStream(signaturePath)) {
            fos.write(digitalSignature);
        }
    }

    // Hàm tạo cặp khóa RSA
    public static KeyPair generateRSAKeyPair() throws NoSuchAlgorithmException {
        KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance("RSA");
        keyPairGen.initialize(2048);
        return keyPairGen.generateKeyPair();
    }
}
