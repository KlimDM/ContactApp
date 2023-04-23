package contactsApp.util;


import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringParser {
    public static boolean checkNumber(String number) {
        final String numberRegex = "^\\+?(\\([A-Za-z0-9]+\\)|[A-Za-z0-9]+[ -]\\([A-Za-z0-9]{2,}\\)" +
                "|[A-Za-z0-9]+)([ -][A-Za-z0-9]{2,})*";
        Pattern numberPattern = Pattern.compile(numberRegex);
        Matcher numberMatcher = numberPattern.matcher(number);
        return numberMatcher.matches();
    }

    public static boolean checkGender(String gender) {
        if (gender.equals("M") || gender.equals("F")) {
            return true;
        } else {
            System.out.println("Bad gender!");
            return false;
        }
    }

    public static LocalDate parseDate (String inputDate) {
        try {
            return LocalDate.parse(inputDate);
        }
        catch (DateTimeParseException e) {
            System.out.println("Bad date!");
            return null;
        }
    }
}
