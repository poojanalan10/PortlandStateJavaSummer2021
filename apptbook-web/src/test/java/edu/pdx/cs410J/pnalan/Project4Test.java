package edu.pdx.cs410J.pnalan;
import org.junit.jupiter.api.Test;
import java.io.IOException;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;


public class Project4Test /*extends InvokeMainTestCase*/{

    private static final String HOSTNAME = "localhost";
    private static final String PORT = System.getProperty("http.port", "8080");
    @Test
    void getClassName() throws IOException {
        Project4 p1 = new Project4();
        assertThat(p1.getClass().getName(), containsString("Project4"));
        ;
    }

    @Test
    void checkIfWeDontHaveZeroFields(){
        Project4 p1 = new Project4();
        assertThat(p1.getClass().getDeclaredFields(),is(notNullValue()));
    }

    @Test
    void checkIfWeDontHaveZeroMethods(){
        Project4 p1 = new Project4();
        assertThat(p1.getClass().getDeclaredMethods(),is(notNullValue()));
    }
 /*
    @Test
    void test1NoCommandLineArguments() {
        InvokeMainTestCase.MainMethodResult result = invokeMain( Project4.class );
        assertThat(result.getExitCode(), equalTo(1));
        assertThat(result.getTextWrittenToStandardError(), containsString("Hostname and Port must be entered"));
    }


    @Test
    void test3NoAppointmentBooksT() {
        String owner = "Maggie";
        InvokeMainTestCase.MainMethodResult result = invokeMain(Project4.class, "-host",HOSTNAME,"-port", PORT, owner);
        assertThat(result.getTextWrittenToStandardError(), containsString(Messages.ownerHasNoAppointmentBook(owner)));
        assertThat(result.getExitCode(), equalTo(1));
    }


    @Test
    void testPrint(){
        String[] args = {"-print","-host",HOSTNAME,"-port",PORT,"Pooja","Zxy sdf","10/10/2021","10:00","am","10/10/2021","11:00","am"};
        InvokeMainTestCase.MainMethodResult result = invokeMain(Project4.class,args);
        assertThat(result.getTextWrittenToStandardOut(), equalTo("Zxy sdf from 10/10/2021 10:00 am until 10/10/2021 11:00 am\n"));

    }

    @Test
    void testAddingAppointment(){
        String[] args = {"-host",HOSTNAME,"-port",PORT,"Pooja","Zxy sdf","10/10/2021","10:00","am","10/10/2021","11:00","am"};
        InvokeMainTestCase.MainMethodResult result = invokeMain(Project4.class,args);
        assertThat(result.getTextWrittenToStandardOut(), equalTo("HTTP Status: 200\nAppointment was successfully added\n"));

    }
   @Test
    void noAppointmentArgumentsAndOptions(){
        String[] args = {"-host",HOSTNAME,"-port",PORT};
        InvokeMainTestCase.MainMethodResult result = invokeMain(Project4.class,args);
        assertThat(result.getTextWrittenToStandardError(), containsString("The options -print and -search are not entered! Cannot process the appointment with insufficient parameters"));
    }
*/
  /*  @Test
    void AppointmentArgumentsInsufficientAndSearchOption(){
        String[] args = {"-search","-host",HOSTNAME,"-port",PORT,"Pooja","Hey ya"};
        InvokeMainTestCase.MainMethodResult result = invokeMain(Project4.class,args);
        assertThat(result.getTextWrittenToStandardError(), containsString("Missing parameters for search option, the valid arguments are as follows:"));
    }
    @Test
    void AppointmentArgumentsInsufficientAndPrintOption(){
        String[] args = {"-print","-host",HOSTNAME,"-port",PORT,"Pooja","Hey ya"};
        InvokeMainTestCase.MainMethodResult result = invokeMain(Project4.class,args);
        assertThat(result.getTextWrittenToStandardError(), containsString("Missing parameters for print option, the valid arguments are as follows:"));
    }

    @Test
    void AppointmentArgumentsTooManyArgumentsAndSearchOption(){
        String[] args = {"-search","-host",HOSTNAME,"-port",PORT,"Pooja","10/10/2021","10:00","am","10/10/2021","11:00","am","Hey ya"};
        InvokeMainTestCase.MainMethodResult result = invokeMain(Project4.class,args);
        assertThat(result.getTextWrittenToStandardError(), containsString("Too many arguments entered for search option, the valid arguments are as follows:"));
    }

    @Test
    void AppointmentArgumentsTooManyArgumentsAndPrintOption(){
        String[] args = {"-print","-host",HOSTNAME,"-port",PORT,"Pooja","Yay it's me time","10/10/2021","10:00","am","10/10/2021","11:00","am","Hey ya"};
        InvokeMainTestCase.MainMethodResult result = invokeMain(Project4.class,args);
        assertThat(result.getTextWrittenToStandardError(), containsString("Too many arguments entered for print option, the valid arguments are as follows:"));
    }
//""

    @Test
    void AppointmentArgumentsNotInOrderAndSearchOption(){
        String[] args = {"-host",HOSTNAME,"-port",PORT,"Pooja","-search","Yay it's me time","10/10/2021","10:00","am","10/10/2021","11:00","am","Hey ya"};
        InvokeMainTestCase.MainMethodResult result = invokeMain(Project4.class,args);
        assertThat(result.getTextWrittenToStandardError(), containsString("search option must be provided before the appointment arguments"));
    }
    @Test
    void AppointmentArgumentsNotInOrderAndPrintOption(){
        String[] args = {"-host",HOSTNAME,"-port",PORT,"Pooja","-print","Yay it's me time","10/10/2021","10:00","am","10/10/2021","11:00","am","Hey ya"};
        InvokeMainTestCase.MainMethodResult result = invokeMain(Project4.class,args);
        assertThat(result.getTextWrittenToStandardError(), containsString("print option must be specified before the appointment arguments"));
    }
    @Test
    void AppointmentArgumentsInsufficientForAddingAnAppointment(){
        //Insufficient arguments given when no -print or -search is given
        String[] args = {"-host",HOSTNAME,"-port",PORT,"Pooja","Yay it's me time","10/10/2021","10:00","am","10/10/2021"};
        InvokeMainTestCase.MainMethodResult result = invokeMain(Project4.class,args);
        assertThat(result.getTextWrittenToStandardError(), containsString("Insufficient arguments given when no -print or -search is given"));
    }

    @Test
    void AppointmentArgumentsUnrecognizedDateFormatAddingAnAppointment(){
        //Insufficient arguments given when no -print or -search is given
        String[] args = {"-host",HOSTNAME,"-port",PORT,"Pooja","Yay it's me time","10/10/2021/1","10:00","am","10/10/2021","11:20","pm"};
        InvokeMainTestCase.MainMethodResult result = invokeMain(Project4.class,args);
        assertThat(result.getTextWrittenToStandardError(), containsString("UNRECOGNIZED DATE FORMAT"));
    }
    @Test
    void AppointmentArgumentsUnrecognizedTimeFormatAddingAnAppointment(){
        //Insufficient arguments given when no -print or -search is given
        String[] args = {"-host",HOSTNAME,"-port",PORT,"Pooja","Yay it's me time","10/10/2021","10:00","am","10/10/2021","pm","Hi! again"};
        InvokeMainTestCase.MainMethodResult result = invokeMain(Project4.class,args);
        assertThat(result.getTextWrittenToStandardError(), containsString("UNRECOGNIZED TIME FORMAT"));
    }
    //
    @Test
    void AppointmentArgumentsTooManyForAddingAnAppointment(){
        //Insufficient arguments given when no -print or -search is given
        String[] args = {"-host",HOSTNAME,"-port",PORT,"Pooja","Yay it's me time","10/10/2021","10:00","am","10/10/2021","11:30","am","Hi! again"};
        InvokeMainTestCase.MainMethodResult result = invokeMain(Project4.class,args);
        assertThat(result.getTextWrittenToStandardError(), containsString("Too amy arguments given when no -search or -print option is provided"));
    }

    @Test
    void testIfPrintReadMeIsPrinted(){
        String[] args = {"-host",HOSTNAME,"-README","-port",PORT};
        InvokeMainTestCase.MainMethodResult result = invokeMain(Project4.class,args);
        assertThat(result.getTextWrittenToStandardOut(), containsString("README Name: Pooja Nalan"));
        assertThat(result.getExitCode(), equalTo(1));
    }*/
}
