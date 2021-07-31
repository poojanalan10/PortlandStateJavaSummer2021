package edu.pdx.cs410J.pnalan;
import edu.pdx.cs410J.ParserException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

import static edu.pdx.cs410J.pnalan.AppointmentBookServlet.*;
import static edu.pdx.cs410J.pnalan.AppointmentBookServlet.END_TIME;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class TextParserTest {
    private String owner = "Pooja";
    private String description = " doctor visit";
    private String startDateTime = "01/01/2020 1:10 am";
    private String endDateTime = "01/01/2020 3:40 am";

    Appointment appointment = new Appointment(description, startDateTime, endDateTime);
    @Test
    void emptyFilecannotBeParsed() throws IOException, ParserException, ServletException {
        String owner = "Pooja";
        AppointmentBook appointmentBook = new AppointmentBook(owner);
        appointmentBook.addAppointment(appointment);
        AppointmentBookServlet servlet = new AppointmentBookServlet();
        HttpServletRequest request1 = mock(HttpServletRequest.class);
        when(request1.getParameter(OWNER_NAME)).thenReturn(owner);
        when(request1.getParameter(DESCRIPTION)).thenReturn(description);
        when(request1.getParameter(START_TIME)).thenReturn(startDateTime);
        when(request1.getParameter(END_TIME)).thenReturn(endDateTime);

        HttpServletResponse response = mock(HttpServletResponse.class);
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw, true);
        try {
            when(response.getWriter()).thenReturn(pw);
        } catch (IOException e) {
            e.printStackTrace();
        }
        servlet.doPost(request1, response);
        System.out.println(sw.toString());
        verify(response).setStatus(HttpServletResponse.SC_OK);
        String textappointment = sw.toString();
        assertThat(textappointment, containsString("Appointment was successfully added"));
        servlet.doPost(request1, response);
        String text = response.getWriter().toString();
        TextParser parser = new TextParser(new StringReader(text));
        parser.parse();
        }
/*
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
*/

}
