package edu.pdx.cs410J.pnalan;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Class for formatting messages on the server side.  This is mainly to enable
 * test methods that validate that the server returned expected strings.
 */
public class Messages
{
    /**
     * Returns the formatted entry count
     * @param count
     * @return count
     */
    public static String formatWordCount(int count )
    {
        return String.format( "Dictionary on server contains %d words", count );
    }

    /**
     * Returns the formatted entry for appointments
     * @param count
     * @return count
     */
    public static String formatAppointmentCount(int count )
    {
        return String.format( "Appointment book on server contains %d appointments", count );
    }

    /**
     * Formats dictionary entry
     * @param word
     * @param definition
     * @return formatted definition
     */
    public static String formatDictionaryEntry(String word, String definition )
    {
        return String.format("  %s : %s", word, definition);
    }

    /**
     * Te required parameter missing message
     * @param parameterName
     * @return formatted parameter name
     */
    public static String missingRequiredParameter( String parameterName )
    {
        return String.format("The required parameter \"%s\" is missing", parameterName);
    }

   /* public static String definedWordAs(String word, String definition )
    {
        return String.format( "Defined %s as %s", word, definition );
    }*/

    /**
     * returns a mesage when owner has no appointments
     * @param owner
     * @return a message
     */
    public static String ownerHasNoAppointmentBook(String owner){
        return String.format("'%s' does not have an appointment book",owner);
    }


    /**
     * returns a message when all appointments are deleted
     * @return a message
     */
    public static String allAppointmentEntriesDeleted() {
        return "All appointment entries have been deleted";
    }

    /**
     * Parsed Dictionary
     * @param content
     *      content
     * @return value
     */
    public static Map.Entry<String, String> parseDictionaryEntry(String content) {
        Pattern pattern = Pattern.compile("\\s*(.*) : (.*)");
        Matcher matcher = pattern.matcher(content);

        if (!matcher.find()) {
            return null;
        }

        return new Map.Entry<>() {
            @Override
            public String getKey() {
                return matcher.group(1);
            }

            @Override
            public String getValue() {
                String value = matcher.group(2);
                if ("null".equals(value)) {
                    value = null;
                }
                return value;
            }

           @Override
            public String setValue(String value) {
                throw new UnsupportedOperationException("This method is not implemented yet");
            }
        };
    }

    /**
     * Returns the format dictionary entries
     * @param pw
     *          printwriter
     * @param dictionary
     *          dictionary
     */
    public static void formatDictionaryEntries(PrintWriter pw, Map<String, String> dictionary) {
        pw.println(Messages.formatWordCount(dictionary.size()));

        for (Map.Entry<String, String> entry : dictionary.entrySet()) {
            pw.println(Messages.formatDictionaryEntry(entry.getKey(), entry.getValue()));
        }
    }
   /* public static void printAppointmentEntries(PrintWriter pw, Map<String, String> dictionary) {
        pw.println(Messages.formatWordCount(dictionary.size()));

        for (Map.Entry<String, String> entry : dictionary.entrySet()) {
            pw.println(Messages.formatDictionaryEntry(entry.getKey(), entry.getValue()));
        }
    }*/

    /**
     * returns parse dictionary
     * @param content
     *          content
     * @return map
     *          map
     */
    public static Map<String, String> parseDictionary(String content) {
        Map<String, String> map = new HashMap<>();

        String[] lines = content.split("\n");
        for (int i = 1; i < lines.length; i++) {
            String line = lines[i];
            Map.Entry<String, String> entry = parseDictionaryEntry(line);
            map.put(entry.getKey(), entry.getValue());
        }

        return map;
    }

    /**
     * Returns added appointments
     * @param app
     *      app
     * @return a message
     */
    public static String addedAppointment(Appointment app){
        return ("Appointment was successfully added");
    }

    /**
     * Returns a message when start date time before end time
     * @return a message
     */
    public static String startTimeEnteredBeforeEndTime(){
        return ("Start date/time cannot be greater than end date/time");
    }

    /**
     * Returns a message when dates are malformatted
     * @param Time
     *      time
     * @param date
     *      date
     * @return message
     */
    public static String malformattedDateTime(String Time, String date){
        final String format = "DateTime: MM/dd/yyy hh:mm am/pm";
        return String.format("Given %s is not in the correct format. Follow the format %s",Time,format);

    }
}
