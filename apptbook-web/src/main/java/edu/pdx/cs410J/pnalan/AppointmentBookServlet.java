package edu.pdx.cs410J.pnalan;

import com.google.common.annotations.VisibleForTesting;
import edu.pdx.cs410J.ParserException;
import edu.pdx.cs410J.web.HttpRequestHelper;
import org.checkerframework.checker.units.qual.A;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.security.InvalidParameterException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Pattern;

/**
 * This servlet ultimately provides a REST API for working with an
 * <code>AppointmentBook</code>.  However, in its current state, it is an example
 * of how to use HTTP and Java servlets to store simple dictionary of words
 * and their definitions.
 */
public class AppointmentBookServlet extends HttpServlet
{
    static final String OWNER_NAME = "owner";
    static final String DESCRIPTION = "description";
    static final String START_TIME = "start";
    static final String END_TIME = "end";

    private final Map<String, String> dictionary = new HashMap<>();
    private Map<String, AppointmentBook> books = new HashMap<>();

    /**
     * Handles an HTTP GET request from a client by writing the definition of the
     * word specified in the "word" HTTP parameter to the HTTP response.  If the
     * "word" parameter is not specified, all of the entries in the dictionary
     * are written to the HTTP response.
     */
    @Override
    protected void doGet( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException
    {
        response.setContentType( "text/plain" );
        String owner = getParameter(OWNER_NAME,request);
        String start = getParameter(START_TIME,request);
        String end = getParameter(END_TIME,request);
        if(owner == null){
            missingRequiredParameter(response, OWNER_NAME);
            return;
        }
        else if (owner != null && start == null && end == null) {
                writeAppointments(owner,response);
          //  getAppointments(request,response);
                return;
            }

        else if (owner != null && end != null && start != null) {
            try {
                findAppointments(request,response);
               // System.out.println("Printing all the appointments:");
              //  getAppointments(request,response);
             //   writePrettyAppointmentWithinRange(request,response);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            return;
            }

    }
  /*  public void getAppointments(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String owner = getParameter(OWNER_NAME, request);
        AppointmentBook appointmentBook = books.get(owner);
        if(appointmentBook == null){
            response.sendError(HttpServletResponse.SC_NOT_FOUND, Messages.ownerHasNoAppointmentBook(owner));
            System.err.println(Messages.ownerHasNoAppointmentBook(owner));
            return;
        }
        AppointmentBookPrettyPrinter prettyPrinter = new AppointmentBookPrettyPrinter();
        String pretty = prettyPrinter.getPrettyAppointments(appointmentBook);
        //PrintWriter pw = response.getWriter();
       // pw.println(pretty);
        System.out.println(pretty);
    }
    */
    /**
     * Handles an HTTP POST request by storing the dictionary entry for the
     * "word" and "definition" request parameters.  It writes the dictionary
     * entry to the HTTP response.
     */
    @Override
    protected void doPost( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException
    {
       response.setContentType( "text/plain" );
        ArrayList<String> list = new ArrayList<>();
        String owner = getParameter(OWNER_NAME,request);
        if(owner == null){
            missingRequiredParameter(response, OWNER_NAME);
            return;
        }
        String description = getParameter(DESCRIPTION, request);
        if(description == null){
            missingRequiredParameter(response, DESCRIPTION);
            return;
        }

        String start = getParameter(START_TIME, request);
        if(start == null){
            missingRequiredParameter(response,START_TIME);
            return;
        }

        String end = getParameter(END_TIME, request);
       if(end == null){
            missingRequiredParameter(response, END_TIME);
            return;
        }

        AppointmentBook book = this.books.get(owner);
        if(book == null){
            book = createAppointmentBook(owner);
        }
        Appointment appointment = new Appointment(description,start,end);
        book.addAppointment(appointment);
        response.setStatus(HttpServletResponse.SC_OK);
        String message = "HTTP Status: " + response.getStatus() + "\n" + Messages.addedAppointment(appointment);
        PrintWriter pw = response.getWriter();
        pw.println(message);
        pw.flush();

     /*   list.add(getParameter(OWNER_NAME,request));
        list.add(getParameter(DESCRIPTION,request));
        list.add(getParameter(START_TIME,request));
        list.add(getParameter(END_TIME,request));
        String parameter = "";
        for(int i = 0; i < list.size(); i++){
            if(list.get(i) == null){
                parameter = ( i == 0)? OWNER_NAME
                        : (i == 1)? DESCRIPTION
                        : (i == 2)? START_TIME
                        : END_TIME;
                return;
            }
        }
        if(validateDates(request,response)) {
            Appointment appointment = new Appointment(list.get(1).toString(), list.get(2).toString(), list.get(3).toString());
            if (this.books.get(list.get(0)) == null) {
                this.books.put(list.get(0), new AppointmentBook(list.get(0), appointment));
            } else {
                var appointmentbook = this.books.get(list.get(0));
                appointmentbook.addAppointment(appointment);
                this.books.put(list.get(0), appointmentbook);
            }
            response.setStatus(HttpServletResponse.SC_OK);
            String message = "HTTP Status: " + response.getStatus() + "\n" + Messages.addedAppointment(appointment);
            PrintWriter pw = response.getWriter();
            pw.println(message);
            pw.flush();
        }
*/
      /*  String word = getParameter(WORD_PARAMETER, request );
        if (word == null) {
            missingRequiredParameter(response, WORD_PARAMETER);
            return;
        }

        String definition = getParameter(DEFINITION_PARAMETER, request );
        if ( definition == null) {
            missingRequiredParameter( response, DEFINITION_PARAMETER );
            return;
        }

        this.dictionary.put(word, definition);

        PrintWriter pw = response.getWriter();
        pw.println(Messages.definedWordAs(word, definition));
        pw.flush();

        response.setStatus( HttpServletResponse.SC_OK);*/
    }

    /**
     * Handles an HTTP DELETE request by removing all dictionary entries.  This
     * behavior is exposed for testing purposes only.  It's probably not
     * something that you'd want a real application to expose.
     */
    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/plain");

        this.dictionary.clear();

        PrintWriter pw = response.getWriter();
        pw.println(Messages.allDictionaryEntriesDeleted());
        pw.flush();

        response.setStatus(HttpServletResponse.SC_OK);

    }

    /**
     * Writes an error message about a missing parameter to the HTTP response.
     *
     * The text of the error message is created by {@link Messages#missingRequiredParameter(String)}
     */
    private void missingRequiredParameter( HttpServletResponse response, String parameterName )
            throws IOException
    {
        String message = Messages.missingRequiredParameter(parameterName);
        response.sendError(HttpServletResponse.SC_PRECONDITION_FAILED, message);
    }

    /**
     * Writes the definition of the given word to the HTTP response.
     *
     * The text of the message is formatted with
     * {@link Messages#formatDictionaryEntry(String, String)}
     */
    private void writeDefinition(String word, HttpServletResponse response) throws IOException {
        String definition = this.dictionary.get(word);

        if (definition == null) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);

        } else {
            PrintWriter pw = response.getWriter();
            pw.println(Messages.formatDictionaryEntry(word, definition));

            pw.flush();

            response.setStatus(HttpServletResponse.SC_OK);
        }
    }



