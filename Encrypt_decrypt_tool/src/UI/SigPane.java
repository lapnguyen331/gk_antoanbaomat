package UI;

import javax.swing.*;
import java.awt.*;

public class SigPane  extends JPanel {
    public SigPane(){
        this.setLayout(new BorderLayout());
        JLabel label = new JLabel("Nội dung Tab 5", SwingConstants.CENTER);
        this.add(label, BorderLayout.CENTER);
        this.setBackground(Color.PINK);
    }
}
