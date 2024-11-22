package Controller;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public abstract class TabController {
    private JPanel view;


    public TabController() {
        // Panel chính của tab
        view = new JPanel(new BorderLayout());
    }


    // Trả về view của tab
    public JPanel getView() {
        return view;
    }
    // Thêm card vào CardLayout

    public abstract Map<String, Object> saveData();
    public abstract void loadData();
}
