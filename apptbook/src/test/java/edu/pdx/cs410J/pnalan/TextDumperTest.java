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
        String owner = "Pooja\n";
        AppointmentBook appointmentBook = new AppointmentBook(owner);
        FileWriter filewriter = new FileWriter(new File("testfile91"));
        StringWriter sw = new StringWriter();
        TextDumper dumper = new TextDumper(filewriter, sw,"testfile91");
        dumper.dump(appointmentBook);
        String text = sw.toString();
        assertThat(text,containsString(owner));
    }

    @Test
    public void fileNameWithoutExtensionIsValid() throws IOException {
        String owner = "Peter";
        AppointmentBook appointmentBook = new AppointmentBook(owner);
        FileWriter filewriter = new FileWriter(new File("PeterFile"));
        StringWriter sw = new StringWriter();
        TextDumper dumper = new TextDumper(filewriter, sw,"PeterFile");
        dumper.dump(appointmentBook);
        String text = sw.toString();
        assertThat(text,containsString(owner));
    }

   @Test
    public void fileNameWithExtensiontxtIsValid() throws IOException {
       String owner = "Pooja\n";
       AppointmentBook appointmentBook = new AppointmentBook(owner);
       FileWriter filewriter = new FileWriter(new File("filewithextension.txt"));
       StringWriter sw = new StringWriter();
       TextDumper dumper = new TextDumper(filewriter, sw,"filewithextension.txt");
       dumper.dump(appointmentBook);
       String text = sw.toString();
       assertThat(text,containsString(owner));
   }
}
