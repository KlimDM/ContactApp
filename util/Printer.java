package contactsApp.util;

import contactsApp.AppState;


public class Printer {
    public static void askForAction(AppState currentState) {
        System.out.print(currentState.getTitle() + " Enter action (" +
                String.join(", ", currentState.getPossibleCommands()) + "): ");
    }
    public static void askForContactType() {
        System.out.print("Enter the type (person, organization): ");
    }
    public static void askForIndex() {
        System.out.print("Select a record: ");
    }
    public static void askForName() {
        System.out.print("Enter the name: ");
    }
    public static void askForSurname() {
        System.out.print("Enter the surname: ");
    }
    public static void askForBirthDate() {
        System.out.print("Enter the birth date: ");
    }
    public static void askForGender() {
        System.out.print("Enter the gender (M, F): ");
    }
    public static void askForNumber(){
        System.out.print("Enter the number: ");
    }
    public static void askForOrgName() {
        System.out.print("Enter the Organization name: ");
    }
    public static void askForAddress() {
        System.out.print("Enter the address: ");
    }
}
