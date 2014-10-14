package com.pitshop.db;

import com.pitshop.PitshopDbConnectionException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class McKoiConnection {


    private static McKoiConnection ourInstance;
    private Connection conn;

    public synchronized static McKoiConnection getInstance() {
        if (ourInstance == null) {
            ourInstance = new McKoiConnection();
        }
        return ourInstance;
    }

    private McKoiConnection() {

    }

    public void initialize() throws PitshopDbConnectionException {
        if (conn == null) {
            try {
                Class.forName("com.mckoi.JDBCDriver");
                conn = DriverManager.getConnection("jdbc:mckoi:local://db.conf", "pitshop", "dualforce");
            } catch (Exception e) {
                throw new PitshopDbConnectionException(e.getMessage());
            }
        }
    }

    public Connection getConn() throws PitshopDbConnectionException {
        return conn;
    }

    public void close() {

        try {
            Statement stmt = conn.createStatement();
            stmt.executeQuery("SHUTDOWN");
            conn.close();
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        }
    }

    //private static final String CONNECTION_ERROR = "Error getting connection.";


}
