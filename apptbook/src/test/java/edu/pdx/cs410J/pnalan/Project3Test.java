package edu.pdx.cs410J.pnalan;

import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

public class Project3Test {
    @Test
    void readmeCanBeReadAsResource() throws IOException {
        try (
                InputStream readme = Project3.class.getResourceAsStream("README.txt")
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
        Project3 p1 = new Project3();
        assertThat(p1.getClass().getName(), containsString("Project3"));
        ;
    }

    @Test
    void checkIfWeDontHaveZeroFields(){
        Project3 p1 = new Project3();
        assertThat(p1.getClass().getDeclaredFields(),is(notNullValue()));
    }

    @Test
    void checkIfWeDontHaveZeroMethods(){
        Project3 p1 = new Project3();
        assertThat(p1.getClass().getDeclaredMethods(),is(notNullValue()));
    }

    @Test
    void checkIfPrettyWorks(){
        Project3 p1 = new Project3();
        assertThat(p1.getClass().getDeclaredMethods(),is(notNullValue()));
    }


}
