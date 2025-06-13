package activites;

import utilities.AppCompatActivity;
import javax.swing.*;
import utilities.Fragment;
import activites.signupSteps.*;

public class SignUp extends AppCompatActivity {

    private JFrame frame;
    private Fragment fragment;

    @Override
    public void onCreate() {
        super.onCreate();

        frame = new JFrame("Signup");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);

        fragment = new Fragment();

        showFirstStep();

        frame.add(fragment);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private void showFirstStep() {
        FirstStep firstStep = new FirstStep(() -> {
            // Email ve şifre doğrulandı, ikinci adıma geç
            showSecondStep();
        });
        setFragmentContent(firstStep);
    }

    private void showSecondStep() {
        SecondStep secondStep = new SecondStep(() -> {
            // İsim girildi, üçüncü adıma geç
            showThirdStep();
        });
        setFragmentContent(secondStep);
    }

    private void showThirdStep() {
        ThirdStep thirdStep = new ThirdStep();
        setFragmentContent(thirdStep);
    }

    private void setFragmentContent(JPanel panel) {
        fragment.setContent(panel);
        fragment.revalidate();
        fragment.repaint();
    }
}
