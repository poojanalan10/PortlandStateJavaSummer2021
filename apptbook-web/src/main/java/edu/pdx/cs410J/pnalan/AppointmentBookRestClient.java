package edu.pdx.cs410J.pnalan;

import com.google.common.annotations.VisibleForTesting;
import edu.pdx.cs410J.ParserException;
import edu.pdx.cs410J.web.HttpRequestHelper;
import java.io.IOException;
import java.io.StringReader;
import java.util.Map;
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

  /**
   * Returns all dictionary entries from the server
   * @return Messages.parseDictionary(response.getContent())
   */
  public Map<String, String> getAllDictionaryEntries() throws IOException {
    Response response = get(this.url, Map.of());
    return Messages.parseDictionary(response.getContent());
  }

  /**
   * Returns the definition for the given word
   * @return Messages.parseDictionaryEntry(content).getValue()
   */
  public String getDefinition(String word) throws IOException {
    Response response = get(this.url, Map.of("word", word));
    throwExceptionIfNotOkayHttpStatus(response);
    String content = response.getContent();
    return Messages.parseDictionaryEntry(content).getValue();
  }

 /* public void addDictionaryEntry(String word, String definition) throws IOException {
    Response response = postToMyURL(Map.of("word", word, "definition", definition));
    throwExceptionIfNotOkayHttpStatus(response);
  }
*/

  /**
   * Posts to URL
   * @param dictionaryEntries
   *        the entries given by user
   * @return post(this.url, dictionaryEntries)
   *          the posted URL
   * @throws IOException
   */
  @VisibleForTesting
  Response postToMyURL(Map<String, String> dictionaryEntries) throws IOException {
    return post(this.url, dictionaryEntries);
  }
/**
 * removes all dictionary entries
 */
  public void removeAllDictionaryEntries() throws IOException {
    Response response = delete(this.url, Map.of());
    throwExceptionIfNotOkayHttpStatus(response);
  }

  /**
   * This method throws an error when HTTP is not OK
   * @param response
   *        the response from the server
   * @return response
   *        the response from the server
   */
  private Response throwExceptionIfNotOkayHttpStatus(Response response) {
    int code = response.getCode();
    if (code != HTTP_OK) {
      String message = response.getContent();
      throw new RestException(code, message);
    }
    return response;
  }

  /**
   *
   * @param appointmentValues
   *        the input values
   * @return response.getContent();
   * @throws IOException
   */
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

  /**
   * Returns the found appointments
   * @param appointmentValues
   *        the input values
   * @return text
   * @throws IOException
   * @throws ParserException
   */
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

  /**
   *
   * @param owner
   *        the name of the owner of the appointment
   * @return parser.parse();
   * @throws IOException
   * @throws ParserException
   */
  public AppointmentBook getAppointments(String owner) throws IOException, ParserException {

    Response response = get(this.url, Map.of(OWNER_NAME, owner));
    if(response.getContent().contains("Not Found")){
      System.err.println(Messages.ownerHasNoAppointmentBook(owner));
      throwExceptionIfNotOkayHttpStatus(response);
      System.exit(1);
    }
    String text = response.getContent();
    TextParser parser = new TextParser(new StringReader(text));
    return parser.parse();

  }

  /**
   * Removes all appointments
   * @throws IOException
   */
  public void removeAllAppointmentBooks() throws IOException {
    Response response = delete(this.url, Map.of());
    throwExceptionIfNotOkayHttpStatus(response);
  }
}
