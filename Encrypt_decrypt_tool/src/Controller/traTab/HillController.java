package Controller.traTab;

import UI.CustomDialog;
import UI.tradition.HillView;
import model.tranditionAlgo.Hill;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.plaf.basic.DefaultMenuLayout;
import java.awt.*;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
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
    public JButton chooseMatrixBut;


    public HillController() {
        super();
        this.hillView = new HillView();
        super.setView(this.hillView);
        this.hill = new Hill();
        this.chooseMatrixBut = this.hillView.chooseMAtrixBut;
        this.rightPane = this.hillView.rightPane;
        this.createMatrix = this.hillView.createMatrix;
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

        //set các giá trị mặc định
        setDefaultValue(this.rowsField,this.colsField, 2,2);

        createMatrix.addActionListener(e -> {
            int[][] mstrix = this.hill.generateMatrix(numRow);
            this.hillView.setMatrix(mstrix);
            //set giá trị key vào field
            this.hillView.createMatrixPanel(this.hillView.matrixPanel,this.numRow,this.numCol);
            this.matrixPanel.setEnabled(false);
            System.out.println("createKey");
        });
        DocumentListener dl = new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                updateFieldState(e);
            }

            @Override
            public void removeUpdate(DocumentEvent e) {


            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                updateFieldState(e);

            }
            protected void updateFieldState(DocumentEvent e){
                Object source = e.getDocument();
                if(source == rowsField.getDocument()){
                    String text = rowsField.getText();
                    if(isInteger(text)){
                        if(Integer.valueOf(text) <= 5) {
                            if (text == null || text.isEmpty()) {
                                rowsField.setText(colsField.getText().trim());

                            } else {
                                colsField.setText(rowsField.getText().trim());
                            }
                            numCol = numRow = Integer.valueOf(text);
                            hillView.setMatrix(new int[numRow][numCol]); //reset lại giá trị matrix
                            hill.setMatrixKey(new int[numRow][numCol]); //reset lại giá trị matrix
                            hillView.createMatrixPanel(hillView.matrixPanel, numRow, numCol);
                        }else {
                            new CustomDialog(hillView,"Phần mềm chỉ hỗ trợ ma trận kích thước dưới 5x5", "lỗi size ma trận");
                        }
                    }else {
                        new CustomDialog(hillView,"Lỗi, chỉ được nhập số","Lỗi inout");
                    }
                }
            }
        };

        rowsField.getDocument().addDocumentListener(dl);

        chooseMatrixBut.addActionListener(e ->{
            int[][] ma = new int[][]{};

            ma = this.hillView.getMatrixFromPanel(this.hillView.matrixPanel,this.numRow,this.numCol);
            if(ma == null){
                new CustomDialog(hillView,"Ma trận chỉ nhận số, yêu cầu xóa nhập lại","Lỗi nhập ma trận");
            }else {
                if(hill.checkMatrix(ma)){
                    this.hill.setMatrixKey(ma);
                    print2D(this.hill.matrixKey);
                    System.out.println("choose");
                    new CustomDialog(hillView,"Đã chọn matrix","chọn matrix success");
                }else {
                    new CustomDialog(hillView,"Ma trận không khả nghịch, yên cầu nhập và chọn ma trận khác","Lỗi nhập ma trận",400,100);
                }

            }

        });
        saveMatrixBut.addActionListener(e ->{
            // Lấy khóa từ JTextArea
            if (this.hill.matrixKey  == null) {
                new CustomDialog(hillView,"ma trân trống. Muốn lưu ma trận trước tiên cần nhập vào","Lỗi lưu key");
                return;
            }

            // Mở JFileChooser để chọn tệp lưu khóa
            JFileChooser fileChooser = new JFileChooser();
            int returnValue = fileChooser.showSaveDialog(hillView);

            // Nếu người dùng chọn tệp
            if (returnValue == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                // Lưu khóa vào tệp đã chọn
                saveMatrixToFile(this.hill.matrixKey,selectedFile.getAbsolutePath());

                // Hiển thị thông báo thành công
                JOptionPane.showMessageDialog(hillView, "ma trận lưu vào:  " + selectedFile.getAbsolutePath(), "Success", JOptionPane.INFORMATION_MESSAGE);
            }
            // Hiển thị thông báo thành công
            new CustomDialog(hillView,"lưu ma trận thành công","lưu key");
        });
        //gắn sự kiện vào các cell matrix
        attachListenersToMatrix();


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
        encryptButton.addActionListener(e -> {
            String input = inputTextArea.getText();
            if(hill.checkMatrix(this.hill.matrixKey)){
                String cipher = hill.encrypt(input,this.hill.matrixKey);
                outputTextArea.setText(cipher);
            }else {
                if(input.isEmpty()){
                    CustomDialog dialog = new CustomDialog(hillView, "Ma trận rỗng, Bạn cần tạo/chọn ma trận trước", "Lỗi nhập key ");
                }else {
                    CustomDialog dialog = new CustomDialog(hillView, "Ma trận không khả nghịch", "Lỗi nhập key ");

                }
            }
            System.out.println("decrypt");
        });

    decryptButton.addActionListener(e -> {
        String input = inputTextArea.getText();
        if(hill.checkMatrix(this.hill.matrixKey)){
            int[][] inverse = this.hill.inverse(this.hill.matrixKey);
            String cipher = hill.decrypt(input,inverse);
            outputTextArea.setText(cipher);
        }else {
            if(input.isEmpty()){
                CustomDialog dialog = new CustomDialog(hillView, "Ma trận rỗng, Bạn cần tạo/chọn ma trận trước", "Lỗi nhập key ");
            }else {
                CustomDialog dialog = new CustomDialog(hillView, "Ma trận không khả nghịch", "Lỗi nhập key ");

            }
        }
        System.out.println("decrypt");
    });
    }
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

    public void attachListenersToMatrix() {
        System.out.println("attach");
        for (int i = 0; i < hillView.getTotalCells(); i++) {
            JTextField cell = hillView.getCell(i);
            if (cell != null) {
                cell.getDocument().addDocumentListener(new DocumentListener() {
                    @Override
                    public void insertUpdate(DocumentEvent e) {
                        updateMatrixModel();
                    }

                    @Override
                    public void removeUpdate(DocumentEvent e) {
                        updateMatrixModel();
                    }

                    @Override
                    public void changedUpdate(DocumentEvent e) {
                        updateMatrixModel();
                    }
                });
            }
        }
    }

    private void updateMatrixModel() {
        System.out.println("update");
        int rows = hill.getSize();

        int[][] updatedMatrix = new int[rows][rows];

        Component[] components = hillView.getMatrixPanel().getComponents();
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < rows; j++) {
                int index = i * rows + j;
                if (components[index] instanceof JTextField) {
                    JTextField cell = (JTextField) components[index];
                    try {
                        if(cell.getText() == null || cell.getText().equals("") || cell.getText().isEmpty()){
                            updatedMatrix[i][j] =0;
                        }else {
                            updatedMatrix[i][j] = Integer.parseInt(cell.getText());
                        }
                    } catch (NumberFormatException e) {
                        updatedMatrix[i][j] = 0;
                    }
                }
            }
        }
        this.hillView.setMatrix(updatedMatrix); //cập nhật lại giá trị tại view cho đồng bộ
        this.hill.setMatrixKey(updatedMatrix); // Cập nhật Model
        System.out.println("model");
        print2D(this.hill.matrixKey);
        System.out.println("view");
        print2D(this.hillView.matrix);
    }
    @Override
    public Map<String, Object> saveData() {
        return null;
    }

    @Override
    public void loadData() {

    }
    public static void print2D(int mat[][])
    {
        // Loop through all rows
        for (int i = 0; i < mat.length; i++)

            // Loop through all elements of current row
            for (int j = 0; j < mat[i].length; j++)
                System.out.print(mat[i][j] + " ");
    }
    public void setDefaultValue(JTextField rowT, JTextField colT,int row,int col){
        this.numRow = row;
        this.numCol = col;
        this.hillView.setMatrixSize(this.numRow,this.numCol);
        this.hill.setMatrixKey(new int[numRow][numCol]);
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
    public void saveMatrixToFile(int[][] matrix, String filename) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            for (int i = 0; i < matrix.length; i++) {
                for (int j = 0; j < matrix[i].length; j++) {
                    writer.write(matrix[i][j] + " ");  // Ghi giá trị vào file, cách nhau bằng dấu cách
                }
                writer.newLine();  // Thêm dòng mới sau mỗi hàng
            }
            new CustomDialog(hillView,"Ma trận đã được lưu vào file:" +filename,"Save success");
        } catch (IOException e) {
            new CustomDialog(hillView,"Lỗi khi lưu ma trận vào file:" +e.getMessage(),"Save failed");
        }
    }


}
