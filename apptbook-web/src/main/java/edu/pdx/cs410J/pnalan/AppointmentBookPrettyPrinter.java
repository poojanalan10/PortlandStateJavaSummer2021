package edu.pdx.cs410J.pnalan;

import edu.pdx.cs410J.ParserException;

import java.io.PrintWriter;
import java.text.ParseException;

public class AppointmentBookPrettyPrinter {
    private String start;
    private String end;
    private PrintWriter writer;
    private int num;

    public AppointmentBookPrettyPrinter() {
        super();
        this.num = 0;
    }

    public AppointmentBookPrettyPrinter(String start, String end) {
        this.start = start;
        this.end = end;
        this.num = 1;
    }

    public String getPrettyAppointments(AppointmentBook appointmentBook) throws ParseException {
        var num_of_appointments = appointmentBook.getAppointments().size();
        int count = num_of_appointments;
        String prettyAppointments = "";
        if (num == 1) {
            if (appointmentBook.getAppointments().isEmpty()) {
                prettyAppointments = "No appointments found for "+ appointmentBook.getOwnerName() + " in the date range " + this.start + " and " + this.end;
                return prettyAppointments;
            } else {
                prettyAppointments = "Appointments between dates " + start + " and " + end + " found!\n";
                appointmentBook = appointmentBook.findAppointmentsWithDateRange(start,end);

            }
        }
        prettyAppointments += "Owner:" + appointmentBook.getOwnerName() + "\n";
        prettyAppointments += "Number of appointments:" + num_of_appointments + "\n";
        prettyAppointments += String.format("%n%-35s%-35s%-35s%-35s", "Description", "Start date and time", "end date and time", "Appointment duration in minutes"+"\n");
        prettyAppointments += "--------------------------------------------------------------------------------------------------------------------------------\n";
        for (Appointment appointment : appointmentBook.getAppointments()) {
            try {
                prettyAppointments += String.format("%n%-35s%-35s%-35s%-35s", appointment.getDescription(), appointment.getPrettyDateTime(/*appointment.getBeginDate() + " " +*/ appointment.getBeginTimeString())
                        , appointment.getPrettyDateTime(/*appointment.getEndDate() + " " +*/ appointment.getEndTimeString()), appointment.appointmentDuration() + "\n");

            } catch (ParserException e) {
                e.printStackTrace();
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return prettyAppointments;
    }

}
