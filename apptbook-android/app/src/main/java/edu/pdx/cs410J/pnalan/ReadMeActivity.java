package edu.pdx.cs410J.pnalan;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.TextView;
public class ReadMeActivity extends AppCompatActivity {
   TextView readme;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_me);
        readme = findViewById(R.id.readme);
        readme.setText("\n\nProject 5: Pooja Nalan \n\n" +
                "This is an android application on Appointment Book\n\n" +
                "There are three options you can chose here: \n\n" +
                "1. ADD APPOINTMENT - Adds an appointment of an owner to their appointment book." +
                "Click on this button to add an appointment to an existing list of appointments or" +
                "to create a new appointment. Once you click on this button you'll be redirected to a page where you enter the following\n" +
                "details in the respective fields " +
                "The format of input is: \n" +
                "(i) owner name\n" +
                "(ii) description\n" +
                "(iii) begin date and time\n" +
                "(iv) end date and time\n" +
                "If you miss entering any of those fields you'll be prompted with an error and you'll have to enter the values\n" +
                "If you enter dates in the wrong format you'll be prompted with an error and the right format of dates in the appointment " +
                "begin date & time and the appointment end date and time.\n\n" +
                "2. VIEW APPOINTMENTS - View all the appointment given the owner name.\n" +
                "Click on this button to view all the appointments added under a given owner. Once you click this button you'll be" +
                "redirected to another page where you need to enter the owner name whose appointments you want to fetch" +
                "Once you enter the field and click on VIEW APPOINTMENT BOOK button you'll be redirected to another page" +
                "where you can view the results. If there are no appointments found for the given owner name then you'll be prompted with" +
                "a message stating the owner doesn't have any appointments.\n\n"+
                "3. VIEW APPOINTMENTS WITHIN GIVEN DATES - Search for appointments between two dates\n" +
                "On clicking this button you'll be redirected to a page that asks you to fill in the fields for the owner name, begin date of the " +
                "appointment and the end date of the appointment. On clicking the SEARCH button here you'll be redirected to another page" +
                "where you'll view all the appointments for the given owner within the date range. If you enter a malformed date you'll be prompted " +
                "with an error message. If there are no appointments in the given date range you'll be prompted with a message stating the same. If uou" +
                "miss any fields an appropriate error message will be displayed. " +
                "All of the messages for errors will pop up in a dialog box.");
    }

}