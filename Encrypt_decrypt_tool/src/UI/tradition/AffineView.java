package UI.tradition;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.awt.*;
import java.util.Map;

public class AffineView extends JPanel implements CardView {
    public JPanel rightPane ;
    public JPanel leftPane;
    public JLabel chooseKeyL;
    public JButton createKey;
    public JTextField fieldA;
    public JTextField fieldB;

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
    public AffineView(){
        rightPane = new JPanel();
        leftPane = new JPanel();

        chooseKeyL = new JLabel("Nhập key vào field hoặc tạo Key : ");
        createKey = new JButton("Tạo Key");
        chooseKey = new JButton("Chọn Key");
        fieldA = new JTextField(8);
        fieldA.setHorizontalAlignment(JTextField.CENTER);
        Font fo = new Font("Serif", Font.BOLD, 20);
        fieldA.setFont(fo);
        fieldB = new JTextField(8);
        fieldB.setHorizontalAlignment(JTextField.CENTER);
        fieldB.setFont(fo);
        JLabel la = new JLabel("Nhập Key A: ");
        JLabel lb = new JLabel("Nhập Key B: ");

        JLabel ja = new JLabel();
        ja.setLayout(new FlowLayout());
        ja.add(la);
        ja.add(fieldA);

        JLabel jb = new JLabel();
        jb.setLayout(new FlowLayout());
        jb.add(lb);
        jb.add(fieldB);
        JLabel jbut= new JLabel();
        jbut.setLayout(new FlowLayout(FlowLayout.CENTER));
        jbut.add(createKey);
        jbut.add(chooseKey);

        keyPane = new JPanel();


        keyPane.add(chooseKeyL);

        keyPane.add(ja);
        keyPane.add(jb);
        keyPane.add(jbut);
        keyPane.setLayout(new GridLayout(4,1));
        chooseKey.setVisible(false);

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


    public AffineView(Map<String, Object> stringObjectMap) {
    }

    @Override
    public Map<String,Object> getInputData() {
        return null;
    }

    @Override
    public void loadData(Map<String, Object> data) {

    }
}
