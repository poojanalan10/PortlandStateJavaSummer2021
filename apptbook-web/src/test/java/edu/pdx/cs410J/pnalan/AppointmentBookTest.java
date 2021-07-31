package edu.pdx.cs410J.pnalan;

import org.checkerframework.checker.units.qual.A;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.text.ParseException;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

public class AppointmentBookTest {
    @Test
    public void checkIfGetOwnerNameReturnsOwnerName(){
        AppointmentBook appBook = new AppointmentBook("Pooja", new Appointment());
        assertThat(appBook.getOwnerName(),equalTo("Pooja"));
    }
    @Test
    public void checkIfAppointmentBookAddsAppointments(){
        AppointmentBook appBook = new AppointmentBook("Pooja", new Appointment("zoom meeting","02/02/2021","09:15 AM","02/02/2021","11:00 AM"));
        assertThat(appBook.getAppointments().iterator().next().toString(),containsString("zoom meeting from 09:15 AM until 11:00 AM"));
    }

    @Test
    void toStringContainsAppointmentInTheAppointmentBook(){
        AppointmentBook appBook = new AppointmentBook("Pooja", new Appointment("zoom meeting","02/02/2021","09:15 AM","02/02/2021","11:00 AM"));
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
    @Test
    void addingAnAppointmentAddsAppointment(){
        Appointment appointment = new Appointment();
        AppointmentBook appointmentBook = new AppointmentBook("Pooja");
        appointmentBook.addAppointment(appointment);
        assertThat(appointmentBook.getAppointments(),hasItem(appointment));
    }

    @Test
    void checkForppointmentsWithinDateRange() throws ParseException {
        AppointmentBook appointmentBook = new AppointmentBook("Pooja");
        appointmentBook.addAppointment(new Appointment("zoom meeting","07/02/2021","09:15 am","07/02/2021","11:00 am"));
        appointmentBook.addAppointment(new Appointment("gym time","07/10/2021","07:15 pm","07/10/2021","9:00 pm"));
        appointmentBook.addAppointment(new Appointment("gym time","07/12/2021","07:15 pm","07/12/2021","9:00 pm"));
        appointmentBook.addAppointment(new Appointment("Pool time","06/08/2021","04:15 pm","06/08/2021","6:30 pm"));
        appointmentBook.addAppointment(new Appointment("painting","08/08/2021","09:00 am","08/08/2021","12:30 pm"));
        appointmentBook.addAppointment(new Appointment("party time!","09/10/2021","09:00 am","09/10/2021","9:30 pm"));
       String startTimeDate = "06/07/2021 10:00 am";
       String endDateTime = "08/10/2021 10:00 pm";
       AppointmentBook search_results = appointmentBook.findAppointmentsWithDateRange(startTimeDate,endDateTime);
       assertThat(search_results.getOwnerName(), equalTo("Pooja"));
       assertThat(search_results.getAppointments().size(),equalTo(5));
    }


}
