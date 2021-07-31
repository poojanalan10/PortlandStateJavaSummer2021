package edu.pdx.cs410J.pnalan;

import edu.pdx.cs410J.ParserException;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.security.InvalidParameterException;

import static edu.pdx.cs410J.pnalan.AppointmentBookServlet.*;
import static edu.pdx.cs410J.pnalan.AppointmentBookServlet.END_TIME;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TextDumperTest {
    @Test
    void dumperDumpsAppointmentBookOwner() throws IOException{

         String owner = "Pooja";
         String description = " doctor visit";
         String startDateTime = "01/01/2020 1:10 am";
         String endDateTime = "01/01/2020 3:40 am";
        AppointmentBook appointmentBook = new AppointmentBook(owner);
        Appointment appointment = new Appointment(description, startDateTime, endDateTime);
        appointmentBook.addAppointment(appointment);
        AppointmentBookServlet servlet = new AppointmentBookServlet();
        HttpServletRequest request1 = mock(HttpServletRequest.class);
        when(request1.getParameter(OWNER_NAME)).thenReturn(owner);
        when(request1.getParameter(DESCRIPTION)).thenReturn(description);
        when(request1.getParameter(START_TIME)).thenReturn(startDateTime);
        when(request1.getParameter(END_TIME)).thenReturn(endDateTime);
        servlet.addAppointmentBook(appointmentBook);
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw, true);
        TextDumper dumper = new TextDumper(pw);
        dumper.dump(appointmentBook);
        pw.flush();
        String textappointment = sw.toString();
        assertThat(textappointment,containsString(owner));
    }
    @Test
    void whenOwnerNameNotPassedTextDumperReturnsOwnerNameNull(){
    String owner = "";
    String description = " doctor visit";
    String startDateTime = "01/01/2020 1:10 am";
    String endDateTime = "01/01/2020 3:40 am";
    AppointmentBook appointmentBook = new AppointmentBook(owner);
    Appointment appointment = new Appointment(description, startDateTime, endDateTime);
    appointmentBook.addAppointment(appointment);
    AppointmentBookServlet servlet = new AppointmentBookServlet();
    HttpServletRequest request1 = mock(HttpServletRequest.class);
    when(request1.getParameter(OWNER_NAME)).thenReturn(owner);
    when(request1.getParameter(DESCRIPTION)).thenReturn(description);
    when(request1.getParameter(START_TIME)).thenReturn(startDateTime);
    when(request1.getParameter(END_TIME)).thenReturn(endDateTime);
    servlet.addAppointmentBook(appointmentBook);
    StringWriter sw = new StringWriter();
    PrintWriter pw = new PrintWriter(sw, true);
    TextDumper dumper = new TextDumper(pw);
    dumper.dump(appointmentBook);
    pw.flush();
    String textappointment = sw.toString();
    assertThat(textappointment,containsString("Owner name is not entered/null"));
}




}
