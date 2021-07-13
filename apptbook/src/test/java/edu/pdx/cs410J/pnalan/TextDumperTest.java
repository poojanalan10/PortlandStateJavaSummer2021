package edu.pdx.cs410J.pnalan;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;

public class TextDumperTest {
    @Test
    void dumperDumpsAppointmentBookOwner() throws IOException{
        String owner = "Pooja";
        AppointmentBook appointmentBook = new AppointmentBook(owner);
        FileWriter filewriter = new FileWriter(new File("testfile"));
        StringWriter sw = new StringWriter();
        TextDumper dumper = new TextDumper(filewriter,sw,"testfile");
        dumper.dump(appointmentBook);
        String text = sw.toString();
        assertThat(text,containsString(owner));
    }
}
