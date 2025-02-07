package components;

import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.image.BufferedImage;
import java.awt.geom.Area;
import java.awt.geom.RoundRectangle2D;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.basic.BasicTextFieldUI;
import utils.ShadowRenderer;

public class CustomTextField extends JTextField {

    private static final long serialVersionUID = 1L;
    
    private String placeholder;
    private JLabel placeholderLabel;
    
    private int round = 10;
    private Color shadowColor = new Color(170, 170, 170);
    private Color borderColor = new Color(211, 211, 211);
    private BufferedImage imageShadow;
    private final Insets shadowSize = new Insets(2, 5, 8, 5);

    public CustomTextField() {
        setUI(new TextUI());
        setOpaque(false);
        setForeground(new Color(80, 80, 80));
        setSelectedTextColor(new Color(255, 255, 255));
        setBorder(new EmptyBorder(10, 12, 15, 12));
        setBackground(new Color(255, 255, 255));
        
        placeholderLabel = new JLabel();
        placeholderLabel.setForeground(Color.GRAY);
        placeholderLabel.setFont(new Font("Arial", Font.PLAIN, 13));
        placeholderLabel.setBounds(12, 5, getWidth(), getHeight());
        add(placeholderLabel);
        
        setLayout(null);
        setPreferredSize(new Dimension(350, 40));

        addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (getText().isEmpty()) {
                    placeholderLabel.setVisible(false);
                }

                shadowColor = new java.awt.Color(18, 61, 42);
                borderColor = new java.awt.Color(18, 61, 42);
                createImageShadow();
                repaint();
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (getText().isEmpty()) {
                    placeholderLabel.setVisible(true);
                }

                shadowColor = new Color(170, 170, 170);
                borderColor = new Color(211, 211, 211);
                createImageShadow();
                repaint();
            }
        });
    }

    public void setPlaceholder(String placeholder) {
        this.placeholder = placeholder;
        placeholderLabel.setText(placeholder);
        if (getText().isEmpty()) {
            placeholderLabel.setVisible(true);
        }
    }

    @Override
    protected void paintComponent(Graphics grphcs) {
        Graphics2D g2 = (Graphics2D) grphcs.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        double width = getWidth() - (shadowSize.left + shadowSize.right);
        double height = getHeight() - (shadowSize.top + shadowSize.bottom);
        double x = shadowSize.left;
        double y = shadowSize.top;

        g2.drawImage(imageShadow, 0, 0, null);
        
        g2.setColor(getBackground());
        Area area = new Area(new RoundRectangle2D.Double(x, y, width, height, round, round));
        g2.fill(area);

        g2.setColor(borderColor);
        g2.setStroke(new java.awt.BasicStroke(1));
        g2.drawRoundRect((int) x, (int) y, (int) width, (int) height, round, round);

        g2.dispose();

        if (getText().isEmpty() && placeholder != null) {
            Graphics2D g2d = (Graphics2D) grphcs;
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2d.setColor(Color.GRAY);
            
            int yPosition = 22;
            g2d.drawString(placeholder, getInsets().left, yPosition);
        }

        super.paintComponent(grphcs);
    }

    @Override
    public void setBounds(int x, int y, int width, int height) {
        super.setBounds(x, y, width, height);
        createImageShadow();
    }

    private void createImageShadow() {
        int height = getHeight();
        int width = getWidth();
        if (width > 0 && height > 0) {
            imageShadow = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2 = imageShadow.createGraphics();
            g2.drawImage(createShadow(), 0, 0, null);
            g2.dispose();
        }
    }

    private BufferedImage createShadow() {
        int width = getWidth() - (shadowSize.left + shadowSize.right);
        int height = getHeight() - (shadowSize.top + shadowSize.bottom);
        if (width > 0 && height > 0) {
            BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2 = img.createGraphics();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.fill(new RoundRectangle2D.Double(0, 0, width, height, round, round));
            g2.dispose();
            return new ShadowRenderer(5, 0.3f, shadowColor).createShadow(img);
        } else {
            return null;
        }
    }
    
    public void restorePlaceholder() {
        if (getText().isEmpty()) {
            placeholderLabel.setVisible(true);
        }
    }
    
    private class TextUI extends BasicTextFieldUI {
        @Override
        protected void paintBackground(Graphics grphcs) {}
    }
}
