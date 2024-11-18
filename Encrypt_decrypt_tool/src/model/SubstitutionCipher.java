package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SubstitutionCipher {
//    template
//    public void generateKey(int key){
//
//    }
//    public void loadKey(int key){
//
//    }
//    public void encrypt(){
//
//    }
//    public void decrypt(){
//
//    }
    public static final String P = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789 *#@!$%^&*()_-=+';:,.?/><`~/+";

    public String generateKey(){
        List<Character> characters = new ArrayList<>();
        for (char c : P.toCharArray()) {
            characters.add(c);
        }
        Collections.shuffle(characters);
        StringBuilder key = new StringBuilder();
        for (char c : characters) {
            key.append(c);
        }
        return key.toString();
    }
    public void loadKey(int key){

    }

    public  String encrypt(String key, String plaintext) {
        StringBuilder ciphertext = new StringBuilder();
        for (int i = 0; i < plaintext.length(); i++) {
            char character = plaintext.charAt(i);
            int index = P.indexOf(character);
            if (index != -1) {
                ciphertext.append(key.charAt(index));
            } else {
                ciphertext.append(character);
            }
        }
        return ciphertext.toString();
    }

    public String decrypt(String key, String ciphertext) {
        StringBuilder plaintext = new StringBuilder();
        for (int i = 0; i < ciphertext.length(); i++) {
            char character = ciphertext.charAt(i);
            int index = key.indexOf(character);
            if (index != -1) {
                plaintext.append(P.charAt(index));
            } else {
                plaintext.append(character);
            }
        }
        return plaintext.toString();
    }


    public static void main(String[] args) {
        SubstitutionCipher sc = new SubstitutionCipher();
        String key = sc.generateKey();
        System.out.println(key);
        String en = sc.encrypt(key,"lập dep05 trai 21345678%^^&&vo cung dk -*+-+");
        String des= sc.decrypt(key,en);
        System.out.println(en);
        System.out.println(des);
    }
}
