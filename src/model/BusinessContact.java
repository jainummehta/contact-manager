package model;

public class BusinessContact extends Contact {
    private String company;

    public BusinessContact(String name, String phone, String email, String company) {
        super(name, phone, email);
        this.company = company;
    }

    @Override public String getType() { return "Business"; }
    @Override public String getExtraLabel() { return "Company"; }
    @Override public String getExtra() { return company; }
}
