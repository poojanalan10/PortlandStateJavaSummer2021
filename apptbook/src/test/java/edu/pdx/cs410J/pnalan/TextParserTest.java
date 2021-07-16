package edu.pdx.cs410J.pnalan;
import edu.pdx.cs410J.ParserException;
import net.bytebuddy.build.HashCodeAndEqualsPlugin;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import java.io.*;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;
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
        FileWriter filewriter = new FileWriter(new File(filename),true);
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
        FileWriter filewriter = new FileWriter(new File(filename),true);
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


}
