package Controller;

import Controller.traTab.CardController;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class SubstitutionController extends CardController {
    private JTextField inputField;
    private JTextField keyField;
    private JTextArea resultArea;

    public SubstitutionController() {
        super();

        JPanel substitutionPanel = new JPanel(new BorderLayout());
        JPanel inputPanel = new JPanel(new GridLayout(2, 2, 10, 10));

        inputField = new JTextField();
        keyField = new JTextField();
        resultArea = new JTextArea();
        resultArea.setEditable(false);

        inputPanel.add(new JLabel("Input Text:"));
        inputPanel.add(inputField);
        inputPanel.add(new JLabel("Key (26 Letters):"));
        inputPanel.add(keyField);

        JButton encryptButton = new JButton("Encrypt");
        encryptButton.addActionListener(e -> {
            String input = inputField.getText();
            String key = keyField.getText();
            if (key.length() == 26) {
                String encrypted = encrypt(input, key);
                resultArea.setText(encrypted);
            } else {
                JOptionPane.showMessageDialog(substitutionPanel, "Key must be 26 letters.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        substitutionPanel.add(inputPanel, BorderLayout.NORTH);
        substitutionPanel.add(encryptButton, BorderLayout.CENTER);
        substitutionPanel.add(new JScrollPane(resultArea), BorderLayout.SOUTH);

        setView(substitutionPanel);
    }

    private String encrypt(String text, String key) {
        StringBuilder result = new StringBuilder();
        for (char c : text.toCharArray()) {
            if (Character.isLetter(c)) {
                char base = Character.isLowerCase(c) ? 'a' : 'A';
                result.append(key.charAt(c - base));
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
        data.put("key", keyField.getText());
        data.put("result", resultArea.getText());
        return data;
    }

    @Override
    public void loadData() {
        // Load logic nếu cần
    }
}
