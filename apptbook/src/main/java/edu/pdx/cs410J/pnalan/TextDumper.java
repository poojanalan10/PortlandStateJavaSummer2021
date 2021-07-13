package edu.pdx.cs410J.pnalan;

import edu.pdx.cs410J.AppointmentBookDumper;

import java.io.*;
import java.security.InvalidParameterException;

public class TextDumper implements AppointmentBookDumper<AppointmentBook> {
    private static String filepath;
    private static File file;
    private static FileWriter filewriter;
    private final Writer writer;


    TextDumper(String filename, Writer writer){
        super();
        filepath = new String(filename) + ".txt";
        this.writer = writer;
    }
    TextDumper(FileWriter writer, Writer sw, String filename){
        this.filepath =  new String(filename) + ".txt";;
        this.file = new File(this.filepath);
        this.writer = sw;
        try {
            this.filewriter = new FileWriter(this.file,true);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    @Override
    public void dump(AppointmentBook appBook) throws IOException {
         //   File file = new File(this.filepath);
            //this.filewriter = new FileWriter(file,true);
            BufferedReader bufferedReader = null;
            var appointments = appBook.getAppointments();
            if(!this.file.exists()){
                createFile(this.filepath);
            }
            else {
                if (file.length() == 0) {
                    filewriter.write(appBook.getOwnerName() + "\n");
                    bufferedReader = new BufferedReader(new FileReader(this.filepath));
                    String owner = bufferedReader.readLine();
                    writer.write(owner);
                    for (var appointment : appointments)
                        filewriter.write(appointment.getDescription() + "," + appointment.getBeginDate() + "," + appointment.getBeginTimeString() + "," + appointment.getEndDate() + "," + appointment.getEndTimeString());
                    filewriter.write("\n");
                } else {

                    bufferedReader = new BufferedReader(new FileReader(filepath));
                    String owner = bufferedReader.readLine();
                    writer.write(owner);
                    if (owner == null) {
                        throw new InvalidParameterException("Owner name is missing in the command line arguments.");
                    } else if (!owner.equalsIgnoreCase(appBook.getOwnerName())) {
                        throw new InvalidParameterException("The owner name doesn't match the name in the records.");
                    }
              /*  else {
                    filewriter.append(appBook.getOwnerName() + "\n " + " description " + "," + "Appointment begin date and time " + "," + "Appointment end date and time");
                }*/


                    for (Appointment appointment : appointments) {
                        // filewriter.append("\n" + app.getDescription() + "," + app.getBeginTimeString() + "," + app.getEndTimeString());
                        //filewriter.append(app.toString());
                        filewriter.append(appointment.getDescription() + "," + appointment.getBeginDate() + "," + appointment.getBeginTimeString() + "," + appointment.getEndDate() + "," + appointment.getEndTimeString());
                        filewriter.append("\n");
                        System.out.println("The given appointment is added to the text file");

                    }
                }
            }
            filewriter.flush();
            filewriter.close();
    }


    public void createFile(String filepath){
        try {
            this.file = new File(filepath);
            File theDir = new File(filepath);
            if(!theDir.exists()) {
                if (filepath.contains("/")) {
                    // var directory = Arrays.asList(filepath.split("/"));
                    theDir.mkdir();
                }
            }
            if(!file.exists()){
                file.createNewFile();
            }
         /*   else if(file.length() == 0){
                throw new InvalidParameterException("The given customer has no appointments");
            }
*/
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
