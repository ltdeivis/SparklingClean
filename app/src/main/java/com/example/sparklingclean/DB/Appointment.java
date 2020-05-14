package com.example.sparklingclean.DB;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Appointment extends DatabaseObject {

    private static String tableName = "appointment";

    private String app_date = "";
    private String app_time = "";
    private String notes = "";
    private String rating = "";
    private String hours = "";
    private int client_id;
    private int employee_id;

    /**
     * Default constructor for Appointment.
     * @param client_id ID of the client.
     * @param app_date Requested date of appointment.
     * @param app_time Requested time of appointment.
     * @param hours Duration of the appointment.
     */
    public Appointment(int client_id, String app_date, String app_time, String hours){
        this.client_id = client_id;
        this.app_date = app_date;
        this.app_time = app_time;
        this.hours = hours;

        insertIntoDB();
    }

    /**
     * Default empty constructor
     */
    private Appointment(){

    }

    /**
     * Delete itself from database.
     */
    public void deleteSelf(){
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
     * Inserts pending appointment into db
     * @return
     */
    private boolean insertIntoDB() {
        String query = "SELECT ID FROM appointment WHERE app_date ='" + app_date + "' AND app_time ='"
                + app_time + "' AND client_id ='" + String.valueOf(client_id) + "'";
        ResultSet rs = getField(query);
        try {
            if(rs.next()){
                closeConnection();
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        // Inserting pending appointment
        String vals = "0" + ",'" + app_date + "','" + app_time + "','" + notes + "','" + rating + "','" + hours + "'," + client_id + "," + employee_id;
        insertEntity(vals,tableName);
        ResultSet primaryKeyRS = getField(query);
        try {
            while (primaryKeyRS.next()){
                primaryKey = primaryKeyRS.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        closeConnection();
        return true;
    }
    
    public void loadFromDB() {
        ResultSet rs = getField("SELECT * FROM appointment WHERE id = " + primaryKey);
        try {
            while(rs.next()){
                this.app_date = rs.getString("date");
                this.app_time = rs.getString("time");
                this.notes = rs.getString("notes");
                this.rating = rs.getString("rating");
                this.hours = rs.getString("hours");
                this.client_id = rs.getInt("client_id");
                this.employee_id = rs.getInt("employee_id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Gets app_date
     * @return app_date
     */
    public String getApp_date(){
        return app_date;
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
        return app_time;
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
        return notes;
    }

    /**
     * Sets notes
     * @param notes
     */
    public void setnotes(String notes){
        this.notes = notes;
        updateSingleField(notes, tableName, "notes");
    }

    /**
     * Gets rating
     * @return
     */
    public String getRating(){
        return rating;
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
        return hours;
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
     * Get client_id. Reads from database not memory as it could be changed by manager at any time.
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
