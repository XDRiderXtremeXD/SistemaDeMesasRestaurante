package components;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListCellRenderer;
import javax.swing.border.EmptyBorder;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;
import javax.swing.plaf.basic.BasicComboBoxUI;
import javax.swing.plaf.basic.BasicComboPopup;
import javax.swing.plaf.basic.BasicScrollBarUI;
import javax.swing.plaf.basic.ComboPopup;

import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;

public class CustomComboBox<E> extends JComboBox<E> {

    private static final long serialVersionUID = 1L;

    public CustomComboBox() {
        setUI(new ComboSuggestionUI());
    }
    
    public class ComboSuggestionUI extends BasicComboBoxUI {

        @Override
        public void installUI(JComponent jc) {
            super.installUI(jc);
            Border border = new Border();
            
            if(comboBox.isEditable()){
                JTextField txt = (JTextField) comboBox.getEditor().getEditorComponent();
                txt.addFocusListener(new FocusAdapter() {
                    @Override
                    public void focusGained(FocusEvent fe) {
                        border.setColor(new java.awt.Color(18, 61, 42));
                    }
                    @Override
                    public void focusLost(FocusEvent fe) {
                        border.setColor(new Color(211, 211, 211));
                    }
                });
                txt.setSelectionColor(new Color(54, 189, 248));
                txt.setBorder(new EmptyBorder(0, 10, 0, 4));
            }
            
            comboBox.setBackground(Color.WHITE);
            comboBox.setBorder(border);
            
            comboBox.addPopupMenuListener(new PopupMenuListener() {
                @Override
                public void popupMenuWillBecomeVisible(PopupMenuEvent pme) {
                    arrowButton.setBackground(new Color(180, 180, 180));
                }
                @Override
                public void popupMenuWillBecomeInvisible(PopupMenuEvent pme) {
                    arrowButton.setBackground(new Color(150, 150, 150));
                }
                @Override
                public void popupMenuCanceled(PopupMenuEvent pme) {
                    arrowButton.setBackground(new Color(150, 150, 150));
                }
            });
            AutoCompleteDecorator.decorate(comboBox);
        }
        
        @Override
        protected JButton createArrowButton() {
            return new ArrowButton();
        }
        
        @Override
        protected ComboPopup createPopup() {
            return new ComboSuggestionPopup(comboBox);
        }
        
        @Override
        protected ListCellRenderer<?> createRenderer() {
            return new ListCellRenderer<Object>() {
                @Override
                public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                    String text = value == null ? "" : value.toString();
                    JLabel label = new JLabel(text);
                    label.setFont(comboBox.getFont());
                    if (index >= 0) {
                        label.setBorder(new EmptyBorder(5, 12, 5, 8));
                    } else {
                        label.setBorder(null);
                    }
                    if (isSelected) {
                        label.setOpaque(true);
                        label.setBackground(new Color(240, 240, 240));
                        label.setForeground(new Color(17, 155, 215));
                    }
                    return label;
                }
            };
        }
        
        @Override
        public void paintCurrentValueBackground(Graphics grphcs, Rectangle rctngl, boolean hasFocus) {
        }
        
        private class ComboSuggestionPopup extends BasicComboPopup {

            private static final long serialVersionUID = 1L;

			public ComboSuggestionPopup(JComboBox<?> combo) {
                super(combo);
                setBorder(new Border(1));
            }

            @Override
            protected JScrollPane createScroller() {
                JScrollPane scroll = super.createScroller();
                list.setBackground(Color.WHITE);
                ScrollBarCustom sb = new ScrollBarCustom();
                sb.setPreferredSize(new Dimension(12, 70));
                scroll.setVerticalScrollBar(sb);
                ScrollBarCustom sbH = new ScrollBarCustom();
                sbH.setOrientation(JScrollBar.HORIZONTAL);
                scroll.setHorizontalScrollBar(sbH);
                scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
                return scroll;
            }
        }
        
        private class ArrowButton extends JButton {

            private static final long serialVersionUID = 1L;

			public ArrowButton() {
                setContentAreaFilled(false);
                setBorder(new EmptyBorder(5, 5, 5, 5));
                setBackground(new Color(150, 150, 150));
            }

            @Override
            public void paint(Graphics grphcs) {
                super.paint(grphcs);
                Graphics2D g2 = (Graphics2D) grphcs.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                int width = getWidth();
                int height = getHeight();
                int sizeX = 12;
                int sizeY = 8;
                int x = (width - sizeX) / 2;
                int y = (height - sizeY) / 2;
                int[] px = {x, x + sizeX, x + sizeX / 2};
                int[] py = {y, y, y + sizeY};
                g2.setColor(getBackground());
                g2.fillPolygon(px, py, px.length);
                g2.dispose();
            }
        }
        
        private class Border extends EmptyBorder {

            private static final long serialVersionUID = 1L;
            private Color focusColor = new Color(128, 189, 255);
            private Color color = new Color(206, 212, 218);

            public Border(int border) {
                super(border, border, border, border);
            }

            public Border() {
                this(5);
            }

            public void setColor(Color color) {
                this.color = color;
            }

            @Override
            public void paintBorder(Component cmpnt, Graphics grphcs, int x, int y, int width, int height) {
                Graphics2D g2 = (Graphics2D) grphcs.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                if (cmpnt.isFocusOwner()) {
                    g2.setColor(focusColor);
                } else {
                    g2.setColor(color);
                }
                g2.drawRoundRect(x, y, width - 1, height - 1, 10, 10);
                g2.dispose();
            }
        }
    }
    
    public class ModernScrollBarUI extends BasicScrollBarUI {

        private final int THUMB_SIZE = 60;

        @Override
        protected Dimension getMinimumThumbSize() {
            if (scrollbar.getOrientation() == JScrollBar.VERTICAL) {
                return new Dimension(0, THUMB_SIZE);
            } else {
                return new Dimension(THUMB_SIZE, 0);
            }
        }

        @Override
        protected JButton createIncreaseButton(int i) {
            return new ScrollBarButton();
        }

        @Override
        protected JButton createDecreaseButton(int i) {
            return new ScrollBarButton();
        }

        @Override
        protected void paintTrack(Graphics grphcs, JComponent jc, Rectangle rctngl) {
        }

        @Override
        protected void paintThumb(Graphics grphcs, JComponent jc, Rectangle rctngl) {
            Graphics2D g2 = (Graphics2D) grphcs;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            int x = rctngl.x;
            int y = rctngl.y;
            int width = rctngl.width - 4;
            int height = rctngl.height;
            if (scrollbar.getOrientation() == JScrollBar.VERTICAL) {
                y += 8;
                height -= 16;
            } else {
                x += 8;
                width -= 16;
            }
            g2.setColor(scrollbar.getForeground());
            g2.fillRoundRect(x + 2, y, width, height, 10, 10);
        }

        private class ScrollBarButton extends JButton {

            private static final long serialVersionUID = 1L;

			public ScrollBarButton() {
                setBorder(BorderFactory.createEmptyBorder());
            }

            @Override
            public void paint(Graphics grphcs) {
            }
        }
    }
    
    public class ScrollBarCustom extends JScrollBar {

        private static final long serialVersionUID = 1L;

		public ScrollBarCustom() {
            setUI(new ModernScrollBarUI());
            setPreferredSize(new Dimension(8, 8));
            setForeground(new Color(180, 180, 180));
            setBackground(Color.WHITE);
            setUnitIncrement(20);
        }
    }
}
