package model;

import java.util.Random;

//mã hóa dịch chuyển
public class Caesar {
//    đã handle 1 số kí tự đặc biệt và khoảng trắng
//public static final String ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789 *#@!$%^&*()_-=+';:,.?/><`~/+";

    public static final String ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz ";
    private int key = 0;
    public Caesar(){

    }
    public int gennerateKey(){
        Random random = new Random();
        return random.nextInt();// đã xử lí khi ra khỏi range anphabet.length

    }
    public String loadKey(){
        key = gennerateKey();
        return String.valueOf(key);
    }
    public String encrypt(String message, int shiftKey) {
        char[] temp = message.toCharArray();
        StringBuilder cipherText = new StringBuilder();
        for (int ii = 0; ii < temp.length; ii++) {
            int charPosition = ALPHABET.indexOf(temp[ii]);
            if (charPosition == -1) { // Nếu ký tự không có trong ALPHABET, giữ nguyên
                cipherText.append(temp[ii]);
                continue;
            }
            int keyVal = (shiftKey + charPosition) % ALPHABET.length();
            char replaceVal = ALPHABET.charAt(keyVal);
            cipherText.append(replaceVal);
        }
        return cipherText.toString();
    }

    public String decrypt(String cipherText, int shiftKey) {
        char[] temp = cipherText.toCharArray();
        StringBuilder message = new StringBuilder();
        for (int ii = 0; ii < temp.length; ii++) {
            int charPosition = ALPHABET.indexOf(temp[ii]);
            if (charPosition == -1) { // Nếu ký tự không có trong ALPHABET, giữ nguyên
                message.append(temp[ii]);
                continue;
            }
            int keyVal = (charPosition - shiftKey) % ALPHABET.length();
            if (keyVal < 0) { // Đảm bảo keyVal không âm
                keyVal += ALPHABET.length();
            }
            char replaceVal = ALPHABET.charAt(keyVal);
            message.append(replaceVal);
        }
        return message.toString();
    }


    public static void main(String[] args) {
        Caesar c = new Caesar();
        c.loadKey();
        System.out.println(c.key);
        String en = c.encrypt("LÂp vô cung *** ^^^)))       ****+}}}}dep trai000111222333", c.key);
        String de = c.decrypt(en, c.key);

        System.out.println("ecrypt:"+en);
        System.out.println("des:"+de);

    }
}
