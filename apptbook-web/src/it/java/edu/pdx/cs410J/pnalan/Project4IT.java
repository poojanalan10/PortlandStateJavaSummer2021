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



    @Test
    void test1NoCommandLineArguments() {
        MainMethodResult result = invokeMain( Project4.class );
        assertThat(result.getExitCode(), equalTo(1));
        assertThat(result.getTextWrittenToStandardError(), containsString("Hostname and Port must be entered"));
    }


    @Test
    void test3NoAppointmentBooksT() {
        String owner = "Maggie";
        MainMethodResult result = invokeMain(Project4.class, "-host",HOSTNAME,"-port", PORT, owner);
        assertThat(result.getTextWrittenToStandardError(), containsString(Messages.ownerHasNoAppointmentBook(owner)));
        assertThat(result.getExitCode(), equalTo(1));
        }


    @Test
    void testPrint(){
        String[] args = {"-print","-host",HOSTNAME,"-port",PORT,"Pooja","Zxy sdf","10/10/2021","10:00","am","10/10/2021","11:00","am"};
        MainMethodResult result = invokeMain(Project4.class,args);
        assertThat(result.getTextWrittenToStandardOut(), equalTo("Zxy sdf from 10/10/2021 10:00 am until 10/10/2021 11:00 am\n"));

    }

    @Test
    void testAddingAppointment(){
        String[] args = {"-host",HOSTNAME,"-port",PORT,"Pooja","Zxy sdf","10/10/2021","10:00","am","10/10/2021","11:00","am"};
        MainMethodResult result = invokeMain(Project4.class,args);
        assertThat(result.getTextWrittenToStandardOut(), equalTo("HTTP Status: 200\nAppointment was successfully added\n"));

    }

}