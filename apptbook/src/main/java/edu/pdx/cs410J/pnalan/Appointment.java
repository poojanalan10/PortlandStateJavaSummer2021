package edu.pdx.cs410J.pnalan;

import edu.pdx.cs410J.AbstractAppointment;

import java.text.ParseException;
import java.util.*;
import java.text.SimpleDateFormat;
import java.util.concurrent.TimeUnit;
import java.util.Date;

public class Appointment extends AbstractAppointment {
  private final String owner;
  private final String description;
  private final String beginTimeString;
  private final String endTimeString;
  private Date begin;
  private Date end;
  public Appointment(){
    super();
    owner = null;
    description = null;
    beginTimeString = null;
    endTimeString = null;
    begin = null;
    end = null;

  }
  /**
   * @param owner -  The person who owns the appt book
   * @param description - A description of the appointment
   * @param begin -  When the appt begins (24-hour time)
   * @param end - When the appt ends (24-hour time)
   */
  public Appointment(final String[] args){
    this.owner = args[1];
    this.description = args[2];
    this.beginTimeString = args[3];
    this.endTimeString = args[4];
    this.begin = convertDateFormat(this.getBeginTimeString());
    this.end = convertDateFormat(this.getEndTimeString());
    if(begin.after(end)){
      System.err.println("Begin time cannot be greater than or equal to the end time for an appointment!");
      System.exit(1);
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
   * @param dateString
   * returns a date formatted according to a particular pattern mentioned in the pattern string variable
   * @return convertedDate
   */
  public Date convertDateFormat(String dateString){
    try{
    String pattern = "mm/dd/yyyy hh:mm";
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
    Date convertedDate = simpleDateFormat.parse(dateString);
    return convertedDate;
    } catch (ParseException e){
        e.printStackTrace();
        System.exit(1);
    }
    return null;
  }
  public double appointmentDuration(){
    double timedifference = getEndTime().getTime() - getBeginTime().getTime();
    double duration = TimeUnit.MILLISECONDS.toMinutes((long) timedifference) % 60;
    return duration;

  }
}
