package Controller.symTab;

import Controller.traTab.CardController;
import UI.CustomDialog;
import UI.sym.AESView;
import model.symAlgo.AES;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.File;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.EventListener;
import java.util.Map;

public class AESController extends CardController {
    AES aes;
    AESView aesView;
    JButton uploadKeyFile;
    JButton cancelUpload;
    JComboBox<String> modeBox;
    JComboBox<String> paddingBox;
    JPanel subHeadPane;
    String[] keyLens;
    JComboBox<String> chooseKeyLengthBox;
    JButton chooseKey;
    JButton createKey;
    JButton saveBut;
    JTextArea keyArea;
    JPanel importKeyPane;
    JPanel leftPane;
    JPanel keyPane;
    JButton encryptBut;
    JButton decryptBut;
    JPanel inPane;
    JPanel outPane;
    JTextArea inputTextArea;
    JTextArea outputTextArea;
    JPanel rightPane;
    JButton downloadBut;

    public AESController() {
        super();
        this.aes = new AES();
        this.aesView = new AESView();
        super.setView(this.aesView);
        this.downloadBut = this.aesView.downloadBut;
        this.uploadKeyFile = this.aesView.uploadKeyFile;
        this.cancelUpload =this.aesView.cancelUpload;
        this.modeBox = this.aesView.modeBox;
        this.paddingBox = this.aesView.paddingBox;
        this.subHeadPane = this.aesView.subHeadPane;
        this.keyLens = this.aesView.keyLens;
        this.chooseKeyLengthBox = this.aesView.chooseKeyLengthBox;
        this.chooseKey = this.aesView.chooseKey;
        this.createKey = this.aesView.createKey;
        this.saveBut = this.aesView.saveBut;
        this.keyArea = this.aesView.keyArea;
        this.importKeyPane = this.aesView.importKeyPane;
        this.leftPane = this.aesView.leftPane;
        this.keyPane = this.aesView.keyPane;
        this.encryptBut = this.aesView.encryptBut;
        this.decryptBut = this.aesView.decryptBut;
        this.inPane = this.aesView.inPane;
        this.outPane = this.aesView.outPane;
        this.inputTextArea = this.aesView.inputTextArea;
        this.outputTextArea = this.aesView.outputTextArea;
        this.rightPane = this.aesView.rightPane;


        //set giá trị mặc định cho mode padding keylength
        setDefaultValue(this.chooseKeyLengthBox,this.modeBox,this.paddingBox);

        createKey.addActionListener(e -> {
            if(!aes.isKeyexist()){
                try {
                    SecretKey key = this.aes.genKey();
                    //set giá trị key vào field
                    keyArea.setText(aes.secretKeyToBase64(key));
                    keyArea.setEditable(false);
//                    this.chooseKeyBut.setVisible(true);
                    System.out.println("createKey");

                } catch (NoSuchAlgorithmException ex) {
                    throw new RuntimeException(ex);
                }

            }
        });
        //cho phép chọn key
        chooseKey.addActionListener(e ->{
            // nếu đã tồn tại thì ko cần
            if(aes.isKeyexist()){

            }else {
                String keyString = keyArea.getText();
                if(aes.checkInputKey(keyString)){
                    try {
                        this.aes.loadKey(aes.base64ToSecretKey(keyString));
                    } catch (NoSuchAlgorithmException ex) {
                        throw new RuntimeException(ex);
                    }
                    System.out.println("chọn key --" +keyString);
                }else {
                    CustomDialog dialog = new CustomDialog(aesView, "Key lỗi, key cần có độ dài" +aes.getLengthKey() +" kí tự hoặc thay đổi độ dài kay", "Lỗi nhập key ");
                }
            }
        });
        saveBut.addActionListener(e ->{
            // nếu đã tồn tại thì ko cần
            //NOTE: chưa handle luu file
            System.out.println("lưu file key");

        });
        encryptBut.addActionListener(e ->{
            String input = inputTextArea.getText();
            if(aes.checkKey()){
                String cipher = null;
                try {
                    cipher = aes.encryptBase64(input);
                } catch (NoSuchPaddingException | NoSuchAlgorithmException | InvalidKeyException |
                         IllegalBlockSizeException | BadPaddingException ex) {
                    new CustomDialog(aesView, "Đã xảy ra lỗi: " + ex.getMessage(), "Lỗi xử lý AES");
                    ex.printStackTrace(); // Ghi ra console để debug (tùy chọn)
                }
                outputTextArea.setText(cipher);
            }else {
                if(input.isEmpty()){
                    CustomDialog dialog = new CustomDialog(aesView, "Key rỗng, Bạn cần tạo/chọn Key trước", "Lỗi nhập key ");
                }else {
                    CustomDialog dialog = new CustomDialog(aesView, "Key lỗi, key cần có độ dài" +aes.getLengthKey() +" kí tự hoặc thay đổi độ dài kay", "Lỗi nhập key ");
                }
            }
            System.out.println("mã hóa");
        });
        decryptBut.addActionListener(e ->{
            String input = inputTextArea.getText();
            if(aes.checkKey()){
                String cipher = null;
                try {
                    cipher = aes.decryptBase64(input);
                } catch (NoSuchPaddingException | NoSuchAlgorithmException | InvalidKeyException |
                         IllegalBlockSizeException | BadPaddingException ex) {
                    new CustomDialog(aesView, "Đã xảy ra lỗi: " + ex.getMessage(), "Lỗi xử lý AES");
                    ex.printStackTrace(); // Ghi ra console để debug (tùy chọn)
                }
                outputTextArea.setText(cipher);
            }else {
                if(input.isEmpty()){
                    CustomDialog dialog = new CustomDialog(aesView, "Key rỗng, Bạn cần tạo/chọn Key trước", "Lỗi nhập key ");
                }else {
                    CustomDialog dialog = new CustomDialog(aesView, "Key lỗi, key cần có độ dài" +aes.getLengthKey() +" kí tự hoặc thay đổi độ dài kay", "Lỗi nhập key ");

                }
            }
            System.out.println("mã hóa");
        });
        chooseKeyLengthBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                // Kiểm tra nếu trạng thái là SELECTED
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    String selectedItem = (String) e.getItem();
                    aes.loadKeyLength(Integer.parseInt(selectedItem));
                    System.out.println("keylen được chọn :" + selectedItem);
                }
            }
        });
        modeBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                // Kiểm tra nếu trạng thái là SELECTED
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    String selectedItem = (String) e.getItem();
                    aes.loadMode(selectedItem);
                    System.out.println("mode được chọn :" + selectedItem);
                }
            }
        });
        paddingBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                // Kiểm tra nếu trạng thái là SELECTED
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    String selectedItem = (String) e.getItem();
                    aes.loadPadding(selectedItem);
                    System.out.println("padding được chọn :" + selectedItem);
                }
            }
        });
        //các nút liên quan đến file
        uploadKeyFile.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(e.getSource() == uploadKeyFile){
                    JFileChooser fileChooser = new JFileChooser();
//                    fileChooser.showOpenDialog(null);
                    int res = fileChooser.showOpenDialog(null);
                    if(res == JFileChooser.FILES_ONLY){
                        File file  = new File(fileChooser.getSelectedFile().getAbsolutePath());
                        System.out.println(file);
                    }
                }
            }
        });

    }
    //chọn độ dài key
    public void setDefaultValue(JComboBox keybox, JComboBox mode,JComboBox padding){
        keybox.setSelectedItem("128");
        mode.setSelectedItem("CBC");
        padding.setSelectedItem("PKCS5Padding");
        this.aes.loadDefaultValue(128,"CBC","PKCS5Padding");
    }

    @Override
    public Map<String, Object> saveData() {
        return null;
    }

    @Override
    public void loadData() {

    }
}
