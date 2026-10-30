package model;

public class PersonalContact extends Contact {
    private String nickname;

    public PersonalContact(String name, String phone, String email, String nickname) {
        super(name, phone, email);
        this.nickname = nickname;
    }

    @Override public String getType() { return "Personal"; }
    @Override public String getExtraLabel() { return "Nickname"; }
    @Override public String getExtra() { return nickname; }
}
