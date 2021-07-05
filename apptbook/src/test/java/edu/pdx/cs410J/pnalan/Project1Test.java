package edu.pdx.cs410J.pnalan;

import edu.pdx.cs410J.InvokeMainTestCase;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * A unit test for code in the <code>Project1</code> class.  This is different
 * from <code>Project1IT</code> which is an integration test (and can handle the calls
 * to {@link System#exit(int)} and the like.
 */
class Project1Test extends InvokeMainTestCase{

  @Test
  void readmeCanBeReadAsResource() throws IOException {
    try (
            InputStream readme = Project1.class.getResourceAsStream("README.txt")
    ) {
      assertThat(readme, not(nullValue()));
      BufferedReader reader = new BufferedReader(new InputStreamReader(readme));
      String line = reader.readLine();
      assertThat(line, containsString("This is a README file!"));
    }
  }
  @Test
    public void invokingMainWithoutArgumentsHasExitCodeOf1(){
    InvokeMainTestCase.MainMethodResult result = invokeMain(project1Copy.class);
    assertThat(result.getExitCode(),equalTo(1));
  }
    @Test
      public void invokingMainWithNoArgumentsPrintsMissingArgumentsToStandardError(){
      InvokeMainTestCase.MainMethodResult result = invokeMain(project1Copy.class,"-print");
      assertThat(result.getTextWrittenToStandardError(),containsString("Missing command line arguments"));
      assertThat(result.getTextWrittenToStandardError(),containsString(project1Copy.USAGE_MESSAGE));
    }
    @Test
      public void missingDescription(){
    MainMethodResult result = invokeMain(project1Copy.class,"-print","Pooja");
    assertThat(result.getTextWrittenToStandardError(), containsString("Missing description"));
    assertThat(result.getTextWrittenToStandardError(),containsString(project1Copy.USAGE_MESSAGE));
    assertThat(result.getExitCode(), equalTo(1));
    }
  @Test
  public void missingBeginDate(){
    MainMethodResult result = invokeMain(project1Copy.class,"-print","Pooja","zoom meeting");
    assertThat(result.getTextWrittenToStandardError(), containsString("Missing begin date"));
    assertThat(result.getTextWrittenToStandardError(),containsString(project1Copy.USAGE_MESSAGE));
    assertThat(result.getExitCode(), equalTo(1));
  }
  @Test
  public void missingBeginTime(){
    MainMethodResult result = invokeMain(project1Copy.class,"-print","Pooja","zoom meeting","02/03/2021");
    assertThat(result.getTextWrittenToStandardError(), containsString("Missing begin time"));
    assertThat(result.getTextWrittenToStandardError(),containsString(project1Copy.USAGE_MESSAGE));
    assertThat(result.getExitCode(), equalTo(1));
  }
  @Test
  public void missingEndDate(){
    MainMethodResult result = invokeMain(project1Copy.class,"-print","Pooja","zoom meeting","02/03/2021","02:20");
    assertThat(result.getTextWrittenToStandardError(), containsString("Missing end date"));
    assertThat(result.getTextWrittenToStandardError(),containsString(project1Copy.USAGE_MESSAGE));
    assertThat(result.getExitCode(), equalTo(1));
  }
  @Test
  public void missingEndTime(){
    MainMethodResult result = invokeMain(project1Copy.class,"-print","Pooja","zoom meeting","02/03/2021","02:20","02/03/2021");
    assertThat(result.getTextWrittenToStandardError(), containsString("Missing end time"));
    assertThat(result.getTextWrittenToStandardError(),containsString(project1Copy.USAGE_MESSAGE));
    assertThat(result.getExitCode(), equalTo(1));
  }
  @Test
    public void unrecognizedDateFormat(){
    MainMethodResult result = invokeMain(project1Copy.class,"-print","Pooja","zoom meeting","02-03-2021","02:30","02-03-2021","03:30");
    assertThat(result.getTextWrittenToStandardError(), containsString("Date not in requested format (hh:mm) \" unrecognized date"));
    assertThat(result.getTextWrittenToStandardError(),containsString(project1Copy.USAGE_MESSAGE));
    assertThat(result.getExitCode(), equalTo(1));
}
  @Test
  public void unrecognizedTimeFormat(){
    MainMethodResult result = invokeMain(project1Copy.class,"-print","Pooja","zoom meeting","02/03/2021","230","02/03/2021","3:30");
    assertThat(result.getTextWrittenToStandardError(), containsString("Time not in requested format (hh:mm) \" unrecognized time"));
    assertThat(result.getTextWrittenToStandardError(),containsString(project1Copy.USAGE_MESSAGE));
    assertThat(result.getExitCode(), equalTo(1));
  }
}
