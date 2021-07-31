package edu.pdx.cs410J.pnalan;

import edu.pdx.cs410J.ParserException;

import org.junit.jupiter.api.Test;


import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

import static edu.pdx.cs410J.pnalan.AppointmentBookServlet.*;
import static edu.pdx.cs410J.pnalan.AppointmentBookServlet.END_TIME;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.*;

public class AppointmentBookRestClientTest {
    private String owner = "Pooja";
    private String description = " doctor visit";
    private String startDateTime = "01/01/2020 1:10 am";
    private String endDateTime = "01/01/2020 3:40 am";

    private String owner2 = "Pooja";
    private String description2 = "Class";
    private String startDateTime2 = "02/01/2020 9:10 am";
    private String endDateTime2 = "02/01/2021 10:40 am";

    private String queryowner = "Pooja";
    private String queryStartDateTime = "01/01/2020 1:00 am";
    private String queryEndDateTime = "01/01/2020 04:00 am";
    @Test
    public void checkClientConnectionDoesNotThrowError() throws ServletException, IOException {
        String owner = "Pooja";
        Appointment appointment = new Appointment(description, startDateTime, endDateTime);
        Appointment appointment2 = new Appointment(description2,startDateTime2,endDateTime2);
        AppointmentBook appointmentBook = new AppointmentBook(owner);
        appointmentBook.addAppointment(appointment);
        AppointmentBookServlet servlet = new AppointmentBookServlet();
        HttpServletRequest request1 = mock(HttpServletRequest.class);
        when(request1.getParameter(OWNER_NAME)).thenReturn(owner);
        when(request1.getParameter(DESCRIPTION)).thenReturn(description);
        when(request1.getParameter(START_TIME)).thenReturn(startDateTime);
        when(request1.getParameter(END_TIME)).thenReturn(endDateTime);

        HttpServletResponse queryresponse = mock(HttpServletResponse.class);
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw, true);
        try {
            when(queryresponse.getWriter()).thenReturn(pw);
        } catch (IOException e) {
            e.printStackTrace();
        }
        servlet.doPost(request1, queryresponse);
        AppointmentBookRestClient client = new AppointmentBookRestClient("localhost", 8080);
        assertThat(client.toString(),containsString("AppointmentBookRestClient"));
    }

    @Test
    public void checkAddAppointment() throws ServletException, IOException {
        String owner = "Pooja";
        Appointment appointment = new Appointment(description, startDateTime, endDateTime);
        Appointment appointment2 = new Appointment(description2,startDateTime2,endDateTime2);
        AppointmentBook appointmentBook = new AppointmentBook(owner);
        appointmentBook.addAppointment(appointment);
        AppointmentBookServlet servlet = new AppointmentBookServlet();
        HttpServletRequest request1 = mock(HttpServletRequest.class);
        when(request1.getParameter(OWNER_NAME)).thenReturn(owner);
        when(request1.getParameter(DESCRIPTION)).thenReturn(description);
        when(request1.getParameter(START_TIME)).thenReturn(startDateTime);
        when(request1.getParameter(END_TIME)).thenReturn(endDateTime);

        HttpServletResponse queryresponse = mock(HttpServletResponse.class);
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw, true);
        try {
            when(queryresponse.getWriter()).thenReturn(pw);
        } catch (IOException e) {
            e.printStackTrace();
        }
        servlet.doPost(request1, queryresponse);
        AppointmentBookRestClient client = new AppointmentBookRestClient("localhost", 8080);
        assertThat(client.addAppointment(new String[]{request1.getParameter(OWNER_NAME),request1.getParameter(DESCRIPTION),request1.getParameter(START_TIME),request1.getParameter(END_TIME)}),equalTo("HTTP Status: 200\nAppointment was successfully added"));
    }

    @Test
    public void checkFindAppointment() throws IOException, ServletException, ParserException {
        String owner = "Pooja";
        AppointmentBook appointmentBook = new AppointmentBook(owner);
        Appointment appointment = new Appointment(description, startDateTime, endDateTime);
        Appointment appointment2 = new Appointment(description2,startDateTime2,endDateTime2);
        appointmentBook.addAppointment(appointment);
        AppointmentBookServlet servlet = new AppointmentBookServlet();


        HttpServletRequest request1 = mock(HttpServletRequest.class);
        when(request1.getParameter(OWNER_NAME)).thenReturn(owner);
        when(request1.getParameter(DESCRIPTION)).thenReturn(description);
        when(request1.getParameter(START_TIME)).thenReturn(startDateTime);
        when(request1.getParameter(END_TIME)).thenReturn(endDateTime);

        HttpServletRequest request2 = mock(HttpServletRequest.class);
        appointmentBook.addAppointment(appointment2);
        when(request2.getParameter(DESCRIPTION)).thenReturn(description2);
        when(request2.getParameter(START_TIME)).thenReturn(startDateTime2);
        when(request2.getParameter(END_TIME)).thenReturn(endDateTime2);

        servlet.addAppointmentBook(appointmentBook);
        HttpServletRequest queryrequest = mock(HttpServletRequest.class);
        when(queryrequest.getParameter(OWNER_NAME)).thenReturn(queryowner);
        when(queryrequest.getParameter(START_TIME)).thenReturn(queryStartDateTime);
        when(queryrequest.getParameter(END_TIME)).thenReturn(queryEndDateTime);


        HttpServletResponse queryresponse = mock(HttpServletResponse.class);
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw, true);
        when(queryresponse.getWriter()).thenReturn(pw);
        servlet.doGet(queryrequest, queryresponse);
        verify(queryresponse).setStatus(HttpServletResponse.SC_OK);
        String textappointment = sw.toString();
        String replacedTextappointmentWithoutNewLinesAndTabs = textappointment.replaceAll("[\\n\\t]","");

        AppointmentBookRestClient client = new AppointmentBookRestClient("localhost", 8080);

        String clientString = client.findAppointment(new String[]{queryrequest.getParameter(OWNER_NAME),queryrequest.getParameter(START_TIME),queryrequest.getParameter(END_TIME)}).replaceAll("[\\n\\t]","");
        clientString = clientString.replaceAll("\n","");
        assertThat(clientString,containsString(replacedTextappointmentWithoutNewLinesAndTabs));
    }
 @Test
