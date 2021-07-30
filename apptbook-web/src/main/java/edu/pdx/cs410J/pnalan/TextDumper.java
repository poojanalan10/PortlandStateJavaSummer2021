package edu.pdx.cs410J.pnalan;

import edu.pdx.cs410J.AppointmentBookDumper;

import java.io.PrintWriter;

public class TextDumper implements AppointmentBookDumper<AppointmentBook> {

    private final PrintWriter writer;
    TextDumper(PrintWriter writer){
        this.writer = writer;
    }

    @Override
    public void dump(AppointmentBook appointmentBook){
        PrintWriter pw = new PrintWriter(this.writer);
        pw.println(appointmentBook.getOwnerName());
        for(Appointment appointment: appointmentBook.getAppointments()){
           // this.writer.println(appointment.getDescription() + "," + appointment.getBeginTimeString() + "," + appointment.getEndTimeString());
            pw.println(appointment.getDescription() + "," + appointment.getBeginTimeString() + "," + appointment.getEndTimeString());

        }
        pw.flush();
    }
}
