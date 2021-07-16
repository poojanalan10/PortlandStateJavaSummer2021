package edu.pdx.cs410J.pnalan;

import edu.pdx.cs410J.InvokeMainTestCase;
import edu.pdx.cs410J.ParserException;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class Project2IT extends InvokeMainTestCase{
    /**
     * Invokes the main method of {@link Project2} with the given arguments.
     */
    private InvokeMainTestCase.MainMethodResult invokeMain(String... args) {
        return invokeMain( Project2.class, args );
    }

    /**
     * Tests that invoking the main method with no arguments issues an error
     */
    @Test
    void testNoCommandLineArguments() {
        InvokeMainTestCase.MainMethodResult result = invokeMain(Project2.class);

        assertThat(result.getTextWrittenToStandardError(), containsString("Missing command line arguments"));
        assertThat(result.getTextWrittenToStandardError(),containsString(Project2.USAGE_MESSAGE));
        //assertThat(result.getExitCode(), equalTo(1));
    }

    @Test
    public void invokingMainWithoutArgumentsHasExitCodeOf1(){
        InvokeMainTestCase.MainMethodResult result = invokeMain(Project2.class);
        assertThat(result.getExitCode(),equalTo(1));
    }
    @Test
    public void invokingMainWithNoArgumentsPrintsMissingArgumentsToStandardError(){
        InvokeMainTestCase.MainMethodResult result = invokeMain(Project2.class,"-print");
        assertThat(result.getTextWrittenToStandardError(),containsString("Name of the Owner of the appointment is missing"));
        assertThat(result.getTextWrittenToStandardError(),containsString(Project2.USAGE_MESSAGE));
    }

    @Test
    public void invokingMainWithREADME(){
        InvokeMainTestCase.MainMethodResult result = invokeMain(Project2.class,"-README");
        assertThat(result.getTextWrittenToStandardOut(),containsString("README Name: Pooja Nalan Project : apptbook This project adds an appointment to an appointment book belonging to a particular owner taking " +
                "the necessary details about the appointment which includes owner name, purpose of the appointment, start date and time and end date and time"));
        assertThat(result.getExitCode(),equalTo(1));
    }

    @Test
    public void invokingMainWithREADMEAnywhereAtPos1Or2ShouldPrintREADMEAndExit(){
        InvokeMainTestCase.MainMethodResult result = invokeMain(Project2.class,"-print","-README","Judy Whiskers","Dentist appointment","02/03/2021","02:30","02/03/2021","5:30");
        assertThat(result.getTextWrittenToStandardOut(),containsString("README Name: Pooja Nalan Project : apptbook This project adds an appointment to an appointment book belonging to a particular owner taking " +
                "the necessary details about the appointment which includes owner name, purpose of the appointment, start date and time and end date and time"));
        assertThat(result.getExitCode(),equalTo(1));
    }
    @Test
    public void invokingMainWithPrintAndREADMEButWithNoArguments(){
        InvokeMainTestCase.MainMethodResult result = invokeMain(Project2.class,"-README", "-print");
        assertThat(result.getTextWrittenToStandardOut(),containsString("README Name: Pooja Nalan Project : apptbook This project adds an appointment to an appointment book belonging to a particular owner taking " +
                "the necessary details about the appointment which includes owner name, purpose of the appointment, start date and time and end date and time"));
        assertThat(result.getExitCode(),equalTo(1));
    }

    @Test
    public void missingOwnername(){
        InvokeMainTestCase.MainMethodResult result = invokeMain(Project2.class,"-print");
        assertThat(result.getTextWrittenToStandardError(), containsString("Name of the Owner of the appointment is missing"));
        assertThat(result.getTextWrittenToStandardError(),containsString(Project2.USAGE_MESSAGE));
        assertThat(result.getExitCode(), equalTo(1));
    }

    @Test
    public void missingownernameWithtextFileOption(){
        InvokeMainTestCase.MainMethodResult result = invokeMain(Project2.class,"-print","-textFile","samplefile");
        assertThat(result.getTextWrittenToStandardError(), containsString("Name of the Owner of the appointment is missing"));
        assertThat(result.getTextWrittenToStandardError(),containsString(Project2.USAGE_MESSAGE));
        assertThat(result.getExitCode(), equalTo(1));
    }
    @Test
    public void missingDescription(){
        InvokeMainTestCase.MainMethodResult result = invokeMain(Project2.class,"-print","Pooja");
        assertThat(result.getTextWrittenToStandardError(), containsString("Missing description"));
        assertThat(result.getTextWrittenToStandardError(),containsString(Project2.USAGE_MESSAGE));
        assertThat(result.getExitCode(), equalTo(1));
    }
    @Test
    public void missingDescriptionWithtextFileOption(){
        InvokeMainTestCase.MainMethodResult result = invokeMain(Project2.class,"-print","-textFile","samplefile","Pooja");
        assertThat(result.getTextWrittenToStandardError(), containsString("Missing description"));
        assertThat(result.getTextWrittenToStandardError(),containsString(Project2.USAGE_MESSAGE));
        assertThat(result.getExitCode(), equalTo(1));
    }


    @Test
    public void missingBeginDate(){
        InvokeMainTestCase.MainMethodResult result = invokeMain(Project2.class,"-print","Pooja","zoom meeting");
        assertThat(result.getTextWrittenToStandardError(), containsString("Missing begin date"));
        assertThat(result.getTextWrittenToStandardError(),containsString(Project2.USAGE_MESSAGE));
        assertThat(result.getExitCode(), equalTo(1));
    }

    @Test
    public void missingBeginDateWithtextFileOption(){
        InvokeMainTestCase.MainMethodResult result = invokeMain(Project2.class,"-print","-textFile","samplefile","Pooja","zoom meeting");
        assertThat(result.getTextWrittenToStandardError(), containsString("Missing begin date"));
        assertThat(result.getTextWrittenToStandardError(),containsString(Project2.USAGE_MESSAGE));
        assertThat(result.getExitCode(), equalTo(1));
    }

    @Test
    public void missingBeginTime(){
        InvokeMainTestCase.MainMethodResult result = invokeMain(Project2.class,"-print","Pooja","zoom meeting","02/03/2021");
        assertThat(result.getTextWrittenToStandardError(), containsString("Missing begin time"));
        assertThat(result.getTextWrittenToStandardError(),containsString(Project2.USAGE_MESSAGE));
        assertThat(result.getExitCode(), equalTo(1));
    }

    @Test
    public void missingBeginTimeWithtextFileOption(){
        InvokeMainTestCase.MainMethodResult result = invokeMain(Project2.class,"-print","-textFile","samplefile","Pooja","zoom meeting","02/03/2021");
        assertThat(result.getTextWrittenToStandardError(), containsString("Missing begin time"));
        assertThat(result.getTextWrittenToStandardError(),containsString(Project2.USAGE_MESSAGE));
        assertThat(result.getExitCode(), equalTo(1));
    }
    @Test
    public void missingEndDate(){
        InvokeMainTestCase.MainMethodResult result = invokeMain(Project2.class,"-print","Pooja","zoom meeting","02/03/2021","02:20");
        assertThat(result.getTextWrittenToStandardError(), containsString("Missing end date"));
        assertThat(result.getTextWrittenToStandardError(),containsString(Project2.USAGE_MESSAGE));
        assertThat(result.getExitCode(), equalTo(1));
    }
    @Test
    public void missingEndDateWithtextFileOption(){
        InvokeMainTestCase.MainMethodResult result = invokeMain(Project2.class,"-print","-textFile","samplefile","Pooja","zoom meeting","02/03/2021","02:20");
        assertThat(result.getTextWrittenToStandardError(), containsString("Missing end date"));
        assertThat(result.getTextWrittenToStandardError(),containsString(Project2.USAGE_MESSAGE));
        assertThat(result.getExitCode(), equalTo(1));
    }
    @Test
    public void missingEndTime(){
        InvokeMainTestCase.MainMethodResult result = invokeMain(Project2.class,"-print","Pooja","zoom meeting","02/03/2021","02:20","02/03/2021");
        assertThat(result.getTextWrittenToStandardError(), containsString("Missing command line arguments"));
        assertThat(result.getTextWrittenToStandardError(),containsString(Project2.USAGE_MESSAGE));
        assertThat(result.getExitCode(), equalTo(1));
    }
    @Test
    public void missingEndTimeWithtextFileOption(){
        InvokeMainTestCase.MainMethodResult result = invokeMain(Project2.class,"-print","-textFile","samplefile","Pooja","zoom meeting","02/03/2021","02:20","02/03/2021");
        assertThat(result.getTextWrittenToStandardError(), containsString("Missing end time"));
        assertThat(result.getTextWrittenToStandardError(),containsString(Project2.USAGE_MESSAGE));
        assertThat(result.getExitCode(), equalTo(1));
    }
    @Test
    public void tooManyArgumentsWithPrint(){
        InvokeMainTestCase.MainMethodResult result = invokeMain(Project2.class,"-print","Judy Whiskers","Dentist appointment","02/03/2021","02:30","02/03/2021","5:30","Teeth whitening Time");
        assertThat(result.getTextWrittenToStandardError(), containsString("The required number of arguments has exceeded."));
        assertThat(result.getTextWrittenToStandardError(),containsString(Project2.USAGE_MESSAGE));
        assertThat(result.getExitCode(), equalTo(1));
    }

    @Test
    public void tooManyArgumentsWithPrintAndtextFileOption(){
        InvokeMainTestCase.MainMethodResult result = invokeMain(Project2.class,"-print","-textFile","samplefile","Judy Whiskers","Dentist appointment","02/03/2021","02:30","02/03/2021","5:30","Teeth whitening Time");
        assertThat(result.getTextWrittenToStandardError(), containsString("The required number of arguments has exceeded."));
        assertThat(result.getTextWrittenToStandardError(),containsString(Project2.USAGE_MESSAGE));
        assertThat(result.getExitCode(), equalTo(1));
    }

    @Test
    public void rightArgumentsGivenForJustPrint(){
        InvokeMainTestCase.MainMethodResult result = invokeMain(Project2.class,"-print","May Thatcher","Football match","11/27/2021","13:15","11/27/2021","17:30");
        assertThat(result.getTextWrittenToStandardOut(), containsString("Football match from 13:15 until 17:30"));
    }


    @Test
    public void rightArgumentsGivenReturnsExitCode0(){
        InvokeMainTestCase.MainMethodResult result = invokeMain(Project2.class,"-print","-textFile","file","May Thatcher","Football match","09/08/2021","15:15","09/08/2021","17:30");
        assertThat(result.getExitCode(), equalTo(1));
    }
  /*  @Test
    public void textFileOptionWithoutFileName(){
        InvokeMainTestCase.MainMethodResult result = invokeMain(Project2.class,"-print","-textFile","May Thatcher","Football match","09/08/2021","15:15","09/08/2021","17:30");
        assertThat(result.getTextWrittenToStandardError(), containsString(Project2.MISSING_COMMAND_LINE_ARGUMENTS));
        assertThat(result.getTextWrittenToStandardError(),containsString(Project2.USAGE_MESSAGE));
    }*/

    @Test
    public void onlytextFileOptionGiven(){
        InvokeMainTestCase.MainMethodResult result = invokeMain(Project2.class,"-textFile","samplefile");
        assertThat(result.getTextWrittenToStandardError(),containsString(Project2.MISSING_COMMAND_LINE_ARGUMENTS));
        assertThat(result.getTextWrittenToStandardError(),containsString(Project2.USAGE_MESSAGE));
    }

    @Test
    public void unrecognizedDateFormat(){
        InvokeMainTestCase.MainMethodResult result = invokeMain(Project2.class,"-print","Pooja","zoom meeting","02-03-2021","02:30","02-03-2021","03:30");
        assertThat(result.getTextWrittenToStandardError(), containsString("Date not in requested format mm/dd/yyyy \" unrecognized date"));
        assertThat(result.getTextWrittenToStandardError(),containsString(Project2.USAGE_MESSAGE));
        assertThat(result.getExitCode(), equalTo(1));
    }

    @Test
    public void unrecognizedDateFormatInBeginDate(){
        InvokeMainTestCase.MainMethodResult result = invokeMain(Project2.class,"-print","Pooja","zoom meeting","07-25-2021","02:30","07/25/2021","03:30");
        assertThat(result.getTextWrittenToStandardError(), containsString("Date not in requested format mm/dd/yyyy \" unrecognized date"));
        assertThat(result.getTextWrittenToStandardError(),containsString(Project2.USAGE_MESSAGE));
        assertThat(result.getExitCode(), equalTo(1));
    }

    @Test
    public void unrecognizedDateFormatInEndDate(){
        InvokeMainTestCase.MainMethodResult result = invokeMain(Project2.class,"-print","Pooja","zoom meeting","08/15/2021","02:30","08-15-2021","03:30");
        assertThat(result.getTextWrittenToStandardError(), containsString("Date not in requested format mm/dd/yyyy \" unrecognized date"));
        assertThat(result.getTextWrittenToStandardError(),containsString(Project2.USAGE_MESSAGE));
        assertThat(result.getExitCode(), equalTo(1));
    }
    @Test
    public void malformedDateFormatInBeginDate(){
        InvokeMainTestCase.MainMethodResult result = invokeMain(Project2.class,"-print","Pooja","zoom meeting","08/15/2021/3333//","02:30","08-15-2021-1","03:30");
        assertThat(result.getTextWrittenToStandardError(), containsString("Date not in requested format mm/dd/yyyy \" unrecognized date"));
        assertThat(result.getTextWrittenToStandardError(),containsString(Project2.USAGE_MESSAGE));
        assertThat(result.getExitCode(), equalTo(1));
    }
    @Test
    public void malformedDateFormatInEndDate(){
        InvokeMainTestCase.MainMethodResult result = invokeMain(Project2.class,"-print","Pooja","zoom meeting","08/15/2021","02:30","08-15-2021-1","03:30");
        assertThat(result.getTextWrittenToStandardError(), containsString("Date not in requested format mm/dd/yyyy \" unrecognized date"));
        assertThat(result.getTextWrittenToStandardError(),containsString(Project2.USAGE_MESSAGE));
        assertThat(result.getExitCode(), equalTo(1));
    }
    @Test
    public void unrecognizedTimeFormatInBeginDate(){
        InvokeMainTestCase.MainMethodResult result = invokeMain(Project2.class,"-print","Pooja","zoom meeting","02/03/2021","230","02/03/2021","3:30");
        assertThat(result.getTextWrittenToStandardError(), containsString("Time not in requested format (hh:mm) \" unrecognized time"));
        assertThat(result.getTextWrittenToStandardError(),containsString(Project2.USAGE_MESSAGE));
        assertThat(result.getExitCode(), equalTo(1));
    }

    @Test
    public void unrecognizedTimeFormatInEndDate(){
        InvokeMainTestCase.MainMethodResult result = invokeMain(Project2.class,"-print","Pooja","zoom meeting","02/03/2021","2:30","02/03/2021","44");
        assertThat(result.getTextWrittenToStandardError(), containsString("Time not in requested format (hh:mm) \" unrecognized time"));
        assertThat(result.getTextWrittenToStandardError(),containsString(Project2.USAGE_MESSAGE));
        assertThat(result.getExitCode(), equalTo(1));
    }

    @Test
    void checkIfBeginDateIsGreaterThanEndDate(){
        InvokeMainTestCase.MainMethodResult result = invokeMain(Project2.class,"-print","Pooja","virtual party","10/10/2021","13:00","10/08/2021","16:00");
        assertThat(result.getTextWrittenToStandardError(),containsString("Begin date/time cannot be greater than or equal to the end date/time for an appointment!"));
        assertThat(result.getExitCode(), equalTo(1));
    }

    @Test
    void OptionsHaveTobespcifiedAttheBegining(){
        InvokeMainTestCase.MainMethodResult result = invokeMain(Project2.class,"-print","samplefile","Pooja","just a meeting","09/09/2021","-textFile","09:14","09/09/2021","16:30");
        assertThat(result.getTextWrittenToStandardError(),containsString("Options must be entered first. Wrong ordering of arguments"));
        assertThat(result.getTextWrittenToStandardError(),containsString(Project2.USAGE_MESSAGE));
    }

    @Test
    void validateDateinanExistingTextFileBeforeReadingThemAndthrowAnError() throws IOException, ParserException {
        String filename = "bad-year.txt";
        InvokeMainTestCase.MainMethodResult result = invokeMain(Project2.class,"-textFile",filename,"Pooja","just a meeting","09/09/2021","09:14","09/09/2021","16:30");
        assertThat(result.getTextWrittenToStandardError(),containsString("UNRECOGNIZED DATE FORMAT"));

    }

    @Test
    void validateTimeinanExistingTextFileBeforeReadingThemAndthrowAnError() throws IOException, ParserException {
        String filename = "bad-time.txt";
        InvokeMainTestCase.MainMethodResult result = invokeMain(Project2.class,"-textFile",filename,"Pooja","just a meeting","09/09/2021","09:14","09/09/2021","16:30");
        assertThat(result.getTextWrittenToStandardError(),containsString("UNRECOGNIZED TIME FORMAT"));

    }

}
