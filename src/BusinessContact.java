public class BusinessContact extends Contact {
    private String company;

    public BusinessContact(String name, String phone, String email, String company) {
        super(name, phone, email);
        this.company = company;
    }

    @Override
    public String getType() {
        return "Business";
    }

    @Override
    public void display() {
        super.display();
        System.out.println("   Company  : " + company);
    }

    @Override
    public String toFileString() {
        return "Business," + getName() + "," + getPhone() + "," + getEmail() + "," + company;
    }
}
