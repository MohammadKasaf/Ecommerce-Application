package org.example.Ecommerce;

import java.sql.ResultSet;

public class Login {

    // Method to authenticate a customer login using email and password
    public Customer customerLogin(String email, String password) {

        // SQL query to select customer information based on provided email and password
        String query = "SELECT * FROM Customer WHERE Email='" + email + "' AND Password_id='" + password + "'";

        // Creating a database connection
        DbConnection connection = new DbConnection();

        try {
            // Executing the SQL query to retrieve customer information
            ResultSet rs = connection.getQueryTable(query);
            if (rs != null && rs.next()) {
                // Returning a new Customer object with retrieved information if login is successful
                return new Customer(
                        rs.getInt("id"),
                        rs.getString("userName"),
                        rs.getString("Email"),
                        rs.getString("mobile"));
            }
        } catch (Exception e) {
            // Printing the stack trace if an exception occurs
            e.printStackTrace();
        }

        // Returning null if login is unsuccessful
        return null;
    }

    // Main method to test the customerLogin method
    public static void main(String[] args) {

        // Creating an instance of Login class
        Login login = new Login();
        // Attempting to login with provided credentials
        Customer customer = login.customerLogin("kaashif611", "chishti@611");
        // Checking if login was successful
        if (customer != null) {
            // Printing a welcome message with the customer's name if login was successful
            System.out.println("Welcome :" + customer.getName());
        } else {
            // Printing an error message if login was unsuccessful
            System.out.println("Invalid username or password");
        }
    }
}

