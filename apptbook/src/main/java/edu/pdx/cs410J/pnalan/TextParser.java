package edu.pdx.cs410J.pnalan;

import edu.pdx.cs410J.AppointmentBookParser;
import edu.pdx.cs410J.ParserException;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class TextParser implements AppointmentBookParser<AppointmentBook> {

    private static String filepath ;
    private  static BufferedReader bufferedreader;
    private static File file;
  //  private static Reader reader;


    /**
     * This constructor assigns values to the data members
     * @param bufferedreader
     * @param filename
     * @param reader
     * @throws IOException
     */
    TextParser(BufferedReader bufferedreader, String filename, Reader reader) throws IOException {
        super();
        this.filepath = new String(createFilePath(filename));
       // createNewFileAtEnteredPathIfFileDoesntExist(filename);
        this.file = new File(this.filepath);
      //  this.bufferedreader = bufferedreader;
        this.bufferedreader = new BufferedReader(new FileReader(this.file));
       // this.bufferedreader = new BufferedReader(reader);


    }
   /*  TextParser(BufferedReader filereader){
        super();
        this.bufferedreader = filereader;
     }


    TextParser(String filename) throws IOException{
        this.filepath = new String(createFilePath(filename));
        this.file = new File(this.filepath);

    }
*/

    /**
     * This method creates a text file name based on ow its inputted with or without extension
     * @param fname
     * @return
     * @throws IOException
     */
    public String createFilePath(String fname) throws IOException{
        if(fname.matches("^.+?\\..*?") && !fname.matches("^.+?\\.txt")){
            throw new IllegalArgumentException("File should only have a .txt extension or can be simply given using a name");
        }
        return (fname.matches("^.+?\\.txt$") ? fname:fname+".txt");
    }

    /**
     * This method parses the appointments, reads from the text file, adds to the appointment book and returns the appointment book object
     * @return
     * @throws ParserException
     */
    @Override
public AppointmentBook parse () throws ParserException{
    try {
    AppointmentBook result = null;
   // boolean finished = false;
    AppointmentBook book = null;
    final List<String> listofAppointments = new ArrayList<>();
    String current = null;
    if(!this.bufferedreader.ready()){
       // System.out.println(this.bufferedreader.readLine());
       // System.err.println("Missing owner");
        throw new ParserException("Missing owner");


    }
    else {
        String owner = this.bufferedreader.readLine();
        String line = this.bufferedreader.readLine();
        book = new AppointmentBook(owner);
        while (line != null && !line.isEmpty()) {
            current = line;
            listofAppointments.add(current);
            line = this.bufferedreader.readLine();
        }
        if (listofAppointments.isEmpty()) {
            //  finished = true;
            result = book;
        } else {
            for (String appointment : listofAppointments) {
                String[] args = appointment.split(",");
                String description = args[0];
                String startDateString = validateDate(args[1]);
                String startTimeString = validateTime(args[2]);
                String endDateString = validateDate(args[3]);
                String endTimeString = validateTime(args[4]);
                //book.addAppointment(new Appointment(args[0],args[1],args[2],args[3],args[4]));
                book.addAppointment(new Appointment(description, startDateString, startTimeString, endDateString, endTimeString));
            }
        }

        //  if (!finished) {
        result = book;
        // }
    }
    return result;
    } catch (IOException e) {

        throw new ParserException("while parsing",e);
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
                System.err.println("UNRECOGNIZED DATE FORMAT: "+dateString+" found in the file!");
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
        String timeregex = "^([0-1]?[0-9]|2[0-3]):[0-5][0-9]$";
        if(TimeString != null) {
            if (Pattern.matches(timeregex, TimeString)) {
                return TimeString;
            } else {
                System.err.println("UNRECOGNIZED TIME FORMAT "+TimeString+ " found in the file!");
                System.exit(1);
            }
        }
        return null;
    }
}
