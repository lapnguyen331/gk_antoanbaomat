package UI;

import javax.swing.*;
import java.awt.*;

public class MainJPanel extends JFrame {

    public JTabbedPane tabPane;

    public TraditionalEncodePane traPane;
    public ASymEncodePane asymPane;
    public SymEncodePane symPane;
    public HashEncodePane hashPane;
    public SigPane sigPane;

    public MainJPanel(TraditionalEncodePane traditionalEncodePane, SymEncodePane symtab, ASymEncodePane aSymEncodePane,HashEncodePane hashPane){
        this.setTitle("Ứng dụng Mã hóa/ Giải mã");
        this.setSize(1400, 500);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(new BorderLayout());


        // Panel nhập liệu
        tabPane = new JTabbedPane(SwingConstants.LEFT);


        // Tab 1
        this. traPane = traditionalEncodePane;
        this.symPane = symtab;
        this.asymPane = aSymEncodePane;
        this.hashPane = hashPane;
        this.sigPane = new SigPane();

        // Thêm các tab vào JTabbedPane
        tabPane.addTab("Mã hóa truyền thống",this.traPane);
        tabPane.addTab("Mã hóa đối xứng",this.symPane);
        tabPane.addTab("Mã hóa bất đối xứng",this.asymPane);
        tabPane.addTab("Hàm băm",this.hashPane);
        tabPane.addTab("Chữ kí điện tử",this.sigPane);
       // mặc định show tab
        tabPane.setSelectedIndex(3);


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
