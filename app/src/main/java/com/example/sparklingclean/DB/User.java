package com.example.sparklingclean.DB;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

public class User extends DatabaseObject {

    private static String tableName = "user";

    private String firstName = "";
    private String lastName= "";
    private String address = "";
    private String email = "";
    private String telNum = "";
    private String dob = "";
    private String username = "";
    private String password = "";
    private String type = "";
    private boolean isAdmin = false;

    private DatabaseObject userObject; // Manager / Employee / Client classes that depend on this instance

    /**
     * Static method to create a user and add to the database.
     * @param username user's username
     * @param password user's password
     */
    public static User registerUser(String username, String password) {
        User user = new User();
        user.username = username;
        user.password = password;

        if(user.insertIntoDB()) {
            return user;
        }

        return null;
    }

    /**
     * Empty constructor
     */
    private User() {
        this.primaryKey = 0;
    }

    /**
     * Finds user using username and passowrd
     * @param username user username
     * @param password user password
     * @return user if found
     */
    public static User findUser(String username, String password){
        User user = new User();
        if(!user.fetchUser(username, password)){
            return null;
        }
        return user;
    }

    /**
     * Finds a single user
     * @param username user username
     * @param password user password
     * @return
     */
    private boolean fetchUser(String username, String password){
        ResultSet rs = getField("SELECT * FROM " + tableName + " WHERE username ='" + username + "'" + " AND password ='" + password + "'" );
        try {
            if(rs.next()){
                this.username = username;
                this.password = password;
                this.primaryKey = rs.getInt("id");
                this.firstName = rs.getString("first_name");
                this.lastName = rs.getString("last_name");
                this.address = rs.getString("address");
                this.email = rs.getString("email");
                this.telNum = rs.getString("tel_num");
                this.dob = rs.getString("dob");
                this.type = rs.getString("type");
                this.isAdmin = rs.getBoolean("is_admin");

                return true;
            }else{
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Inserts new user into database.
     * @return Returns true if successful.
     */
    public boolean insertIntoDB() {
        String query = "SELECT id FROM " + tableName + " WHERE username = '" + username + "'";
        // Checking if there are no duplicate username and password combinations
        ResultSet rs = getField(query);
        try {
            if(rs.next()){
                closeConnection();
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        // Inserting blank user into database, primary key is auto incremented
        String vals = "0" + ",'" + firstName + "','" + lastName + "','" + address + "','" + email + "','" + telNum
                + "','" + dob + "','" + username + "','" + password + "','" + type + "'," + "false";
        insertEntity(vals,tableName);
        // Fetching primary key using username and password
        ResultSet primaryKeyRS = getField(query + " AND password ='" + password + "'" );
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

    /**
     * Gets first name
     * @return
     */
    public String getFirstName(){
        return firstName;
    }

    /**
     * Sets first name
     */
    public void setFirstName(String firstName){
        this.firstName = firstName;
        updateSingleField(firstName, tableName, "first_name");
    }

    /**
     * Gets last name
     * @return Last name
     */
    public String getLastName(){
        return lastName;
    }

    /**
     * Sets last name
     */
    public void setLastName(String lastName){
        this.lastName = lastName;
        updateSingleField(lastName, tableName, "last_name");
    }

    /**
     * Gets DoB
     * @return Date of birth
     */
    public String getDoB(){
        return dob;
    }

    /**
     * Sets dob
     * @param dob Date of birth
     */
    public void setDoB(String dob){
        this.dob = dob;
        updateSingleField(dob, tableName, "dob");
    }

    /**
     * Gets username
     * @return
     */
    public String getUsername(){
        return username;
    }

    /**
     * Sets username
     * @param password
     */
    public void setPassword(String password){
        this.password = password;
        updateSingleField(password, tableName, "password");
    }

    /**
     * Gets password
     * @return Password
     */
    public String getPassword(){
        return password;
    }

    /**
     * Sets email
     * @param email
     */
    public void setEmail(String email){
        this.email = email;
        updateSingleField(email, tableName, "email");
    }

    /**
     * Gets email
     * @return
     */
    public String getEmail(){
        return email;
    }

    /**
     * Sets address
     * @param address
     */
    public void setAddress(String address){
        this.address = address;
        updateSingleField(address, tableName, "address");
    }

    /**
     * Gets address
     * @return
     */
    public String getAddress(){
        return address;
    }

    /**
     * Sets the telephone number
     * @param telNum Telephone Number
     */
    public void setTelNum(String telNum){
        this.telNum = telNum;
        updateSingleField(telNum, tableName, "tel_num");
    }

    /**
     * Gets Telephone Number
     * @return Telephone number
     */
    public String getTelNum(){
        return telNum;
    }

    /**
     * Returns type of user.
     * @param type Type of user.
     */
    public void setType(String type){
        this.type = type;
        updateSingleField(type, tableName, "type");
    }

    /**
     * Gets admin level of user
     * @return true if user is an admin
     */
    public String getType(){
        return type;
    }

    /**
     * Check if user is an admin
     * @return Return true if user is an admin
     */
    public boolean isAdmin(){
        ResultSet rs = getSingleField("is_admin",  tableName);
        try {
            while(rs.next()) {
                boolean isAdmin = rs.getBoolean("is_admin");
                closeConnection();
                return isAdmin;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void setUserObject(DatabaseObject object) {
        this.userObject = userObject;
    }

    public DatabaseObject getUserObject() {
        return userObject;
    }
}

