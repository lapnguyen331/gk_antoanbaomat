package Controller.asymTab;

import Controller.traTab.CardController;
import UI.Asym.RSAView;
import UI.CustomDialog;
import model.symAlgo.AES;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Map;

public class RSAController extends CardController {
    AES rsa;
    RSAView rsaView;
    public JButton uploadInputFile;
    public JComboBox<String> modePaddingBox;
    public JPanel subHeadPane;
    public JComboBox<String> chooseKeyLengthBox;
    public JButton choosePKey;
    public JButton createKey;

    public JTextArea keyPArea;

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
    public JLabel fileKeyName;
    public JLabel inputFName;
    JButton choosePrKey;
    JTextArea keyPrArea;
    public JButton saveKPBut;
    JButton saveKPrBut;
    JLabel keyPL;
    JButton uploadPKeyBut;
    JButton uploadPrKeyBut;
    JLabel uploadPrKeyL;
    JLabel uploadPKeyL;
    String inputFilePath;
    String outputFilePath;


    public RSAController() {
        super();
        this.rsa = new AES();
        this.rsaView = new RSAView();
        super.setView(this.rsaView);

        this.uploadInputFile=rsaView.uploadInputFile;
        this.modePaddingBox = rsaView.modePaddingBox;
        this.subHeadPane= rsaView.subHeadPane;
        this.chooseKeyLengthBox= rsaView.chooseKeyLengthBox;
        this.choosePKey= rsaView.choosePKey;
        this.createKey= rsaView.createKey;

        this.keyPArea= rsaView.keyPArea;

        this.importKeyPane= rsaView.importKeyPane;
        this.leftPane= rsaView.leftPane;
        this.keyPane= rsaView.keyPane;
        this.encryptBut= rsaView.encryptBut;
        this.decryptBut= rsaView.decryptBut;
        this.inPane= rsaView.inPane;
        this.outPane= rsaView.outPane;
        this.inputTextArea= rsaView.inputTextArea;
        this.outputTextArea= rsaView.outputTextArea;
        this.rightPane= rsaView.rightPane;
        this.downloadBut= rsaView.downloadBut;
        this.fileKeyName= rsaView.fileKeyName;
        this.inputFName= rsaView.inputFName;
        this.choosePrKey= rsaView.choosePrKey;
        this.keyPrArea= rsaView.keyPrArea;
        this.saveKPBut= rsaView.saveKPBut;
        this.saveKPrBut= rsaView.saveKPrBut;
        this.keyPL= rsaView.keyPL;
        this.uploadPKeyBut= rsaView.uploadPKeyBut;
        this.uploadPrKeyBut= rsaView.uploadPrKeyBut;
        this.uploadPrKeyL= rsaView.uploadPrKeyL;
        this.uploadPKeyL= rsaView.uploadPKeyL;

        //set giá trị mặc định cho mode padding keylength
        setDefaultValue(this.chooseKeyLengthBox,this.modePaddingBox);

        createKey.addActionListener(e -> {
            if(!rsa.isKeyExist()){
                try {
                    SecretKey key = this.rsa.genKey();
                    //set giá trị key vào field
                    keyPrArea.setText(rsa.secretKeyToBase64(key));
                    keyPrArea.setEditable(false);
//                    this.chooseKeyBut.setVisible(true);
                    System.out.println("createKey");

                } catch (NoSuchAlgorithmException ex) {
                    throw new RuntimeException(ex);
                }

            }else {
                CustomDialog dialog = new CustomDialog(rsaView, "Key đã có, bạn có thể mã hóa /giải mã ", "Lỗi nhập key ");
            }
        });
        //cho phép chọn key
        choosePrKey.addActionListener(e ->{
            // nếu đã tồn tại thì ko cần
            if(rsa.isKeyExist()){

            }else {
                String keyString = keyPrArea.getText();
                if(rsa.checkInputKey(keyString)){
                    if(updateKeylen(rsa.getInputKeySize(keyString))){
                        this.rsa.loadKey(rsa.base64ToSecretKey(keyString));
                        System.out.println("chọn key --" +keyString);
                    }
                }else {
                    CustomDialog dialog = new CustomDialog(rsaView, "Key lỗi, key cần có độ dài" + rsa.getLengthKey() +" bits hoặc thay đổi độ dài kay", "Lỗi nhập key ");
                }
            }
        });

        choosePKey.addActionListener(e ->{
            // nếu đã tồn tại thì ko cần
            if(rsa.isKeyExist()){

            }else {
                String keyString = keyPArea.getText();
                if(rsa.checkInputKey(keyString)){
                    if(updateKeylen(rsa.getInputKeySize(keyString))){
                        this.rsa.loadKey(rsa.base64ToSecretKey(keyString));
                        System.out.println("chọn key --" +keyString);
                    }
                }else {
                    CustomDialog dialog = new CustomDialog(rsaView, "Key lỗi, key cần có độ dài" + rsa.getLengthKey() +" bits hoặc thay đổi độ dài kay", "Lỗi nhập key ");
                }
            }
        });
        saveKPBut.addActionListener(e ->{
            // nếu đã tồn tại thì ko cần
            //NOTE: chưa handle luu file
            System.out.println("lưu file key");

        });

        //NOTE: thiếu private
        encryptBut.addActionListener(e -> {
            String input = inputTextArea.getText();
            if (!input.trim().isEmpty()) {
                if (rsa.isKeyExist()) {
                    if (rsa.checkKey()) {
                        String cipher = null;
                        try {
                            cipher = rsa.encryptBase64(input);
                        } catch (NoSuchPaddingException | NoSuchAlgorithmException | InvalidKeyException |
                                 IllegalBlockSizeException | BadPaddingException ex) {
                            new CustomDialog(rsaView, "Đã xảy ra lỗi: " + ex.getMessage(), "Lỗi xử lý AES");
                            ex.printStackTrace(); // Ghi ra console để debug (tùy chọn)
                        } catch (Exception ex) {
                            throw new RuntimeException(ex);
                        }
                        if(!cipher.isEmpty()){
                            outputTextArea.setText(cipher);
                            CustomDialog dialog = new CustomDialog(rsaView, "Mã hóa thành công !!", "ecrypt success! ");
                        }else {
                            new CustomDialog(rsaView, "Đã xảy ra lỗi: " , "Lỗi xử lý AES");
                        }

                    } else {
                        CustomDialog dialog = new CustomDialog(rsaView, "Key lỗi, key cần có độ dài" + rsa.getLengthKey() + " kí tự hoặc thay đổi độ dài kay", "Lỗi nhập key ");
                    }
                    System.out.println("mã hóa");
                } else {
                    CustomDialog dialog = new CustomDialog(rsaView, "Key rỗng, Bạn cần tạo/chọn Key trước", "Lỗi nhập inout ");
                }
            } else {
                //th file
                String inputPath = inputFilePath;
                System.out.println("in"+inputPath);
                if ( inputPath == null|| inputPath.isEmpty() ) {
                    CustomDialog dialog = new CustomDialog(rsaView, "Bạn cần nhập input trước", "Lỗi nhập inout ");
                } else {
                    if (rsa.isKeyExist()) {
                        if (rsa.checkKey()) {

                            try {
                                //xử lí để ra tên của file
                                String[] path = extractFilePath(inputPath);
                                String outpath = path[0] +"_encrypted." +path[1];
                                rsa.encryptFile(inputPath, outpath);
                                outputFilePath = outpath;// lưu trữ tạm lại file output
                            } catch (NoSuchPaddingException | NoSuchAlgorithmException | InvalidKeyException |
                                     IllegalBlockSizeException | BadPaddingException ex) {
                                new CustomDialog(rsaView, "Đã xảy ra lỗi: " + ex.getMessage(), "Lỗi xử lý AES");
                                ex.printStackTrace(); // Ghi ra console để debug (tùy chọn)
                            } catch (Exception ex) {
                                throw new RuntimeException(ex);
                            }
                            CustomDialog dialog = new CustomDialog(rsaView, "Mã hóa thành công !!", "ecrypt success! ");

                        } else {
                            CustomDialog dialog = new CustomDialog(rsaView, "Key lỗi, key cần có độ dài" + rsa.getLengthKey() + " kí tự hoặc thay đổi độ dài kay", "Lỗi nhập key ");
                        }
                        System.out.println("mã hóa");
                    }
                }
            }
        });
        decryptBut.addActionListener(e ->{
            String input = inputTextArea.getText();
            if (!input.trim().isEmpty()) {
                if (rsa.isKeyExist()) {
                    if (rsa.checkKey()) {
                        String cipher = null;
                        try {
                            cipher = rsa.decryptBase64(input);
                        } catch (NoSuchPaddingException | NoSuchAlgorithmException | InvalidKeyException |
                                 IllegalBlockSizeException | BadPaddingException ex) {
                            new CustomDialog(rsaView, "Đã xảy ra lỗi: " + ex.getMessage(), "Lỗi xử lý AES");
                            ex.printStackTrace(); // Ghi ra console để debug (tùy chọn)
                        } catch (Exception ex) {
                            throw new RuntimeException(ex);
                        }
                        if(!cipher.isEmpty()){
                            outputTextArea.setText(cipher);
                            CustomDialog dialog = new CustomDialog(rsaView, "Mã hóa thành công !!", "ecrypt success! ");
                        }else {
                            new CustomDialog(rsaView, "Đã xảy ra lỗi: " , "Lỗi xử lý AES");
                        }

                    } else {
                        CustomDialog dialog = new CustomDialog(rsaView, "Key lỗi, key cần có độ dài" + rsa.getLengthKey() + " kí tự hoặc thay đổi độ dài kay", "Lỗi nhập key ");
                    }
                    System.out.println("mã hóa");
                } else {
                    CustomDialog dialog = new CustomDialog(rsaView, "Key rỗng, Bạn cần tạo/chọn Key trước", "Lỗi nhập inout ");
                }
            } else {
                //th file
                String inputPath = inputFilePath;
                System.out.println("in"+inputPath);
                if ( inputPath == null || inputPath.isEmpty()) {
                    CustomDialog dialog = new CustomDialog(rsaView, "Bạn cần nhập input trước", "Lỗi nhập inout ");
                } else {
                    if (rsa.isKeyExist()) {
                        if (rsa.checkKey()) {

                            try {
                                //xử lí để ra tên của file
                                String[] path = extractFilePath(inputPath);
                                path[0] = path[0].substring(0,path[0].indexOf("_"));
                                String outpath = path[0] +"_descrypted." +path[1];
                                rsa.decryptFile(inputPath, outpath);
                                outputFilePath = outpath;
                            } catch (NoSuchPaddingException | NoSuchAlgorithmException | InvalidKeyException |
                                     IllegalBlockSizeException | BadPaddingException ex) {
                                new CustomDialog(rsaView, "Đã xảy ra lỗi: " + ex.getMessage(), "Lỗi xử lý AES");
                                ex.printStackTrace(); // Ghi ra console để debug (tùy chọn)
                            } catch (Exception ex) {
                                throw new RuntimeException(ex);
                            }
                            CustomDialog dialog = new CustomDialog(rsaView, "Giải mã thành công !!", "decrypt success! ");

                        } else {
                            CustomDialog dialog = new CustomDialog(rsaView, "Key lỗi, key cần có độ dài" + rsa.getLengthKey() + " kí tự hoặc thay đổi độ dài kay", "Lỗi nhập key ");
                        }
                        System.out.println("giải mã");
                    }
                }
            }
        });
        chooseKeyLengthBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                // Kiểm tra nếu trạng thái là SELECTED
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    String selectedItem = (String) e.getItem();
                    rsa.loadKeyLength(Integer.parseInt(selectedItem));
                    System.out.println("keylen được chọn :" + selectedItem);
                }
            }
        });
        modePaddingBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                //clear data
                chooseKeyLengthBox.setEnabled(true);
                rsa.setKeyExist(false);
                rsa.setKey(null);
                // Kiểm tra nếu trạng thái là SELECTED
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    String selectedItem = (String) e.getItem();
                    rsa.loadPaddingMode(selectedItem);
                    System.out.println("mode được chọn :" + selectedItem);
                }
            }
        });
        //các nút liên quan đến file
        uploadPKeyBut.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(e.getSource() == uploadPKeyBut){
                    JFileChooser fileChooser = new JFileChooser();
//                    fileChooser.showOpenDialog(null);
                    int res = fileChooser.showOpenDialog(null);
                    if(res == JFileChooser.FILES_ONLY){
                        SecretKey key = null;
                        try {
                            String filepath = fileChooser.getSelectedFile().getAbsolutePath();
                            System.out.println(filepath);

                            if(isTextFile(filepath)){
                                String base64Key = new String(Files.readAllBytes(Paths.get(filepath))).trim();
                                if (filepath.length() > 20) {
                                    filepath = filepath.substring(0, 20) + "...";
                                }

                                uploadPKeyL.setText(filepath);
                                keyPArea.setText(base64Key);
                                keyPArea.setEditable(false);
                            }else {
                               CustomDialog diag =new  CustomDialog(rsaView, "Hiện tại chúng tôi mới chỉ hỗ trợ file key có định dạng *txt .Mong bân thông cảm","Lỗi định dạng file",500,200);
                            }


                        } catch (IOException ex) {
                            new CustomDialog(rsaView, "Đã xảy ra lỗi: " + ex.getMessage(), "Lỗi đọc file");
                        }

                    }
                }
            }
        });

        uploadInputFile.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent en) {
                if(en.getSource() == uploadInputFile){
                    JFileChooser fileChooser1 = new JFileChooser();
//                    fileChooser.showOpenDialog(null);
                    int res = fileChooser1.showOpenDialog(null);
                    if(res == JFileChooser.FILES_ONLY){
                        SecretKey key = null;

                            String filepath = fileChooser1.getSelectedFile().getAbsolutePath();
                            System.out.println(filepath);
                            inputFilePath = filepath;
                            System.out.println("he"+inputFilePath);
                            if (filepath.length() > 20) {
                                filepath = filepath.substring(0, 20) + "...";
                            }

                            inputFName.setText(filepath);
//                            keyArea.setText(base64Key);
                            keyPrArea.setEditable(false);
                    }
                }
            }
        });
        // Khi nhấn nút Save Key
        saveKPBut.addActionListener(e -> {
            try {
                // Lấy khóa từ JTextArea
                String keyText = keyPrArea.getText().trim();
                if (keyText.isEmpty()) {
                    new CustomDialog(rsaView,"Key trống. Muốn lưu key trước tiên cần nhập vào","Lỗi lưu key");
                    return;
                }

                // Mở JFileChooser để chọn tệp lưu khóa
                JFileChooser fileChooser = new JFileChooser();
                int returnValue = fileChooser.showSaveDialog(rsaView);

                // Nếu người dùng chọn tệp
                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fileChooser.getSelectedFile();
                    // Lưu khóa vào tệp đã chọn
                    rsa.saveKeyFile(keyText,selectedFile.getAbsolutePath());

                    // Hiển thị thông báo thành công
                    JOptionPane.showMessageDialog(rsaView, "Key saved to " + selectedFile.getAbsolutePath(), "Success", JOptionPane.INFORMATION_MESSAGE);
                }
                // Hiển thị thông báo thành công
                new CustomDialog(rsaView,"lưu key thành công","lưu key");
            } catch (IOException ex) {
                new CustomDialog(rsaView,"Kiểm tra dung lượng ổ hoặc tên file","lưu key thất bại");
            }
        });
        // Lắng nghe sự kiện bấm nút
        downloadBut.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if(outputFilePath ==null||outputFilePath.isEmpty()){
                    new CustomDialog(rsaView,"Chưa có file output","Lổi xem file");
                }else {
                    openExplorer(outputFilePath);
                }
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
    //chọn độ dài key
    public void setDefaultValue(JComboBox keybox, JComboBox mode){
        keybox.setSelectedItem("128");
        mode.setSelectedItem("CBC/PKCS5Padding");
        this.rsa.loadDefaultValue(128,"CBC/PKCS5Padding");
    }
    public static boolean isTextFile(String filePath) {
        // Lấy phần mở rộng của file
        String extension = filePath.substring(filePath.lastIndexOf(".") + 1).toLowerCase();

        // Kiểm tra xem phần mở rộng có phải là một trong các loại file văn bản hay không
        return extension.equals("txt");
    }
    @Override
    public Map<String, Object> saveData() {
        return null;
    }

    @Override
    public void loadData() {

    }
    public boolean updateKeylen(int keysize){
        System.out.println(keysize);
        this.chooseKeyLengthBox.setSelectedItem(String.valueOf(keysize));

        // Vô hiệu hóa JComboBox
        this.chooseKeyLengthBox.setEnabled(false);
        return true;
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
    }
