package model.tranditionAlgo;

import java.util.Map;
import java.util.Random;

public class Affine extends ATraditionModel{

     int A = 0; // Multiplicative Key
     int B = 0; // Additive Key
    // Tìm GCD của hai số
    public static int gcd(int a, int b) {
        while (b != 0) {
            int temp = b;
            b = a % b;
            a = temp;
        }
        return a;
    }

    // Kiểm tra nếu a và b là nguyên tố cùng nhau (GCD(a, b) = 1)
    public static boolean areCoprime(int a, int b) {
        return gcd(a, b) == 1;
    }
    // Tạo cặp số nguyên tố cùng nhau ngẫu nhiên
    public static int[] generateRandomCoprimePair(int maxValue) {
        Random random = new Random();
        int a, b;
        do {
            a = random.nextInt(maxValue) + 1; // Tạo số ngẫu nhiên trong phạm vi [1, maxValue]
            b = random.nextInt(maxValue) + 1; // Tạo số ngẫu nhiên trong phạm vi [1, maxValue]
        } while (!areCoprime(a, b)); // Kiểm tra nếu a và b là nguyên tố cùng nhau

        return new int[]{a, b};
    }
    public void loadKey(int a , int b){
        this.A = a;
        this.B = b;
    }


    // hàm tìm nghịch đảo
    public int modInverse(int a, int m) {
        for (int x = 1; x < m; x++) {
            if (((a % m) * (x % m)) % m == 1) {
                return x;
            }
        }
        return -1; // nếu ko có nghịch đảo
    }
    public  String encryptMessage(String pt) {
        StringBuilder ciphertext = new StringBuilder();
        for (int i = 0; i < pt.length(); i++) {
            char ch = pt.charAt(i);
            if (Character.isLetter(ch)) {
                if (Character.isUpperCase(ch)) {
                    ciphertext.append((char) ('A' + (A * (ch - 'A') + B) % 26));
                } else {
                    ciphertext.append((char) ('a' + (A * (ch - 'a') + B) % 26));
                }
            } else {
                ciphertext.append(ch);
            }
        }
        return ciphertext.toString();
    }

    public String decryptMessage(String ciphertext) {
        StringBuilder pt = new StringBuilder();
        int aInverse = modInverse(A, 26);
        if (aInverse == -1) {
            return "Inverse doesn't exist";
        }
        for (int i = 0; i < ciphertext.length(); i++) {
            char ch = ciphertext.charAt(i);
            if (Character.isLetter(ch)) {
                if (Character.isUpperCase(ch)) {
                    int x = (aInverse * (ch - 'A' - B + 26)) % 26;
                    pt.append((char) ('A' + x));
                } else {
                    int x = (aInverse * (ch - 'a' - B + 26)) % 26;
                    pt.append((char) ('a' + x));
                }
            } else {
                pt.append(ch);
            }
        }
        return pt.toString();
    }

    public static void main(String[] args) {
        String pt = "KHOA CONG NGHE THONG TIN";
        Affine affine = new Affine();
        int[] coprimePair = generateRandomCoprimePair(26);
        int a = coprimePair[0];
        int b = coprimePair[1];
        affine.loadKey(a,b);
        System.out.println(a + "/"+b);
        String et = affine.encryptMessage(pt); // Encrypted Text
        System.out.println("The Encrypted Text: " + et);

        String dt = affine.decryptMessage(et); // Decrypted Text
        System.out.println("The Decrypted Text: " + dt);
    }
    public boolean checkNumValidate(String input){
        if (input == null) {
            return false;
        }
        try {
            int d = Integer.parseInt(input);
            if(d > 26) return false;
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }
    public boolean checkKey(){
        if( this.A ==0 || this.B ==0){
            return false;
        }
        return true;
    }


    @Override
    public void saveData(Map<String, Object> data) {

    }

    @Override
    public Map<String, Object> loadData() {
        return null;
    }
}
