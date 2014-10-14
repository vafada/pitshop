package com.pitshop.db;

import java.sql.*;

public class InsertData {
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
        String url = "jdbc:mckoi:local://db.conf";

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
            System.out.println("Unable to create the database.\n The reason: " + e.getMessage());
            return;
        }

        // --- Set up the database ---

        try {
            // Create a Statement object to execute the queries on,
            Statement statement = connection.createStatement();


            System.out.println("-- Inserting Data --");
            System.out.println("-- Adding to Customer Table --");
            statement.executeUpdate("INSERT INTO customer (name, address, phone_number) VALUES ('bryan yu', 'malabon', '4476561')");
            statement.executeUpdate("INSERT INTO customer (name, address, phone_number) VALUES ('david ang', 'quezon city', '7166547')");
            statement.executeUpdate("INSERT INTO customer (name, address, phone_number) VALUES ('pacita abenojar', 'manila', '3099585')");

            System.out.println("-- Adding to Category Table --");
            statement.executeQuery("INSERT INTO category (name) VALUES ('wheel')");
            statement.executeQuery("INSERT INTO category (name) VALUES ('motor')");
            statement.executeQuery("INSERT INTO category (name) VALUES ('chasis')");

            System.out.println("-- Adding to Manufacturer Table --");
            statement.executeQuery("INSERT INTO manufacturer (name) VALUES ('honda')");
            statement.executeQuery("INSERT INTO manufacturer (name) VALUES ('toyota')");
            statement.executeQuery("INSERT INTO manufacturer (name) VALUES ('mitsubishi')");

            System.out.println("-- Adding to Source Table --");
            statement.executeQuery("INSERT INTO source (name) VALUES ('direct')");
            statement.executeQuery("INSERT INTO source (name) VALUES ('indirect')");
            statement.executeQuery("INSERT INTO source (name) VALUES ('friends')");

            System.out.println("-- Adding to discount Table --");
            statement.executeQuery("INSERT INTO discount (name, value) VALUES ('discount 1', 10)");
            statement.executeQuery("INSERT INTO discount (name, value) VALUES ('discount 2', 20)");
            statement.executeQuery("INSERT INTO discount (name, value) VALUES ('discount 3', 30)");

            System.out.println("-- Adding to encoder Table --");
            statement.executeQuery("INSERT INTO encoder (name) VALUES ('maroon 5')");
            statement.executeQuery("INSERT INTO encoder (name) VALUES ('thrall')");
            statement.executeQuery("INSERT INTO encoder (name) VALUES ('marlon')");

            System.out.println("-- Adding to sales_type Table --");
            statement.executeQuery("INSERT INTO sales_type (name) VALUES ('credit')");
            statement.executeQuery("INSERT INTO sales_type (name) VALUES ('debt')");
            statement.executeQuery("INSERT INTO sales_type (name) VALUES ('cash')");

            System.out.println("-- Adding to parts Table --");
            statement.executeQuery("INSERT INTO parts (number, description, quantity, srp, max_level, reorder_level, manufacturer_id, " +
                    "category_id, source_id) VALUES ('020626851824', 'starcraft', 4, 10.0, 'max level', 'reorder level', 2, 1, 3)");
            statement.executeQuery("INSERT INTO parts (number, description, quantity, srp, max_level, reorder_level, manufacturer_id, " +
                    "category_id, source_id) VALUES ('9789716560411', 'turbo c', 3, 13.0, 'max level', 'reorder level', 1, 2, 2)");
            statement.executeQuery("INSERT INTO parts (number, description, quantity, srp, max_level, reorder_level, manufacturer_id, " +
                    "category_id, source_id) VALUES ('020626851763', 'thrall model', 6, 16.0, 'max level', 'reorder level', 3, 3, 1)");
            statement.executeQuery("INSERT INTO parts (number, description, quantity, srp, max_level, reorder_level, manufacturer_id, " +
                    "category_id, source_id) VALUES ('1', 'turbo c', 3, 13.0, 'max level', 'reorder level', 1, 2, 2)");
            statement.executeQuery("INSERT INTO parts (number, description, quantity, srp, max_level, reorder_level, manufacturer_id, " +
                    "category_id, source_id) VALUES ('2', 'thrall model', 6, 16.0, 'max level', 'reorder level', 3, 3, 1)");
            statement.executeQuery("INSERT INTO parts (number, description, quantity, srp, max_level, reorder_level, manufacturer_id, " +
                    "category_id, source_id) VALUES ('3', 'turbo c', 3, 13.0, 'max level', 'reorder level', 1, 2, 2)");
            statement.executeQuery("INSERT INTO parts (number, description, quantity, srp, max_level, reorder_level, manufacturer_id, " +
                    "category_id, source_id) VALUES ('4', 'thrall model', 6, 16.0, 'max level', 'reorder level', 3, 3, 1)");

            // Close the statement and the connection.
            statement.close();
            connection.close();

        } catch (SQLException e) {
            System.out.println("An error occured The SQLException message is: " + e.getMessage());
        }

        // Close the the connection.
        try {
            connection.close();
        } catch (SQLException e2) {
            e2.printStackTrace(System.err);
        }
    }
}
