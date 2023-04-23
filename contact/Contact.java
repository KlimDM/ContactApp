package contactsApp.contact;

import contactsApp.util.StringParser;

import java.io.Serializable;
import java.time.LocalDateTime;

public abstract class Contact implements Serializable {
    private String number;
    private LocalDateTime timeCreated;
    private LocalDateTime timeLastEdited;


    public Contact(String number) {
        this.number = number;
        this.timeCreated = LocalDateTime.now().withSecond(0).withNano(0);
        this.timeLastEdited = LocalDateTime.now().withSecond(0).withNano(0);
    }



    @Override
    public abstract String toString();

    public void setNumber(String number) {
        if (StringParser.checkNumber(number)) {
            this.number = number;
        } else {
            System.out.println("Wrong number format!");
            this.number = "";
        }
    }

    public abstract String getChangeableFields();
    public abstract String getField(String field);
    public abstract void editField(String field);

    public abstract String getHeading();

    public String getNumber() {
        return this.hasNumber() ? this.number : "[no number]";
    }

    public boolean hasNumber() {
        return !this.number.equals("");
    }

    public LocalDateTime getTimeCreated(){
        return this.timeCreated;
    }

    public LocalDateTime getTimeLastEdited() {
        return this.timeLastEdited;
    }

}
