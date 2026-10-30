package logic;

import model.*;

import java.util.ArrayList;
import java.util.List;

public class ContactManager {
    private final ArrayList<Contact> contacts;

    public ContactManager() {
        contacts = FileStorage.load();
        if (contacts.isEmpty()) {
            addSampleContacts();
            save();
        }
    }

    public ArrayList<Contact> getAll() {
        ArrayList<Contact> copy = new ArrayList<>(contacts);
        copy.sort((a, b) -> a.getName().compareToIgnoreCase(b.getName()));
        return copy;
    }

    public void add(Contact c) {
        contacts.add(c);
        save();
    }

    public void remove(Contact c) {
        contacts.remove(c);
        save();
    }

    public void replace(Contact oldContact, Contact newContact) {
        int index = contacts.indexOf(oldContact);
        if (index >= 0) {
            contacts.set(index, newContact);
            save();
        }
    }

    public List<Contact> search(String text) {
        final String query = text.trim();
        if (query.isEmpty()) return getAll();

        ArrayList<Contact> found = new ArrayList<>();
        for (Contact c : contacts) {
            if (FuzzySearch.score(query, c) > 0) found.add(c);
        }
        found.sort((a, b) -> FuzzySearch.score(query, b) - FuzzySearch.score(query, a));
        return found;
    }

    public List<DuplicateFinder.Pair> findDuplicates() {
        return DuplicateFinder.find(contacts);
    }

    public void merge(Contact keep, Contact other) {
        if (keep.getPhone().isEmpty()) keep.setPhone(other.getPhone());
        if (keep.getEmail().isEmpty()) keep.setEmail(other.getEmail());
        contacts.remove(other);
        save();
    }

    private void save() {
        FileStorage.save(contacts);
    }

    private void addSampleContacts() {
        contacts.add(new PersonalContact("Aarav Sharma", "+91 98100 11122", "aarav@mail.com", "Aru"));
        contacts.add(new PersonalContact("Aditi Verma", "98200 22233", "aditi.v@mail.com", ""));
        contacts.add(new PersonalContact("Ankit Jain", "97300 33344", "", ""));
        contacts.add(new PersonalContact("Bhavna Rao", "96400 44455", "bhavna@mail.com", "Bhav"));
        contacts.add(new BusinessContact("Dr. Kapoor", "011 4000 5000", "kapoor@cityhospital.in", "City Hospital"));
        contacts.add(new EmergencyContact("Mom", "95500 55566", "", "Mother"));
        contacts.add(new PersonalContact("Rohan Mehta", "9876543210", "rohan@mail.com", ""));
        contacts.add(new PersonalContact("Rohan Mehtha", "+91 98765-43210", "", "Ro"));
        contacts.add(new PersonalContact("Priya Singh", "90000 11111", "priya@mail.com", ""));
        contacts.add(new PersonalContact("Priya Sing", "91111 22222", "", ""));
        contacts.add(new BusinessContact("Zoya Khan", "93333 44444", "zoya@studio.com", "Pixel Studio"));
    }
}
