package UI.tradition;

import Controller.traTab.HillController;
import UI.CustomDialog;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.awt.*;

public class HillView extends JPanel {
    public JPanel rightPane;
    public JPanel leftPane;
    public JTextField rowsField; // Trường nhập số dòng
    public JTextField colsField; // Trường nhập số cột
    public JPanel matrixPanel; // Panel chứa ma trận
    public int numRow =1;
    public int numCol=1;
    public JButton encryptButton ;
    public JButton decryptButton ;
    public JTextArea inputTextArea;
    public JTextArea outputTextArea;
    public JButton saveMatrixBut;
    public JButton chooseMAtrixBut;
    public JButton createMatrix;
    public int[][] matrix ;

    public HillView() {
        // Left Pane: Cài đặt
        leftPane = new JPanel();
        leftPane.setPreferredSize(new Dimension(700,500));
        leftPane.setLayout(new GridBagLayout());
        leftPane.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createBevelBorder(BevelBorder.RAISED), "Cài đặt"));

        GridBagConstraints gbc = new GridBagConstraints();

        // Phần nhập kích thước ma trận
        JLabel sizeLabel = new JLabel("Nhập kích thước ma trận:");
        rowsField = new JTextField(2);
        rowsField.setHorizontalAlignment(JTextField.CENTER);
        colsField = new JTextField(2);
        colsField.setHorizontalAlignment(JTextField.CENTER);
        JLabel xLabel = new JLabel(" X ");
        JPanel sizeInputPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 2, 0));
        sizeInputPanel.add(sizeLabel);
        sizeInputPanel.add(rowsField);
        sizeInputPanel.add(xLabel);
        sizeInputPanel.add(colsField);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1;
        gbc.weighty = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);
        leftPane.add(sizeInputPanel, gbc);

        // Phần hiển thị ma trận

        JLabel matrixLabel = new JLabel("Nhập giá trị ma trận:");
        saveMatrixBut = new JButton("Lưu ma trận");
        createMatrix = new JButton("Tạo ma trận");
        chooseMAtrixBut = new JButton("Chọn ma trận");

        JPanel topan = new JPanel(new GridLayout(0,4));
        GridBagConstraints g = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 1;
        gbc.weighty = 0;
        gbc.insets = new Insets(10, 5, 5, 5);
        topan.add(matrixLabel,g);
        gbc.gridx = 2;
        gbc.gridy = 1;
        gbc.weightx = 0;
        gbc.weighty = 0;
        gbc.insets = new Insets(10, 2, 5, 2);
        topan.add(createMatrix,g);
        gbc.gridx = 3;
        gbc.gridy = 1;
        gbc.weightx = 0;
        gbc.weighty = 0;
        gbc.insets = new Insets(10, 2, 5, 2);
        topan.add(chooseMAtrixBut,g);
        gbc.gridx = 4;
        gbc.gridy = 1;
        gbc.weightx = 0;
        gbc.weighty = 0;
        gbc.insets = new Insets(10, 2, 5, 2);
        topan.add(saveMatrixBut,g);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 1;
        gbc.weighty = 0;
        gbc.insets = new Insets(10, 5, 5, 5);
        leftPane.add(topan,gbc);

        //phần nhập giá trị
        matrixPanel = new JPanel();
        matrixPanel.setLayout(new GridLayout()); // Mặc định 5x5
//        createMatrixPanel(matrixPanel,this.numRow, this.numCol); // Tạo ma trận mặc định

//        JScrollPane matrixScrollPane = new JScrollPane(matrixPanel);
//        matrixScrollPane.setPreferredSize(new Dimension(50, 50)); // Cố định kích thước phần hiển thị ma trận

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.weightx = 1;
        gbc.weighty = 1;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(5, 5, 5, 5);

        JPanel matrixPanelWrapper = new JPanel();
        matrixPanelWrapper.setPreferredSize(new Dimension(700,200));
        matrixPanelWrapper.setLayout(new GridLayout());

        JScrollPane scrol = new JScrollPane(matrixPanel);

        scrol.setPreferredSize(new Dimension(150,150));

        JPanel pan = new JPanel(new FlowLayout());
        pan.add(scrol);
