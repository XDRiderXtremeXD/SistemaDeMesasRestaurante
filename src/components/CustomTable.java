package components;

import java.awt.AWTEvent;
import java.awt.AlphaComposite;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.SystemColor;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.swing.ImageIcon;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JLayer;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.plaf.LayerUI;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;

import org.jdesktop.animation.timing.Animator;
import org.jdesktop.animation.timing.TimingTarget;
import org.jdesktop.animation.timing.TimingTargetAdapter;
import org.jdesktop.animation.timing.interpolation.PropertySetter;

import utils.ScrollBarCustomUI;

public class CustomTable {

    public static class BooleanCellRenderer extends TableCustomCellRender {
        private static final long serialVersionUID = 1L;

        public BooleanCellRenderer(HoverIndex hoverRow) {
            super(hoverRow);
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            Component com = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            JCheckBox ch = new JCheckBox();
            ch.setHorizontalAlignment(SwingConstants.CENTER);
            ch.setOpaque(true);
            ch.setSelected((boolean) value);
            ch.setBackground(com.getBackground());
            return ch;
        }
    }

    public static class HoverIndex {
        private int index = -1;

        public int getIndex() {
            return index;
        }

        public void setIndex(int index) {
            this.index = index;
        }
    }

    public static class TableCustom {
        public static void apply(JScrollPane scroll, TableType type) {
            JTable table = (JTable) scroll.getViewport().getComponent(0);
            table.setSelectionBackground(SystemColor.activeCaption);
            table.getTableHeader().setReorderingAllowed(false);
            table.getTableHeader().setDefaultRenderer(new TableHeaderCustomCellRender(table));
            table.setRowHeight(30);
            HoverIndex hoverRow = new HoverIndex();
            TableCellRenderer cellRender;
            if (type == TableType.DEFAULT) {
                cellRender = new TableCustomCellRender(hoverRow);
            } else {
                cellRender = new TextAreaCellRenderer(hoverRow);
            }
            table.setDefaultRenderer(Object.class, cellRender);
            table.setDefaultRenderer(Boolean.class, new BooleanCellRenderer(hoverRow));
            table.setShowVerticalLines(true);
            table.setGridColor(new Color(220, 220, 220));
            table.setForeground(new Color(51, 51, 51));
            table.setSelectionForeground(new Color(51, 51, 51));
            scroll.setBorder(new LineBorder(new Color(220, 220, 220)));
            JPanel panel = new JPanel() {
                private static final long serialVersionUID = 1L;

                @Override
                public void paint(Graphics grphcs) {
                    super.paint(grphcs);
                    grphcs.setColor(new Color(220, 220, 220));
                    grphcs.drawLine(0, getHeight() - 1, getWidth(), getHeight() - 1);
                    grphcs.dispose();
                }
            };
            panel.setBackground(new Color(250, 250, 250));
            scroll.setCorner(JScrollPane.UPPER_RIGHT_CORNER, panel);
            scroll.getViewport().setBackground(Color.WHITE);
            scroll.getVerticalScrollBar().setUI(new ScrollBarCustomUI());
            scroll.getHorizontalScrollBar().setUI(new ScrollBarCustomUI());
            table.getTableHeader().setBackground(new Color(250, 250, 250));
            table.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseExited(MouseEvent e) {
                    hoverRow.setIndex(-1);
                    table.repaint();
                }
            });
            table.addMouseMotionListener(new MouseMotionAdapter() {
                @Override
                public void mouseMoved(MouseEvent e) {
                    int row = table.rowAtPoint(e.getPoint());
                    if (row != hoverRow.getIndex()) {
                        hoverRow.setIndex(row);
                        table.repaint();
                    }
                }

                @Override
                public void mouseDragged(MouseEvent e) {
                    int row = table.rowAtPoint(e.getPoint());
                    if (row != hoverRow.getIndex()) {
                        hoverRow.setIndex(row);
                        table.repaint();
                    }
                }
            });
        }

        public static enum TableType {
            MULTI_LINE, DEFAULT
        }
    }

    public static class TableCustomCellRender extends DefaultTableCellRenderer {
        private static final long serialVersionUID = 1L;
        private final HoverIndex hoverRow;

        public TableCustomCellRender(HoverIndex hoverRow) {
            this.hoverRow = hoverRow;
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            Component com = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            setBorder(new EmptyBorder(10, 10, 10, 10));
            if (isSelected) {
                com.setBackground(table.getSelectionBackground());
            } else {
                if (row == hoverRow.getIndex()) {
                    com.setBackground(new Color(230, 230, 230));
                } else {
                    if (row % 2 == 0) {
                        com.setBackground(Color.WHITE);
                    } else {
                        com.setBackground(new Color(242, 242, 242));
                    }
                }
            }
            com.setFont(table.getFont());
            return com;
        }
    }

    public static class TableHeaderCustomCellRender extends DefaultTableCellRenderer {
        private static final long serialVersionUID = 1L;
        private final JTable table;
        private final TableCellRenderer oldCellRenderer;

        public TableHeaderCustomCellRender(JTable table) {
            this.table = table;
            oldCellRenderer = table.getTableHeader().getDefaultRenderer();
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            Component com = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            Component oldHeader = oldCellRenderer.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            JLabel oldLabel = (JLabel) oldHeader;
            JLabel label = (JLabel) com;
            label.setHorizontalTextPosition(oldLabel.getHorizontalTextPosition());
            label.setIcon(oldLabel.getIcon());
            setBorder(new EmptyBorder(8, 10, 8, 10));
            com.setFont(table.getTableHeader().getFont().deriveFont(Font.BOLD));
            com.setBackground(table.getTableHeader().getBackground());
            
         // Aplicar el fondo verde oscuro y el texto en blanco y en mayúsculas
            com.setBackground(new Color(0, 51, 0));  // Verde oscuro
            com.setForeground(Color.WHITE);  // Texto blanco
            label.setHorizontalAlignment(SwingConstants.CENTER);  // Centrado del texto
            label.setText(label.getText().toUpperCase());  // Convertir a mayúsculas
            
            return com;
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setColor(table.getGridColor());
            g2.drawLine(getWidth() - 1, 0, getWidth() - 1, getHeight());
            g2.drawLine(0, getHeight() - 1, getWidth() - 1, getHeight() - 1);
            g2.dispose();
        }
    }

    public static class TableScrollButton extends JLayeredPane {
        private static final long serialVersionUID = 1L;
        private float animate;
        private boolean show = false;
        private Animator animator;
        private Animator animatorScroll;
        private TimingTarget target;

        public TableScrollButton() {
            init();
        }

        private void init() {
            setLayout(new BorderLayout());
            animator = new Animator(300, new TimingTargetAdapter() {
                @Override
                public void timingEvent(float fraction) {
                    if (show) {
                        animate = fraction;
                    } else {
                        animate = 1f - fraction;
                    }
                    repaint();
                }
            });
            animator.setAcceleration(.5f);
            animator.setDeceleration(.5f);
            animator.setResolution(5);
            animatorScroll = new Animator(300);
            animatorScroll.setAcceleration(.5f);
            animatorScroll.setDeceleration(.5f);
            animatorScroll.setResolution(5);
        }

        private void start(boolean show) {
            if (animator.isRunning()) {
                float f = animator.getTimingFraction();
                animator.stop();
                animator.setStartFraction(1f - f);
            } else {
                animator.setStartFraction(0f);
            }
            this.show = show;
            animator.start();
        }

        @SuppressWarnings({ "unchecked", "rawtypes" })
        @Override
        public void add(Component comp, Object constraints) {
            super.add(new JLayer(comp, new ScrollLayerUI()), constraints);
        }

        private class ScrollLayerUI extends LayerUI<JScrollPane> {
            private static final long serialVersionUID = 1L;
            private Shape shape;
            @SuppressWarnings("unused")
			private Color color = new Color(60, 148, 225);
            private boolean mousePressed;
            private boolean mouseHovered;
            private final Image image = new ImageIcon(getClass().getResource("up.png")).getImage();

            @SuppressWarnings("rawtypes")
			@Override
            public void installUI(JComponent c) {
                super.installUI(c);
                if (c instanceof JLayer) {
                    ((JLayer) c).setLayerEventMask(AWTEvent.MOUSE_EVENT_MASK | AWTEvent.MOUSE_MOTION_EVENT_MASK);
                }
            }

            @SuppressWarnings("rawtypes")
			@Override
            public void uninstallUI(JComponent c) {
                if (c instanceof JLayer) {
                    ((JLayer) c).setLayerEventMask(0);
                }
                super.uninstallUI(c);
            }

            @Override
            public void paint(Graphics g, JComponent c) {
                super.paint(g, c);
                @SuppressWarnings("rawtypes")
				JScrollPane scroll = (JScrollPane) ((JLayer) c).getView();
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                if (mousePressed) {
                    g2.setColor(new Color(10, 92, 137));
                } else {
                    if (mouseHovered) {
                        g2.setColor(new Color(14, 122, 181));
                    } else {
                        g2.setColor(new Color(18, 149, 220));
                    }
                }
                int gapx = scroll.getVerticalScrollBar().isShowing() ? scroll.getVerticalScrollBar().getWidth() : 0;
                int gapy = scroll.getHorizontalScrollBar().isShowing() ? scroll.getHorizontalScrollBar().getHeight() : 0;
                int y_over = 50 + gapy;
                int x = c.getWidth() - 50 - gapx;
                int y = (int) ((c.getHeight() - 50 - gapy) + (y_over * (1f - animate)));
                shape = new Ellipse2D.Double(x, y, 40, 40);
                g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, animate * 0.7f));
                g2.fill(shape);
                g2.drawImage(image, x + 10, y + 10, null);
                g2.dispose();
                if (scroll.getViewport().getViewRect().y > 0) {
                    if (!show) {
                        start(true);
                    }
                } else if (show) {
                    start(false);
                }
            }

            @Override
            protected void processMouseEvent(MouseEvent e, JLayer<? extends JScrollPane> l) {
                Point point = SwingUtilities.convertPoint(e.getComponent(), e.getPoint(), l.getView());
                if (SwingUtilities.isLeftMouseButton(e)) {
                    if (e.getID() == MouseEvent.MOUSE_PRESSED) {
                        if (shape.contains(point)) {
                            mousePressed = true;
                            e.consume();
                        }
                    } else if (e.getID() == MouseEvent.MOUSE_RELEASED) {
                        if (mousePressed && mouseHovered) {
                            scrollBackToTop(l.getView());
                        }
                        mousePressed = false;
                    }
                }
                l.repaint();
            }

            @Override
            protected void processMouseMotionEvent(MouseEvent e, JLayer<? extends JScrollPane> l) {
                Point point = SwingUtilities.convertPoint(e.getComponent(), e.getPoint(), l.getView());
                if (shape.contains(point)) {
                    mouseHovered = true;
                    e.consume();
                } else {
                    mouseHovered = false;
                }
                l.repaint();
            }
        }

        private void scrollBackToTop(JScrollPane scroll) {
            animatorScroll.removeTarget(target);
            target = new PropertySetter(scroll.getVerticalScrollBar(), "value", scroll.getVerticalScrollBar().getValue(), 0);
            animatorScroll.addTarget(target);
            animatorScroll.start();
        }
    }

    public static class TextAreaCellRenderer extends JTextArea implements TableCellRenderer {
        private static final long serialVersionUID = 1L;
        private final List<List<Integer>> rowAndCellHeights = new ArrayList<>();
        private final HoverIndex hoverRow;

        public TextAreaCellRenderer(HoverIndex hoverRow) {
            this.hoverRow = hoverRow;
            setWrapStyleWord(true);
            setLineWrap(true);
            setOpaque(true);
            setBorder(new EmptyBorder(8, 10, 8, 10));
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            setText(Objects.toString(value, ""));
            adjustRowHeight(table, row, column);
            if (isSelected) {
                setBackground(table.getSelectionBackground());
            } else {
                if (row == hoverRow.getIndex()) {
                    setBackground(new Color(230, 230, 230));
                } else {
                    if (row % 2 == 0) {
                        setBackground(Color.WHITE);
                    } else {
                        setBackground(new Color(242, 242, 242));
                    }
                }
            }
            setFont(table.getFont());
            return this;
        }

        private void adjustRowHeight(JTable table, int row, int column) {
            setBounds(table.getCellRect(row, column, false));
            int preferredHeight = getPreferredSize().height;
            while (rowAndCellHeights.size() <= row) {
                rowAndCellHeights.add(new ArrayList<>(column));
            }
            List<Integer> list = rowAndCellHeights.get(row);
            while (list.size() <= column) {
                list.add(0);
            }
            list.set(column, preferredHeight);
            int max = list.stream().max((x, y) -> Integer.compare(x, y)).get();
            if (table.getRowHeight(row) != max) {
                table.setRowHeight(row, max);
            }
        }
    }
}