package edu.pdx.cs410J.pnalan;

import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

public class Project4Test {


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


}
