package model;

public abstract class Contact {
    private String name;
    private String phone;
    private String email;

    public Contact(String name, String phone, String email) {
        this.name = name;
        this.phone = phone;
        this.email = email;
    }

    public abstract String getType();
    public abstract String getExtraLabel();
    public abstract String getExtra();

    public String getName() { return name; }
    public String getPhone() { return phone; }
    public String getEmail() { return email; }
    public void setPhone(String phone) { this.phone = phone; }
    public void setEmail(String email) { this.email = email; }

    public String getInitials() {
        String[] parts = name.trim().split("\\s+");
        String result = parts[0].substring(0, 1);
        if (parts.length > 1) {
            result += parts[parts.length - 1].substring(0, 1);
        }
        return result.toUpperCase();
    }

    public String toFileLine() {
        return getType() + "|" + clean(name) + "|" + clean(phone) + "|" + clean(email) + "|" + clean(getExtra());
    }

    private String clean(String s) {
        return s.replace("|", " ");
    }

    public static Contact create(String type, String name, String phone, String email, String extra) {
        if (type.equals("Business")) {
            return new BusinessContact(name, phone, email, extra);
        } else if (type.equals("Emergency")) {
            return new EmergencyContact(name, phone, email, extra);
        }
        return new PersonalContact(name, phone, email, extra);
    }
}
