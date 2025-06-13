package elements;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;

import org.apache.batik.anim.dom.SAXSVGDocumentFactory;
import org.apache.batik.bridge.BridgeContext;
import org.apache.batik.bridge.GVTBuilder;
import org.apache.batik.bridge.UserAgentAdapter;
import org.apache.batik.gvt.GraphicsNode;
import org.apache.batik.util.XMLResourceDescriptor;
import org.w3c.dom.svg.SVGDocument;

public class MyButton extends JButton {

    private GraphicsNode svgNode;
    private Color baseColor;
    private Color hoverColor;
    private Color clickColor;

    private boolean hovered = false;
    private boolean clicked = false;

    public MyButton(String text, String svgPath, Color baseColor, Color hoverColor, Color clickColor) {
        super(text);
        this.baseColor = baseColor;
        this.hoverColor = hoverColor;
        this.clickColor = clickColor;

        loadSVG(svgPath);

        setFont(new Font("Arial", Font.BOLD, 14));
        setForeground(Color.WHITE);
        setFocusPainted(false);
        setBorderPainted(false);
        setContentAreaFilled(false);
        setCursor(new Cursor(Cursor.HAND_CURSOR));

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                hovered = true;
                repaint();
            }
            @Override
            public void mouseExited(MouseEvent e) {
                hovered = false;
                repaint();
            }
            @Override
            public void mousePressed(MouseEvent e) {
                clicked = true;
                repaint();
            }
            @Override
            public void mouseReleased(MouseEvent e) {
                clicked = false;
                repaint();
            }
        });
    }

    private void loadSVG(String svgPath) {
        try {
            String parser = XMLResourceDescriptor.getXMLParserClassName();
            SAXSVGDocumentFactory factory = new SAXSVGDocumentFactory(parser);
            SVGDocument doc = factory.createSVGDocument(new File(svgPath).toURI().toString());

            GVTBuilder builder = new GVTBuilder();
            BridgeContext ctx = new BridgeContext(new UserAgentAdapter());
            ctx.setDynamicState(BridgeContext.DYNAMIC);

            svgNode = builder.build(ctx, doc);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g.create();

        // Arka plan rengi buton durumu ile değişiyor
        if (clicked) {
            g2d.setColor(clickColor);
        } else if (hovered) {
            g2d.setColor(hoverColor);
        } else {
            g2d.setColor(baseColor);
        }
        g2d.fillRect(0, 0, getWidth(), getHeight());

        int padding = 8; // Buton iç boşluk
        int availableHeight = getHeight() - 2 * padding;
        int availableWidth = getWidth() - 2 * padding;

        // SVG ölçeği (yükseklik veya genişlikten küçük olanı baz al)
        float svgScale = Math.min(availableHeight, availableWidth * 0.3f) / 100f; 
        // 100 burada SVG'nin orijinal varsayılan boyutu, SVG'ye göre ayarla

        if (svgNode != null) {
            // SVG'yi çizmeden önce konumlandırma
            // SVG'yi butonun sol tarafına ortala (dikeyde)
            int svgWidth = (int)(100 * svgScale);
            int svgHeight = (int)(100 * svgScale);
            int svgX = padding;
            int svgY = (getHeight() - svgHeight) / 2;

            g2d.translate(svgX, svgY);
            g2d.scale(svgScale, svgScale);
            svgNode.paint(g2d);
            g2d.scale(1/svgScale, 1/svgScale); // ölçek geri al
            g2d.translate(-svgX, -svgY); // translate geri al
        }

        // Yazı fontunu buton yüksekliğine orantıla (örneğin yüksekliğin %50'si)
        int fontSize = (int)(getHeight() * 0.5);
        g2d.setFont(getFont().deriveFont((float) fontSize));

        // Yazı rengini ayarla
        g2d.setColor(getForeground());

        // Yazıyı svg'nin sağına ortala
        FontMetrics fm = g2d.getFontMetrics();
        int strWidth = fm.stringWidth(getText());
        int strHeight = fm.getAscent();

        int textX = padding + (int)(100 * svgScale) + padding; // svg genişliği + boşluk
        int textY = (getHeight() + strHeight) / 2 - fm.getDescent();

        g2d.drawString(getText(), textX, textY);

        g2d.dispose();
    }

}
