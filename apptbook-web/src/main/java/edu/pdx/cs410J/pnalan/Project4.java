package edu.pdx.cs410J.pnalan;

import edu.pdx.cs410J.ParserException;
import java.io.*;
import java.net.ConnectException;
import java.util.*;
import java.util.regex.Pattern;

/**
 * The main class that parses the command line and communicates with the
 * Appointment Book server using REST.
 */
public class Project4 {

    /**
     * Missing arguments message
     */
    public static final String MISSING_ARGS = "Missing command line arguments";
    /**
     * search arguments parameters
     */
    public static final String searchArguments = "<owner> <start date> <start time> <am/pm> <end date> <end time> <am/pm>";
    /**
     * print arguments parameters
     */
    public static final String printArguments = "<owner> <start date> <start time> <am/pm> <end date> <end time> <am/pm>";

    public static void main(String... args) {
        try {
            if(Arrays.asList(args).contains("-README") && Arrays.asList(args).indexOf("-README") < 5)
            {
                printReadMeAndExit();
            }
            else {
                String hostname = null;
                String portString = null;
                boolean print = false;
                boolean search = false;
                int port;
                String owner = null;
                String description = null;
                String startDate = null;
                String startTime = null;
                String startMerediem = null;
                String endDate = null;
                String endTime = null;
                String endMerediem = null;

                ArrayList<String> list = new ArrayList<>();
                list.addAll(List.of(args));
                if (validateInput(args)) {

                    if (Arrays.asList(args).contains("-print")) {
                        print = true;
                        list.remove("-print");
                    }
                    if (Arrays.asList(args).contains("-host")) {
                        list.remove("-host");
                    }
                    if (Arrays.asList(args).contains("-port")) {
                        list.remove("-port");
                    }
                    if(Arrays.asList(args).contains("-search")){
                        list.remove("-search");
                        search = true;
                    }
                    for (String arg : list) {
                        if (hostname == null) {
                            hostname = arg;
                        } else if (portString == null) {
                            portString = arg;
                        } else if (owner == null) {
                            owner = arg;
                        } else if (description == null && !Arrays.asList(args).contains("-search")) {
                            description = arg;
                        } else if (startDate == null) {
                            startDate = arg;
                        } else if (startTime == null) {
                            startTime = arg;
                        } else if (startMerediem == null) {
                            startMerediem = arg;
                        } else if (endDate == null) {
                            endDate = arg;
                        } else if (endTime == null) {
                            endTime = arg;
                        } else if (endMerediem == null) {
                            endMerediem = arg;
                        } else {
                            usage("Extraneous command line argument: " + arg);
                        }
                    }
                    if (hostname == null) {
                        usage(MISSING_ARGS);

                    }
                    if (portString == null) {
                        usage("Missing port");
                    }

                    port = Integer.parseInt(portString);
                    AppointmentBookRestClient client = new AppointmentBookRestClient(hostname, port);


                    list.remove(hostname);
                    list.remove(portString);
                    try {


                        if (list.isEmpty()) {
                            System.out.println(Messages.formatAppointmentCount(0));
                            System.exit(1);
                        }
                        if (search) {
                            //list.removeAll(Collections.singleton("-search"));
                            //String appointments = client.findAppointment(new String[]{list.get(0),  list.get(1) + " " + list.get(2) + " " + list.get(3), list.get(4) + " " +list.get(5) + " " + list.get(6)});
                            if (list.size() == 1) {
                                System.out.println(client.findAppointment(new String[]{list.get(0)}));
                            } else {
                                System.out.println(client.findAppointment(new String[]{list.get(0), list.get(1) + " " + list.get(2) + " " + list.get(3), list.get(4) + " " + list.get(5) + " " + list.get(6)}));
                            }

                        } else if (list.get(0) != null && list.size() == 1) {
                            AppointmentBook appointmentBook = new AppointmentBook(list.get(0));
                            appointmentBook = client.getAppointments(list.get(0));
                            AppointmentBookPrettyPrinter prettyPrinter = new AppointmentBookPrettyPrinter();
                            System.out.println(prettyPrinter.getPrettyAppointments(appointmentBook));
                        } else if (print) {
                            client.addAppointment(new String[]{list.get(0), list.get(1), list.get(2) + " " + list.get(3) + " " + list.get(4), list.get(5) + " " + list.get(6) + " " + list.get(7)});
                            Appointment appointment = new Appointment(list.get(1), list.get(2) + " " + list.get(3) + " " + list.get(4), list.get(5) + " " + list.get(6) + " " + list.get(7));
                            System.out.println(appointment.toString());
                            System.exit(0);
                        } else if (list.size() == 8 && hostname != null && portString != null && description != null && startDate != null && startTime != null &&
                                startMerediem != null && endDate != null && endTime != null && endMerediem != null) {

                            System.out.println(client.addAppointment(new String[]{list.get(0), list.get(1), list.get(2) + " " + list.get(3) + " " + list.get(4), list.get(5) + " " + list.get(6) + " " + list.get(7)}));
                        } else {
                            System.err.println("Enter the right parameter");
                        }

                    } catch (ParserException parserException) {
                        parserException.printStackTrace();
                    } catch (ConnectException connectException){
                        System.err.println(connectException.getMessage());
                        System.err.println("Connection refused to Server "+hostname+ " on port "+port);
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }
                }
            }

            System.exit(0);
        }
        catch (Exception e){
            System.err.println(e);
        } finally {
            System.exit(1);
        }
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
        err.println("usage: java Project4 " +
                "-search -host hostname -port portname <owner> <start date> <start time> <am/pm> <end date> <end time> <am/pm>" +
                "-print -host hostname -port portname <owner> <description> <start date> <start time> <am/pm> <end date> <end time> <am/pm>" +
                "-host hostname -port portname <owner> <description> <start date> <start time> <am/pm> <end date> <end time> <am/pm>");
        err.println("");
        err.println("  hostname         Host of web server");
        err.println("  portname         Port of web server");
        err.println("  owner         owner of the appointment book");
        err.println("  description   description of the appointment");
        err.println("  start date   start date of the appointment");
        err.println("  start time   start time of the appointment");
        err.println("  start time am/pm    am/pm of start time of the appointment");
        err.println("  end date   end date of the appointment");
        err.println("  end time   end time of the appointment");
        err.println("  end time am/pm    am/pm of end time of the appointment");
        err.println();
        err.println("This program generates an appointment book when appointments are added for a owner");
        err.println("to the server.");
        err.println("If only owner name is specified, then all the appointments of that owner are displayed");
        err.println("If the owner name and the date ranges is given all the appointments within the date range for that owner is printed");
        //err.println("If no word is specified, all dictionary entries are printed");
        err.println();

        System.exit(1);
    }

