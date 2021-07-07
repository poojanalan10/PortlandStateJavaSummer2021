package edu.pdx.cs410J.pnalan;

import edu.pdx.cs410J.AbstractAppointment;

import java.text.DateFormat;
import java.text.ParseException;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.text.SimpleDateFormat;
import java.util.concurrent.TimeUnit;
import java.util.Date;

/**
 * This class represents a <code>Appointment</code>
 */


public class Appointment extends AbstractAppointment {
  private final String description;
  private String beginDate ;
  private String endDate;
  private final String beginTimeString ;
  private final String endTimeString;
  private Date begin;
  private Date end;

  /**
   * Default constructor
   */
  public Appointment(){
    super();
    description = null;
    beginDate = null;
    endDate = null;
    beginTimeString = null;
    endTimeString = null;
    begin = null;
    end = null;
  }
  /**
   * Creates a new <code>Appointment</code>
   *
   * @param description
   *        The description of the appointment
   * @param beginDate
   *        The date on which the appointment starts
   * @param beginTimeString
   *        The time the appointment begins
   * @param endDate
   *        The date on which the appointment ends
   * @param endTimeString
   *        The time the appointment ends
   */
  public Appointment(String description,String beginDate,String beginTimeString,String endDate, String endTimeString)  {
    this.description = description;
    this.beginDate = beginDate;
    this.beginTimeString = beginTimeString;
    this.endDate = endDate;
    this.endTimeString = endTimeString;
    this.begin = convertDateFormat(this.getBeginDate() + " " + this.getBeginTimeString());
    this.end = convertDateFormat(this.getEndDate() + " " + this.getEndTimeString());
    if(begin.after(end)){
      System.err.println("Begin date/time cannot be greater than or equal to the end date/time for an appointment!");
      System.exit(1);
    }
  }


  /**
   * returns the begin date of an appointment
   * @return beginDate
   */
  public String getBeginDate(){
    return beginDate;
  }

  /**
   * returns the end date of an appointment
   * @return endDate
   */
  public String getEndDate(){
    return endDate;
  }

  /**
   * returns a String describing the begin date and time of the appointment
   * @return begin
   */
  @Override
  public String getBeginTimeString() {
    return beginTimeString;
  }

  /**
   * returns a String describing the ending date and time of this appointment
   * @return end
   */
  @Override
  public String getEndTimeString() {
    return endTimeString;
  }

  /**
   * @return returns a Date that represents the begin date time of this appointment
   */
  @Override
  public Date getBeginTime(){
    return begin;
  }

  /**
   * @return returns a Date that represents the end date and time of this appointment
   */
  @Override
  public Date getEndTime(){
    return end;
  }

  /**
   * returns a description of this appointment (for instance, "Have coffee with Marsha" )
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
   /* String pattern = "MM/dd/yyyy hh:mm";
    SimpleDateFormat formatter = new SimpleDateFormat(pattern);
    Date d = formatter.parse(dateString);
    return d;*/
      DateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy hh:mm");
      Date date1 =new SimpleDateFormat("dd/MM/yyyy hh:mm").parse(dateString);
     // System.out.println("Converted date: " + dateFormat.format(date1).toString());
      return dateFormat.parse(dateFormat.format(date1));

   } catch (ParseException e) {
      e.printStackTrace();
    }
    return null;
  }



  /**
   * Returns the duration/time for which the appointment lasts
   * @return duration
   */
  public double appointmentDuration(){
    double timedifference = getEndTime().getTime() - getBeginTime().getTime();
    double duration = TimeUnit.MILLISECONDS.toMinutes((long) timedifference) % 60;
    return duration;

  }
}
