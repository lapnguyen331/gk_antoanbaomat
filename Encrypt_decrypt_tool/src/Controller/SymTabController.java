package Controller;

import Controller.*;
import Controller.symTab.AESController;
import Controller.traTab.*;
import UI.MainJPanel;
import UI.SymEncodePane;
import UI.TraditionalEncodePane;
import UI.sym.AESView;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class SymTabController extends TabController {
    public String[] algoNames = new String[]{"AES", "Vigenere"};
    public String[] modeName = new String[]{"ECB","CBC","CFB","OFB","CTR","GCM"};
    public String[] paddingName = new String[]{"NoPadding","PKCS5Padding","ISO10126Padding","ZeroPadding"};
    private JComboBox<String> modeComboBox;
    private JComboBox<String> paddingComboBox;
    private JComboBox<String> algoComboBox;


    private CardLayout cardLayout;
    private JPanel cardPanel;
    private Map<String, CardController> cardControllers;
    private JPanel headPanel;

    private SymEncodePane symEncodePane;//view của tratab

    public SymTabController(MainJPanel mainJPanel) {
        super();
        //khởi tạo pane cho tab này
        this.symEncodePane = mainJPanel.symPane;
        // Khởi tạo ComboBox
        this.algoComboBox = this.symEncodePane.algoBox;

        // Panel chứa các Card
        cardLayout = this.symEncodePane.cardLayout;
        cardPanel = this.symEncodePane.cardPanel;
        cardControllers = createCardController();

//        // Thêm các CardView vào cardPanel
//        cardControllers.forEach((name, controller) -> cardPanel.add(controller.getView(), name));
        // Thêm các CardController vào cardPanel
        for (String key : cardControllers.keySet()) {
            cardPanel.add(cardControllers.get(key).getView(), key); // Gắn từng card vào CardLayout
        }
        // Đặt mặc định Card và ComboBox
        cardLayout.show(cardPanel, "AES"); // Hiển thị card "Caesar" mặc định
        algoComboBox.setSelectedItem("AES");   // Đặt giá trị mặc định cho ComboBox


        // Thêm ComboBox vào headPanel
        this.headPanel = symEncodePane.headPane;


        // Lắng nghe sự kiện ComboBox để chuyển đổi Card
        algoComboBox.addActionListener(e -> {
            String selectedCard = (String) algoComboBox.getSelectedItem();
            cardLayout.show(cardPanel, selectedCard);
            System.out.println("show" + selectedCard);
        });
    }

    private Map<String, CardController> createCardController() {
        Map<String, CardController> temp = new HashMap<>();
        temp.put("AES", new AESController());
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