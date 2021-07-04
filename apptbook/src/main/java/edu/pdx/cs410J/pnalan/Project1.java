package edu.pdx.cs410J.pnalan;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * The main class for the CS410J appointment book Project
 */
public class Project1 {
  static final String README = "\n README \n Name: Pooja Nalan \n Project : AppClasses \nThis project adds an appointment to an appointment book +\n" + "belonging to a particular owner ";
  private static final String arguments = "<owner> <description> <begin> <end> \n";
  /**
   * @param args
   *        [Options] <arguments>
   *        [-README -print] "<owner> <description> <start time> <end time>
   *
   * 3 cases can happen in this main method
   * <var>length</var> = length of command line Arguments
   * CASE 1: length = 0
   * CASE 2: length <= 6
   *          [Sub case's]
   *          CASE i:   -README option present in given arguments as 1st or 2nd Argument
   *          CASE ii:  NO -README but -print Option is given as 1st Argument
   *          CASE iii: NO Options Provided
   * CASE 3: length > 6
   * */
  public static void main(String[] args) {
    List<String> argumentList = Arrays.asList(args);
    List<String> userInput = new ArrayList<>();
    //String owner = userInput.get(1);
    //String description = userInput.get(2);
    //String beginTimeString = userInput.get(3);
   // String endTimeString = userInput.get(4);
    //Appointment appointment = new Appointment(args);
    var length = args.length;

    if(args.length == 0){
      System.err.println("Missing command line arguments");
      System.err.println("---Valid Input---");
      System.err.println("[options] <args>\n" +
              "[options] = [-print -README]\n" +
              "<args> are in this order:\n" +
              arguments+ "The <args> should be given in String.\n");

      System.exit(1);
    }
    /*
     * CASE 2
     * */

    else if (length <= 6) {
      /**
       * If the user enters a README option but all data is not inputted
       */
      if (argumentList.contains("-README") & argumentList.indexOf("-README") < 2 ) {
        System.out.println(README);
        System.exit(1);
      }
      /**
       * If the user enters a print option but all data is not inputted
       */
      else if(argumentList.contains("-print") & argumentList.indexOf("-print") < 2 ){
       System.err.println("Cannot print due to missing arguments.");
      }
      /**
       * If no options are entered by the user and the argument list is incomplete
       */
      else{
        System.err.println("The input information is incomplete!");
      }
      System.exit(1);
    }

      if(args.length !=0 && args[0].equals("README")){
      System.out.println(README);

      } else {
       //System.out.println()
      }



    if(args.length == 7 & argumentList.contains("-print") & argumentList.indexOf("-print") == 0){
      Appointment app = new Appointment(args);
      AppointmentBook appBook = new AppointmentBook(app);
      //System.out.println(args[1]);
      System.out.println("Appointment information");
      System.out.println(app.toString());
      System.exit(0);

    }
    for (String arg : args) {
      System.out.println(arg);
    }
    System.exit(1);
  }

}
