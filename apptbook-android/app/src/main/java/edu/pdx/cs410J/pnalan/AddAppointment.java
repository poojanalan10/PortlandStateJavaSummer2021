package edu.pdx.cs410J.pnalan;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Pattern;

public class AddAppointment extends AppCompatActivity {
    private static final Pattern DATE_PATTERN =
            Pattern.compile("^(0?[1-9]|1[0-2])/(0?[1-9]|1\\d|2\\d|3[01])/(19|20)\\d{2}\\s(((0?[1-9])|(1[0-2])):([0-5])([0-9])\\s[PpAa][Mm])$");
    EditText OwnerName;
    EditText description;
    EditText start;
    EditText end;
    private Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_appointment);
        OwnerName = findViewById(R.id.owneredit);
        description = findViewById(R.id.descriptionedit);
        start = findViewById(R.id.startedit);
        end = findViewById(R.id.endedit);
        button = (Button) findViewById(R.id.add_appointment);
        button.setOnClickListener(view -> addAnAppointment());
    }
    public void addAnAppointment(){
            if (validateOwnerNameEntry() & validateDescriptionEntry() & validateDateAndTime(start) & validateDateAndTime(end)) {
                if (checkIfStartBeforeEndDateTime()) {
                    String ownerNameEntry = OwnerName.getText().toString().trim();
                    String descriptionEntry = description.getText().toString().trim();
                    String startDateTimeEntry = start.getText().toString().trim();
                    String endDateTimeEntry = end.getText().toString().trim();
                    String startDateTime = convertDateTime(startDateTimeEntry).toString();
                    String endDateTime = convertDateTime(endDateTimeEntry).toString();
                    Appointment appointment = new Appointment(descriptionEntry,startDateTimeEntry,endDateTimeEntry);
                    String fileName = ownerNameEntry + ".txt";
                    String appointmentData = appointment.getDescription() + "," + startDateTime + "," + endDateTime + "," + appointment.appointmentDuration() + "\n";
                    FileOutputStream fileOutputStream = null;
                    try {
                        fileOutputStream = openFileOutput(fileName, MODE_APPEND);
                        fileOutputStream.write(appointmentData.getBytes());
                        clearAllFields();
                    } catch (IOException e) {
                        openDialogBox("ERROR", "An unexpected error occurred when writing to the file!");
                    } finally {
                        if (fileOutputStream != null) {
                            try {
                                fileOutputStream.close();
                            } catch (IOException e) {
                                openDialogBox("ERROR", "There was an unexpected error!");
                            }
                        }
                    }
                    openDialogBox("APPOINTMENT INFORMATION", "Appointment has been added successfully");
                }
            }
        }

    private boolean validateOwnerNameEntry(){
        String ownerName = OwnerName.getText().toString().trim();
        if(ownerName.isEmpty()){
            OwnerName.setError("Owner name field cannot be empty!");
            return false;
        }else{
            OwnerName.setError(null);
            return true;
        }
    }
    private boolean validateDescriptionEntry(){
        String descriptionEntry = description.getText().toString().trim();
        if(descriptionEntry.isEmpty()){
            description.setError("Description field cannot be empty!");
            return false;
        }else{
            description.setError(null);
            return true;
        }
    }

    private boolean validateDateAndTime(EditText DateTimeEntry){
        String DateTimeEntryString = DateTimeEntry.getText().toString().trim();
        if(DateTimeEntryString.isEmpty()){
            start.setError("Date/Time cannot be empty!");
            return false;
        }else if(!DATE_PATTERN.matcher(DateTimeEntryString).matches()){
            openDialogBox("ERROR", "The entered date and time are not in the expected format:\n\nmm/dd/yyyy hh:mm am/pm\nyou have entered:\n" + DateTimeEntryString);
            return false;
        }else{
            start.setError(null);
            return true;
        }
    }

    public void openDialogBox(String title, String message){
        DialogBox dialog = new DialogBox(title, message);
        dialog.show(getSupportFragmentManager(), "Dialog");
    }
    private boolean checkIfStartBeforeEndDateTime(){
        String startDateTimeEntry = start.getText().toString().trim();
        String endDateTimeEntry = end.getText().toString().trim();
        Date startDateTime = getDateAndTimeDateObject(startDateTimeEntry);
        Date endDateTime = getDateAndTimeDateObject(endDateTimeEntry);
        if(!startDateTime.before(endDateTime)){
            openDialogBox("ERROR", "The start date/time cannot be greater than the end date/time!");
            return false;
        }
        return true;
    }

    public Date getDateAndTimeDateObject(String dateTime) {
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy hh:mm a");
            Date date = simpleDateFormat.parse(dateTime);
            return date;
        } catch (ParseException e){
            openDialogBox("ERROR","An Error occurred while trying to parse the date!");
            return null;
        }
    }
    public Date convertDateTime(String datestring) {
        try {
            Date date = new Date();
            DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy hh:mm a");
            date = dateFormat.parse(datestring);
            String strDate = DateFormat.getDateInstance(DateFormat.SHORT).format(date);
            String strTime = DateFormat.getTimeInstance(DateFormat.SHORT).format(date);
            date = dateFormat.parse(strDate + " " + strTime, new ParsePosition(0));
            return date;
        } catch (ParseException e){
            openDialogBox("DATE PARSE ERROR","The given date time cannot be parsed " + datestring + " Enter date and time in the format "
                    + "MM/dd/yyyy hh:mm a");
            return null;
        }
    }

    public void clearAllFields(){
        OwnerName.getText().clear();
        description.getText().clear();
        start.getText().clear();
        end.getText().clear();
    }

}
