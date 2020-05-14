package com.example.sparklingclean.DB;


import java.sql.ResultSet;

public interface Entity {

    void updateField(String sqlStatement);

    ResultSet getField(String sqlStatement);

    void insertField(String sqlStatement);

    void deleteField(String sqlStatement);
}
