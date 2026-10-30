package logic;

import model.Contact;

import java.util.ArrayList;
import java.util.List;

public class DuplicateFinder {

    public static class Pair {
        public final Contact a;
        public final Contact b;
        public final String reason;

        public Pair(Contact a, Contact b, String reason) {
            this.a = a;
            this.b = b;
            this.reason = reason;
        }
    }

    public static List<Pair> find(List<Contact> contacts) {
        List<Pair> pairs = new ArrayList<>();
        for (int i = 0; i < contacts.size(); i++) {
            for (int j = i + 1; j < contacts.size(); j++) {
                Contact a = contacts.get(i);
                Contact b = contacts.get(j);
                String reason = compare(a, b);
                if (reason != null) {
                    pairs.add(new Pair(a, b, reason));
                }
            }
        }
        return pairs;
    }

    private static String compare(Contact a, Contact b) {
        String pa = lastTenDigits(a.getPhone());
        String pb = lastTenDigits(b.getPhone());
        if (!pa.isEmpty() && pa.equals(pb)) return "Same phone number";

        if (!a.getEmail().isEmpty() && a.getEmail().equalsIgnoreCase(b.getEmail())) return "Same email";

        String na = a.getName().toLowerCase();
        String nb = b.getName().toLowerCase();
        if (na.length() >= 4 && nb.length() >= 4 && FuzzySearch.editDistance(na, nb) <= 2) {
            return "Very similar names";
        }
        return null;
    }

    private static String lastTenDigits(String phone) {
        String d = phone.replaceAll("\\D", "");
        return d.length() > 10 ? d.substring(d.length() - 10) : d;
    }
}
