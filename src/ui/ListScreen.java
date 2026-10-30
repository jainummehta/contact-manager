package ui;

import logic.ContactManager;
import model.Contact;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class ListScreen extends JPanel {
    private final MainWindow win;
    private final ContactManager manager;
    private final JPanel listPanel = new JPanel();
    private final JLabel countLabel = new JLabel("", SwingConstants.CENTER);
    private final Ui.HintField searchField = new Ui.HintField("Search (small typos are okay)");

    public ListScreen(MainWindow win, ContactManager manager) {
        this.win = win;
        this.manager = manager;
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        JLabel title = new JLabel("Contacts");
        title.setFont(Ui.font(Font.BOLD, 32));
        JButton addBtn = Ui.textButton("+", Ui.BLUE);
        addBtn.setFont(Ui.font(Font.PLAIN, 30));
        addBtn.addActionListener(e -> win.showForm(null));
        JPanel top = new JPanel(new BorderLayout());
        top.setOpaque(false);
        top.setBorder(BorderFactory.createEmptyBorder(14, 20, 6, 14));
        top.add(title, BorderLayout.WEST);
        top.add(addBtn, BorderLayout.EAST);

        Ui.Round searchBox = new Ui.Round(Ui.LIGHT, 22);
        searchBox.setLayout(new BorderLayout());
        searchBox.add(searchField);
        JPanel searchWrap = new JPanel(new BorderLayout());
        searchWrap.setOpaque(false);
        searchWrap.setBorder(BorderFactory.createEmptyBorder(0, 16, 8, 16));
        searchWrap.add(searchBox);

        JPanel north = new JPanel(new BorderLayout());
        north.setOpaque(false);
        north.add(top, BorderLayout.NORTH);
        north.add(searchWrap, BorderLayout.SOUTH);
        add(north, BorderLayout.NORTH);

        listPanel.setLayout(new BoxLayout(listPanel, BoxLayout.Y_AXIS));
        listPanel.setBackground(Color.WHITE);
        JPanel wrapper = new JPanel(new BorderLayout());
        wrapper.setBackground(Color.WHITE);
        wrapper.add(listPanel, BorderLayout.NORTH);
        JScrollPane scroll = new JScrollPane(wrapper);
        scroll.setBorder(null);
        scroll.getViewport().setBackground(Color.WHITE);
        scroll.getVerticalScrollBar().setUnitIncrement(16);
        Ui.slimScrollBar(scroll);
        add(scroll, BorderLayout.CENTER);

        JButton dupBtn = Ui.textButton("Duplicates", Ui.BLUE);
        dupBtn.setFont(Ui.font(Font.PLAIN, 14));
        dupBtn.setHorizontalAlignment(SwingConstants.LEFT);
        dupBtn.addActionListener(e -> win.showDuplicates());
        countLabel.setForeground(Ui.GRAY);
        countLabel.setFont(Ui.font(Font.PLAIN, 13));
        JPanel bottom = new JPanel(new GridLayout(1, 3));
        bottom.setBackground(Ui.LIGHT);
        bottom.setBorder(BorderFactory.createEmptyBorder(10, 14, 10, 14));
        bottom.add(dupBtn);
        bottom.add(countLabel);
        bottom.add(new JLabel());
        add(bottom, BorderLayout.SOUTH);

        searchField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                refresh();
            }
        });

        refresh();
    }

    private void refresh() {
        listPanel.removeAll();
        String query = searchField.getText().trim();
        List<Contact> shown = manager.search(query);

        String lastLetter = "";
        for (Contact c : shown) {

            if (query.isEmpty()) {
                String letter = c.getName().substring(0, 1).toUpperCase();
                if (!letter.equals(lastLetter)) {
                    listPanel.add(sectionHeader(letter));
                    lastLetter = letter;
                }
            }
            listPanel.add(row(c));
            listPanel.add(Ui.separator(72));
        }

        if (shown.isEmpty()) {
            JLabel none = Ui.label("No results", Font.PLAIN, 15, Ui.GRAY);
            none.setBorder(BorderFactory.createEmptyBorder(20, 20, 0, 0));
            listPanel.add(none);
        }

        countLabel.setText(manager.getAll().size() + " Contacts");
        listPanel.revalidate();
        listPanel.repaint();
    }

    private JPanel sectionHeader(String letter) {
        JPanel p = new JPanel(new BorderLayout());
        p.setBackground(Ui.LIGHT);
        p.setBorder(BorderFactory.createEmptyBorder(4, 20, 4, 0));
        p.add(Ui.label(letter, Font.BOLD, 13, Ui.GRAY));
        p.setAlignmentX(Component.LEFT_ALIGNMENT);
        Ui.fullWidth(p);
        return p;
    }

    private JPanel row(Contact c) {
        JPanel p = new JPanel(new BorderLayout(14, 0));
        p.setBackground(Color.WHITE);
        p.setBorder(BorderFactory.createEmptyBorder(8, 20, 8, 20));
        p.setAlignmentX(Component.LEFT_ALIGNMENT);
        p.setPreferredSize(new Dimension(100, 58));
        p.setMaximumSize(new Dimension(Integer.MAX_VALUE, 58));
        p.setCursor(new Cursor(Cursor.HAND_CURSOR));

        p.add(new Ui.Avatar(c.getInitials(), c.getName(), 40), BorderLayout.WEST);

        JLabel name = new JLabel(c.getName());
        name.setFont(Ui.font(Font.PLAIN, 16));
        p.add(name, BorderLayout.CENTER);

        JLabel type = new JLabel(c.getType());
        type.setFont(Ui.font(Font.PLAIN, 12));
        type.setForeground(Ui.GRAY);
        p.add(type, BorderLayout.EAST);

        p.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                win.showDetail(c);
            }
        });
        return p;
    }
}
