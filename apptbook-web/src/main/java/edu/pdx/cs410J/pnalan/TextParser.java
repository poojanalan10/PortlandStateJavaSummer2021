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
     //   this.bufferedreader = new BufferedReader(this.reader);
      //  this.bufferedreader = new BufferedReader(new InputStreamReader(new ReaderInputStream(this.reader)));
      //  String owner = bufferedreader.readLine();


    }
    @Override
    public AppointmentBook parse() throws ParserException {
        AppointmentBook book = null;
      //  final List<String> listofAppointments = new ArrayList<>();
     //   String current = null;
    //    AppointmentBook result = null;
        BufferedReader bufferedreader = new BufferedReader(this.reader);
        try {
            if (!bufferedreader.ready()) {
                throw new ParserException("Missing owner");
            } else {
                String owner = bufferedreader.readLine();
                //  String line = bufferedreader.readLine();
                book = new AppointmentBook(owner);
                for (String line = bufferedreader.readLine(); line != null; line = bufferedreader.readLine()) {

                    // }
             /*   while (line != null && !line.isEmpty()) {
                    current = line;
                    listofAppointments.add(current);
                    line = bufferedreader.readLine();
                }*/
              /*  if (listofAppointments.isEmpty()) {
                    //  finished = true;
                    result = book;
                } else {*/
                    //  for (String appointment : listofAppointments) {
                   // System.out.println(line);
                    String[] args = line.split(",");
                    String description = args[0];
                    String startTimeString = args[1];
                    //validateTime(args[2]);
                    String endTimeString = args[2];
                    //validateTime(args[4]);

                    //book.addAppointment(new Appointment(args[0],args[1],args[2],args[3],args[4]));
                    book.addAppointment(new Appointment(description, startTimeString, endTimeString));
                }
                return book;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


        //     result = book;


     /*   } catch (IOException ioException) {
            ioException.printStackTrace();
        }

        return result;*/
       // BufferedReader br = new BufferedReader(this.reader);
   /*     String owner = "";
        try {
             owner = br.readLine();
            appointmentBook = new AppointmentBook(owner);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String current = null;
        try {
            while((current = br.readLine())!=null){
                listOfAppointments.add(current);
            }
            if(listOfAppointments == null){
                return appointmentBook;
            }
            int count = 0;
            for(String app:listOfAppointments){
                if(app ==listOfAppointments.get(0)){
                    appointmentBook = new AppointmentBook(app);
                }
                else{
                    if(count != 0){
                        String[] args = app.split(",");
                        String description = args[0];
                        String startTimeString = validateTime(args[2]);
                        String endTimeString = validateTime(args[4]);

                        //book.addAppointment(new Appointment(args[0],args[1],args[2],args[3],args[4]));
                        appointmentBook.addAppointment(new Appointment(description,startTimeString,endTimeString));
                    }
                    count++;
                }
            }
            return appointmentBook;
        }
        catch (Exception e){
            System.out.println(e);
        }*/
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
