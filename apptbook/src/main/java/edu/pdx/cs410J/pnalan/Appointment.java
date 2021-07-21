package edu.pdx.cs410J.pnalan;

import edu.pdx.cs410J.AbstractAppointment;
import edu.pdx.cs410J.ParserException;

import java.text.*;
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
    this.beginTimeString = beginTimeString ;
    this.endDate = endDate;
    this.endTimeString = endTimeString;
    this.begin = convertDateFormat(this.getBeginDate() + " " + this.getBeginTimeString());
    this.end = convertDateFormat(this.getEndDate() + " " + this.getEndTimeString());

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
    return beginTimeString ;
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
  public Date convertDateFormat(String dateString){
    try{

      Date date = new Date();
      DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy hh:mm a");
      date = dateFormat.parse(dateString);
      String strDate = DateFormat.getDateInstance(DateFormat.SHORT).format(date);
      String strTime = DateFormat.getTimeInstance(DateFormat.SHORT).format(date);
      date = dateFormat.parse(strDate + " " +strTime, new ParsePosition(0));
      return date;

   } catch (Exception e) {
      e.printStackTrace();
    }
    return null;
  }

  /**
   * This method returns the date and time string in a pretty format
   * @param dateString
   * @return
   * @throws ParserException
   * @throws ParseException
   */
  public String getPrettyDateTime(String dateString) throws ParserException, ParseException {
    String pattern = "MM/dd/yy HH:mm a";
    DateFormat df = new SimpleDateFormat(pattern);
    Date date = null;
    date = df.parse(dateString);
    String prettyDate = df.format(date);
    return prettyDate;

  }


  /**
   * Returns the duration/time for which the appointment lasts
   * @return duration
   */
  public double appointmentDuration( ){
    double timedifference =  this.getEndTime().getTime() - this.getBeginTime().getTime();
    double duration = TimeUnit.MILLISECONDS.toMinutes((long) timedifference);
    return duration;

  }

  /**
   * This method validates if the begin date entered is greater than the end date entered for a given appointment
   * @param begin
   * @param end
   * @return a message based on the comparison
   */
  public String validateBeginLessThanEndDate(Date begin,Date end){
    if(begin.after(end)){
      System.err.println("Begin date/time cannot be greater than or equal to the end date/time for an appointment!");
     return "Begin date/time cannot be greater than or equal to the end date/time for an appointment!";
    }
    else{
      return "begin date is before end date as expected";
    }
  }
}
