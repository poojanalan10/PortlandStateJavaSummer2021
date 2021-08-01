package edu.pdx.cs410J.pnalan;

import edu.pdx.cs410J.ParserException;
import org.apache.groovy.json.internal.IO;
import org.checkerframework.checker.units.qual.A;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.mockito.ArgumentCaptor;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.net.HttpURLConnection;
import java.security.InvalidParameterException;
import java.security.Principal;
import java.text.ParseException;
import java.util.*;

import static edu.pdx.cs410J.pnalan.AppointmentBookServlet.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

/**
 * A unit test for the {@link AppointmentBookServlet}.  It uses mockito to
 * provide mock http requests and responses.
 */
public class AppointmentBookServletTest {

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

    private String queryownernotfound = "Pooja";
    private String queryStartDateTimenotfound = "10/01/2020 1:00 am";
    private String queryEndDateTimenotfound = "10/01/2020 04:00 am";

  Appointment appointment = new Appointment(description, startDateTime, endDateTime);
  Appointment appointment2 = new Appointment(description2,startDateTime2,endDateTime2);
  @Test
  @Disabled
  void initiallyServletContainsNoDictionaryEntries() throws ServletException, IOException {
    AppointmentBookServlet servlet = new AppointmentBookServlet();

    HttpServletRequest request = mock(HttpServletRequest.class);
    HttpServletResponse response = mock(HttpServletResponse.class);
    PrintWriter pw = mock(PrintWriter.class);

    when(response.getWriter()).thenReturn(pw);

    servlet.doGet(request, response);

    int expectedWords = 0;
    verify(pw).println(Messages.formatWordCount(expectedWords));
    verify(response).setStatus(HttpServletResponse.SC_OK);
  }

  @Test
  void addOneAppointmentToAppointmentBook() throws ServletException, IOException {
    AppointmentBookServlet servlet = new AppointmentBookServlet();

    HttpServletRequest request = mock(HttpServletRequest.class);
    when(request.getParameter(OWNER_NAME)).thenReturn(owner);
    when(request.getParameter(DESCRIPTION)).thenReturn(description);
    when(request.getParameter(START_TIME)).thenReturn(startDateTime);
    when(request.getParameter(END_TIME)).thenReturn(endDateTime);

    HttpServletResponse response = mock(HttpServletResponse.class);

    // Use a StringWriter to gather the text from multiple calls to println()
    StringWriter stringWriter = new StringWriter();
    PrintWriter pw = new PrintWriter(stringWriter, true);

    when(response.getWriter()).thenReturn(pw);

    servlet.doPost(request, response);
    verify(response).setStatus(HttpServletResponse.SC_OK);
    AppointmentBook appointmentBook = servlet.getAppointment(owner);
    assertThat(appointmentBook, notNullValue());
    assertThat(appointmentBook.getOwnerName(), equalTo(owner));
    Appointment appointment = appointmentBook.getAppointments().iterator().next();
    assertThat(appointment.getBeginTimeString(),equalTo(startDateTime));
    assertThat(appointment.getEndTimeString(),equalTo(endDateTime));

   /* assertThat(stringWriter.toString(), containsString(Messages.definedWordAs(word, definition)));

    // Use an ArgumentCaptor when you want to make multiple assertions against the value passed to the mock
    ArgumentCaptor<Integer> statusCode = ArgumentCaptor.forClass(Integer.class);
    verify(response).setStatus(statusCode.capture());

    assertThat(statusCode.getValue(), equalTo(HttpServletResponse.SC_OK));

    assertThat(servlet.getDefinition(word), equalTo(definition));*/
  }
  @Test
  public void requestingExistingAppointmentBook() throws IOException, ServletException{
     String owner = "Pooja";
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
      when(queryrequest.getParameter(START_TIME)).thenReturn(queryStartDateTime);
      when(queryrequest.getParameter(END_TIME)).thenReturn(queryEndDateTime);


     HttpServletResponse queryresponse = mock(HttpServletResponse.class);
     StringWriter sw = new StringWriter();
     PrintWriter pw = new PrintWriter(sw, true);
     when(queryresponse.getWriter()).thenReturn(pw);
     servlet.doGet(queryrequest, queryresponse);
     verify(queryresponse).setStatus(HttpServletResponse.SC_OK);
     String textappointment = sw.toString();
   //  assertThat(textappointment, containsString(owner));
     assertThat(textappointment,containsString(description));
  }

