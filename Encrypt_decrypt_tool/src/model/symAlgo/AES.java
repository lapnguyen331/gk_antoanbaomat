package model.symAlgo;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.Map;

public class AES extends SymAlgo{
    private SecretKey key;
    private String filePath;
    private int keySize ;
    private String padding;
    private String mode;
    private boolean isKeyExist = false;
    public SecretKey genKey() throws NoSuchAlgorithmException {
        KeyGenerator gen = KeyGenerator.getInstance("AES");
        gen.init(this.keySize);  // Sử dụng độ dài khóa 128 bit
        isKeyExist = true;
        return gen.generateKey();
    }
    public void loadDefaultValue(int keylength,String mode, String padding){
        this.keySize = keylength;
        this.padding = padding;
        this.mode = mode;
    }
    public void loadKeyLength(int keyLength){
        this.keySize=keyLength;
    }
    public void loadMode(String mode){
        this.mode=mode;
    }
    public void loadPadding(String padding){
        this.padding=padding;
    }
    public void loadKey(SecretKey secretKey) throws NoSuchAlgorithmException {
        this.key = secretKey ;
        isKeyExist = true;
    }
    // Phương thức lưu khóa vào tệp
    public void saveKeyFile(String filePath) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filePath))) {
            oos.writeObject(key);  // Lưu đối tượng khóa vào tệp
        }
        this.filePath = filePath;
    }
    //kiểm tra key exist
    public boolean isKeyexist(){
        if(this.key == null || isKeyExist == false) return false;
        return true;
    }
    // Phương thức đọc khóa từ tệp và nạp vào
    public void importKeyFromFile(String filePath) throws IOException, ClassNotFoundException {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filePath))) {
            key = (SecretKey) ois.readObject();  // Đọc đối tượng khóa từ tệp và gán vào
        }
        this.filePath = filePath;
    }

    // Phương thức mã hóa (chuỗi)
    public byte[] encrypt(String data) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        Cipher cipher = Cipher.getInstance("AES/" +mode+"/"+padding);
        cipher.init(Cipher.ENCRYPT_MODE, key);
        return cipher.doFinal(data.getBytes(StandardCharsets.UTF_8));
    }

    // Phương thức mã hóa Base64 (chuỗi)
    public String encryptBase64(String data) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        byte[] encodeText = encrypt(data);
        return Base64.getEncoder().encodeToString(encodeText);
    }
    public String secretKeyToBase64(SecretKey secretKey){
        return Base64.getEncoder().encodeToString(secretKey.getEncoded());
    }
    public  SecretKey base64ToSecretKey(String base64Key) {
        // Giải mã chuỗi Base64 thành mảng byte
        byte[] decodedKey = Base64.getDecoder().decode(base64Key);

        // Tạo đối tượng SecretKey từ mảng byte
        return new SecretKeySpec(decodedKey, "AES");
    }


    // Phương thức giải mã (chuỗi)
    public String decrypt(byte[] data) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        Cipher cipher = Cipher.getInstance("AES/"+mode+"/"+padding);
        cipher.init(Cipher.DECRYPT_MODE, key);
        byte[] decryptedData = cipher.doFinal(data);
        return new String(decryptedData, StandardCharsets.UTF_8);
    }

    // Phương thức giải mã Base64 (chuỗi)
    public String decryptBase64(String data) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        byte[] cipherText = Base64.getDecoder().decode(data);
        return decrypt(cipherText);
    }

    // Phương thức mã hóa file
    public boolean encryptFile(String srcF, String desF) throws IOException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        Cipher cipher = Cipher.getInstance("AES/"+mode+"/"+padding);
        cipher.init(Cipher.ENCRYPT_MODE, key);

        try (BufferedInputStream bis = new BufferedInputStream(new FileInputStream(srcF));
             BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(desF));
             CipherInputStream cis = new CipherInputStream(bis, cipher)) {

            byte[] writebuff = new byte[64];
            int writeByte;
            while ((writeByte = cis.read(writebuff)) != -1) {
                bos.write(writebuff, 0, writeByte);
            }
            bos.flush();
        }
        return true;
    }

    // Phương thức giải mã file
    public boolean decryptFile(String srcF, String desF) throws IOException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        Cipher cipher = Cipher.getInstance("AES/"+mode+"/"+padding);
        cipher.init(Cipher.DECRYPT_MODE, key);

        try (BufferedInputStream bis = new BufferedInputStream(new FileInputStream(srcF));
             BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(desF));
             CipherOutputStream cos = new CipherOutputStream(bos, cipher)) {

            byte[] writebuff = new byte[64];
            int writeByte;
            while ((writeByte = bis.read(writebuff)) != -1) {
                cos.write(writebuff, 0, writeByte);
            }
            cos.flush();
        }
        return true;
    }

    public static void main(String[] args) throws NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException, InvalidKeyException, IOException {
        AES aes = new AES();

        // Tạo và load khóa AES
//        aes.loadKey();

        // Kiểm tra mã hóa và giải mã chuỗi
        String data = "lập đẹp trai";
        String encryptedData = aes.encryptBase64(data);
        System.out.println("Encrypted (Base64): " + encryptedData);
        String decryptedData = aes.decryptBase64(encryptedData);
        System.out.println("Decrypted: " + decryptedData);

        // Kiểm tra mã hóa và giải mã file
        String srcFile = "D:\\learning_code\\an_toan_bao_mat\\TH\\Nguyen_Huy_Binh1.webp";
        String destFile = "D:\\learning_code\\an_toan_bao_mat\\TH\\Nguyen_Huy_Binh1_en.webp";
        String decryptedFile = "D:\\learning_code\\an_toan_bao_mat\\TH\\Nguyen_Huy_Binh1_de.webp";

        // Mã hóa file
        aes.encryptFile(srcFile, destFile);

        // Giải mã file
        aes.decryptFile(destFile, decryptedFile);
    }

    @Override
    public void saveData(Map<String, Object> data) {

    }

    @Override
    public Map<String, Object> loadData() {
        return null;
    }

    //hàm này nhằm kiểm tra độ dài key có đúng hay không
    public boolean checkKey() {
        int keyLengthInBits = key.getEncoded().length * 8;
        if(keyLengthInBits == keySize){
            return true;
        }
        return false;
    }
    public boolean checkInputKey(String input){
        byte[] keyBytes = Base64.getDecoder().decode(input);
        // Tính độ dài key theo bit
        int keyLengthInBits = keyBytes.length * 8;
        if(keyLengthInBits == keySize){
            return true;
        }
        return false;
    }
    public int getLengthKey(){
        return keySize;
    }
}
