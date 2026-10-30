package ui;

import logic.ContactManager;
import logic.DuplicateFinder;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class DuplicateScreen extends JPanel {

    public DuplicateScreen(MainWindow win, ContactManager manager) {
        setLayout(new BorderLayout());
        setBackground(Ui.LIGHT);

        JButton back = Ui.textButton("\u2039 Contacts", Ui.BLUE);
        back.addActionListener(e -> win.showList());
        add(Ui.topBar(back, "Duplicates", null, Ui.LIGHT), BorderLayout.NORTH);

        JPanel list = new JPanel();
        list.setLayout(new BoxLayout(list, BoxLayout.Y_AXIS));
        list.setOpaque(false);
        list.setBorder(BorderFactory.createEmptyBorder(8, 16, 16, 16));

        List<DuplicateFinder.Pair> pairs = manager.findDuplicates();
        if (pairs.isEmpty()) {
            list.add(Ui.label("No duplicates found.", Font.PLAIN, 15, Ui.GRAY));
        }
        for (DuplicateFinder.Pair p : pairs) {
            list.add(card(win, manager, p));
            list.add(Box.createVerticalStrut(12));
        }

        JPanel wrapper = new JPanel(new BorderLayout());
        wrapper.setOpaque(false);
        wrapper.add(list, BorderLayout.NORTH);
        JScrollPane scroll = new JScrollPane(wrapper);
        scroll.setBorder(null);
        scroll.setOpaque(false);
        scroll.getViewport().setOpaque(false);
        scroll.getVerticalScrollBar().setUnitIncrement(16);
        Ui.slimScrollBar(scroll);
        add(scroll, BorderLayout.CENTER);
    }

    private JPanel card(MainWindow win, ContactManager manager, DuplicateFinder.Pair p) {
        Ui.Round card = new Ui.Round(Color.WHITE, 16);
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBorder(BorderFactory.createEmptyBorder(12, 16, 12, 16));
        card.setAlignmentX(Component.LEFT_ALIGNMENT);

        card.add(Ui.label(p.reason.toUpperCase(), Font.BOLD, 11, Ui.GRAY));
        card.add(Box.createVerticalStrut(6));
        card.add(Ui.label(p.a.getName() + "  \u00b7  " + p.a.getPhone(), Font.PLAIN, 15, Color.BLACK));
        card.add(Ui.label(p.b.getName() + "  \u00b7  " + p.b.getPhone(), Font.PLAIN, 15, Color.BLACK));
        card.add(Box.createVerticalStrut(6));

        JButton merge = Ui.textButton("Merge into " + p.a.getName(), Ui.BLUE);
        merge.setFont(Ui.font(Font.PLAIN, 14));
        merge.setHorizontalAlignment(SwingConstants.LEFT);
        merge.setBorder(BorderFactory.createEmptyBorder(4, 0, 0, 0));
        merge.setAlignmentX(Component.LEFT_ALIGNMENT);
        merge.addActionListener(e -> {
            manager.merge(p.a, p.b);
            win.showDuplicates();
        });
        card.add(merge);

        Ui.fullWidth(card);
        return card;
    }
}
