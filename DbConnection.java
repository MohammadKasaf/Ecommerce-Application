package org.example.Ecommerce;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class DbConnection {

    // Database connection parameters
    private final String DbUrl = "jdbc:mysql://localhost:3306/Ecommerce";
    private final String userName = "root";
    private final String password = "Chishti@611";

    // Method to get a statement object for executing queries
    private Statement getStatement(){

        try{
            // Establishing a connection to the database
            Connection connection = DriverManager.getConnection(DbUrl, userName, password);
            // Returning a statement object for executing queries
            return connection.createStatement();
        }
        catch(Exception e){
            // Printing the stack trace if an exception occurs
            e.printStackTrace();
        }

        return null;
    }

    // Method to execute a query and return the result set
    public ResultSet getQueryTable(String query){

        try{
            // Getting a statement object
            Statement statement = getStatement();
            // Executing the query and returning the result set
            return statement.executeQuery(query);
        }
        catch(Exception e){
            // Printing the stack trace if an exception occurs
            e.printStackTrace();
        }
        return null;
    }

    // Method to execute an update query (insert, update, delete) and return the number of affected rows
    public int updateDatabase(String query){

        try{
            // Getting a statement object
            Statement statement = getStatement();
            // Executing the update query and returning the number of affected rows
            return statement.executeUpdate(query);
        }
        catch(Exception e){
            // Printing the stack trace if an exception occurs
            e.printStackTrace();
        }
        return 0;
    }

    // Main method to test the database connection
    public static void main(String args[]){

        // Creating an instance of DbConnection class
        DbConnection conn = new DbConnection();
        // Executing a test query to check the connection
        ResultSet rs = conn.getQueryTable("SELECT * FROM Customer");

        // Checking if the result set is not null
        if(rs != null){
            System.out.print("Connection successful");
        }
        else{
            System.out.print("Connection failed");
        }
    }
}
