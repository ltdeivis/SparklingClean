package com.example.sparklingclean.DB;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Supplier extends DatabaseObject {

    private static String tableName = "supplier";

    private String name;
    private String address;
    private String telephone;
    private String email;
    private String collection;

    /**
     * Runs insert into DB function
     * @param primaryKey Supplier primary key
     */
    public Supplier(int primaryKey){
        insertIntoDB();
    }

    /**
     * Constructor
     */
    private Supplier(){

    }

    /**
     * Delete object from database
     */
    public void delete() {
        Appointment.deleteAllFromSupplier(primaryKey);
        deleteEntity(tableName);
    }

    /**
     * Fetches supplier using sortby
     * @param searchString Value inserted in search bar
     * @param sortBy User input select in filter combobox
     * @return Suppliers sorted by search
     */
    public static List<Supplier> search(String searchString, String sortBy) {
        Supplier supplier = new Supplier();
        if(searchString.isEmpty()) {
            return supplier.fetchSuppliers(sortBy);
        }
        else {
            return supplier.fetchSuppliers(searchString, sortBy);
        }
    }

    /**
     * SQL Select statement to find multiple suppliers
     * @param sortBy Filters suppliers
     * @return Sorted list of suppliers
     */
    private List<Supplier> fetchSuppliers(String sortBy){
        ResultSet rs = getField("SELECT * FROM supplier ORDER BY " + sortBy + " asc;");
        List<Supplier> suppliers = new ArrayList<>();
        try {
            while(rs.next()){
                Supplier supplier = new Supplier();

                supplier.primaryKey = rs.getInt("id");
                supplier.name = rs.getString("name");
                supplier.address = rs.getString("address");
                supplier.email = rs.getString("email");
                supplier.telephone = rs.getString("telephone");
                supplier.collection = rs.getString("collection");

                suppliers.add(supplier);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return suppliers;
    }

    /**
     * SQL Select statement to find multiple suppliers
     * @param searchString Search bar input
     * @param sortBy Sort combobox input
     * @return Sorted Supplier list with search criteria
     */
    private List<Supplier> fetchSuppliers(String searchString, String sortBy) {
        List<Supplier> suppliers = new ArrayList<>();

        ResultSet rs = getField("SELECT * FROM supplier WHERE name LIKE '%" + searchString + "%' ORDER BY " + sortBy + " ASC;");
        try {
            while(rs.next()){
                Supplier supplier = new Supplier();

                supplier.primaryKey = rs.getInt("id");
                supplier.name = rs.getString("name");
                supplier.address = rs.getString("address");
                supplier.email = rs.getString("email");
                supplier.telephone = rs.getString("telephone");
                supplier.collection = rs.getString("collection");

                suppliers.add(supplier);

                System.out.println("Supplier found: name - " + supplier.name);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return suppliers;
    }

    /**
     * Uses insert into DB to insert new supplier;
     * @return Supplier
     */
    public static Supplier createSupplier() {
        Supplier supplier = new Supplier();
        supplier.insertIntoDB();
        return supplier;
    }

    /**
     * Finds all suppliers
     * @return Suppliers
     */
    public static List<Supplier> findAllSupplier() {
        Supplier supplier = new Supplier();
        return supplier.fetchSuppliers();
    }

    /**
     * Finds a single supplier
     * @param name Supplier name
     * @return Supplier
     */
    public static Supplier findSupplier(String name){
        Supplier supplier = new Supplier();
        if(!supplier.fetchSupplier(name)){
            return null;
        }
        return supplier;
    }

    /**
     * Finds a single supplier using sql select query
     * @param name Supplier name
     * @return Supplier if true
     */
    private boolean fetchSupplier(String name){
        ResultSet rs = getField("SELECT * FROM supplier WHERE name ='" + name + "'");
        try {
            if(rs.next()){
                this.primaryKey = rs.getInt("id");
                this.name = rs.getString("name");
                this.address = rs.getString("address");
                this.telephone = rs.getString("telephone");
                this.email = rs.getString("email");
                this.collection = rs.getString("collection");

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
     * Finds all suppliers using query
     * @return All supplier if true
     */
    private List<Supplier> fetchSuppliers(){
        ResultSet rs = getField("SELECT * FROM supplier");
        List<Supplier> suppliers = new ArrayList<>();
        try {
            while(rs.next()){
                Supplier supplier = new Supplier();

                supplier.primaryKey = rs.getInt("id");
                supplier.name = rs.getString("name");
                supplier.address = rs.getString("address");
                supplier.telephone = rs.getString("telephone");
                supplier.email = rs.getString("email");
                supplier.collection = rs.getString("collection");

                suppliers.add(supplier);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return suppliers;
    }

    /**
     * Inserts supplier into DB
     * @return
     */
    public boolean insertIntoDB() {
        // Checking if there are no name duplicates.
        ResultSet rs = getField("SELECT ID FROM supplier WHERE name ='" + name + "'");
        try {
            if(rs.next()){
                closeConnection();
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        // Inserting blank supplier into the database.
        String vals = "0" + ",'" + name + "','" + address + "','" + telephone + "','" + email + "','" + collection + "'";
        insertEntity(vals,tableName);
        ResultSet primaryKeyRS = getField("SELECT ID FROM supplier WHERE name ='" + name + "'");
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
     * Gets name
     * @return
     */
    public String getName(){
        ResultSet rs = getSingleField("name", tableName);
        try {
            while(rs.next()){
                name = rs.getString(1);
            }
            closeConnection();
            return name;
        }   catch (SQLException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * Sets name
     * @param name
     */
    public void setName(String name){
        this.name = name;
        updateSingleField(name, tableName, "name");
    }

    /**
     * Gets address
     * @return
     */
    public String getAddress(){
        ResultSet rs = getSingleField("address", tableName);
        try {
            while(rs.next()) {
                address = rs.getString(1);
            }
            closeConnection();
            return address;
        }   catch (SQLException e) {
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
     * Gets telephone
     * @return
     */
    public String getTelephone(){
        ResultSet rs = getSingleField("telephone", tableName);
        try {
            while(rs.next()){
                telephone = rs.getString(1);
            }
            closeConnection();
            return telephone;
        }   catch (SQLException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * Sets telephone
     * @param telephone
     */
    public void setTelephone(String telephone){
        this.telephone = telephone;
        updateSingleField(telephone, tableName, "telephone");
    }

    /**
     * Gets email
     * @return
     */
    public String getEmail(){
        ResultSet rs = getSingleField("email", tableName);
        try {
            while(rs.next()){
                email = rs.getString(1);
            }
            closeConnection();
            return email;
        }   catch (SQLException e) {
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

    public String getCollection(){
        ResultSet rs = getSingleField("collection", tableName);
        try {
            while(rs.next()) {
                collection = rs.getString(1);
            }
            closeConnection();
            return collection;
        }   catch (SQLException e) {
            e.printStackTrace();
        }
        return "";
    }

    public void setCollection(String collection){
        this.collection = collection;
        updateSingleField(collection, tableName, "collection");
    }

    /**
     * Gets primary key
     * @return
     */
    public int getPrimaryKey() {
        return primaryKey;
    }
}
