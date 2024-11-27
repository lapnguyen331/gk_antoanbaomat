package model.hash;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class SHA1 {

    private static SHA1 instance;

    public static SHA1 getInstance() {
        if (instance == null) instance = new SHA1();
        return instance;
    }

    public String check(String data) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-1");
            byte[] output = md.digest(data.getBytes());
            BigInteger num = new BigInteger(1, output);
            return num.toString(16);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    public String checkFile(String path) {
        try (DigestInputStream dis = new DigestInputStream(
                new BufferedInputStream(new FileInputStream(new File(path))),
                MessageDigest.getInstance("SHA-1"))) {

            byte[] read = new byte[1024];
            while (dis.read(read) != -1) {}

            BigInteger num = new BigInteger(1, dis.getMessageDigest().digest());
            return num.toString(16);
        } catch (IOException | NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
}
