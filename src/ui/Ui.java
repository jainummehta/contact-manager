package ui;

import javax.swing.*;
import java.awt.*;

public class Ui {
    public static final Color BLUE = new Color(0, 122, 255);
    public static final Color RED = new Color(255, 59, 48);
    public static final Color GRAY = new Color(142, 142, 147);
    public static final Color LIGHT = new Color(242, 242, 247);
    public static final Color LINE = new Color(229, 229, 234);

    public static Font font(int style, int size) {
        return new Font("SansSerif", style, size);
    }

    public static JLabel label(String text, int style, int size, Color color) {
        JLabel l = new JLabel(text);
        l.setFont(font(style, size));
        l.setForeground(color);
        l.setAlignmentX(Component.LEFT_ALIGNMENT);
        return l;
    }

    public static JButton textButton(String text, Color color) {
        JButton b = new JButton(text);
        b.setForeground(color);
        b.setFont(font(Font.PLAIN, 16));
        b.setBorderPainted(false);
        b.setContentAreaFilled(false);
        b.setFocusPainted(false);
        b.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return b;
    }

    public static JPanel topBar(Component left, String title, Component right, Color bg) {
        JPanel bar = new JPanel(new BorderLayout());
        bar.setBackground(bg);
        bar.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        JLabel t = new JLabel(title, SwingConstants.CENTER);
        t.setFont(font(Font.BOLD, 16));
        bar.add(t, BorderLayout.CENTER);
        if (left != null) bar.add(left, BorderLayout.WEST);
        if (right != null) bar.add(right, BorderLayout.EAST);
        return bar;
    }

    public static JPanel separator(int indent) {
        JPanel wrap = new JPanel(new BorderLayout());
        wrap.setOpaque(false);
        wrap.setBorder(BorderFactory.createEmptyBorder(0, indent, 0, 0));
        JPanel line = new JPanel();
        line.setBackground(LINE);
        wrap.add(line);
        wrap.setPreferredSize(new Dimension(10, 1));
        wrap.setMaximumSize(new Dimension(Integer.MAX_VALUE, 1));
        wrap.setAlignmentX(Component.LEFT_ALIGNMENT);
        return wrap;
    }

    public static void fullWidth(JComponent c) {
        c.setMaximumSize(new Dimension(Integer.MAX_VALUE, c.getPreferredSize().height));
    }

    public static void slimScrollBar(JScrollPane scroll) {
        JScrollBar bar = scroll.getVerticalScrollBar();
        bar.setPreferredSize(new Dimension(8, 0));
        bar.setUI(new javax.swing.plaf.basic.BasicScrollBarUI() {
            @Override protected void paintTrack(Graphics g, JComponent c, Rectangle r) { }
            @Override protected void paintThumb(Graphics g, JComponent c, Rectangle r) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(new Color(0, 0, 0, 70));
                g2.fillRoundRect(r.x + 1, r.y, r.width - 2, r.height, 6, 6);
                g2.dispose();
            }
            @Override protected JButton createDecreaseButton(int o) { return emptyButton(); }
            @Override protected JButton createIncreaseButton(int o) { return emptyButton(); }
            private JButton emptyButton() {
                JButton b = new JButton();
                b.setPreferredSize(new Dimension(0, 0));
                return b;
            }
        });
    }

    public static class Round extends JPanel {
        private final Color fill;
        private final int arc;

        public Round(Color fill, int arc) {
            this.fill = fill;
            this.arc = arc;
            setOpaque(false);
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(fill);
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), arc, arc);
            g2.dispose();
            super.paintComponent(g);
        }
    }

    public static class Avatar extends JComponent {
        private final String initials;
        private final Color color;

        public Avatar(String initials, String seed, int size) {
            this.initials = initials;
            float hue = (Math.abs(seed.hashCode()) % 360) / 360f;
            this.color = Color.getHSBColor(hue, 0.35f, 0.85f);
            Dimension d = new Dimension(size, size);
            setPreferredSize(d);
            setMinimumSize(d);
            setMaximumSize(d);
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
            int d = Math.min(getWidth(), getHeight());
            int x = (getWidth() - d) / 2;
            int y = (getHeight() - d) / 2;
            g2.setColor(color);
            g2.fillOval(x, y, d, d);
            g2.setColor(Color.WHITE);
            g2.setFont(font(Font.BOLD, d / 3));
            FontMetrics fm = g2.getFontMetrics();
            g2.drawString(initials, x + (d - fm.stringWidth(initials)) / 2, y + (d - fm.getHeight()) / 2 + fm.getAscent());
            g2.dispose();
        }
    }

    public static class HintField extends JTextField {
        private String hint;

        public HintField(String hint) {
            this.hint = hint;
            setOpaque(false);
            setFont(font(Font.PLAIN, 16));
            setBorder(BorderFactory.createEmptyBorder(9, 12, 9, 12));
            setAlignmentX(Component.LEFT_ALIGNMENT);
        }

        public void setHint(String hint) {
            this.hint = hint;
            repaint();
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (getText().isEmpty()) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
                g2.setColor(GRAY);
                g2.setFont(getFont());
                FontMetrics fm = g2.getFontMetrics();
                g2.drawString(hint, getInsets().left, (getHeight() - fm.getHeight()) / 2 + fm.getAscent());
                g2.dispose();
            }
        }
    }
}
