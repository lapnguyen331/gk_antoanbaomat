package Controller.traTab;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class AffineController extends CardController {
    private JTextField inputField;
    private JTextField aField;
    private JTextField bField;
    private JTextArea resultArea;

    public AffineController() {
        super();

        JPanel affinePanel = new JPanel(new BorderLayout());
        JPanel inputPanel = new JPanel(new GridLayout(3, 2, 10, 10));

        inputField = new JTextField();
        aField = new JTextField();
        bField = new JTextField();
        resultArea = new JTextArea();
        resultArea.setEditable(false);

        inputPanel.add(new JLabel("Input Text:"));
        inputPanel.add(inputField);
        inputPanel.add(new JLabel("Key A:"));
        inputPanel.add(aField);
        inputPanel.add(new JLabel("Key B:"));
        inputPanel.add(bField);

        JButton encryptButton = new JButton("Encrypt");
        encryptButton.addActionListener(e -> {
            try {
                String input = inputField.getText();
                int a = Integer.parseInt(aField.getText());
                int b = Integer.parseInt(bField.getText());
                String encrypted = encrypt(input, a, b);
                resultArea.setText(encrypted);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(affinePanel, "Keys A and B must be integers.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        affinePanel.add(inputPanel, BorderLayout.NORTH);
        affinePanel.add(encryptButton, BorderLayout.CENTER);
        affinePanel.add(new JScrollPane(resultArea), BorderLayout.SOUTH);

        setView(affinePanel);
    }

    private String encrypt(String text, int a, int b) {
        StringBuilder result = new StringBuilder();
        int m = 26; // Số chữ cái trong bảng chữ cái tiếng Anh
        for (char c : text.toCharArray()) {
            if (Character.isLetter(c)) {
                char base = Character.isLowerCase(c) ? 'a' : 'A';
                int x = c - base;
                int encryptedChar = (a * x + b) % m;
                result.append((char) (encryptedChar + base));
            } else {
                result.append(c);
            }
        }
        return result.toString();
    }

    @Override
    public Map<String, Object> saveData() {
        Map<String, Object> data = new HashMap<>();
        data.put("input", inputField.getText());
        data.put("a", aField.getText());
        data.put("b", bField.getText());
        data.put("result", resultArea.getText());
        return data;
    }

    @Override
    public void loadData() {
        // Load logic nếu cần
    }
}
