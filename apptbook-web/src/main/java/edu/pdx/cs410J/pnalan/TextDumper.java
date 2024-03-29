package edu.pdx.cs410J.pnalan;

import edu.pdx.cs410J.AppointmentBookDumper;

import java.io.PrintWriter;
/**
 * This class represents a <code>TextDumper</code>
 */
public class TextDumper implements AppointmentBookDumper<AppointmentBook> {
    /**
     * The writer object
     */
    private final PrintWriter writer;

    /**
     * a constructor that assigns to writer
     * @param writer
     */
    TextDumper(PrintWriter writer){
        this.writer = writer;
    }

    /**
     * This method dumps the contents
     * @param appointmentBook
     *          the book of appointments
     */
    @Override
    public void dump(AppointmentBook appointmentBook){
        PrintWriter pw = new PrintWriter(this.writer);
        if(!appointmentBook.getOwnerName().isEmpty()) {
            pw.println(appointmentBook.getOwnerName());
            for (Appointment appointment : appointmentBook.getAppointments()) {
                pw.println(appointment.getDescription() + "," + appointment.getBeginTimeString() + "," + appointment.getEndTimeString());

            }
            pw.flush();
        }
        else{
            System.err.println("Owner name is empty");
            pw.println("Owner name is not entered/null");
            pw.flush();
        }
    }
}
