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
    EditText ownerName_et;
    Button button;
    String ownerNameInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_view_appointments);

        ownerName_et =findViewById(R.id.viewowneredit);
        button = (Button) findViewById(R.id.view_appointment);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(validateName()){
                    ownerNameInput = ownerName_et.getText().toString().trim();
                    String fileName = ownerNameInput + ".txt";

                    try {
                        FileInputStream fileInputStream = null;
                        fileInputStream = openFileInput(fileName);
                        InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
                        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                        StringBuilder stringBuilder = new StringBuilder();
                        String appointment;

                        while((appointment = bufferedReader.readLine()) != null){
                            stringBuilder.append(appointment).append("\n");
                        }
                        if(stringBuilder.toString().length() == 0){
                            openDialogBox("APPOINTMENT BOOK INFO", "No appointment book found for "+ownerNameInput);
                        }
                        else{
                            openViewResultActivity();
                        }
                    } catch (FileNotFoundException e) {
                        openDialogBox("ERROR FILE NOT FOUND", "No appointment book found for "+ownerNameInput);
                        //e.printStackTrace();
                    } catch (IOException e) {
                        openDialogBox("ERROR ", "An Unexpected error has occurred!");
                        //e.printStackTrace();
                    }
                    ownerName_et.getText().clear();
                }
            }
        });
    }

    public void openViewResultActivity(){
        Intent intent = new Intent(this, GetAppointments.class);
        intent.putExtra("owner_name", ownerNameInput);
        startActivity(intent);
    }

    private boolean validateName(){
        String ownerNameInput = ownerName_et.getText().toString().trim();
        if(ownerNameInput.isEmpty()){
            ownerName_et.setError("Enter the missing field - owner name");
            return false;
        }else{
            ownerName_et.setError(null);
            return true;
        }
    }

    public void openDialogBox(String t, String s){
        DialogBox dialog = new DialogBox(t, s);
        dialog.show(getSupportFragmentManager(), "Alert Dialog");
    }

}