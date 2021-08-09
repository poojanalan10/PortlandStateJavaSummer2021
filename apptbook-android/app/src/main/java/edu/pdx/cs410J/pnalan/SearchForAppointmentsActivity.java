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
import java.text.DateFormat;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Pattern;

public class SearchForAppointmentsActivity extends AppCompatActivity {

    private Button button;
    EditText OwnerName;
    EditText start;
    EditText end;
    private static final Pattern DATE_PATTERN =
            Pattern.compile("^(0?[1-9]|1[0-2])/(0?[1-9]|1\\d|2\\d|3[01])/(19|20)\\d{2}\\s(((0?[1-9])|(1[0-2])):([0-5])([0-9])\\s[PpAa][Mm])$");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_search_for_appointments);
        OwnerName = findViewById(R.id.searchCustomerNameEditText);
        start = findViewById(R.id.searchStartEditText);
        end = findViewById(R.id.searchEndEditText);
        button = findViewById(R.id.searchPhoneCallButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validateOwnerNameEntry() & validateDateAndTime(start) & validateDateAndTime(end)) {
                    if (checkIfStartBeforeEndDateTime()) {
                        String ownerNameInput = OwnerName.getText().toString().trim();
                        String fileName = ownerNameInput + ".txt";
                        try {
                            FileInputStream fileInputStream = openFileInput(fileName);
                            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
                            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                            StringBuilder stringBuilder = new StringBuilder();
                            String appointment;

                            while ((appointment = bufferedReader.readLine()) != null) {
                                stringBuilder.append(appointment).append("\n");
                            }
                            if (stringBuilder.toString().length() == 0) {
                                openDialogBox("APPOINTMENT BOOK INFO", "No appointments exist for " + ownerNameInput);
                            } else {
                                openSearchResultActivity();
                            }
                            clearAllFields();
                        } catch (FileNotFoundException e) {
                            openDialogBox("APPOINTMENT BOOK INFO", "No appointmentBook found for owner " + ownerNameInput);
                        } catch (IOException e) {
                            openDialogBox("ERROR", "An unexpected error has occurred!");
                        }
                    }
                }
            }
        });
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

    private boolean validateDateAndTime(EditText DateTimeEntry) {
        String DateTimeEntryString = DateTimeEntry.getText().toString().trim();
        if (DateTimeEntryString.isEmpty()) {
            start.setError("Date/Time cannot be empty!");
            return false;
        } else if (!DATE_PATTERN.matcher(DateTimeEntryString).matches()) {
            openDialogBox("ERROR", "The entered date and time are not in the expected format:\n\nmm/dd/yyyy hh:mm am/pm\nyou have entered:\n" + DateTimeEntryString);
            return false;
        } else {
            start.setError(null);
            return true;
        }
    }

    public void openDialogBox(String title, String message) {
        DialogBox dialog = new DialogBox(title, message);
        dialog.show(getSupportFragmentManager(), "Dialog");
    }

    private boolean checkIfStartBeforeEndDateTime() {
        String startDateTimeEntry = start.getText().toString().trim();
        String endDateTimeEntry = end.getText().toString().trim();
        Date startDateTime = getDateAndTimeDateObject(startDateTimeEntry);
        Date endDateTime = getDateAndTimeDateObject(endDateTimeEntry);
        if (!startDateTime.before(endDateTime)) {
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
        } catch (ParseException e) {
            openDialogBox("ERROR", "An Error occurred while trying to parse the date!");
            return null;
        }
    }

    public void clearAllFields() {
        OwnerName.getText().clear();
        start.getText().clear();
        end.getText().clear();
    }

    private void openSearchResultActivity() {
        Intent intent = new Intent(this, appointmentsInGivenDateRangeActivity.class);
        String customerNameInput = OwnerName.getText().toString().trim();
        String startInput = start.getText().toString().trim();
        String endInput = end.getText().toString().trim();
        intent.putExtra("owner_name_search", customerNameInput);
        intent.putExtra("start_datetime_search", startInput);
        intent.putExtra("end_datetime_search", endInput);
        startActivity(intent);
    }

}