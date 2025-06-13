package activites.signupSteps;

import javax.swing.*;
import java.awt.*;

public class FirstStep extends JPanel {

    private Runnable onNext;

    // Bu alanları sınıf değişkeni yapıyoruz ki getter ile erişebilelim
    private JTextField emailField;
    private JPasswordField passwordField;
    private JPasswordField passwordConfirmField;

    public FirstStep(Runnable onNext) {
        this.onNext = onNext;

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel emailLabel = new JLabel("Email:");
        emailField = new JTextField();
        emailField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 25));

        JLabel passwordLabel = new JLabel("Şifre:");
        passwordField = new JPasswordField();
        passwordField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 25));

        JLabel passwordConfirmLabel = new JLabel("Şifre (Tekrar):");
        passwordConfirmField = new JPasswordField();
        passwordConfirmField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 25));

        JButton nextButton = new JButton("İlerle");
        nextButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        nextButton.addActionListener(e -> {
            String email = emailField.getText().trim();
            String password = new String(passwordField.getPassword());
            String passwordConfirm = new String(passwordConfirmField.getPassword());

            if (!password.equals(passwordConfirm)) {
                JOptionPane.showMessageDialog(this, "Şifreler uyuşmuyor!", "Hata", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (password.length() < 8) {
                JOptionPane.showMessageDialog(this, "Şifre en az 8 karakter olmalı!", "Hata", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if(email.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Email boş olamaz!", "Hata", JOptionPane.ERROR_MESSAGE);
                return;
            }

            System.out.println("Email: " + email);
            System.out.println("Şifre: " + password);

            if (onNext != null) {
                onNext.run(); // İkinci adıma geç
            }
        });

        add(emailLabel);
        add(emailField);
        add(Box.createRigidArea(new Dimension(0, 10)));
        add(passwordLabel);
        add(passwordField);
        add(Box.createRigidArea(new Dimension(0, 10)));
        add(passwordConfirmLabel);
        add(passwordConfirmField);
        add(Box.createRigidArea(new Dimension(0, 15)));
        add(nextButton);
    }

    // Getter metotları
    public String getEmail() {
        return emailField.getText().trim();
    }

    public String getPassword() {
        return new String(passwordField.getPassword());
    }
}
