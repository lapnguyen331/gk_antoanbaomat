package UI;

import javax.swing.*;
import java.awt.*;

public class HashPane extends JPanel {
    public HashPane(){
        this.setLayout(new BorderLayout());
        JLabel label = new JLabel("Nội dung Tab 4", SwingConstants.CENTER);
        this.add(label, BorderLayout.CENTER);
        this.setBackground(Color.PINK);
    }
}