    private void writeAppointments(String owner, HttpServletResponse response) throws IOException {
        AppointmentBook appointmentBook = this.books.get(owner);
        if(appointmentBook == null){
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        }else{
            PrintWriter pw = response.getWriter();
            TextDumper dumper = new TextDumper(pw);
            dumper.dump(appointmentBook);
            pw.flush();
            response.setStatus(HttpServletResponse.SC_OK);

        }

    }

    /**
     * Writes all of the dictionary entries to the HTTP response.
     *
     * The text of the message is formatted with
     * {@link Messages#formatDictionaryEntry(String, String)}
     */
   /* private void writeAllDictionaryEntries(HttpServletResponse response ) throws IOException
    {
        PrintWriter pw = response.getWriter();
        Messages.formatDictionaryEntries(pw, dictionary);

        pw.flush();

        response.setStatus( HttpServletResponse.SC_OK );
    }
*/
    private void findAppointments(HttpServletRequest request, HttpServletResponse response) throws IOException, ParseException{
        String owner = getParameter(OWNER_NAME, request);
        AppointmentBook appointmentBook = books.get(owner);
        if(appointmentBook == null){
            response.sendError(HttpServletResponse.SC_NOT_FOUND, Messages.ownerHasNoAppointmentBook(owner));
            return;
        }

        var startDateTime = (getParameter(START_TIME,request));
        var endDateTime = (getParameter(END_TIME, request));
        try{
            AppointmentBook appbookFind = appointmentBook.findAppointmentsWithDateRange(startDateTime,endDateTime);
            if( appbookFind.getAppointments().isEmpty()){
                PrintWriter pw = response.getWriter();
                pw.println("Appointments between range doesn't exist!");
                pw.flush();
                response.setStatus(HttpServletResponse.SC_OK);
            }
            else {
                PrintWriter pw = response.getWriter();
                TextDumper dumper = new TextDumper(pw);
                dumper.dump(appbookFind);
                pw.flush();
                response.setStatus(HttpServletResponse.SC_OK);


            }

        }
        catch (InvalidParameterException e){
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
        }

    }

/*
    private void writePrettyAppointmentWithinRange(HttpServletRequest request,HttpServletResponse response) throws IOException, ParseException {
        String owner = getParameter(OWNER_NAME, request);
        AppointmentBook appointmentBook = books.get(owner);
        if(appointmentBook == null){
            response.sendError(HttpServletResponse.SC_NOT_FOUND, Messages.ownerHasNoAppointmentBook(owner));
            return;
        }

        var startDateTime = (getParameter(START_TIME,request));
        var endDateTime = (getParameter(END_TIME, request));
        AppointmentBook appbookFind = appointmentBook.findAppointmentsWithDateRange(startDateTime,endDateTime);
        if( appbookFind.getAppointments().isEmpty()){
           // PrintWriter pw = response.getWriter();
           // pw.println("Appointments between range doesn't exist!");
            //pw.flush();
            System.out.println("Appointments between range doesn't exist!");
            response.setStatus(HttpServletResponse.SC_OK);
        }
        else {
            AppointmentBookPrettyPrinter prettyPrinter = new AppointmentBookPrettyPrinter();
            String pretty = prettyPrinter.getPrettyAppointments(appointmentBook);
            //PrintWriter pw = response.getWriter();
            // pw.println(pretty);
            System.out.println(pretty);
        }

    }*/

