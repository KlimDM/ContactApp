package contactsApp;

import contactsApp.util.Printer;

import java.awt.*;
import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;

public class AppController {
    int index = 0;

    Scanner sc = new Scanner(System.in);

    public void run(ContactApp application) throws IOException {
        Printer.askForAction(application.getState());
        String command = sc.next();
            switch (application.getState()) {
                case IN_MENU:
                    application.new Menu().processCommand(command);
                    break;
                case IN_RECORD:
                    application.new Record(index).processCommand(command);
                    break;
                case LISTING:
                    try {
                        index = Integer.parseInt(command);
                        if (1 <= index && index <= application.countContacts()) {
                            application.setState(AppState.IN_RECORD);
                        }
                    } catch (NumberFormatException e) {}
                    application.new Listing().processCommand(command);
                    break;
                case SEARCHING:
                    application.new Search().processCommand(command);
                    break;
            }
    }
    public static void main(String[] args) throws IOException {
        AppController appController = new AppController();
        ContactApp application;
        if (args.length != 0) {
            String filename = args[0];
            application = new ContactApp(filename);
        } else {
            application = new ContactApp();
        }
        while (application.getState() != AppState.OFF) {
            appController.run(application);
        }
    }
}
