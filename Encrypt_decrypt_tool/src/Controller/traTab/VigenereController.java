package Controller.traTab;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class VigenereController extends CardController {
    private JTextField inputField;
    private JTextField keyField;
    private JTextArea resultArea;

    public VigenereController() {
        super();

        JPanel vigenerePanel = new JPanel(new BorderLayout());
        JPanel inputPanel = new JPanel(new GridLayout(2, 2, 10, 10));

        inputField = new JTextField();
        keyField = new JTextField();
        resultArea = new JTextArea();
        resultArea.setEditable(false);

        inputPanel.add(new JLabel("Input Text:"));
        inputPanel.add(inputField);
        inputPanel.add(new JLabel("Key:"));
        inputPanel.add(keyField);

        JButton encryptButton = new JButton("Encrypt");
        encryptButton.addActionListener(e -> {
            String input = inputField.getText();
            String key = keyField.getText();
            String encrypted = encrypt(input, key);
            resultArea.setText(encrypted);
        });

        vigenerePanel.add(inputPanel, BorderLayout.NORTH);
        vigenerePanel.add(encryptButton, BorderLayout.CENTER);
        vigenerePanel.add(new JScrollPane(resultArea), BorderLayout.SOUTH);

        setView(vigenerePanel);
    }

    private String encrypt(String text, String key) {
        StringBuilder result = new StringBuilder();
        key = key.toLowerCase();
        int keyIndex = 0;

        for (char c : text.toCharArray()) {
            if (Character.isLetter(c)) {
                char base = Character.isLowerCase(c) ? 'a' : 'A';
                char keyChar = key.charAt(keyIndex % key.length());
                int shift = keyChar - 'a';
                result.append((char) ((c - base + shift) % 26 + base));
                keyIndex++;
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