public void checkGetAppointment() throws IOException, ServletException, ParserException {
    String owner = "Pooja";
    String queryowner = "Pooja";

     Appointment appointment = new Appointment(description, startDateTime, endDateTime);
     Appointment appointment2 = new Appointment(description2,startDateTime2,endDateTime2);

     AppointmentBook appointmentBook = new AppointmentBook(owner);
     appointmentBook.addAppointment(appointment);
     AppointmentBookServlet servlet = new AppointmentBookServlet();


     HttpServletRequest request1 = mock(HttpServletRequest.class);
     when(request1.getParameter(OWNER_NAME)).thenReturn(owner);
     when(request1.getParameter(DESCRIPTION)).thenReturn(description);
     when(request1.getParameter(START_TIME)).thenReturn(startDateTime);
     when(request1.getParameter(END_TIME)).thenReturn(endDateTime);

     HttpServletRequest request2 = mock(HttpServletRequest.class);
     appointmentBook.addAppointment(appointment2);
     when(request2.getParameter(DESCRIPTION)).thenReturn(description2);
     when(request2.getParameter(START_TIME)).thenReturn(startDateTime2);
     when(request2.getParameter(END_TIME)).thenReturn(endDateTime2);

     servlet.addAppointmentBook(appointmentBook);
     HttpServletRequest queryrequest = mock(HttpServletRequest.class);
     when(queryrequest.getParameter(OWNER_NAME)).thenReturn(queryowner);
     HttpServletResponse queryresponse = mock(HttpServletResponse.class);
     StringWriter sw = new StringWriter();
     PrintWriter pw = new PrintWriter(sw, true);
     when(queryresponse.getWriter()).thenReturn(pw);
     servlet.doGet(queryrequest, queryresponse);
     verify(queryresponse).setStatus(HttpServletResponse.SC_OK);
     String textappointment = sw.toString();
     System.out.println(appointmentBook.getAppointments());
     AppointmentBookRestClient client = new AppointmentBookRestClient("localhost", 8080);
     var appointments = client.getAppointments(owner).getAppointments();
     assertThat(appointments.stream().findFirst().toString(),containsString(description));

}
}