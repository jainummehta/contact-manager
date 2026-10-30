package ui;

import logic.ContactManager;
import model.Contact;

import javax.swing.*;
import java.awt.*;

public class DetailScreen extends JPanel {

    public DetailScreen(MainWindow win, ContactManager manager, Contact c) {
        setLayout(new BorderLayout());
        setBackground(Ui.LIGHT);

        JButton back = Ui.textButton("\u2039 Contacts", Ui.BLUE);
        back.addActionListener(e -> win.showList());
        JButton edit = Ui.textButton("Edit", Ui.BLUE);
        edit.addActionListener(e -> win.showForm(c));
        add(Ui.topBar(back, "", edit, Ui.LIGHT), BorderLayout.NORTH);

        JPanel body = new JPanel();
        body.setLayout(new BoxLayout(body, BoxLayout.Y_AXIS));
        body.setOpaque(false);
        body.setBorder(BorderFactory.createEmptyBorder(10, 16, 16, 16));

        Ui.Avatar avatar = new Ui.Avatar(c.getInitials(), c.getName(), 96);
        avatar.setAlignmentX(Component.CENTER_ALIGNMENT);
        JLabel name = new JLabel(c.getName(), SwingConstants.CENTER);
        name.setFont(Ui.font(Font.BOLD, 26));
        name.setAlignmentX(Component.CENTER_ALIGNMENT);
        JLabel type = new JLabel(c.getType());
        type.setFont(Ui.font(Font.PLAIN, 14));
        type.setForeground(Ui.GRAY);
        type.setAlignmentX(Component.CENTER_ALIGNMENT);

        body.add(avatar);
        body.add(Box.createVerticalStrut(10));
        body.add(name);
        body.add(type);
        body.add(Box.createVerticalStrut(24));

        Ui.Round card = new Ui.Round(Color.WHITE, 16);
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        addInfo(card, "phone", c.getPhone(), Ui.BLUE);
        addInfo(card, "email", c.getEmail(), Ui.BLUE);
        addInfo(card, c.getExtraLabel().toLowerCase(), c.getExtra(), Color.BLACK);
        card.setAlignmentX(Component.CENTER_ALIGNMENT);
        Ui.fullWidth(card);
        body.add(card);
        body.add(Box.createVerticalStrut(16));

        Ui.Round delCard = new Ui.Round(Color.WHITE, 16);
        delCard.setLayout(new BorderLayout());
        JButton del = Ui.textButton("Delete Contact", Ui.RED);
        del.setHorizontalAlignment(SwingConstants.LEFT);
        del.setBorder(BorderFactory.createEmptyBorder(10, 12, 10, 12));
        del.addActionListener(e -> {
            int answer = JOptionPane.showConfirmDialog(this, "Delete " + c.getName() + "?", "Delete Contact", JOptionPane.YES_NO_OPTION);
            if (answer == JOptionPane.YES_OPTION) {
                manager.remove(c);
                win.showList();
            }
        });
        delCard.add(del);
        delCard.setAlignmentX(Component.CENTER_ALIGNMENT);
        Ui.fullWidth(delCard);
        body.add(delCard);

        JPanel holder = new JPanel(new BorderLayout());
        holder.setOpaque(false);
        holder.add(body, BorderLayout.NORTH);
        add(holder, BorderLayout.CENTER);
    }

    private void addInfo(JPanel card, String label, String value, Color valueColor) {
        if (value == null || value.isEmpty()) return;
        if (card.getComponentCount() > 0) card.add(Ui.separator(16));

        JPanel row = new JPanel();
        row.setLayout(new BoxLayout(row, BoxLayout.Y_AXIS));
        row.setOpaque(false);
        row.setBorder(BorderFactory.createEmptyBorder(9, 16, 9, 16));
        row.setAlignmentX(Component.LEFT_ALIGNMENT);
        row.add(Ui.label(label, Font.PLAIN, 12, Ui.GRAY));
        row.add(Ui.label(value, Font.PLAIN, 16, valueColor));
        Ui.fullWidth(row);
        card.add(row);
    }
}
