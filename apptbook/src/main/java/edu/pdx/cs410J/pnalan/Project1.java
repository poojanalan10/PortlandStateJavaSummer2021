package edu.pdx.cs410J.pnalan;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.regex.Pattern;

/**
 * The main class for the CS410J appointment book Project <code>Project1</code>
 */
public class Project1 {

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
    public static final String USAGE_MESSAGE = "java edu.pdx.cs410J.pnalan.Project1 [options] <args> args are in this order: \n"+ argumentList +"\n" + "[options] may appear in any order and the options are:\n"+ " -print\n"+"-README";

    /**
     * MISSING_COMMAND_LINE_ARGUMENTS has an error message displayed when the corresponding message when no options or arguments are passed in the commnad line
     */
    public static final String MISSING_COMMAND_LINE_ARGUMENTS = "Missing command line arguments";

    /**
     * TOO_MANY_ARGUMENTS has an error message displayed when the arguments entered exceeds the expected count
     */
    public static final String TOO_MANY_ARGUMENTS = "The required number of arguments is 6 and you've exceeded that.";

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
    public static final String UNRECOGNIZED_DATE_FORMAT = "Date not in requested format (hh:mm) \" unrecognized date";

    /**
     * UNRECOGNIZED_TIME_FORMAT has an error message displayed when times entered for an appointment is not in the right format
     */
    public static final String UNRECOGNIZED_TIME_FORMAT = "Time not in requested format (hh:mm) \" unrecognized time";
    /**
     * BEGIN_GREATER_THAN_END has an error message when begin date and time is after end date/time
     */
    public static  final String BEGIN_GREATER_THAN_END = "Begin date/time cannot be greater than or equal to the end date/time for an appointment!";
    /**
     * The main method for our Project1
     * @param args
     *        [options] arguments
     * [-README -print] 'owner' 'description' 'begin date' 'begin time' 'end date' 'end time'
     *     case 1 : args.length is 0, which means no command line arguments
     *     case 2: args.length is > 6, which means too many arguments
     *     case 3: expected number of arguments. arguments are assigned and checked. They are validated and errors are thrown for any unexpected format
     */
    public static void main(String[] args)  {
        String owner = null;
        String description = null;
        String beginDate = null;
        String beginTimeString = null;
        String endDate = null;
        String endTimeString = null;
       // System.out.print(args.length);
        if (Arrays.asList(args).contains("-README") & Arrays.asList(args).indexOf("-README") < 2 ) {
            printReadMeAndExit();
        }
        else if(Arrays.asList(args).contains("-print") & Arrays.asList(args).indexOf("-print") == 0) {

            args = Arrays.copyOfRange(args, 1 , args.length);
            /**
             * If the user enters a only print option but not the other arguments
             * prints that the command line arguments are missing
             */
            if(args.length == 0){
                printErrorMessageAndExit(MISSING_COMMAND_LINE_ARGUMENTS);
                return;
            }
            /**
             * if the user enters more than the required argument count of 6
             */

            else if(args.length > 6) {
                printErrorMessageAndExit(TOO_MANY_ARGUMENTS);
            }
            /**
             * If the user enters all the arguments we need to validate the entry of date and time.
             * validateDate function validates the user input of begin and end date of the appointment
             * validateTime function validates the user input of begin and end time of the appointment
             */
            else {
                //  args = Arrays.copyOfRange(args, 1 , args.length);
                for (String arg : args) {
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

                if (description == null) {
                    printErrorMessageAndExit(MISSING_DESCRIPTION);
                }
                else if(beginDate == null) {
                    printErrorMessageAndExit(MISSING_BEGIN_DATE);
                }
                else if(beginTimeString == null) {
                    printErrorMessageAndExit(MISSING_BEGIN_TIME);
                }
                else if(endDate == null) {
                    printErrorMessageAndExit(MISSING_END_DATE);
                }
                else if(endTimeString == null) {
                    printErrorMessageAndExit(MISSING_END_TIME);
                }
                Appointment app = new Appointment(description,beginDate,beginTimeString,endDate,endTimeString);
                if(app.validateBeginLessThanEndDate(app.getBeginTime(),app.getEndTime()) != "begin date is before end date as expected")
                {
                    printErrorMessageAndExit(BEGIN_GREATER_THAN_END);
                }
                AppointmentBook appBook = new AppointmentBook(owner, app);
                System.out.println(app.toString());
              //  System.out.println("Appointment information:\n" + app.toString());
              //  System.out.println("\n Appointment Book information :\n");
              //  System.out.println(" "+ appBook.toString());
                System.exit(0);

            }

        }

        else if(args.length == 0){

            System.exit(1);
        }
        else if(args[0] == ""){
            printErrorMessageAndExit(MISSING_COMMAND_LINE_ARGUMENTS);
        }


        System.exit(1);
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

    /**
     *
     * @param message
     * prints the error message passed to the method and also how to input the command line arguments and exits
     */
    private static void printErrorMessageAndExit(String message) {

        System.err.println(message);
        System.err.println(USAGE_MESSAGE);
        System.exit(1);
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
            // String line = reader.readLine();
            // System.out.println(line);
            System.exit(1);
        }
        catch (IOException e){
            System.out.println(e);;
        }
    }




}
