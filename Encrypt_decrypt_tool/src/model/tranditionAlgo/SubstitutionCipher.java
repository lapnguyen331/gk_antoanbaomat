package model.tranditionAlgo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class SubstitutionCipher extends ATraditionModel{
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
    String key =null;
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
    public void loadKey(String key){
        this.key = key;
    }

    public  String encrypt( String plaintext) {
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

    public String decrypt( String ciphertext) {
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
    public boolean checkKey(){
        if(this.key == null || this.key.length() == 0 || this.key.isEmpty()){
            return false;
        }
        return true;
    }
    public  boolean checkValidKey(String text){
        return true;
    }


    public static void main(String[] args) {
        SubstitutionCipher sc = new SubstitutionCipher();
        String key = sc.generateKey();
        sc.loadKey(key);
        String en = sc.encrypt("lập dep05 trai 21345678%^^&&vo cung dk -*+-+");
        String des= sc.decrypt(en);
        System.out.println(en);
        System.out.println(des);
    }

    @Override
    public void saveData(Map<String, Object> data) {

    }

    @Override
    public Map<String, Object> loadData() {
        return null;
    }
}
