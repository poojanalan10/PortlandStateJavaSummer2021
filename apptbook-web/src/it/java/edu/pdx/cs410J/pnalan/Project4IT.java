package edu.pdx.cs410J.pnalan;

import edu.pdx.cs410J.InvokeMainTestCase;
import edu.pdx.cs410J.UncaughtExceptionInMain;
import edu.pdx.cs410J.web.HttpRequestHelper.RestException;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.MethodOrderer.MethodName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import java.io.IOException;
import java.net.HttpURLConnection;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;

/**
 * An integration test for {@link Project4} that invokes its main method with
 * various arguments
 */
@Disabled
@TestMethodOrder(MethodName.class)
class Project4IT extends InvokeMainTestCase {
    private static final String HOSTNAME = "localhost";
    private static final String PORT = System.getProperty("http.port", "8080");

  /*  @Test
    void test0RemoveAllMappings() throws IOException {
      AppointmentBookRestClient client = new AppointmentBookRestClient(HOSTNAME, Integer.parseInt(PORT));
      client.removeAllDictionaryEntries();
    }
*/

    @Test
    void test1NoCommandLineArguments() {
        MainMethodResult result = invokeMain( Project4.class );
        assertThat(result.getExitCode(), equalTo(1));
        assertThat(result.getTextWrittenToStandardError(), containsString(Project4.MISSING_ARGS));
    }


    @Test
    void test3NoAppointmentBooksT() {
        String owner = "Maggie";
        MainMethodResult result = invokeMain(Project4.class, HOSTNAME, PORT, owner);
        assertThat(result.getTextWrittenToStandardError(), containsString(Messages.ownerHasNoAppointmentBook(owner)));
        assertThat(result.getExitCode(), equalTo(0));
        }


    @Disabled
    @Test
    void test4AddAppointment() {
        String owner = "Pooja";
        String description = "Still teaching java";
        MainMethodResult result = invokeMain( Project4.class, HOSTNAME, PORT, owner, description );
        assertThat(result.getTextWrittenToStandardError(), result.getExitCode(), equalTo(1));
        result = invokeMain( Project4.class, HOSTNAME, PORT, owner );
        String out = result.getTextWrittenToStandardOut();
        assertThat(out, out, containsString(owner));
        assertThat(out, out, containsString(description));
    }
}