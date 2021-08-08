package edu.pdx.cs410J.pnalan;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class appointmentsInGivenDateRangeActivity extends AppCompatActivity {
    TextView searchResultTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_appointments_in_given_date_range);
            searchResultTextView = findViewById(R.id.appointmentsInGivenRangeSearchResult);
            Intent intent = getIntent();
            String owner = intent.getStringExtra("value_key");
            String startInput = intent.getStringExtra("start_key");
            Date startTime = convertDateTime(startInput);
            String endInput = intent.getStringExtra("end_key");
            Date endTime = convertDateTime(endInput);
            String fileName = owner+".txt";

            FileInputStream fileInputStream = null;
            try {
                fileInputStream = openFileInput(fileName);
                InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
                BufferedReader fileData = new BufferedReader(inputStreamReader);
                final List<String> listOfAppointments = new ArrayList<>();
                String strCurrentLine;
                String contentHead = "The appointments between "+startInput+" and "+endInput+" for owner "+owner;
                String content = "";
                while((strCurrentLine = fileData.readLine()) != null){
                    listOfAppointments.add(strCurrentLine);
                }
                for(String appointment: listOfAppointments) {
                    String[] args = appointment.split(", ");
                    System.out.println(args.length);
                    String datefile = args[1];
                    Date dateFromFile = convertDateTime(datefile);
                    if((dateFromFile.after(startTime) || startTime.equals(dateFromFile)) && (dateFromFile.before(endTime) || endTime.equals(dateFromFile))) {
                        content += appointment + "\n\n";
                    }
                }
                if(content.length() == 0){
                    content = "\n\nNo appointments found in the given date range";
                }
                else{
                    contentHead += "\n\nDescription,StartDate, EndDate, Appointment Duration\n"
                            + "-----------------------------------------------------------------------------------\n\n";
                }
                searchResultTextView.setText(contentHead + "" +content);
            } catch (FileNotFoundException e) {
                openDialogBox("ERROR", "File Does not exist");
                e.printStackTrace();
            }  catch (IOException e) {
                openDialogBox("ERROR", "An unexpected error has occurred!");
                e.printStackTrace();
            }
        }

        public Date convertDateTime(String datestring) {
            try {
                SimpleDateFormat formatter1 = new SimpleDateFormat("MM/dd/yy hh:mm a");
                Date date1 = formatter1.parse(datestring);
                return date1;
            /*    Date date = new Date();
                DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy hh:mm a");
                date = dateFormat.parse(datestring);
                String strDate = DateFormat.getDateInstance(DateFormat.SHORT).format(date);
                String strTime = DateFormat.getTimeInstance(DateFormat.SHORT).format(date);
                date = dateFormat.parse(strDate + " " + strTime, new ParsePosition(0));
                return date;*/
            } catch (ParseException e){
                openDialogBox("DATE PARSE ERROR","Date time cannot be parsed :" + datestring);
                return null;
            }
        }

        public void openDialogBox(String title, String message){
            DialogBox dialog = new DialogBox(title, message);
            dialog.show(getSupportFragmentManager(), "Dialog");
        }

}