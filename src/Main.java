import java.util.Scanner;

public class Main {
    private static Scanner sc = new Scanner(System.in);
    private static ContactManager manager = new ContactManager();

    public static void main(String[] args) {
        manager.load();
        int choice;
        do {
            printMenu();
            choice = readInt("Enter choice: ");
            switch (choice) {
                case 1:
                    addContact();
                    break;
                case 2:
                    manager.viewAll();
                    break;
                case 3:
                    searchContact();
                    break;
                case 4:
                    updateContact();
                    break;
                case 5:
                    deleteContact();
                    break;
                case 0:
                    manager.save();
                    System.out.println("Data saved. Bye!");
                    break;
                default:
                    System.out.println("Wrong input! Choose a number from 0 to 5.");
            }
        } while (choice != 0);
    }

    private static void printMenu() {
        System.out.println();
        System.out.println("===== CONTACT MANAGER =====");
        System.out.println("1. Add Contact");
        System.out.println("2. View All Contacts");
        System.out.println("3. Search Contact");
        System.out.println("4. Update Contact");
        System.out.println("5. Delete Contact");
        System.out.println("0. Save and Exit");
    }

    private static void addContact() {
        System.out.println("1. Personal   2. Business");
        int type = readInt("Choose type: ");
        if (type != 1 && type != 2) {
            System.out.println("Wrong input! Choose 1 or 2.");
            return;
        }
        String name = readText("Name: ");
        String phone = readPhone();
        String email = readEmail();
        if (type == 1) {
            String nickname = readText("Nickname: ");
            manager.addContact(new PersonalContact(name, phone, email, nickname));
        } else {
            String company = readText("Company: ");
            manager.addContact(new BusinessContact(name, phone, email, company));
        }
        System.out.println("Contact added!");
    }

    private static void searchContact() {
        String text = readText("Enter name to search: ");
        manager.search(text);
    }

    private static void updateContact() {
        String name = readText("Enter the name of the contact to update: ");
        Contact c = manager.findByName(name);
        if (c == null) {
            System.out.println("Contact not found.");
            return;
        }
        c.setPhone(readPhone());
        c.setEmail(readEmail());
        System.out.println("Contact updated!");
    }

    private static void deleteContact() {
        String name = readText("Enter the name of the contact to delete: ");
        if (manager.deleteContact(name)) {
            System.out.println("Contact deleted!");
        } else {
            System.out.println("Contact not found.");
        }
    }

    private static int readInt(String message) {
        while (true) {
            System.out.print(message);
            String input = sc.nextLine().trim();
            try {
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("Wrong input! Please enter a number.");
            }
        }
    }

    private static String readText(String message) {
        while (true) {
            System.out.print(message);
            String input = sc.nextLine().trim();
            if (!input.isEmpty()) {
                return input.replace(",", " ");
            }
            System.out.println("Wrong input! This cannot be empty.");
        }
    }

    private static String readPhone() {
        while (true) {
            System.out.print("Phone (10 digits): ");
            String phone = sc.nextLine().trim();
            try {
                Contact.checkPhone(phone);
                return phone;
            } catch (InvalidInputException e) {
                System.out.println("Wrong input! " + e.getMessage());
            }
        }
    }

    private static String readEmail() {
        while (true) {
            System.out.print("Email: ");
            String email = sc.nextLine().trim();
            try {
                Contact.checkEmail(email);
                return email;
            } catch (InvalidInputException e) {
                System.out.println("Wrong input! " + e.getMessage());
            }
        }
    }
}
