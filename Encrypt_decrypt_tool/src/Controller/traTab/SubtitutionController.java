package Controller.traTab;

import UI.CustomDialog;
import UI.tradition.SubtitutionView;
import model.tranditionAlgo.SubstitutionCipher;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.util.Map;

public class SubtitutionController extends CardController {
    SubstitutionCipher substitutionCipher;
    SubtitutionView substitutionView;
    public JButton createKeyBut;
    JTextArea field;
    JButton encryptBut;
    JButton decryptBut;
    JTextArea outputTextArea;
    JTextArea inputTextArea;
    JButton chooseKeyBut ;

    public SubtitutionController() {
        super();
        this.substitutionView = new SubtitutionView();
        super.setView(this.substitutionView);
        this.decryptBut = this.substitutionView.decryptBut;
        this.createKeyBut = this.substitutionView.createKey;
        this.field= this.substitutionView.field;
        this.encryptBut= this.substitutionView.encryptBut;
        this.outputTextArea= this.substitutionView.outputTextArea;
        this.inputTextArea = this.substitutionView.inputTextArea;
        this.substitutionCipher = new SubstitutionCipher();
        this.chooseKeyBut = this.substitutionView.chooseKey;

        createKeyBut.addActionListener(e -> {
            String key = this.substitutionCipher.generateKey();
            //set giá trị key vào field
            field.setText(String.valueOf(key));
            field.setEditable(false);
            this.chooseKeyBut.setVisible(true);
            System.out.println("createKey");
        });
        chooseKeyBut.addActionListener(e ->{
            String key = String.valueOf(field.getText());
            this.substitutionCipher.loadKey(key);
            System.out.println("chọn key --" +key);
        });
        field.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                chooseKeyBut.setVisible(true); // Hiển thị button nếu khóa hợp lệ
                createKeyBut.setVisible(true);
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
            if(substitutionCipher.checkKey()){
                String cipher = substitutionCipher.decrypt(input);
                outputTextArea.setText(cipher);
            }else {
                if(input.isEmpty()){
                    CustomDialog dialog = new CustomDialog(substitutionView, "Key rỗng, Bạn cần tạo Key trước", "Lỗi nhập key ");
                }else {
                    CustomDialog dialog = new CustomDialog(substitutionView, "Key rỗng, Bạn cần nhấn 'chọn key' trước", "Lỗi nhập key ");

                }
            }

            System.out.println("decrypt");
        });
        encryptBut.addActionListener(e -> {
            String input = inputTextArea.getText();
            if(substitutionCipher.checkKey()){
                String cipher = substitutionCipher.encrypt(input);
                outputTextArea.setText(cipher);
            }else {
                if(input.isEmpty()){
                    CustomDialog dialog = new CustomDialog(substitutionView, "Key rỗng, Bạn cần tạo Key trước", "Lỗi nhập key ");
                }else {
                    CustomDialog dialog = new CustomDialog(substitutionView, "Key rỗng, Bạn cần nhấn 'chọn key' trước", "Lỗi nhập key ");

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
}
