package edu.pdx.cs410J.pnalan;

import edu.pdx.cs410J.AppointmentBookParser;
import edu.pdx.cs410J.ParserException;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class TextParser implements AppointmentBookParser<AppointmentBook> {

    private static String filepath ;
    private  static BufferedReader bufferedreader;
    private static File file;
 //   private static Reader reader;



    TextParser(String filename, Reader reader) {
        super();
        if(filename.contains(".txt")){
            filepath = new String(filename);
        }
        else {
            filepath = new String(filename) + ".txt";
        }
        try {
            this.file = new File(this.filepath);
            this.bufferedreader = new BufferedReader(new FileReader(file));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
     TextParser(BufferedReader filereader){
        super();
        this.bufferedreader = filereader;
     }


    TextParser(String filename){
        if(filename.contains(".txt")){
            filepath = new String(filename);
        }
        else {
            filepath = new String(filename) + ".txt";
        }
        this.file = new File(this.filepath);

       /* try {
            this.bufferedreader = new BufferedReader(new FileReader(this.file));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }*/

    }


@Override
public AppointmentBook parse () throws ParserException{
    try {
    AppointmentBook result = null;
    boolean finished = false;
    AppointmentBook book = null;
    final List<String> listofAppointments = new ArrayList<>();
    if(!this.bufferedreader.ready()){
        throw new ParserException("Missing owner");
    }
   // File file = new File(filepath);
    this.bufferedreader = new BufferedReader(new FileReader(file));
    String current = null;
    if(!file.exists()){
        return null;
    }

   // br = new BufferedReader(new FileReader(file));
    String owner = this.bufferedreader.readLine();
    String line = this.bufferedreader.readLine();
    book = new AppointmentBook(owner);
    while (line != null && !line.isEmpty()) {
        current = line;
        listofAppointments.add(current);
        line = this.bufferedreader.readLine();
        }
    if (listofAppointments.isEmpty()) {
        finished = true;
        result = book;
    } else {
        for (String appointment : listofAppointments) {
            String[] args = appointment.split(",");
            book.addAppointment(new Appointment(args[0],args[1],args[2],args[3],args[4]));
        }
    }
    if (!finished) {
        result = book;
    }

    return result;
    } catch (IOException e) {
        throw new ParserException("while parsing",e);
    }

    }
}
