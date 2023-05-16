package com.example.weeklyplaner;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

// TODO: Statt Memory abspeichern, auf Festplatte
public class DatabaseOp {
    private static final String DB_DRIVER = "org.h2.Driver";
    private static final String DB_CONNECTION = "jdbc:h2:file:/data/data/com.example.weeklyplaner/databases/loginDatenbank";
    private static final String DB_USER = "sa";
    private static final String DB_PASSWORD = "";
    private static Connection connection;
    private static boolean connected;

    /**
     * Methode um eine Verbindung zur Datenbank zur erstellen
     */
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

    /**
     * Erstellung für die Tabelle, wo die Nutzerdaten abgespeichert werden
     */
    public static void createTable() {
        try {
            if (connected) {
                Statement statement = connection.createStatement();
                String sqlQuery = "CREATE TABLE IF NOT EXISTS LOGIN (email VARCHAR(50) PRIMARY KEY, passwort VARCHAR(50));";
                statement.execute(sqlQuery);
                statement.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Methode zum Überprüfen, ob Tabelle existiert
     *
     * @return true, wenn Tabelle existiert
     */
    public static boolean doesTableExists() {
        try {
            if (connected) {
                DatabaseMetaData metaData = connection.getMetaData();
                ResultSet resultSet = metaData.getTables(null, null,
                        "LOGIN", null);
                boolean tableExists = resultSet.next();
                resultSet.close();
                return tableExists;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Methode die createTable aufruft, wenn Table nicht existiert
     */
    public static void createTableIfNotExists() {
        if (!doesTableExists()) {
            createTable();
        }
    }

    /**
     * Methode, die einen neuen User registriert
     * TODO: Hinweis, falls existierende Mail schon benutzt wurde
     * TODO: Popup, für erfolgreiche Registrierung
     *
     * @param email    mit den sich der User registrieren möchte.
     *                 ! Pro User nur eine E-Mail, da E-Mail Primary Key ist
     * @param passwort mit den sich der User einloggen möchte später
     */
    public static void registerNewUser(String email, String passwort) {
        try {
            if (connected) {
                Statement statement = connection.createStatement();
                String sqlQuery = "INSERT INTO LOGIN VALUES ( '" + email +
                        "', '" + passwort + "')";
                statement.execute(sqlQuery);
                sqlQuery = "SELECT * FROM LOGIN";
                ResultSet resultSet = statement.executeQuery(sqlQuery);
                while (resultSet.next()) {
                    String em = resultSet.getString("email");
                    String password = resultSet.getString("passwort");
                    System.out.println(em + " " + password);
                }
                resultSet.close();
                statement.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Methode um die Datenbank Verbindung zu trennen
     */
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

    public static Connection getConnection() {
        return connection;
    }
}

