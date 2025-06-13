package activites.signupSteps;

import javax.swing.*;
import java.awt.*;

public class SecondStep extends JPanel {

    private JTextField nameField;
    private Runnable onNext;

    public SecondStep(Runnable onNext) {
        this.onNext = onNext;

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel nameLabel = new JLabel("İsim:");
        nameField = new JTextField();
        nameField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 25));

        JButton nextButton = new JButton("İlerle");
        nextButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        nextButton.addActionListener(e -> {
            String name = getNickname();
            if (name.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Lütfen isim giriniz!", "Uyarı", JOptionPane.WARNING_MESSAGE);
                return;
            }
            System.out.println("İsim: " + name);

            if (onNext != null) {
                onNext.run();  // Üçüncü adıma geçiş tetikleniyor
            }
        });

        add(nameLabel);
        add(nameField);
        add(Box.createRigidArea(new Dimension(0, 15)));
        add(nextButton);
    }

    public String getNickname() {
        return nameField.getText().trim();
    }
}
