package edu.pdx.cs410J.pnalan;

import edu.pdx.cs410J.ParserException;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.MethodOrderer.MethodName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.fail;

/**
 * Integration test that tests the REST calls made by {@link AppointmentBookRestClient}
 */
@Disabled
@TestMethodOrder(MethodName.class)
class AppointmentBookRestClientIT {

  String owner = "Pooja";
  String description = " doctor visit";
  String startDateTime = "01/01/2020 1:10 am";
  String endDateTime = "01/01/2020 3:40 am";

  String owner2 = "Pooja";
  String description2 = "Class";
  String startDateTime2 = "02/01/2020 9:10 am";
  String endDateTime2 = "02/01/2021 10:40 am";

  private String queryowner = "Pooja";
  private String queryStartDateTime = "01/01/2020 1:00 am";
  private String queryEndDateTime = "01/01/2020 04:00 am";

  private String queryownernotfound = "Pooja";
  private String queryStartDateTimenotfound = "10/01/2020 1:00 am";
  private String queryEndDateTimenotfound = "10/01/2020 04:00 am";

  private static final String HOSTNAME = "localhost";
  private static final String PORT = System.getProperty("http.port", "8080");
  Appointment appointment = new Appointment(description, startDateTime, endDateTime);
  Appointment appointment2 = new Appointment(description2,startDateTime2,endDateTime2);

  private AppointmentBookRestClient newAppointmentBookRestClient() {
    int port = Integer.parseInt(PORT);
    return new AppointmentBookRestClient(HOSTNAME, port);
  }

  @Test
  void test0RemoveAllDictionaryEntries() throws IOException {
    AppointmentBookRestClient client = newAppointmentBookRestClient();
    client.removeAllDictionaryEntries();
  }

  @Test
  void test1EmptyServerContainsNoDictionaryEntries() throws IOException {
    AppointmentBookRestClient client = newAppointmentBookRestClient();
    Map<String, String> dictionary = client.getAllDictionaryEntries();
    assertThat(dictionary.size(), equalTo(0));
  }
  

  @Test
  public void testNonExistantAppointmentBookThrowsError() throws ParserException, IOException {
    AppointmentBookRestClient client = newAppointmentBookRestClient();
  try {
    client.getAppointments("Nalan");
    fail("Should have thrown a AppointmentBookRestClientException");
    }
  catch (AppointmentBookRestClient.RestException ex){
    assertThat(ex.getHttpStatusCode(),equalTo(HttpURLConnection.HTTP_NOT_FOUND));
    }

  }
  @Test
  public void addAppointmentAndGetAllAppointments() throws ParserException, IOException {
    AppointmentBookRestClient client = newAppointmentBookRestClient();
    client.removeAllAppointmentBooks();
    String[] args = {owner,description,startDateTime,endDateTime};
    String status = client.addAppointment(args);
    assertThat(status, equalTo("HTTP Status: 200\nAppointment was successfully added"));
    AppointmentBook appointments = client.getAppointments(owner);
    System.out.println(appointments);
    assertThat(appointments.getAppointments().stream().findFirst().toString(),containsString(description));
    assertThat(appointments.getAppointments().stream().findFirst().toString(),containsString(startDateTime));
    assertThat(appointments.getAppointments().stream().findFirst().toString(),containsString(endDateTime));
  }

  @Test
  public void findAppointmentsCheck() throws ParserException, IOException {
    AppointmentBookRestClient client = newAppointmentBookRestClient();
    client.removeAllAppointmentBooks();
    String[] args1 = {owner,description,startDateTime,endDateTime};
    String[] args2 = {owner2,description2,startDateTime2,endDateTime2};
    String[] queryargs = {queryowner,queryStartDateTime,queryEndDateTime};
    String status1 = client.addAppointment(args1);
    status1 = client.addAppointment(args2);

    assertThat(status1, equalTo("HTTP Status: 200\nAppointment was successfully added"));
    String appointments = client.findAppointment(queryargs);
    System.out.println(appointments);
    assertThat(appointments,containsString(description));
    assertThat(appointments,containsString(startDateTime));
    assertThat(appointments,containsString(endDateTime));

    assertNotEquals(appointments,containsString(description2));
    assertNotEquals(appointments,containsString(startDateTime2));
    assertNotEquals(appointments,containsString(endDateTime2));

  }

  @Test
  public void findAppointmentsEmpty() throws IOException, ParserException {
    AppointmentBookRestClient client = newAppointmentBookRestClient();
    client.removeAllAppointmentBooks();
    String[] args1 = {owner,description,startDateTime,endDateTime};
    String[] args2 = {owner2,description2,startDateTime2,endDateTime2};
    String[] queryargsnotfound = {queryownernotfound,queryStartDateTimenotfound,queryEndDateTimenotfound};
    String status1 = client.addAppointment(args1);
    status1 = client.addAppointment(args2);

    assertThat(status1, equalTo("HTTP Status: 200\nAppointment was successfully added"));
    String appointments = client.findAppointment(queryargsnotfound);
    System.out.println(appointments);
    assertThat(appointments, equalTo("Appointments between range doesn't exist!"));
  }

  @Test
  public void clientDoesNotThrowConnectionError(){
    AppointmentBookRestClient client = newAppointmentBookRestClient();
    assertThat(client.toString(),containsString("AppointmentBookRestClient"));
  }


}
