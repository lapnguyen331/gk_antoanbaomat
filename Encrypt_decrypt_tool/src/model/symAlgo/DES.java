package model.symAlgo;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.*;
import java.util.Base64;
import java.util.Map;

public class DES extends SymAlgo {
    private SecretKey key;
    private IvParameterSpec iv;
    private String modePadding;
    private boolean isKeyExist = false;
    private int keySize =56;
    static {
        Security.addProvider(new BouncyCastleProvider());  // Thêm BouncyCastle provider
    }
    // Generate DES key
    public SecretKey genKey() throws NoSuchAlgorithmException {
        KeyGenerator keyGen = KeyGenerator.getInstance("DES");
        System.out.println("genkey" +keySize);
        keyGen.init(56); // DES uses a fixed key size of 56 bits
        this.isKeyExist = true;
        return keyGen.generateKey();
    }

    // Load key from SecretKey
    public void loadKey(SecretKey secretKey) {
        this.key = secretKey;
        isKeyExist = true;
    }

    // Generate random IV for CBC or CTR modes
    public void genIV() {
        byte[] ivBytes = new byte[8]; // DES uses 8-byte IV
        new SecureRandom().nextBytes(ivBytes);
        this.iv = new IvParameterSpec(ivBytes);
    }

    // Set IV
    public void setIv(byte[] ivBytes) {
        this.iv = new IvParameterSpec(ivBytes);
    }

    // Get IV
    public IvParameterSpec getIv() {
        return this.iv;
    }
    public void loadDefaultValue(int keylength, String mode) {
        this.keySize = keylength;
        this.modePadding = mode;
    }

    // Save SecretKey to file
    public void saveKeyFile(String keyText, String filePath) throws IOException {
        try (FileWriter fw = new FileWriter(filePath)) {
            fw.write(keyText);
        }
    }

    // Load SecretKey from file
    public SecretKey loadKeyFromFile(String filePath) throws IOException {
        String base64Key = new String(Files.readAllBytes(Paths.get(filePath))).trim();
        byte[] decodedKey = Base64.getDecoder().decode(base64Key);
        return new SecretKeySpec(decodedKey, "DES");
    }

    // Convert SecretKey to Base64
    public String secretKeyToBase64(SecretKey secretKey) {
        return Base64.getEncoder().encodeToString(secretKey.getEncoded());
    }

    // Convert Base64 to SecretKey
    public SecretKey base64ToSecretKey(String base64Key) {
        byte[] decodedKey = Base64.getDecoder().decode(base64Key);
        return new SecretKeySpec(decodedKey, "DES");
    }
    public boolean checkInputKey(String input) {
        try {
            byte[] keyBytes = Base64.getDecoder().decode(input);
            // DES yêu cầu khóa dài 8 byte (64 bit) nhưng chỉ 56 bit hiệu dụng
            return keyBytes.length == 8;
        } catch (IllegalArgumentException e) {
            return false; // Input không hợp lệ
        }
    }


    public boolean isKeyExist() {
        return this.key != null && isKeyExist;
    }
    public int getInputKeySize(String input){
        byte[] keyBytes = Base64.getDecoder().decode(input);
        // Tính độ dài key theo bit
        return  keyBytes.length * 8;
    }
    public int getLengthKey(){
        return keySize;
    }
    public boolean checkKey() {

        return checkInputKey(Base64.getEncoder().encodeToString(this.key.getEncoded()));
    }

    public void setKeyExist(boolean b){
        this.isKeyExist = b;
    }
    public void setKey(SecretKey key) {
        this.key = key;
    }
    public void loadPaddingMode(String mode) {
        this.modePadding = mode;
    }


    // Encrypt text
    public byte[] encrypt(String data) throws Exception {
        Cipher cipher = Cipher.getInstance("DES/" + modePadding);

        if ("ECB".equalsIgnoreCase(modePadding.split("/")[0])) {
            cipher.init(Cipher.ENCRYPT_MODE, key);
        } else {
            if (iv == null) genIV();
            cipher.init(Cipher.ENCRYPT_MODE, key, iv);
        }

        return cipher.doFinal(data.getBytes(StandardCharsets.UTF_8));
    }

