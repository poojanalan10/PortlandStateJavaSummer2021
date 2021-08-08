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
            String owner_search = intent.getStringExtra("owner_name_search");
            String start_search = intent.getStringExtra("start_datetime_search");
            String end_search = intent.getStringExtra("end_datetime_search");
            Date startDateTime = convertDateTime(start_search);
            Date endDateTime = convertDateTime(end_search);
            FileInputStream fileInputStream = null;
            try {
                String fileName = owner_search+".txt";
                fileInputStream = openFileInput(fileName);
                InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                final List<String> listOfAppointments = new ArrayList<>();
                String heading = "\n\n\n   The appointments between "+start_search+" and "+end_search+" for owner "+owner_search;
                String appointments = "";
                String current;
                String date_read;
                while((current = bufferedReader.readLine()) != null){
                    listOfAppointments.add(current);
                }
                for(String appointment: listOfAppointments) {
                    String[] args = appointment.split(",");
                    date_read = args[1];
                    Date convertedDateTime = convertDateTime(date_read);
                    if((convertedDateTime.after(startDateTime) || convertedDateTime.equals(startDateTime)) && (convertedDateTime.before(endDateTime) || convertedDateTime.equals(endDateTime))) {
                        appointments += appointment + "\n\n";
                    }
                }
                if(appointments.length() == 0){
                    appointments = "\n\n    No appointments found in the given date range "+ startDateTime + " and "+ endDateTime;
                }
                else{
                    heading += "\n\n\n    Description,StartDate, EndDate, Appointment Duration\n\n"
                            + " ------------------------------------------------------------------------------------------------\n\n";
                }
                searchResultTextView.setText(heading + "" +appointments);
            } catch (FileNotFoundException e) {
                openDialogBox("ERROR", "File for the owner doesn't exist or is corrupted!");
                e.printStackTrace();
            }  catch (IOException e) {
                openDialogBox("ERROR", "An unexpected error has occurred!");
                e.printStackTrace();
            }
        }
        public void openDialogBox(String title, String message){
         DialogBox dialog = new DialogBox(title, message);
         dialog.show(getSupportFragmentManager(), "Dialog");
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
}