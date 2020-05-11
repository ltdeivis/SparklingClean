package com.example.sparklingclean.DB;


import java.sql.ResultSet;

public interface Entity {

    public void updateField(String sqlStatement);

    public ResultSet getField(String sqlStatement);

    public void insertField(String sqlStatement);

    public void deleteField(String sqlStatement);

    public void deleteRow();
}
