package com.example.sparklingclean.DB;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseObject implements Entity {

    protected int primaryKey;

    public DatabaseObject() {

    }

    /**
     * Retrieves a single field from the database using primary key
     * @param field Field to select from the table
     * @param tableName Table to select from
     * @return
     */
    public ResultSet getSingleField(String field, String tableName) {
        return getField("select " + field + " from " + tableName + " where id = " + primaryKey + ";");
    }

    /**
     * Inserts a value or values into a table.
     * @param values Values to be inserted.
     * @param tableName Name of the table to be inserted in.
     */
    public void insertEntity(String values, String tableName){
        insertField("insert into " + tableName + " values("+values+");");
    }

    /**
     * Inserts a value or values into a table.
     * @param tableName Name of the table to be inserted in.
     */
    public void deleteEntity(String tableName){
        insertField("delete from " + tableName + " where id = " + primaryKey + ";");
    }

    /**
     * Updates a table using the values sent in based on primary key.
     * @param value Values to be updated.
     * @param tableName Name of table to be updated.
     * @param fieldName Names of field within table to be updated.
     */

    public void updateSingleField(String value, String tableName, String fieldName){
        updateField("update " + tableName + " set " + fieldName + " = '" + value + "' where id = " + primaryKey + ";");
    }

    /**
     * Closes database connection.
     */
    protected void closeConnection(){
        DB_Interface db_interface = DB_Interface.DB_Interface();
        db_interface.closeConnection();
    }

    /**
     * Executes sqlquery.
     * @param sqlStatement Query to be exectuted.
     */
    @Override
    public void updateField(String sqlStatement) {
        DB_Interface db_interface = DB_Interface.DB_Interface();
        Connection con = db_interface.openConnection();

        try (Statement stmt = con.createStatement())
        {
            stmt.executeUpdate(sqlStatement);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        closeConnection();
    }

    /**
     * Retrieves information from db.
     * @param sqlStatement Query to be executed.
     * @return Query results.
     */
    @Override
    public ResultSet getField(String sqlStatement) {
        DB_Interface db_interface = DB_Interface.DB_Interface();
        Connection con = db_interface.openConnection();

        try
        {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(sqlStatement);
            return rs;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Inserts information in db.
     * @param sqlStatement Statement to be executed.
     */
    @Override
    public void insertField(String sqlStatement) {
        DB_Interface db_interface = DB_Interface.DB_Interface();
        Connection con = db_interface.openConnection();

        try (Statement stmt = con.createStatement())
        {
            stmt.executeUpdate(sqlStatement);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        closeConnection();
    }

    /**
     * Delete row in db.
     * @param sqlStatement Statement to be executed.
     */
    @Override
    public void deleteField(String sqlStatement) {
        DB_Interface db_interface = DB_Interface.DB_Interface();
        Connection con = db_interface.openConnection();

        try (Statement stmt = con.createStatement())
        {
            stmt.executeUpdate(sqlStatement);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        closeConnection();
    }
}
