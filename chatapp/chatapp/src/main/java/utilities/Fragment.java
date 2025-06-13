package utilities;

import javax.swing.*;
import java.awt.*;

public class Fragment extends JPanel {

    public Fragment() {
        setLayout(new BorderLayout()); // İçerik rahat yerleşsin diye
    }

    // Fragment içine dinamik olarak başka bir JPanel (veya Component) ekle
    public void setContent(Component content) {
        this.removeAll();        // Önce var olanı temizle
        this.add(content, BorderLayout.CENTER);  // Yeni içeriği ekle
        this.revalidate();       // Layout güncelle
        this.repaint();          // Görünümü yenile
    }

    // Lifecycle metodları isteğe bağlı
    public void onCreate() { }
    public void onResume() { }
    public void onPause() { }
    public void onDestroy() { }
}