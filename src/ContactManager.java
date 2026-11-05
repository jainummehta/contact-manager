import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class ContactManager {
    private static final String FILE_NAME = "contacts.txt";
    private ArrayList<Contact> contacts = new ArrayList<>();

    public void addContact(Contact c) {
        contacts.add(c);
    }

    public void viewAll() {
        if (contacts.isEmpty()) {
            System.out.println("No contacts yet.");
            return;
        }
        for (int i = 0; i < contacts.size(); i++) {
            System.out.println((i + 1) + ".");
            contacts.get(i).display();
        }
    }

    public void search(String text) {
        int found = 0;
        for (Contact c : contacts) {
            if (c.getName().toLowerCase().contains(text.toLowerCase())) {
                c.display();
                found++;
            }
        }
        if (found == 0) {
            System.out.println("No contact found.");
        }
    }

    public Contact findByName(String name) {
        for (Contact c : contacts) {
            if (c.getName().equalsIgnoreCase(name)) {
                return c;
            }
        }
        return null;
    }

    public boolean deleteContact(String name) {
        Contact c = findByName(name);
        if (c == null) {
            return false;
        }
        contacts.remove(c);
        return true;
    }

    public void save() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(FILE_NAME))) {
            for (Contact c : contacts) {
                writer.println(c.toFileString());
            }
        } catch (IOException e) {
            System.out.println("Could not save the file.");
        }
    }

    public void load() {
        File file = new File(FILE_NAME);
        if (!file.exists()) {
            return;
        }
        try (Scanner reader = new Scanner(file)) {
            while (reader.hasNextLine()) {
                String[] parts = reader.nextLine().split(",");
                if (parts.length == 5) {
                    if (parts[0].equals("Business")) {
                        contacts.add(new BusinessContact(parts[1], parts[2], parts[3], parts[4]));
                    } else {
                        contacts.add(new PersonalContact(parts[1], parts[2], parts[3], parts[4]));
                    }
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("Could not read the file.");
        }
    }
}
