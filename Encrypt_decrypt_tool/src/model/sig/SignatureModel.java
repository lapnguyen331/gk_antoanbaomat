package model.sig;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.interfaces.RSAKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

public class SignatureModel {
    KeyPair keyPair;
    PrivateKey privateKey ;
    PublicKey publicKey ;
    PublicKey publicTempKey;
    PrivateKey privateTempKey;
    public String getTempPublicKeyBase64(){
        return Base64.getEncoder().encodeToString(publicTempKey.getEncoded());
    }
    public String getTempPrivateKeyBase64(){
        return Base64.getEncoder().encodeToString(privateTempKey.getEncoded());
    }
    public String getPublicKeyBase64(){
        return Base64.getEncoder().encodeToString(publicKey.getEncoded());
    }
    public String getPrivateKeyBase64(){
        return Base64.getEncoder().encodeToString(privateKey.getEncoded());
    }
    public boolean isKeyPairExist(){
        if(this.publicKey == null || this.privateKey == null){
            return false;
        }else {
            return true;
        }

    }
    public void loadPublicKey(PublicKey publicKey){
        this.publicKey = publicKey;
    }
    public void loadPrivateKey(PrivateKey privateKey){
        this.privateKey = privateKey;
    }
    public PrivateKey base64ToPrivateKey(String base64Key) throws Exception {
        // Xóa header/footer nếu có
        base64Key = base64Key.replaceAll("\\s+", ""); // Xóa các khoảng trắng

        // Decode Base64
        byte[] keyBytes = Base64.getDecoder().decode(base64Key);

        // Tạo đối tượng PrivateKey từ key bytes
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
     ;
        return keyFactory.generatePrivate(keySpec);
    }
    public boolean isKeyPExist(){
        if(this.publicKey ==null){
            return false;
        }else {
            return true;
        }
    }
    public boolean checkPKey(){
        int keyLength = ((RSAKey) publicKey).getModulus().bitLength();

        if(this.publicKey != null){
            return true;
        }else {
            return false;
        }

    }
    public void resetPrivateKey(){
        this.privateKey = null;
    }
    public void resetPublicKey(){
        this.publicKey = null;
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


    // Hàm tạo chữ ký số cho file
    public byte[] signFile(byte[] fileData) throws Exception {
        Signature signature = Signature.getInstance("SHA256withRSA");
        signature.initSign(privateKey);  // Khóa riêng để ký
        signature.update(fileData);      // Chỉ cần đưa dữ liệu gốc
        return signature.sign();         // Tạo chữ ký số
    }
    // Hàm chuyển chữ ký số sang định dạng Base64 để hiển thị
    public String signFileBase64(byte[] fileData) throws Exception {
        byte[] signature = signFile(fileData);
        return Base64.getEncoder().encodeToString(signature);  // Chữ ký dưới dạng Base64
    }

    // Hàm xác minh chữ ký của file
    public boolean verifyFile(byte[] fileData, byte[] digitalSignature) throws Exception {
        Signature signature = Signature.getInstance("SHA256withRSA");
        signature.initVerify(publicKey); // Khóa công khai để xác minh
        signature.update(fileData);      // Chỉ cần đưa dữ liệu gốc
        return signature.verify(digitalSignature); // So sánh chữ ký
    }
    public boolean verifyFileWithBase64(byte[] fileData, String base64Signature) throws Exception {
        byte[] digitalSignature = Base64.getDecoder().decode(base64Signature); // Giải mã chữ ký
        return verifyFile(fileData, digitalSignature);
    }

    // Hàm đọc dữ liệu từ file
    public  byte[] readFile(String filePath) throws IOException {
        try (FileInputStream fis = new FileInputStream(filePath)) {
            return fis.readAllBytes();
        }
    }

    // Hàm lưu chữ ký vào file
    public  void saveSignature(String signaturePath, String  digitalSignature) throws IOException {
        try (FileOutputStream fos = new FileOutputStream(signaturePath)) {
            fos.write(digitalSignature.getBytes());
        }
    }
    public void saveSignedFile(String inputFilePath, String signatureFilePath, String base64Signature) throws IOException {
        try (FileOutputStream fos = new FileOutputStream(signatureFilePath);
             FileInputStream fis = new FileInputStream(inputFilePath)) {

            // Ghi dữ liệu gốc vào file mới
            fos.write(fis.readAllBytes());

            // Thêm một dòng phân cách
            fos.write("\n---SIGNATURE---\n".getBytes());

            // Giải mã chữ ký từ Base64 sang byte[]
            byte[] signatureBytes = Base64.getDecoder().decode(base64Signature);

            // Ghi chữ ký vào cuối file
            fos.write(signatureBytes);
        }
    }
//    public  boolean verifySignedFile(String signedFilePath, String base64PublicKey) throws Exception {
//        try (FileInputStream fis = new FileInputStream(signedFilePath)) {
//            // Đọc toàn bộ nội dung file đã ký
//            byte[] signedFileData = fis.readAllBytes();
//            String signedFileContent = new String(signedFileData, StandardCharsets.UTF_8);
//
//            // Tách dữ liệu và chữ ký
//            String[] parts = signedFileContent.split("\n---SIGNATURE---\n");
//
//            if (parts.length != 2) {
//                throw new IllegalArgumentException("File đã ký không hợp lệ.");
//            }
//
//            // Tách dữ liệu file và chữ ký
//            byte[] fileData = parts[0].getBytes(StandardCharsets.UTF_8);
//            byte[] signature = Base64.getDecoder().decode(parts[1]);
//
//            // Chuyển đổi chuỗi Base64 public key thành PublicKey
//            byte[] publicKeyBytes = Base64.getDecoder().decode(base64PublicKey);
//            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
//            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(publicKeyBytes);
//            PublicKey publicKey = keyFactory.generatePublic(keySpec);
//
//            // Tạo đối tượng Signature để xác minh
//            Signature sig = Signature.getInstance("SHA256withRSA");
//            sig.initVerify(publicKey);
//            sig.update(fileData);
//
//            // Xác minh chữ ký
//            return sig.verify(signature);
//        }
//    }


    public boolean isKeyPrExist(){
        if(this.privateKey ==null){
            return false;
        }else {
            return true;
        }
    }

    // Hàm tạo cặp khóa RSA
    public void generateRSAKeyPair() throws NoSuchAlgorithmException {
        KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance("RSA");
        keyPairGen.initialize(2048);
        KeyPair p = keyPairGen.genKeyPair();
        this.keyPair = p;
        this.publicTempKey = keyPair.getPublic();
        this.privateTempKey = keyPair.getPrivate();
    }

    public static void main(String[] args) throws Exception {
        // Bước 1: Tạo cặp khóa RSA
//        KeyPair keyPair = SignatureModel.generateRSAKeyPair();
//        PrivateKey privateKey = keyPair.getPrivate();
//        PublicKey publicKey = keyPair.getPublic();

        // Bước 2: Đọc dữ liệu từ một file (hoặc dữ liệu mẫu)
//        String filePath = "sample.txt"; // Đảm bảo rằng file này tồn tại
//        byte[] fileData = SignatureModel.readFile(filePath);
//
//        // Bước 3: Tạo chữ ký cho dữ liệu file
//        byte[] digitalSignature = SignatureModel.signFile(fileData, privateKey);
//        System.out.println("Digital Signature: " + Base64.getEncoder().encodeToString(digitalSignature));
//
//        // Bước 4: Lưu chữ ký vào file
//        String signaturePath = "file_signature.sig";
//        SignatureModel.saveSignature(signaturePath, digitalSignature);
//        System.out.println("Signature saved to: " + signaturePath);
//
//        // Bước 5: Xác minh chữ ký
//        boolean isVerified = SignatureModel.verifyFile(fileData, digitalSignature, publicKey);
//        System.out.println("Signature verified: " + isVerified);
    }
}
