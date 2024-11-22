package Controller.traTab;

import UI.CustomDialog;
import UI.tradition.VigenereView;
import model.tranditionAlgo.Vigenere;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.util.Map;

import static model.tranditionAlgo.Caesar.ALPHABET;

public class VigenereController extends CardController {
    Vigenere vigenere;
    VigenereView vigenereView;
    public JButton createKeyBut;
    JTextArea field;
    JButton encryptBut;
    JButton decryptBut;
    JTextArea outputTextArea;
    JTextArea inputTextArea;
    JButton chooseKeyBut ;

    public VigenereController() {
        super();
        this.vigenereView = new VigenereView();
        super.setView(this.vigenereView);
        this.decryptBut = this.vigenereView.decryptBut;
        this.createKeyBut = this.vigenereView.createKey;
        this.field= this.vigenereView.field;
        this.encryptBut= this.vigenereView.encryptBut;
        this.outputTextArea= this.vigenereView.outputTextArea;
        this.inputTextArea = this.vigenereView.inputTextArea;
        this.vigenere = new Vigenere();
        this.chooseKeyBut = this.vigenereView.chooseKey;

        createKeyBut.addActionListener(e -> {
            String key = this.vigenere.generateKey(26);
            //set giá trị key vào field
            field.setText(key);
            field.setEditable(false);
            this.chooseKeyBut.setVisible(true);
            System.out.println("createKey");
        });
        chooseKeyBut.addActionListener(e ->{
            String key = String.valueOf(field.getText());
            this.vigenere.loadKey(key);
            System.out.println("chọn key --" +key);
        });
        field.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                if (!vigenere.isValidKey(field.getText())) {
                    // Tạo và hiển thị CustomDialog trong EDT
                    SwingUtilities.invokeLater(() -> {
                        CustomDialog dialog = new CustomDialog(vigenereView, "Chỉ có thể nhập kí tự, không được có kí tự đặc biệt và khoảng trắng", "Lỗi key");

                        // Hiển thị dialog
                        dialog.setVisible(true); // Chờ đến khi dialog đóng
                        createKeyBut.setVisible(true);
                        chooseKeyBut.setVisible(false);
                        // Sau khi dialog đóng, kiểm tra trạng thái và xử lý
                        if (dialog.isCloseDialog()) {
                            field.setText(""); // Reset text field nếu dialog được đóng
                        }

                    });
                } else {
                    SwingUtilities.invokeLater(() -> {
                        chooseKeyBut.setVisible(true); // Hiển thị button nếu khóa hợp lệ
                        createKeyBut.setVisible(true);
                    });
                }

            }

            @Override
            public void removeUpdate(DocumentEvent e) {

            }
            @Override
            public void changedUpdate(DocumentEvent e) {

            }
        });
        inputTextArea.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {

            }

            @Override
            public void removeUpdate(DocumentEvent e) {

            }

            @Override
            public void changedUpdate(DocumentEvent e) {
            }
        });
        outputTextArea.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {

            }

            @Override
            public void removeUpdate(DocumentEvent e) {

            }

            @Override
            public void changedUpdate(DocumentEvent e) {

            }
        });
        decryptBut.addActionListener(e -> {
            String input = inputTextArea.getText();
            if(!vigenere.checkKey()){
                String cipher = vigenere.decrypt(input);
                outputTextArea.setText(cipher);
            }else {
                if(input.isEmpty()){
                    CustomDialog dialog = new CustomDialog(vigenereView, "Key rỗng, Bạn cần tạo Key trước", "Lỗi nhập key ");
                }else {
                    CustomDialog dialog = new CustomDialog(vigenereView, "Key rỗng, Bạn cần nhấn 'chọn key' trước", "Lỗi nhập key ");

                }
            }

            System.out.println("decrypt");
        });
        encryptBut.addActionListener(e -> {
            String input = inputTextArea.getText();
            if(!vigenere.checkKey()){
                String cipher = vigenere.encrypt(input);
                outputTextArea.setText(cipher);
            }else {
                if(input.isEmpty()){
                    CustomDialog dialog = new CustomDialog(vigenereView, "Key rỗng, Bạn cần tạo Key trước", "Lỗi nhập key ");
                }else {
                    CustomDialog dialog = new CustomDialog(vigenereView, "Key rỗng, Bạn cần nhấn 'chọn key' trước", "Lỗi nhập key ");

                }
            }
            System.out.println("decrypt");
        });
    }


    @Override
    public Map<String, Object> saveData() {
        return null;
    }

    @Override
    public void loadData() {

    }

    private String encrypt(String input) {
        // vigenere encryption logic
        return input.toUpperCase();
    }


}
