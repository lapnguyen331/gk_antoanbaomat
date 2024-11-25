package UI;

import javax.swing.*;
import java.awt.*;

public class MainJPanel extends JFrame {

    public JTabbedPane tabPane;

    public TraditionalEncodePane traPane;
    public AsymEncodePane asymPane;
    public SymEncodePane symPane;
    public HashPane hashPane;
    public SigPane sigPane;

    public MainJPanel(TraditionalEncodePane traditionalEncodePane, SymEncodePane symtab){
        this.setTitle("Ứng dụng Mã hóa/ Giải mã");
        this.setSize(1400, 500);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(new BorderLayout());


        // Panel nhập liệu
        tabPane = new JTabbedPane(SwingConstants.LEFT);


        // Tab 1
        traPane = traditionalEncodePane;
        symPane = symtab;
        asymPane = new AsymEncodePane();
        hashPane = new HashPane();
        sigPane = new SigPane();

        // Thêm các tab vào JTabbedPane
        tabPane.addTab("Mã hóa truyền thống",traPane);
        tabPane.addTab("Mã hóa đối xứng",symPane);
        tabPane.addTab("Mã hóa bất đối xứng",asymPane);
        tabPane.addTab("Hàm băm",hashPane);
        tabPane.addTab("Chữ kí điện tử",sigPane);


        // Đặt JTabbedPane vào JFrame chính
        this.add(tabPane);

        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
    }

//    public static void main(String[] args) {
//        new MainJPanel();
//    }
}
