package model.symAlgo;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.SecureRandom;
import java.util.Random;

public class XORCipher {

    private String key = "";

    private static XORCipher instance;

    public static XORCipher getInstance() {
        if (instance == null) {
            instance = new XORCipher();
        }
        return instance;
    }

    public void setKeyToDefault() {
        key = "";
    }
    public void generateKey() {
        key = generateRandomString(new Random().nextInt(6) + 5);
    }
    public String generateRandomString(int length) {
        // Chuỗi chứa tất cả các ký tự mà bạn muốn sử dụng trong chuỗi ngẫu nhiên
        String characters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        StringBuilder randomString = new StringBuilder();

        SecureRandom random = new SecureRandom();

        for (int i = 0; i < length; i++) {
            int randomIndex = random.nextInt(characters.length());
            char randomChar = characters.charAt(randomIndex);
            randomString.append(randomChar);
        }

        return randomString.toString();
    }
    public void createKey(String key) {
        this.key = key;
    }
    public String encrypt(String text) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < text.length(); i++) {
            char charXOR = (char) (text.charAt(i) ^ key.charAt(i % key.length()));
            result.append(String.format("%02x", (int) charXOR)); // Chuyển đổi sang hex
        }
        return result.toString();
    }

    public String decrypt(String text) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < text.length(); i += 2) {
            String hexByte = text.substring(i, i + 2);
            int intValue = Integer.parseInt(hexByte, 16);
            char charXOR = (char) (intValue ^ key.charAt(i / 2 % key.length()));
            result.append(charXOR);
        }
        return result.toString();
    }

    public void encryptFile(String srcFilePath, String desFilePath) {
        try {
            byte[] fileBytes = Files.readAllBytes(Paths.get(srcFilePath));
            byte[] encryptedBytes = encryptBytes(fileBytes);
            Files.write(Paths.get(desFilePath), encryptedBytes);
            System.out.println("File encrypted successfully!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void decryptFile(String srcFilePath, String desFilePath) {
        try {
            byte[] encryptedBytes = Files.readAllBytes(Paths.get(srcFilePath));
            byte[] decryptedBytes = decryptBytes(encryptedBytes);
            Files.write(Paths.get(desFilePath), decryptedBytes);
            System.out.println("File decrypted successfully!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private byte[] encryptBytes(byte[] inputBytes) {
        byte[] result = new byte[inputBytes.length];
        for (int i = 0; i < inputBytes.length; i++) {
            result[i] = (byte) (inputBytes[i] ^ key.charAt(i % key.length()));
        }
        return result;
    }

    private byte[] decryptBytes(byte[] inputBytes) {
        byte[] result = new byte[inputBytes.length];
        for (int i = 0; i < inputBytes.length; i++) {
            result[i] = (byte) (inputBytes[i] ^ key.charAt(i % key.length()));
        }
        return result;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public static void main(String[] args) {
        XORCipher x = new XORCipher();
        x.generateKey();
        String en = x.encrypt("hehe");
        String de = x.decrypt(en);
        System.out.println("en " + en);
        System.out.println("de " + de);

    }
}
