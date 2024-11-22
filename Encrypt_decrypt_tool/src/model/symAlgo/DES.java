package model.symAlgo;

import javax.crypto.*;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class DES {
    private SecretKey key ;
    //genarate secret key
    public SecretKey genKey() throws NoSuchAlgorithmException {
        KeyGenerator gen = KeyGenerator.getInstance("DES");
        return gen.generateKey();
    }
    public void loadKey() throws NoSuchAlgorithmException {
        this.key = genKey();
    }
    public byte[] encrypt(String data) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        Cipher cip = Cipher.getInstance("DES");

        cip.init(Cipher.ENCRYPT_MODE,key);
        return cip.doFinal(data.getBytes(StandardCharsets.UTF_8));
    }
    public String encryptBase64(String data) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        return Base64.getEncoder().encodeToString(encrypt(data));
    }

    public String decrypt(byte[] data) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        Cipher cip = Cipher.getInstance("DES");

        cip.init(Cipher.DECRYPT_MODE,key);
        return new String(cip.doFinal(data),StandardCharsets.UTF_8);
    }
    public String decryptBase64(String data) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        Cipher cip = Cipher.getInstance("DES");

        cip.init(Cipher.DECRYPT_MODE,key);
        return new String(cip.doFinal(data.getBytes(StandardCharsets.UTF_8)),StandardCharsets.UTF_8);
    }

//        //ma hoa file
    public boolean encryptFile(String srcF, String desF) throws NoSuchPaddingException, NoSuchAlgorithmException, IOException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        Cipher cip = Cipher.getInstance("DES");
        cip.init(Cipher.ENCRYPT_MODE,key);
        //tạo luồng đọc/xuất file
        BufferedInputStream bis = new BufferedInputStream(new FileInputStream(srcF));
        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(desF));

        //tạo luồng mã hóa

        CipherInputStream cis = new CipherInputStream(bis,cip);

        // đọc file và mã hóa]
        byte[] writebuff= new byte[100];
        int readByte = 0;

        while ((readByte = cis.read(writebuff)) != -1) {
            bos.write(writebuff,0,readByte);
        }
        writebuff = cip.doFinal();
        if(writebuff != null){
            bos.write(writebuff);
        }
        //đóng các luồng
        bos.flush();
        bos.close();
        bis.close();
        cis.close();

        return false;
    }
    public boolean decryptFile(String srcF, String desF) throws NoSuchPaddingException, NoSuchAlgorithmException, IOException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        Cipher cip = Cipher.getInstance("DES");
        cip.init(Cipher.DECRYPT_MODE,key);
        //tạo luồng đọc/xuất file
        BufferedInputStream bis = new BufferedInputStream(new FileInputStream(srcF));
        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(desF));

        //tạo luồng mã hóa

        CipherOutputStream cos = new CipherOutputStream(bos, cip);

        // đọc file và mã hóa]
        byte[] writebuff= new byte[100];
        int readByte = 0;

        while ((readByte = bis.read(writebuff)) != -1) {
            cos.write(writebuff,0,readByte);
        }
        writebuff = cip.doFinal();
        if(writebuff != null){
            cos.write(writebuff);
        }
        //đóng các luồng
        cos.flush();
        bos.flush();
        bos.close();
        bis.close();
        cos.close();

        return false;
    }

    public static void main(String[] args) throws NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException, InvalidKeyException, IOException {
        DES des = new DES();
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
