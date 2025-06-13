package activites.signupSteps;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Base64;
import javax.imageio.ImageIO;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.WriteResult;
import com.google.firebase.cloud.FirestoreClient;

import java.util.HashMap;
import java.util.Map;

import utilities.Constants;

public class ThirdStep extends JPanel {
    private JLabel imageLabel;
    private String base64Image; // Seçilen resmin Base64 hali burada tutulabilir

    public ThirdStep() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        imageLabel = new JLabel("Resim seçilmedi", SwingConstants.CENTER);
        imageLabel.setPreferredSize(new Dimension(300, 200));
        imageLabel.setBorder(BorderFactory.createLineBorder(Color.GRAY));

        JButton selectButton = new JButton("Select");
        selectButton.addActionListener(e -> selectImage());

        JButton doneButton = new JButton("Done");
        doneButton.addActionListener(e -> {
            if (base64Image == null) {
                JOptionPane.showMessageDialog(this, "Lütfen önce bir resim seçin!", "Uyarı", JOptionPane.WARNING_MESSAGE);
                return;
            }

            // Örnek email ve password, bunları signup akışından alman lazım
            String email = "user@example.com";
            String password = "password123";

            saveUserToFirestore(email, password, base64Image);
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(1, 2, 10, 0)); // İki buton yan yana boşluklu
        buttonPanel.add(selectButton);
        buttonPanel.add(doneButton);

        add(imageLabel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void selectImage() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Fotoğraf seçiniz");
        fileChooser.setAcceptAllFileFilterUsed(false);
        fileChooser.addChoosableFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("Image files", "jpg", "jpeg", "png", "gif"));

        int result = fileChooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            try {
                BufferedImage img = ImageIO.read(selectedFile);
                Image scaledImg = img.getScaledInstance(imageLabel.getWidth(), imageLabel.getHeight(), Image.SCALE_SMOOTH);
                imageLabel.setIcon(new ImageIcon(scaledImg));
                imageLabel.setText(null);

                base64Image = encodeFileToBase64(selectedFile);
                System.out.println("Base64 Encoded Image:");
                System.out.println(base64Image);

            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, "Resim yüklenirken hata oluştu!", "Hata", JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace();
            }
        }
    }

    private String encodeFileToBase64(File file) throws IOException {
        try (FileInputStream fileInputStreamReader = new FileInputStream(file)) {
            byte[] bytes = new byte[(int) file.length()];
            fileInputStreamReader.read(bytes);
            return Base64.getEncoder().encodeToString(bytes);
        }
    }

    private void saveUserToFirestore(String email, String password, String encodedImage) {
        try {
            Firestore db = FirestoreClient.getFirestore();

            Map<String, Object> user = new HashMap<>();
            user.put("email", email);
            user.put("password", password);
            user.put("encodedImage", encodedImage);

            // Belgeyi otomatik id ile oluştur
            ApiFuture<DocumentReference> addedDocRef = db.collection(Constants.KEY_COLLECTION_USERS).add(user);

            // İşlemin sonucunu beklemek istersen:
            DocumentReference docRef = addedDocRef.get();
            System.out.println("Kullanıcı Firestore'a kaydedildi, ID: " + docRef.getId());

            JOptionPane.showMessageDialog(this, "Kullanıcı başarıyla kaydedildi!");
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Firestore kaydı başarısız oldu!", "Hata", JOptionPane.ERROR_MESSAGE);
        }
    }
}
