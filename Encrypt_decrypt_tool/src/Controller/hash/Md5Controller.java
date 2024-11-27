package Controller.hash;

import Controller.traTab.CardController;

import UI.CustomDialog;
import UI.hash.MD5View;
import model.hash.MD5;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Map;

public class Md5Controller extends CardController {
    MD5 md5 ;
    MD5View md5View;

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
    public JButton clearBut ;
    String inputFilePath = null;


    public Md5Controller() {
        super();
        this.md5 = MD5.getInstance();
        this.md5View = new MD5View();
        super.setView(this.md5View);
        this.clearBut = this.md5View.clearBut;
        this.leftPane = this.md5View.leftPane;
        this.saveOutput = this.md5View.saveOutput;
        this.inputFName = this.md5View.inputFName;

        this.outPane = this.md5View.outPane;
        this.rightPane = this.md5View.rightPane;
        this.uploadInputBut = this.md5View.uploadInputBut;
        this.inputL = this.md5View.inputL;
        this.saveInputFile = this.md5View.saveInputFile;
        this.inputArea = this.md5View.inputArea;
        this.outPutL = this.md5View.outPutL;
        this.outputTextArea = this.md5View.outputTextArea;
        this.middepane = this.md5View.middepane;
        this.hashBut = this.md5View.hashBut;
        this.decryptBut = this.md5View.decryptBut;


//        //NOTE: thiếu private
        hashBut.addActionListener(e -> {
            String input = inputArea.getText();
            if (!input.trim().isEmpty()) {
                String hashText = null;
                hashText = md5.check(input);

                if (!hashText.isEmpty()) {
                    outputTextArea.setText(hashText);
                    CustomDialog dialog = new CustomDialog(md5View, "hash md5 thành công !!", "hash success! ");
                } else {
                    new CustomDialog(md5View, "Đã xảy ra lỗi: ", "Lỗi xử lý hash");
                }
                System.out.println("mã hóa");
            } else {
                //th file
                String inputPath = inputFilePath;
                System.out.println("in" + inputPath);
                if (inputPath == null || inputPath.isEmpty()) {
                    CustomDialog dialog = new CustomDialog(md5View, "Bạn cần nhập input trước", "Lỗi nhập inout ");
                } else {
                    //xử lí để ra tên của file
                    String[] path = extractFilePath(inputPath);
                    String outpath = path[0] + "_encrypted." + path[1];
                    String hashText = md5.checkFile(inputPath);
                    outputTextArea.setText(hashText);
                    CustomDialog dialog = new CustomDialog(md5View, "Mã hóa thành công !!", "ecrypt success! ");
                    System.out.println("mã hóa");
                }
            }
        });
        clearBut.addActionListener( e ->{
            outputTextArea.setText("");
            inputFName.setText("");
            inputArea.setText("");
            inputFilePath= null;
            CustomDialog customDialog  = new CustomDialog(md5View, "Đã clear dữ liệu thành công. Bây giờ có thể nhập text/file để hash","import file success",400,100);
            inputArea.setEditable(true);
            inputArea.setDisabledTextColor(Color.black); // Màu chữ xám
            inputArea.setBackground(Color.white);  // Nền xám nhạt
        });

        uploadInputBut.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent en) {
                if(en.getSource() == uploadInputBut){
                    JFileChooser fileChooser1 = new JFileChooser();
                    int res = fileChooser1.showOpenDialog(null);
                    if(res == JFileChooser.FILES_ONLY){
                        String filepath = fileChooser1.getSelectedFile().getAbsolutePath();
                        System.out.println(filepath);
                        inputFilePath = filepath;
                        System.out.println("he"+inputFilePath);
                        if (filepath.length() > 20) {
                            filepath = filepath.substring(0, 20) + "...";
                        }

                        inputFName.setText(filepath);
                        CustomDialog customDialog  = new CustomDialog(md5View, "Đã thêm file thành công. Bây giờ có thể hash","import file success");
                        inputArea.setEditable(false);
                        inputArea.setDisabledTextColor(Color.white); // Màu chữ xám
                        inputArea.setBackground(Color.LIGHT_GRAY);  // Nền xám nhạt
                    }
                }
            }
        });
        // Khi nhấn nút Save Key
        saveInputFile.addActionListener(e -> {
            try {
                // Lấy khóa từ JTextArea
                String inputText = inputArea.getText().trim();
                if (inputText.isEmpty()) {
                    new CustomDialog(md5View, "Input text trống. Vui lòng nhập input trước khi lưu.", "Lỗi lưu input");
                    return;
                }
                // Mở JFileChooser để chọn tệp lưu khóa
                JFileChooser fileChooser = new JFileChooser();
                int returnValue = fileChooser.showSaveDialog(md5View);

                // Nếu người dùng chọn tệp
                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fileChooser.getSelectedFile();

                    // Đảm bảo phần mở rộng là .key
                    if (!selectedFile.getName().endsWith(".key")) {
                        selectedFile = new File(selectedFile.getAbsolutePath() + "_input.txt");
                    }

                    // Lưu khóa vào tệp đã chọn
                    saveFile(inputText, selectedFile.getAbsolutePath());

                    // Hiển thị thông báo thành công
                    new CustomDialog(md5View, "Dữ liệu inout đã được lưu thành công tại: " + selectedFile.getAbsolutePath(), "Lưu key thành công");
                }
            } catch (IOException ex) {
                new CustomDialog(md5View, "Lỗi khi lưu input. Vui lòng kiểm tra tên tệp hoặc dung lượng ổ đĩa.", "Lưu key thất bại");
            } catch (Exception ex) {
                new CustomDialog(md5View, "Đã xảy ra lỗi không xác định (chịu rồi ní): " + ex.getMessage(), "Lỗi");
            }
        });
        saveOutput.addActionListener(e -> {
            try {
                // Lấy khóa từ JTextArea
                String inputText = outputTextArea.getText().trim();
                if (inputText.isEmpty()) {
                    new CustomDialog(md5View, "output text trống. Vui lòng nhập hash trước khi lưu.", "Lỗi lưu output",600,100);
                    return;
                }
                // Mở JFileChooser để chọn tệp lưu khóa
                JFileChooser fileChooser = new JFileChooser();
                int returnValue = fileChooser.showSaveDialog(md5View);

                // Nếu người dùng chọn tệp
                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fileChooser.getSelectedFile();

                    // Đảm bảo phần mở rộng là .key
                    if (!selectedFile.getName().endsWith(".key")) {
                        selectedFile = new File(selectedFile.getAbsolutePath() + "_output.txt");
                    }

                    // Lưu khóa vào tệp đã chọn
                    saveFile(inputText, selectedFile.getAbsolutePath());

                    // Hiển thị thông báo thành công
                    new CustomDialog(md5View, "Dữ liệu inout đã được lưu thành công tại: " + selectedFile.getAbsolutePath(), "Lưu key thành công");
                }
            } catch (IOException ex) {
                new CustomDialog(md5View, "Lỗi khi lưu input. Vui lòng kiểm tra tên tệp hoặc dung lượng ổ đĩa.", "Lưu key thất bại",600,100);
            } catch (Exception ex) {
                new CustomDialog(md5View, "Đã xảy ra lỗi không xác định (chịu rồi ní): " + ex.getMessage(), "Lỗi");
            }
        });


    }
    public static void openExplorer(String path) {
        File file = new File(path);

        if (!file.exists()) {
            JOptionPane.showMessageDialog(null, "Bị lỗi khi mở" + path, "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            // Nếu hệ điều hành là Windows
            if (System.getProperty("os.name").toLowerCase().contains("win")) {
                // Mở Explorer tại đường dẫn thư mục
                Runtime.getRuntime().exec("explorer.exe /select," + file.getAbsolutePath());
            } else if (System.getProperty("os.name").toLowerCase().contains("mac")) {
                // Nếu hệ điều hành là MacOS
                Runtime.getRuntime().exec(new String[] { "open", path });
            } else if (System.getProperty("os.name").toLowerCase().contains("nix") || System.getProperty("os.name").toLowerCase().contains("nux")) {
                // Nếu hệ điều hành là Linux
                Runtime.getRuntime().exec(new String[] { "xdg-open", path });
            }
        } catch (IOException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Failed to open Explorer.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    @Override
    public Map<String, Object> saveData() {
        return null;
    }

    @Override
    public void loadData() {

    }
    public  String[] extractFilePath(String fullPath) {
        // Tìm dấu phân cách cuối cùng
        int dotIndex = fullPath.lastIndexOf('.');
        int slashIndex = fullPath.lastIndexOf(File.separator);


        // Nếu tìm thấy dấu phân cách, trả về phần đường dẫn
        if (dotIndex == -1 || dotIndex < slashIndex) {
            return new String[]{fullPath, ""}; // Trả về đường dẫn và chuỗi rỗng cho extension
        }

        // Tách đường dẫn (không chứa extension) và phần mở rộng
        String path = fullPath.substring(0, dotIndex);
        String extension = fullPath.substring(dotIndex + 1);

        return new String[]{path, extension};
    }
    public void saveFile(String text, String filePath) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filePath))) {
            oos.writeObject(text);
        }
    }
}

