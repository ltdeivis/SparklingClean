package com.example.sparklingclean.DB;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Appointment extends DatabaseObject {

    private static String tableName = "appointment";

    private String app_date;
    private String app_time;
    private String notes;
    private String rating;
    private String hours;
    private int client_id;
    private int employee_id;

    /**
     * Runs insert into DB function.
     * @param client_id
     */
    public Appointment(int client_id, String app_date, String app_time, String hours){
        insertIntoDB();
    }

    /**
     * Constructor
     */
    private Appointment(){

    }

    /**
     * Delete appointment based on PK
     */
    @Override
    public void deleteRow(){
        this.deleteEntity(tableName);
    }

    /**
     * Selects multiple appointments.
     * @return All appointments.
     */
    public static List<Appointment> findAllAppointments() {
        Appointment appointment = new Appointment();
        return appointment.fetchAppointments();
    }

    /**
     * Fetches all of a client's appointments
     * @param client_id Is the client's id
     * @return Multiple appointments
     */
    public static List<Appointment> fetchClientAppointments(int client_id){
        Appointment appointment = new Appointment();
        return appointment.fetchAppointments(client_id, "client_id");
    }

    /**
     * Fetches all of an employee's appointments
     * @param employee_id Is the employee's id
     * @return Multiple appointments
     */
    public static List<Appointment> fetchEmployeeAppointments(int employee_id){
        Appointment appointment = new Appointment();
        return appointment.fetchAppointments(employee_id, "employee_id");
    }

    /**
     * Inserts an empty appointment into the database
     * @param client_id Client_id for insertion
     * @param employee_id Employee_id for insertion
     * @return A single appointment
     */
    public static Appointment createAppointment(int client_id, int employee_id) {
        Appointment appointment = new Appointment();
        appointment.client_id = client_id;
        appointment.employee_id = employee_id;
        appointment.insertIntoDB();
        return appointment;
    }

    /**
     * Fetches appointments that match search string
     * @param searchString String used to match results against database
     * @param sortBy Sorts the results using the passed value
     * @return Appointments sorted by search
     */
    public static List<Appointment> search(String searchString, String sortBy) {
        Appointment appointment = new Appointment();
        if(searchString.isEmpty()) {
            return appointment.fetchAppointments(sortBy);
        }
        else {
            return appointment.fetchAppointments(searchString, sortBy);
        }
    }

    /**
     * SQL Select statement to find all appointments
     * @return List of all appointments
     */
    private List<Appointment> fetchAppointments(){
        ResultSet rs = getField("SELECT * FROM appointment");
        List<Appointment> appointments = new ArrayList<>();
        try {
            while(rs.next()){
                Appointment appointment = new Appointment();

                appointment.primaryKey = rs.getInt("id");
                appointment.app_date = rs.getString("date");
                appointment.app_time = rs.getString("time");
                appointment.notes = rs.getString("notes");
                appointment.rating = rs.getString("rating");
                appointment.hours = rs.getString("hours");
                appointment.client_id = rs.getInt("client_id");
                appointment.employee_id = rs.getInt("employee_id");

                appointments.add(appointment);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return appointments;
    }

    /**
     * Find appointments based on client or employee id used
     * @return appointments based on client_id or employee_id
     */
    private List<Appointment> fetchAppointments(int selectedID, String colName){
        ResultSet rs = getField("SELECT * FROM appointment where " + colName + " = " + selectedID);
        List<Appointment> appointments = new ArrayList<>();
        try {
            while(rs.next()){
                Appointment appointment = new Appointment();

                appointment.primaryKey = rs.getInt("id");
                appointment.app_date = rs.getString("date");
                appointment.app_time = rs.getString("time");
                appointment.notes = rs.getString("notes");
                appointment.rating = rs.getString("rating");
                appointment.hours = rs.getString("hours");
                appointment.client_id = rs.getInt("client_id");
                appointment.employee_id = rs.getInt("employee_id");

                appointments.add(appointment);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return appointments;
    }

    /**
     * SQL Select statement to find multiple appointments
     * @param sortBy Filters appointments
     * @return appointments if true
     */
    private List<Appointment> fetchAppointments(String sortBy){
        ResultSet rs = getField("SELECT * FROM appointment ORDER BY " + sortBy + " asc;");
        List<Appointment> appointments = new ArrayList<>();
        try {
            while(rs.next()){
                Appointment appointment = new Appointment();

                appointment.primaryKey = rs.getInt("id");
                appointment.app_date = rs.getString("date");
                appointment.app_time = rs.getString("time");
                appointment.notes = rs.getString("notes");
                appointment.rating = rs.getString("rating");
                appointment.hours = rs.getString("hours");
                appointment.client_id = rs.getInt("client_id");
                appointment.employee_id = rs.getInt("employee_id");

                appointments.add(appointment);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return appointments;
    }

    /**
     * Fetches all appointments matching search string and sorted using sortBy
     * @param searchString String used to match against the database
     * @param sortBy Selected sorting
     * @return Appointment list
     */
    private List<Appointment> fetchAppointments(String searchString, String sortBy) {
        List<Appointment> appointments = new ArrayList<>();

        //TODO: Fix so that client can search by date. Employee search by date, client data. Manager by date, rating, client data, employee data.
//        ResultSet rs = getField("SELECT * FROM appointment WHERE name LIKE '%" + searchString + "%' ORDER BY " + sortBy + " ASC;");
//        try {
//            while(rs.next()){
//                Appointment appointment = new Appointment();
//
//                appointment.primaryKey = rs.getInt("id");
//                appointment.app_date = rs.getString("date");
//                appointment.app_time = rs.getString("time");
//                appointment.notes = rs.getString("notes");
//                appointment.rating = rs.getString("rating");
//                appointment.hours = rs.getString("hours");
//                appointment.client_id = rs.getInt("client_id");
//                appointment.employee_id = rs.getInt("employee_id");
//
//                appointments.add(appointment);
//
//                System.out.println("Fireplace found: name - " + appointment.name);
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }

        return appointments;
    }

    /**
     * Inserts empty appointment into db
     * @return
     */
    private boolean insertIntoDB() {

        //TODO: Check if an employee does not have an appointment at that time and day already.
        // Checking if there are no name duplicates.
//        ResultSet rs = getField("SELECT ID FROM appointment WHERE name ='" + name + "'");
//        try {
//            if(rs.next()){
//                closeConnection();
//                return false;
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        // Inserting blank appointment into the database.
//        String vals = "0" + ",'" + app_date + "','" + app_time + "','" + notes + "','" + rating + "','" + hours + "'," + client_id + "," + employee_id;
//        insertEntity(vals,tableName);
//        ResultSet primaryKeyRS = getField("SELECT ID FROM appointment WHERE name ='" + name + "'");
//        try {
//            while (primaryKeyRS.next()){
//                primaryKey = primaryKeyRS.getInt(1);
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        closeConnection();
        return true;
    }

    /**
     * Delete object from database
     */
    public void delete() {
        deleteEntity(tableName);
    }

    /**
     * Delete all appointments with this client id
     */
    public void delete(int supplier_id) {
        deleteField("DELETE FROM " + tableName + " WHERE supplier_id = " + supplier_id +";");
    }


    /**
     * Gets app_date
     * @return app_date
     */
    public String getApp_date(){
        ResultSet rs = getSingleField("app_date", tableName);
        try {
            while(rs.next()){
                app_date = rs.getString(1);
            }
            closeConnection();
            return app_date;
        }
            catch (SQLException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * Sets app_date
     * @param app_date
     */
    public void setApp_date(String app_date){
        this.app_date = app_date;
        updateSingleField(app_date, tableName, "app_date");
    }

    /**
     * Gets app_time
     * @return
     */
    public String getApp_time() {
        ResultSet rs = getSingleField("app_time", tableName);
        try {
            while (rs.next()) {
                app_time = rs.getString(1);
            }
            closeConnection();
            return app_time;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * Sets app_time
     * @param app_time
     */
    public void setApp_time(String app_time){
        this.app_time = app_time;
        updateSingleField(app_time, tableName, "app_time");
    }

    /**
     * Gets notes
     * @return notes
     */
    public String getNotes(){
        ResultSet rs = getSingleField("notes", tableName);
        try {
            while(rs.next()){
                notes = rs.getString(1);
            }
            closeConnection();
            return notes;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * Sets notes
     * @param notes
     */
    public void setnotes(String notes){
        this.notes = notes;
        updateSingleField(notes, tableName, "stockQ");
    }

    /**
     * Gets rating
     * @return
     */
    public String getRating(){
        ResultSet rs = getSingleField("rating", tableName);
        try {
            while(rs.next()){
                rating = rs.getString(1);
            }
            closeConnection();
            return rating;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * Sets rating
     * @param rating
     */
    public void setrating(String rating){
        this.rating = rating;
        updateSingleField(rating, tableName, "style");
    }

    /**
     * Gets hours
     * @return
     */
    public String getHours(){
        ResultSet rs = getSingleField("hours", tableName);
        try {
            while(rs.next()){
                hours = rs.getString(1);
            }
            closeConnection();
            return hours;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * Sets hours
     * @param hours
     */
    public void setHours(String hours){
        this.hours = hours;
        updateSingleField(hours, tableName, "finish");
    }

    /**
     * Get client_id
     * @return
     */
    public int getClient_id(){
        ResultSet rs = getSingleField("client_id", tableName);
        try {
            while(rs.next()){
                client_id = rs.getInt(1);
            }
            closeConnection();
            return client_id;
        }   catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
}
