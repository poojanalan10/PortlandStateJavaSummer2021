package edu.pdx.cs410J.pnalan;

import edu.pdx.cs410J.ParserException;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import java.io.*;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.containsStringIgnoringCase;
import static org.hamcrest.CoreMatchers.*;
import static org.junit.jupiter.api.Assertions.*;

public class TextDumperTest {

    @Test
    public void whenFilesWithExtensionOtherThantxtIsEntered() throws IOException {
        String owner = "Pooja";
            AppointmentBook appointmentBook = new AppointmentBook(owner);
            StringWriter sw = new StringWriter();
            FileWriter filewriter = new FileWriter(new File("life"));

            assertThrows(IllegalArgumentException.class, new Executable() {
                @Override
                public void execute() throws Throwable {
                    TextDumper dumper = new TextDumper(filewriter, sw, "life.pdf");
                }
            });

    }
    @Test
    void dumperDumpsAppointmentBookOwner() throws IOException{
        String owner = "Pooja";
        AppointmentBook appointmentBook = new AppointmentBook(owner);
        FileWriter filewriter = new FileWriter(new File("file1"));
        StringWriter sw = new StringWriter();
        TextDumper dumper = new TextDumper(filewriter, sw,"file1");
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
