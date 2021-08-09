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
public class ViewAppointmentsActivity extends AppCompatActivity {
    EditText OwnerName;
    Button button;
    String ownerNameInputEntry;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_view_appointments);
        OwnerName = findViewById(R.id.viewowneredit);
        button = (Button) findViewById(R.id.view_appointment);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validateOwnerNameEntry()) {
                    ownerNameInputEntry = OwnerName.getText().toString().trim();
                    String fileName = ownerNameInputEntry + ".txt";

                    try {
                        FileInputStream fileInputStream = null;
                        fileInputStream = openFileInput(fileName);
                        InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
                        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                        StringBuilder stringBuilder = new StringBuilder();
                        String appointment;

                        while ((appointment = bufferedReader.readLine()) != null) {
                            stringBuilder.append(appointment).append("\n");
                        }
                        if (stringBuilder.toString().length() == 0) {
                            openDialogBox("APPOINTMENT BOOK INFO", "No appointment book found for " + ownerNameInputEntry);
                        } else {
                            openViewResultActivity();
                        }
                    } catch (FileNotFoundException e) {
                        openDialogBox("ERROR FILE NOT FOUND", "No appointment book found for " + ownerNameInputEntry);
                    } catch (IOException e) {
                        openDialogBox("ERROR ", "An Unexpected error has occurred!");
                    }
                    OwnerName.getText().clear();
                }
            }
        });
    }

    public void openDialogBox(String title, String message) {
        DialogBox dialog = new DialogBox(title, message);
        dialog.show(getSupportFragmentManager(), "Dialog");
    }
    public void openViewResultActivity() {
        Intent intent = new Intent(this, GetAppointments.class);
        intent.putExtra("owner_name", ownerNameInputEntry);
        startActivity(intent);
    }

    private boolean validateOwnerNameEntry() {
        String ownerName = OwnerName.getText().toString().trim();
        if (ownerName.isEmpty()) {
            OwnerName.setError("Owner name field cannot be empty!");
            return false;
        } else {
            OwnerName.setError(null);
            return true;
        }
    }



}