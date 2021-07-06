package edu.pdx.cs410J.pnalan;
import java.util.Arrays;
import java.util.regex.Pattern;

/**
 * The main class for the CS410J appointment book Project <code>Project1</code>
 */
public class Project1 {

    /**
     * various error comments initialized to be called at appropriate places
     * README gives information about the input
     */
    static final String README = "\n README \n Name: Pooja Nalan \n Project : apptbook \n This project adds an appointment to an appointment book belonging to a particular owner"
            +"\n taking the necessary details about the appointment which includes owner name, purpose of the appointment,"+"\n start date and time and end date and time ";
    private static final String argumentList = "[options] <owner> <description> <begin date> <begin time> <end date> <end time> ";
    public static final String USAGE_MESSAGE = "java edu.pdx.cs410J.pnalan.Project1 [options] <args> args are in this order: \n"+ argumentList +"\n" + "[options] may appear in any order and the options are:\n"+ " -print\n"+"-README";
    public static final String MISSING_COMMAND_LINE_ARGUMENTS = "Missing command line arguments";
    public static final String TOO_MANY_ARGUMENTS = "The required number of arguments is 6 and you've exceeded that.";
    public static final String MISSING_DESCRIPTION = "Missing description";
    public static final String MISSING_BEGIN_DATE = "Missing begin date";
    public static final String MISSING_BEGIN_TIME = "Missing begin time";
    public static final String MISSING_END_DATE = "Missing end date";
    public static final String MISSING_END_TIME = "Missing end time";
    public static final String UNRECOGNIZED_DATE_FORMAT = "Date not in requested format (hh:mm) \" unrecognized date";
    public static final String UNRECOGNIZED_TIME_FORMAT = "Time not in requested format (hh:mm) \" unrecognized time";
    /**
     * @param args
     *        [options] arguments
     * [-README -print] 'owner' 'description' 'begin date' 'begin time' 'end date' 'end time'
     *     case 1 : args.length is 0, which means no command line arguments
     *     case 2: args.length is > 6, which means too many arguments
     *     case 3: expected number of arguments. arguments are assigned and checked. They are validated and errors are thrown for any unexpected format
     */
    public static void main(String[] args) {
        String owner = null;
        String description = null;
        String beginDate = null;
        String beginTimeString = null;
        String endDate = null;
        String endTimeString = null;

        if (Arrays.asList(args).contains("-README") & Arrays.asList(args).indexOf("-README") < 2 ) {
            printReadMeAndExit(README);
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
                AppointmentBook appBook = new AppointmentBook(owner, app);
                System.out.println("\n Appointment information:\n");
                System.out.println(" " +app.toString());
                System.out.println("\n Appointment Book information :\n");
                System.out.println(" "+ appBook.toString());
                System.exit(0);

            }

        }
        else if(args.length == 0){
            printErrorMessageAndExit(MISSING_COMMAND_LINE_ARGUMENTS);
        }


        System.exit(1);
    }

    /**
     * @param dateString
     *        A date in the format of String input from the user
     * @return dateString or an error message
     * Validates the input string date against the regex expression and returns the date string back if it matches the regex pattern,
     * else throws unrecognized date format error and exits
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
     *
     * @param TimeString
     *         A time in the form of string input from the user
     * @return TimeString or error message
     *Validates the input string time against the regex expression and returns the time string back if it matches the regex pattern,
     * else throws unrecognized time format error and exits
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
     * @param readmetext
     *        it contains the readme message
     *prints the README message to the user when the option is entered
     */
    public static void printReadMeAndExit(String readmetext){
        System.out.println(readmetext);
        System.exit(1);
    }

}
