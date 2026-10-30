package model;

public class EmergencyContact extends Contact {
    private String relation;

    public EmergencyContact(String name, String phone, String email, String relation) {
        super(name, phone, email);
        this.relation = relation;
    }

    @Override public String getType() { return "Emergency"; }
    @Override public String getExtraLabel() { return "Relation"; }
    @Override public String getExtra() { return relation; }
}
