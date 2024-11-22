package Controller.traTab;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class HillController extends CardController {
    private JTextField inputField;
    private JTextField keyField;
    private JTextArea resultArea;

    public HillController() {
        super();

        JPanel hillPanel = new JPanel(new BorderLayout());
        JPanel inputPanel = new JPanel(new GridLayout(2, 2, 10, 10));

        inputField = new JTextField();
        keyField = new JTextField();
        resultArea = new JTextArea();
        resultArea.setEditable(false);

        inputPanel.add(new JLabel("Input Text:"));
        inputPanel.add(inputField);
        inputPanel.add(new JLabel("Key (Matrix as String):"));
        inputPanel.add(keyField);

        JButton encryptButton = new JButton("Encrypt");
        encryptButton.addActionListener(e -> {
            String input = inputField.getText();
            String key = keyField.getText();
            try {
                String encrypted = encrypt(input, key);
                resultArea.setText(encrypted);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(hillPanel, "Invalid key format.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        hillPanel.add(inputPanel, BorderLayout.NORTH);
        hillPanel.add(encryptButton, BorderLayout.CENTER);
        hillPanel.add(new JScrollPane(resultArea), BorderLayout.SOUTH);

        setView(hillPanel);
    }

    private String encrypt(String text, String key) throws Exception {
        // Implement Hill cipher logic using a matrix from the key
        return "Encrypted Text (Logic Pending)";
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
