package contactsApp.contact;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Scanner;
import java.lang.reflect.*;
import contactsApp.util.Printer;
import contactsApp.util.StringParser;

public class PersonContact extends Contact {

    private String name;
    private String surname;
    private String gender; // optional
    private LocalDate birthDate; // optional


    public PersonContact(PersonContactBuilder builder) {
        super(builder.number);
        this.name = builder.name;
        this.surname = builder.surname;
        this.gender = builder.gender;
        this.birthDate = builder.birthDate;

    }

    public String getName(){
        return this.name;
    }

    public String getSurname() {
        return this.surname;
    }
    public String getGender() {
        if (!this.gender.equals("")) {
            return this.gender;
        } else {
            return "[no data]";
        }
    }
    public LocalDate getBirthDate() {
        return this.birthDate;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setSurname(String surname) {
        this.surname = surname;
    }
    public void setBirthDate(String birthDate) {
        try {
            this.birthDate = StringParser.parseDate(birthDate);
        }
        catch (DateTimeParseException e) {
            System.out.println("Bad date!");
        }
    }
    public void setGender(String gender) {
        this.gender = StringParser.checkGender(gender) ? gender : "";
    }


    @Override
    public String toString() {
        return String.format("Name: %s\nSurname: %s\nBirth date: %s\nGender: %s\nNumber: %s\nTime created: %s\nTime last edit: %s",
                this.getName(), this.getSurname(), this.getBirthDate() != null ? this.getBirthDate() : "[no data]", this.getGender(),
                this.getNumber(), this.getTimeCreated(), this.getTimeLastEdited());
    }

    @Override
    public String getField(String field) {
        switch (field) {
            case "name":
                return this.getName();
            case "surname":
                return this.getSurname();
            case "number":
                return this.getNumber();
            case "birth":
                return this.getBirthDate().toString();
            case "gender":
                return this.getGender();
            default:
                return "Wrong field!";
        }
    }
    @Override
    public void editField(String field) {
        Scanner sc = new Scanner(System.in);
        switch (field) {
            case "name":
                Printer.askForName();
                this.setName(sc.next());
                break;
            case "surname":
                Printer.askForSurname();
               this.setSurname(sc.next());
                break;
            case "number":
                Printer.askForNumber();
                this.setNumber(sc.next());
                break;
            case "birth":
                Printer.askForBirthDate();
                this.setBirthDate(sc.next());
                break;
            case "gender":
                Printer.askForGender();
                this.setGender(sc.next());
        }
    }

    @Override
    public String getChangeableFields() {
        return "(name, surname, birth, gender, number)";
    }
    @Override
    public String getHeading() {
        return String.format("%s %s", this.getName(), this.getSurname());
    }

    public static class PersonContactBuilder {
        private String number;
        private String name;
        private String surname;
        private String gender; // optional
        private LocalDate birthDate; // optional


        public PersonContactBuilder() {};

        public PersonContactBuilder setName() {
            var sc = new Scanner(System.in);
            Printer.askForName();
            this.name = sc.nextLine();
            return this;
        }
        public PersonContactBuilder setSurname() {
            var sc = new Scanner(System.in);
            Printer.askForSurname();
            this.surname = sc.nextLine();
            return this;
        }
        public PersonContactBuilder setBirthDate() {
            var sc = new Scanner(System.in);
            Printer.askForBirthDate();
            String birthDate = sc.nextLine();
            this.birthDate = StringParser.parseDate(birthDate);
            return this;
        }
        public PersonContactBuilder setGender() {
            var sc = new Scanner(System.in);
            Printer.askForGender();
            String gender = sc.nextLine();
            this.gender = StringParser.checkGender(gender) ? gender : "";
            return this;
        }

        public PersonContactBuilder setNumber() {
            var sc = new Scanner(System.in);
            Printer.askForNumber();
            String number = sc.nextLine();
            if (StringParser.checkNumber(number)) {
                this.number = number;
            }
            else {
                System.out.println("Wrong number format!");
                this.number = "";
            }
            return this;
        }

        public PersonContact build() {
            return new PersonContact(this);
        }
    }
}
