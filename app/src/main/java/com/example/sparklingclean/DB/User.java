package com.example.sparklingclean.DB;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

public class User extends DatabaseObject {

    private static String tableName = "user";

    private String firstName = "";
    private String lastName= "";
    private String DoB = "";
    private String username = "";
    private String password = "";
    private String email = "";
    private String address = "";
    private String secQ = "";
    private String secA = "";

    /**
     * Inerts user into DB
     * @param primaryKey user PK
     * @param username user username
     * @param password user password
     */
    public User(int primaryKey, String username, String password){
        this.primaryKey = primaryKey;
        this.username = username;
        this.password = password;

        insertIntoDB();
    }

    /**
    Create empty user
     */
    private User(){

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
        ResultSet rs = getField("SELECT * FROM USER WHERE USERNAME ='" + username + "'" + "AND PASSWORD ='" + password + "'" );
        try {
            if(rs.next()){
                this.username = username;
                this.password = password;
                this.primaryKey = rs.getInt("id");
                this.firstName = rs.getString("first_name");
                this.lastName = rs.getString("last_name");
                this.DoB = rs.getString("DoB");
                this.email = rs.getString("email");
                this.address = rs.getString("address");
                this.secQ = rs.getString("security_question");
                this.secA = rs.getString("security_answer");

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
     * Inserts new user into database after registering
     * @return user if true
     */
    public boolean insertIntoDB() {
        // Checking if there are no duplicate username and password combinations
        ResultSet rs = getField("SELECT ID FROM USER WHERE username ='" + username + "'");
        try {
            if(rs.next()){
                closeConnection();
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        // Inserting blank user into database, primary key is auto incremented
        String vals = "0" + ",'" + firstName + "','" + lastName + "','" + DoB + "','" + username + "','" + password + "','" + email + "','" + address + "','" + secQ + "','" + secA + "'," + "false";
        insertEntity(vals,tableName);
        // Fetching primary key using username and password
        ResultSet primaryKeyRS = getField("SELECT ID FROM USER WHERE username ='" + username + "' AND password ='" + password + "'" );
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
        ResultSet rs = getSingleField("first_name", tableName);
        try {
            while(rs.next()){
                firstName = rs.getString(1);
            }
            closeConnection();
            return firstName;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "";
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
     * @return
     */
    public String getLastName(){
        ResultSet rs = getSingleField("last_name", tableName);
        try {
            while(rs.next()){
                lastName = rs.getString(1);
            }
            closeConnection();
            return lastName;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "";
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
     */
    public String getDoB(){
        ResultSet rs = getSingleField("DoB", tableName);
        try {
            DoB = rs.getString(1);
            closeConnection();
            return DoB;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Sets dob
     * @param DoB
     */
    public void setDoB(String DoB){
        this.DoB = DoB;
        updateSingleField(DoB, tableName, "dob");
    }

    /**
     * Gets username
     * @return
     */
    public String getUsername(){
        ResultSet rs = getSingleField("username",  tableName);
        try {
            username = rs.getString(1);
            closeConnection();
            return username;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "";
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
     * @return
     */
    public String getPassword(){
        ResultSet rs = getSingleField("password",  tableName);
        try {
            password = rs.getString(1);
            closeConnection();
            return password;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "";
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
        ResultSet rs = getSingleField("email",  tableName);
        try {
            email = rs.getString(1);
            closeConnection();
            return email;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "";
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
        ResultSet rs = getSingleField("address",  tableName);
        try {
            address = rs.getString(1);
            closeConnection();
            return address;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * Sets security question
     * @param secQ
     */
    public void setSecQ(String secQ){
        this.secQ = secQ;
        updateSingleField(secQ, tableName, "security_question");
    }

    /**
     * Gets security question
     * @return
     */
    public String getSecQ(){
        ResultSet rs = getSingleField("security_question",  tableName);
        try {
            secQ = rs.getString(1);
            closeConnection();
            return secQ;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * Sets security answer
     * @param secA
     */
    public void setSecA(String secA){
        this.secA = secA;
        updateSingleField(secA, tableName, "security_answer");
    }

    /**
     * Gets security answer
     * @return
     */
    public String getSecA(){
        ResultSet rs = getSingleField("security_answer",  tableName);
        try {
            secA = rs.getString(1);
            closeConnection();
            return secA;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "";
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
}

