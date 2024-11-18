package model;

public class Vigenere {

    public static String encrypt(String plaintext, String keyword) {
        StringBuilder ciphertext = new StringBuilder();
        keyword = keyword.toUpperCase();
        plaintext = plaintext.toUpperCase();

        for (int i = 0, j = 0; i < plaintext.length(); i++) {
            char letter = plaintext.charAt(i);

            if (letter < 'A' || letter > 'Z') {
                // Non-alphabetic characters are not changed
                ciphertext.append(letter);
                continue;
            }

            // Shift the plaintext letter by the keyword letter
            char shift = keyword.charAt(j % keyword.length());
            char encryptedLetter = (char)((letter + shift - 2 * 'A') % 26 + 'A');
            ciphertext.append(encryptedLetter);

            j++;
        }

        return ciphertext.toString();
    }


    public static void main(String[] args) {
        String plaintext = "thiscryptosystemisnotsecure";
        String keyword = "CIPHER";
        String ciphertext = encrypt(plaintext, keyword);

        System.out.println("Plaintext: " + plaintext);
        System.out.println("Keyword: " + keyword);
        System.out.println("Ciphertext: " + ciphertext);
    }
}
