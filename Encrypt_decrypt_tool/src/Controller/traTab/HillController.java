package Controller.traTab;

import UI.CustomDialog;
import UI.tradition.HillView;
import model.tranditionAlgo.Hill;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.util.Map;

public class HillController extends CardController {
    Hill hill;
    HillView hillView;
    public JButton createKeyBut;
    JTextField field;
    JButton encryptBut;
    JButton decryptBut;
    JTextArea outputTextArea;
    JTextArea inputTextArea;
    JButton chooseKeyBut ;

    public HillController() {
        super();
        this.hillView = new HillView();
        super.setView(this.hillView);
//        this.decryptBut = this.hillView.decryptBut;
//        this.createKeyBut = this.hillView.createKey;
//        this.field = this.hillView.field;
//        this.encryptBut = this.hillView.encryptBut;
//        this.outputTextArea = this.hillView.outputTextArea;
//        this.inputTextArea = this.hillView.inputTextArea;
        this.hill = new Hill();
//        this.chooseKeyBut = this.hillView.chooseKey;

//        createKeyBut.addActionListener(e -> {
//            int keyNum = this.hill.gennerateKey();
//            //set giá trị key vào field
//            field.setText(String.valueOf(keyNum));
//            field.setEditable(false);
//            this.chooseKeyBut.setVisible(true);
//            System.out.println("createKey");
//        });
//        chooseKeyBut.addActionListener(e ->{
//            int keyNum = Integer.parseInt(field.getText());
//            String key  = this.hill.loadKey(keyNum);
//            System.out.println("chọn key --" +key);
//        });
//        field.getDocument().addDocumentListener(new DocumentListener() {
//            @Override
//            public void insertUpdate(DocumentEvent e) {
//                if (!hill.checkValidKey(field.getText())) {
//                    // Tạo và hiển thị CustomDialog trong EDT
//                    SwingUtilities.invokeLater(() -> {
//                        CustomDialog dialog = new CustomDialog(hillView, "Chỉ có thể nhập SỐ", "Lỗi key");
//
//                        // Hiển thị dialog
//                        dialog.setVisible(true); // Chờ đến khi dialog đóng
//                        createKeyBut.setVisible(true);
//                        chooseKeyBut.setVisible(false);
//                        // Sau khi dialog đóng, kiểm tra trạng thái và xử lý
//                        if (dialog.isCloseDialog()) {
//                            field.setText(""); // Reset text field nếu dialog được đóng
//                        }
//
//                    });
//                } else {
//                    SwingUtilities.invokeLater(() -> {
//                        chooseKeyBut.setVisible(true); // Hiển thị button nếu khóa hợp lệ
//                        createKeyBut.setVisible(true);
//                    });
//                }
//
//            }
//
//            @Override
//            public void removeUpdate(DocumentEvent e) {
//
//            }
//            @Override
//            public void changedUpdate(DocumentEvent e) {
//
//            }
//        });
//        inputTextArea.getDocument().addDocumentListener(new DocumentListener() {
//            @Override
//            public void insertUpdate(DocumentEvent e) {
//                if(!validateText(inputTextArea,hill.ALPHABET)){
//                    CustomDialog dialog = new CustomDialog(hillView, "Kí tự vừa nhập không đúng", "Lỗi nhập ");
//                };
//            }
//
//            @Override
//            public void removeUpdate(DocumentEvent e) {
//
//            }
//
//            @Override
//            public void changedUpdate(DocumentEvent e) {
//                if(!validateText(inputTextArea,hill.ALPHABET)){
//                    CustomDialog dialog = new CustomDialog(hillView, "Kí tự vừa nhập không đúng", "Lỗi nhập ");
//                };
//            }
//        });
//        outputTextArea.getDocument().addDocumentListener(new DocumentListener() {
//            @Override
//            public void insertUpdate(DocumentEvent e) {
//                if(!validateText(outputTextArea,hill.ALPHABET)){
//                    CustomDialog dialog = new CustomDialog(hillView, "Kí tự vừa nhập không đúng", "Lỗi nhập ");
//                };
//            }
//
//            @Override
//            public void removeUpdate(DocumentEvent e) {
//
//            }
//
//            @Override
//            public void changedUpdate(DocumentEvent e) {
//                if(!validateText(outputTextArea,hill.ALPHABET)){
//                    CustomDialog dialog = new CustomDialog(hillView, "Kí tự vừa nhập không đúng", "Lỗi nhập ");
//                };
//            }
//        });
//        decryptBut.addActionListener(e -> {
//            String input = inputTextArea.getText();
//            if(hill.checkKey()){
//                String cipher = hill.decrypt(input);
//                outputTextArea.setText(cipher);
//            }else {
//                if(input.isEmpty()){
//                    CustomDialog dialog = new CustomDialog(hillView, "Key rỗng, Bạn cần tạo Key trước", "Lỗi nhập key ");
//                }else {
//                    CustomDialog dialog = new CustomDialog(hillView, "Key rỗng, Bạn cần nhấn 'chọn key' trước", "Lỗi nhập key ");
//
//                }
//            }
//
//            System.out.println("decrypt");
//        });
//        encryptBut.addActionListener(e -> {
//            String input = inputTextArea.getText();
//            if(hill.checkKey()){
//                String cipher = hill.encrypt(input);
//                outputTextArea.setText(cipher);
//            }else {
//                if(input.isEmpty()){
//                    CustomDialog dialog = new CustomDialog(hillView, "Key rỗng, Bạn cần tạo Key trước", "Lỗi nhập key ");
//                }else {
//                    CustomDialog dialog = new CustomDialog(hillView, "Key rỗng, Bạn cần nhấn 'chọn key' trước", "Lỗi nhập key ");
//
//                }
//            }
//            System.out.println("decrypt");
//        });
//
//    }
//    public boolean validateText(JTextArea textArea,String ALPHABET){
//        String text = textArea.getText();
//
//        // Tạo StringBuilder để lưu các ký tự hợp lệ
//        StringBuilder validText = new StringBuilder();
//        // Kiểm tra từng ký tự
//        for (char c : text.toCharArray()) {
//            if (ALPHABET.indexOf(c) >= 0) { // Ký tự hợp lệ
//                validText.append(c);
//            } else {
//                // Hiển thị thông báo lỗi nếu có ký tự không hợp lệ
//                return false;
//            }
//        }
//
//        // Nếu có ký tự không hợp lệ, cập nhật lại JTextArea
//        if (!text.equals(validText.toString())) {
//            SwingUtilities.invokeLater(() -> textArea.setText(validText.toString()));
//        }
//        return true;
//    }
//
//    @Override
//    public Map<String, Object> saveData() {
//        return null;
//    }
//
//    @Override
//    public void loadData() {
//
//    }
//
//    private String encrypt(String input) {
//        // hill encryption logic
//        return input.toUpperCase();
//    }
    }

    @Override
    public Map<String, Object> saveData() {
        return null;
    }

    @Override
    public void loadData() {

    }


}
