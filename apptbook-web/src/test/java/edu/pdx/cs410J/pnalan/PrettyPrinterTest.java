package edu.pdx.cs410J.pnalan;

import edu.pdx.cs410J.ParserException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class PrettyPrinterTest {
    @Test
    public void prettyPrintOptionAddstoPrettyFile() throws IOException{
        AppointmentBook appointmentBook = new AppointmentBook("Peter");
        Appointment a1 = new Appointment("This is a pretty good check","09/05/2021 9:30 AM","09/05/2021 11:00 AM");
        Appointment a2 = new Appointment("This is another preety good check","09/05/2021 11:30 AM","09/05/2021 1:00 PM");
        appointmentBook.addAppointment(a1);
        appointmentBook.addAppointment(a2);
        AppointmentBookPrettyPrinter prettyPrinter = new AppointmentBookPrettyPrinter();
        prettyPrinter.getPrettyAppointments(appointmentBook);

    }

}
