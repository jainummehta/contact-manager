package ui;

import logic.ContactManager;
import model.Contact;

import javax.swing.*;
import java.awt.*;

public class FormScreen extends JPanel {
    private final Ui.HintField nameField = new Ui.HintField("Name");
    private final Ui.HintField phoneField = new Ui.HintField("Phone");
    private final Ui.HintField emailField = new Ui.HintField("Email");
    private final Ui.HintField extraField = new Ui.HintField("Nickname");
    private final JComboBox<String> typeBox = new JComboBox<>(new String[]{"Personal", "Business", "Emergency"});
    private final JLabel error = new JLabel(" ");

    public FormScreen(MainWindow win, ContactManager manager, Contact existing) {
        setLayout(new BorderLayout());
        setBackground(Ui.LIGHT);

        JButton cancel = Ui.textButton("Cancel", Ui.BLUE);
        cancel.addActionListener(e -> {
            if (existing == null) win.showList();
            else win.showDetail(existing);
        });
        JButton done = Ui.textButton("Done", Ui.BLUE);
        done.setFont(Ui.font(Font.BOLD, 16));
        add(Ui.topBar(cancel, existing == null ? "New Contact" : "Edit Contact", done, Ui.LIGHT), BorderLayout.NORTH);

        if (existing != null) {
            nameField.setText(existing.getName());
            phoneField.setText(existing.getPhone());
            emailField.setText(existing.getEmail());
            typeBox.setSelectedItem(existing.getType());
            extraField.setText(existing.getExtra());
        }
        updateHint();
        typeBox.addActionListener(e -> updateHint());

        JPanel body = new JPanel();
        body.setLayout(new BoxLayout(body, BoxLayout.Y_AXIS));
        body.setOpaque(false);
        body.setBorder(BorderFactory.createEmptyBorder(10, 16, 16, 16));

        Ui.Round typeCard = new Ui.Round(Color.WHITE, 16);
        typeCard.setLayout(new BorderLayout());
        typeCard.setBorder(BorderFactory.createEmptyBorder(8, 14, 8, 10));
        typeCard.add(new JLabel("Type"), BorderLayout.WEST);
        typeCard.add(typeBox, BorderLayout.EAST);
        typeCard.setAlignmentX(Component.LEFT_ALIGNMENT);
        Ui.fullWidth(typeCard);

        Ui.Round fieldsCard = new Ui.Round(Color.WHITE, 16);
        fieldsCard.setLayout(new BoxLayout(fieldsCard, BoxLayout.Y_AXIS));
        fieldsCard.add(nameField);
        fieldsCard.add(Ui.separator(12));
        fieldsCard.add(phoneField);
        fieldsCard.add(Ui.separator(12));
        fieldsCard.add(emailField);
        fieldsCard.add(Ui.separator(12));
        fieldsCard.add(extraField);
        for (JComponent f : new JComponent[]{nameField, phoneField, emailField, extraField}) {
            Ui.fullWidth(f);
        }
        fieldsCard.setAlignmentX(Component.LEFT_ALIGNMENT);
        Ui.fullWidth(fieldsCard);

        error.setForeground(Ui.RED);
        error.setFont(Ui.font(Font.PLAIN, 13));
        error.setAlignmentX(Component.LEFT_ALIGNMENT);

        body.add(typeCard);
        body.add(Box.createVerticalStrut(14));
        body.add(fieldsCard);
        body.add(Box.createVerticalStrut(10));
        body.add(error);
        JPanel holder = new JPanel(new BorderLayout());
        holder.setOpaque(false);
        holder.add(body, BorderLayout.NORTH);
        add(holder, BorderLayout.CENTER);

        done.addActionListener(e -> save(win, manager, existing));
    }

    private void updateHint() {
        String type = (String) typeBox.getSelectedItem();
        if ("Business".equals(type)) extraField.setHint("Company");
        else if ("Emergency".equals(type)) extraField.setHint("Relation (Mother, Friend...)");
        else extraField.setHint("Nickname");
    }

    private void save(MainWindow win, ContactManager manager, Contact existing) {
        String name = nameField.getText().trim();
        String phone = phoneField.getText().trim();
        String email = emailField.getText().trim();
        String extra = extraField.getText().trim();
        String type = (String) typeBox.getSelectedItem();

        int digits = phone.replaceAll("\\D", "").length();
        if (name.isEmpty()) {
            error.setText("Please enter a name.");
            return;
        }
        if (digits < 7 || digits > 15) {
            error.setText("Phone needs 7 to 15 digits.");
            return;
        }
        if (!email.isEmpty() && !email.matches("[^@\\s]+@[^@\\s]+\\.[^@\\s]+")) {
            error.setText("That email does not look right.");
            return;
        }

        Contact newContact = Contact.create(type, name, phone, email, extra);
        if (existing == null) manager.add(newContact);
        else manager.replace(existing, newContact);
        win.showDetail(newContact);
    }
}