    /*private boolean validateDates(HttpServletRequest request, HttpServletResponse response) throws IOException{
        String start = getParameter(START_TIME,request);
        String end = getParameter(END_TIME,request);
        String dateregex = "^(1[0-2]|0[1-9])/(3[01]|[12][0-9]|0[1-9])/[0-9]{4}\\s(([0-1]?[0-9]|2[0-3]):[0-5][0-9]\\s[PpAa][Mm])$";
        if (!Pattern.matches(dateregex,start)) {
            response.sendError(HttpServletResponse.SC_NOT_ACCEPTABLE, Messages.malformattedDateTime(START_TIME,start));
            return false;
        }
        if(!Pattern.matches(dateregex,end)){
            response.sendError(HttpServletResponse.SC_NOT_ACCEPTABLE, Messages.malformattedDateTime(END_TIME,end));
            return false;
        }
        Date startTime = getDateAndTime(start);
        Date endTime = getDateAndTime(end);
        if(!startTime.before(endTime)) {
            response.sendError(HttpServletResponse.SC_NOT_ACCEPTABLE, Messages.startTimeEnteredBeforeEndTime());
            return false;
        }
        return true;

    }*/

    AppointmentBook getAppointment(String owner){
        return this.books.get(owner);
    }


    void addAppointmentBook(AppointmentBook appbook){
        this.books.put(appbook.getOwnerName(), appbook);
    }

    /**
     * Returns the value of the HTTP request parameter with the given name.
     *
     * @return <code>null</code> if the value of the parameter is
     *         <code>null</code> or is the empty string
     */
    private String getParameter(String name, HttpServletRequest request) {
        String value = request.getParameter(name);
        if (value == null || "".equals(value)) {
            return null;

        } else {
            return value;
        }
    }

    /*private Date getDateAndTime(String dateTime){
        try{
            SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy hh:mm a");
            Date date = dateFormat.parse(dateTime);
            return date;
        }
        catch (ParseException e){
            System.err.println("Date time is not in the expected format of MM/dd/yyyy hh:mm");
        }
        return null;
    }

    @VisibleForTesting
    String getDefinition(String word) {
        return this.dictionary.get(word);
    }
*/
    public AppointmentBook createAppointmentBook(String owner){
        AppointmentBook book = new AppointmentBook(owner);
        this.books.put(owner, book);
        return book;
    }
}