  @Test
    public void checkDoPost() throws ServletException, IOException {
      String owner = "Pooja";
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
      System.out.println(sw.toString());
      verify(queryresponse).setStatus(HttpServletResponse.SC_OK);
      String textappointment = sw.toString();
       assertThat(textappointment, containsString("Appointment was successfully added"));
   //   assertThat(textappointment,containsString(description));
  }

  @Test
    public void checkppointmentPrettyPrint() throws IOException, ServletException, ParseException {
      String owner = "Pooja";
      AppointmentBook appointmentBook = new AppointmentBook(owner);

      AppointmentBookServlet servlet = new AppointmentBookServlet();
      HttpServletRequest request = mock(HttpServletRequest.class);
      when(request.getParameter(OWNER_NAME)).thenReturn(owner);
      when(request.getParameter(DESCRIPTION)).thenReturn(description);
      when(request.getParameter(START_TIME)).thenReturn(startDateTime);
      when(request.getParameter(END_TIME)).thenReturn(endDateTime);

    //  HttpServletResponse response = mock(HttpServletResponse.class);
      appointmentBook.addAppointment(appointment);

      // Use a StringWriter to gather the text from multiple calls to println()
      //StringWriter stringWriter = new StringWriter();
      //PrintWriter pw = new PrintWriter(stringWriter, true);

      //when(response.getWriter()).thenReturn(pw);
      AppointmentBookPrettyPrinter prettyPrinter = new AppointmentBookPrettyPrinter();
      System.out.println(prettyPrinter.getPrettyAppointments(appointmentBook));
     // servlet.doGet(request, response);
      //verify(response).setStatus(HttpServletResponse.SC_OK);
  }

