package contactsApp;

import java.util.HashMap;
import java.util.Map;

public enum AppState {

    IN_MENU("[menu]", new String[] {"add", "list", "search", "count", "exit"}),
    IN_RECORD("[record]", new String[] {"edit", "delete", "menu"}),
    LISTING("[list]", new String[] {"[number]", "back"}),
    SEARCHING("[search]", new String[] {"[number]", "back", "again"}),
    OFF("[off]", new String[] {});

    private String title;
    private String[] possibleCommands;

    AppState (final String title, String[] possibleCommands) {
        this.title = title;
        this.possibleCommands = possibleCommands;
    }
    public String getTitle() {
        return this.title;
    }
    public String[] getPossibleCommands() {
        return this.possibleCommands;
    }


}