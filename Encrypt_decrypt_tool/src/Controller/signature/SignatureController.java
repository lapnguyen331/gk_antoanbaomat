package Controller.signature;

import Controller.traTab.CardController;
import UI.CustomDialog;
import UI.sig.SignatureView;
import model.sig.SignatureModel;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Map;

public class SignatureController extends CardController {
    SignatureModel sig;
    SignatureView signatureView;
    public JButton uploadInputFile;
    public JPanel subHeadPane;

    public JToggleButton choosePKey;
    public JButton createKey;

    public JTextArea keyPArea;

    public JPanel importKeyPane;
    public JPanel leftPane;
    public JPanel keyPane;
    public JButton createSigBut;
    public  JButton validateSigBut;
    public JPanel inPane;
    public JPanel outPane;
    public JTextArea inputTextArea;
    public JTextArea outputTextArea;
    public  JPanel rightPane;
    public JButton downloadBut;
    public JLabel fileKeyName;
    public JLabel inputFName;
    JToggleButton choosePrKey;
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


    public SignatureController() {
        super();
        this.sig = new SignatureModel();
        this.signatureView = new SignatureView();
        super.setView(this.signatureView);

        this.uploadInputFile= signatureView.uploadInputFile;
        this.subHeadPane= signatureView.subHeadPane;
        this.choosePKey= signatureView.choosePKey;
        this.createKey= signatureView.createKey;

        this.keyPArea= signatureView.keyPArea;

        this.importKeyPane= signatureView.importKeyPane;
        this.leftPane= signatureView.leftPane;
        this.keyPane= signatureView.keyPane;
        this.createSigBut = signatureView.createSigBut;
        this.validateSigBut= signatureView.validateSigBut;
        this.inPane= signatureView.inPane;
        this.outPane= signatureView.outPane;
        this.inputTextArea= signatureView.inputTextArea;
        this.outputTextArea= signatureView.outputTextArea;
        this.rightPane= signatureView.rightPane;
        this.downloadBut= signatureView.downloadBut;
        this.fileKeyName= signatureView.fileKeyName;
        this.inputFName= signatureView.inputFName;
        this.choosePrKey= signatureView.choosePrKey;
        this.keyPrArea= signatureView.keyPrArea;
        this.saveKPBut= signatureView.saveKPBut;
        this.saveKPrBut= signatureView.saveKPrBut;
        this.keyPL= signatureView.keyPL;
        this.uploadPKeyBut= signatureView.uploadPKeyBut;
        this.uploadPrKeyBut= signatureView.uploadPrKeyBut;
        this.uploadPrKeyL= signatureView.uploadPrKeyL;
        this.uploadPKeyL= signatureView.uploadPKeyL;

        //set giá trị mặc định cho mode padding keylength

        createKey.addActionListener(e -> {
            if(!sig.isKeyPairExist()){
                try {
                    this.sig.generateRSAKeyPair();

                    //set giá trị key vào field
                    keyPArea.setText(sig.getTempPublicKeyBase64());
                    keyPArea.setEditable(false);
                    //set giá trị key vào field
                    keyPrArea.setText(sig.getTempPrivateKeyBase64());
                    keyPrArea.setEditable(false);
                    //khóa không cho import file
                    uploadPrKeyBut.setEnabled(false);
                    uploadPKeyBut.setEnabled(false);

                    System.out.println("createKey");

                } catch (NoSuchAlgorithmException ex) {
                    throw new RuntimeException(ex);
                }

            }else {
                CustomDialog dialog = new CustomDialog(signatureView, "Key đã có, bạn có thể mã hóa /giải mã ", "Lỗi nhập key ");
            }
        });
//        cho phép chọn key
        choosePrKey.addChangeListener(new ChangeListener(){
            @Override
            public void stateChanged(ChangeEvent e) {
                if (choosePrKey.isSelected()) {
                    String prkey = keyPrArea.getText();
                    if(prkey == null || prkey.isEmpty()){
                        new CustomDialog(signatureView,"Key trồng vui lòng nhập key","lỗi chọn key");
                    }else {
                        try {
                            sig.loadPrivateKey(sig.base64ToPrivateKey(prkey));
                            System.out.println("pri"+sig.getPrivateKeyBase64());

                        } catch (Exception ex) {
                            new CustomDialog(signatureView, "không chọn được key này","Lỗi chõn key");
                        }
                    }

                } else {
                    sig.resetPrivateKey();
                }
            }
        });
        choosePKey.addChangeListener(new ChangeListener(){
            @Override
            public void stateChanged(ChangeEvent e) {
                if (choosePKey.isSelected()) {
                    String pkey = keyPArea.getText();
                    if(pkey == null ||pkey.isEmpty()){
                        new CustomDialog(signatureView,"Key trồng vui lòng nhập key","lỗi chọn key");

                    }else {
                        try {
                            sig.loadPublicKey(sig.base64ToPublicKey(pkey));
                            System.out.println("pu" +sig.getPublicKeyBase64());

                        } catch (Exception ex) {
                            new CustomDialog(signatureView, "không chọn được key này","Lỗi chọn key");
                        }
                    }
                } else {
                    sig.resetPublicKey();
                }
            }
        });

        //NOTE: thiếu private
        createSigBut.addActionListener(e -> {
                //th file
                String inputPath = inputFilePath;
                System.out.println("in"+inputPath);
                if ( inputPath == null|| inputPath.isEmpty() ) {
                    CustomDialog dialog = new CustomDialog(signatureView, "Bạn cần nhập input trước", "Lỗi nhập inout ");
                } else {
                    if (sig.isKeyPExist()) {
                        if (sig.checkPKey()) {
                            try{
                                //xử lí để ra tên của file
                                String[] path = extractFilePath(inputPath);
                                String outpath = path[0] +"_signature.sig";
                                byte[] fileData = sig.readFile(inputPath);
                                String digitalSignature = sig.signFileBase64(fileData);
                                outputTextArea.setText( digitalSignature);
                                sig.saveSignature(outpath,digitalSignature);
                                outputFilePath = outpath;// lưu trữ tạm lại file output
                            } catch (Exception ex) {
                                throw new RuntimeException(ex);
                            }
                            CustomDialog dialog = new CustomDialog(signatureView, "Kí file thành công !!", "sign success! ");
                        }
                        System.out.println("kí");
                    }

            }
        });
        validateSigBut.addActionListener(e ->{
            String input = inputTextArea.getText().trim();
            if (!input.trim().isEmpty()) {
                if (sig.isKeyPrExist()) {
                    String cipher = null;
                    try {
                        //cần đọc file đã kí
                        String inputPath = inputFilePath;
                        System.out.println("file xacs minh "+ inputPath);
                        if (inputPath == null || inputPath.isEmpty()) {
                            CustomDialog dialog = new CustomDialog(signatureView, "Bạn cần nhập file đã kí trước", "Lỗi nhập inout ");
                        } else {
                            byte[] fiData = sig.readFile(inputPath);
                            String dig = input;
                            boolean isVerified = sig.verifyFileWithBase64(fiData,dig);
                            if (isVerified) {
                                new CustomDialog(signatureView, "chữ kí hợp lệ", "xác minh chữ kí");
                            } else {
                                new CustomDialog(signatureView, "chữ kí không hợp lệ", "xác minh chữ kí");
                            }
                        }

                    } catch (NoSuchPaddingException | NoSuchAlgorithmException | InvalidKeyException |
                             IllegalBlockSizeException | BadPaddingException ex) {
                        new CustomDialog(signatureView, "Đã xảy ra lỗi: " + ex.getMessage(), "Lỗi xử lý sig");
                        ex.printStackTrace(); // Ghi ra console để debug (tùy chọn)
                    } catch (Exception ex) {
                        throw new RuntimeException(ex);
                    }
                    System.out.println("giải mã");
                } else {
                    CustomDialog dialog = new CustomDialog(signatureView, "Key rỗng, Bạn cần tạo /chọn trước", "Lỗi nhập inout ");

                }
            }else {
                CustomDialog dialog = new CustomDialog(signatureView, "Chữ kí rỗng, Bạn cần nhập chữ kí trước", "Lỗi nhập inout ");

            }
//            } else {
//                //th file
//                String inputPath = inputFilePath;
//                System.out.println("in"+inputPath);
//                if ( inputPath == null || inputPath.isEmpty()) {
//                    CustomDialog dialog = new CustomDialog(signatureView, "Bạn cần nhập input trước", "Lỗi nhập inout ");
//                } else {
//                    if (sig.isKeyPrExist()) {
//                        if (sig.checkPrKey()) {
//
//                            try {
//                                //xử lí để ra tên của file
//                                String[] path = extractFilePath(inputPath);
//                                path[0] = path[0].substring(0,path[0].indexOf("_"));
//                                String outpath = path[0] +"_descrypted." +path[1];
//                                sig.decryptFile(inputPath, outpath);
//                                outputFilePath = outpath;
//                            } catch (NoSuchPaddingException | NoSuchAlgorithmException | InvalidKeyException |
//                                     IllegalBlockSizeException | BadPaddingException ex) {
//                                new CustomDialog(signatureView, "Đã xảy ra lỗi: " + ex.getMessage(), "Lỗi xử lý AES");
//                                ex.printStackTrace(); // Ghi ra console để debug (tùy chọn)
//                            } catch (Exception ex) {
//                                throw new RuntimeException(ex);
//                            }
//                            CustomDialog dialog = new CustomDialog(signatureView, "Giải mã thành công !!", "decrypt success! ");
//
//                        } else {
//                            CustomDialog dialog = new CustomDialog(signatureView, "Key lỗi, key cần có độ dài" + sig.getLengthKey() + " kí tự hoặc thay đổi độ dài kay", "Lỗi nhập key ");
//                        }
//                        System.out.println("giải mã");
//                    }
//                }

        });
//        chooseKeyLengthBox.addItemListener(new ItemListener() {
//            @Override
//            public void itemStateChanged(ItemEvent e) {
//                // Kiểm tra nếu trạng thái là SELECTED
//                if (e.getStateChange() == ItemEvent.SELECTED) {
//                    String selectedItem = (String) e.getItem();
//                    sig.loadKeyLength(Integer.parseInt(selectedItem));
//                    System.out.println("keylen được chọn :" + selectedItem);
//                }
//            }
//        });
//        modePaddingBox.addItemListener(new ItemListener() {
//            @Override
//            public void itemStateChanged(ItemEvent e) {
//                //clear data
//                chooseKeyLengthBox.setEnabled(true);
////                sig.setKeyExist(false);
////                sig.setKey(null);
//                // Kiểm tra nếu trạng thái là SELECTED
//                if (e.getStateChange() == ItemEvent.SELECTED) {
//                    String selectedItem = (String) e.getItem();
//                    sig.loadPaddingMode(selectedItem);
//                    System.out.println("mode được chọn :" + selectedItem);
//                }
//            }
//        });
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

//                            if(isTextFile(filepath)){
                                String base64Key = new String(Files.readAllBytes(Paths.get(filepath))).trim();
                                if (filepath.length() > 20) {
                                    filepath = filepath.substring(0, 20) + "...";
                                }

                                uploadPKeyL.setText(filepath);
                                keyPArea.setText(base64Key);
                                keyPArea.setEditable(false);
//                            }else {
//                               CustomDialog diag =new  CustomDialog(signatureView, "Hiện tại chúng tôi mới chỉ hỗ trợ file key có định dạng *txt .Mong bân thông cảm","Lỗi định dạng file",500,200);
//                            }


                        } catch (IOException ex) {
                            new CustomDialog(signatureView, "Đã xảy ra lỗi: " + ex.getMessage(), "Lỗi đọc file");
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
//        saveKPBut.addActionListener(e -> {
//            try {
//                // Lấy khóa từ JTextArea
//                String keyText = keyPArea.getText().trim();
//                if (keyText.isEmpty()) {
//                    new CustomDialog(signatureView, "Key trống. Vui lòng nhập khóa trước khi lưu.", "Lỗi lưu key");
//                    return;
//                }
//
//                // Mở JFileChooser để chọn tệp lưu khóa
//                JFileChooser fileChooser = new JFileChooser();
//                int returnValue = fileChooser.showSaveDialog(signatureView);
//
//                // Nếu người dùng chọn tệp
//                if (returnValue == JFileChooser.APPROVE_OPTION) {
//                    File selectedFile = fileChooser.getSelectedFile();
//
//                    // Đảm bảo phần mở rộng là .key
//                    if (!selectedFile.getName().endsWith(".key")) {
//                        selectedFile = new File(selectedFile.getAbsolutePath() + "_public.key");
//                    }
//
//                    // Lưu khóa vào tệp đã chọn
//                    sig.saveKeyFile(keyText, selectedFile.getAbsolutePath());
//
//                    // Hiển thị thông báo thành công
//                    new CustomDialog(signatureView, "Key đã được lưu thành công tại: " + selectedFile.getAbsolutePath(), "Lưu key thành công");
//                }
//            } catch (IOException ex) {
//                new CustomDialog(signatureView, "Lỗi khi lưu key. Vui lòng kiểm tra tên tệp hoặc dung lượng ổ đĩa.", "Lưu key thất bại");
//            } catch (Exception ex) {
//                new CustomDialog(signatureView, "Đã xảy ra lỗi không xác định: " + ex.getMessage(), "Lỗi");
//            }
//        });
//        saveKPrBut.addActionListener(e -> {
//            try {
//                // Lấy khóa từ JTextArea
//                String keyText = keyPrArea.getText().trim();
//                if (keyText.isEmpty()) {
//                    new CustomDialog(signatureView, "Key trống. Vui lòng nhập khóa trước khi lưu.", "Lỗi lưu key");
//                    return;
//                }
//
//                // Mở JFileChooser để chọn tệp lưu khóa
//                JFileChooser fileChooser = new JFileChooser();
//                int returnValue = fileChooser.showSaveDialog(signatureView);
//
//                // Nếu người dùng chọn tệp
//                if (returnValue == JFileChooser.APPROVE_OPTION) {
//                    File selectedFile = fileChooser.getSelectedFile();
//
//                    // Đảm bảo phần mở rộng là .key
//                    if (!selectedFile.getName().endsWith(".key")) {
//                        selectedFile = new File(selectedFile.getAbsolutePath() + "_private.key");
//                    }
//
//                    // Lưu khóa vào tệp đã chọn
//                    sig.saveKeyFile(keyText, selectedFile.getAbsolutePath());
//
//                    // Hiển thị thông báo thành công
//                    new CustomDialog(signatureView, "Key đã được lưu thành công tại: " + selectedFile.getAbsolutePath(), "Lưu key thành công");
//                }
//            } catch (IOException ex) {
//                new CustomDialog(signatureView, "Lỗi khi lưu key. Vui lòng kiểm tra tên tệp hoặc dung lượng ổ đĩa.", "Lưu key thất bại");
//            } catch (Exception ex) {
//                new CustomDialog(signatureView, "Đã xảy ra lỗi không xác định: " + ex.getMessage(), "Lỗi");
//            }
//        });

        // Lắng nghe sự kiện bấm nút
        downloadBut.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if(outputFilePath ==null||outputFilePath.isEmpty()){
                    new CustomDialog(signatureView,"Chưa có file output","Lổi xem file");
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
//        keybox.setSelectedItem("2048");
//        mode.setSelectedItem("ECB/PKCS1Padding");
//        this.sig.loadDefaultValue(2048,"ECB/PKCS1Padding");
    }
//    public static boolean isTextFile(String filePath) {
//        // Lấy phần mở rộng của file
//        String extension = filePath.substring(filePath.lastIndexOf(".") + 1).toLowerCase();
//
//        // Kiểm tra xem phần mở rộng có phải là một trong các loại file văn bản hay không
//        return extension.equals("txt");
//    }
    @Override
    public Map<String, Object> saveData() {
        return null;
    }

    @Override
    public void loadData() {

    }
    public boolean updateKeylen(int keysize){
        System.out.println(keysize);

        // Vô hiệu hóa JComboBox
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
