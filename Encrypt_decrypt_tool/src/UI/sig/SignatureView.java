package UI.sig;

import UI.tradition.CardView;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.awt.*;
import java.util.Map;

public class SignatureView extends JPanel implements CardView {
    public String[] modePadingNames = new String[]{"ECB/PKCS1Padding","ECB/OAEPWithSHA-256AndMGF1Padding","ECB/OAEPWithSHA-512AndMGF1Padding","ECB/PKCS1PSSPadding"}; //danh sách các mode và padding

    public String[] keyLens = new String[]{"512","1024","2048","3072","4096","8192"};

    public JButton uploadInputFile;
    public JComboBox<String> modePaddingBox;
    public JPanel subHeadPane;
    public JComboBox<String> chooseKeyLengthBox;
    public JToggleButton choosePKey;
    public JButton createKey;

    public JTextArea keyPArea;

    public JPanel importKeyPane;
    public JPanel leftPane;
    public JPanel keyPane;
    public JButton encryptBut;
    public  JButton decryptBut;
    public JPanel inPane;
    public JPanel outPane;
    public JTextArea inputTextArea;
    public JTextArea outputTextArea;
    public  JPanel rightPane;
    public JButton downloadBut;
    public JLabel fileKeyName;
    public JLabel inputFName;
    public JToggleButton choosePrKey;
    public JTextArea keyPrArea;
    public JButton saveKPBut;
    public JButton saveKPrBut;
    public  JLabel keyPL;
    public JButton uploadPKeyBut;
    public JButton uploadPrKeyBut;
    public JLabel uploadPrKeyL;
    public JLabel uploadPKeyL;
    public SignatureView(){

        fileKeyName = new JLabel();
        JLabel modePaddingL = new JLabel("chọn mode/Padding");

        modePaddingBox = new JComboBox<>(modePadingNames);

        subHeadPane = new JPanel();
        subHeadPane.add(modePaddingL);
        subHeadPane.add(modePaddingBox);
//        subHeadPane.add(choosePadding);
        //left pane
        leftPane = new JPanel();
        JLabel chooseKeyLengthL = new JLabel("Chọn độ dài key: ");
        chooseKeyLengthBox = new JComboBox<>(keyLens);
        createKey = new JButton("Tạo cặp Key");
        saveKPBut = new JButton("Lưu");

        keyPL = new JLabel("Khóa public:");
        uploadPKeyL = new JLabel();
        uploadPKeyBut = new JButton("Chọn File");
        choosePKey = new JToggleButton("Chọn khóa");
        keyPArea = new JTextArea(10,20);
        keyPArea.setFont(new Font("Monospaced", Font.PLAIN, 14));
        keyPArea.setLineWrap(true);
        keyPArea.setWrapStyleWord(true);
        uploadPKeyBut.setPreferredSize(new Dimension(70,30));
        choosePKey.setPreferredSize(new Dimension(70,30));
        saveKPBut.setPreferredSize(new Dimension(70,30));
        uploadPKeyL.setPreferredSize(new Dimension(70, 30));

        JLabel keyPrL = new JLabel("Khóa private:");
        uploadPrKeyL = new JLabel();
        uploadPrKeyBut = new JButton("Chọn File");

        choosePrKey = new JToggleButton("Chọn khóa");
        saveKPrBut = new JButton("Lưu");
        saveKPBut = new JButton("Lưu");

        keyPrArea = new JTextArea(10,20);
        keyPrArea.setFont(new Font("Monospaced", Font.PLAIN, 14));
        keyPrArea.setLineWrap(true);
        keyPrArea.setWrapStyleWord(true);


        keyPane = new JPanel(new FlowLayout());
        keyPane.add(chooseKeyLengthL);
        keyPane.add(chooseKeyLengthBox);
        keyPane.add(createKey);

        JPanel publicPane = new JPanel();
        publicPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        publicPane.setLayout(new GridLayout(1, 5));
        publicPane.add(keyPL);
        publicPane.add(uploadPKeyL);
        publicPane.add(uploadPKeyBut);
        publicPane.add(choosePKey);
        publicPane.add(saveKPBut);
        JScrollPane publicscrollPane1 = new JScrollPane(keyPArea);

        JPanel privatePane = new JPanel();
        privatePane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        privatePane.setLayout(new GridLayout(1, 5));
        privatePane.add(keyPrL);
        privatePane.add(uploadPrKeyL);
        privatePane.add(uploadPrKeyBut);

        privatePane.add(choosePrKey);
        privatePane.add(saveKPrBut);
        JScrollPane privatescrollPane1 = new JScrollPane(keyPrArea);



        leftPane.setLayout(new BoxLayout(leftPane,BoxLayout.Y_AXIS));
        leftPane.setBorder(BorderFactory.createTitledBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED),"Chọn khóa"));
        leftPane.add(keyPane);
        leftPane.add(publicPane);
        leftPane.add(publicscrollPane1);
        leftPane.add(privatePane);
        leftPane.add(privatescrollPane1);

        // right Pane
        rightPane = new JPanel();
        JLabel inputL = new JLabel("Input: ");
        uploadInputFile = new JButton("Chọn File Input");
        inputFName = new JLabel();
        uploadInputFile.setPreferredSize(new Dimension(100,30));
        inputFName.setPreferredSize(new Dimension(80, 30));
        encryptBut = new JButton("Mã hóa");
        decryptBut = new JButton("Giải mã");
        encryptBut.setPreferredSize(new Dimension(70,30));
        decryptBut.setPreferredSize(new Dimension(70,30));

        inPane = new JPanel();
        inPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        inPane.setLayout(new GridLayout(1, 5));
        inPane.add(inputL);
        inPane.add(inputFName);
        inPane.add(uploadInputFile);
        inPane.add(encryptBut);
        inPane.add(decryptBut);
        inputTextArea = new JTextArea(10, 20);
        JScrollPane inputScrollPane = new JScrollPane(inputTextArea);
        JLabel outputL = new JLabel("Output: ");
        downloadBut =new JButton("xem file output");
        outPane = new JPanel();
        outPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        outPane.setLayout(new GridLayout(1, 3));
        outPane.add(outputL);
        outPane.add(downloadBut);

        outputTextArea = new JTextArea(10, 20);

         JScrollPane outputScrollPane = new JScrollPane(outputTextArea);

        rightPane.setLayout(new BoxLayout(rightPane, BoxLayout.Y_AXIS));
        rightPane.setBorder(BorderFactory.createTitledBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED), "Mã hóa/Giải mã"));
        rightPane.add(inPane);
        rightPane.add(inputScrollPane);
        rightPane.add(outPane);
        rightPane.add(outputScrollPane);

        JPanel mainPane = new JPanel();
        mainPane.setLayout(new BoxLayout(mainPane, BoxLayout.X_AXIS));
        mainPane.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
        mainPane.add(leftPane);
        mainPane.add(rightPane);
        //tạo border
//        this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
//        this.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
//        this.add(leftPane);
//        this.add(rightPane);
        this.setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
        this.add(subHeadPane);
        this.add(mainPane);
    }
    @Override
    public Map<String, Object> getInputData() {
        return null;
    }

    @Override
    public void loadData(Map<String, Object> data) {

    }
}
