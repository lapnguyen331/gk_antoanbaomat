package Controller.traTab;

import UI.MainJPanel;

import javax.swing.*;
import java.util.Map;

public abstract class CardController {
    private JPanel view; // View của Card
    public CardController() {
        this.view = new JPanel(); // Giao diện mặc định
    }

    // Lấy giao diện của Card
    public JPanel getView() {
        return view;
    }

    // Ghi đè phương thức này để khởi tạo giao diện cụ thể
    protected void setView(JPanel view) {
        this.view = view;
    }

    // Lưu trạng thái dữ liệu của Card
    public abstract Map<String, Object> saveData();

    // Tải dữ liệu vào Card
    public abstract void loadData();
}
