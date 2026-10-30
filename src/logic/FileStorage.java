package logic;

import model.Contact;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileStorage {
    private static final String FILE_NAME = "contacts.txt";

    public static ArrayList<Contact> load() {
        ArrayList<Contact> list = new ArrayList<>();
        File file = new File(FILE_NAME);
        if (!file.exists()) return list;

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] p = line.split("\\|", -1);
                if (p.length >= 5) {
                    list.add(Contact.create(p[0], p[1], p[2], p[3], p[4]));
                }
            }
        } catch (IOException e) {
            System.out.println("Could not read file: " + e.getMessage());
        }
        return list;
    }

    public static void save(List<Contact> contacts) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(FILE_NAME))) {
            for (Contact c : contacts) {
                writer.println(c.toFileLine());
            }
        } catch (IOException e) {
            System.out.println("Could not save file: " + e.getMessage());
        }
    }
}
