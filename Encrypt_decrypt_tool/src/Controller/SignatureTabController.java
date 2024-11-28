package Controller;

import Controller.signature.SignatureController;
import Controller.traTab.CardController;
import UI.MainJPanel;
import UI.SignatureEncodePane;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class SignatureTabController extends TabController {

    private JComboBox<String> algoComboBox;


    private CardLayout cardLayout;
    private JPanel cardPanel;
    private Map<String, CardController> cardControllers;
    private JPanel headPanel;

    private SignatureEncodePane signatureEncodePane;//view của tratab

    public SignatureTabController(MainJPanel mainJPanel) {
        super();
        //khởi tạo pane cho tab này
        this.signatureEncodePane = mainJPanel.sigPane;
        // Khởi tạo ComboBox
        this.algoComboBox = this.signatureEncodePane.algoBox;

        // Panel chứa các Card
        cardLayout = this.signatureEncodePane.cardLayout;
        cardPanel = this.signatureEncodePane.cardPanel;
        cardControllers = createCardController();

//        // Thêm các CardView vào cardPanel
//        cardControllers.forEach((name, controller) -> cardPanel.add(controller.getView(), name));
        // Thêm các CardController vào cardPanel
        for (String key : cardControllers.keySet()) {
            cardPanel.add(cardControllers.get(key).getView(), key); // Gắn từng card vào CardLayout
        }
        // Đặt mặc định Card và ComboBox
        cardLayout.show(cardPanel, "Signature"); // Hiển thị card "Caesar" mặc định
        algoComboBox.setSelectedItem("Signature");   // Đặt giá trị mặc định cho ComboBox


        // Thêm ComboBox vào headPanel
        this.headPanel = signatureEncodePane.headPane;


        // Lắng nghe sự kiện ComboBox để chuyển đổi Card
        algoComboBox.addActionListener(e -> {
            String selectedCard = (String) algoComboBox.getSelectedItem();
            cardLayout.show(cardPanel, selectedCard);
            System.out.println("show" + selectedCard);
        });
    }

    private Map<String, CardController> createCardController() {
        Map<String, CardController> temp = new HashMap<>();
        temp.put("Signature",new SignatureController());

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
