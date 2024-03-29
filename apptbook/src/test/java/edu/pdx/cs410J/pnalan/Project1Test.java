/*package edu.pdx.cs410J.pnalan;

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
/*class Project1Test extends InvokeMainTestCase {


  @Test
  void readmeCanBeReadAsResource() throws IOException {
    try (
            InputStream readme = Project1.class.getResourceAsStream("README.txt")
    ) {
      assertThat(readme, not(nullValue()));
      BufferedReader reader = new BufferedReader(new InputStreamReader(readme));
      String line = reader.readLine();
      assertThat(line, containsString("README Name: Pooja Nalan Project : apptbook This project adds an appointment to an appointment book belonging to a particular owner " +
              "taking the necessary details about the appointment which includes owner name, purpose of the appointment, start date and time and end date and time."));
    }
  }

  @Test
  void getClassName() throws IOException {
    Project1 p1 = new Project1();
    assertThat(p1.getClass().getName(), containsString("Project1"));
    ;
  }

  @Test
  void checkIfWeDontHaveZeroFields(){
    Project1 p1 = new Project1();
    assertThat(p1.getClass().getDeclaredFields(),is(notNullValue()));
  }

  @Test
  void checkIfWeDontHaveZeroMethods(){
    Project1 p1 = new Project1();
    assertThat(p1.getClass().getDeclaredMethods(),is(notNullValue()));
  }



}
*/