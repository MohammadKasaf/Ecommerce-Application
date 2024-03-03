package org.example.Ecommerce;

public class Customer {

    private int id;
    private String name, Email, Mobile;

    // Constructor to initialize a Customer object with provided information
    public Customer(int id,String name, String email, String mobile) {
        // Setting the provided values to respective fields
        this.id = id;
        this.name = name;
        Email = email;
        Mobile = mobile;
    }

    // Getter method to retrieve the customer ID
    public int getId() {
        return id;
    }

    // Getter method to retrieve the customer name
    public String getName() {
        return name;
    }

    // Getter method to retrieve the customer email
    public String getEmail() {
        return Email;
    }

    // Getter method to retrieve the customer mobile number
    public String getMobile() {
        return Mobile;
    }
}

