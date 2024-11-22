package UI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class CustomDialog extends JDialog {

    private DialogListener listener; // Listener để bắt sự kiện
    public boolean isCloseDialog = false;
    // Constructor của dialog
    public CustomDialog(Component parent, String message, String title) {
        super((Frame) SwingUtilities.getWindowAncestor(parent), title, true); // true để dialog là modal

        // Cấu hình dialog
        setSize(300, 150);
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout());


        // Nội dung thông báo
        JLabel messageLabel = new JLabel(message, SwingConstants.CENTER);
        add(messageLabel, BorderLayout.CENTER);

        // Tạo panel chứa các nút OK và Exit
        JPanel buttonPanel = new JPanel();
        JButton okButton = new JButton("OK");
        JButton exitButton = new JButton("Exit");

        // Lắng nghe sự kiện click OK
        okButton.addActionListener(event -> {
//            if (listener != null) {
//                listener.onOkClicked(); // Gọi phương thức khi OK được nhấn
//            }
            isCloseDialog = true;
            dispose();  // Đóng dialog
        });

        // Lắng nghe sự kiện click Exit
        exitButton.addActionListener(event -> {
//            if (listener != null) {
//                listener.onExitClicked(); // Gọi phương thức khi Exit được nhấn
//            }
            isCloseDialog = true;
            dispose();  // Đóng dialog
        });

        // Thêm các nút vào panel
        buttonPanel.add(okButton);
        buttonPanel.add(exitButton);
        add(buttonPanel, BorderLayout.SOUTH);
        setVisible(true);
    }

    // Phương thức để đăng ký listener
    public void setDialogListener(DialogListener listener) {
        this.listener = listener;
    }
    public boolean isCloseDialog(){
        return isCloseDialog;
    }
}
