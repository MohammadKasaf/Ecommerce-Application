package org.example.Ecommerce;

import javafx.collections.ObservableList;

import java.sql.ResultSet;

import static java.time.chrono.JapaneseEra.values;


public class Order {

    // Method to place a single order for a customer and a product
    public static boolean placeOrder(Customer customer, Product product){

        // SQL query to get the maximum group_order_id and increment it by 1
        String groupOrderId="select max(group_order_id)+1 AS id from orders";
        DbConnection dbConnection=new DbConnection();

        try{
            ResultSet rs=dbConnection.getQueryTable(groupOrderId);
            if(rs.next()){
                String placeOrder = "INSERT INTO orders(group_order_id, customer_id, product_id) VALUES ("+rs.getInt("id")+","+customer.getId()+","+product.getId()+")";
                return dbConnection.updateDatabase(placeOrder) !=0;
            }
        }
        catch (Exception e){

            e.printStackTrace();
        }

        return false;
    }

    // Method to place multiple orders for a customer with a list of products
    public static int placeMultipleOrder(Customer customer, ObservableList<Product> productList){


        String groupOrderId="select max(group_order_id)+1 AS id from orders";
        DbConnection dbConnection=new DbConnection();

        try{

            ResultSet rs=dbConnection.getQueryTable(groupOrderId);
            int count=0;
            if(rs.next()){
                // Looping through the product list and inserting each product as a separate order
                for(Product product:productList) {
                    // Constructing the SQL query to insert the order into the database
                    String placeOrder = "INSERT INTO orders(group_order_id, customer_id, product_id) VALUES (" + rs.getInt("id") + "," + customer.getId() + "," + product.getId() + ")";
                    // Executing the database update query and incrementing the count for successful updates
                    count +=dbConnection.updateDatabase(placeOrder);
                }
                return count;
            }

        }
        catch (Exception e){
            // Printing the stack trace if an exception occurs
            e.printStackTrace();
        }
        // Returning 0 if no orders were successfully placed
        return 0;
    }
}
