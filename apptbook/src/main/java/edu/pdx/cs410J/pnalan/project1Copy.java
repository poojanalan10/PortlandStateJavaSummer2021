package edu.pdx.cs410J.pnalan;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

/**
 * The main class for the CS410J appointment book Project
 */
public class project1Copy {
    static final String README = "\n README \n Name: Pooja Nalan \n Project : apptbook \n This project adds an appointment to an appointment book belonging to a particular owner"
            +"\n taking the necessary details about the appointment which includes owner name, purpose of the appointment,"+"\n start date and time and end date and time ";
    private static final String argumentList = "[options] <owner> <description> <begin date> <begin time> <end date> <end time> ";
    public static final String USAGE_MESSAGE = "java edu.pdx.cs410J.pnalan.Project1 [options] <args> args are in this order: \n"+ argumentList +"\n" + "[options] may appear in any order and the options are:\n"+ " -print\n"+"-README";
    public static final String MISSING_COMMAND_LINE_ARGUMENTS = "Missing command line arguments";
    public static final String TOO_MANY_ARGUMENTS = "The required number of arguments is 6 and you've exceeded that.";
    public static final String TOO_FEW_ARGUMENTS = "The required number of arguments is 6 and you've entered less than that.";
    public static final String MISSING_DESCRIPTION = "Missing description";
    public static final String MISSING_BEGIN_DATE = "Missing begin date";
    public static final String MISSING_BEGIN_TIME = "Missing begin time";
    public static final String MISSING_END_DATE = "Missing end date";
    public static final String MISSING_END_TIME = "Missing end time";

    public static void main(String[] args) {
        List<String> argumentList = Arrays.asList(args);
      //  List<String> userInput = new ArrayList<>();
        var length = args.length;
        String owner = null;
        String description = null;
        String beginDate = null;
        String beginTimeString = null;
        String endDate = null;
        String endTimeString = null;
        if (Arrays.asList(args).contains("-README") & Arrays.asList(args).indexOf("-README") < 2 ) {
            System.out.println(README);
            System.exit(1);
        }
        else if(Arrays.asList(args).contains("-print") & Arrays.asList(args).indexOf("-print") == 0) {

            args = Arrays.copyOfRange(args, 1 , args.length);
            /**
             * If the user enters a only print option but not the other arguments
             */
            if(args.length == 0){
                printErorMessageAndExit(MISSING_COMMAND_LINE_ARGUMENTS);
                return;
            }
            /**
             * If the user enters print but just few of the arguments
             */
         /*  else if(args.length < 6) {
               // printErorMessageAndExit(TOO_FEW_ARGUMENTS);
                return;
            }*/
            else if(args.length > 6) {
                printErorMessageAndExit(TOO_MANY_ARGUMENTS);
            }
            /**
             * If the user enters all the arguments we need to validate the entry of date and time.
             * validateDate function validates the user input of begin and end date of the appointment
             * validateTime function validates the user input of begin and end time of the appointment
             */
            /*else if(args.length == 6) */
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
                    printErorMessageAndExit(MISSING_DESCRIPTION);
                }
                else if(beginDate == null) {
                    printErorMessageAndExit(MISSING_BEGIN_DATE);
                }
                else if(beginTimeString == null) {
                    printErorMessageAndExit(MISSING_BEGIN_TIME);
                }
                else if(endDate == null) {
                    printErorMessageAndExit(MISSING_END_DATE);
                }
                else if(endTimeString == null) {
                    printErorMessageAndExit(MISSING_END_TIME);
                }
                Appointment app = new Appointment(description,beginDate,beginTimeString,endDate,endTimeString);
                AppointmentBook appBook = new AppointmentBook(owner, app);
                System.out.println("Appointment information");
                System.out.println(app.toString());
                System.exit(0);

            }
            /**
             * if the user enters more than the required argument count of 6
             */

        }


        System.exit(1);
    }
    public static String validateDate(String dateString){
    try {
        String dateregex = "^(0[1-9]|1[0-2])/(3[01][12][0-9]|0[1-9])/[0-9]{4}$";
        if(dateString != null) {
            if (Pattern.matches(dateregex, dateString)) {
                return dateString;
            } else {
                return "Date not in requested format(mm/dd/yyyy) / unrecognized date " + dateString;
            }
        }

            return null;
    }
    catch (Exception e) {
        return e.toString();
    }

    }
    public static String validateTime(String TimeString){
    try {
        String timeregex = "^([0-1]?[0-9]|2[0-3]):[0-5][0-9]$";
        if(TimeString != null) {
            if (Pattern.matches(timeregex, TimeString)) {
                return TimeString;
            } else {
                return "Time not in requested format (hh:mm) / unrecognized time " + TimeString;
            }
        }
        return null;

    }
    catch (Exception e) {
        return e.toString();
    }
    }
    private static void printErorMessageAndExit(String message) {

        System.err.println(message);
        System.err.println(USAGE_MESSAGE);
        System.exit(1);
    }

}
