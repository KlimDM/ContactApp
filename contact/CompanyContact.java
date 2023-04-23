package contactsApp.contact;

import contactsApp.util.Printer;
import contactsApp.util.StringParser;
import java.util.Scanner;

public class CompanyContact extends Contact{
    private String orgName;
    private String address;



    public CompanyContact(CompanyContactBuilder builder) {
        super(builder.number);
        this.orgName = builder.orgName;
        this.address = builder.address;
    }

    public String getOrgName() {
        return this.orgName;
    }

    @Override
    public String getHeading() {
        return this.orgName;
    }
    @Override
    public String getField(String field) {
        switch (field) {
            case "address":
                return this.getAddress();
            case "number":
                return this.getNumber();
            default:
                return "Wrong field!";
        }
    }
    @Override
    public void editField(String field) {
        Scanner sc = new Scanner(System.in);
        switch (field) {
            case "address":
                Printer.askForAddress();
                this.setAddress(sc.nextLine());
                break;
            case "number":
                Printer.askForNumber();
                this.setNumber(sc.next());
                break;
        }
    }

    @Override
    public String getChangeableFields() {
        return "(address, number)";
    }

    public String getAddress() {
        return this.address;
    }

    @Override
    public String toString() {
        return String.format("Organization name: %s\nAddress: %s\nNumber: %s\nTime created: %s\nTime last edit: %s",
                this.getOrgName(), this.getAddress(), this.getNumber(), this.getTimeCreated(), this.getTimeLastEdited());
    }
    public void setAddress(String address) {
        this.address = address;
    }
    public void setOrgName() {
        this.orgName = orgName;
    }
    public static class CompanyContactBuilder {
        private String orgName;
        private String address;
        private String number;
        public CompanyContactBuilder() {};

        Scanner sc = new Scanner(System.in);

        public CompanyContactBuilder setOrgName() {
            Printer.askForOrgName();
            this.orgName = sc.nextLine();
            return this;
        }
        public CompanyContactBuilder setAddress() {
            System.out.print("Enter the address: ");
            this.address = sc.nextLine();
            return this;
        }
        public CompanyContactBuilder setNumber() {
            System.out.print("Enter the number: ");
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
        public CompanyContact build() {
            return new CompanyContact(this);
        }
    }
}
