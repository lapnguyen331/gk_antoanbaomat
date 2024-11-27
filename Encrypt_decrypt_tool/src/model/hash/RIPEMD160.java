package model.hash;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

import java.math.BigInteger;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.Security;
import java.io.*;

public class RIPEMD160 {

    private static RIPEMD160 instance;

    public static RIPEMD160 getInstance() {
        if (instance == null) instance = new RIPEMD160();
        return instance;
    }

    private RIPEMD160() {
        // Đăng ký Bouncy Castle Provider
        Security.addProvider(new BouncyCastleProvider());
    }

    public String check(String data) {
        try {
            MessageDigest md = MessageDigest.getInstance("RIPEMD160");
            byte[] output = md.digest(data.getBytes());
            BigInteger num = new BigInteger(1, output);
            return String.format("%040x", num);  // Đảm bảo chuỗi Hex đúng độ dài 40 ký tự
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("RIPEMD-160 không được hỗ trợ", e);
        }
    }

    public String checkFile(String path) {
        try (DigestInputStream dis = new DigestInputStream(
                new BufferedInputStream(new FileInputStream(new File(path))),
                MessageDigest.getInstance("RIPEMD160"))) {

            byte[] read = new byte[1024];
            while (dis.read(read) != -1) {}

            BigInteger num = new BigInteger(1, dis.getMessageDigest().digest());
            return String.format("%040x", num);  // Đảm bảo chuỗi Hex đúng độ dài
        } catch (IOException | NoSuchAlgorithmException e) {
            throw new RuntimeException("Lỗi trong quá trình băm tệp với RIPEMD-160", e);
        }
    }
}
