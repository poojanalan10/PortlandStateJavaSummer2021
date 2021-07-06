package edu.pdx.cs410J.pnalan;

import org.junit.jupiter.api.Test;
import java.text.ParseException;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class AppointmentBookTest {
    @Test
    public void checkIfGetOwnerNameReturnsOwnerName(){
        AppointmentBook appBook = new AppointmentBook("Pooja", new Appointment());
        assertThat(appBook.getOwnerName(),equalTo("Pooja"));
    }
    @Test
    public void checkIfAppointmentBookAddsAppointments(){
        AppointmentBook appBook = new AppointmentBook("Pooja", new Appointment("zoom meeting","02/02/2021","09:15","02/02/2021","11:00"));
        assertThat(appBook.getAppointments().iterator().next().toString(),containsString("zoom meeting from 09:15 until 11:00"));
    }

    @Test
    void toStringContainsAppointmentInTheAppointmentBook(){
        AppointmentBook appBook = new AppointmentBook("Pooja", new Appointment("zoom meeting","02/02/2021","09:15","02/02/2021","11:00"));
        assertThat(appBook.toString(),containsString("Pooja's appointment book with 1 appointment"));
    }

    @Test
    void AddingtoAppointmentBookAcceptsAddingAppointmentsOnly(){
        AppointmentBook appBook = new AppointmentBook();
        appBook.addAppointment(new Appointment());
    }

    @Test
    void checkIfAppointmentBookHasDefaultConstructor(){
        AppointmentBook appBook = new AppointmentBook();
        assertThat(appBook.toString(),containsString("0 appointment"));
    }

    @Test
    void checkIfAppointmentBookOneArgumentConstructorWorks(){
        AppointmentBook appBook = new AppointmentBook("Gwen");
        assertThat(appBook.toString(),equalTo("Gwen's appointment book with 0 appointments"));
    }

}
