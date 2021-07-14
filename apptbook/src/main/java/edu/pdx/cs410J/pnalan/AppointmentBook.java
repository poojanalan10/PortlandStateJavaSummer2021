package edu.pdx.cs410J.pnalan;
import edu.pdx.cs410J.AbstractAppointmentBook;
import edu.pdx.cs410J.AbstractAppointment;
import edu.pdx.cs410J.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.*;

/**
 * A class AppointmentBook that maintains all the appointments of a particular owner
 */
public class AppointmentBook extends AbstractAppointmentBook<Appointment>{
    /**
     * Owner of the appointments in String format
     */
    private String owner;
    /**
     * appointments collection holds a record of all the appointments
     */
    private Collection<Appointment> appointments = new ArrayList<>();

    /**
     * an AppointmentBook constructor
     */
    public AppointmentBook(){
        super();
    }

    /**
     * Constructor with a single parameter that initializes the owner name
     * @param owner
     *        The name of the owner of this appointment
     */
    public AppointmentBook(String owner){
        super();
        this.owner = owner;
    }

    /**
     * Constructor with two parameters that initializes the owner name and adds a new appointment to an existing list
     * @param owner
     *        The name of the owner of this appointment
     * @param newAppointment
     *        A new appointment yet, to add to a collections of Appointments
     *
     */
    public AppointmentBook(String owner,Appointment newAppointment){
        super();
        this.owner = owner;
        addAppointment(newAppointment);
    }



    /**
     * Returns the name of the owner of the appointment book
     * @return owner
     */
    @Override
    public String getOwnerName() {
        return this.owner;
    }

    /**
     * Adds an appointment to the collection of Appointments
     * @param app
     *        An appointment object
     *
     */
    @Override
    public void addAppointment(Appointment app){
        this.appointments.add(app);
    }


    /**
     * Returns all the appointments of a owner
     * @return appointments collection
     */
    @Override
    public Collection<Appointment> getAppointments(){
        return this.appointments;
    }

}