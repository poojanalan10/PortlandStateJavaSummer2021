package edu.pdx.cs410J.pnalan;
import edu.pdx.cs410J.AbstractAppointmentBook;
import edu.pdx.cs410J.AbstractAppointment;
import edu.pdx.cs410J.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.*;


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
     */
    public AppointmentBook(String owner){
        super();
        this.owner = owner;
    }

    /**
     * Constructor with two parameters that initializes the owner name and adds a new appointment to an existing list
     * @param owner
     * @param newAppointment
     */
    public AppointmentBook(String owner,Appointment newAppointment){
        super();
        this.owner = owner;
        addAppointment(newAppointment);
    }

    /**
     * This constructor takes two parameters and adds all the appointments of a owner
     * @param owner
     * @param appBook
     */
    public AppointmentBook(String owner, AppointmentBook... appBook){
        super();
        this.owner = owner;
        allAppointments(appBook);
    }
    @Override
    public String getOwnerName() {
        return this.owner;
    }

    /**
     * Adds an appointment to the collection of Appointments
     * @param app - An appointment object
     */
    @Override
    public void addAppointment(Appointment app){
        this.appointments.add(app);
    }

    /**
     * This method is used for adding all the appointments to the appointments collection
     * @param appBook
     */
    public void allAppointments(AppointmentBook... appBook){
     for(var app: appBook)   {
         if(app != null ) {
             appointments.addAll(app.getAppointments());
         }
         else{
             System.out.println("No appointments to add!");
         }
         }
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