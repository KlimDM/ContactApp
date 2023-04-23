package contactsApp;


import contactsApp.contact.CompanyContact;
import contactsApp.contact.PersonContact;
import contactsApp.contact.Contact;
import contactsApp.util.Printer;
import contactsApp.util.SerializationUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class ContactApp {
    String filename = null;

    private ArrayList<Contact> contactList;
    private AppState state;
    Scanner sc = new Scanner(System.in).useDelimiter("\n");


    @SuppressWarnings("unchecked")
    public ContactApp(String filename) {
        try {
            this.filename = filename;
            this.contactList = (ArrayList<Contact>)SerializationUtils.deserialize(filename);
        }
        catch (IOException | ClassNotFoundException e) {
            this.contactList = new ArrayList<>();
        }
        this.state = AppState.IN_MENU;
    }
    public ContactApp() {
        this.contactList = new ArrayList<>();
        this.state = AppState.IN_MENU;
    }
    public AppState getState() {
        return this.state;
    }
    public boolean isEmpty() {
        return this.contactList.size() == 0;
    }
    public void printContactsMainFields() {
        contactList.forEach((contact) -> System.out.printf("%d. %s\n",
                contactList.indexOf(contact) + 1, contact.getHeading()));
    }
    public void setState(AppState state) {
        this.state = state;
    }

    public int countContacts() {
        return contactList.size();
    }

    public void removeContact(int id) {
            Contact contact = this.contactList.get(id - 1);
            this.contactList.remove(contact);
            try {
                this.saveContacts();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            System.out.println("Contact deleted");
    }
    public Contact selectContact() {
        if (!this.isEmpty()) {
            this.printContactsMainFields();
            Printer.askForIndex();
            try {
                int contactInd = sc.nextInt();
                if (1 <= contactInd && contactInd <= this.countContacts()) {
                    return this.contactList.get(contactInd - 1);
                } else {
                    System.out.println("Invalid index!");
                }
            }
            catch (InputMismatchException e) {
                System.out.println("Index must be an Integer!\n");
            }
        } else {
            System.out.println("No records!\n");
        }
        return null;
    }
    public void saveContacts() throws IOException {
        if (this.filename != null) {
            SerializationUtils.serialize(this.contactList, filename);
        }
    }
    public void addContact() {
        Printer.askForContactType();
        String contactType = sc.next();
        switch (contactType) {
            case "organization":
                contactList.add(new CompanyContact.CompanyContactBuilder()
                        .setOrgName().setAddress().setNumber().build());
                try {
                    this.saveContacts();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                System.out.println("The record added.\n");
                break;
            case "person":
                contactList.add(new PersonContact.PersonContactBuilder().setName()
                        .setSurname().setBirthDate().setGender().setNumber().build());
                try {
                    this.saveContacts();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                System.out.println("The record added.\n");
                break;
            default:
                System.out.println("Invalid type!\n");
                break;
        }
    }
    public List<Contact> searchContacts() {
        List<Contact> matches = new ArrayList<>();
        System.out.print("Enter search query: ");
        String searchQuery = sc.next();
        Pattern pattern = Pattern.compile(searchQuery, Pattern.CASE_INSENSITIVE);
        for (Contact contact: this.contactList) {
            Matcher matcher = pattern.matcher(contact.toString());
            if (matcher.find()) {
                matches.add(contact);
            }
        }
        return matches;
    }
    public void editContact(int id) throws IOException{
                Contact contact = this.contactList.get(id - 1);
                System.out.printf("Select a field %s: ", contact.getChangeableFields());
                String field = sc.next();
                contact.editField(field);
                this.saveContacts();
                System.out.println("Saved");
                System.out.println(contact);
    }

    public void exit() {
        this.state = AppState.OFF;
    }
    public class Menu {

        public void processCommand(String command) {
            switch (command) {
                case "add": ContactApp.this.addContact();
                    break;
                case "list":  {
                    ContactApp.this.printContactsMainFields();
                    System.out.println();
                    ContactApp.this.state = AppState.LISTING;
                    break;
                }
                case "search":  {
                    List<Contact> arr = ContactApp.this.searchContacts();
                            arr.forEach(contact -> System.out.printf("%s. %s\n",
                                    arr.indexOf(contact) + 1, contact.getHeading()));
                    ContactApp.this.state = AppState.SEARCHING;
                    System.out.println();
                    break;
                }

                case "count": System.out.printf("The Phone Book has %s records.\n\n",
                        ContactApp.this.countContacts()); break;

                case "exit": ContactApp.this.state = AppState.OFF;
                default:
                    System.out.println();
            }
        }
    }
    public class Record {
        int id;
        public Record(int id) {
            this.id = id;
        }

        public void processCommand(String command) throws IOException {
            switch (command) {
                case "edit": ContactApp.this.editContact(id); break;
                case "delete": ContactApp.this.removeContact(id); break;
                case "menu": ContactApp.this.state = AppState.IN_MENU;
            }
            ContactApp.this.state = AppState.IN_MENU;
            System.out.println();
        }

    }
    public class Search {
        public void processCommand(String command) {
            try {
                int ind = Integer.parseInt(command);
                if (ind < ContactApp.this.countContacts()) {
                    System.out.println(ContactApp.this.contactList.get(ind));
                }
                ContactApp.this.state = AppState.IN_MENU;
            }
            catch (NumberFormatException e) {
                switch (command) {
                    case "back": ContactApp.this.state = AppState.IN_MENU; break;
                    case "again": List<Contact> arr = ContactApp.this.searchContacts();
                        arr.forEach(contact -> System.out.printf("%s. %s\n",
                                arr.indexOf(contact) + 1, contact.getHeading())); break;
                }
            }
            System.out.println();
        }
    }
    public class Listing {
        public void processCommand(String command) {
            try {
                int ind = Integer.parseInt(command);
                if (1 <= ind && ind <= ContactApp.this.countContacts()) {
                    System.out.println(ContactApp.this.contactList.get(ind - 1));
                    ContactApp.this.state = AppState.IN_RECORD;
                } else {
                    ContactApp.this.state = AppState.IN_MENU;
                }
            } catch (NumberFormatException e) {ContactApp.this.state = AppState.IN_MENU;}
            System.out.println();
        }
    }

}


