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
import java.util.Properties;
import java.util.Random;

public class DatabaseManager {

    private String url;
    private String user;
    private String password;
    private Connection dbConn;
    private BufferedReader lineReader;

    public DatabaseManager() {
        connect();
    }

    public void connect() {
        Properties props = new Properties();
        this.url = "";
        this.user = "";
        this.password = "";
        String driver = "";

        try (FileInputStream propsReader = new FileInputStream("Database.Properties");) { // load database properties
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

        try {
            Class.forName(driver);
            System.out.println("PostgrSQL driver registered.");
        } catch (ClassNotFoundException ex) {
            System.out.println("Driver not found");
        }

        try {
            this.dbConn = DriverManager.getConnection(url, user, password);
            System.out.println("Database connection established.");

            try {
                Thread.sleep(100000);
            } catch (InterruptedException e) {
                System.out.println("Thread couldn't sleep.");
            }
        } catch (SQLException e) {
            System.out.println("Could not establish connection to database");
            if (dbConn == null) attemptReconnection();
        }
    }

    public void manage() {
        //flush DB
        resetDB();

        //create tables and populate them
        setupDB();

        //read queries and resolve them
        //resolveQueries();

        System.out.println("Program finished executing");
    }

    private void attemptReconnection() {
        if (!url.equals(""))
            try {
                System.out.println("You have been disconnected from the DB; Attempting to reconnect...");
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

    private void setupDB() {
        if (createTables()) {
            System.out.println("Tables created succesfully");
        } else {
            System.out.println("Problems while creating tables");
        }
    }

    private boolean createTables() {
        return createUsers() && createAchievements();
    }

    //to finish query
    private boolean createUsers() {
        boolean created = true;
        try {
            Statement statement = dbConn.createStatement();
            String createUsersTable = "CREATE TABLE Users (\n" +
                    "	uid					INTEGER			," +
                    "	username			VARCHAR(50)		NOT NULL," +
                    "	password			NUMERIC(10, 2)	NOT NULL CHECK(venuecost > 0)," +
                    "	PRIMARY KEY (uid)" +
                    ");";
            statement.execute(createUsersTable);
        } catch (SQLException e) {
            System.out.println("Users table was not created successfully");
            created = false;
            if (dbConn == null) attemptReconnection();
        }
        return created;
    }

    //to finish query
    private boolean createAchievements() {
        boolean created = true;
        try {
            Statement statement = dbConn.createStatement();
            String createAchievementsTable = "CREATE TABLE Achievements (\n" +
                    "	vid					INTEGER			," +
                    "	name				VARCHAR(50)		NOT NULL," +
                    "	venuecost			NUMERIC(10, 2)	NOT NULL CHECK(venuecost > 0)," +
                    "	PRIMARY KEY (vid)" +
                    ");";
            statement.execute(createAchievementsTable);
        } catch (SQLException e) {
            System.out.println("Achievements table was not created successfully");
            created = false;
            if (dbConn == null) attemptReconnection();
        }
        return created;
    }

}
