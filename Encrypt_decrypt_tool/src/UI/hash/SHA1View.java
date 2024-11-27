package UI.hash;

import UI.tradition.CardView;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.Map;

public class SHA1View extends JPanel implements CardView {

    public JPanel leftPane;
    public JButton saveOutput;

    public JPanel outPane;
    public  JPanel rightPane;

    public JButton uploadInputBut;
    public JLabel inputL;
    public JButton saveInputFile;
    public JTextArea inputArea;
    public JLabel outPutL;
    public JTextArea outputTextArea;
    public JPanel middepane;
    public JButton hashBut;
    public JButton decryptBut;
    public JLabel inputFName;
    public JButton clearBut;
    public SHA1View(){

        //left pane
        leftPane = new JPanel();

        inputL = new JLabel("Nhập Input");
        inputFName= new JLabel();
        uploadInputBut = new JButton("Chọn File");
        saveInputFile = new JButton("Lưu file");
        inputArea = new JTextArea(14,20);
        inputArea.setFont(new Font("Monospaced", Font.PLAIN, 14));
        inputArea.setLineWrap(true);
        inputArea.setWrapStyleWord(true);
        uploadInputBut.setPreferredSize(new Dimension(30,30));
        saveInputFile.setPreferredSize(new Dimension(30,30));
        inputL.setPreferredSize(new Dimension(70, 30));
        inputFName.setPreferredSize(new Dimension(70, 30));
        JPanel inputPane = new JPanel();
        inputPane.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        inputPane.setLayout(new GridLayout(1, 5));
        inputPane.setPreferredSize(new Dimension(100,10));
        inputPane.add(inputL);
        inputPane.add(inputFName);
        inputPane.add(uploadInputBut);
        inputPane.add(saveInputFile);
        JScrollPane inputcrollPane1 = new JScrollPane(inputArea);




        leftPane.setLayout(new BoxLayout(leftPane,BoxLayout.Y_AXIS));
        leftPane.setBorder(BorderFactory.createTitledBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED),"Input"));
        leftPane.add(inputPane);
        leftPane.add(inputcrollPane1);

        // right Pane
        rightPane = new JPanel();
        outPutL = new JLabel("Output: ");
        saveOutput = new JButton("Lưu File");
        saveOutput.setPreferredSize(new Dimension(30,10));

        outPane = new JPanel();
        outPane.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        outPane.setPreferredSize(new Dimension(100,10));
        outPane.setLayout(new GridLayout(1, 2));
        outPane.add(outPutL);
        outPane.add(saveOutput);
        outputTextArea = new JTextArea(14, 20);
        outputTextArea.setFont(new Font("Monospaced", Font.PLAIN, 14));
        outputTextArea.setLineWrap(true);
        outputTextArea.setWrapStyleWord(true);
        JScrollPane outputScrollPane = new JScrollPane(outputTextArea);


        rightPane.setLayout(new BoxLayout(rightPane, BoxLayout.Y_AXIS));
        rightPane.setBorder(BorderFactory.createTitledBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED), "Mã hóa/Giải mã"));
        rightPane.add(outPane);
        rightPane.add(outputScrollPane);

        //pane ở giữa
        middepane = new JPanel();
        hashBut = new JButton("Hash ->");
        clearBut = new JButton("Clear X ");
        hashBut.setPreferredSize(new Dimension(150, 30));
        clearBut.setPreferredSize(new Dimension(150, 30));

        middepane.setLayout(new BoxLayout(middepane, BoxLayout.X_AXIS));
        middepane.setBorder(new EmptyBorder(10, 10, 10, 10));
        middepane.setAlignmentX(Component.CENTER_ALIGNMENT);

        JPanel mid = new JPanel();
        mid.setLayout(new BoxLayout(mid, BoxLayout.Y_AXIS));
        mid.add(hashBut);
        mid.add(clearBut);
        middepane.add(Box.createVerticalGlue());
        middepane.add(mid);

        JPanel mainPane = new JPanel();
        mainPane.setLayout(new BoxLayout(mainPane, BoxLayout.X_AXIS));
        mainPane.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
        mainPane.add(leftPane);
        mainPane.add(middepane);
        mainPane.add(rightPane);
        this.setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
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
