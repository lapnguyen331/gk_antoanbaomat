package UI;

import javax.swing.*;
import java.awt.*;

public class SymEncodePane extends JPanel {
    public SymEncodePane(){
        this.setLayout(new BorderLayout());
        JLabel label = new JLabel("Nội dung Tab 2", SwingConstants.CENTER);
        this.add(label, BorderLayout.CENTER);
        this.setBackground(Color.PINK);

        setLayout(new BorderLayout());




        this.setVisible(true);
    }
}
