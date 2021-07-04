package edu.pdx.cs410J.pnalan;
import edu.pdx.cs410J.AbstractAppointmentBook;


public class AppointmentBook extends AbstractAppointmentBook<Appointment>{
    private Collection<Appointment> appointments = new ArrayList<>();
    public AppointmentBook(){
        super();
    }
    public AppointmentBook(Appointment newAppointment){
        super();
        addAppointment(newAppointment);
    }
    public void allAppointments(AppointmentBook... appointments){
     for(var app: appointments)   {
         if(app != null) {
             appointments.addAll(app.getAppointments());
         }
         else{
             System.out.println("No appointments to add!");
         }
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
    public Collection<Appointment> getAppointments() {
        Collections.sort(appointments,compareByBeginTime);
        return this.appointments;
    }

    Comparator<Appointment> compareByBeginTime = new Comparator<Appointment>(){
        Collections.sort(appointments,new Comparator<Appointment>() {
        @Override
        public int compare(Appointment a, Appointment b) {
            return a.getBeginTime().compareToIgnoreCase(b.getBeginTime());
        }
    });

}