package ui;

import logic.ContactManager;
import model.Contact;

import javax.swing.*;

public class MainWindow extends JFrame {
    private final ContactManager manager;

    public MainWindow(ContactManager manager) {
        this.manager = manager;
        setTitle("Contacts");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(390, 780);
        setResizable(false);
        setLocationRelativeTo(null);
        showList();
    }

    private void setScreen(JPanel screen) {
        setContentPane(screen);
        revalidate();
        repaint();
    }

    public void showList() { setScreen(new ListScreen(this, manager)); }
    public void showDetail(Contact c) { setScreen(new DetailScreen(this, manager, c)); }
    public void showForm(Contact existing) { setScreen(new FormScreen(this, manager, existing)); }
    public void showDuplicates() { setScreen(new DuplicateScreen(this, manager)); }
}
