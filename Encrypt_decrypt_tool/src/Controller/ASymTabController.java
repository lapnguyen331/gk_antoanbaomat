package Controller;

import Controller.asymTab.RSAController;
import Controller.traTab.CardController;
import UI.ASymEncodePane;
import UI.MainJPanel;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class ASymTabController extends TabController {

    private JComboBox<String> algoComboBox;


    private CardLayout cardLayout;
    private JPanel cardPanel;
    private Map<String, CardController> cardControllers;
    private JPanel headPanel;

    private ASymEncodePane aSymEncodePane;//view của tratab

    public ASymTabController(MainJPanel mainJPanel) {
        super();
        //khởi tạo pane cho tab này
        this.aSymEncodePane = mainJPanel.asymPane;
        // Khởi tạo ComboBox
        this.algoComboBox = this.aSymEncodePane.algoBox;

        // Panel chứa các Card
        cardLayout = this.aSymEncodePane.cardLayout;
        cardPanel = this.aSymEncodePane.cardPanel;
        cardControllers = createCardController();

//        // Thêm các CardView vào cardPanel
//        cardControllers.forEach((name, controller) -> cardPanel.add(controller.getView(), name));
        // Thêm các CardController vào cardPanel
        for (String key : cardControllers.keySet()) {
            cardPanel.add(cardControllers.get(key).getView(), key); // Gắn từng card vào CardLayout
        }
        // Đặt mặc định Card và ComboBox
        cardLayout.show(cardPanel, "RSA"); // Hiển thị card "Caesar" mặc định
        algoComboBox.setSelectedItem("RSA");   // Đặt giá trị mặc định cho ComboBox


        // Thêm ComboBox vào headPanel
        this.headPanel = aSymEncodePane.headPane;


        // Lắng nghe sự kiện ComboBox để chuyển đổi Card
        algoComboBox.addActionListener(e -> {
            String selectedCard = (String) algoComboBox.getSelectedItem();
            cardLayout.show(cardPanel, selectedCard);
            System.out.println("show" + selectedCard);
        });
    }

    private Map<String, CardController> createCardController() {
        Map<String, CardController> temp = new HashMap<>();
        temp.put("RSA",new RSAController());
//        temp.put("Substitution(Thay thế)", new SubtitutionController());
//        temp.put("Affine", new AffineController());
//        temp.put("Hill", new HillController());
//        temp.put("Vigenere", new VigenereController());
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
