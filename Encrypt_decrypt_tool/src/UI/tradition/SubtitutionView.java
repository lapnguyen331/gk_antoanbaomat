package UI.tradition;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.awt.*;
import java.util.Map;

public class SubtitutionView extends JPanel implements CardView {
    public JPanel rightPane ;
    public JPanel leftPane;
    public JLabel chooseKeyL;
    public JButton createKey;
    public JTextArea field;
    public JPanel keyPane;
    public JLabel inputL;
    public JButton encryptBut;
    public JButton decryptBut;
    public JPanel inPane;
    public JTextArea inputTextArea;
    public JScrollPane inputScrollPane;
    public JLabel outputL;
    public JPanel outPane;
    public JScrollPane outputScrollPane;
    public JTextArea outputTextArea;
    public JButton chooseKey;
    public SubtitutionView(){
        rightPane = new JPanel();
        leftPane = new JPanel();

        chooseKeyL = new JLabel("Nhập key vào field hoặc tạo Key : ");
        createKey = new JButton("Tạo Key");
        chooseKey = new JButton("Chọn Key");

        field = new JTextArea(5,2);
        field.setLineWrap(true);
//        field.setWrapStyleWord(true);
        JScrollPane keyscroll  = new JScrollPane(field);
        keyscroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        Font fo = new Font("Serif", Font.BOLD, 15);
        field.setFont(fo);
//        keyscroll.setPreferredSize(new Dimension(30, 500)); // Kích thước tùy chỉnh cho JScrollPane


        keyPane = new JPanel();
        JPanel jPanel = new JPanel();
        jPanel.setLayout(new GridLayout(0,2));
        jPanel.add(chooseKeyL);
        jPanel.add(keyscroll);
        JPanel butPane = new JPanel();
        butPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
        butPane.add(createKey);
        butPane.add(chooseKey);

        keyPane.setLayout(new GridLayout(3, 0));
//        keyPane.add(chooseKeyL);
//        keyPane.add(keyscroll);
        keyPane.add(jPanel);
        keyPane.add(butPane);
        chooseKey.setVisible(true);

        leftPane.setLayout(new BoxLayout(leftPane, BoxLayout.Y_AXIS));
        leftPane.setBorder(BorderFactory.createTitledBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED), "Cài đặt"));
        leftPane.add(keyPane);

        // right Pane
        inputL = new JLabel("Input: ");
        encryptBut = new JButton("Mã hóa");
        decryptBut = new JButton("Giải mã");
        inPane = new JPanel();
        inPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        inPane.setLayout(new GridLayout(1, 3));
        inPane.add(inputL);
        inPane.add(encryptBut);
        inPane.add(decryptBut);
        inputTextArea = new JTextArea(10, 20);

        inputScrollPane = new JScrollPane(inputTextArea);
        outputL = new JLabel("Output: ");
        outPane = new JPanel();
        outPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        outPane.setLayout(new GridLayout(1, 3));
        outPane.add(outputL);
        outputTextArea = new JTextArea(10, 20);

        outputScrollPane = new JScrollPane(outputTextArea);

        rightPane.setLayout(new BoxLayout(rightPane, BoxLayout.Y_AXIS));
        rightPane.setBorder(BorderFactory.createTitledBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED), "Mã hóa/Giải mã"));
        rightPane.add(inPane);
        rightPane.add(inputScrollPane);
        rightPane.add(outPane);
        rightPane.add(outputScrollPane);

        //tạo border
        this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        this.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
        this.add(leftPane);
        this.add(rightPane);
    }

    public SubtitutionView(Map<String, Object> stringObjectMap) {
    }

    @Override
    public Map<String,Object> getInputData() {
        return null;
    }

    @Override
    public void loadData(Map<String, Object> data) {

    }
}
