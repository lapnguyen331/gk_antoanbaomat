package Controller.traTab;

import UI.CustomDialog;
import UI.tradition.CaesarView;
import model.tranditionAlgo.Caesar;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.util.Map;

public class CaesaController extends CardController {
    Caesar caesar;
    CaesarView caesarView;
    public JButton createKeyBut;
    JTextField field;
    JButton encryptBut;
    JButton decryptBut;
    JTextArea outputTextArea;
    JTextArea inputTextArea;
    JButton chooseKeyBut ;

    public CaesaController() {
        super();
        this.caesarView = new CaesarView();
        super.setView(this.caesarView);
        this.decryptBut = this.caesarView.decryptBut;
        this.createKeyBut = this.caesarView.createKey;
        this.field= this.caesarView.field;
        this.encryptBut= this.caesarView.encryptBut;
        this.outputTextArea= this.caesarView.outputTextArea;
        this.inputTextArea = this.caesarView.inputTextArea;
        this.caesar = new Caesar();
        this.chooseKeyBut = this.caesarView.chooseKey;

        createKeyBut.addActionListener(e -> {
            int keyNum = this.caesar.gennerateKey();
            //set giá trị key vào field
            field.setText(String.valueOf(keyNum));
            field.setEditable(false);
            this.chooseKeyBut.setVisible(true);
            System.out.println("createKey");
        });
        chooseKeyBut.addActionListener(e ->{
            int keyNum = Integer.parseInt(field.getText());
            String key  = this.caesar.loadKey(keyNum);
            System.out.println("chọn key --" +key);
        });
        field.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                if (!caesar.checkValidKey(field.getText())) {
                    // Tạo và hiển thị CustomDialog trong EDT
                    SwingUtilities.invokeLater(() -> {
                        CustomDialog dialog = new CustomDialog(caesarView, "Chỉ có thể nhập SỐ", "Lỗi key");

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
                if(!validateText(inputTextArea,Caesar.ALPHABET)){
                    CustomDialog dialog = new CustomDialog(caesarView, "Kí tự vừa nhập không đúng", "Lỗi nhập ");
                };
            }

            @Override
            public void removeUpdate(DocumentEvent e) {

            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                if(!validateText(inputTextArea,Caesar.ALPHABET)){
                    CustomDialog dialog = new CustomDialog(caesarView, "Kí tự vừa nhập không đúng", "Lỗi nhập ");
                };
            }
        });
        outputTextArea.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                if(!validateText(outputTextArea,Caesar.ALPHABET)){
                    CustomDialog dialog = new CustomDialog(caesarView, "Kí tự vừa nhập không đúng", "Lỗi nhập ");
                };
            }

            @Override
            public void removeUpdate(DocumentEvent e) {

            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                if(!validateText(outputTextArea,Caesar.ALPHABET)){
                    CustomDialog dialog = new CustomDialog(caesarView, "Kí tự vừa nhập không đúng", "Lỗi nhập ");
                };
            }
        });
        decryptBut.addActionListener(e -> {
            String input = inputTextArea.getText();
            if(caesar.checkKey()){
                String cipher = caesar.decrypt(input);
                outputTextArea.setText(cipher);
            }else {
                if(input.isEmpty()){
                    CustomDialog dialog = new CustomDialog(caesarView, "Key rỗng, Bạn cần tạo Key trước", "Lỗi nhập key ");
                }else {
                    CustomDialog dialog = new CustomDialog(caesarView, "Key rỗng, Bạn cần nhấn 'chọn key' trước", "Lỗi nhập key ");

                }
            }

            System.out.println("decrypt");
        });
        encryptBut.addActionListener(e -> {
            String input = inputTextArea.getText();
            if(caesar.checkKey()){
                String cipher = caesar.encrypt(input);
                outputTextArea.setText(cipher);
            }else {
                if(input.isEmpty()){
                    CustomDialog dialog = new CustomDialog(caesarView, "Key rỗng, Bạn cần tạo Key trước", "Lỗi nhập key ");
                }else {
                    CustomDialog dialog = new CustomDialog(caesarView, "Key rỗng, Bạn cần nhấn 'chọn key' trước", "Lỗi nhập key ");

                }
            }
            System.out.println("decrypt");
        });

    }
    public boolean validateText(JTextArea textArea,String ALPHABET){
        String text = textArea.getText();

        // Tạo StringBuilder để lưu các ký tự hợp lệ
        StringBuilder validText = new StringBuilder();
        // Kiểm tra từng ký tự
        for (char c : text.toCharArray()) {
            if (ALPHABET.indexOf(c) >= 0) { // Ký tự hợp lệ
                validText.append(c);
            } else {
                // Hiển thị thông báo lỗi nếu có ký tự không hợp lệ
               return false;
            }
        }

        // Nếu có ký tự không hợp lệ, cập nhật lại JTextArea
        if (!text.equals(validText.toString())) {
            SwingUtilities.invokeLater(() -> textArea.setText(validText.toString()));
        }
        return true;
    }

    @Override
    public Map<String, Object> saveData() {
        return null;
    }

    @Override
    public void loadData() {

    }

    private String encrypt(String input) {
        // Caesar encryption logic
        return input.toUpperCase();
    }


}
