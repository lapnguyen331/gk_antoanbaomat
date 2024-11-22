package Controller.traTab;

import UI.CustomDialog;
import UI.tradition.AffineView;
import model.tranditionAlgo.Affine;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.util.Map;

public class AffineController extends CardController {
    Affine affine;
    AffineView affineView;
    public JButton createKeyBut;
    JTextField fieldA;
    JTextField fieldB;

    JButton encryptBut;
    JButton decryptBut;
    JTextArea outputTextArea;
    JTextArea inputTextArea;
    JButton chooseKeyBut ;

    public AffineController() {
        super();
        this.affineView = new AffineView();
        super.setView(this.affineView);
        this.decryptBut = this.affineView.decryptBut;
        this.createKeyBut = this.affineView.createKey;
        this.fieldA= this.affineView.fieldA;
        this.fieldB= this.affineView.fieldB;

        this.encryptBut= this.affineView.encryptBut;
        this.outputTextArea= this.affineView.outputTextArea;
        this.inputTextArea = this.affineView.inputTextArea;
        this.affine = new Affine();
        this.chooseKeyBut = this.affineView.chooseKey;

        createKeyBut.addActionListener(e -> {
            int[] keyPair = Affine.generateRandomCoprimePair(26);
            int keyNumA = keyPair[0];
            int keyNumB = keyPair[1];
            //load key
            //set giá trị key vào field
            fieldA.setText(String.valueOf(keyNumA));
            fieldA.setEditable(false);

            fieldB.setText(String.valueOf(keyNumB));
            fieldB.setEditable(false);
            this.chooseKeyBut.setVisible(true);
            System.out.println("createKey");
        });
        chooseKeyBut.addActionListener(e ->{
            int keyNumA = Integer.parseInt(fieldA.getText());
            int keyNumB = Integer.parseInt(fieldB.getText());
            if(Affine.areCoprime(keyNumA,keyNumB)){
                this.affine.loadKey(keyNumA,keyNumB);
                System.out.println("cùng");
            }else {
                System.out.println("ko");

                SwingUtilities.invokeLater(() -> {
                            CustomDialog dialog = new CustomDialog(affineView, "Chỉ có thể nhập cặp số nguyên tố cùng nhau !", "Lỗi key");

                            // Hiển thị dialog
                            dialog.setVisible(true); // Chờ đến khi dialog đóng
                            createKeyBut.setVisible(true);
                            chooseKeyBut.setVisible(false);
                            // Sau khi dialog đóng, kiểm tra trạng thái và xử lý
                            if (dialog.isCloseDialog()) {
                                fieldA.setText(""); // Reset text field nếu dialog được đóng
                            }

                });
            }
        });
        fieldA.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                if (!affine.checkNumValidate(fieldA.getText())) {
                    // Tạo và hiển thị CustomDialog trong EDT
                    SwingUtilities.invokeLater(() -> {
                        CustomDialog dialog = new CustomDialog(affineView, "Chỉ có thể nhập SỐ", "Lỗi key");

                        // Hiển thị dialog
                        dialog.setVisible(true); // Chờ đến khi dialog đóng
                        createKeyBut.setVisible(true);
                        chooseKeyBut.setVisible(false);
                        // Sau khi dialog đóng, kiểm tra trạng thái và xử lý
                        if (dialog.isCloseDialog()) {
                            fieldA.setText(""); // Reset text field nếu dialog được đóng
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
        fieldB.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                if (!affine.checkNumValidate(fieldB.getText())) {
                    // Tạo và hiển thị CustomDialog trong EDT
                    SwingUtilities.invokeLater(() -> {
                        CustomDialog dialog = new CustomDialog(affineView, "Chỉ có thể nhập số và nhỏ hơn 26", "Lỗi key");
                        // Hiển thị dialog
                        dialog.setVisible(true); // Chờ đến khi dialog đóng
                        createKeyBut.setVisible(true);
                        chooseKeyBut.setVisible(false);
                        // Sau khi dialog đóng, kiểm tra trạng thái và xử lý
                        if (dialog.isCloseDialog()) {
                            fieldA.setText(""); // Reset text field nếu dialog được đóng
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
                if(!validateAndCleanText(inputTextArea)){
                    CustomDialog dialog = new CustomDialog(affineView, "Kí tự vừa nhập không đúng", "Lỗi nhập ");
                };
            }

            @Override
            public void removeUpdate(DocumentEvent e) {

            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                if(!validateAndCleanText(inputTextArea)){
                    CustomDialog dialog = new CustomDialog(affineView, "Kí tự vừa nhập không đúng", "Lỗi nhập ");
                };
            }
        });
        outputTextArea.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                if(!validateAndCleanText(outputTextArea)){
                    CustomDialog dialog = new CustomDialog(affineView, "Kí tự vừa nhập không đúng", "Lỗi nhập ");
                };
            }

            @Override
            public void removeUpdate(DocumentEvent e) {

            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                if(!validateAndCleanText(outputTextArea)){
                    CustomDialog dialog = new CustomDialog(affineView, "Kí tự vừa nhập không đúng", "Lỗi nhập ");
                };
            }
        });
        decryptBut.addActionListener(e -> {
            String input = inputTextArea.getText();
            if(affine.checkKey()){
                String cipher = affine.decryptMessage(input);
                outputTextArea.setText(cipher);
            }else {
                if(input.isEmpty()){
                    CustomDialog dialog = new CustomDialog(affineView, "Key rỗng, Bạn cần tạo Key trước", "Lỗi nhập key ");
                }else {
                    CustomDialog dialog = new CustomDialog(affineView, "Key rỗng, Bạn cần nhấn 'chọn key' trước", "Lỗi nhập key ");

                }
            }

            System.out.println("decrypt");
        });
        encryptBut.addActionListener(e -> {
            String input = inputTextArea.getText();
            if(affine.checkKey()){
                String cipher = affine.encryptMessage(input);
                outputTextArea.setText(cipher);
            }else {
                if(input.isEmpty()){
                    CustomDialog dialog = new CustomDialog(affineView, "Key rỗng, Bạn cần tạo Key trước", "Lỗi nhập key ");
                }else {
                    CustomDialog dialog = new CustomDialog(affineView, "Key rỗng, Bạn cần nhấn 'chọn key' trước", "Lỗi nhập key ");

                }
            }
            System.out.println("encrypt");
        });

    }
    public boolean validateAndCleanText(JTextArea textArea) {
        String text = textArea.getText();
        StringBuilder validText = new StringBuilder();

        boolean hasInvalidCharacter = false;

        // Kiểm tra từng ký tự và chỉ giữ lại các ký tự hợp lệ
        for (char ch : text.toCharArray()) {
            if (isAlphabetCharacter(ch)) {
                validText.append(ch);
            } else {
                hasInvalidCharacter = true; // Đánh dấu phát hiện ký tự không hợp lệ
            }
        }

        // Nếu có ký tự không hợp lệ, cập nhật lại JTextArea
        if (hasInvalidCharacter) {
            SwingUtilities.invokeLater(() -> textArea.setText(validText.toString()));
        }

        // Trả về true nếu toàn bộ text hợp lệ, false nếu có ký tự không hợp lệ
        return !hasInvalidCharacter;
    }

    // Hàm kiểm tra ký tự có thuộc bảng chữ cái hoặc là khoảng trắng
    private boolean isAlphabetCharacter(char ch) {
        return Character.isLetter(ch) || ch == ' '; // Chấp nhận chữ cái và khoảng trắng
    }


    @Override
    public Map<String, Object> saveData() {
        return null;
    }

    @Override
    public void loadData() {

    }

    private String encrypt(String input) {
        // affine encryption logic
        return input.toUpperCase();
    }
}
