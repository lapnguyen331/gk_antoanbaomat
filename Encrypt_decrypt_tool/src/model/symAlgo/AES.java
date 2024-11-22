package model.symAlgo;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

public class AES {
    private SecretKey key;
    private IvParameterSpec initv; // Initialization Vector
    public SecretKey genKey() throws NoSuchAlgorithmException {
        KeyGenerator gen = KeyGenerator.getInstance("AES");
        return gen.generateKey();
    }
    public void loadKey() throws NoSuchAlgorithmException {
        this.key = genKey();
    }
    public byte[] encrypt(String data) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, key);
        byte[] cipherText = cipher.doFinal(data.getBytes());
        return cipherText;
    }
    public String encryptBase64(String data) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, key);
        byte[] cipherText = cipher.doFinal(data.getBytes());
        return new String(Base64.getEncoder().encodeToString(cipherText));
    }
    public static IvParameterSpec generateIv() {
        byte[] iv = new byte[16];
        new SecureRandom().nextBytes(iv);
        return new IvParameterSpec(iv);
    }
    public String decrypt(byte[] data) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        Cipher cip = Cipher.getInstance("AES");
//        iv = new IvParameterSpec();
        cip.init(Cipher.DECRYPT_MODE,key,iv);
        return new String(cip.doFinal(data), StandardCharsets.UTF_8);
    }
    public String decryptBase64(String data) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        Cipher cip = Cipher.getInstance("AES/ECB/PKCS5Padding");

        cip.init(Cipher.DECRYPT_MODE,key);
        return new String(cip.doFinal(data.getBytes(StandardCharsets.UTF_8)),StandardCharsets.UTF_8);
    }
    //ma hoa file
    public boolean encryptFile(String srcF, String desF) throws IOException, InvalidKeyException, NoSuchPaddingException, NoSuchAlgorithmException, IllegalBlockSizeException, BadPaddingException {
        Cipher cip = Cipher.getInstance("AES/ECB/PKCS5Padding");
        cip.init(Cipher.ENCRYPT_MODE,key);

        BufferedInputStream bis = new BufferedInputStream(new FileInputStream(srcF));
        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(desF));

        CipherInputStream cis = new CipherInputStream(bis,cip);

        byte[] writebuff = new byte[64];
        int writeByte =0;
        while((writeByte = cis.read(writebuff))!=-1){
            bos.write(writebuff,0,writeByte);
        }

        bos.flush();
        bos.close();
        cis.close();
        return true;
    }
    public boolean decryptFile(String srcF, String desF) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, IOException, IllegalBlockSizeException, BadPaddingException {
        Cipher cip = Cipher.getInstance("AES/ECB/PKCS5Padding");
        cip.init(Cipher.DECRYPT_MODE,key);

        BufferedInputStream bis = new BufferedInputStream(new FileInputStream(srcF));
        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(desF));

        CipherOutputStream cos = new CipherOutputStream(bos,cip);

        byte[] writebuff = new byte[64];
        int writeByte =0;
        while((writeByte = bis.read(writebuff))!=-1){
            cos.write(writebuff,0,writeByte);
        }
        cos.flush();
        bos.flush();
        bos.close();
        bis.close();
        return true;
    }

    public static void main(String[] args) throws NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException, InvalidKeyException, IOException {
        AES des = new AES();
        //test cho mã hóa thường
//        des.loadKey();
//        String data = "lập đẹp trai";
//        System.out.println(Base64.getEncoder().encodeToString(des.key.getEncoded()));
//        System.out.println(Base64.getEncoder().encodeToString(des.encrypt(data)));
//        System.out.println("decrypt res:"+des.decrypt(des.encrypt(data)));
        //test cho mã hoá file
        des.loadKey();
        String srcf = "D:\\learning_code\\an_toan_bao_mat\\TH\\Nguyen_Huy_Binh1.webp";
        String desf = "D:\\learning_code\\an_toan_bao_mat\\TH\\Nguyen_Huy_Binh1_en.webp";
        String dec = "D:\\learning_code\\an_toan_bao_mat\\TH\\Nguyen_Huy_Binh1_de.webp";
        des.encryptFile(srcf,desf);
        des.decryptFile(desf,dec);
    }

}
