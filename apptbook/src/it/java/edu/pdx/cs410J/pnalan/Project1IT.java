/*package edu.pdx.cs410J.pnalan;

import edu.pdx.cs410J.InvokeMainTestCase;
import org.junit.jupiter.api.Test;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Integration tests for the {@link Project1} main class.
 */
/*class Project1IT extends InvokeMainTestCase {


  */
/**
   * Invokes the main method of {@link Project1} with the given arguments.
   *//*

  private MainMethodResult invokeMain(String... args) {
    return invokeMain( Project1.class, args );
  }

  */
/**
   * Tests that invoking the main method with no arguments issues an error
   *//*

  @Test
  void testNoCommandLineArguments() {
    MainMethodResult result = invokeMain(Project1.class,"");

    assertThat(result.getTextWrittenToStandardError(), containsString("Missing command line arguments"));
    assertThat(result.getTextWrittenToStandardError(),containsString(Project1.USAGE_MESSAGE));
    assertThat(result.getExitCode(), equalTo(1));
  }
  @Test
  public void invokingMainWithoutArgumentsHasExitCodeOf1(){
    InvokeMainTestCase.MainMethodResult result = invokeMain(Project1.class);
    assertThat(result.getExitCode(),equalTo(1));
  }
  @Test
  public void invokingMainWithNoArgumentsPrintsMissingArgumentsToStandardError(){
    InvokeMainTestCase.MainMethodResult result = invokeMain(Project1.class,"-print");
    assertThat(result.getTextWrittenToStandardError(),containsString("Missing command line arguments"));
    assertThat(result.getTextWrittenToStandardError(),containsString(Project1.USAGE_MESSAGE));
  }

  @Test
  public void invokingMainWithREADME(){
    InvokeMainTestCase.MainMethodResult result = invokeMain(Project1.class,"-README");
    assertThat(result.getTextWrittenToStandardOut(),containsString("README Name: Pooja Nalan Project : apptbook This project adds an appointment to an appointment book belonging to a particular owner taking " +
            "the necessary details about the appointment which includes owner name, purpose of the appointment, start date and time and end date and time"));
    assertThat(result.getExitCode(),equalTo(1));
  }

  @Test
  public void invokingMainWithREADMEAnywhereAtPos1Or2ShouldPrintREADMEAndExit(){
    InvokeMainTestCase.MainMethodResult result = invokeMain(Project1.class,"-print","-README","Judy Whiskers","Dentist appointment","02/03/2021","02:30","02/03/2021","5:30");
    assertThat(result.getTextWrittenToStandardOut(),containsString("README Name: Pooja Nalan Project : apptbook This project adds an appointment to an appointment book belonging to a particular owner taking " +
            "the necessary details about the appointment which includes owner name, purpose of the appointment, start date and time and end date and time"));
    assertThat(result.getExitCode(),equalTo(1));
  }
  @Test
  public void invokingMainWithPrintAndREADMEButWithNoArguments(){
    InvokeMainTestCase.MainMethodResult result = invokeMain(Project1.class,"-README", "-print");
    assertThat(result.getTextWrittenToStandardOut(),containsString("README Name: Pooja Nalan Project : apptbook This project adds an appointment to an appointment book belonging to a particular owner taking " +
            "the necessary details about the appointment which includes owner name, purpose of the appointment, start date and time and end date and time"));
    assertThat(result.getExitCode(),equalTo(1));
  }

  @Test
  public void missingDescription(){
    MainMethodResult result = invokeMain(Project1.class,"-print","Pooja");
    assertThat(result.getTextWrittenToStandardError(), containsString("Missing description"));
    assertThat(result.getTextWrittenToStandardError(),containsString(Project1.USAGE_MESSAGE));
    assertThat(result.getExitCode(), equalTo(1));
  }
  @Test
  public void missingBeginDate(){
    MainMethodResult result = invokeMain(Project1.class,"-print","Pooja","zoom meeting");
    assertThat(result.getTextWrittenToStandardError(), containsString("Missing begin date"));
    assertThat(result.getTextWrittenToStandardError(),containsString(Project1.USAGE_MESSAGE));
    assertThat(result.getExitCode(), equalTo(1));
  }
  @Test
  public void missingBeginTime(){
    MainMethodResult result = invokeMain(Project1.class,"-print","Pooja","zoom meeting","02/03/2021");
    assertThat(result.getTextWrittenToStandardError(), containsString("Missing begin time"));
    assertThat(result.getTextWrittenToStandardError(),containsString(Project1.USAGE_MESSAGE));
    assertThat(result.getExitCode(), equalTo(1));
  }
  @Test
  public void missingEndDate(){
    MainMethodResult result = invokeMain(Project1.class,"-print","Pooja","zoom meeting","02/03/2021","02:20");
    assertThat(result.getTextWrittenToStandardError(), containsString("Missing end date"));
    assertThat(result.getTextWrittenToStandardError(),containsString(Project1.USAGE_MESSAGE));
    assertThat(result.getExitCode(), equalTo(1));
  }
  @Test
  public void missingEndTime(){
    MainMethodResult result = invokeMain(Project1.class,"-print","Pooja","zoom meeting","02/03/2021","02:20","02/03/2021");
    assertThat(result.getTextWrittenToStandardError(), containsString("Missing end time"));
    assertThat(result.getTextWrittenToStandardError(),containsString(Project1.USAGE_MESSAGE));
    assertThat(result.getExitCode(), equalTo(1));
  }

  @Test
  public void tooManyArguments(){
    MainMethodResult result = invokeMain(Project1.class,"-print","Judy Whiskers","Dentist appointment","02/03/2021","02:30","02/03/2021","5:30","Teeth whitening Time");
    assertThat(result.getTextWrittenToStandardError(), containsString("The required number of arguments is 6 and you've exceeded that."));
    assertThat(result.getTextWrittenToStandardError(),containsString(Project1.USAGE_MESSAGE));
    assertThat(result.getExitCode(), equalTo(1));
  }

  @Test
  public void rightArgumentsGiven(){
    MainMethodResult result = invokeMain(Project1.class,"-print","May Thatcher","Football match","11/27/2021","13:15","11/27/2021","17:30");
    assertThat(result.getTextWrittenToStandardOut(), containsString("Football match from 13:15 until 17:30"));
  }


  @Test
  public void rightArgumentsGivenReturnsExitCode0(){
    MainMethodResult result = invokeMain(Project1.class,"-print","May Thatcher","Football match","09/08/2021","15:15","09/08/2021","17:30");
    assertThat(result.getExitCode(), equalTo(0));
  }




  @Test
  public void unrecognizedDateFormat(){
    MainMethodResult result = invokeMain(Project1.class,"-print","Pooja","zoom meeting","02-03-2021","02:30","02-03-2021","03:30");
    assertThat(result.getTextWrittenToStandardError(), containsString("Date not in requested format (hh:mm) \" unrecognized date"));
    assertThat(result.getTextWrittenToStandardError(),containsString(Project1.USAGE_MESSAGE));
    assertThat(result.getExitCode(), equalTo(1));
  }

  @Test
  public void unrecognizedDateFormatInBeginDate(){
    MainMethodResult result = invokeMain(Project1.class,"-print","Pooja","zoom meeting","07-25-2021","02:30","07/25/2021","03:30");
    assertThat(result.getTextWrittenToStandardError(), containsString("Date not in requested format (hh:mm) \" unrecognized date"));
    assertThat(result.getTextWrittenToStandardError(),containsString(Project1.USAGE_MESSAGE));
    assertThat(result.getExitCode(), equalTo(1));
  }

  @Test
  public void unrecognizedDateFormatInEndDate(){
    MainMethodResult result = invokeMain(Project1.class,"-print","Pooja","zoom meeting","08/15/2021","02:30","08-15-2021","03:30");
    assertThat(result.getTextWrittenToStandardError(), containsString("Date not in requested format (hh:mm) \" unrecognized date"));
    assertThat(result.getTextWrittenToStandardError(),containsString(Project1.USAGE_MESSAGE));
    assertThat(result.getExitCode(), equalTo(1));
  }

  @Test
  public void unrecognizedDateInBeginDate(){
    MainMethodResult result = invokeMain(Project1.class,"-print","Pooja","zoom meeting","07/35/2021","02:30","07/25/2021","03:30");
    assertThat(result.getTextWrittenToStandardError(), containsString("Date not in requested format (hh:mm) \" unrecognized date"));
    assertThat(result.getTextWrittenToStandardError(),containsString(Project1.USAGE_MESSAGE));
    assertThat(result.getExitCode(), equalTo(1));
  }

  @Test
  public void unrecognizedDateInEndDate(){
    MainMethodResult result = invokeMain(Project1.class,"-print","Pooja","zoom meeting","07/15/2021","02:30","25/25/40215","03:30");
    assertThat(result.getTextWrittenToStandardError(), containsString("Date not in requested format (hh:mm) \" unrecognized date"));
    assertThat(result.getTextWrittenToStandardError(),containsString(Project1.USAGE_MESSAGE));
    assertThat(result.getExitCode(), equalTo(1));
  }

  @Test
  public void unrecognizedTimeFormatInBeginDate(){
    MainMethodResult result = invokeMain(Project1.class,"-print","Pooja","zoom meeting","02/03/2021","230","02/03/2021","3:30");
    assertThat(result.getTextWrittenToStandardError(), containsString("Time not in requested format (hh:mm) \" unrecognized time"));
    assertThat(result.getTextWrittenToStandardError(),containsString(Project1.USAGE_MESSAGE));
    assertThat(result.getExitCode(), equalTo(1));
  }

  @Test
  public void unrecognizedTimeFormatInEndDate(){
    MainMethodResult result = invokeMain(Project1.class,"-print","Pooja","zoom meeting","02/03/2021","2:30","02/03/2021","44");
    assertThat(result.getTextWrittenToStandardError(), containsString("Time not in requested format (hh:mm) \" unrecognized time"));
    assertThat(result.getTextWrittenToStandardError(),containsString(Project1.USAGE_MESSAGE));
    assertThat(result.getExitCode(), equalTo(1));
  }

  @Test
  void checkIfBeginDateIsGreaterThanEndDate(){
    MainMethodResult result = invokeMain(Project1.class,"-print","Pooja","virtual party","10/10/2021","13:00","10/08/2021","16:00");
    assertThat(result.getTextWrittenToStandardError(),containsString("Begin date/time cannot be greater than or equal to the end date/time for an appointment!"));
    assertThat(result.getExitCode(), equalTo(1));
  }
*/


  /*@Test
  void checkIfBeginTimeIsGreaterThanEndDate(){
    MainMethodResult result = invokeMain(Project1.class,"-print","Pooja","virtual party","11/30/2021","20:00","11/30/2021","16:00");
    assertThat(result.getTextWrittenToStandardError(),containsString("Begin date/time cannot be greater than or equal to the end date/time for an appointment!"));
    assertThat(result.getExitCode(), equalTo(1));
  }*/

//}