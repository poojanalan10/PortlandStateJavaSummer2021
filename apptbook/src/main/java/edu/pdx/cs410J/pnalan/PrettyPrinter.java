package edu.pdx.cs410J.pnalan;

import edu.pdx.cs410J.AppointmentBookDumper;
import edu.pdx.cs410J.ParserException;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.ParseException;
import java.util.Arrays;

public class PrettyPrinter implements AppointmentBookDumper<AppointmentBook> {

    private static String path;
    PrettyPrinter(String filename) throws IOException {
        super();
        this.path = new String(createFilePath(filename));
        createNewFileAtEnteredPathIfFileDoesntExist(this.path);

    }
    @Override
    public void dump(AppointmentBook appointmentBook) throws IOException {
        File file = new File(this.path);
        FileWriter fileWriter = new FileWriter(file);
        var number_of_appointments = appointmentBook.getAppointments().size();
        int count = number_of_appointments;
        if(file.length() == 0){
            fileWriter.write("\n***********************************************\n");
            fileWriter.write(  "\n"+"Owner : " +appointmentBook.getOwnerName()+"\n"+
                    "Number of appointments : "+ number_of_appointments +"\n"+
                    "--------------------------------------------------------------------------------------------------------------------------------------------------"+
                    String.format("%n%-35s%-35s%-35s%-35s",
                    "Description","Start Date and Time","End Date and Time","Appointment Duration"));

            fileWriter.write("\n--------------------------------------------------------------------------------------------------------------------------------------------------\n");
        }
        for(Appointment appointment:appointmentBook.getAppointments()){
            try {
                fileWriter.append("\n"+
                        String.format("%n%-35s%-35s%-35s%-35s",appointment.getDescription(), appointment.getPrettyDateTime(appointment.getBeginDate()+ " " + appointment.getBeginTimeString() )
                       ,appointment.getPrettyDateTime(appointment.getEndDate() + " " + appointment.getEndTimeString()),appointment.appointmentDuration() +"\n"));
            } catch (ParserException e) {
                e.printStackTrace();
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        fileWriter.flush();
        fileWriter.close();

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
         if (fname.contains(".txt"))
        {
             return fname;
        }
        else{
            return fname+".txt";
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
            if (!Files.exists(Paths.get(String.valueOf(file)))) {
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
}
