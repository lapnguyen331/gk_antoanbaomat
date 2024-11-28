package UI;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.awt.*;

public class SignatureEncodePane extends JPanel {
    public String[] algoNames = new String[]{"RSA"}; //danh sách các algo
    public JPanel mainPanel ;
    public  JPanel headPane ;
    public  JComboBox<String> algoBox ;

    public  CardLayout cardLayout;
    public  JPanel cardPanel ;
    public SignatureEncodePane(){
        this.setLayout(new BorderLayout());

        mainPanel = new JPanel();

        headPane = new JPanel();

        headPane = new JPanel();
        headPane.setLayout(new FlowLayout());
        JLabel chooseAlgo = new JLabel("Chọn thuật toán: ");

        this.algoBox = new JComboBox<>(algoNames);

        JPanel centerPane = new JPanel();
        JPanel rightPane = new JPanel();
        JPanel leftPane = new JPanel();

        headPane.add(chooseAlgo);
        headPane.add(algoBox);



        //tạo border
        headPane.setBorder(BorderFactory.createTitledBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED),""));

        centerPane.setLayout(new BoxLayout(centerPane,BoxLayout.X_AXIS));
        centerPane.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
        centerPane.add(leftPane);
        centerPane.add(rightPane);

        this.cardLayout = new CardLayout();
        this.cardPanel = new JPanel(this.cardLayout);
        this.cardPanel.add(centerPane);

        mainPanel.setLayout(new BorderLayout());
        mainPanel.add(headPane,BorderLayout.NORTH);
        mainPanel.add(cardPanel,BorderLayout.CENTER);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));


        // Thêm mainPanel vào JFrame
        this.add(mainPanel);
        this.setVisible(true);
    }
}