    /**
     * Validates the input
     * @param args
     *      the input arguments
     * @return true or false
     */
    public static boolean validateInput(String[] args){
        int length = args.length;
        List<String> arguments = Arrays.asList(args);
        if(!arguments.contains("-host") || !arguments.contains("-port")){
            usage("Hostname and Port must be entered");
        }
        if(arguments.indexOf("-host") > 4 || arguments.indexOf("-port") > 4){
            usage("command line arguments are not in order");
        }
        if(arguments.get(arguments.indexOf("-host") + 1 ).contains("port") || arguments.get(arguments.indexOf("-host") + 1).contains("search")
        || arguments.get(arguments.indexOf("-host") + 1).contains("print"))
        {
            usage("Enter a valid hostname");
        }
        try{
            Integer.parseInt(arguments.get(arguments.indexOf("-port") + 1));
        }
        catch (Exception e){
            System.err.println("Port number should be a number");
        }
        if(arguments.contains("-search") && arguments.contains("-print")){
            usage("search and print option cannot come together");
        }
        else if(!(arguments.indexOf("-search") < 5) && arguments.contains("-search") ){
            usage("search option must be provided before the appointment arguments");
        }
        else if(arguments.contains("-search") && arguments.size() < 12){
            usage("Missing parameters for search option, the valid arguments are as follows:"+searchArguments);
        }
        else if(arguments.contains("-search") && arguments.size() > 12){
            usage("Too many arguments entered for search option, the valid arguments are as follows:"+searchArguments);
        }
        if(arguments.contains("-search")){
            boolean check = checkInputFormat(1,arguments);
        }
        if(arguments.contains("-print") && !(arguments.indexOf("-print") < 5)){
            usage("print option must be specified before the appointment arguments");
        }
        else if(arguments.contains("-print") && arguments.size() < 13){
            usage("Missing parameters for print option, the valid arguments are as follows:"+printArguments);
        }
        else if(arguments.contains("-print") && arguments.size() > 13){
            usage("Too many arguments entered for print option, the valid arguments are as follows:"+printArguments);
        }
        if(arguments.contains("-print")){
            boolean check = checkInputFormat(3,arguments);
        }
        if(arguments.size() < 5){
        if(!(arguments.contains("-search"))) {
            if (!(arguments.contains("-print"))) {
                usage("The options -print or -search are not entered! Cannot process the appointment with insufficient parameters");
            }
        }
        }
        if(arguments.size() > 5 && arguments.size() < 12){
            if(!arguments.contains("-print") && !arguments.contains("-search")){
                usage("Insufficient arguments given when no -print or -search is given");
            }
        }
        if(arguments.size() == 12){
            if(!arguments.contains("-print") && !arguments.contains("-search")){
                boolean check = checkInputFormat(2,arguments);
            }
        }
        else if(arguments.size() > 12){
            if(!arguments.contains("-print") && !arguments.contains("-search")){
               usage("Too amy arguments given when no -search or -print option is provided");
            }
        }
    return true;
    }
    public static boolean checkInputFormat(int option, List<String> arguments){

       if(option == 1 ){
           //search option

           String startDate = validateDate(arguments.get(6));
           String startTime = validateTime(arguments.get(7) + " " + arguments.get(8));
           String endDate = validateDate(arguments.get(9));
           String endTime = validateTime(arguments.get(10) + " " + arguments.get(11) );
           if(startDate != null && startTime != null && endDate != null && endTime != null){
               return true;
           }
           else{
               return false;
           }
       }
       else if(option == 2){
           //add appointment option check format of begin date and time and end date and time
           String startDate = validateDate(arguments.get(6));
           String startTime = validateTime(arguments.get(7) + " " + arguments.get(8));
           String endDate = validateDate(arguments.get(9));
           String endTime = validateTime(arguments.get(10) + " " + arguments.get(11) );
           if(startDate != null && startTime != null && endDate != null && endTime != null){
               return true;
           }
           else{
               return false;
           }
       }
       else if(option == 3){
           //print option
           String startDate = validateDate(arguments.get(7));
           String startTime = validateTime(arguments.get(8) + " " + arguments.get(9));
           String endDate = validateDate(arguments.get(10));
           String endTime = validateTime(arguments.get(11) + " " + arguments.get(12) );
           if(startDate != null && startTime != null && endDate != null && endTime != null){
               return true;
           }
           else{
               return false;
           }
       }
    return true;
    }

