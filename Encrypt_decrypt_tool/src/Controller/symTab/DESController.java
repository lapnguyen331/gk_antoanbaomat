package Controller.symTab;

import Controller.traTab.CardController;
import UI.CustomDialog;
import UI.sym.AESView;
import UI.sym.DESView;
import model.symAlgo.AES;
import model.symAlgo.DES;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.swing.*;
import java.awt.*;
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

public class DESController extends CardController {
    DES des;
    DESView desView;
    JButton uploadKeyFile;
    JButton cancelUpload;
    JComboBox<String> modeBox;
    JComboBox<String> paddingBox;
    JPanel subHeadPane;
    String[] keyLens;
    JButton chooseKey;
    JButton createKey;
    JButton saveBut;
    JTextArea keyArea;
    JPanel importKeyPane;
    JPanel leftPane;
    JPanel keyPane;
    JButton encryptBut;
    JButton decryptBut;
    JPanel inPane;
    JPanel outPane;
    JTextArea inputTextArea;
    JTextArea outputTextArea;
    JPanel rightPane;
    JComboBox<String>chooseKeyLengthBox;
    JButton downloadBut;
    JLabel fileKeyName;
    JButton uploadInputFile;
    JLabel inputFName;
    public String inputFilePath;
    String outputFilePath;

