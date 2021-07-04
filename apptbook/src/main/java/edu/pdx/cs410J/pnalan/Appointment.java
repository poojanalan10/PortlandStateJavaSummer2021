package edu.pdx.cs410J.pnalan;

import edu.pdx.cs410J.AbstractAppointment;
import java.util.*;
import java.text.SimpleDateFormat;
import java.util.concurrent.TimeUnit;
import java.util.Date;

public class Appointment extends AbstractAppointment {
  public final String owner;
  public final String description;
  public final Date begin;
  public final Date end;
  /**
   * @param owner -  The person who owns the appt book
   * @param description - A description of the appointment
   * @param begin -  When the appt begins (24-hour time)
   * @param end - When the appt ends (24-hour time)
   */
  public AppointmentBook(String owner,String description,Date begin,Date end){
    this.owner = owner;
    this.description = description;
    this.beginTimeString = begin;
    this.endTimeString = end;
    this.begin = convertDateFormat(getBeginTimeString());
    this.end = convertDateFormat(getEndTimeString());
    if(begin.after(end)){
      System.err.println("Begin time cannot be greater than or equal to the end time for an appointment!");
      System.out.exit(1);
    }
  }
  /**
   * returns the owner of the appointment book
   * @return owner
   */
  public String getOwner(){
    return this.owner;
  }
  /**
   * returns the date and begin time of the appointment
   * @return begin
   */
  @Override
  public String getBeginTimeString() {
    return beginTimeString;
  }

  /**
   * returns the date and end time of the appointment
   * @return end
   */
  @Override
  public String getEndTimeString() {
    return endTimeString;
  }

  /**
   * @return returns the begin time
   */
  @Override
  public Date getBeginTime(){
    return begin;
  }

  /**
   * @return returns the end time
   */
  @Override
  public Date getEndTime(){
    return end;
  }

  /**
   * returns the description of the appointment
   * @return description
   */
  @Override
  public String getDescription() {
    return this.description;
  }

  /**
   * @param date
   * returns a date formatted according to a particular pattern mentioned in the pattern string variable
   * @return convertedDate
   */
  String convertDateFormat(Date date){
    try{
    String pattern = "mm/dd/yyyy hh:mm";
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
    String convertedDate = simpleDateFormat.format(date);
    return convertedDate;
    } catch (ParseException e){
        e.printStackTrace();
        System.exit(1);
    }

  }
  public double appointmentDuration(){
    double timedifference = getEndTime().getTime() - getBeginTime().getTime();
    double duration = TimeUnit.MILLISECONDS.toMinutes(timedifference) % 60;
    return duration;

  }
}
