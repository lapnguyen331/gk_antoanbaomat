package UI.tradition;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.awt.*;

public class HillView extends JPanel {
    public JPanel rightPane;
    public JPanel leftPane;
    public JTextField rowsField; // Trường nhập số dòng
    public JTextField colsField; // Trường nhập số cột
    public JPanel matrixPanel; // Panel chứa ma trận

    public HillView() {
        // Left Pane: Cài đặt
        leftPane = new JPanel();
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
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 1;
        gbc.weighty = 0;
        gbc.insets = new Insets(10, 5, 5, 5);
        leftPane.add(matrixLabel, gbc);

        matrixPanel = new JPanel();
        matrixPanel.setLayout(new GridLayout(5, 5, 5, 5)); // Mặc định 5x5
        fillMatrixPanel(5, 5); // Tạo ma trận mặc định

        JScrollPane matrixScrollPane = new JScrollPane(matrixPanel);
        matrixScrollPane.setPreferredSize(new Dimension(300, 300)); // Cố định kích thước phần hiển thị ma trận

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.weightx = 1;
        gbc.weighty = 1;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(5, 5, 5, 5);
        leftPane.add(matrixScrollPane, gbc);

        // Right Pane: Mã hóa/Giải mã
        rightPane = new JPanel();
        rightPane.setLayout(new BoxLayout(rightPane, BoxLayout.Y_AXIS));
        rightPane.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createBevelBorder(BevelBorder.RAISED), "Mã hóa/Giải mã"));

        JTextArea inputTextArea = new JTextArea(10, 20);
        JScrollPane inputScrollPane = new JScrollPane(inputTextArea);
        inputScrollPane.setBorder(BorderFactory.createTitledBorder("Input"));

        JTextArea outputTextArea = new JTextArea(10, 20);
        JScrollPane outputScrollPane = new JScrollPane(outputTextArea);
        outputScrollPane.setBorder(BorderFactory.createTitledBorder("Output"));

        JButton encryptButton = new JButton("Mã hóa");
        JButton decryptButton = new JButton("Giải mã");

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
    private void fillMatrixPanel(int rows, int cols) {
        matrixPanel.removeAll();
        rows = Math.min(rows, 10); // Giới hạn tối đa 10 dòng
        cols = Math.min(cols, 10); // Giới hạn tối đa 10 cột
        matrixPanel.setLayout(new GridLayout(rows, cols, 2, 2));

        for (int i = 0; i < rows * cols; i++) {
            JTextField cell = new JTextField();
            cell.setHorizontalAlignment(JTextField.CENTER);
            cell.setPreferredSize(new Dimension(10, 25)); // Kích thước nhỏ hơn

            matrixPanel.add(cell);
        }

        matrixPanel.revalidate();
        matrixPanel.repaint();
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
}