    // Decrypt text
    public String decrypt(byte[] encryptedData) throws Exception {
        Cipher cipher = Cipher.getInstance("DES/" + modePadding);

        if ("ECB".equalsIgnoreCase(modePadding.split("/")[0])) {
            cipher.init(Cipher.DECRYPT_MODE, key);
        } else {
            if (iv == null) throw new IllegalStateException("IV is required but not set for mode: " + modePadding);
            cipher.init(Cipher.DECRYPT_MODE, key, iv);
        }

        byte[] decryptedData = cipher.doFinal(encryptedData);
        return new String(decryptedData, StandardCharsets.UTF_8);
    }

    // Encrypt text to Base64
    public String encryptBase64(String data) throws Exception {
        return Base64.getEncoder().encodeToString(encrypt(data));
    }

    // Decrypt Base64 text
    public String decryptBase64(String base64Data) throws Exception {
        byte[] encryptedData = Base64.getDecoder().decode(base64Data);
        return decrypt(encryptedData);
    }

    // Encrypt file
    public void encryptFile(String inputFile, String outputFile) throws Exception {
        Cipher cipher = Cipher.getInstance("DES/" + modePadding);

        if ("ECB".equalsIgnoreCase(modePadding.split("/")[0])) {
            cipher.init(Cipher.ENCRYPT_MODE, key);
        } else {
            if (iv == null) genIV();
            cipher.init(Cipher.ENCRYPT_MODE, key, iv);
        }

        try (FileInputStream fis = new FileInputStream(inputFile);
             FileOutputStream fos = new FileOutputStream(outputFile);
             CipherOutputStream cos = new CipherOutputStream(fos, cipher)) {

            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = fis.read(buffer)) != -1) {
                cos.write(buffer, 0, bytesRead);
            }
        }
    }

    // Decrypt file
    public void decryptFile(String inputFile, String outputFile) throws Exception {
        Cipher cipher = Cipher.getInstance("DES/" + modePadding);

        if ("ECB".equalsIgnoreCase(modePadding.split("/")[0])) {
            cipher.init(Cipher.DECRYPT_MODE, key);
        } else {
            if (iv == null) throw new IllegalStateException("IV is required but not set for mode: " + modePadding);
            cipher.init(Cipher.DECRYPT_MODE, key, iv);
        }

        try (FileInputStream fis = new FileInputStream(inputFile);
             FileOutputStream fos = new FileOutputStream(outputFile);
             CipherInputStream cis = new CipherInputStream(fis, cipher)) {

            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = cis.read(buffer)) != -1) {
                fos.write(buffer, 0, bytesRead);
            }
        }
    }

    // Save or load additional data (custom logic)
    @Override
    public void saveData(Map<String, Object> data) {
        // Custom logic
    }

    @Override
    public Map<String, Object> loadData() {
        return null;
    }

    // Test the DES implementation
    public static void main(String[] args) throws Exception {
        DES des = new DES();
        des.modePadding = "CBC/PKCS5Padding"; // Example mode and padding
        des.genKey(); // Generate DES key
        des.genIV(); // Generate IV

        // Encrypt and decrypt a string
        String original = "Hello DES!";
        String encrypted = des.encryptBase64(original);
        String decrypted = des.decryptBase64(encrypted);

        System.out.println("Original: " + original);
        System.out.println("Encrypted: " + encrypted);
        System.out.println("Decrypted: " + decrypted);

        // Encrypt and decrypt a file
        String inputFile = "C:\\Users\\ADMIN\\Documents\\cong-cu-ho-tro-ma-hoa-va-giai-ma-rsa-des-aes-sha-md5-14370.png";
        String encryptedFile = "C:\\Users\\ADMIN\\Documents\\cong-cu-ho-tro-ma-hoa-va-giai-ma-rsa-des-aes-sha-md5-14370_encrypted.png";
        String decryptedFile = "C:\\Users\\ADMIN\\Documents\\cong-cu-ho-tro-ma-hoa-va-giai-ma-rsa-des-aes-sha-md5-14370_decrypted.png";

        des.encryptFile(inputFile, encryptedFile);
        des.decryptFile(encryptedFile, decryptedFile);
        System.out.println("File encryption and decryption complete.");
    }
}