    public DESController() {
        super();
        this.des = new DES();
        this.desView = new DESView();
        super.setView(this.desView);

        this.inputFName = this.desView.inputFName;
        this.inputFilePath = null;
        this.uploadInputFile = this.desView.uploadInputFile;
        this.fileKeyName= this.desView.fileKeyName;
        this.downloadBut = this.desView.downloadBut;
        this.uploadKeyFile = this.desView.uploadKeyFile;
        this.cancelUpload =this.desView.cancelUpload;
        this.modeBox = this.desView.modePaddingBox;
        this.subHeadPane = this.desView.subHeadPane;
        this.keyLens = this.desView.keyLens;
        this.chooseKeyLengthBox = this.desView.chooseKeyLengthBox;
        this.chooseKey = this.desView.chooseKey;
        this.createKey = this.desView.createKey;
        this.saveBut = this.desView.saveBut;
        this.keyArea = this.desView.keyArea;
        this.importKeyPane = this.desView.importKeyPane;
        this.leftPane = this.desView.leftPane;
        this.keyPane = this.desView.keyPane;
        this.encryptBut = this.desView.encryptBut;
        this.decryptBut = this.desView.decryptBut;
        this.inPane = this.desView.inPane;
        this.outPane = this.desView.outPane;
        this.inputTextArea = this.desView.inputTextArea;
        this.outputTextArea = this.desView.outputTextArea;
        this.rightPane = this.desView.rightPane;


        //set giá trị mặc định cho mode padding keylength
        setDefaultValue(this.chooseKeyLengthBox,this.modeBox,this.paddingBox);

        createKey.addActionListener(e -> {
            if(!des.isKeyExist()){
                try {
                    SecretKey key = this.des.genKey();
                    //set giá trị key vào field
                    keyArea.setText(des.secretKeyToBase64(key));
                    keyArea.setEditable(false);
//                    this.chooseKeyBut.setVisible(true);
                    System.out.println("createKey");

                } catch (NoSuchAlgorithmException ex) {
                    throw new RuntimeException(ex);
                }

            }else {
                CustomDialog dialog = new CustomDialog(desView, "Key đã có, bạn có thể mã hóa /giải mã ", "Lỗi nhập key ");
            }
        });
        //cho phép chọn key
        chooseKey.addActionListener(e ->{
            // nếu đã tồn tại thì ko cần
            if(des.isKeyExist()){

            }else {
                String keyString = keyArea.getText().trim();
                System.out.println("hehe+ "+keyString);
                if(des.checkInputKey(keyString)){
                    if(updateKeylen(des.getInputKeySize(keyString))){
                        this.des.loadKey(des.base64ToSecretKey(keyString));
                        System.out.println("chọn key --" +keyString);
                    }
                }else {
                    System.out.println("lỗi chọn key");
                    CustomDialog dialog = new CustomDialog(desView, "Key lỗi, key cần có độ dài" + des.getLengthKey() +" bits hoặc thay đổi độ dài kay", "Lỗi nhập key ");
                }
            }
        });

        encryptBut.addActionListener(e -> {
            String input = inputTextArea.getText();
            if (!input.trim().isEmpty()) {
                if (des.isKeyExist()) {
                    if (des.checkKey()) {
                        String cipher = null;
                        try {
                            cipher = des.encryptBase64(input);
                        } catch (NoSuchPaddingException | NoSuchAlgorithmException | InvalidKeyException |
                                 IllegalBlockSizeException | BadPaddingException ex) {
                            new CustomDialog(desView, "Đã xảy ra lỗi: " + ex.getMessage(), "Lỗi xử lý AES");
                            ex.printStackTrace(); // Ghi ra console để debug (tùy chọn)
                        } catch (Exception ex) {
                            throw new RuntimeException(ex);
                        }
                        if(!cipher.isEmpty()){
                            outputTextArea.setText(cipher);
                            CustomDialog dialog = new CustomDialog(desView, "Mã hóa thành công !!", "ecrypt success! ");
                        }else {
                            new CustomDialog(desView, "Đã xảy ra lỗi: " , "Lỗi xử lý AES");
                        }
                    } else {
                        System.out.println("key ko 56");
                        CustomDialog dialog = new CustomDialog(desView, "Key lỗi, key cần có độ dài" + des.getLengthKey() + " bit hoặc thay đổi độ dài kay", "Lỗi nhập key ");
                    }
                    System.out.println("mã hóa");
                } else {
                    CustomDialog dialog = new CustomDialog(desView, "Key rỗng, Bạn cần tạo/chọn Key trước", "Lỗi nhập inout ");
                }
            } else {
                //th file
                String inputPath = inputFilePath;
                System.out.println("in"+inputPath);
                if ( inputPath == null|| inputPath.isEmpty() ) {
                    CustomDialog dialog = new CustomDialog(desView, "Bạn cần nhập input trước", "Lỗi nhập inout ");
                } else {
                    if (des.isKeyExist()) {
                        if (des.checkKey()) {

                            try {
                                //xử lí để ra tên của file
                                String[] path = extractFilePath(inputPath);
                                String outpath = path[0] +"_encrypted." +path[1];
                                des.encryptFile(inputPath, outpath);
                                outputFilePath = outpath;
                            } catch (NoSuchPaddingException | NoSuchAlgorithmException | InvalidKeyException |
                                     IllegalBlockSizeException | BadPaddingException ex) {
                                new CustomDialog(desView, "Đã xảy ra lỗi: " + ex.getMessage(), "Lỗi xử lý AES");
                                ex.printStackTrace(); // Ghi ra console để debug (tùy chọn)
                            } catch (Exception ex) {
                                throw new RuntimeException(ex);
                            }
                            CustomDialog dialog = new CustomDialog(desView, "Mã hóa thành công !!", "ecrypt success! ");

                        } else {
                            CustomDialog dialog = new CustomDialog(desView, "Key lỗi, key cần có độ dài" + des.getLengthKey() + " kí tự hoặc thay đổi độ dài kay", "Lỗi nhập key ");
                        }
                        System.out.println("mã hóa");
                    }
                }
            }
        });
        decryptBut.addActionListener(e ->{
            String input = inputTextArea.getText();
            if (!input.trim().isEmpty()) {
                if (des.isKeyExist()) {
                    if (des.checkKey()) {
                        String cipher = null;
                        try {
                            cipher = des.decryptBase64(input);
                        } catch (NoSuchPaddingException | NoSuchAlgorithmException | InvalidKeyException |
                                 IllegalBlockSizeException | BadPaddingException ex) {
                            new CustomDialog(desView, "Đã xảy ra lỗi: " + ex.getMessage(), "Lỗi xử lý AES");
                            ex.printStackTrace(); // Ghi ra console để debug (tùy chọn)
                        } catch (Exception ex) {
                            throw new RuntimeException(ex);
                        }
                        if(!cipher.isEmpty()){
                            outputTextArea.setText(cipher);
                            CustomDialog dialog = new CustomDialog(desView, "Giải mã thành công !!", "decrypt success! ");
                        }else {
                            new CustomDialog(desView, "Đã xảy ra lỗi: " , "Lỗi xử lý AES");
                        }

                    } else {
                        CustomDialog dialog = new CustomDialog(desView, "Key lỗi, key cần có độ dài" + des.getLengthKey() + " kí tự hoặc thay đổi độ dài kay", "Lỗi nhập key ");
                    }
                    System.out.println("giải mã");
                } else {
                    CustomDialog dialog = new CustomDialog(desView, "Key rỗng, Bạn cần tạo/chọn Key trước", "Lỗi nhập inout ");
                }
            } else {
                //th file
                String inputPath = inputFilePath;
                System.out.println("in"+inputPath);
                if ( inputPath == null || inputPath.isEmpty()) {
                    CustomDialog dialog = new CustomDialog(desView, "Bạn cần nhập input trước", "Lỗi nhập inout ");
                } else {
                    if (des.isKeyExist()) {
                        if (des.checkKey()) {

                            try {
                                //xử lí để ra tên của file
                                String[] path = extractFilePath(inputPath);
                                path[0] = path[0].substring(0,path[0].indexOf("_"));
                                String outpath = path[0] +"_descrypted." +path[1];
                                des.decryptFile(inputPath, outpath);
                                outputFilePath= outpath;
                            } catch (NoSuchPaddingException | NoSuchAlgorithmException | InvalidKeyException |
                                     IllegalBlockSizeException | BadPaddingException ex) {
                                new CustomDialog(desView, "Đã xảy ra lỗi: " + ex.getMessage(), "Lỗi xử lý AES");
                                ex.printStackTrace(); // Ghi ra console để debug (tùy chọn)
                            } catch (Exception ex) {
                                throw new RuntimeException(ex);
                            }
                            CustomDialog dialog = new CustomDialog(desView, "Giải mã thành công !!", "decrypt success! ");

                        } else {
                            CustomDialog dialog = new CustomDialog(desView, "Key lỗi, key cần có độ dài" + des.getLengthKey() + " bits hoặc thay đổi độ dài kay", "Lỗi nhập key ");
                        }
                        System.out.println("giải mã");
                    }
                }
            }
        });
//        chooseKeyLengthBox.addItemListener(new ItemListener() {
//            @Override
//            public void itemStateChanged(ItemEvent e) {
//                // Kiểm tra nếu trạng thái là SELECTED
//                if (e.getStateChange() == ItemEvent.SELECTED) {
//                    String selectedItem = (String) e.getItem();
//                    des.loadKeyLength(Integer.parseInt(selectedItem));
//                    System.out.println("keylen được chọn :" + selectedItem);
//                }
//            }
//        });
        modeBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                //clear data
                chooseKeyLengthBox.setEnabled(true);
                des.setKeyExist(false);
                des.setKey(null);
                // Kiểm tra nếu trạng thái là SELECTED
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    String selectedItem = (String) e.getItem();
                    des.loadPaddingMode(selectedItem);
                    System.out.println("mode được chọn :" + selectedItem);
                }
            }
        });
        //các nút liên quan đến file
        uploadKeyFile.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(e.getSource() == uploadKeyFile){
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

                                fileKeyName.setText(filepath);
                                keyArea.setText(base64Key);
                                keyArea.setEditable(false);
                            }else {
                               CustomDialog diag =new  CustomDialog(desView, "Hiện tại chúng tôi mới chỉ hỗ trợ file key có định dạng *txt .Mong bân thông cảm","Lỗi định dạng file",500,200);
                            }


                        } catch (IOException ex) {
                            new CustomDialog(desView, "Đã xảy ra lỗi: " + ex.getMessage(), "Lỗi đọc file");
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
                            keyArea.setEditable(false);
                    }
                }
            }
        });
        // Khi nhấn nút Save Key
        saveBut.addActionListener(e -> {
            try {
                // Lấy khóa từ JTextArea
                String keyText = keyArea.getText().trim();
                if (keyText.isEmpty()) {
                    new CustomDialog(desView,"Key trống. Muốn lưu key trước tiên cần nhập vào","Lỗi lưu key");
                    return;
                }

                // Mở JFileChooser để chọn tệp lưu khóa
                JFileChooser fileChooser = new JFileChooser();
                int returnValue = fileChooser.showSaveDialog(desView);

                // Nếu người dùng chọn tệp
                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fileChooser.getSelectedFile();
                    // Lưu khóa vào tệp đã chọn
                    des.saveKeyFile(keyText,selectedFile.getAbsolutePath());

                    // Hiển thị thông báo thành công
                    JOptionPane.showMessageDialog(desView, "Key saved to " + selectedFile.getAbsolutePath(), "Success", JOptionPane.INFORMATION_MESSAGE);
                }
                // Hiển thị thông báo thành công
                new CustomDialog(desView,"lưu key thành công","lưu key");
            } catch (IOException ex) {
                new CustomDialog(desView,"Kiểm tra dung lượng ổ hoặc tên file","lưu key thất bại");
            }
        });

        downloadBut.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(outputFilePath ==null||outputFilePath.isEmpty()){
                    new CustomDialog(desView,"Chưa có file output","Lổi xem file");
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
    public void setDefaultValue(JComboBox keybox, JComboBox mode,JComboBox padding){
        keybox.setSelectedItem("56");
        mode.setSelectedItem("CBC/PKCS5Padding");
        this.des.loadDefaultValue(56,"CBC/PKCS5Padding");
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