    /**
     * Validates the input string date against the regex expression and returns the date string back if it matches the regex pattern,
     * else throws unrecognized date format error and exits
     * @param dateString
     *        A date in the format of String input from the user
     * @return dateString or an error message
     */
    public static String validateDate(String dateString){
        String dateregex = "^(1[0-2]|0[1-9])/(3[01]|[12][0-9]|0[1-9])/[0-9]{4}$";
        if(dateString != null) {
            if (Pattern.matches(dateregex, dateString)) {
                return dateString;
            } else {
                error("UNRECOGNIZED DATE FORMAT : " +dateString);
                System.exit(1);
            }
        }
        return null;
    }

    /**
     * Validates the input string time against the regex expression and returns the time string back if it matches the regex pattern,
     * else throws unrecognized time format error and exits
     * @param TimeString
     *         A time in the form of string input from the user
     * @return TimeString or error message
     */
    public static String validateTime(String TimeString){
        String timeregex = "^([0-1]?[0-9]|2[0-3]):[0-5][0-9]\\s[PpAa][Mm]$";
        if(TimeString != null) {
            if (Pattern.matches(timeregex, TimeString)) {
                return TimeString;
            } else {
                error("UNRECOGNIZED TIME FORMAT : " +TimeString);
                System.exit(1);
            }
        }
        return null;
    }

    /**
     *prints the README message to the user when the option is entered and exits
     */
    public static void printReadMeAndExit() {
          String readme ="README Name: Pooja Nalan Project : apptbook This project adds an appointment to an appointment book belonging to a particular owner taking the necessary details about the appointment which includes owner name, purpose of the appointment, start date and time and end date and time.\n" +
                  "The argument list is as follows:\n" +
                  "<owner> <description> <appointment begin date> <appointment begin time> <appointment end date> <appointment end time>\n" +
                  "[options] may appear in any order and the options are\n" +
                  "    -print\n" +
                  "    -README\n" +
                  "    -textfile\n" +
                  "When -README option is entered either as the first or second argument along with other arguments or just that -README option alone, the program will display the contents of the README.txt file in which the input command line arguments are mentioned in its order of usage. When\n" +
                  "-print option is selected, te program checks if all the arguments necessary for an appointment is given and once entered, the arguments appointment date and time are validated and a new appointment is created and added to the appointment book. Also, the appointment details of\n" +
                  "the owner are printed.\n" +
                  "When no arguments are entered or argument list is empty or just a -print is given without any options, if any of the required options are missing, when the validation of the entered arguments fails, then the program will print the usage of the command line arguments by\n" +
                  "printing appropriate error message.\n" +
                  "Project 2\n" +
                  "When the -textfile option is entered. This option is followed by the textfilename and then the rest of the parameters as in Project 1. Text Dumper helps write the content of an appointment book into the text file and Text Parser helps read the contents of the textfile.\n" +
                  "When -print is mentioned in the command line all the appointments are read and printed.\n" +
                  "Project 3\n" +
                  "When the -pretty option is entered along with a file name or a -, the appointments from an appointment book has to be dumped into a file in a formatted fashion. If a '-' is specified next to -pretty option, then the formatted content has to be printed to the console\n" +
                  "instead of storing it in a file." +
                  "Project 4\n"+
                  "This project focuses on the interaction between a client and a server for a web application where all the add appointments, get appointments and remove appointments are implemented in both the client and server side. The servlet does the " +
                  "doGet, doPost and doDelete functionalities accordingly. The input is given as follows:" +
                  "-search -host hostname -port portname <owner> <start date> <start time> <am/pm> <end date> <end time> <am/pm>\" +\n" +
                  "-print -host hostname -port portname <owner> <description> <start date> <start time> <am/pm> <end date> <end time> <am/pm>\" +\n" +
                  "-host hostname -port portname <owner> <description> <start date> <start time> <am/pm> <end date> <end time> <am/pm>\"";
          System.out.println(readme);
          System.exit(0);
    }

}