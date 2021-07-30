package edu.pdx.cs410J.pnalan;

import com.google.common.annotations.VisibleForTesting;
import edu.pdx.cs410J.ParserException;
import edu.pdx.cs410J.web.HttpRequestHelper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringReader;
import java.util.Map;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import static edu.pdx.cs410J.pnalan.AppointmentBookServlet.*;
import static java.net.HttpURLConnection.HTTP_OK;

/**
 * A helper class for accessing the rest client.  Note that this class provides
 * an example of how to make gets and posts to a URL.  You'll need to change it
 * to do something other than just send dictionary entries.
 */
public class AppointmentBookRestClient extends HttpRequestHelper {
  private static final String WEB_APP = "apptbook";
  private static final String SERVLET = "appointments";
  static final String OWNER_NAME = "owner";
  static final String DESCRIPTION = "description";
  static final String START_TIME = "start";
  static final String END_TIME = "end";

  private final String url;


  /**
   * Creates a client to the appointment book REST service running on the given host and port
   *
   * @param hostName The name of the host
   * @param port     The port
   */
  public AppointmentBookRestClient(String hostName, int port) {
    this.url = String.format("http://%s:%d/%s/%s", hostName, port, WEB_APP, SERVLET);
  }
/*
  /**
   * Returns all dictionary entries from the server
   */
 /* public Map<String, String> getAllDictionaryEntries() throws IOException {
    Response response = get(this.url, Map.of());
    return Messages.parseDictionary(response.getContent());
  }

  /**
   * Returns the definition for the given word
   */
  /*public String getDefinition(String word) throws IOException {
    Response response = get(this.url, Map.of("word", word));
    throwExceptionIfNotOkayHttpStatus(response);
    String content = response.getContent();
    return Messages.parseDictionaryEntry(content).getValue();
  }

  public void addDictionaryEntry(String word, String definition) throws IOException {
    Response response = postToMyURL(Map.of("word", word, "definition", definition));
    throwExceptionIfNotOkayHttpStatus(response);
  }
*/
  @VisibleForTesting
  Response postToMyURL(Map<String, String> dictionaryEntries) throws IOException {
    return post(this.url, dictionaryEntries);
  }

  /*public void removeAllDictionaryEntries() throws IOException {
    Response response = delete(this.url, Map.of());
    throwExceptionIfNotOkayHttpStatus(response);
  }*/

  private Response throwExceptionIfNotOkayHttpStatus(Response response) {
    int code = response.getCode();
    if (code != HTTP_OK) {
      String message = response.getContent();
      throw new RestException(code, message);
    }
    return response;
  }

  public String addAppointment(final String[] appointmentValues) throws IOException {
    String owner = appointmentValues[0];
    String description =appointmentValues[1];
    String start = appointmentValues[2];
    String end = appointmentValues[3];
    Response response = postToMyURL(Map.of(OWNER_NAME,owner, DESCRIPTION,description, START_TIME, start,
            END_TIME, end));
    throwExceptionIfNotOkayHttpStatus(response);
    return response.getContent();
  }

  public String findAppointment(final String[] appointmentValues) throws IOException, ParserException {
    Response response = null;
    if(appointmentValues.length == 1){
       response = get(this.url, Map.of(OWNER_NAME, appointmentValues[0]));
    }
    else {
       response = get(this.url, Map.of(OWNER_NAME, appointmentValues[0],
              START_TIME, appointmentValues[1],
              END_TIME, appointmentValues[2]));
    }
    throwExceptionIfNotOkayHttpStatus(response);
    String text = response.getContent();


    return text;

  }
  public AppointmentBook getAppointments(String owner) throws IOException, ParserException {

    Response response = get(this.url, Map.of(OWNER_NAME, owner));

    throwExceptionIfNotOkayHttpStatus(response);
    String text = response.getContent();
    TextParser parser = new TextParser(new StringReader(text));
    return parser.parse();

  }


  public void removeAllAppointmentBooks() throws IOException {
    Response response = delete(this.url, Map.of());
    throwExceptionIfNotOkayHttpStatus(response);
  }
}
