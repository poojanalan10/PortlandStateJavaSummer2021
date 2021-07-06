package edu.pdx.cs410J.pnalan;

import org.junit.jupiter.api.Test;

import java.text.ParseException;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Unit tests for the {@link Appointment} class.
 *
 * You'll need to update these unit tests as you build out your program.
 */
public class AppointmentTest {

  @Test
  void initiallyAllAppointmentsHaveTheSameDescription() {
    Appointment appointment = new Appointment();
    assertThat(appointment.getDescription(), equalTo(null));
  }

  @Test
  void forProject1ItIsOkayIfGetBeginTimeReturnsNull() {
    Appointment appointment = new Appointment();
    assertThat(appointment.getBeginTime(), is(nullValue()));
  }

  @Test
  void getBeginTimeStringreturnsTheAppointmentBeginTime() throws ParseException {
    String beginTime = "09:15";
    var obj = new Appointment("zoom meeting","02/02/2021","09:15","02/02/2021","11:00");
    assertThat(obj.getBeginTimeString(),equalTo(beginTime));
  }

  @Test
  void getEndTimeStringreturnsTheAppointmentEndTime() throws ParseException {
    String beginTime = "11:00";
    var obj = new Appointment("zoom meeting","02/02/2021","09:15","02/02/2021","11:00");
    assertThat(obj.getEndTimeString(),equalTo(beginTime));
  }

  @Test
  void PoojaAppointmentRecordedAs(){
    Appointment Pooja = getAppointmentOfPooja();
    assertThat(Pooja.toString(),equalTo("zoom meeting from 09:15 until 11:00"));
  }

  public Appointment getAppointmentOfPooja() {
    return new Appointment("zoom meeting","02/02/2021","09:15","02/02/2021","11:00");
  }
  @Test
  void toStringContainsAppointmentDescription(){
    Appointment Pooja = getAppointmentOfPooja();
    assertThat(Pooja.toString(),containsString("zoom meeting"));
  }

  @Test
  void toStringContainsAppointmentBeginTime(){
    Appointment Pooja = getAppointmentOfPooja();
    assertThat(Pooja.toString(),containsString("09:15"));
  }

  @Test
  void toStringContainsAppointmentEndTime(){
    Appointment Pooja = getAppointmentOfPooja();
    assertThat(Pooja.toString(),containsString("11:00"));
  }
  @Test
  void checkAppointmentDuration(){
    Appointment Pooja = getAppointmentOfPooja();
    assertThat(String.valueOf(Pooja.appointmentDuration()), equalTo("45.0"));
  }


}
