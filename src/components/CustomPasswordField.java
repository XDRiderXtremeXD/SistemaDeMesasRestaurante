package components;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.RenderingHints;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Area;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.basic.BasicPasswordFieldUI;
import utils.ShadowRenderer;

public class CustomPasswordField extends JPasswordField {

    private static final long serialVersionUID = 1L;

    private String placeholder;
    private JLabel placeholderLabel;
    private JLabel eyeLabel;
    private JLabel spacerLabel;
    
    private boolean showPassword = false;
    
    private int round = 10;
    private Color shadowColor = new Color(170, 170, 170);
    private Color borderColor = new Color(211, 211, 211);
    private BufferedImage imageShadow;
    private final Insets shadowSize = new Insets(2, 5, 8, 5);

    private final ImageIcon eyeIcon = new ImageIcon(getClass().getResource("/imgs/eye.png"));
    private final ImageIcon eyeOffIcon = new ImageIcon(getClass().getResource("/imgs/eye-off.png"));

    public CustomPasswordField() {
        setUI(new TextUI());
        setOpaque(false);
        setForeground(new Color(80, 80, 80));
        setSelectedTextColor(new Color(255, 255, 255));
        setBorder(new EmptyBorder(10, 12, 15, 12));
        setBackground(new Color(255, 255, 255));
        setLayout(null);
        setPreferredSize(new Dimension(350, 40));
        setEchoChar('•');

        placeholderLabel = new JLabel();
        placeholderLabel.setForeground(Color.GRAY);
        placeholderLabel.setFont(new Font("Arial", Font.PLAIN, 13));
        placeholderLabel.setBounds(12, 5, getWidth(), getHeight());
        add(placeholderLabel);

        eyeLabel = new JLabel(eyeIcon);
        eyeLabel.setSize(20, 20);
        eyeLabel.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        eyeLabel.setOpaque(true);
        eyeLabel.setBackground(getBackground());
        add(eyeLabel);

        spacerLabel = new JLabel();
        spacerLabel.setSize(20, 20);
        spacerLabel.setOpaque(true);
        spacerLabel.setBackground(getBackground());
        add(spacerLabel);

        eyeLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                togglePasswordVisibility();
            }
        });

        addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (getPassword().length == 0) {
                    placeholderLabel.setVisible(false);
                }

                shadowColor = new java.awt.Color(18, 61, 42);
                borderColor = new java.awt.Color(18, 61, 42);
                createImageShadow();
                repaint();
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (getPassword().length == 0) {
                    placeholderLabel.setVisible(true);
                }

                shadowColor = new Color(170, 170, 170);
                borderColor = new Color(211, 211, 211);
                createImageShadow();
                repaint();
            }
        });
    }

    private void togglePasswordVisibility() {
        showPassword = !showPassword;
        setEchoChar(showPassword ? '\0' : '•');
        eyeLabel.setIcon(showPassword ? eyeOffIcon : eyeIcon);
        repaint();
    }

    public void setPlaceholder(String placeholder) {
        this.placeholder = placeholder;
        placeholderLabel.setText(placeholder);
        if (getPassword().length == 0) {
            placeholderLabel.setVisible(true);
        }
    }

    @Override
    protected void paintComponent(Graphics grphcs) {
        Graphics2D g2 = (Graphics2D) grphcs.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2.drawImage(imageShadow, 0, 0, null);

        g2.setColor(getBackground());
        Area area = new Area(new RoundRectangle2D.Double(shadowSize.left, shadowSize.top,
                getWidth() - shadowSize.left - shadowSize.right,
                getHeight() - shadowSize.top - shadowSize.bottom, round, round));
        g2.fill(area);

        g2.setColor(borderColor);
        g2.setStroke(new java.awt.BasicStroke(1));
        g2.drawRoundRect(shadowSize.left, shadowSize.top,
                getWidth() - shadowSize.left - shadowSize.right,
                getHeight() - shadowSize.top - shadowSize.bottom, round, round);
        g2.dispose();

        super.paintComponent(grphcs);

        if (getPassword().length == 0 && placeholder != null) {
            Graphics2D g2d = (Graphics2D) grphcs;
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2d.setColor(Color.GRAY);
            int yPosition = 22;
            g2d.drawString(placeholder, getInsets().left, yPosition);
        }
    }

    @Override
    public void setBounds(int x, int y, int width, int height) {
        super.setBounds(x, y, width, height);

        int verticalCenter = (height - 20) / 2 - 3;
        eyeLabel.setBounds(width - 30, verticalCenter, 20, 20);
        spacerLabel.setBounds(width - 50 + 15, verticalCenter, 5, 20);

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
            return new ShadowRenderer(5, 0.3f, shadowColor).createShadow(img); // Usar el color de sombra actualizado
        } else {
            return null;
        }
    }

    private class TextUI extends BasicPasswordFieldUI {
        @Override
        protected void paintBackground(Graphics grphcs) {}
    }
}
