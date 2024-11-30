package UI.sig;

import UI.tradition.CardView;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.awt.*;
import java.util.Map;

public class SignatureView extends JPanel implements CardView {

    public JButton uploadInputFile;
    public JPanel subHeadPane;
    public JToggleButton choosePKey;
    public JButton createKey;

    public JTextArea keyPArea;

    public JPanel importKeyPane;
    public JPanel leftPane;
    public JPanel keyPane;
    public JButton createSigBut;
    public  JButton validateSigBut;
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


        subHeadPane = new JPanel();

//        subHeadPane.add(choosePadding);
        //left pane
        leftPane = new JPanel();
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
        createSigBut = new JButton("Kí ");
        validateSigBut = new JButton("Xác minh");
        createSigBut.setPreferredSize(new Dimension(70,30));
        validateSigBut.setPreferredSize(new Dimension(70,30));

        inPane = new JPanel();
        inPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        inPane.setLayout(new GridLayout(1, 5));
        inPane.add(inputL);
        inPane.add(inputFName);
        inPane.add(uploadInputFile);

        JPanel midpane = new JPanel();
        midpane.setLayout(new FlowLayout());
        midpane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
//        midpane.setPreferredSize(new Dimension(1000,40));
        createSigBut.setPreferredSize(new Dimension(100,30));
        validateSigBut.setPreferredSize(new Dimension(120,30));

        midpane.add(createSigBut);
        midpane.add(validateSigBut);

        inputTextArea = new JTextArea(10, 20);
        JScrollPane inputScrollPane = new JScrollPane(inputTextArea);
        inputScrollPane.setBorder(BorderFactory.createTitledBorder("Nhập chữ kí:"));

        JLabel outputL = new JLabel("Output: ");
        downloadBut =new JButton("xem file output");
        downloadBut.setPreferredSize(new Dimension(80,30));
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
        rightPane.add(midpane);
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
