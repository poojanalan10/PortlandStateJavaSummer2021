package edu.pdx.cs410J.pnalan;
import edu.pdx.cs410J.ParserException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import java.io.*;
import java.util.Collection;
import java.util.Iterator;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TextParserTest  {
    @Test
    void emptyFilecannotBeParsed() throws IOException, ParserException{
        String filename = "emptyfile.txt";
        InputStream resource = getClass().getResourceAsStream(filename);
        assertNotNull(resource);
        BufferedReader bufferedreader = new BufferedReader(new InputStreamReader(resource));
        TextParser parser = new TextParser(bufferedreader,filename, new BufferedReader(new InputStreamReader(resource)));
        assertThrows(ParserException.class,parser::parse);
        }

    @Test
    void appointmentBookOwnerCanBeDumpedAndParsed() throws IOException, ParserException{
        String filename = "file1.txt";
        InputStream resource = getClass().getResourceAsStream(filename);
        assertNotNull(resource);
        String owner = "Pooja";
        AppointmentBook appBook = new AppointmentBook(owner);
        StringWriter sw = new StringWriter();
        FileWriter filewriter = null;
        TextDumper textDumper = new TextDumper(filewriter,sw,filename);
        textDumper.dump(appBook);
        BufferedReader bufferedreader = new BufferedReader(new FileReader(filename));
        TextParser txtParser = new TextParser(bufferedreader,filename,new StringReader(sw.toString()));
        AppointmentBook appointmentBook = txtParser.parse();
        assertThat(appointmentBook.getOwnerName(), equalTo(owner));

    }
    @Test
    void appointmentBookOwnerCanBeDumpedAndParsedWithRelativePath() throws IOException, ParserException{
        String filename = "Folder/file2";
        String owner = "Pooja";
        AppointmentBook appBook = new AppointmentBook(owner);
        StringWriter sw = new StringWriter();
       // FileWriter filewriter = new FileWriter(new File(filename),true);
        FileWriter filewriter = null;
        TextDumper textDumper = new TextDumper(filewriter,sw,filename);

        textDumper.dump(appBook);
        BufferedReader bufferedreader = new BufferedReader(new FileReader(filename));
        TextParser txtParser = new TextParser(bufferedreader,filename,new StringReader(sw.toString()));
        AppointmentBook appointmentBook = txtParser.parse();
        assertThat(appointmentBook.getOwnerName(), equalTo(owner));

    }
    @Test
    void appointmentBookOwnerCanBeDumpedAndParsedWithRelativePathAndExtension() throws IOException, ParserException{
        String filename = "Folder/file1.txt";
        String owner = "Pooja";
        AppointmentBook appBook = new AppointmentBook(owner);
        StringWriter sw = new StringWriter();
        FileWriter filewriter = null;
        TextDumper textDumper = new TextDumper(filewriter,sw,filename);

        textDumper.dump(appBook);
        BufferedReader bufferedreader = new BufferedReader(new FileReader(filename));
        TextParser txtParser = new TextParser(bufferedreader,filename,new StringReader(sw.toString()));
        AppointmentBook appointmentBook = txtParser.parse();
        assertThat(appointmentBook.getOwnerName(), equalTo(owner));

    }
    @Test
    public void whenFilesWithExtensionOtherThantxtIsEntered() throws IOException {
        String owner = "Pooja";
        String filename = "life.pdf";
        InputStream resource = getClass().getResourceAsStream(filename);
        assertNotNull(resource);
        AppointmentBook appointmentBook = new AppointmentBook(owner);
        StringWriter sw = new StringWriter();

        assertThrows(IllegalArgumentException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                BufferedReader bufferedreader = new BufferedReader(new InputStreamReader(resource));
                TextParser parser = new TextParser(bufferedreader,filename, new BufferedReader(new InputStreamReader(resource)));
            }
        });

    }

    @Test
    void parserCanParseTextDumpedByDumper() throws IOException, ParserException {
        String owner = "Owner";
        String description1 = "Description 1";
        String description2 = "Description 2";

        AppointmentBook book1 = new AppointmentBook(owner);
        book1.addAppointment(new Appointment(description1, "01/01/2021", "5:00 am", "01/01/2021", "6:00 am"));
        book1.addAppointment(new Appointment(description2, "01/01/2021", "7:00 am", "01/01/2021", "8:00 am"));

        StringWriter sw = new StringWriter();

        TextDumper dumper = new TextDumper(sw);
        dumper.dump(book1);

        String text = sw.toString();
        assertThat(text, containsString(owner));
        assertThat(text, containsString(description1));
        assertThat(text, containsString(description2));

        TextParser parser = new TextParser(new StringReader(text));
        AppointmentBook book2 = parser.parse();

        assertThat(book2, notNullValue());
        assertThat(book2.getOwnerName(), equalTo(book1.getOwnerName()));

        assertAppointmentsAreEqual(book2.getAppointments(), book1.getAppointments());

    }

    private void assertAppointmentsAreEqual(Collection<Appointment> actual, Collection<Appointment> expected) {
        assertThat(actual, notNullValue());
        assertThat(actual.size(), equalTo(expected.size()));

        Iterator<Appointment> expectedIter = expected.iterator();
        Iterator<Appointment> actualIter = actual.iterator();
        while (expectedIter.hasNext()) {
            assertAppointmentsAreEqual(actualIter.next(), expectedIter.next());
        }
    }

    private void assertAppointmentsAreEqual(Appointment actual, Appointment expected) {
        assertThat(actual.getDescription(), equalTo(expected.getDescription()));
        assertThat(actual.getBeginTime(), equalTo(expected.getBeginTime()));
        assertThat(actual.getEndTime(), equalTo(expected.getEndTime()));
    }


}
