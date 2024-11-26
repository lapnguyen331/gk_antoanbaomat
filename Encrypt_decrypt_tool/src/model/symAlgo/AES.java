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

public class AES extends SymAlgo {
    private SecretKey key;
    private IvParameterSpec iv; // IV cho các chế độ không phải ECB
    private String modePadding;
    private int keySize;
    private boolean isKeyExist = false;
    private String inputFilePath =null;
    static {
        Security.addProvider(new BouncyCastleProvider());  // Thêm BouncyCastle provider
    }
    public SecretKey genKey() throws NoSuchAlgorithmException {
        KeyGenerator gen = KeyGenerator.getInstance("AES");
        gen.init(this.keySize); // Sử dụng độ dài khóa được cài đặt
        isKeyExist = true;
        return gen.generateKey();
    }

    public void loadDefaultValue(int keylength, String mode) {
        this.keySize = keylength;
        this.modePadding = mode;
    }

    public void loadKey(SecretKey secretKey) {
        this.key = secretKey;
        isKeyExist = true;
    }
    public void loadKeyLength(int keyLength){
        this.keySize=keyLength;
    }

    public void loadPaddingMode(String mode) {
        this.modePadding = mode;
    }

    public void setKey(SecretKey key) {
        this.key = key;
    }

    public void genIV() {
        byte[] ivBytes = new byte[16]; // Độ dài IV 16 byte
        new SecureRandom().nextBytes(ivBytes);
        this.iv = new IvParameterSpec(ivBytes);
    }

    public IvParameterSpec getIv() {
        return iv;
    }
    public String secretKeyToBase64(SecretKey secretKey){
        return Base64.getEncoder().encodeToString(secretKey.getEncoded());
    }
    public int getLengthKey(){
        return keySize;
    }
    public  SecretKey base64ToSecretKey(String base64Key) {
        // Giải mã chuỗi Base64 thành mảng byte
        byte[] decodedKey = Base64.getDecoder().decode(base64Key);

        // Tạo đối tượng SecretKey từ mảng byte
        return new SecretKeySpec(decodedKey, "AES");
    }


    public boolean checkInputKey(String input){
        byte[] keyBytes = Base64.getDecoder().decode(input);
        // Tính độ dài key theo bit
        int keyLengthInBits = keyBytes.length * 8;
        if(keyLengthInBits ==128  || keyLengthInBits ==192 ||keyLengthInBits ==256){
            return true;
        }
        return false;
    }
    public int getInputKeySize(String input){
        byte[] keyBytes = Base64.getDecoder().decode(input);
        // Tính độ dài key theo bit
        return  keyBytes.length * 8;
    }

    public void setIv(byte[] ivBytes) {
        this.iv = new IvParameterSpec(ivBytes);
    }

    public SecretKey loadKeyFromFile(String filePath) throws IOException {
        String base64Key = new String(Files.readAllBytes(Paths.get(filePath))).trim();
        byte[] keyBytes = Base64.getDecoder().decode(base64Key);
        return new SecretKeySpec(keyBytes, "AES");
    }
    //hàm này nhằm kiểm tra độ dài key có đúng hay không
    public boolean checkKey() {
        int keyLengthInBits = key.getEncoded().length * 8;
        if(keyLengthInBits == keySize){
            return true;
        }
        return false;
    }

    public void saveKeyFile(String keyText,String filePath) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filePath))) {
            oos.writeObject(keyText);
        }
    }

    public boolean isKeyExist() {
        return this.key != null && isKeyExist;
    }

    public void importKeyFromFile(String filePath) throws IOException, ClassNotFoundException {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filePath))) {
            key = (SecretKey) ois.readObject();
        }
    }
    public String decrypt(byte[] data) throws Exception {
        Cipher cipher = Cipher.getInstance("AES/" + modePadding,"BC");

        // Kiểm tra chế độ yêu cầu IV
        if (modePadding.startsWith("CBC") || modePadding.startsWith("CFB") || modePadding.startsWith("OFB")) {
            if (iv == null) {
                throw new IllegalStateException("IV is required but not set for mode: " + modePadding);
            }
            cipher.init(Cipher.DECRYPT_MODE, key, iv);
        } else {
            cipher.init(Cipher.DECRYPT_MODE, key);
        }

        byte[] decryptedData = cipher.doFinal(data);
        return new String(decryptedData, StandardCharsets.UTF_8);
    }



    public String encryptBase64(String data) throws Exception {
        return Base64.getEncoder().encodeToString(encrypt(data));
    }

    public String decryptBase64(String data) throws Exception {
        byte[] cipherText = Base64.getDecoder().decode(data);
        return decrypt(cipherText);
    }
    public byte[] encrypt(String data) throws Exception {
        Cipher cipher = Cipher.getInstance("AES/" + modePadding,"BC");

        // Kiểm tra chế độ yêu cầu IV
        if (modePadding.startsWith("CBC") || modePadding.startsWith("CFB") || modePadding.startsWith("OFB")|| modePadding.startsWith("CTR")) {
            if (iv == null) genIV(); // Tạo IV nếu chưa có
            cipher.init(Cipher.ENCRYPT_MODE, key, iv);
        } else {
            cipher.init(Cipher.ENCRYPT_MODE, key);
        }

        return cipher.doFinal(data.getBytes(StandardCharsets.UTF_8));
    }
    // Mã hóa file
    public void encryptFile(String inputFile, String outputFile) throws Exception {
        // Khởi tạo Cipher với chế độ và padding AES
        Cipher cipher = Cipher.getInstance("AES/"+modePadding,"BC");
        if (modePadding.startsWith("CBC") || modePadding.startsWith("CFB") || modePadding.startsWith("OFB") || modePadding.startsWith("CTR")) {
            if (iv == null) {
                genIV();
            }
            cipher.init(Cipher.ENCRYPT_MODE, key, iv);
        } else {
            cipher.init(Cipher.ENCRYPT_MODE, key);
        }

        // Đọc tệp đầu vào và tạo CipherOutputStream để mã hóa
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
    public  void decryptFile(String inputFile, String outputFile) throws Exception {
        // Khởi tạo Cipher với chế độ và padding AES
        Cipher cipher = Cipher.getInstance("AES/"+modePadding,"BC");
        if (modePadding.startsWith("CBC") || modePadding.startsWith("CFB") || modePadding.startsWith("OFB")) {
            if (iv == null) genIV();
            cipher.init(Cipher.DECRYPT_MODE, key, iv);
        } else {
            cipher.init(Cipher.DECRYPT_MODE, key);
        }

        // Đọc tệp mã hóa và tạo CipherInputStream để giải mã
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

    @Override
    public void saveData(Map<String, Object> data) {
        // Custom logic
    }

    @Override
    public Map<String, Object> loadData() {
        return null;
    }
    public void setKeyExist(boolean b){
        this.isKeyExist = b;
    }

    public static void main(String[] args) throws Exception {
        AES a = new AES();
        a.loadDefaultValue(128,"ECB/PKCF5Padding");
        a.loadKey(a.genKey());
        a.decryptFile("D://cong-cu-ho-tro-ma-hoa-va-giai-ma-rsa-des-aes-sha-md5-14370_des.png","D://cong-cu-ho-tro-ma-hoa-va-giai-ma-rsa-des-aes-sha-md5-14370_decrypt.png");
    }
}
