package com.example.weeklyplaner;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseOp {
    private static final String DB_DRIVER = "org.h2.Driver";
    private static final String DB_CONNECTION = "jdbc:h2:mem:testdb";
    private static final String DB_USER = "sa";
    private static final String DB_PASSWORD = "";
    private static Connection connection;
    private static boolean connected;

    public static void createDatabaseConnection() {
        try {
            Class.forName(DB_DRIVER);
            connection = DriverManager.getConnection(DB_CONNECTION, DB_USER, DB_PASSWORD);
            connected = true;
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    public static boolean isConnected() {
        return connected;
    }

    public static void createTable() {
        try {
            if (connected) {
                Statement statement = connection.createStatement();
                String sqlQuery = "CREATE TABLE IF NOT EXISTS " +
                        "LOGIN (email VARCHAR(50), passwort VARCHAR(50));";
                statement.execute(sqlQuery);
                statement.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static boolean isTableExists() {
        try {
            if (connected) {
                DatabaseMetaData metaData = connection.getMetaData();
                ResultSet resultSet = metaData.getTables(null, null, "LOGIN", null);
                boolean tableExists = resultSet.next();
                resultSet.close();
                return tableExists;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static void createTableIfNotExists() {
        if (!isTableExists()) {
            createTable();
        }
    }

    public static void registerNewUser() {
        try {
            if (connected) {
                Statement statement = connection.createStatement();
                String sqlQuery = "INSERT INTO LOGIN VALUES ('wwm@hh.de', 'fredbob')";
                statement.execute(sqlQuery);
                sqlQuery = "SELECT * FROM LOGIN";
                ResultSet resultSet = statement.executeQuery(sqlQuery);
                while (resultSet.next()) {
                    String email = resultSet.getString("email");
                    String password = resultSet.getString("passwort");
                    System.out.println(email + password);
                }
                resultSet.close();
                statement.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void closeDatabaseConnection() {
        try {
            if (connected && connection != null) {
                connection.close();
                connected = false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}

