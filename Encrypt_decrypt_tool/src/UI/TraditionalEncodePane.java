package UI;

import UI.tradition.*;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.awt.*;

public class TraditionalEncodePane extends JPanel {
    public String[] algoNames = new String[]{"Caesar(dịch chuyển)","Substitutuin(Thay thế)","Affine","Hill","Vigenere"}; //danh sách các algo
    public JComboBox<String> comboBox;
    public JPanel mainPanel;
    public  JPanel headPane;
    public  JLabel chooseAlgo;

    public JPanel cardPane; //panel chính bọc các card
    public CardLayout cardLayout;
    public  CaesarView caesarView;
    public HillView hillView;
    public AffineView affineView;
    public VigenereView vigenereView;
    public SubstitutionView substitutionView;
    public TraditionalEncodePane() {
        this.setLayout(new BorderLayout());


        mainPanel = new JPanel();
        headPane = new JPanel();
        headPane.setLayout(new FlowLayout());
        chooseAlgo = new JLabel("Chọn thuật toán: ");
        //thêm component cho headpane
        comboBox = new JComboBox<>(algoNames);
        headPane.add(chooseAlgo);
        headPane.add(comboBox);
//
//        caesarView = new CaesarView();
//        hillView = new HillView();
//        substitutionView = new SubstitutionView();
//        vigenereView = new VigenereView();
//        affineView = new AffineView();
//        cardLayout = new CardLayout();
        this.cardLayout = new CardLayout();
        this.cardPane = new JPanel(this.cardLayout);

//        cardPane.add(caesarView,"caesarPane");
//        cardPane.add(substitutionView,"substitutionPane");
//        cardPane.add(hillView,"hillPane");
//        cardPane.add(vigenereView,"vigenerePane");
//        cardPane.add(affineView,"affinePane");

        headPane.setBorder(BorderFactory.createTitledBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED), ""));

        mainPanel.setLayout(new BorderLayout());
        mainPanel.add(headPane, BorderLayout.NORTH);
        mainPanel.add(cardPane, BorderLayout.CENTER);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Thêm mainPanel vào JFrame
        this.add(mainPanel);
        this.setVisible(true);
    }
}
