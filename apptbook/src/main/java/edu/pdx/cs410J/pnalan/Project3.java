package edu.pdx.cs410J.pnalan;

import java.io.*;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

public class Project3 {
    /**
     * various error comments initialized to be called at appropriate places
     */
    /**
     * argumentList An argument list that specifies the order of input values
     */
    private static final String argumentList = "[options] <owner> <description> <begin date> <begin time> <AM/PM> <end date> <end time> <AM/PM>";

    /**
     * USAGE_MESSAGE prints the way an input has to be given for creating an appointment
     */
    public static final String USAGE_MESSAGE = "java edu.pdx.cs410J.pnalan.Project2 [options] <args> args are in this order: \n"+ argumentList +"\n" + "[options] may appear in any order, " +
            "but the text file name should follow the -textFile option and the options are:\n"+
            "-pretty (prettyFile/-)"+
            " -print\n"
            +"-README\n"
            +"-textFile textfilename";

    /**
     * MISSING_COMMAND_LINE_ARGUMENTS has an error message displayed when the corresponding message when no options or arguments are passed in the commnad line
     */
    public static final String MISSING_COMMAND_LINE_ARGUMENTS = "Missing command line arguments";

    /**
     * TOO_MANY_ARGUMENTS has an error message displayed when the arguments entered exceeds the expected count
     */
    public static final String TOO_MANY_ARGUMENTS = "The required number of arguments has exceeded.";

    /**
     * MISSING_DESCRIPTION has an error message displayed when the arguments entered at the input has a missing description
     */
    public static final String MISSING_DESCRIPTION = "Missing description";
    /**
     * MISSING_BEGIN_DATE has an error message displayed when the begin date is missing
     */
    public static final String MISSING_BEGIN_DATE = "Missing begin date";

    /**
     * MISSING_BEGIN_TIME has an error message displayed when begin time of an appointment is missing
     */
    public static final String MISSING_BEGIN_TIME = "Missing begin time";

    /**
     * MISSING_END_DATE has an error message displayed when end date is missing for an appointment
     */
    public static final String MISSING_END_DATE = "Missing end date";

    /**
     * MISSING_END_TIME has an error message displayed when end time is missing for an appointment
     */
    public static final String MISSING_END_TIME = "Missing end time";

    /**
     * UNRECOGNIZED_DATE_FORMAT has an error message displayed when dates entered for an appointment is not in the right format
     */
    public static final String UNRECOGNIZED_DATE_FORMAT = "Date not in requested format mm/dd/yyyy \" unrecognized date";

    /**
     * UNRECOGNIZED_TIME_FORMAT has an error message displayed when times entered for an appointment is not in the right format
     */
    public static final String UNRECOGNIZED_TIME_FORMAT = "Time not in requested format (hh:mm) \" unrecognized time";

    /**
     * UNRECOGNIZED_FILE_NAME has an error message displayed when the textfilename is not in the specified format
     */
    public static final String UNRECOGNIZED_FILE_NAME = "File name is not in the specified format. It should be textfilename or textfilename.txt or myDir/textfilename or myDir/textfilename.txt";

    /**
     * MISSING_FILE_NAME has an error message displayed when the file name is missing
     */
    public static final String MISSING_FILE_NAME = "File name is not entered";

    /**
     * MISSING_OWNER_NAME has an error message displayed when the owner name is missing
     */
    public static final String MISSING_OWNER_NAME = "Name of the Owner of the appointment is missing";

    /**
     * WRONG_ORDERING_OPTIONS has an error message displayed when the ordering of options in the command line is mixed
     */
    public static final String WRONG_ORDERING_OPTIONS = "Options must be entered first. Wrong ordering of arguments";

    /**
     * BEGIN_GREATER_THAN_END has an error message when begin date and time is after end date/time
     */
    public static  final String BEGIN_GREATER_THAN_END = "Begin date/time cannot be greater than or equal to the end date/time for an appointment!";

