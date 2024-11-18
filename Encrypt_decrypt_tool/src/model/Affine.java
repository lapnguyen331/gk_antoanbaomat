package model;

public class Affine {

    static final int A = 7; // Multiplicative Key
    static final int B = 3; // Additive Key

    // hàm tìm nghịch đảo
    static int modInverse(int a, int m) {
        for (int x = 1; x < m; x++) {
            if (((a % m) * (x % m)) % m == 1) {
                return x;
            }
        }
        return -1; // nếu ko có nghịch đảo
    }
    static String encryptMessage(String pt) {
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

    static String decryptMessage(String ciphertext) {
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

        String et = encryptMessage(pt); // Encrypted Text
        System.out.println("The Encrypted Text: " + et);

        String dt = decryptMessage(et); // Decrypted Text
        System.out.println("The Decrypted Text: " + dt);
    }
}
