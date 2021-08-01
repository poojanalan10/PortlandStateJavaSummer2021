package edu.pdx.cs410J.pnalan;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import java.io.IOException;
import java.text.ParseException;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;


public class PrettyPrinterTest {
    @Test
    public void prettyPrintOptionAddstoPrettyFile() throws IOException, ParseException {
        AppointmentBook appointmentBook = new AppointmentBook("Peter");
        Appointment a1 = new Appointment("This is a pretty good check","09/05/2021 9:30 AM","09/05/2021 11:00 AM");
        Appointment a2 = new Appointment("This is another preety good check","09/05/2021 11:30 AM","09/05/2021 1:00 PM");
        appointmentBook.addAppointment(a1);
        appointmentBook.addAppointment(a2);
        AppointmentBookPrettyPrinter prettyPrinter = new AppointmentBookPrettyPrinter();
        prettyPrinter.getPrettyAppointments(appointmentBook);

    }

    @Test
    public void prettyPrintOptionPrintsNoAppointmentInTheDateRange() throws IOException, ParseException {
        AppointmentBook appointmentBook = new AppointmentBook("Dave");
        AppointmentBookPrettyPrinter prettyPrinter = new AppointmentBookPrettyPrinter("01/01/2021 10:00 am","01/01/2021 11:00 am");
        assertThat(prettyPrinter.getPrettyAppointments(appointmentBook),equalTo("No appointments found for Dave in the date range 01/01/2021 10:00 am and 01/01/2021 11:00 am"));

    }

    @Test
    public void prettyPrintOptionPrintsAppointmentInTheDateRange() throws IOException, ParseException {
        AppointmentBook appointmentBook = new AppointmentBook("Dave");
        Appointment a1 = new Appointment("This is a pretty good check","09/05/2021 9:30 AM","09/05/2021 11:00 AM");
        Appointment a2 = new Appointment("This is another preety good check","09/05/2021 11:30 AM","09/05/2021 1:00 PM");
        appointmentBook.addAppointment(a1);
        appointmentBook.addAppointment(a2);
        AppointmentBookPrettyPrinter prettyPrinter = new AppointmentBookPrettyPrinter("09/05/2021 08:00 am","09/05/2021 11:00 am");
        assertThat(prettyPrinter.getPrettyAppointments(appointmentBook),containsString("Appointments between dates 09/05/2021 08:00 am and 09/05/2021 11:00 am found!"));

    }

    @Test
    public void prettyPrintOptionThrowsException() throws IOException, ParseException {
        AppointmentBook appointmentBook = new AppointmentBook("Dave");
        Appointment a1 = new Appointment("This is a pretty good check","09/05/2021 9:30 AM","09/05/2021 11:00 AM");
        Appointment a2 = new Appointment("This is another preety good check","09/05/2021 11:30 AM","09/05/2021 1:00 PM");
        appointmentBook.addAppointment(a1);
        appointmentBook.addAppointment(a2);
        assertThrows(ParseException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                AppointmentBookPrettyPrinter prettyPrinter = new AppointmentBookPrettyPrinter("09-05-2021 08:00 am","09/05/2021 11:00 am");
                prettyPrinter.getPrettyAppointments(appointmentBook);
            }
        });

    }

    @Test
    public void prettyPrintOptionThrowsParseExceptionWhenDateIsNull() throws IOException, ParseException {
        AppointmentBook appointmentBook = new AppointmentBook("Dave");
        Appointment a1 = new Appointment("This is a pretty good check","09/05/2021 9:30 AM","09/05/2021 11:00 AM");
        Appointment a2 = new Appointment("This is another preety good check","09/05/2021 11:30 AM","09/05/2021 1:00 PM");
        appointmentBook.addAppointment(a1);
        appointmentBook.addAppointment(a2);
        assertThrows(ParseException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                AppointmentBookPrettyPrinter prettyPrinter = new AppointmentBookPrettyPrinter("","09/05/2021 11:00 am");
                prettyPrinter.getPrettyAppointments(appointmentBook);
            }
        });

    }



}
