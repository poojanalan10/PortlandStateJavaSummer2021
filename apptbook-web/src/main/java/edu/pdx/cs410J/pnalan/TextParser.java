package edu.pdx.cs410J.pnalan;

import edu.pdx.cs410J.AppointmentBookParser;
import edu.pdx.cs410J.ParserException;
import org.apache.tools.ant.util.ReaderInputStream;
import org.checkerframework.checker.units.qual.A;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;


public class TextParser implements AppointmentBookParser<AppointmentBook> {
    private final Reader reader;
    private  static BufferedReader bufferedreader;
    public TextParser(Reader reader) throws IOException {

        this.reader = reader;



    }
    @Override
    public AppointmentBook parse() throws ParserException {
        AppointmentBook book = null;

        BufferedReader bufferedreader = new BufferedReader(this.reader);
        try {
            if (!bufferedreader.ready()) {
                throw new ParserException("Missing owner");
            } else {
                String owner = bufferedreader.readLine();
                book = new AppointmentBook(owner);
                for (String line = bufferedreader.readLine(); line != null; line = bufferedreader.readLine()) {
                    String[] args = line.split(",");
                    String description = args[0];
                    String startTimeString = args[1];
                    String endTimeString = args[2];
                    book.addAppointment(new Appointment(description, startTimeString, endTimeString));
                }
                return book;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
    public static String validateTime(String dateString){
        String dateregex = "^(1[0-2]|0[1-9])/(3[01]|[12][0-9]|0[1-9])/[0-9]{4}\\s(([0-1]?[0-9]|2[0-3]):[0-5][0-9]\\s[PpAa][Mm])$";
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
}
