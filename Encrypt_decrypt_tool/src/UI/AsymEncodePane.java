package UI;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.awt.*;

public class AsymEncodePane extends JPanel {
    String[] algoNames = new String[]{"RSA","AES"}; //danh sách các algo
    String[] modeNames = new String[]{"ECB","CBC"}; //danh sách các mode và padding

    public AsymEncodePane(){
        this.setLayout(new BorderLayout());

        JPanel mainPanel = new JPanel();
        JPanel headPane = new JPanel();
        headPane.setLayout(new FlowLayout());
        JLabel chooseAlgo = new JLabel("Chọn thuật toán: ");

        JComboBox<String> algoBox = new JComboBox<>(algoNames);
        JLabel uploadL = new JLabel("import key file (nếu có): ");
        JButton uploadFile = new JButton("Chọn File");
        JButton cancelUpload = new JButton("Hủy chọn File");

        JLabel chooseModeL = new JLabel("Chọn Mode");
        JComboBox<String> modeBox = new JComboBox<>(modeNames);
        headPane.add(chooseAlgo);
        headPane.add(algoBox);

        JPanel subhead = new JPanel();
        subhead.add(chooseModeL);
        subhead.add(modeBox);

        JPanel header = new JPanel(new GridLayout(2,0));
        header.add(headPane);
        header.add(subhead);

        JPanel centerPane = new JPanel();
        JPanel rightPane = new JPanel();
        JPanel leftPane = new JPanel();

        JLabel chooseKeyLengthL = new JLabel("Chọn độ dài key: ");
        String[] keyLens = new String[]{"580","1024"};
        JComboBox<String> chooseKeyLengthBox = new JComboBox<>(keyLens);
        JButton createKey = new JButton("Tạo Key");
        JLabel publicKeyL = new JLabel("Khóa công khai");
        JButton choosePKey = new JButton("Chọn khóa");
        JButton saveBut1 = new JButton("Lưu");
        JTextArea publicKeyArea = new JTextArea(5,20);
        publicKeyArea.setLineWrap(true);
        publicKeyArea.setWrapStyleWord(true);
        JLabel privateKeyL = new JLabel("Khóa bí mật");
        JButton choosePrKey = new JButton("Chọn khóa");
        JButton saveBut2 = new JButton("Lưu");
        JTextArea privateKeyArea = new JTextArea(5,20);
        privateKeyArea.setLineWrap(true);
        privateKeyArea.setWrapStyleWord(true);
        JPanel importKeyPane = new JPanel(new FlowLayout());
        importKeyPane.add(uploadL);
        importKeyPane.add(uploadFile);
        leftPane.add(importKeyPane);
        JPanel keyPane = new JPanel(new FlowLayout());
        keyPane.add(chooseKeyLengthL);
        keyPane.add(chooseKeyLengthBox);
        keyPane.add(createKey);

        JPanel publicPane = new JPanel();
        publicPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        publicPane.setLayout(new GridLayout(1, 3));
        publicPane.add(publicKeyL);
        publicPane.add(choosePKey);
        publicPane.add(saveBut1);
        JScrollPane publicscrollPane1 = new JScrollPane(publicKeyArea);

        JPanel privatePane = new JPanel();
        privatePane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        privatePane.setLayout(new GridLayout(1, 3));
        privatePane.add(privateKeyL);
        privatePane.add(choosePrKey);
        privatePane.add(saveBut2);
        JScrollPane privatescrollPane1 = new JScrollPane(privateKeyArea);


        leftPane.setLayout(new BoxLayout(leftPane,BoxLayout.Y_AXIS));
        leftPane.setBorder(BorderFactory.createTitledBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED),"Chọn khóa"));
        leftPane.add(keyPane);
        leftPane.add(publicPane);
        leftPane.add(publicscrollPane1);
        leftPane.add(privatePane);
        leftPane.add(privatescrollPane1);

        // right Pane
        JLabel inputL = new JLabel("Input: ");
        JButton encryptBut = new JButton("Mã hóa");
        JButton decryptBut = new JButton("Giải mã");
        JPanel inPane = new JPanel();
        inPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        inPane.setLayout(new GridLayout(1,3));
        inPane.add(inputL);
        inPane.add(encryptBut);
        inPane.add(decryptBut);
        JTextArea inputTextArea = new JTextArea(10,20);
        publicKeyArea.setLineWrap(true);
        publicKeyArea.setWrapStyleWord(true);
        JScrollPane inputScrollPane = new JScrollPane(inputTextArea);
        JLabel outputL = new JLabel("Output: ");
        JPanel outPane = new JPanel();
        outPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        outPane.setLayout(new GridLayout(1,3));
        outPane.add(outputL);
        JTextArea outputTextArea = new JTextArea(10,20);
        publicKeyArea.setLineWrap(true);
        publicKeyArea.setWrapStyleWord(true);
        JScrollPane outputScrollPane = new JScrollPane(outputTextArea);

        rightPane.setLayout(new BoxLayout(rightPane,BoxLayout.Y_AXIS));
        rightPane.setBorder(BorderFactory.createTitledBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED),"Mã hóa/Giải mã"));
        rightPane.add(inPane);
        rightPane.add(inputScrollPane);
        rightPane.add(outPane);
        rightPane.add(outputScrollPane);

        //tạo border
        headPane.setBorder(BorderFactory.createTitledBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED),""));
        subhead.setBorder(BorderFactory.createTitledBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED),""));
        centerPane.setLayout(new BoxLayout(centerPane,BoxLayout.X_AXIS));
        centerPane.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
        centerPane.add(leftPane);
        centerPane.add(rightPane);
        mainPanel.setLayout(new BorderLayout());
        mainPanel.add(header,BorderLayout.NORTH);
//        mainPanel.add(subhead,BorderLayout.NORTH);
        mainPanel.add(centerPane,BorderLayout.CENTER);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Thêm mainPanel vào JFrame
        this.add(mainPanel);
        this.setVisible(true);
    }
}
