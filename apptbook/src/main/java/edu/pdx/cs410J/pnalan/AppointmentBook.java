package edu.pdx.cs410J.pnalan;
import edu.pdx.cs410J.AbstractAppointmentBook;
import edu.pdx.cs410J.AbstractAppointment;
import edu.pdx.cs410J.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.*;


public class AppointmentBook extends AbstractAppointmentBook<Appointment>{
    private String owner;
    private Collection<Appointment> appointments = new ArrayList<>();
    public AppointmentBook(){
        super();
    }
    public AppointmentBook(String owner){
        super();
        this.owner = owner;
    }
    public AppointmentBook(String owner,Appointment newAppointment){
        super();
        this.owner = owner;
        addAppointment(newAppointment);
    }
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



   /* public static Comparator<Appointment> compareByBeginTime = new Comparator<Appointment>() {
        public int compare(Appointment a, Appointment b) {
            Date s1 = a.getBeginTime();
            Date s2 = b.getBeginTime();
            if(s1.compareTo(s2) == 0) {
                return s1.compareToIgnoreCase(s2);
            }
            return s1.compareToIgnoreCase(s2);
        }
    };*/

}