package edu.pdx.cs410J.pnalan;

import edu.pdx.cs410J.AbstractAppointment;

import java.text.DateFormat;
import java.text.ParseException;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.text.SimpleDateFormat;
import java.util.concurrent.TimeUnit;
import java.util.Date;

public class Appointment extends AbstractAppointment {
  private final String description;
  private String beginDate;
  private String endDate;
  private final String beginTimeString;
  private final String endTimeString;
  private Date begin;
  private Date end;
  public Appointment(){
    super();
    description = null;
    beginDate = null;
    beginTimeString = null;
    endDate = null;
    endTimeString = null;
    begin = null;
    end = null;

  }

  //public Appointment(final String[] args){
  public Appointment(String description,String beginDate,String beginTimeString,String endDate, String endTimeString)  {
    this.description = description;
    this.beginDate = beginDate;
    this.beginTimeString = beginTimeString;
    this.endDate = endDate;
    this.endTimeString = endTimeString;
    this.begin = convertDateFormat(this.getBeginDate() + " " + this.getBeginTimeString());
    this.end = convertDateFormat(this.getEndDate() + " " + this.getEndTimeString());
    if(begin.after(end)){
      System.err.println("Begin time cannot be greater than or equal to the end time for an appointment!");
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
    String pattern = "MM/dd/yyyy hh:mm";
    SimpleDateFormat formatter = new SimpleDateFormat(pattern);
    Date d = formatter.parse(dateString);
    return d;
      /*DateFormat srcDf = new SimpleDateFormat("MM/dd/yyyy hh:mm");
      Date date = srcDf.parse(dateString);

      DateFormat destDf = new SimpleDateFormat("MM/dd/yyy hh:mm");
      dateString = destDf.format(date);
      System.out.print(dateString);
*/
    } catch (ParseException e) {
      e.printStackTrace();
    }
    return null;
  }
  public double appointmentDuration(){
    double timedifference = getEndTime().getTime() - getBeginTime().getTime();
    double duration = TimeUnit.MILLISECONDS.toMinutes((long) timedifference) % 60;
    return duration;

  }
}
