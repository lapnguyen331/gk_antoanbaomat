package UI.sym;

import UI.tradition.CardView;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.awt.*;
import java.util.Map;

public class AESView extends JPanel implements CardView {
    public String[] modeNames = new String[]{"CBC","ECB","CFB","CTR","GCM"}; //danh sách các mode và padding
    public String [] paddingName = new String[]{"PKCS5Padding","PKCS7Padding","Zero","NoPadding"};
    public String[] keyLens = new String[]{"128","192","256"};

    public JButton uploadKeyFile;
    public JButton uploadInputFile;
    public JButton cancelUpload;
    public JComboBox<String> modeBox;
    public JComboBox<String> paddingBox;
    public JPanel subHeadPane;
    public JComboBox<String> chooseKeyLengthBox;
    public JButton chooseKey;
    public JButton createKey;
    public JButton saveBut;

    public JTextArea keyArea;

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
    public AESView(){

        JLabel uploadL = new JLabel("import key file (nếu có): ");
        uploadKeyFile = new JButton("Chọn File");
        cancelUpload = new JButton("Hủy chọn File");
        JLabel chooseModeL = new JLabel("Chọn Mode:");
        modeBox = new JComboBox<>(modeNames);
        JLabel choosePadding = new JLabel("Chọn Padding:");
        paddingBox = new JComboBox<>(paddingName);

        subHeadPane = new JPanel();
        subHeadPane.add(chooseModeL);
        subHeadPane.add(modeBox);
        subHeadPane.add(choosePadding);
        subHeadPane.add(paddingBox);
        //left pane
        leftPane = new JPanel();
        JLabel chooseKeyLengthL = new JLabel("Chọn độ dài key: ");
        chooseKeyLengthBox = new JComboBox<>(keyLens);
        createKey = new JButton("Tạo Key");
        saveBut = new JButton("Lưu");

        JLabel keyL = new JLabel("Khóa:");
        chooseKey = new JButton("Chọn khóa");
        keyArea = new JTextArea(10,20);
        keyArea.setFont(new Font("Monospaced", Font.PLAIN, 14));
        keyArea.setLineWrap(true);
        keyArea.setWrapStyleWord(true);
        importKeyPane = new JPanel(new FlowLayout());
        importKeyPane.add(uploadL);
        importKeyPane.add(uploadKeyFile);
        leftPane.add(importKeyPane);
        keyPane = new JPanel(new FlowLayout());
        keyPane.add(chooseKeyLengthL);
        keyPane.add(chooseKeyLengthBox);
        keyPane.add(createKey);

        JPanel publicPane = new JPanel();
        publicPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        publicPane.setLayout(new GridLayout(1, 3));
        publicPane.add(keyL);
        publicPane.add(chooseKey);
        publicPane.add(saveBut);


        JScrollPane privatescrollPane1 = new JScrollPane(keyArea);

        leftPane.setLayout(new BoxLayout(leftPane,BoxLayout.Y_AXIS));
        leftPane.setBorder(BorderFactory.createTitledBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED),"Chọn khóa"));
        leftPane.add(keyPane);
        leftPane.add(publicPane);
        leftPane.add(privatescrollPane1);
        // right Pane
        rightPane = new JPanel();
        JLabel inputL = new JLabel("Input: ");
        uploadInputFile = new JButton("Chọn File Input");

        encryptBut = new JButton("Mã hóa");
        decryptBut = new JButton("Giải mã");
        inPane = new JPanel();
        inPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        inPane.setLayout(new GridLayout(1, 4));
        inPane.add(inputL);
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
