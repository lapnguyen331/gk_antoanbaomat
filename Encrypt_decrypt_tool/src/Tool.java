import Controller.MainController;
import Controller.SymTabController;
import Controller.TabController;
import Controller.TraTabController;
import UI.*;

import javax.swing.*;
import java.awt.*;

public class Tool {

    public static void main(String[] args) {
        // Khởi chạy ứng dụng trong luồng giao diện người dùng chính
        SwingUtilities.invokeLater(() -> {
            // Khởi tạo MainController và các tab controller

            //tạo các tabpane
            TraditionalEncodePane tratab  = new TraditionalEncodePane();
            SymEncodePane symtab = new SymEncodePane();
            ASymEncodePane aSymtab = new ASymEncodePane();
            HashEncodePane hashEncodePane = new HashEncodePane();
            SignatureEncodePane signatureEncodePane = new SignatureEncodePane();
            // Tạo View chính (JFrame)
            MainJPanel mainFrame = new MainJPanel(tratab,symtab,aSymtab,hashEncodePane,signatureEncodePane);
            MainController mainController = new MainController(mainFrame);
        });
    }
}
