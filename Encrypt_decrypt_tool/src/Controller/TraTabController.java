package Controller;

import Controller.*;
import Controller.traTab.*;
import UI.MainJPanel;
import UI.TraditionalEncodePane;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class TraTabController extends TabController {
    public String[] algoNames = new String[]{"Caesar(dịch chuyển)", "Substitutuin(Thay thế)", "Affine", "Hill", "Vigenere"};
    private JComboBox<String> comboBox;
    private CardLayout cardLayout;
    private JPanel cardPanel;
    private Map<String, CardController> cardControllers;
    private JPanel headPanel;

    private TraditionalEncodePane traditionalEncodePane;//view của tratab

    public TraTabController(MainJPanel mainJPanel) {
        super();
        //khởi tạo pane cho tab này
        this.traditionalEncodePane = mainJPanel.traPane;
        // Khởi tạo ComboBox
        this.comboBox = traditionalEncodePane.comboBox;

        // Panel chứa các Card
        cardLayout = traditionalEncodePane.cardLayout;
        cardPanel = traditionalEncodePane.cardPane;
        cardControllers = createCardController();

//        // Thêm các CardView vào cardPanel
//        cardControllers.forEach((name, controller) -> cardPanel.add(controller.getView(), name));
        // Thêm các CardController vào cardPanel
        for (String key : cardControllers.keySet()) {
            cardPanel.add(cardControllers.get(key).getView(), key); // Gắn từng card vào CardLayout
        }
        // Đặt mặc định Card và ComboBox
        cardLayout.show(cardPanel, "Caesar(dịch chuyển)"); // Hiển thị card "Caesar" mặc định
        comboBox.setSelectedItem("Caesar(dịch chuyển)");   // Đặt giá trị mặc định cho ComboBox


        // Thêm ComboBox vào headPanel
        this.headPanel = traditionalEncodePane.headPane;


        // Lắng nghe sự kiện ComboBox để chuyển đổi Card
        comboBox.addActionListener(e -> {
            String selectedCard = (String) comboBox.getSelectedItem();
            cardLayout.show(cardPanel, selectedCard);
        });
    }

    private Map<String, CardController> createCardController() {
        Map<String, CardController> temp = new HashMap<>();
        temp.put("Caesar(dịch chuyển)", new CaesarController());
        temp.put("Substitutuin(Thay thế)", new SubstitutionController());
        temp.put("Affine", new AffineController());
        temp.put("Hill", new HillController());
        temp.put("Vigenere", new VigenereController());
        return temp;
    }

    @Override
    public Map<String, Object> saveData() {
        // Thu thập dữ liệu từ tất cả các CardController
        Map<String, Object> data = new HashMap<>();
        cardControllers.forEach((name, controller) -> data.put(name, controller.saveData()));
        return data;
    }

    @Override
    public void loadData() {
        // Load dữ liệu vào tất cả các CardController
        cardControllers.forEach((name, controller) -> controller.loadData());
    }
}