    @Test
    public void checkAppointmentForOwnerWhoasnoAppointmentBook() throws IOException, ServletException {
        String owner = "Dave";
        AppointmentBook appointmentBook = new AppointmentBook(owner);

        AppointmentBookServlet servlet = new AppointmentBookServlet();
        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getParameter(OWNER_NAME)).thenReturn(owner);
        HttpServletResponse query = mock(HttpServletResponse.class);


        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw, true);

        servlet.doGet(request, query);
        when(query.getWriter()).thenReturn(pw);
        System.out.println(sw.toString());
        String textappointment = sw.toString();
     //   assertThat(textappointment, containsString(Messages.ownerHasNoAppointmentBook(owner)));

    }

  /*  @Test
    public void checkAppointmentForOwnerWhoHasNoAppointmentBookThrowsException() throws IOException, ServletException {
        String owner = "Dave";
        AppointmentBook appointmentBook = new AppointmentBook(owner);

        AppointmentBookServlet servlet = new AppointmentBookServlet();
        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getParameter(OWNER_NAME)).thenReturn(owner);
        HttpServletResponse query = mock(HttpServletResponse.class);

        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw, true);

        assertThrows(IllegalArgumentException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                servlet.doGet(request, query);
            }
        });

    }*/

    @Test
    public void findAppointmentsDateRangeDoesNotExist() throws IOException, ServletException {

        String owner = "Pooja";
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
        when(queryrequest.getParameter(OWNER_NAME)).thenReturn(queryownernotfound);
        when(queryrequest.getParameter(START_TIME)).thenReturn(queryStartDateTimenotfound);
        when(queryrequest.getParameter(END_TIME)).thenReturn(queryEndDateTimenotfound);
        HttpServletResponse queryresponse = mock(HttpServletResponse.class);
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw, true);
        when(queryresponse.getWriter()).thenReturn(pw);
        servlet.doGet(queryrequest, queryresponse);
        verify(queryresponse).setStatus(HttpServletResponse.SC_OK);
        String textappointment = sw.toString().replaceAll("[\\n\\t]","");
        assertThat(textappointment, equalTo("Appointments between range doesn't exist!"));
        verify(queryresponse).setStatus(HttpServletResponse.SC_OK);
    }
    @Test
    void invokingFindAppointmentThrowsInvalidParameter() throws ParseException, ServletException, IOException {
       // AppointmentBook appointmentBook = new AppointmentBook("Pooja");
      //  appointmentBook.addAppointment(new Appointment("","07/02/2021","09:15 am","07/02/2021","11:00 am"));
        AppointmentBook appointmentBook = new AppointmentBook(owner);
        appointmentBook.addAppointment(appointment);
        AppointmentBookServlet servlet = new AppointmentBookServlet();
        HttpServletRequest request1 = mock(HttpServletRequest.class);
        when(request1.getParameter(OWNER_NAME)).thenReturn(owner);
        when(request1.getParameter(DESCRIPTION)).thenReturn(description);
        when(request1.getParameter(START_TIME)).thenReturn(startDateTime);
        when(request1.getParameter(END_TIME)).thenReturn(endDateTime);

        HttpServletRequest queryrequest = mock(HttpServletRequest.class);
        when(queryrequest.getParameter(OWNER_NAME)).thenReturn(owner);
        when(queryrequest.getParameter(START_TIME)).thenReturn(startDateTime);
        when(queryrequest.getParameter(END_TIME)).thenReturn(endDateTime);


        // assertThrows(IllegalArgumentException.class, new Executable() {
        //    @Override
        //  public void execute() throws Throwable {
        HttpServletResponse queryresponse = mock(HttpServletResponse.class);
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw, true);
        when(queryresponse.getWriter()).thenReturn(pw);

        try{
            servlet.doGet(queryrequest, queryresponse);
            }
            catch (ServletException e) {

            assertThat(e.getStackTrace(),equalTo(HttpURLConnection.HTTP_NOT_FOUND));
            }

    }
    @Test
    public void testInvalidParameterException() throws IOException, ServletException {
        String owner = "Pooja";
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
        when(queryrequest.getParameter(START_TIME)).thenReturn(endDateTime);
        when(queryrequest.getParameter(END_TIME)).thenReturn(startDateTime);



        HttpServletResponse queryresponse = mock(HttpServletResponse.class);
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw, true);
        when(queryresponse.getWriter()).thenReturn(pw);
        assertThrows(InvalidParameterException.class, new Executable() {

            @Override
            public void execute() throws Throwable {
        servlet.doGet(queryrequest, queryresponse);
        verify(queryresponse).setStatus(HttpServletResponse.SC_OK);
        String textappointment = sw.toString();
        assertThat(textappointment,containsString(description));
            }
        });
    }

    @Test
    public void testParseException() throws IOException, ServletException {
        String owner = "Pooja";
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
        when(queryrequest.getParameter(START_TIME)).thenReturn("10-01-2020 1:00 am");
        when(queryrequest.getParameter(END_TIME)).thenReturn(startDateTime);
        HttpServletResponse queryresponse = mock(HttpServletResponse.class);

        try {
            servlet.doGet(queryrequest, queryresponse);
        }
        catch (ServletException e) {
            assertThat(e.getStackTrace(),equalTo("java.text.ParseException: Unparseable date: \"10-01-2020 1:00 am\""));
        }

    }
    @Test
    void checkDoPostThrowsErrorIfOwnerNotFoundForDoGet()throws IOException, ServletException {

        String owner = "Pooja";
        AppointmentBook appointmentBook = new AppointmentBook(owner);
        appointmentBook.addAppointment(appointment);
        AppointmentBookServlet servlet = new AppointmentBookServlet();


        HttpServletRequest request1 = mock(HttpServletRequest.class);
        when(request1.getParameter(OWNER_NAME)).thenReturn(null);
        when(request1.getParameter(DESCRIPTION)).thenReturn(description);
        when(request1.getParameter(START_TIME)).thenReturn(startDateTime);
        when(request1.getParameter(END_TIME)).thenReturn(endDateTime);

        HttpServletResponse queryresponse = mock(HttpServletResponse.class);

        try {
            servlet.doGet(request1, queryresponse);
        }
        catch ( InvalidParameterException e) {
            assertThat(e.getMessage(),equalTo("The required parameter \"owner\" is missing"));
        }

    }


    @Test
    void checkDoPostThrowsErrorIfOwnerNotFoundForDoPost()throws IOException, ServletException {

            String owner = "Pooja";
            AppointmentBook appointmentBook = new AppointmentBook(owner);
            appointmentBook.addAppointment(appointment);
            AppointmentBookServlet servlet = new AppointmentBookServlet();


            HttpServletRequest request1 = mock(HttpServletRequest.class);
            when(request1.getParameter(OWNER_NAME)).thenReturn(null);
            when(request1.getParameter(DESCRIPTION)).thenReturn(description);
            when(request1.getParameter(START_TIME)).thenReturn(startDateTime);
            when(request1.getParameter(END_TIME)).thenReturn(endDateTime);

            HttpServletResponse queryresponse = mock(HttpServletResponse.class);

            try {
                servlet.doPost(request1, queryresponse);
            }
            catch ( InvalidParameterException e) {
                assertThat(e.getMessage(),equalTo("The required parameter \"owner\" is missing"));
            }

        }
    @Test
    void checkDoPostThrowsErrorIfOwnerNotFoundForDoPostDirectCall()throws IOException, ServletException {

        String owner = "Dave";
        String description = "Teach Java";
        Map<String, String> queryParams = null;
        StringWriter sw = null;

        AppointmentBookServlet servlet = new AppointmentBookServlet();
        AppointmentBook book = servlet.createAppointmentBook(owner);
        book.addAppointment(new Appointment(description,"10/10/2021 10:00 am","10/10/2021 11:00 am"));
        Map<String,String> queryparams = Map.of("owner","Dave","description",description,"start","10/10/2021 10:00 am","end","10/10/2021 11:00 am");
        HttpServletResponse queryresponse = mock(HttpServletResponse.class);
        try {
            servlet.missingRequiredParameter(queryresponse, owner);
        }
        catch (InvalidParameterException e){
            System.out.println("Exception");
            System.out.println(e.getMessage());
            assertThat(e.getMessage(), equalTo("The required parameter \"Dave\" is missing"));
        }



      //  assertThat(text, containsString("java.security.InvalidParameterException: The required parameter \"owner\" is missing"));
     //   assertThat(text, containsString(description));


      /*  try {
            servlet.missingRequiredParameter(queryresponse,owner);
        }
        catch ( InvalidParameterException e) {

            assertThat(e.getMessage(),equalTo("The required parameter \"Pooja\" is missing") );
        }*/
    }

    @Test
    void checkDoPostThrowsErrorIfDescriptionNotFound()throws IOException, ServletException {

        String owner = "Pooja";
        AppointmentBook appointmentBook = new AppointmentBook(owner);
        appointmentBook.addAppointment(appointment);
        AppointmentBookServlet servlet = new AppointmentBookServlet();


        HttpServletRequest request1 = mock(HttpServletRequest.class);
        when(request1.getParameter(OWNER_NAME)).thenReturn(owner);
        when(request1.getParameter(DESCRIPTION)).thenReturn(null);
        when(request1.getParameter(START_TIME)).thenReturn(startDateTime);
        when(request1.getParameter(END_TIME)).thenReturn(endDateTime);

        HttpServletResponse queryresponse = mock(HttpServletResponse.class);

        try {
            servlet.doPost(request1, queryresponse);
        }
        catch ( InvalidParameterException e) {
            assertThat(e.getMessage(),equalTo("The required parameter \"description\" is missing"));
        }

    }

    @Test
    void checkDoPostThrowsErrorIfStartTimeNotFound()throws IOException, ServletException {

        String owner = "Pooja";
        AppointmentBook appointmentBook = new AppointmentBook(owner);
        appointmentBook.addAppointment(appointment);
        AppointmentBookServlet servlet = new AppointmentBookServlet();


        HttpServletRequest request1 = mock(HttpServletRequest.class);
        when(request1.getParameter(OWNER_NAME)).thenReturn(owner);
        when(request1.getParameter(DESCRIPTION)).thenReturn(description);
        when(request1.getParameter(START_TIME)).thenReturn(null);
        when(request1.getParameter(END_TIME)).thenReturn(endDateTime);

        HttpServletResponse queryresponse = mock(HttpServletResponse.class);

        try {
            servlet.doPost(request1, queryresponse);
        }
        catch ( InvalidParameterException e) {
            assertThat(e.getMessage(),equalTo("The required parameter \"start\" is missing"));
        }

    }


    @Test
    void checkDoPostThrowsErrorIfEndTimeNotFound()throws IOException, ServletException {

        String owner = "Pooja";
        AppointmentBook appointmentBook = new AppointmentBook(owner);
        appointmentBook.addAppointment(appointment);
        AppointmentBookServlet servlet = new AppointmentBookServlet();


        HttpServletRequest request1 = mock(HttpServletRequest.class);
        when(request1.getParameter(OWNER_NAME)).thenReturn(owner);
        when(request1.getParameter(DESCRIPTION)).thenReturn(description);
        when(request1.getParameter(START_TIME)).thenReturn(startDateTime);
        when(request1.getParameter(END_TIME)).thenReturn(null);

        HttpServletResponse queryresponse = mock(HttpServletResponse.class);

        try {
            servlet.doPost(request1, queryresponse);
        }
        catch ( InvalidParameterException e) {
            assertThat(e.getMessage(),equalTo("The required parameter \"end\" is missing"));
        }

    }
    @Test
    void deleteAllAppointments() throws IOException, ServletException {
        String owner = "Pooja";
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
        when(queryrequest.getParameter(OWNER_NAME)).thenReturn(queryownernotfound);
        when(queryrequest.getParameter(START_TIME)).thenReturn(queryStartDateTimenotfound);
        when(queryrequest.getParameter(END_TIME)).thenReturn(queryEndDateTimenotfound);
        HttpServletResponse queryresponse = mock(HttpServletResponse.class);
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw, true);
        when(queryresponse.getWriter()).thenReturn(pw);
        servlet.doDelete(queryrequest, queryresponse);
        String textappointment = sw.toString().replaceAll("[\\n\\t]","");
        assertThat(textappointment, equalTo("All appointment entries have been deleted"));
        verify(queryresponse).setStatus(HttpServletResponse.SC_OK);
    }
    @Test
    void gettingAppointmentBookReturnsTextFormat() throws ServletException, IOException {
        String owner = "Dave";
        String description = "Teach Java";

        AppointmentBookServlet servlet = new AppointmentBookServlet();
        AppointmentBook book = servlet.createAppointmentBook(owner);
        book.addAppointment(new Appointment(description,"10/10/2021 10:00 am", "10/10/2021 11:00 am"));

        Map<String, String> queryParams = Map.of("owner", owner);
        StringWriter sw = invokeServletMethod(queryParams, servlet::doGet);

        String text = sw.toString();
        assertThat(text, containsString(owner));
        assertThat(text, containsString(description));
    }

    private StringWriter invokeServletMethod(Map<String, String> params, ServletMethodInvoker invoker) throws IOException, ServletException {
        HttpServletRequest request = mock(HttpServletRequest.class);
        params.forEach((key, value) -> when(request.getParameter(key)).thenReturn(value));

        HttpServletResponse response = mock(HttpServletResponse.class);

        StringWriter sw = new StringWriter();
        when(response.getWriter()).thenReturn(new PrintWriter(sw));

        invoker.invoke(request, response);

        verify(response).setStatus(HttpServletResponse.SC_OK);
        return sw;
    }

    @Test
    void addAppointment() throws ServletException, IOException {
        AppointmentBookServlet servlet = new AppointmentBookServlet();

        String owner = "Dave";
        String description = "Teach Java";

        invokeServletMethod(Map.of("owner", owner, "description", description,"start","10/10/2021 10:00 am","end","10/10/2021 11:00 am"), servlet::doPost);

        AppointmentBook book = servlet.getAppointment(owner);
        assertThat(book, notNullValue());
        assertThat(book.getOwnerName(), equalTo(owner));

        Collection<Appointment> appointments = book.getAppointments();
        assertThat(appointments, hasSize(1));

        Appointment appointment = appointments.iterator().next();
        assertThat(appointment.getDescription(), equalTo(description));

    }

    private interface ServletMethodInvoker {
        void invoke(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException;
    }

}
