package main.com.bodyconquest.database;

import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

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

    /**
     * Attempts to connect to the DB
     */
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
            System.out.println(this.dbConn);

        } catch (SQLException e) {
            System.out.println("Could not establish connection to database");
            if (dbConn == null) attemptReconnection();
        }
    }

    /**
     * @param username the username to be inserted in the database
     * @param password the password paired with that username to be inserted
     * @return true if the insertion was successful; false otherwise (if username is already taken)
     */
    public boolean addUser(String username, String password) {

        boolean exists = checkUser(username, password);
        if (!exists) {
            PreparedStatement insertUser;
            try {
                String insertUserString = "INSERT INTO Users (username, password) VALUES " +
                        "(?, ?)";
                insertUser = dbConn.prepareStatement(insertUserString);
                insertUser.setString(1, username);
                insertUser.setString(2, password);

                insertUser.executeUpdate();
                return true;
            } catch (SQLException e) {
                System.out.println("SQL error in addUser method");
            }
        } else {

        }

        return false;
    }

    /**
     * @param username the username to be checked in the database
     * @param password the password given by the user for that username to be checked
     * @return true if the username password pair exists in the database; false otherwise
     */
    public boolean checkUser(String username, String password) {

        PreparedStatement getUser;
        try {
            String getUserString = "SELECT username, password " +
                    "FROM Users " +
                    "WHERE username = ?;";
            getUser = dbConn.prepareStatement(getUserString);
            getUser.setString(1, username);
            ResultSet rs = getUser.executeQuery();
            while (rs.next()) {
                String dbPassword = rs.getString(2);
                if (dbPassword.equals(password)) return true;
            }
        } catch (SQLException e) {
            System.out.println("SQL error in checkUser method");
        }

        return false;
    }

    /**
     * @return all username - points pairs from the leaderboard table in the database as a HashMap
     */
    public HashMap<String, Integer> getLeaderboard() {
        HashMap<String, Integer> resultMap = new HashMap<String, Integer>();

        PreparedStatement getLeaderboard;
        try {
            String getLeaderboardString = "SELECT username, points" +
                    "FROM Leaderboard;";
            getLeaderboard = dbConn.prepareStatement(getLeaderboardString);
            ResultSet rs = getLeaderboard.executeQuery();
            while (rs.next()) {
                String name = rs.getString(1);
                Integer points = rs.getInt(2);
                resultMap.put(name, points);
            }
        } catch (SQLException e) {
            System.out.println("Problems while getting leaderboard");
            attemptReconnection();
        }

        return resultMap;
    }

    /**
     * Tries to insert a new highscore for a user; In case the user already has a larger score in the DB, keep it
     *
     * @param username the user whose score is to be inserted
     * @param points   the score to be inserted for that user
     * @return
     */
    public boolean insertAchievement(String username, int points) {

        PreparedStatement insertPoints;
        try {
            String insertPointsString = "INSERT INTO Leaderboard (aid, username, points) " +
                    "VALUES (COUNT(aid) + 1, ?, ?) " +
                    "ON CONFLICT (username) DO UPDATE " +
                    "SET points = MAX(points, ?) " +
                    "SET aid = MIN(aid, COUNT(aid) + 1) ";
            insertPoints = dbConn.prepareStatement(insertPointsString);
            insertPoints.setString(1, username);
            insertPoints.setInt(2, points);
            insertPoints.setInt(3, points);

            insertPoints.executeUpdate();
        } catch (SQLException e) {
            System.out.println("SQL error in insertAchievement method");
        }

        return false;
    }

    /**
     * Creates the Users table in the DB
     *
     * @return True if the users table was created successfully; False if not
     */
    private boolean createUsers() {
        boolean created = true;
        try {
            Statement statement = dbConn.createStatement();
            String createUsersTable = "CREATE TABLE Users (\n" +
                    "	username			VARCHAR(50)		NOT NULL," +
                    "	password			VARCHAR(50)		NOT NULL," +
                    "	PRIMARY KEY (username)" +
                    ");";
            statement.execute(createUsersTable);
        } catch (SQLException e) {
            System.out.println("Users table was not created successfully");
            created = false;
            if (dbConn == null) attemptReconnection();
        }
        return created;
    }

    /**
     * Creates the Leaderborad table in the DB
     *
     * @return True if the table was created successfully; False if not
     */
    private boolean createBoard() {
        boolean created = true;
        try {
            Statement statement = dbConn.createStatement();
            String createLeaderboardTable = "CREATE TABLE Leaderboard (\n" +
                    "	aid					INTEGER			," +
                    "	username			VARCHAR(50)		NOT NULL," +
                    "	points			    INTEGER		    NOT NULL," +
                    "	PRIMARY KEY (aid)," +
                    "   FOREIGN KEY (username) REFERENCES Users(username)" +
                    "	    ON UPDATE CASCADE" +
                    ");";
            statement.execute(createLeaderboardTable);
        } catch (SQLException e) {
            System.out.println("Leaderboard table was not created successfully");
            created = false;
            if (dbConn == null) attemptReconnection();
        }
        return created;
    }

    private boolean createTables() {
        return createUsers() && createBoard();
    }

    /**
     * Completely wipes the DB
     */
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

    /**
     * Clears all data stored in the DB
     */
    private void emptyDB() {
        try {
            Statement statement = dbConn.createStatement();
            String emptyTables = "TRUNCATE TABLE Users, Leaderboard;";
            statement.execute(emptyTables);
        } catch (SQLException e) {
            System.out.println("Problems while emptying DB");
            attemptReconnection();
        }
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

}
