package edu.pdx.cs410J.pnalan;

import edu.pdx.cs410J.AbstractAppointment;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.concurrent.TimeUnit;
import java.util.Date;

/**
 * This class represents a <code>Appointment</code>
 */

public class Appointment extends AbstractAppointment {
  /**
   * description
   *        The description of the appointment
   */
  private final String description;

  /**
   *  beginDate
   *        The date on which the appointment starts
   */
  private String beginDate ;

  /** endDate
  *        The date on which the appointment ends
  */
  private String endDate;

  /**
   * beginTimeString
   *        The time the appointment begins
   */
  private final String beginTimeString ;


  /** endTimeString
   *   The time the appointment ends
   */
  private final String endTimeString;

  /** begin
   *      The begin date and time of an appointment as a date object
   */
  private Date begin;

  /**
   * end
   *  The end date and time of an appointment as a date object
   */
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
   *
   */
  public Appointment(String description,String beginDate,String beginTimeString,String endDate, String endTimeString) {
    this.description = description;
    this.beginDate = beginDate;
    this.beginTimeString = beginTimeString;
    this.endDate = endDate;
    this.endTimeString = endTimeString;
    try {
      this.begin = new SimpleDateFormat("MM/dd/yyyy HH:mm").parse(this.getBeginDate() + " " + this.getBeginTimeString());
    } catch (ParseException e) {
      e.printStackTrace();
    }
    try {
      this.end = new SimpleDateFormat("MM/dd/yyyy HH:mm").parse(this.getEndDate() + " " + this.getEndTimeString());
    } catch (ParseException e) {
      e.printStackTrace();
    }
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
   * @return beginTimeString
   */
  @Override
  public String getBeginTimeString() {
    return beginTimeString;
  }

  /**
   * returns a String describing the ending date and time of this appointment
   * @return endTimeString
   */
  @Override
  public String getEndTimeString() {
    return endTimeString;
  }

  /**
   * returns a Date that represents the begin date time of this appointment
   * @return begin
   */
  @Override
  public Date getBeginTime(){
    return begin;
  }

  /**
   * returns a Date that represents the end date and time of this appointment
   * @return end
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
   * returns a date formatted according to a particular pattern mentioned in the pattern string variable
   * @param dateString
   *        the date in string format that needs to be converted to a date object
   * @return convertedDate
   */
  public String convertDateFormat(String dateString){
    try{
      SimpleDateFormat dateformat = new SimpleDateFormat("MM/dd/yyyy HH:mm");
      return dateformat.format(dateString);

   } catch (Exception e) {
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
