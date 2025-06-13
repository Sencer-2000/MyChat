package activites;

import utilities.AppCompatActivity;
import javax.swing.*;

import activites.signupSteps.FirstStep;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Welcome extends AppCompatActivity {

    private JFrame frame;

    @Override
    public void onCreate() {
        super.onCreate();

        // Pencere oluştur
        frame = new JFrame("Welcome Activity");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300, 200);
        frame.setLayout(new FlowLayout());

        // Signup butonu
        JButton signupButton = new JButton("Signup");
        signupButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println("Signup butonuna basıldı!");
                SignUp signUp = new SignUp();
                signUp.onCreate();
                frame.dispose();
            }
        });

        // Signin butonu
        JButton signinButton = new JButton("Login");
        signinButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println("Signin butonuna basıldı!");
                Login login = new Login();
                login.onCreate();
                frame.dispose();
            }
        });

        // Butonları pencereye ekle
        frame.add(signupButton);
        frame.add(signinButton);

        // Pencereyi göster
        frame.setLocationRelativeTo(null); // Ortala
        frame.setVisible(true);

        System.out.println("WelcomeActivity başlatıldı.");
    }
}
