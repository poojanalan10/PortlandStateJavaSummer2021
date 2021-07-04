package edu.pdx.cs410J.pnalan;
import edu.pdx.cs410J.AbstractAppointmentBook;
import edu.pdx.cs410J.AbstractAppointment;
import edu.pdx.cs410J.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.*;


public class AppointmentBook extends AbstractAppointmentBook<Appointment>{
    private Collection<Appointment> appointments = new ArrayList<>();
    public AppointmentBook(){

        super();
    }

    @Override
    public String getOwnerName() {
        for(var app : appointments){
            if(app != null){
                return app.getOwner();
            }
        }
        return null;
    }

    public AppointmentBook(Appointment newAppointment){
        super();
        addAppointment(newAppointment);
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
     * Adds an appointment to the collection of Appointments
     * @param app - An appointment object
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
    public Collection<Appointment> getAppointments() {
      //  Collections.sort(appointments,compareByBeginTime);
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