    /**
     * SOMETHING_WRONG this message is an unexpected error not yet handled
     */
    public static final String SOMETHING_WRONG = "An unexpected error in the input. Please check the usage for the right format!";
    /**
     * SAME_NAME this message is thrown when the textfile and the pretty file have the same name
     */
    public static final String SAME_NAME = "The filename for pretty and textFile option are the same Please provide different filenames";

    public static final String MISSING_PRETTY_FILE_NAME = "Pretty file name is missing";

    /**
     * The main method for our Project3
     * @param args
     *        [options] arguments
     * [-README -print -pretty textfilename -textFile textfilename] 'owner' 'description' 'begin date' 'begin time' 'end date' 'end time'
     *     case 1 : args.length is 0, which means no command line arguments
     *     case 2: args.length is > 6, which means too many arguments
     *     case 3: expected number of arguments. arguments are assigned and checked. They are validated and errors are thrown for any unexpected format
     */
    public static  void main(String[] args) {
        try {
            String textfilename = null;
            String prettyfilename = null;
            String owner = null;
            String description = null;
            String beginDate = null;
            String beginTimeString = null;
            String beginmeridiem = null;
            String endDate = null;
            String endTimeString = null;
            String endmeridiem = null;
            String[] argsValues = null;
            boolean hastextFileoption = false;
            boolean hasprettyfileoption = false;
            int texttextfilenameindex = 0;

            if (Arrays.asList(args).contains("-README") & Arrays.asList(args).indexOf("-README") < 3) {
                printReadMeAndExit();
            }
            /**
             * If the user enters a only print option but not the other arguments
             * prints that the command line arguments are missing
             */
            else if (args.length == 0 && Arrays.asList(args).isEmpty()) {
                printErrorMessageAndExit(MISSING_COMMAND_LINE_ARGUMENTS);
                System.exit(1);

            }
            else if ((Arrays.asList(args).contains("-textFile") && Arrays.asList(args).indexOf("-textFile") >= 5) && !Arrays.asList(args).contains("-README")) {
                printErrorMessageAndExit(WRONG_ORDERING_OPTIONS);
                System.exit(1);

            } else if ((Arrays.asList(args).contains("-print") && Arrays.asList(args).indexOf("-print") >= 5) && !Arrays.asList(args).contains("-README")) {
                printErrorMessageAndExit(WRONG_ORDERING_OPTIONS);
                System.exit(1);

            }

            else if ((Arrays.asList(args).contains("-textFile") && Arrays.asList(args).indexOf("-textFile") > 4) && !checktextfilenameGivenAftertextfileOption(args)) {
                printErrorMessageAndExit(WRONG_ORDERING_OPTIONS);
                System.exit(1);

            } else if ((Arrays.asList(args).contains("-print") && Arrays.asList(args).indexOf("-print") >= 5) && !checktextfilenameGivenAftertextfileOption(args)) {
                printErrorMessageAndExit(WRONG_ORDERING_OPTIONS);
                System.exit(1);

            } else if ((Arrays.asList(args).contains("-pretty") && Arrays.asList(args).indexOf("-pretty") >= 4) && !checkprettyfilenameGivenAfterPrintfileOption(args)) {
                printErrorMessageAndExit(WRONG_ORDERING_OPTIONS);
                System.exit(1);

            }
            else if (Arrays.asList(args).contains("-pretty") && !checkprettyfilenameGivenAfterPrintfileOption(args)) {
                printErrorMessageAndExit(WRONG_ORDERING_OPTIONS);
                System.exit(1);

            }

            else if (Arrays.asList(args).contains("-pretty") && !checkprettyfilenameNotGivenAndFoundTextfileOption(args)) {
                printErrorMessageAndExit(WRONG_ORDERING_OPTIONS);
                System.exit(1);

            }


            else if ((Arrays.asList(args).contains("-textFile") && Arrays.asList(args).indexOf("-textFile") == 0) && args.length < 5) {
                printErrorMessageAndExit(MISSING_COMMAND_LINE_ARGUMENTS);
                System.exit(1);

            }
            else if((Arrays.asList(args).contains("-pretty") && Arrays.asList(args).contains("-textFile") && args.length > 12)){
                printErrorMessageAndExit(TOO_MANY_ARGUMENTS);
                System.exit(1);
            }
            else if((Arrays.asList(args).contains("-pretty") && Arrays.asList(args).contains("-textFile") && args.length < 12)){
                printErrorMessageAndExit(MISSING_COMMAND_LINE_ARGUMENTS);
                System.exit(1);
            }
            else if(Arrays.asList(args).contains("-textFile") && Arrays.asList(args).contains("-print") && !Arrays.asList(args).contains("-pretty") && args.length < 11){
                printErrorMessageAndExit(MISSING_COMMAND_LINE_ARGUMENTS);
                System.exit(1);
            }
            else if(Arrays.asList(args).contains("-print") && Arrays.asList(args).contains("-pretty") && args.length < 11){
                printErrorMessageAndExit(MISSING_COMMAND_LINE_ARGUMENTS);
                System.exit(1);
            }
            else if(Arrays.asList(args).contains("-print") && Arrays.asList(args).contains("-pretty") && args.length > 11){
                printErrorMessageAndExit(TOO_MANY_ARGUMENTS);
                System.exit(1);
            }


            /**
             * if the user enters more than the required argument count of 9
             */
            else if (args.length > 11 && Arrays.asList(args).contains("-textFile") && Arrays.asList(args).contains("-print") && Arrays.asList(args).indexOf("-textFile") < 2 && !Arrays.asList(args).contains("-pretty")) {
                printErrorMessageAndExit(TOO_MANY_ARGUMENTS);
                System.exit(1);
            } else if (args.length > 9 && Arrays.asList(args).contains("-print") && Arrays.asList(args).indexOf("-print") < 2 && !Arrays.asList(args).contains("-textFile") && !Arrays.asList(args).contains("-pretty")) {
                printErrorMessageAndExit(TOO_MANY_ARGUMENTS);
                System.exit(1);
            } else if (args.length > 10 && !Arrays.asList(args).contains("-print") && Arrays.asList(args).contains("-textFile") && !Arrays.asList(args).contains("-pretty")) {
                printErrorMessageAndExit(TOO_MANY_ARGUMENTS);
                System.exit(1);
            } else {

                /**
                 * If the user enters all the arguments we need to validate the entry of date and time.
                 * validateDate function validates the user input of begin and end date of the appointment
                 * validateTime function validates the user input of begin and end time of the appointment
                 */

                if (args.length == 8 && !Arrays.asList(args).contains("-print") && Arrays.asList(args).contains("-textFile")) {
                    hastextFileoption = true;
                    textfilename = Arrays.asList(args).get(1);
                    argsValues = Arrays.copyOfRange(args, 2, args.length);

                } else if (args.length == 7 && !Arrays.asList(args).contains("-print") && Arrays.asList(args).contains("-textFile")) {
                    hastextFileoption = true;
                    printErrorMessageAndExit(MISSING_COMMAND_LINE_ARGUMENTS);
                    return;
                } /*else if (args.length == 6 && Arrays.asList(args).contains("-print") && !Arrays.asList(args).contains("-textFile")) {
                    printErrorMessageAndExit(MISSING_COMMAND_LINE_ARGUMENTS);
                    return;
                } */ else if (Arrays.asList(args).contains("-print") && Arrays.asList(args).contains("-textFile") && !Arrays.asList(args).contains("-pretty") && args.length > 3) {
                    if (Arrays.asList(args).indexOf("-print") == 2 && Arrays.asList(args).indexOf("-textFile") == 0) {
                        if (checktextfilenameGivenAftertextfileOption(args)) {
                            hastextFileoption = true;
                            textfilename = Arrays.asList(args).get(1);
                            argsValues = Arrays.copyOfRange(args, 3, args.length);
                        }
                    } else if (Arrays.asList(args).indexOf("-print") == 0 && Arrays.asList(args).indexOf("-textFile") == 1) {
                        if (checktextfilenameGivenAftertextfileOption(args)) {
                            hastextFileoption = true;
                            textfilename = Arrays.asList(args).get(2);
                            argsValues = Arrays.copyOfRange(args, 3, args.length);
                        }
                    }
                } else if ((Arrays.asList(args).indexOf("-print") == 0 && Arrays.asList(args).indexOf("-textFile") == 1)) {
                    hastextFileoption = true;
                    textfilename = Arrays.asList(args).get(2);
                    argsValues = Arrays.copyOfRange(args, 3, args.length);
                } else if (Arrays.asList(args).indexOf("-print") == 0 && !Arrays.asList(args).contains("-textFile") && args.length >= 2) {
                    argsValues = Arrays.copyOfRange(args, 1, args.length);
                } else if (!Arrays.asList(args).contains("-print") && Arrays.asList(args).contains("-textFile") && !Arrays.asList(args).contains("-pretty")) {
                    hastextFileoption = true;
                    textfilename = Arrays.asList(args).get(1);
                    argsValues = Arrays.copyOfRange(args, 2, args.length);
                } else if (!Arrays.asList(args).contains("-print") && !Arrays.asList(args).contains("-textFile") && Arrays.asList(args).contains("-pretty")) {
                    hasprettyfileoption = true;
                    prettyfilename = Arrays.asList(args).get(Arrays.asList(args).indexOf("-pretty") + 1);
                    argsValues = Arrays.copyOfRange(args, 2, args.length);
                } else if (Arrays.asList(args).contains("-print") && Arrays.asList(args).contains("-textFile") && Arrays.asList(args).contains("-pretty")) {
                    hastextFileoption = true;
                    hasprettyfileoption = true;
                    textfilename = Arrays.asList(args).get(Arrays.asList(args).indexOf("-textFile") + 1);
                    prettyfilename = Arrays.asList(args).get(Arrays.asList(args).indexOf("-pretty") + 1);
                    argsValues = Arrays.copyOfRange(args, 5, args.length);
                  /*  if (prettyfilename.equals(textfilename)) {
                        printErrorMessageAndExit(SAME_NAME);
                        System.exit(1);
                    }*/
                } else if (!Arrays.asList(args).contains("-print") && Arrays.asList(args).contains("-textFile") && Arrays.asList(args).contains("-pretty")) {
                    hastextFileoption = true;
                    hasprettyfileoption = true;
                    textfilename = Arrays.asList(args).get(Arrays.asList(args).indexOf("-textFile") + 1);
                    prettyfilename = Arrays.asList(args).get(Arrays.asList(args).indexOf("-pretty") + 1);
                   /* if (prettyfilename.equals(textfilename)) {
                        printErrorMessageAndExit(SAME_NAME);
                        // System.exit(1);
                    }*/
                    argsValues = Arrays.copyOfRange(args, 4, args.length);
                }
                if (argsValues == null || argsValues.length == 0) {
                    printErrorMessageAndExit(SOMETHING_WRONG);
                    System.exit(1);
                } else {


                    for (String arg : argsValues) {

                        if (owner == null) {
                            owner = arg;
                        } else if (description == null) {
                            description = arg;
                        } else if (beginDate == null) {
                            beginDate = validateDate(arg);
                        } else if (beginTimeString == null) {
                            beginTimeString = arg;
                        } else if (beginmeridiem == null) {
                            beginmeridiem = arg;
                        } else if (endDate == null) {
                            endDate = validateDate(arg);
                        } else if (endTimeString == null) {
                            endTimeString = arg;
                        } else if (endmeridiem == null) {
                            endmeridiem = arg;
                        }

                    }
                    if (beginTimeString != null) {
                        beginTimeString = validateTime(beginTimeString + " " + beginmeridiem);

                    }
                    if (endTimeString != null) {
                        endTimeString = validateTime(endTimeString + " " + endmeridiem);
                    }
                    if (hastextFileoption) {
                        if (textfilename == null) {
                            printErrorMessageAndExit(MISSING_FILE_NAME);
                            System.exit(1);
                        }
                    }
                    if (hasprettyfileoption) {
                        if (prettyfilename == null) {
                            printErrorMessageAndExit(MISSING_PRETTY_FILE_NAME);
                            System.exit(1);
                        }
                    }
                    //if this condition is true exit
                    if (hasprettyfileoption && hastextFileoption && prettyfilename.equals(textfilename)) {
                        printErrorMessageAndExit(SAME_NAME);
                        //  System.exit(1);

                    } else {
                        if (owner == null) {
                            printErrorMessageAndExit(MISSING_OWNER_NAME);
                            System.exit(1);
                        }
                        if (description == null) {
                            printErrorMessageAndExit(MISSING_DESCRIPTION);
                            System.exit(1);
                        } else if (beginDate == null) {
                            printErrorMessageAndExit(MISSING_BEGIN_DATE);
                            System.exit(1);
                        } else if (beginTimeString == null) {
                            printErrorMessageAndExit(MISSING_BEGIN_TIME);
                            System.exit(1);
                        } else if (endDate == null) {
                            printErrorMessageAndExit(MISSING_END_DATE);
                            System.exit(1);
                        } else if (endTimeString == null) {
                            printErrorMessageAndExit(MISSING_END_TIME);
                            System.exit(1);
                        }


                        Appointment app = new Appointment(description, beginDate, beginTimeString, endDate, endTimeString);
                        if (app.validateBeginLessThanEndDate(app.getBeginTime(), app.getEndTime()) != "begin date is before end date as expected") {
                            printErrorMessageAndExit(BEGIN_GREATER_THAN_END);
                            System.exit(1);
                        }
                        AppointmentBook appBook = new AppointmentBook(owner, app);
                        StringWriter sw = new StringWriter();


                        if (Arrays.asList(args).contains("-textFile") && Arrays.asList(args).indexOf("-textFile") < 2 && !Arrays.asList(args).contains("-pretty")) {
                            writeToFile(args, appBook, sw, textfilename);

                            AppointmentBook readappointment = readFromFile(textfilename, sw);
                            System.out.println(readappointment.getAppointments());
                            if (Arrays.asList(args).contains("-print")) {
                                System.out.println("Latest appointment information:");
                                System.out.println(app.toString());

                            }
                        } else if (!Arrays.asList(args).contains("-print") && !Arrays.asList(args).contains("-textFile") && Arrays.asList(args).contains("-pretty")) {
                            writeToFile(args, appBook, sw, prettyfilename);

                            AppointmentBook readappointment = readFromFile(prettyfilename, sw);
                            if (Arrays.asList(args).contains("-print")) {
                                System.out.println("Latest appointment information:");
                                System.out.println(app.toString());

                            }
                            Pretty(args, readappointment.getOwnerName(), readappointment);
                        } else if (Arrays.asList(args).contains("-textFile") && Arrays.asList(args).contains("-pretty")) {
                            writeToFile(args, appBook, sw, textfilename);

                            AppointmentBook readappointment = readFromFile(textfilename, sw);
                            System.out.println(readappointment.getAppointments());
                            if (Arrays.asList(args).contains("-print")) {
                                System.out.println("Latest appointment information:");
                                System.out.println(app.toString());

                            }
                            Pretty(args, readappointment.getOwnerName(), readappointment);
                        } else {
                            System.out.println(app.toString());
                        }

                    }
                }
            }
               // System.exit(0);


        }
        catch (Exception e){
            System.err.println(e);

        }
        finally {
            System.exit(1);
        }

    }

