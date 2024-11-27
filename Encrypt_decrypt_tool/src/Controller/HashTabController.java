package Controller;

import Controller.asymTab.RSAController;
import Controller.hash.*;
import Controller.traTab.CardController;
import UI.ASymEncodePane;
import UI.HashEncodePane;
import UI.MainJPanel;
import UI.hash.MD5View;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class HashTabController extends TabController {
    public String[] algoNames = new String[]{"MD5","SHA-1","SHA-3","SHA-256","SHA-512","RIPEMD-160"};
//    private JComboBox<String> modeComboBox;
//    private JComboBox<String> paddingComboBox;
    private JComboBox<String> algoComboBox;


    private CardLayout cardLayout;
    private JPanel cardPanel;
    private Map<String, CardController> cardControllers;
    private JPanel headPanel;

    private HashEncodePane hashEncodePane;//view của hashtab

    public HashTabController(MainJPanel mainJPanel) {
        super();
        //khởi tạo pane cho tab này
        this.hashEncodePane = mainJPanel.hashPane;
        // Khởi tạo ComboBox
        this.algoComboBox = this.hashEncodePane.algoBox;

        // Panel chứa các Card
        cardLayout = this.hashEncodePane.cardLayout;
        cardPanel = this.hashEncodePane.cardPanel;
        cardControllers = createCardController();

//        // Thêm các CardView vào cardPanel
//        cardControllers.forEach((name, controller) -> cardPanel.add(controller.getView(), name));
        // Thêm các CardController vào cardPanel
        for (String key : cardControllers.keySet()) {
            cardPanel.add(cardControllers.get(key).getView(), key); // Gắn từng card vào CardLayout
        }
        // Đặt mặc định Card và ComboBox
        cardLayout.show(cardPanel, "MD5"); // Hiển thị card "Caesar" mặc định
        algoComboBox.setSelectedItem("MD5");   // Đặt giá trị mặc định cho ComboBox


        // Thêm ComboBox vào headPanel
        this.headPanel = hashEncodePane.headPane;


        // Lắng nghe sự kiện ComboBox để chuyển đổi Card
        algoComboBox.addActionListener(e -> {
            String selectedCard = (String) algoComboBox.getSelectedItem();
            cardLayout.show(cardPanel, selectedCard);
            System.out.println("show" + selectedCard);
        });
    }

    private Map<String, CardController> createCardController() {
        Map<String, CardController> temp = new HashMap<>();
        temp.put("MD5",new Md5Controller());
        temp.put("SHA-1",new SHA1Controller());
        temp.put("SHA-256",new SHA256Controller());
        temp.put("SHA-512",new SHA512Controller());
        temp.put("SHA3-256",new SHA3Controller());
        temp.put("RIPEMD-160",new RiPemd160Controller());


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
