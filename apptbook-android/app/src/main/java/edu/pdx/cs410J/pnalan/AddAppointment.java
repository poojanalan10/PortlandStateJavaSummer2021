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
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Pattern;

public class AddAppointment extends AppCompatActivity {
    private static final Pattern DATE_TIME_PATTERN =
            Pattern.compile("^(0?[1-9]|1[0-2])/(0?[1-9]|1\\d|2\\d|3[01])/(19|20)\\d{2}\\s(((0?[1-9])|(1[0-2])):([0-5])([0-9])\\s[PpAa][Mm])$");

    EditText OwnerName_et;
    EditText description_et;
    EditText start_et;
    EditText end_et;
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_appointment);
        OwnerName_et = findViewById(R.id.owneredit);
        description_et = findViewById(R.id.descriptionedit);
        start_et = findViewById(R.id.startedit);
        end_et = findViewById(R.id.endedit);
        button = (Button) findViewById(R.id.add_appointment);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validateName() & validateStart() & validateEnd()) {
                    if (startTimeBeforeEndTime()) {
                        String ownerNameInput = OwnerName_et.getText().toString().trim();
                        String descriptionInput = description_et.getText().toString().trim();
                        String startInput = start_et.getText().toString().trim();
                        String endInput = end_et.getText().toString().trim();

                        Date startTime = getDateAndTimeInDate(startInput);
                        Date endTime = getDateAndTimeInDate(endInput);
                        String prettyStartTime = getPrettyDateTime(startInput);
                        String prettyEndTime = getPrettyDateTime(endInput);

                        Appointment appointment = new Appointment(descriptionInput,startInput,endInput);
                        AppointmentBook appointmentBook = new AppointmentBook(ownerNameInput,appointment);
                        String fileName = ownerNameInput + ".txt";
                        String appointmentData = appointment.getDescription()
                                + "," + prettyStartTime
                                + "," + prettyEndTime
                                + "," + appointment.appointmentDuration() + "\n";
                        FileOutputStream fileOutputStream = null;
                        try {
                            fileOutputStream = openFileOutput(fileName, MODE_APPEND);
                            fileOutputStream.write(appointmentData.getBytes());
                            clearAllFields();

                        } catch (FileNotFoundException ex) {
                            openDialogBox("ERROR", "The given file "+fileName+", does not exists");
                        } catch (IOException e) {
                            openDialogBox("ERROR", "Error when writing to the file");
                        } finally {
                            if (fileOutputStream != null) {
                                try {
                                    fileOutputStream.close();
                                    // fileInputStream.close();
                                } catch (IOException e) {
                                    openDialogBox("ERROR", "There was an unexpected error!");
                                }
                            }
                        }
                        openDialogBox("APPOINTMENT INFORMATION", "Appointment has been added successfully");
                    }
                }
            }
        });

    }
    private boolean validateName(){
        String ownerName = OwnerName_et.getText().toString().trim();
        if(ownerName.isEmpty()){
            OwnerName_et.setError("Owner name field cannot be empty!");
            return false;
        }else{
            OwnerName_et.setError(null);
            return true;
        }
    }

    private boolean validateStart(){
        String startInput = start_et.getText().toString().trim();
        if(startInput.isEmpty()){
            start_et.setError("Start Date/Time can't be Empty");
            return false;
        }else if(!DATE_TIME_PATTERN.matcher(startInput).matches()){
            openDialogBox("Error", "Given start date and time are in wrong format\nPlease follow the format\nmm/dd/yyyy hh:mm am/pm\nyou entered:\n" + startInput);
            return false;
        }else{
            start_et.setError(null);
            return true;
        }
    }

    private boolean validateEnd(){
        String endInput = end_et.getText().toString().trim();
        if(endInput.isEmpty()){
            end_et.setError("Field can't be Empty");
            return false;
        }else if(!DATE_TIME_PATTERN.matcher(endInput).matches()){
            openDialogBox("Error","Given end date and time are in wrong format\nPlease follow the format\nmm/dd/yyyy hh:mm am/pm\nyou entered:\n" + endInput);
            return false;
        }else{
            end_et.setError(null);
            return true;
        }
    }

    public void openDialogBox(String t, String s){
        DialogBox dialog = new DialogBox(t, s);
        dialog.show(getSupportFragmentManager(), "Alert Dialog");
    }
    private boolean startTimeBeforeEndTime(){
        String startInput = start_et.getText().toString().trim();
        String endInput = end_et.getText().toString().trim();
        Date startTime = getDateAndTimeInDate(startInput);
        Date endTime = getDateAndTimeInDate(endInput);
        if(!startTime.before(endTime)){
            openDialogBox("Error", "The end date and time should not be equal or before the start date.");
            return false;
        }
        return true;
    }

    public Date getDateAndTimeInDate(String dateTime) {
        try {
            SimpleDateFormat formatter1 = new SimpleDateFormat("MM/dd/yyyy hh:mm a");
            Date date1 = formatter1.parse(dateTime);
            return date1;
        } catch (ParseException e){
            openDialogBox("Error","Something went wrong, please try after some time");
            return null;
        }
    }

    public String getPrettyDateTime(String dateTime){
        String pattern = "MM/dd/yy HH:mm a";
        String sdate= dateTime;
        DateFormat df = new SimpleDateFormat(pattern);
        Date date = null;
        try {
            date = df.parse(sdate);
        } catch (ParseException e) {
            openDialogBox("Error","Something went wrong, please try after some time");
        }
        String dateTimePrettyFormat = df.format(date);
        return dateTimePrettyFormat;
    }

    public void clearAllFields(){
        OwnerName_et.getText().clear();
        description_et.getText().clear();
        start_et.getText().clear();
        end_et.getText().clear();
    }

}
