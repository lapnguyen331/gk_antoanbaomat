package model.asymAlgo;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;
import java.io.*;
import java.security.*;
import java.security.interfaces.RSAKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

public class RSA {
    private KeyPairGenerator keyPairGenerator;
    private KeyPair keyPair;
    private PrivateKey privateKey;
    private PublicKey publicKey;
    private PrivateKey privateTempKey;
    private PublicKey publicTempKey;
    private int keySize;
    private String modePadding;
    static {
        Security.addProvider(new BouncyCastleProvider());  // Thêm BouncyCastle provider
    }
    public void generateKeyPair() throws NoSuchAlgorithmException {
        keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(keySize); // 2048 bits là độ dài khóa phổ biến
        keyPair = keyPairGenerator.generateKeyPair();
        this.publicTempKey = keyPair.getPublic();
        this.privateTempKey = keyPair.getPrivate();
    }
    public boolean isKeyPExist(){
        if(this.publicKey ==null){
            return false;
        }else {
            return true;
        }
    }
    public void loadKeyLength(int size){
        this.keySize =size;
    }
    public boolean isKeyPrExist(){
        if(this.privateKey ==null){
            return false;
        }else {
            return true;
        }
    }
    public void loadDefaultValue(int num, String text){
        this.keySize =num;
        this.modePadding = text;
    }
    public void loadPaddingMode(String modepadding){
        this.modePadding = modepadding;
    }
    public boolean isKeyPairExist(){
        if(this.publicKey == null || this.privateKey == null){
            return false;
        }else {
            return true;
        }

    }
    public void base64ToPrivateKey(PrivateKey privateKey){
        this.privateKey = privateKey;
    }
    public void loadPublicKey(PublicKey publicKey){
        this.publicKey = publicKey;
    }
    public void loadPrivateKey(PrivateKey privateKey){
        this.privateKey = privateKey;
    }
    public void saveKeyFile(String keyText,String filePath) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filePath))) {
            oos.writeObject(keyText);
        }
    }


    public PrivateKey base64ToPrivateKey(String base64Key) throws Exception {
        // Xóa header/footer nếu có
        base64Key = base64Key.replaceAll("\\s+", ""); // Xóa các khoảng trắng

        // Decode Base64
        byte[] keyBytes = Base64.getDecoder().decode(base64Key);

        // Tạo đối tượng PrivateKey từ key bytes
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
        return keyFactory.generatePrivate(keySpec);
    }
    public PublicKey base64ToPublicKey(String base64Key) throws Exception {
        // Xóa header/footer nếu có
        base64Key = base64Key.replaceAll("\\s+", ""); // Xóa các khoảng trắng

        // Decode Base64
        byte[] keyBytes = Base64.getDecoder().decode(base64Key);

        // Tạo đối tượng PrivateKey từ key bytes
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec (keyBytes);
        return keyFactory.generatePublic(keySpec);
    }


    public void resetPrivateKey(){
        this.privateKey = null;
    }
    public void resetPublicKey(){
        this.publicKey = null;
    }
    public int getLengthKey(){
        return keySize;
    }

    public String encrypt(String data) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA/"+modePadding);
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        byte[] encryptedData = cipher.doFinal(data.getBytes());
        return Base64.getEncoder().encodeToString(encryptedData);
    }
    public void encryptFile(String inputFilePath, String outputFilePath) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA/" + modePadding);
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);

        try (FileInputStream fis = new FileInputStream(inputFilePath);
             FileOutputStream fos = new FileOutputStream(outputFilePath);
             CipherOutputStream cos = new CipherOutputStream(fos, cipher)) {

            byte[] buffer = new byte[1024]; // Đọc từng khối dữ liệu
            int bytesRead;
            while ((bytesRead = fis.read(buffer)) != -1) {
                cos.write(buffer, 0, bytesRead);
            }
        }
        System.out.println("File encrypted and saved to: " + outputFilePath);
    }

    public String decrypt(String data) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA/"+modePadding,"BC");
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        byte[] decryptedData = cipher.doFinal(Base64.getDecoder().decode(data));
        return new String(decryptedData);
    }
    public void decryptFile(String inputFilePath, String outputFilePath) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA/" + modePadding);
        cipher.init(Cipher.DECRYPT_MODE, privateKey);

        try (FileInputStream fis = new FileInputStream(inputFilePath);
             CipherInputStream cis = new CipherInputStream(fis, cipher);
             FileOutputStream fos = new FileOutputStream(outputFilePath)) {

            byte[] buffer = new byte[1024]; // Đọc từng khối dữ liệu
            int bytesRead;
            while ((bytesRead = cis.read(buffer)) != -1) {
                fos.write(buffer, 0, bytesRead);
            }
        }
        System.out.println("File decrypted and saved to: " + outputFilePath);
    }
    public boolean checkPKey(){
        int keyLength = ((RSAKey) publicKey).getModulus().bitLength();

        if(this.publicKey != null || keyLength == keySize){
            return true;
        }else {
            return false;
        }

    }
    public boolean checkPrKey(){
        int keyLength = ((RSAKey) privateKey).getModulus().bitLength();

        if(this.privateKey != null || keyLength == keySize){
            return true;
        }else {
            return false;
        }

    }

    public String getPublicKeyBase64() {
        return Base64.getEncoder().encodeToString(publicKey.getEncoded());
    }
    public String getTempPublicKeyBase64(){
        return Base64.getEncoder().encodeToString(publicTempKey.getEncoded());
    }
    public String getTempPrivateKeyBase64(){
        return Base64.getEncoder().encodeToString(privateTempKey.getEncoded());
    }

    public String getPrivateKeyBase64() {
        if(privateKey ==null){
            return "";
        }else {
            return Base64.getEncoder().encodeToString(privateKey.getEncoded());

        }
    }

    public static void main(String[] args) throws Exception {
        RSA rsa = new RSA();
//        rsa.generateKeyPair(2048);

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
