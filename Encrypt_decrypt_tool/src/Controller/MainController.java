package Controller;

import Controller.TabController;
import Controller.TraTabController;
import UI.MainJPanel;
import UI.TraditionalEncodePane;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class MainController {
    private MainJPanel mainJPanel;
    private JTabbedPane tabbedPane;
    private Map<String, TabController> tabControllers;

    public MainController(MainJPanel mainJPanel) {
        // Khởi tạo frame chính
        this.mainJPanel = mainJPanel;
        // TabbedPane chứa các tab

        // Khởi tạo danh sách tab controllers
        tabControllers = createTabControllers();

    }

    /**
     * Khởi tạo các TabController
     */
    private Map<String, TabController> createTabControllers() {
        Map<String, TabController> controllers = new HashMap<>();
        controllers.put("TraTab", new TraTabController(mainJPanel)); // Tab mã hóa
        controllers.put("SymTab", new SymTabController(mainJPanel)); // Tab mã hóa
        controllers.put("ASymTab", new ASymTabController(mainJPanel)); // Tab mã hóa
        controllers.put("HashTab",new HashTabController(mainJPanel));
        controllers.put("SigTab",new SignatureTabController(mainJPanel));
        return controllers;
    }

    /**
     * Lưu dữ liệu từ tất cả các tab
     */
    public void saveAllTabsData() {
        Map<String, Map<String, Object>> allData = new HashMap<>();
        for (Map.Entry<String, TabController> entry : tabControllers.entrySet()) {
            allData.put(entry.getKey(), entry.getValue().saveData());
        }
        // Thực hiện lưu trữ dữ liệu toàn cục
        System.out.println("Saved Data: " + allData);
    }

    /**
     * Tải dữ liệu vào tất cả các tab
     */
    public void loadAllTabsData() {
        for (TabController controller : tabControllers.values()) {
            controller.loadData();
        }
    }
}
