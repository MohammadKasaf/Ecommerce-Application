package org.example.Ecommerce;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;

public class Product {

    // Properties for the Product class
    private SimpleIntegerProperty id;
    private SimpleStringProperty name;
    private SimpleDoubleProperty price;

    // Constructor to initialize a Product object with provided information
    public Product(int id, String name, Double price) {
        this.id = new SimpleIntegerProperty(id);
        this.name = new SimpleStringProperty(name);
        this.price = new SimpleDoubleProperty(price);
    }

    // Method to retrieve all products from the database
    public static ObservableList getAllProducts(){

        // SQL query to select all products from the Product table
        String selectAllProducts=" SELECT id,Product_Name,price FROM Product";
        // Fetching product data using the provided query
        return fetchProductData(selectAllProducts);
    }

    // Method to fetch product data from the database based on a given query
    public static ObservableList fetchProductData(String query){

        // Creating an observable list to store fetched product data
        ObservableList data= FXCollections.observableArrayList();
        // Creating a database connection
        DbConnection dbConnection=new DbConnection();

        try{
            // Executing the SQL query to fetch product data
            ResultSet rs=dbConnection.getQueryTable(query);
            // Looping through the result set and adding products to the observable list
            while(rs.next()){
                Product product=new Product(rs.getInt("id"),rs.getString("Product_Name"),rs.getDouble("price"));
                data.add(product);
            }
        }
        catch(Exception e){
            // Printing the stack trace if an exception occurs
            e.printStackTrace();
        }

        // Returning the fetched product data
        return data;
    }

    // Getter method to retrieve the product ID
    public int getId() {
        return id.get();
    }

    // Getter method to retrieve the product name
    public String getName() {
        return name.get();
    }

    // Getter method to retrieve the product price
    public double getPrice() {
        return price.get();
    }
}

