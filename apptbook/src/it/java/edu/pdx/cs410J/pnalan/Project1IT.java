package edu.pdx.cs410J.pnalan;

import edu.pdx.cs410J.InvokeMainTestCase;
import org.junit.jupiter.api.Test;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Integration tests for the {@link Project1} main class.
 */
class Project1IT extends InvokeMainTestCase {

  /**
   * Invokes the main method of {@link Project1} with the given arguments.
   */
  private MainMethodResult invokeMain(String... args) {
    return invokeMain( Project1.class, args );
  }

  /**
   * Tests that invoking the main method with no arguments issues an error
   */
  @Test
  void testNoCommandLineArguments() {
    MainMethodResult result = invokeMain();
    assertThat(result.getExitCode(), equalTo(1));
    assertThat(result.getTextWrittenToStandardError(), containsString("Missing command line arguments"));
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
  public void unrecognizedDateFormat(){
    MainMethodResult result = invokeMain(Project1.class,"-print","Pooja","zoom meeting","02-03-2021","02:30","02-03-2021","03:30");
    assertThat(result.getTextWrittenToStandardError(), containsString("Date not in requested format (hh:mm) \" unrecognized date"));
    assertThat(result.getTextWrittenToStandardError(),containsString(Project1.USAGE_MESSAGE));
    assertThat(result.getExitCode(), equalTo(1));
  }
  @Test
  public void unrecognizedTimeFormat(){
    MainMethodResult result = invokeMain(Project1.class,"-print","Pooja","zoom meeting","02/03/2021","230","02/03/2021","3:30");
    assertThat(result.getTextWrittenToStandardError(), containsString("Time not in requested format (hh:mm) \" unrecognized time"));
    assertThat(result.getTextWrittenToStandardError(),containsString(Project1.USAGE_MESSAGE));
    assertThat(result.getExitCode(), equalTo(1));
  }

  @Test
  void checkIfBeginDateIsGreerThanEndDate(){
    MainMethodResult result = invokeMain(Project1.class,"-print","Pooja","virtual party","10/10/2021","13:00","10/08/2021","16:00");
    assertThat(result.getTextWrittenToStandardError(),containsString("Begin date/time cannot be greater than or equal to the end date/time for an appointment!"));
    assertThat(result.getExitCode(), equalTo(1));
  }

}