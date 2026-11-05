public abstract class Contact implements Displayable {
    private String name;
    private String phone;
    private String email;

    public Contact(String name, String phone, String email) {
        this.name = name;
        this.phone = phone;
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public abstract String getType();

    public abstract String toFileString();

    @Override
    public void display() {
        System.out.println("[" + getType() + "] " + name);
        System.out.println("   Phone    : " + phone);
        System.out.println("   Email    : " + email);
    }

    public static void checkPhone(String phone) throws InvalidInputException {
        if (phone.length() != 10) {
            throw new InvalidInputException("Phone must have exactly 10 digits.");
        }
        for (int i = 0; i < phone.length(); i++) {
            if (!Character.isDigit(phone.charAt(i))) {
                throw new InvalidInputException("Phone can have only digits, no letters.");
            }
        }
    }

    public static void checkEmail(String email) throws InvalidInputException {
        if (!email.contains("@") || !email.contains(".")) {
            throw new InvalidInputException("Email must contain @ and a dot.");
        }
    }
}
