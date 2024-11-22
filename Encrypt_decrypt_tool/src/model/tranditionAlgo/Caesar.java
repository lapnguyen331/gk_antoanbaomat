package model.tranditionAlgo;

import javax.swing.*;
import java.util.Map;
import java.util.Random;

//mã hóa dịch chuyển
public class Caesar extends ATraditionModel {
//    đã handle 1 số kí tự đặc biệt và khoảng trắng
//public static final String ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789 *#@!$%^&*()_-=+';:,.?/><`~/+";

    public static final String ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz ";
    private int key = 0;
    public Caesar(){

    }
    public int gennerateKey(){
        Random random = new Random();
        return random.nextInt(-500, 4000);// đã xử lí khi ra khỏi range anphabet.length

    }
    public String loadKey(int inputkey){
        key = inputkey;
        return String.valueOf(key);
    }
    public  boolean checkValidKey(String input){
        if (input == null) {
            return false;
        }
        try {
            int d = Integer.parseInt(input);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }
    public boolean checkKey(){
        if(this.key  != 0){
            return true;
        }
        return false;
    }
    public String encrypt(String message) {
        char[] temp = message.toCharArray();
        StringBuilder cipherText = new StringBuilder();
        for (int ii = 0; ii < temp.length; ii++) {
            int charPosition = ALPHABET.indexOf(temp[ii]);
            if (charPosition == -1) { // Nếu ký tự không có trong ALPHABET, giữ nguyên
                cipherText.append(temp[ii]);
                continue;
            }
            int keyVal = (this.key + charPosition) % ALPHABET.length();
            char replaceVal = ALPHABET.charAt(keyVal);
            cipherText.append(replaceVal);
        }
        return cipherText.toString();
    }

    public String decrypt(String cipherText) {
        char[] temp = cipherText.toCharArray();
        StringBuilder message = new StringBuilder();
        for (int ii = 0; ii < temp.length; ii++) {
            int charPosition = ALPHABET.indexOf(temp[ii]);
            if (charPosition == -1) { // Nếu ký tự không có trong ALPHABET, giữ nguyên
                message.append(temp[ii]);
                continue;
            }
            int keyVal = (charPosition - this.key) % ALPHABET.length();
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
        c.loadKey(15);
        System.out.println(c.key);
        String en = c.encrypt("LÂp vô cung *** ^^^)))       ****+}}}}dep trai000111222333");
        String de = c.decrypt(en);

        System.out.println("ecrypt:"+en);
        System.out.println("des:"+de);

    }

    @Override
    public void saveData(Map<String, Object> data) {

    }

    @Override
    public Map<String, Object> loadData() {
        return null;
    }
}