//        matrixPanelWrapper.add(pan);

        leftPane.add(pan, gbc);

        // Right Pane: Mã hóa/Giải mã
        rightPane = new JPanel();
        rightPane.setPreferredSize(new Dimension(700,500));

        rightPane.setLayout(new BoxLayout(rightPane, BoxLayout.Y_AXIS));
        rightPane.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createBevelBorder(BevelBorder.RAISED), "Mã hóa/Giải mã"));

         inputTextArea = new JTextArea(10, 20);
        JScrollPane inputScrollPane = new JScrollPane(inputTextArea);
        inputScrollPane.setBorder(BorderFactory.createTitledBorder("Input"));

         outputTextArea = new JTextArea(10, 20);
        JScrollPane outputScrollPane = new JScrollPane(outputTextArea);
        outputScrollPane.setBorder(BorderFactory.createTitledBorder("Output"));

         encryptButton = new JButton("Mã hóa");
         decryptButton = new JButton("Giải mã");

        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.add(encryptButton);
        buttonPanel.add(decryptButton);

        rightPane.add(inputScrollPane);
        rightPane.add(buttonPanel);
        rightPane.add(outputScrollPane);

        // Tổng hợp giao diện
        this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        this.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        this.add(leftPane);
        this.add(Box.createHorizontalStrut(10)); // Khoảng cách giữa hai phần
        this.add(rightPane);
    }

    /**
     * Tạo hoặc cập nhật panel ma trận với số dòng và cột mới.
     *
     * @param rows Số dòng của ma trận.
     * @param cols Số cột của ma trận.
     */
    public void setMatrixSize(int rows,int cols){
        this.numRow = rows;
        this.numCol = cols;
        System.out.println(this.numCol +"/"+this.numRow);
    }
    public void setMatrix(int[][] matrix){
        this.matrix = matrix;
    }
    public void createMatrixPanel(JPanel pane, int rows, int cols) {
        pane.removeAll();
//        rows = Math.min(rows, 10); // Giới hạn tối đa 10 dòng
//        cols = Math.min(cols, 10); // Giới hạn tối đa 10 cột
        pane.setLayout(new GridLayout(rows, cols, 2, 2));
        if (matrix == null) {
            // Khởi tạo ma trận mới nếu chưa có
            matrix = new int[rows][cols];
            for (int i = 0; i < rows; i++) {
                for (int j = 0; j < cols; j++) {
                    JTextField cell = new JTextField();
                    cell.setHorizontalAlignment(JTextField.CENTER);
                    cell.setPreferredSize(new Dimension(50, 30)); // Điều chỉnh kích thước phù hợp
                    pane.add(cell);
                }
            }
        }else {
            for (int i = 0; i < rows; i++) {
                for (int j = 0; j < cols; j++) {
                    JTextField cell = new JTextField();
                    cell.setHorizontalAlignment(JTextField.CENTER);
                    cell.setPreferredSize(new Dimension(50, 30)); // Điều chỉnh kích thước phù hợp
//                    HillController.print2D(matrix);
                    cell.setText(String.valueOf(matrix[i][j]));
                    pane.add(cell);
                }
            }
        }
        pane.validate();
        pane.repaint();
    }
    public JTextField getCell(int index) {
        Component component = matrixPanel.getComponent(index);
        if (component instanceof JTextField) {
            return (JTextField) component;
        }
        return null;
    }

    public int getTotalCells() {
        return matrixPanel.getComponentCount();
    }
//    private void updateMatrixValue(JTextField textField, int row, int col) {
//        try {
//            String text = textField.getText();
//            if (text.isEmpty()) {
//                matrix[row][col] = 0; // Nếu trống, đặt giá trị mặc định là 0
//            } else {
//                matrix[row][col] = Integer.parseInt(text); // Chuyển giá trị về số nguyên
//            }
//        } catch (NumberFormatException e) {
//            matrix[row][col] = 0; // Xử lý khi người dùng nhập không phải số
//        }
//    }
   public JPanel getMatrixPanel(){
        return this.matrixPanel;
    }
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Hill Cipher");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(800, 600);
            frame.setContentPane(new HillView());
            frame.setVisible(true);
        });
    }
    //ấy ma trận đã tạo từ panel
    public  int[][] getMatrixFromPanel(JPanel pane, int rows, int cols) {
        int[][] matrix = new int[rows][cols]; // Khởi tạo ma trận theo số hàng và số cột
        Component[] components = pane.getComponents(); // Lấy tất cả các thành phần trong pane

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                int index = i * cols + j; // Tính chỉ số của JTextField
                if (index < components.length && components[index] instanceof JTextField) {
                    JTextField textField = (JTextField) components[index];
                    String text = textField.getText(); // Lấy giá trị văn bản trong JTextField
                    if(isInteger(text)){
                        try {
                            matrix[i][j] = Integer.parseInt(text); // Chuyển đổi giá trị thành số nguyên
                        } catch (NumberFormatException e) {
                            matrix[i][j] = 0; // Nếu không thể chuyển đổi, gán giá trị mặc định là 0
                        }
                    }else {
                        return null;
                    }

                }
            }
        }
        return matrix;
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
