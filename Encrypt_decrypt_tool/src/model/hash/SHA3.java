package model.hash;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class SHA3 {

    private static SHA3 instance;

    public static SHA3 getInstance() {
        if (instance == null) instance = new SHA3();
        return instance;
    }

    public String check(String data) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA3-256");
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
                MessageDigest.getInstance("SHA3-256"))) {

            byte[] read = new byte[1024];
            while (dis.read(read) != -1) {}

            BigInteger num = new BigInteger(1, dis.getMessageDigest().digest());
            return num.toString(16);
        } catch (IOException | NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
}