    private static boolean checktextfilenameGivenAftertextfileOption(String[] args){
        int textfile_index = Arrays.asList(args).indexOf("-textFile");
        int print_index = Arrays.asList(args).indexOf("-print");
        return print_index != textfile_index + 1;
    }
    private static boolean checkprettyfilenameGivenAfterPrintfileOption(String[] args){
        int print_index = 0, textfile_index = 0;
        List<String> arguments = Arrays.asList(args);
        int prettyfile_index = arguments.indexOf("-pretty");
        if(arguments.contains("-print"))
            print_index = Arrays.asList(args).indexOf("-print");
      /*  if(arguments.contains("-textFile"))
            textfile_index = arguments.indexOf("-textFile");*/
        return (print_index != prettyfile_index + 1);
       // return arguments.contains("-textFile") && arguments.contains("-print") ? (print_index != prettyfile_index + 1 && textfile_index != prettyfile_index + 1) :
       //         arguments.contains("-textFile") ? (textfile_index != prettyfile_index + 1) :  (print_index != prettyfile_index + 1);
    }
    private static boolean checkprettyfilenameNotGivenAndFoundTextfileOption(String[] args){
        int textfile_index = 0;
        List<String> arguments = Arrays.asList(args);
        int prettyfile_index = arguments.indexOf("-pretty");
        if(arguments.contains("-textFile"))
            textfile_index = arguments.indexOf("-textFile");
        return textfile_index != prettyfile_index + 1;
    }
    private static AppointmentBook readFromFile(String textfilename, StringWriter sw){
        try {
            //BufferedReader bufferedreader = new BufferedReader(new FileReader(textfilename));
            BufferedReader bufferedreader = null;
            TextParser txtParser = new TextParser(bufferedreader,textfilename,new StringReader(sw.toString()));
            AppointmentBook appointmentBook = txtParser.parse();
            return appointmentBook;
        }
        catch (Exception e){
            System.err.println("The given file doesn't exist or an error occured from reading the text file");
            e.printStackTrace();
            System.exit(1);

        }
        return null;
    }
    private static void writeToFile(String[] args, AppointmentBook appointmentBook, StringWriter sw, String textfilename){
        try{
            FileWriter filewriter = null;
            TextDumper textDumper = new TextDumper(filewriter,sw,textfilename);
            textDumper.dump(appointmentBook);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void Pretty(String[] args, String owner, final AppointmentBook appBook){
        try{
            AppointmentBook appbook = new AppointmentBook(owner, appBook);
          //  System.out.println(appbook.getAppointments());
            var  prettyfilename =  args[Arrays.asList(args).indexOf("-pretty")+1];
            PrettyPrinter prettyPrinter = new PrettyPrinter(prettyfilename);
            prettyPrinter.dump(appbook);
           // System.out.println(appbook.getAppointments());
            System.out.println("Appointment have been added successfully to pretty print");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private static String validateMeridiem(String m){
        if(m.equalsIgnoreCase("AM")){
            return m;
        }
        else if(m.equalsIgnoreCase("PM")){
            return m;
        }
        else{
            throw new IllegalArgumentException("Merediem other than AM/PM entered : "+m);
        }
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
                printErrorMessageAndExit(UNRECOGNIZED_DATE_FORMAT+" : " +dateString);
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
                printErrorMessageAndExit(UNRECOGNIZED_TIME_FORMAT+" : " +TimeString);
                System.exit(1);
            }
        }
        return null;
    }


    /**
     *
     * @param message
     * prints the error message passed to the method and also how to input the command line arguments and exits
     */
    private static void printErrorMessageAndExit(String message) {

        System.err.println(message);
        System.err.println(USAGE_MESSAGE);
    }

    /**
     *prints the README message to the user when the option is entered and exits
     */
    public static void printReadMeAndExit() {
        try {
            InputStream readme = Project3.class.getResourceAsStream("README.txt");
            BufferedReader reader = new BufferedReader(new InputStreamReader(readme));
            String line = reader.readLine();
            ;
            for (; line != null; ) {
                System.out.println(line);
                line = reader.readLine();

            }
            reader.close();
            System.exit(1);
        }
        catch (IOException e){
            System.out.println(e);;
        }
    }



}
