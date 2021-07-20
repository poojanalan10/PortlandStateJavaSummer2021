package edu.pdx.cs410J.pnalan;

import edu.pdx.cs410J.ParserException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import java.io.*;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class PrettyPrinterTest {
    @Test
    public void prettyPrintOptionAddstoPrettyFile() throws IOException{
        AppointmentBook appointmentBook = new AppointmentBook("Peter");
        Appointment a1 = new Appointment("This is a pretty good check","09/05/2021","9:30 AM","09/05/2021","11:00 AM");
        Appointment a2 = new Appointment("This is another preety good check","09/05/2021","11:30 AM","09/05/2021","1:00 PM");
        appointmentBook.addAppointment(a1);
        appointmentBook.addAppointment(a2);
        PrettyPrinter prettyPrinter = new PrettyPrinter("prettyfiletest.txt");
        prettyPrinter.dump(appointmentBook);

    }
    @Test
    public void whenFilesWithExtensionOtherThantxtIsEnteredPdf() throws IOException {
        String owner = "Pooja";
        String filename = "life.pdf";
        AppointmentBook appointmentBook = new AppointmentBook(owner);
        assertThrows(IllegalArgumentException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                PrettyPrinter prettyPrinter = new PrettyPrinter(filename);
            }
        });

    }

    @Test
    public void whenFilesWithExtensionOtherThantxtIsEnteredDoc() throws IOException {
        String owner = "Pooja";
        String filename = "Folder/wordfile.doc";
        AppointmentBook appointmentBook = new AppointmentBook(owner);
        assertThrows(IllegalArgumentException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                PrettyPrinter prettyPrinter = new PrettyPrinter(filename);
            }
        });

    }

    @Test
    void appointmentBookOwnerCanBeDumpedWithRelativePathAndExtension() throws IOException, ParserException {
        String filename = "Folder/prettyfile10.txt";
        String owner = "Pooja";
        AppointmentBook appBook = new AppointmentBook(owner);
        PrettyPrinter prettyPrinter = new PrettyPrinter(filename);
        prettyPrinter.dump(appBook);

    }

    @Test
    void appointmentBookOwnerCanBeDumpedWithoutRelativePathAndExtension() throws IOException, ParserException {
        String filename = "PoojaFile";
        String owner = "Pooja";
        AppointmentBook appBook = new AppointmentBook(owner);
        PrettyPrinter prettyPrinter = new PrettyPrinter(filename);
        prettyPrinter.dump(appBook);

    }
}
