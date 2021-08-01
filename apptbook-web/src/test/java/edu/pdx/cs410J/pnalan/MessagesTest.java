package edu.pdx.cs410J.pnalan;

import org.junit.jupiter.api.Test;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

class MessagesTest {

  @Test
  void malformedWordAndDefinitionReturnsNull() {
    assertThat(Messages.parseDictionaryEntry("blah"), nullValue());
  }

  @Test
  void canParseFormattedDictionaryEntryPair() {
    String word = "testWord";
    String definition = "testDefinition";
    String formatted = Messages.formatDictionaryEntry(word, definition);
    Map.Entry<String, String> parsed = Messages.parseDictionaryEntry(formatted);
    assertThat(parsed, notNullValue());
    assertThat(parsed.getKey(), equalTo(word));
    assertThat(parsed.getValue(), equalTo(definition));
  }

  @Test
  void canParseFormattedDictionaryEntryWithoutLeadingSpaces() {
    String word = "testWord";
    String definition = "testDefinition";
    String formatted = Messages.formatDictionaryEntry(word, definition);
    String trimmed = formatted.trim();
    Map.Entry<String, String> parsed = Messages.parseDictionaryEntry(trimmed);
    assertThat(parsed, notNullValue());
    assertThat(parsed.getKey(), equalTo(word));
    assertThat(parsed.getValue(), equalTo(definition));

  }

  @Test
  void nullDefinitionIsParsedAsNull() {
    String word = "testWord";
    String formatted = Messages.formatDictionaryEntry(word, null);
    Map.Entry<String, String> parsed = Messages.parseDictionaryEntry(formatted);
    assertThat(parsed, notNullValue());
    assertThat(parsed.getKey(), equalTo(word));
    assertThat(parsed.getValue(), equalTo(null));
  }

  @Test
  void canParseFormattedDictionary() {
    Map<String, String> dictionary = new HashMap<>();

    for (int i = 0; i < 5; i++) {
      String word = String.valueOf(i);
      String definition = "QQ" + word;
      dictionary.put(word, definition);
    }

    StringWriter sw = new StringWriter();
    Messages.formatDictionaryEntries(new PrintWriter(sw, true), dictionary);

    String formatted = sw.toString();

    Map<String, String> actual = Messages.parseDictionary(formatted);
    assertThat(actual, equalTo(dictionary));
  }

  @Test
  void noAppointmentsForAOwnerTest(){
    assertThat(Messages.ownerHasNoAppointmentBook("Devi"),containsString("'Devi' does not have an appointment book"));
  }

  @Test
  void startTimeBeforeEndTimeTest(){
    assertThat(Messages.startTimeEnteredBeforeEndTime(),containsString("Start date/time cannot be greater than end date/time"));
  }

  @Test
  void addedAppointmentTest(){
    assertThat(Messages.addedAppointment(new Appointment("A check for adding appoint! Ok Bye!","07/30/2021 11:30 pm","07/31/2021 02:00 am")),containsString("Appointment was successfully added"));
  }

  @Test
  void malformattedDateTimeCheck(){
    assertThat(Messages.malformattedDateTime("10:345 am","07/31/2021"), containsString("Given 10:345 am is not in the correct format. Follow the format DateTime: MM/dd/yyy hh:mm am/pm"));
  }
  @Test
  void formatAppointmentCountCheck(){
    assertThat(Messages.formatAppointmentCount(0),containsString("Appointment book on server contains 0 appointments"));
  }
}
