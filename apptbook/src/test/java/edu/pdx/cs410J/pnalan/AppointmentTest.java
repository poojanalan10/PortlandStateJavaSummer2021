package edu.pdx.cs410J.pnalan;

import org.junit.jupiter.api.Disabled;
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
  void getBeginTimeStringReturnsTheAppointmentBeginTime() throws ParseException {
    String beginTime = "09:15 AM";
    var obj = new Appointment("zoom meeting","02/02/2021","09:15 AM","02/02/2021","11:00 AM");
    assertThat(obj.getBeginTimeString(),equalTo(beginTime));
  }

  @Test
  void getEndTimeStringReturnsTheAppointmentEndTime() throws ParseException {
    String endTime = "11:00 AM";
    var obj = new Appointment("zoom meeting","02/02/2021","09:15 AM","02/02/2021","11:00 AM");
    assertThat(obj.getEndTimeString(),equalTo(endTime));
  }

  @Test
  void getBeginDateStringReturnsTheAppointmentBeginDate(){
    Appointment app = new Appointment("abc","03/05/2020","3:24 AM","03/05/2020","4:20 AM");
    assertThat(app.getBeginDate(),containsString("03/05/2020"));

  }

  @Test
  void getEndDateStringReturnsTheAppointmentEndDate(){
    Appointment app = new Appointment("abc","03/05/2020","3:24 AM","03/05/2020","4:20 AM");
    assertThat(app.getEndDate(),containsString("03/05/2020"));

  }

  @Test
  void PoojaAppointmentRecordedAs(){
    Appointment Pooja = getAppointmentOfPooja();
    assertThat(Pooja.toString(),equalTo("zoom meeting from 09:15 AM until 11:00 AM"));
  }

  public Appointment getAppointmentOfPooja() {
    return new Appointment("zoom meeting","02/02/2021","09:15 AM","02/02/2021","11:00 AM");
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
  @Disabled
  void checkAppointmentDuration(){
    Appointment Pooja = getAppointmentOfPooja();
    assertThat(String.valueOf(Pooja.appointmentDuration()), equalTo("45.0"));
  }
  @Test
  void checkEmptyAppointment(){
    Appointment app = new Appointment();
    assertThat(app.toString(),containsString(""));
  }


  @Test
  @Disabled
  void convertDateFormatCheckForBeginDateTime(){
    Appointment appointment = new Appointment();
    Appointment app = new Appointment("zoom meeting","02/02/2021","09:15 AM","02/02/2021","11:00 AM");
    assertThat(app.getBeginTime().toString(),containsString("Tue Feb 02 09:15:00"));
  }

  @Test
  @Disabled
  void convertDateFormatCheckForEndDateTime(){
    Appointment appointment = new Appointment();
    Appointment app = new Appointment("zoom meeting","02/02/2021","09:15 AM","02/03/2021","11:00 AM");
    assertThat(app.getEndTime().toString(),containsString("Wed Feb 03 11:00:00"));
  }
  @Test
  @Disabled
  void convertDateBeginGreaterThanEnd(){
    Appointment appointment = new Appointment();
    Appointment app = new Appointment("zoom meeting","02/04/2021","09:15 AM","02/03/2021","11:00 AM");
    assertThat(app.validateBeginLessThanEndDate(app.getBeginTime(),app.getEndTime()),containsString("Begin date/time cannot be greater than or equal to the end date/time for an appointment!"));
  }
}
