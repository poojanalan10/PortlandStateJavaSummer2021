package edu.pdx.cs410J.pnalan;
import edu.pdx.cs410J.AbstractAppointmentBook;

import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.security.InvalidParameterException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.*;

/**
 * A class AppointmentBook that maintains all the appointments of a particular owner
 */
public class AppointmentBook extends AbstractAppointmentBook<Appointment> {
    /**
     * Owner of the appointments in String format
     */
    private String owner;
    /**
     * appointments collection holds a record of all the appointments
     */
    private ArrayList<Appointment> appointments = new ArrayList<>();

    /**
     * an AppointmentBook constructor
     */
    public AppointmentBook() {
        super();
    }

    /**
     * Constructor with a single parameter that initializes the owner name
     *
     * @param owner The name of the owner of this appointment
     */
    public AppointmentBook(String owner) {
        super();
        this.owner = owner;
    }

    /**
     * Constructor with two parameters that initializes the owner name and adds a new appointment to an existing list
     *
     * @param owner          The name of the owner of this appointment
     * @param newAppointment A new appointment yet, to add to a collections of Appointments
     */
    public AppointmentBook(String owner, Appointment newAppointment) {
        super();
        this.owner = owner;
        addAppointment(newAppointment);
    }

    /**
     * This constructor is called when owner name and an appointment book is passed when creating an object
     *
     * @param ownerName        the name of the owner of the appointment
     * @param appointmentBooks the list of appointmentbook
     */
    public AppointmentBook(final String ownerName, AppointmentBook... appointmentBooks) {
        super();
        owner = ownerName;
        copyAppointments(appointmentBooks);
    }

    /**
     * This method copies all the appointments in the appointment book to an array list
     *
     * @param appointmentBooks the appointment book
     */
    protected void copyAppointments(AppointmentBook... appointmentBooks) {
        for (var app : appointmentBooks) {
            if (app != null && owner.equals(app.getOwnerName())) {
                appointments.addAll(app.getAppointments());
            } else {
                throw new InvalidParameterException();
            }
        }

    }

    /**
     * Returns the name of the owner of the appointment book
     *
     * @return owner
     */
    @Override
    public String getOwnerName() {
        return this.owner;
    }

    /**
     * Adds an appointment to the collection of Appointments
     *
     * @param app An appointment object
     */
    @Override
    public void addAppointment(Appointment app) {
        this.appointments.add(app);
    }


    /**
     * Returns all the appointments of a owner
     *
     * @return appointments collection
     */
    @Override
    public Collection<Appointment> getAppointments() {
        Collections.sort(appointments, AppointmentBook.startTime);
        return this.appointments;
    }


    /**
     * Returns appointments based on a comparison, first is start time and if they are equl next is end time and if they are also equal then it is by descriptiom
     */
    public static Comparator<Appointment> startTime = new Comparator<Appointment>() {
        @Override
        public int compare(Appointment o1, Appointment o2) {
            Date st1 = o1.getBeginTime();
            Date st2 = o2.getBeginTime();
            Date et1 = o1.getEndTime();
            Date et2 = o2.getEndTime();
            String des1 = o1.getDescription();
            String des2 = o2.getDescription();
            if (st1.compareTo(st2) == 0) {
                if (et1.compareTo(et2) == 0) {
                    return des1.compareTo(des2);
                } else {
                    return et1.compareTo(et2);
                }
            }
            return st1.compareTo(st2);

        }
    };

    /**
     * @param start the start of an appointment
     * @param end   the end of an appointment
     * @return
     * @throws InvalidParameterException throws invalid parameter when a parameter is invalid
     * @throws ParseException            throws invalid parameter when a date is parsed incorrectly
     */
    final AppointmentBook findAppointmentsWithDateRange(String start, String end) throws InvalidParameterException, ParseException {
            String aprettyappointments = null;
            AppointmentBook appointmentBook = new AppointmentBook(this.owner);
            var start1 = convertDateTime(start.toString());
            var end1 = convertDateTime(end.toString());

            if (!start1.before(end1)) {
                throw new InvalidParameterException("Start date and time cannot be after end date and time");
            } else {

                var apps = getAppointments();
                for (Appointment appointment : apps) {
                    var date = appointment.getBeginTime();
                    if ((date.after(start1) || date.equals(start1)) && (date.before(end1) || date.equals(start1))) {
                        //

                        appointmentBook.addAppointment(appointment);
                    }
                }

            }
            return appointmentBook;

    }

    /**
     * @param datestring the date which needs to be converted
     * @return date
     * the formatted date
     * @throws ParseException an exception when parse error
     */
    public static Date convertDateTime(String datestring) throws ParseException {
        try {
            Date date = new Date();
            DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy hh:mm a");
            date = dateFormat.parse(datestring);
            String strDate = DateFormat.getDateInstance(DateFormat.SHORT).format(date);
            String strTime = DateFormat.getTimeInstance(DateFormat.SHORT).format(date);
            date = dateFormat.parse(strDate + " " + strTime, new ParsePosition(0));
            return date;
        }
        catch (ParseException e){
            throw new ParseException(String.format("Date time cannot be parsed",datestring), 404);
        }


    }
}