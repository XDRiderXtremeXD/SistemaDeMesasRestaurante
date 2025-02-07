package components;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.basic.BasicComboBoxUI;
import javax.swing.plaf.basic.BasicComboPopup;
import javax.swing.plaf.basic.ComboPopup;

import java.awt.*;
import java.awt.geom.RoundRectangle2D;

public class CustomComboBox<E> extends JComboBox<E> {

    private static final long serialVersionUID = 1L;

    private int round = 10; // Radio de redondeo
    private Color borderColor = new Color(180, 180, 180); // Borde normal
    private Color focusBorderColor = new Color(100, 100, 100); // Color al enfocar (neutral)
    private Color backgroundColor = Color.WHITE; // Fondo blanco
    private boolean focused = false;

    public CustomComboBox() {
        setUI(new CustomComboBoxUI()); 
        setOpaque(false);
        setBackground(backgroundColor);
        setBorder(new EmptyBorder(5, 10, 5, 10)); 
        setPreferredSize(new Dimension(200, 40));

        addFocusListener(new java.awt.event.FocusAdapter() {
            @Override
            public void focusGained(java.awt.event.FocusEvent evt) {
                focused = true;
                repaint();
            }

            @Override
            public void focusLost(java.awt.event.FocusEvent evt) {
                focused = false;
                repaint();
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2.setColor(backgroundColor);
        g2.fill(new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), round, round));

        super.paintComponent(g);
        g2.dispose();
    }

    @Override
    protected void paintBorder(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2.setColor(focused ? focusBorderColor : borderColor); // Color del borde neutro
        g2.setStroke(new BasicStroke(1.5f));
        g2.draw(new RoundRectangle2D.Double(0, 0, getWidth() - 1, getHeight() - 1, round, round));

        g2.dispose();
    }

    private class CustomComboBoxUI extends BasicComboBoxUI {
        @Override
        protected JButton createArrowButton() {
            JButton button = new JButton();
            button.setBorder(new EmptyBorder(5, 5, 5, 5));
            button.setContentAreaFilled(false);
            button.setFocusPainted(false);
            button.setOpaque(false);
            button.setIcon(new ArrowIcon());
            return button;
        }

        @Override
        protected ComboPopup createPopup() {
            BasicComboPopup popup = new BasicComboPopup(comboBox) {
                @Override
                protected void paintComponent(Graphics g) {
                    Graphics2D g2 = (Graphics2D) g.create();
                    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                    g2.setColor(new Color(255, 255, 255, 220)); // Fondo blanco con transparencia
                    g2.fillRect(0, 0, getWidth(), getHeight());

                    g2.dispose();
                    super.paintComponent(g);
                }
            };
            popup.setBorder(BorderFactory.createLineBorder(borderColor, 1));
            return popup;
        }
    }

    private class ArrowIcon implements Icon {
        @Override
        public void paintIcon(Component c, Graphics g, int x, int y) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(new Color(100, 100, 100));

            int[] xPoints = { 4, 12, 20 };
            int[] yPoints = { 10, 18, 10 };
            g2.fillPolygon(xPoints, yPoints, 3);

            g2.dispose();
        }

        @Override
        public int getIconWidth() {
            return 24;
        }

        @Override
        public int getIconHeight() {
            return 24;
        }
    }
}
