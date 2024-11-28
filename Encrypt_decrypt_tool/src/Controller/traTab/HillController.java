package Controller.traTab;

import UI.CustomDialog;
import UI.tradition.HillView;
import model.tranditionAlgo.Hill;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.plaf.basic.DefaultMenuLayout;
import java.util.Map;

public class HillController extends CardController {
    Hill hill;
    HillView hillView;
    public JPanel rightPane;
    public JPanel leftPane;
    public JTextField rowsField; // Trường nhập số dòng
    public JTextField colsField; // Trường nhập số cột
    public JPanel matrixPanel; // Panel chứa ma trận
    public int numRow ;
    public int numCol;
    public JButton encryptButton ;
    public JButton decryptButton ;
    public JTextArea inputTextArea;
    public JTextArea outputTextArea;
    public JButton saveMatrixBut;
    public JButton createMatrix;
    public int[][] matrix;


    public HillController() {
        super();
        this.hillView = new HillView();
        super.setView(this.hillView);
        this.hill = new Hill();
        this.rightPane = this.hillView.rightPane;
        this.leftPane = this.hillView.leftPane;
        this.rowsField= this.hillView.rowsField; // Trường nhập số dòng
        this.colsField = this.hillView.colsField; // Trường nhập số cột
        this.matrixPanel = this.hillView.matrixPanel; // Panel chứa ma trận
        this.numRow = this.hillView.numRow;
        this.numCol = this.hillView.numCol;
        this.encryptButton = this.hillView.encryptButton;
        this.decryptButton = this.hillView.decryptButton;
        this.inputTextArea = this.hillView.inputTextArea;
        this.outputTextArea = this.hillView.outputTextArea;
        this.saveMatrixBut = this.hillView.saveMatrixBut;
        this.createMatrix = this.hillView.createMatrix;
        this.matrix = this.hillView.matrix;

        //set các giá trị mặc định
        setDefaultValue(this.rowsField,this.colsField, 2,2);

        createMatrix.addActionListener(e -> {
            int[][] mstrix = this.hill.generateMatrix(numRow,numCol);
            this.matrix = mstrix;
            this.hillView.setMatrix(this.matrix);
            //set giá trị key vào field
            this.hillView.createMatrixPanel(this.hillView.matrixPanel,this.numRow,this.numCol);
            this.matrixPanel.setEnabled(false);
            System.out.println("createKey");
        });
        DocumentListener dl = new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                updateFieldState();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {


            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                updateFieldState();

            }
            protected void updateFieldState(){
                String text = rowsField.getText();
                if(isInteger(text)){
                    if(Integer.valueOf(text) <= 5) {
                        if (text == null || text.isEmpty()) {
                            rowsField.setText(colsField.getText().trim());

                        } else {
                            colsField.setText(rowsField.getText().trim());
                        }
                        numCol = numRow = Integer.valueOf(text);
                        hillView.createMatrixPanel(hillView.matrixPanel, numRow, numCol);
                    }else {
                        new CustomDialog(hillView,"Phần mềm chỉ hỗ trợ ma trận kích thước dưới 5x5", "lỗi size ma trận");
                    }
                }else {
                    new CustomDialog(hillView,"Lỗi, chỉ được nhập số","Lỗi inout");
                }


            }
        };
        rowsField.getDocument().addDocumentListener(dl);

//        colsField.getDocument().addDocumentListener(new DocumentListener() {
//            @Override
//            public void insertUpdate(DocumentEvent e) {
//                String text = colsField.getText().trim();
//                if(isInteger(text)){
//                    int em = Integer.valueOf(text);
//                    hillView.rowsField.setText(text);
//
//                    hillView.setMatrixSize(em,em);
//                    numRow = em;
//                    numCol = em;
//                    hillView.createMatrixPanel(hillView.matrixPanel,numRow,numCol);
//                }else {
//                    new CustomDialog(hillView,"Lỗi , yêu cầu chỉ nhập số","Lỗi input");
//                }
//
//            }
//
//            @Override
//            public void removeUpdate(DocumentEvent e) {
//
//            }
//
//            @Override
//            public void changedUpdate(DocumentEvent e) {
//
//            }
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
    public void setDefaultValue(JTextField rowT, JTextField colT,int row,int col){
        this.numRow = row;
        this.numCol = col;
        this.hillView.setMatrixSize(row,col);
        rowT.setText(String.valueOf(this.numRow));
        colT.setText(String.valueOf(this.numCol));
        this.hillView.createMatrixPanel(this.matrixPanel,this.numRow,this.numCol);
    }
    public  boolean isInteger(String str) {
        if (str == null) {
            return false;
        }
        int length = str.length();
        if (length == 0) {
            return false;
        }
        int i = 0;
        if (str.charAt(0) == '-') {
            if (length == 1) {
                return false;
            }
            i = 1;
        }
        for (; i < length; i++) {
            char c = str.charAt(i);
            if (c < '0' || c > '9') {
                return false;
            }
        }
        return true;
    }

}
