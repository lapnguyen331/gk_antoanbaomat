package model.hash;

import java.io.*;
import java.math.BigInteger;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5 {

    public static MD5 instance;

    public static MD5 getInstance() {
        if(instance == null)  instance = new MD5();
        return instance;
    }
    public String check(String data) {
        try {
            // Khai báo thuật toán
            MessageDigest md = MessageDigest.getInstance("MD5");
            // hoặc là
//            MessageDigest md = MessageDigest.getInstance("SHA-256");

            // Lấy ra dữ liệu dạng byte từ input data
            byte[] output = md.digest(data.getBytes());

//            Chuyển mảng byte vừa lấy được về 1 số có dạng BigInteger
            BigInteger num = new BigInteger(1, output);

//            return về dạng cơ số 16.
            return num.toString(16);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    public String checkFile(String path) {
        try {
//            Vẫn là khai báo thuật toán, có thể đổi thành md5
            MessageDigest md = MessageDigest.getInstance("MD5");

//            Đọc file vào dis.
            DigestInputStream dis = new DigestInputStream(new BufferedInputStream(new FileInputStream(new File(path))), md);

//            Đọc file như ltm thôi
            byte[] read = new byte[1024];
            int i;
            do {
                i = dis.read(read);
            } while (i != -1);

//            Sau khi đọc xong thì chuyển về BigInteger
            BigInteger num = new BigInteger(1, dis.getMessageDigest().digest());

//            return về cơ số 16
            return num.toString(16);
        } catch (NoSuchAlgorithmException | IOException e) {
            throw new RuntimeException(e);
        }
    }

}
