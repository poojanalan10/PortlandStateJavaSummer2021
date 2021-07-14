package edu.pdx.cs410J.pnalan;

import edu.pdx.cs410J.AppointmentBookDumper;

import java.io.*;
import java.security.InvalidParameterException;
import java.util.Arrays;

public class TextDumper implements AppointmentBookDumper<AppointmentBook> {
    private static  String filepath;

    private static  File file;
    private static  FileWriter filewriter;
    private static  Writer writer;


   /* TextDumper(String filename, Writer writer){
        super();
        if(filename.contains(".txt")){
            filepath = new String(filename);
        }
        else {
            filepath = new String(filename) + ".txt";
        }
        this.writer = writer;
    }*/
    TextDumper(FileWriter filewriter, Writer sw, String filename) throws IOException {
        this.filepath = new String(createFilePath(filename));
        createNewFileAtEnteredPathIfFileDoesntExist(filename);
        this.file = new File(this.filepath);
        this.writer = sw;
        this.filewriter = filewriter;


    }
   /* TextDumper(String filename){
        super();
        try {
            this.filepath = new String(createFilePath(filename));
            createNewFileAtEnteredPathIfFileDoesntExist(filename);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }*/
    public void appointmentOwnerComparison(String owner) throws IOException{
        BufferedReader bf = new BufferedReader(new FileReader(this.filepath));
        String ownerName = bf.readLine();
        if(ownerName == null && owner == null){
            throw new InvalidParameterException("Owner name not found in appointmentbook");
        }
        else if(ownerName != null && !owner.equals(ownerName)){
            throw new InvalidParameterException("The owner names in the file and command line do not match");
        }
    }
    public void createNewFileAtEnteredPathIfFileDoesntExist(String fname) throws IOException {
        File file = new File(fname);
        File mkdir = null;
        if (fname.contains("/")) {
            String regex = "/" + file.getName();
            var directorytocreate = Arrays.asList(fname.split(regex));
            mkdir = new File(directorytocreate.get(0));
            if (!mkdir.exists() || mkdir == null) {
                mkdir.mkdirs();
            }
            if (!file.exists()) {
                if (fname.matches("([^\\s]+|(\\.(?i)(txt))$)")) {
                    file.createNewFile();
                }
            }
        } else {
            if (!file.isFile()) {
                if (fname.matches("([^\\s]+|(\\.(?i)(txt))$)")) {
                    file.createNewFile();
                }
            }

        }
    }

    public String createFilePath(String fname) throws IOException{
        if(fname.matches("^.+?\\..*?") && !fname.matches("^.+?\\.txt")){
            throw new IllegalArgumentException("File should only have a .txt extension or can be simply given using a name");
        }
        return (fname.matches("^.+?\\.txt$") ? fname:fname+".txt");
    }
    @Override
    public void dump(AppointmentBook appBook) throws IOException {

           // File file = new File(filepath);
           // FileWriter filewriter = new FileWriter(file,true);
            BufferedReader bufferedReader = null;
            var appointments = appBook.getAppointments();
            if (this.file.length() == 0) {
                filewriter.write(appBook.getOwnerName() + "\n");
               // bufferedReader = new BufferedReader(new FileReader(this.filepath));
              //  String owner = bufferedReader.readLine();
                writer.write(appBook.getOwnerName() + "\n");
                for (var appointment : appointments)
                   filewriter.write(appointment.getDescription() + "," + appointment.getBeginDate() + "," + appointment.getBeginTimeString() + "," + appointment.getEndDate() + "," + appointment.getEndTimeString());
                   filewriter.write("\n");
            } else {
                    bufferedReader = new BufferedReader(new FileReader(this.filepath));
                    String owner = bufferedReader.readLine();
                    appointmentOwnerComparison(appBook.getOwnerName());
                    writer.write(owner);
                    for (Appointment appointment : appointments) {
                       filewriter.append(appointment.getDescription() + "," + appointment.getBeginDate() + "," + appointment.getBeginTimeString() + "," + appointment.getEndDate() + "," + appointment.getEndTimeString());
                       filewriter.append("\n");
                       System.out.println("The given appointment is added to the text file"+this.file.getAbsolutePath());

                    }
                }

            filewriter.flush();
            filewriter.close();
    }


    /*public void createFile(String filepath){
        try {
            this.file = new File(filepath);
            File theDir = new File(filepath);
            if(!theDir.exists()) {
                if (filepath.contains("/")) {
                    // var directory = Arrays.asList(filepath.split("/"));
                    theDir.mkdir();
                }
            }
            this.file.createNewFile();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }*/
}
