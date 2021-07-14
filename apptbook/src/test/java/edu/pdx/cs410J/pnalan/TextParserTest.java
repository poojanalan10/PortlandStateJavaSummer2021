package edu.pdx.cs410J.pnalan;
import edu.pdx.cs410J.ParserException;
import org.junit.jupiter.api.Test;

import java.io.*;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TextParserTest {
    @Test
    void emptyFilecannotBeParsed(){
        InputStream resource = getClass().getResourceAsStream("filewithnocontent.txt");
        assertNotNull(resource);
        TextParser parser = new TextParser(new BufferedReader(new InputStreamReader(resource)));
        assertThrows(ParserException.class,parser::parse);
        }
   @Test
   void appointmentBookOwnerCanBeDumpedAndParsed() throws IOException, ParserException{
        String owner = "Pooja";
        String filename = "appointmentdumpedandparsed";
        AppointmentBook appointmentBook = new AppointmentBook(owner);
        FileWriter filewriter = new FileWriter(new File(filename),true);
        StringWriter sw = new StringWriter();
        TextDumper dumper = new TextDumper(filewriter, sw,filename);
       //TextDumper dumper = new TextDumper(filename);
        dumper.dump(appointmentBook);
        TextParser parser = new TextParser(filename, new StringReader(sw.toString()));
        appointmentBook = parser.parse();
        assertThat(appointmentBook.getOwnerName(), equalTo(owner));

    }

}
