package edu.pdx.cs410J.pnalan;

import java.io.*;
import java.security.InvalidParameterException;
import java.util.Arrays;
import java.util.regex.Pattern;

public class Project2 {
    /**
     * various error comments initialized to be called at appropriate places
     */
    /**
     * argumentList An argument list that specifies the order of input values
     */
    private static final String argumentList = "[options] <owner> <description> <begin date> <begin time> <end date> <end time> ";

    /**
     * USAGE_MESSAGE prints the way an input has to be given for creating an appointment
     */
    public static final String USAGE_MESSAGE = "java edu.pdx.cs410J.pnalan.Project1 [options] <args> args are in this order: \n"+ argumentList +"\n" + "[options] may appear in any order and the options are:\n"+ " -print\n"+"-README\n"+"-textFile";

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
     * UNRECOGNIZED_FILE_NAME has an error message displayed when the filename is not in the specified format
     */
    public static final String UNRECOGNIZED_FILE_NAME = "File name is not in the specified format. It should be filename or filename.txt or myDir/filename or myDir/filename.txt";

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
     * The main method for our Project1
     * @param args
     *        [options] arguments
     * [-README -print -textFile filename] 'owner' 'description' 'begin date' 'begin time' 'end date' 'end time'
     *     case 1 : args.length is 0, which means no command line arguments
     *     case 2: args.length is > 6, which means too many arguments
     *     case 3: expected number of arguments. arguments are assigned and checked. They are validated and errors are thrown for any unexpected format
     */
    public static  void main(String[] args) {
        try {
            String filename = null;
            String owner = null;
            String description = null;
            String beginDate = null;
            String beginTimeString = null;
            String endDate = null;
            String endTimeString = null;
            String[] argsValues = null;
            boolean hastextFileoption = false;
            int textFilenameindex = 0;

            if (Arrays.asList(args).contains("-README") & Arrays.asList(args).indexOf("-README") < 3) {
                printReadMeAndExit();
            }
            /**
             * If the user enters a only print option but not the other arguments
             * prints that the command line arguments are missing
             */
            else if (args.length == 0 && Arrays.asList(args).isEmpty()) {
                printErrorMessageAndExit(MISSING_COMMAND_LINE_ARGUMENTS);

            } else if ((Arrays.asList(args).contains("-textFile") && Arrays.asList(args).indexOf("-textFile") >= 2) && !Arrays.asList(args).contains("-README")) {
                printErrorMessageAndExit(WRONG_ORDERING_OPTIONS);

            } else if ((Arrays.asList(args).contains("-print") && Arrays.asList(args).indexOf("-print") >= 3) && !Arrays.asList(args).contains("-README")) {
                printErrorMessageAndExit(WRONG_ORDERING_OPTIONS);

            } else if ((Arrays.asList(args).contains("-textFile") && Arrays.asList(args).indexOf("-textFile") >= 2) && !checkFileNameGivenAftertextfileOption(args)) {
                printErrorMessageAndExit(WRONG_ORDERING_OPTIONS);

            } else if ((Arrays.asList(args).contains("-print") && Arrays.asList(args).indexOf("-print") >= 3) && !checkFileNameGivenAftertextfileOption(args)) {
                printErrorMessageAndExit(WRONG_ORDERING_OPTIONS);

            } else if ((Arrays.asList(args).contains("-textFile") && Arrays.asList(args).indexOf("-textFile") == 0) && args.length == 2) {
                printErrorMessageAndExit(MISSING_COMMAND_LINE_ARGUMENTS);

            }
            /**
             * if the user enters more than the required argument count of 9
             */
            else if (args.length > 9 && Arrays.asList(args).contains("-textFile") && Arrays.asList(args).indexOf("-textFile") < 2) {
                printErrorMessageAndExit(TOO_MANY_ARGUMENTS);
            } else if (args.length > 7 && Arrays.asList(args).contains("-print") && Arrays.asList(args).indexOf("-print") < 2 && !Arrays.asList(args).contains("-textFile")) {
                printErrorMessageAndExit(TOO_MANY_ARGUMENTS);
            } else if (args.length > 8 && !Arrays.asList(args).contains("-print") && Arrays.asList(args).contains("-textFile")) {
                printErrorMessageAndExit(TOO_MANY_ARGUMENTS);
            } else {

                /**
                 * If the user enters all the arguments we need to validate the entry of date and time.
                 * validateDate function validates the user input of begin and end date of the appointment
                 * validateTime function validates the user input of begin and end time of the appointment
                 */

                if (args.length == 8 && !Arrays.asList(args).contains("-print") && Arrays.asList(args).contains("-textFile")) {
                    hastextFileoption = true;
                    filename = Arrays.asList(args).get(1);
                    argsValues = Arrays.copyOfRange(args, 2, args.length);

                } else if (args.length == 7 && !Arrays.asList(args).contains("-print") && Arrays.asList(args).contains("-textFile")) {
                    hastextFileoption = true;
                    printErrorMessageAndExit(MISSING_COMMAND_LINE_ARGUMENTS);
                } else if (args.length == 6 && Arrays.asList(args).contains("-print") && !Arrays.asList(args).contains("-textFile")) {
                    printErrorMessageAndExit(MISSING_COMMAND_LINE_ARGUMENTS);
                } else if (Arrays.asList(args).contains("-print") && Arrays.asList(args).contains("-textFile")) {
                    if (Arrays.asList(args).indexOf("-print") == 2 && Arrays.asList(args).indexOf("-textFile") == 0) {
                        if (checkFileNameGivenAftertextfileOption(args)) {
                            hastextFileoption = true;
                            filename = Arrays.asList(args).get(1);
                            argsValues = Arrays.copyOfRange(args, 3, args.length);
                        }
                    } else if (Arrays.asList(args).indexOf("-print") == 0 && Arrays.asList(args).indexOf("-textFile") == 1) {
                        if (checkFileNameGivenAftertextfileOption(args)) {
                            hastextFileoption = true;
                            filename = Arrays.asList(args).get(2);
                            argsValues = Arrays.copyOfRange(args, 3, args.length);
                        }
                    }
                } else if ((Arrays.asList(args).indexOf("-print") == 0 && Arrays.asList(args).indexOf("-textFile") == 1)) {
                    hastextFileoption = true;
                    filename = Arrays.asList(args).get(2);
                    argsValues = Arrays.copyOfRange(args, 3, args.length);
                } else if (Arrays.asList(args).indexOf("-print") == 0 && !Arrays.asList(args).contains("-textFile")) {
                    argsValues = Arrays.copyOfRange(args, 1, args.length);
                } else if (!Arrays.asList(args).contains("-print") && Arrays.asList(args).contains("-textFile")) {
                    hastextFileoption = true;
                    filename = Arrays.asList(args).get(1);
                    argsValues = Arrays.copyOfRange(args, 2, args.length);
                }

                if (argsValues == null) {
                    printErrorMessageAndExit(SOMETHING_WRONG);
                }


                for (String arg : argsValues) {

                    if (owner == null) {
                        owner = arg;
                    } else if (description == null) {
                        description = arg;
                    } else if (beginDate == null) {
                        beginDate = validateDate(arg);
                    } else if (beginTimeString == null) {
                        beginTimeString = validateTime(arg);
                    } else if (endDate == null) {
                        endDate = validateDate(arg);
                    } else if (endTimeString == null) {
                        endTimeString = validateTime(arg);
                    }

                }
                if (hastextFileoption) {
                    if (filename == null) {
                        printErrorMessageAndExit(MISSING_FILE_NAME);
                    }
                }
                if (owner == null) {
                    printErrorMessageAndExit(MISSING_OWNER_NAME);
                }
                if (description == null) {
                    printErrorMessageAndExit(MISSING_DESCRIPTION);
                } else if (beginDate == null) {
                    printErrorMessageAndExit(MISSING_BEGIN_DATE);
                } else if (beginTimeString == null) {
                    printErrorMessageAndExit(MISSING_BEGIN_TIME);
                } else if (endDate == null) {
                    printErrorMessageAndExit(MISSING_END_DATE);
                } else if (endTimeString == null) {
                    printErrorMessageAndExit(MISSING_END_TIME);
                }

                Appointment app = new Appointment(description, beginDate, beginTimeString, endDate, endTimeString);
                if (app.validateBeginLessThanEndDate(app.getBeginTime(), app.getEndTime()) != "begin date is before end date as expected") {
                    printErrorMessageAndExit(BEGIN_GREATER_THAN_END);
                }
                AppointmentBook appBook = new AppointmentBook(owner, app);
                StringWriter sw = new StringWriter();
                if (Arrays.asList(args).contains("-textFile") && Arrays.asList(args).indexOf("-textFile") < 2) {
                    writeToFile(args, appBook, sw, filename);
                    AppointmentBook readappointment = readFromFile(filename, sw);
                    System.out.println(readappointment.getAppointments());
                    if (Arrays.asList(args).contains("-print")) {
                        System.out.println("Latest appointment information:");
                        System.out.println(app.toString());
                        //   AppointmentBook readappointment = readFromFile(filename, sw);
                        //   System.out.println(readappointment.getAppointments());
                    }

                    System.exit(1);
                } else {
                    System.out.println(app.toString());
                }


                System.exit(0);

            }
        }
        catch (Exception e){
            System.err.println(e);

        }
        finally {
            System.exit(1);
        }

    }
    private static boolean checkFileNameGivenAftertextfileOption(String[] args){
        int textfile_index = Arrays.asList(args).indexOf("-textFile");
        int print_index = Arrays.asList(args).indexOf("-print");
        return print_index != textfile_index + 1;
    }
    private static AppointmentBook readFromFile(String filename, StringWriter sw){
        try {
            BufferedReader bufferedreader = new BufferedReader(new FileReader(filename));
            TextParser txtParser = new TextParser(bufferedreader,filename,new StringReader(sw.toString()));
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
    private static void writeToFile(String[] args, AppointmentBook appointmentBook, StringWriter sw, String filename){
        try{
            FileWriter filewriter = null;
            TextDumper textDumper = new TextDumper(filewriter,sw,filename);
            textDumper.dump(appointmentBook);

        } catch (IOException e) {
            e.printStackTrace();
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
        String timeregex = "^([0-1]?[0-9]|2[0-3]):[0-5][0-9]$";
        if(TimeString != null) {
            if (Pattern.matches(timeregex, TimeString)) {
                return TimeString;
            } else {
                printErrorMessageAndExit(UNRECOGNIZED_TIME_FORMAT+" : " +TimeString);
            }
        }
        return null;
    }

    /*public static String validateFile(String filename) {
        String fileregex;
        fileregex = "^([A-Za-z0-9 .+?\\.txt$])";
        if (filename != null) {
            if (Pattern.matches(fileregex, filename)) {
                return filename;
            } else {
                printErrorMessageAndExit(UNRECOGNIZED_FILE_NAME+ " : "+filename);
            }
        }
        return null;
    }*/

    /*public static boolean validateFileNameEntryPosition(String filename,String[] args){
        int textFilenameindex = (Arrays.asList(args).indexOf("-textFile")) + 2;
        if( args[textFilenameindex].equals(filename) ){
           return true;
        }
        else{
            return false;
        }
    }*/

    /**
     *
     * @param message
     * prints the error message passed to the method and also how to input the command line arguments and exits
     */
    private static void printErrorMessageAndExit(String message) {

        System.err.println(message);
        System.err.println(USAGE_MESSAGE);
       // System.exit(1);
    }

    /**
     *prints the README message to the user when the option is entered and exits
     */
    public static void printReadMeAndExit() {
        try {
            InputStream readme = Project1.class.getResourceAsStream("README.txt");
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
