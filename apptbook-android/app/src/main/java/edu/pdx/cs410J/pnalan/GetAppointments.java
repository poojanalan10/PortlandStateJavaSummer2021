package edu.pdx.cs410J.pnalan;
import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

public class GetAppointments extends AppCompatActivity {
    TextView viewResult;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_appointments);
        viewResult = (TextView) findViewById(R.id.viewAppointmentBookLabel);
        Intent intent = getIntent();
        String owner_name = null;
        try {
            owner_name = intent.getStringExtra("owner_name");
            String fileName = owner_name + ".txt";
            FileInputStream fileInputStream = null;
            fileInputStream = openFileInput(fileName);
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            StringBuilder stringBuilder = new StringBuilder();
            String appointment;

            while((appointment = bufferedReader.readLine()) != null){
                stringBuilder.append(appointment).append("\n\n");
            }

            if(stringBuilder.toString().length() == 0){
                openDialogBox("APPOINTMENT BOOK INFO", "No appointment Book for "+owner_name);
            }
            else{
                viewResult.setText("\n\n"+"Owner: "+owner_name + "\n\n"
                        + "Description, Start date and time, end date and time, Appointment duration in minutes"+"\n"+
                        "-----------------------------------------------------------------------\n"+ stringBuilder.toString());
            }
            Button backbutton = findViewById(R.id.backtoviewappointments);
            backbutton.setOnClickListener(view -> backToPreviousPage());
            Button backtomainbutton = findViewById(R.id.backtomainmenu);
            backtomainbutton.setOnClickListener(view -> sendNavBackToMain());

        } catch (FileNotFoundException e) {
            openDialogBox("File not found or corrupted for the given owner name!", "No appointments exist for "+owner_name);
            e.printStackTrace();
        } catch (IOException  e) {
            e.printStackTrace();
        }

    }
    public void backToPreviousPage(){
        Intent intent = new Intent(this,ViewAppointmentsActivity.class);
        startActivity(intent);

    }
    public void sendNavBackToMain(){
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
    }
    public void openDialogBox(String title_of_dialog, String message){
        DialogBox dialog = new DialogBox(title_of_dialog, message);
        dialog.show(getSupportFragmentManager(), "Dialog");
    }
}