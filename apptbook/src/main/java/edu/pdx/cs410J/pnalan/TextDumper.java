package edu.pdx.cs410J.pnalan;

import edu.pdx.cs410J.AppointmentBookDumper;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.InvalidParameterException;
import java.util.Arrays;
import java.util.regex.Pattern;

public class TextDumper implements AppointmentBookDumper<AppointmentBook> {
    private static  String filepath;

    private static  File file;
    private static  FileWriter filewriter;
    private static  Writer writer;

    /**
     * This constructor assigns the string writer and filename to the datamenbers.
     * @param filewriter
     * @param sw
     * @param filename
     * @throws IOException
     */

    TextDumper(FileWriter filewriter, Writer sw, String filename) throws IOException {
        this.filepath = new String(createFilePath(filename));
        createNewFileAtEnteredPathIfFileDoesntExist(filename);
        this.file = new File(this.filepath);
        this.writer = sw;
        this.filewriter = new FileWriter(this.file,true);


    }

    /**
     * This method throws an exception when the owner name is null or when the owner name given by the user doesn't match the name in the corresponding textfile
     * @param owner
     * @throws IOException
     */
    public void appointmentOwnerComparison(String owner) throws IOException{
        BufferedReader bf = new BufferedReader(new FileReader(this.filepath));
        String ownerName = bf.readLine();
        if((ownerName == null && owner == null) || (owner == "" && ownerName == "") || (owner == ""  && ownerName == null)){
          //  System.err.println("Owner name not found in appointmentbook and the file");
         //   System.exit(1);
            throw new InvalidParameterException("Owner name not found in appointmentbook");
        }
        else if(owner == null || owner.isEmpty()){
          //  System.err.println("Owner name not entered in the input");
            throw new InvalidParameterException("Owner name not entered in the input");
        }
        else if(ownerName != null && !owner.equals(ownerName)){
          //  System.err.println("The owner name in the file and command line do not match");
           // System.exit(1);
            throw new InvalidParameterException("The owner names in the file and command line do not match");
        }
    }

    /**
     * This method creates a file path with the file name. If a relative path is given , it creates the directory and adds the file under it and if a file doesn't exist it creates a new one or else just continue
     * @param fname
     * @throws IOException
     */
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
            if(!Files.exists(Paths.get(String.valueOf(file)))){

                System.out.println("Going to create a new file since it doen't exist");
                if (fname.matches("([^\\s]+|(\\.(?i)(txt))$)")) {
                    file.createNewFile();
                }
            }

        }
    }

    /**
     * This method creates a file path based on whether a file name is entered with .txt extension or not
     * @return
     * @throws IOException
     */
    public String createFilePath(String fname) throws IOException{
        if(fname.matches("^.+?\\..*?") && !fname.matches("^.+?\\.txt")){
           // throw new IllegalArgumentException("File should only have a .txt extension or can be simply given using a name");
            throw new IllegalArgumentException();

        }
        return (fname.matches("^.+?\\.txt$") ? fname:fname+".txt");
    }

    /**
     * This method dumps the appointment details in the appointment book to the text file
     * @param appBook
     * @throws IOException
     */
    @Override
    public void dump(AppointmentBook appBook) throws IOException {

            BufferedReader bufferedreader = new BufferedReader(new FileReader(this.filepath));
            var appointments = appBook.getAppointments();
            appointmentOwnerComparison(appBook.getOwnerName());
            String line = bufferedreader.readLine();
            if ( line == null || line.isEmpty()) {
                filewriter.write(appBook.getOwnerName() + "\n");
                BufferedReader bf = new BufferedReader(new FileReader(this.filepath));
                String owner = bf.readLine();
                this.writer.write(appBook.getOwnerName() + "\n");

                for (var appointment : appointments) {
                    filewriter.write(appointment.getDescription() + "," + appointment.getBeginDate() + "," + appointment.getBeginTimeString() + "," + appointment.getEndDate() + "," + appointment.getEndTimeString());
                    filewriter.write("\n");
                }
            } else {
                    String owner = line;
                 //   appointmentOwnerComparison(appBook.getOwnerName());
                    this.writer.write(owner);
                    for (Appointment appointment : appointments) {
                       filewriter.append(appointment.getDescription() + "," + appointment.getBeginDate() + "," + appointment.getBeginTimeString() + "," + appointment.getEndDate() + "," + appointment.getEndTimeString());
                       filewriter.append("\n");
                       System.out.println("The given appointment is added to the text file"+this.file.getAbsolutePath());

                    }
                }

            filewriter.flush();
            filewriter.close();
    }

}
