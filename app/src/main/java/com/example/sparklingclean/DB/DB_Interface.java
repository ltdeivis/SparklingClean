package com.example.sparklingclean.DB;

import android.annotation.SuppressLint;
import android.os.StrictMode;
import android.util.Log;

import java.sql.*;

public class DB_Interface {

    private String dbName = "";
    private String dbUsername = "";
    private String dbPassword = "";

    private Connection con = null;

    /**
     * Establish connection to DB.
     * @return DB Connnection.
     */
    @SuppressLint("NewApi")
    public Connection openConnection() {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                .permitAll().build();
        StrictMode.setThreadPolicy(policy);
        try {
            Log.d("Database", "Attempting to establish connection to database server...");
            con = DriverManager.getConnection(dbName+";user="+dbUsername+";password="+dbPassword+";");
            Log.d("Database", "Connection to " + con.getMetaData().getURL());
            return con;
        } catch (Exception ex) {
            Log.e("Database", ex.getMessage());
        }
        return null;
    }

    /**
     * Closes DB connection.
     */
    public void closeConnection(){
        try {
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Sets up database connection.
     * @param dbName Name of database: javabase.
     * @param dbUsername DB Username : java
     * @param dbPassword DB Password : password
     * @throws ClassNotFoundException
     */
    public void setupConnection(String dbName, String dbUsername, String dbPassword) throws ClassNotFoundException {
        this.dbName = dbName;
        this.dbUsername = dbUsername;
        this.dbPassword = dbPassword;

        Log.d("Database Driver", "Looking for JTDS Drivers");
        Class.forName("net.sourceforge.jtds.jdbc.Driver");
        Log.d("Database Driver", "JTDS Drivers found");
    }

    /**
     * Default constructor
     */
    private DB_Interface() {
    }

    /**
     * Makes sure only one instace of db interface can run at once.
     * @return DB_interface
     */
    public static DB_Interface DB_Interface()
    {
        // To ensure only one instance is created
        if (db_interface == null)
        {
            db_interface = new DB_Interface();
        }
        return db_interface;
    }

    private static DB_Interface db_interface = null;
}
