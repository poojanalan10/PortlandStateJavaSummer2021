package edu.pdx.cs410J.pnalan;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Pattern;

public class SearchForAppointmentsActivity extends AppCompatActivity {

    private Button button;
    EditText ownerName_et;
    EditText start_et;
    EditText end_et;
    private static final Pattern DATE_TIME_PATTERN =
            Pattern.compile("^(0?[1-9]|1[0-2])/(0?[1-9]|1\\d|2\\d|3[01])/(19|20)\\d{2}\\s(((0?[1-9])|(1[0-2])):([0-5])([0-9])\\s[PpAa][Mm])$");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_search_for_appointments);
        ownerName_et =findViewById(R.id.searchCustomerNameEditText);
        start_et = findViewById(R.id.searchStartEditText);
        end_et = findViewById(R.id.searchEndEditText);

        button = (Button) findViewById(R.id.searchPhoneCallButton);


        button.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if(validateName() & validateStart() & validateEnd()){
                if(startTimeBeforeEndTime()){

                    String ownerNameInput = ownerName_et.getText().toString().trim();
                    String fileName =ownerNameInput + ".txt";

                    try {
                        FileInputStream fileInputStream = openFileInput(fileName);
                        InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
                        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                        StringBuilder stringBuilder = new StringBuilder();
                        String appointment;

                        while((appointment = bufferedReader.readLine()) != null){
                            stringBuilder.append(appointment).append("\n");
                        }
                        if(stringBuilder.toString().length() == 0){
                            openDialog("APPOINTMENT BOOK INFO", "No appointments exist for "+ownerNameInput);
                        }
                        else{
                            openSearchResultActivity();
                        }
                        clearAllFields();
                    } catch (FileNotFoundException e) {
                        openDialog("APPOINTMENT BOOK INFO", "No appointmentBook found for owner "+ownerNameInput);
                    } catch (IOException e) {
                        openDialog("Error", "An Unexpected error occurred!");
                    }
                }
            }
        }
        });
    }

    private boolean validateName(){
        String customerNameInput = ownerName_et.getText().toString().trim();
        if(customerNameInput.isEmpty()){
            ownerName_et.setError("Field can't be Empty");
            return false;
        }else{
            ownerName_et.setError(null);
            return true;
        }
    }

    private boolean validateStart(){
        String startInput = start_et.getText().toString().trim();
        if(startInput.isEmpty()){
            start_et.setError("Field can't be Empty");
            return false;
        }else if(!DATE_TIME_PATTERN.matcher(startInput).matches()){
            openDialog("Error", "Given start date and time are in wrong format\nPlease follow the format\nmm/dd/yyyy hh:mm am/pm\nyou entered:\n" + startInput);
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
            openDialog("Error","Given end date and time are in wrong format\nPlease follow the format\nmm/dd/yyyy hh:mm am/pm\nyou entered:\n" + endInput);
            return false;
        }else{
            end_et.setError(null);
            return true;
        }
    }

    private boolean startTimeBeforeEndTime(){
        String startInput = start_et.getText().toString().trim();
        String endInput = end_et.getText().toString().trim();
        Date startTime = getDateAndTimeInDate(startInput);
        Date endTime = getDateAndTimeInDate(endInput);
        if(!startTime.before(endTime)){
            openDialog("Error", "The end date and time should not be equal or before the start date.");
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
            openDialog("Error","date parsing error");
            return null;
        }
    }

    public void clearAllFields(){
        ownerName_et.getText().clear();
        start_et.getText().clear();
        end_et.getText().clear();
    }

    private void openSearchResultActivity() {
        Intent intent = new Intent(this, appointmentsInGivenDateRangeActivity.class);
        String customerNameInput = ownerName_et.getText().toString().trim();
        String startInput = start_et.getText().toString().trim();
        String endInput = end_et.getText().toString().trim();
        intent.putExtra("value_key", customerNameInput);
        intent.putExtra("start_key", startInput);
        intent.putExtra("end_key", endInput);
        startActivity(intent);
    }

    public void openDialog(String title, String message){
        DialogBox dialog = new DialogBox(title, message);
        dialog.show(getSupportFragmentManager(), "Alert Dialog");
    }
}