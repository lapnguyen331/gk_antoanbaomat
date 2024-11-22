package model.tranditionAlgo;

import java.util.Map;
import java.util.Random;

import static model.tranditionAlgo.Caesar.ALPHABET;

public class Vigenere extends ATraditionModel {
    public String key =null;
    public Vigenere(){

    }
    public void loadKey(String key){
        this.key = key;
    }
    public  boolean isValidKey(String key) {
        if (key == null || key.isEmpty()) {
            return false; // Key không được rỗng
        }
        for (char ch : key.toCharArray()) {
            if (!Character.isLetter(ch)) {
                return false; // Key chỉ được chứa chữ cái
            }
        }
        return true; // Key hợp lệ
    }
    public boolean checkKey(){
        if(this.key == null || this.key.isEmpty()){
            return true;
        }
        return false;
    }
    public static String generateKey(int length) {
        // Danh sách chữ cái
        String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        Random random = new Random();
        StringBuilder keyBuilder = new StringBuilder();

        // Tạo key ngẫu nhiên với độ dài yêu cầu
        for (int i = 0; i < length; i++) {
            char randomChar = alphabet.charAt(random.nextInt(alphabet.length()));
            keyBuilder.append(randomChar);
        }

        return keyBuilder.toString();
    }

    public String encrypt(String plaintext) {
        StringBuilder ciphertext = new StringBuilder();
        key = key.toUpperCase();
        plaintext = plaintext.toUpperCase();

        for (int i = 0, j = 0; i < plaintext.length(); i++) {
            char letter = plaintext.charAt(i);

            if (letter < 'A' || letter > 'Z') {
                // Non-alphabetic characters are not changed
                ciphertext.append(letter);
                continue;
            }

            // Shift the plaintext letter by the key letter
            char shift = key.charAt(j % key.length());
            char encryptedLetter = (char)((letter + shift - 2 * 'A') % 26 + 'A');
            ciphertext.append(encryptedLetter);

            j++;
        }

        return ciphertext.toString();
    }

    public  String decrypt(String ciphertext) {
        StringBuilder plaintext = new StringBuilder();
        key = key.toUpperCase();
        ciphertext = ciphertext.toUpperCase();

        for (int i = 0, j = 0; i < ciphertext.length(); i++) {
            char letter = ciphertext.charAt(i);

            if (letter < 'A' || letter > 'Z') {
                // Không thay đổi ký tự không phải chữ cái
                plaintext.append(letter);
                continue;
            }

            // Tính ngược lại giá trị shift từ key
            char shift = key.charAt(j % key.length());
            char decryptedLetter = (char)((letter - shift + 26) % 26 + 'A');
            plaintext.append(decryptedLetter);

            j++;
        }

        return plaintext.toString();
    }

    public static void main(String[] args) {
//        String plaintext = "thiscryptosystemisnotsecure";
//        String keyword = "CIPHER";
//        String ciphertext = encrypt(plaintext);
//
//        System.out.println("Plaintext: " + plaintext);
//        System.out.println("Keyword: " ;
//        System.out.println("Ciphertext: " + ciphertext);
//
//        String decryptedText = decrypt(ciphertext);
//        System.out.println("Decrypted Text: " + decryptedText);
    }

    @Override
    public void saveData(Map<String, Object> data) {
    }

    @Override
    public Map<String, Object> loadData() {
        return null;
    }
}
