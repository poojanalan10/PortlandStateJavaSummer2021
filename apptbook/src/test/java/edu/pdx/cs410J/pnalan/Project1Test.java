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
      InvokeMainTestCase.MainMethodResult result = invokeMain(project1Copy.class);
      assertThat(result.getTextWrittenToStandardError(),containsString("Missing command line arguments"));
      assertThat(result.getTextWrittenToStandardError(),containsString(project1Copy.USAGE_MESSAGE));
    }

}
