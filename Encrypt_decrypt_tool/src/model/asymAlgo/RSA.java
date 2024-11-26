package model.symAlgo;

import javax.crypto.Cipher;
import java.io.*;
import java.security.*;
import java.util.Base64;

public class RSA {
    private KeyPairGenerator keyPairGenerator;
    private KeyPair keyPair;
    private PrivateKey privateKey;
    private PublicKey publicKey;

    public void generateKeyPair(int keySize) throws NoSuchAlgorithmException {
        keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(keySize); // 2048 bits là độ dài khóa phổ biến
        keyPair = keyPairGenerator.generateKeyPair();
        this.publicKey = keyPair.getPublic();
        this.privateKey = keyPair.getPrivate();
    }

    public String encrypt(String data) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        byte[] encryptedData = cipher.doFinal(data.getBytes());
        return Base64.getEncoder().encodeToString(encryptedData);
    }

    public String decrypt(String data) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        byte[] decryptedData = cipher.doFinal(Base64.getDecoder().decode(data));
        return new String(decryptedData);
    }

    public String getPublicKeyBase64() {
        return Base64.getEncoder().encodeToString(publicKey.getEncoded());
    }

    public String getPrivateKeyBase64() {
        return Base64.getEncoder().encodeToString(privateKey.getEncoded());
    }

    public static void main(String[] args) throws Exception {
        RSA rsa = new RSA();
        rsa.generateKeyPair(2048);

        String originalMessage = "Hello, RSA Encryption!";
        String encryptedMessage = rsa.encrypt(originalMessage);
        String decryptedMessage = rsa.decrypt(encryptedMessage);

        System.out.println("Original: " + originalMessage);
        System.out.println("Encrypted: " + encryptedMessage);
        System.out.println("Decrypted: " + decryptedMessage);

        // Lưu và đọc khóa từ file
        rsa.saveKeyToFile("public.key", rsa.getPublicKeyBase64());
        rsa.saveKeyToFile("private.key", rsa.getPrivateKeyBase64());
    }

    public void saveKeyToFile(String fileName, String keyText) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            writer.write(keyText);
        }
    }

    public String loadKeyFromFile(String fileName) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            return reader.readLine();
        }
    }
}
