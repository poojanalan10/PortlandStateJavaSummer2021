package edu.pdx.cs410J.pnalan;

import edu.pdx.cs410J.ParserException;
import org.checkerframework.checker.units.qual.A;

import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.*;

/**
 * The main class that parses the command line and communicates with the
 * Appointment Book server using REST.
 */
public class Project4 {

    public static final String MISSING_ARGS = "Missing command line arguments";

    public static void main(String... args) {
        String hostName = null;
        String portString = null;
        String word = null;
        String definition = null;

        int port;

        String hostname = args[Arrays.asList(args).indexOf("-host") + 1];
        port = Integer.parseInt(args[Arrays.asList(args).indexOf("-port") + 1]);
        if (hostname == null) {
            usage( MISSING_ARGS );

        } else if ( port == 0) {
            usage( "Missing port" );
        }

        AppointmentBookRestClient client = new AppointmentBookRestClient(hostname, port);
        ArrayList<String> list = new ArrayList<>();
        list.addAll(List.of(args));
        list.remove(list.get(list.indexOf("-host") + 1));
        list.remove(list.get(list.indexOf("-port") + 1));
        list.remove("-host");
        list.remove("-port");
        try {
           /* if (list.size() == 1) {
                System.out.println(client);
            } else*/ if (list.contains("-search")) {
                list.removeAll(Collections.singleton("-search"));
                //String appointments = client.findAppointment(new String[]{list.get(0),  list.get(1) + " " + list.get(2) + " " + list.get(3), list.get(4) + " " +list.get(5) + " " + list.get(6)});
                if(list.size() == 1){
                    System.out.println(client.findAppointment(new String[]{list.get(0)}));
                }
                else {
                    System.out.println(client.findAppointment(new String[]{list.get(0), list.get(1) + " " + list.get(2) + " " + list.get(3), list.get(4) + " " + list.get(5) + " " + list.get(6)}));
                }

            }
            else if (list.get(0) != null && list.size() == 1){
                AppointmentBook appointmentBook = new AppointmentBook(list.get(0));
                appointmentBook = client.getAppointments(list.get(0));
                AppointmentBookPrettyPrinter prettyPrinter = new AppointmentBookPrettyPrinter();
                System.out.println(prettyPrinter.getPrettyAppointments(appointmentBook));
            }
            else {
                boolean pcheck = list.removeAll(Collections.singleton("-print"));
                System.out.println(client.addAppointment(new String[]{list.get(0), list.get(1),list.get(2) + " " + list.get(3)+ " " + list.get(4) , list.get(5) + " " + list.get(6) + " " + list.get(7)}));
                if (pcheck) {
                    Appointment appointment = new Appointment(list.get(1), list.get(2)+ " " + list.get(3) + " " + list.get(4) , list.get(5)+ " " +list.get(6) + " " + list.get(7));
                    System.out.println(appointment.toString());
                }


                System.exit(0);
            }
        }catch (ParserException parserException) {
            parserException.printStackTrace();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }

        System.exit(0);
          /*   try {
            port = Integer.parseInt( portString );

        } catch (NumberFormatException ex) {
            usage("Port \"" + portString + "\" must be an integer");
            return;
        }
*/

      /*  AppointmentBookRestClient client = new AppointmentBookRestClient(hostName, port);

        String message;
        try {
            if (word == null) {
                // Print all word/definition pairs
                Map<String, String> dictionary = client.getAllDictionaryEntries();
                StringWriter sw = new StringWriter();
                Messages.formatDictionaryEntries(new PrintWriter(sw, true), dictionary);
                message = sw.toString();

            } else if (definition == null) {
                // Print all dictionary entries
                message = Messages.formatDictionaryEntry(word, client.getDefinition(word));

            } else {
                // Post the word/definition pair
                client.addDictionaryEntry(word, definition);
                message = Messages.definedWordAs(word, definition);
            }

        } catch ( IOException ex ) {
            error("While contacting server: " + ex);
            return;
        }

        System.out.println(message);*/
    }

    private static void error( String message )
    {
        PrintStream err = System.err;
        err.println("** " + message);

        System.exit(1);
    }

    /**
     * Prints usage information for this program and exits
     * @param message An error message to print
     */
    private static void usage( String message )
    {
        PrintStream err = System.err;
        err.println("** " + message);
        err.println();
        err.println("usage: java Project4 host port [word] [definition]");
        err.println("  host         Host of web server");
        err.println("  port         Port of web server");
        err.println("  word         Word in dictionary");
        err.println("  definition   Definition of word");
        err.println();
        err.println("This simple program posts words and their definitions");
        err.println("to the server.");
        err.println("If no definition is specified, then the word's definition");
        err.println("is printed.");
        err.println("If no word is specified, all dictionary entries are printed");
        err.println();

        System.exit(1);
    }
}