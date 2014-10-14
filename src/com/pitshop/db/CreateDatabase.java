package com.pitshop.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class CreateDatabase {
    public static void main(String[] args) {
        // Register the Mckoi JDBC Driver
        try {
            Class.forName("com.mckoi.JDBCDriver").newInstance();
        } catch (Exception e) {
            System.out.println("Unable to register the JDBC Driver.\n Make sure the classpath is correct.\n");
            return;
        }

        // This URL specifies we are creating a local database.  The
        // configuration file for the database is found at './ExampleDB.conf'
        // The 'create=true' argument means we want to create the database.  If
        // the database already exists, it can not be created.
        String url = "jdbc:mckoi:local://db.conf?create=true";

        // The username/password for the database.  This will be the username/
        // password for the user that has full control over the database.
        // ( Don't use this demo username/password in your application! )
        String username = "pitshop";
        String password = "dualforce";

        // Make a connection with the database.  This will create the database
        // and log into the newly created database.
        Connection connection;
        try {
            connection = DriverManager.getConnection(url, username, password);
        } catch (SQLException e) {
            System.out.println("Unable to create the database.\n" +
                    "The reason: " + e.getMessage());
            return;
        }

        // --- Set up the database ---

        try {
            // Create a Statement object to execute the queries on,
            Statement statement = connection.createStatement();

            System.out.println("-- Creating Tables --");

            statement.executeQuery(
                    "    CREATE TABLE customer ( " +
                    "       id NUMERIC DEFAULT UNIQUEKEY('customer'), " +
                    "       name VARCHAR(50)," +
                    "       address VARCHAR(100)," +
                    "       phone_number VARCHAR(50)," +
                    "       member BOOLEAN DEFAULT false)");

            statement.executeQuery(
                    "    CREATE TABLE category ( " +
                    "       id NUMERIC DEFAULT UNIQUEKEY('category'), " +
                    "       name VARCHAR(50) ) ");

            statement.executeQuery(
                    "    CREATE TABLE discount ( " +
                    "       id NUMERIC DEFAULT UNIQUEKEY('discount'), " +
                    "       name VARCHAR(50), " +
				    " 	    value NUMERIC NOT NULL ) ");

            statement.executeQuery(
                    "    CREATE TABLE encoder ( " +
                    "       id NUMERIC DEFAULT UNIQUEKEY('encoder'), " +
                    "       name VARCHAR(50) ) ");

            statement.executeQuery(
                    "    CREATE TABLE manufacturer ( " +
                    "       id NUMERIC DEFAULT UNIQUEKEY('manufacturer'), " +
                    "       name VARCHAR(50) ) ");

            statement.executeQuery(
                    "    CREATE TABLE sales_type ( " +
                    "       id NUMERIC DEFAULT UNIQUEKEY('sales_type'), " +
                    "       name VARCHAR(50) ) ");

            statement.executeQuery(
                    "    CREATE TABLE source ( " +
                    "       id NUMERIC DEFAULT UNIQUEKEY('source'), " +
                    "       name VARCHAR(50) ) ");

            statement.executeQuery(
                    "    CREATE TABLE parts ( " +
                    "       number VARCHAR(50) NOT NULL UNIQUE PRIMARY KEY," +
                    "       description VARCHAR(100)," +
                    "       quantity INT NOT NULL," +
                    "       srp DOUBLE NOT NULL, " +
                    "       max_level VARCHAR(20)," +
                    "       reorder_level VARCHAR(20), " +
                    "       manufacturer_id NUMERIC NOT NULL, " +
                    "       category_id NUMERIC NOT NULL," +
                    "       source_id NUMERIC NOT NULL, " +
                    "       foreign key (manufacturer_id) references manufacturer(id), " +
                    "       foreign key (category_id) references category(id), " +
                    "       foreign key (source_id) references source(id))");

            statement.executeQuery(
                    "    CREATE TABLE sales ( " +
                    "       id NUMERIC DEFAULT UNIQUEKEY('sales'), " +
                    "       customer_id NUMERIC," +
                    "       customer VARCHAR(100)," +
                    "       address VARCHAR(100)," +
                    "       create_date DATE NOT NULL," +
                    "       phone VARCHAR(100)," +
                    "       remarks VARCHAR(100)," +
                    "       sales_type VARCHAR(100)," +
                    "       member BOOLEAN," +
                    "       encoded_by VARCHAR(100)," +
                    "       total DOUBLE)");

            statement.executeQuery(
                    "    CREATE TABLE sales_part ( " +
                    "       sales_id NUMERIC DEFAULT UNIQUEKEY('sales'), " +
                    "       part_number VARCHAR(50)," +
                    "       description VARCHAR(100)," +
                    "       quantity INT," +
                    "       unit_price NUMERIC," +
                    "       discounted_price NUMERIC," +
                    "       net_price NUMERIC," +
                    "       subtotal NUMERIC," +
                    "       foreign key (sales_id) references sales(id))");

            // Close the statement and the connection.
            statement.close();
            connection.close();

        } catch (SQLException e) {
            System.out.println("An error occured\n" +
                    "The SQLException message is: " + e.getMessage());

        }

        // Close the the connection.
        try {
            connection.close();
        } catch (SQLException e2) {
            e2.printStackTrace(System.err);
        }
    }
}
