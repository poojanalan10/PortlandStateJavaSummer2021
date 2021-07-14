package edu.pdx.cs410J.pnalan;

import edu.pdx.cs410J.AppointmentBookParser;
import edu.pdx.cs410J.ParserException;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TextParser implements AppointmentBookParser<AppointmentBook> {

    private static String filepath ;
    private  static BufferedReader bufferedreader;
    private static File file;
  //  private static Reader reader;



    TextParser(BufferedReader bufferedreader, String filename, Reader reader) throws IOException {
        super();
        this.filepath = new String(createFilePath(filename));
       // createNewFileAtEnteredPathIfFileDoesntExist(filename);
        this.file = new File(this.filepath);
        this.bufferedreader = bufferedreader;
       // this.bufferedreader = new BufferedReader(reader);


    }
     TextParser(BufferedReader filereader){
        super();
        this.bufferedreader = filereader;
     }


    TextParser(String filename) throws IOException{
        this.filepath = new String(createFilePath(filename));
        this.file = new File(this.filepath);

    }


    public String createFilePath(String fname) throws IOException{
        if(fname.matches("^.+?\\..*?") && !fname.matches("^.+?\\.txt")){
            throw new IllegalArgumentException("File should only have a .txt extension or can be simply given using a name");
        }
        return (fname.matches("^.+?\\.txt$") ? fname:fname+".txt");
    }

@Override
public AppointmentBook parse () throws ParserException{
    try {
    AppointmentBook result = null;
    boolean finished = false;
    AppointmentBook book = null;
    final List<String> listofAppointments = new ArrayList<>();
    String current = null;
    if(!this.bufferedreader.ready()){
        throw new ParserException("Missing owner");
    }
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
