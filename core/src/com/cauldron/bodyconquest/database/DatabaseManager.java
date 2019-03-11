package com.cauldron.bodyconquest.database;

import java.io.*;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;
import java.util.Random;

public class DatabaseManager {

    private String url = "jdbc:postgresql://achievementsdb.c1dv9wmfmc0j.us-east-2.rds.amazonaws.com:5432/cauldronDB";
    private String user = "cauldron_admin";
    private String password = "Cauldr()n";
    private String driver = "org.postgresql.Driver";
    private Connection dbConn;
    private BufferedReader lineReader;

    public DatabaseManager() {
        connect();
    }

    public void connect() {

        /*
        Properties props = new Properties();
        this.url = "";
        this.user = "";
        this.password = "";
        String driver = "";

        try (FileInputStream propsReader = new FileInputStream("Database.Properties")) { // load database properties
            props.load(propsReader); // connect to the database
            this.url = props.getProperty("database.url");
            this.user = props.getProperty("username");
            this.password = props.getProperty("password");
            driver = props.getProperty("jdbc.drivers");
        } catch (FileNotFoundException e1) {
            System.out.println("Database.Properties file not provided");
        } catch (IOException e2) {
            System.out.println("Error while reading Properties file");
        }
        */

        try {
            Class.forName(driver);
            System.out.println("PostgrSQL driver registered.");
        } catch (ClassNotFoundException ex) {
            System.out.println("Driver not found");
        }

        try {
            this.dbConn = DriverManager.getConnection(url, user, password);
            System.out.println("Database connection established.");
            //System.out.println(this.dbConn);

            try {
                Thread.sleep(100000);
            } catch (InterruptedException e) {
                System.out.println("Thread couldn't sleep.");
            }
        } catch (SQLException e) {
            System.out.println("Could not establish connection to database");
            //if (dbConn == null) attemptReconnection();
        }
    }

    public boolean addUser(String username, String password) {
        boolean successful = false;

        //query
        successful = true;

        return successful;
    }

    public boolean checkUser(String username, String password) {
        boolean successful = false;

        //query
        successful = true;

        return successful;
    }

    public LinkedList<String> getUserAchievements(String username) {
        LinkedList<String> resultList = new LinkedList<String>();

        //query

        return resultList;
    }

    private void attemptReconnection() {
        if (!url.equals(""))
            try {
                System.out.println("You have been disconnected from the DB; Attempting to reconnect...");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    System.out.println("Thread couldn't sleep.");
                }
                dbConn = DriverManager.getConnection(url, user, password);
                System.out.println("Database connection established.");
            } catch (SQLException e) {
                System.out.println("Could not establish connection to database");
                if (dbConn == null) attemptReconnection();
            }
    }

    private void resetDB() {
        try {
            Statement statement = dbConn.createStatement();
            String recreateSchema = "DROP SCHEMA public CASCADE;" +
                    "CREATE SCHEMA public;" +
                    "GRANT ALL ON SCHEMA public TO postgres;" +
                    "GRANT ALL ON SCHEMA public TO public;" +
                    "COMMENT ON SCHEMA public IS 'standard public schema';";
            statement.execute(recreateSchema);
        } catch (SQLException e) {
            System.out.println("Problems while clearing DB");
            attemptReconnection();
        }
    }

}
