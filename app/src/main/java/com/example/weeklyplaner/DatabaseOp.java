package com.example.weeklyplaner;

import static com.example.weeklyplaner.Utils.getSpecificTerminliste;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import items.DatabaseLoadListener;
import items.Termin;

public class DatabaseOp {
    private static final String DB_DRIVER = "org.h2.Driver";
    private static final String DB_CONNECTION =
            "jdbc:h2:file:/data/data/com.example.weeklyplaner/databases/loginDatenbank";
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

    public static Connection getConnection() {
        return connection;
    }

    public static boolean isConnected() {
        return connected;
    }

    /**
     * Erstellung für die Tabelle, wo die Nutzerdaten abgespeichert werden
     */
    public static void createTables() {
        try {
            if (connected) {
                Statement statement = connection.createStatement();
                String sqlQuery = "CREATE TABLE IF NOT EXISTS LOGIN (\n" +
                        "email VARCHAR(50) PRIMARY KEY, \n" +
                        "passwort VARCHAR(50) \n" +
                        ");";
                statement.execute(sqlQuery);
                sqlQuery = "CREATE TABLE IF NOT EXISTS TERMINE (\n" +
                        "id INT, \n" +
                        "email VARCHAR (100),\n" +
                        "name VARCHAR(100),\n" +
                        "beschreibung VARCHAR(100),\n" +
                        "prio VARCHAR(100),\n" +
                        "tag VARCHAR (100),\n" +
                        "FOREIGN KEY (email) REFERENCES LOGIN(email));";
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
            createTables();
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
                statement.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Methode um den erstellten Termin in die Datenbank abzuspeichern
     *
     * @param email        des Users
     * @param terminName   des Termins
     * @param beschreibung des Termins
     * @param prio         des Termins
     * @param tag          des Termins
     */
    public static void saveAppointment(int id, String email, String terminName, String beschreibung,
                                       String prio, String tag) {
        try {
            if (connected) {
                Statement statement = connection.createStatement();
                String sql = "INSERT INTO TERMINE VALUES (" + id + ", '" + email + "', '" + terminName + "', '" + beschreibung + "', '" + prio + "', '" + tag + "');";
                statement.execute(sql);
                statement.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void editAppointment(String email, String terminName) {

    }

    /**
     * Methode um einen existierenden Termin zu löschen aus der Datenbank
     *
     * @param email des Users
     */
    public static void deleteAppointment(int id, String email) {
        try {
            Statement statement = connection.createStatement();
            String sqlQuery =
                    "DELETE FROM TERMINE WHERE id = " + id + " AND email = '" + email + "';";
            statement.executeUpdate(sqlQuery);

            sqlQuery = "SELECT * FROM TERMINE WHERE id = " + id + " AND email = '" + email + "';";
            ResultSet resultSet = statement.executeQuery(sqlQuery);
            while (resultSet.next()) {
                System.out.println(resultSet.getString("id") + "\n" + resultSet.getString("name"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Methode um die Termine eines Users von der Datenbank zu laden
     *
     * @param email des eingeloggten Users, von dem die Termine geladen werden sollen
     */
    public static void loadAppointments(String email) {
        try {
            if (connected) {
                Statement statement = connection.createStatement();
                String sql = "SELECT * FROM TERMINE WHERE email = '" + email + "';";
                ResultSet resultSet = statement.executeQuery(sql);
                Termin termin;
                while (resultSet.next()) {
                    termin = new Termin(resultSet.getString("name"),
                            resultSet.getString("beschreibung"),
                            resultSet.getString("prio"),
                            resultSet.getString("tag"),
                            resultSet.getInt("id"));
                    getSpecificTerminliste(resultSet.getString("tag")).add(termin);
                    System.out.println(termin);
                    SpecificDay.refresh_needed = true;
                }
                statement.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static int getMaxID() {
        try {
            if (connected) {
                Statement statement = connection.createStatement();
                String sqlQueue = "SELECT MAX(id) FROM TERMINE";
                ResultSet resultSet = statement.executeQuery(sqlQueue);
                if (resultSet.next()) {
                    return resultSet.getInt("MAX(id)");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * Methode um Daten aus der Datenbank zu laden und hinterher den übergebenden Listener
     * zu signalisieren, dass Daten fertig geladen wurden
     *
     * @param listener der über den Abschluss des Ladevorgans informieren soll
     * @param email    um auf die Termine des Users zugreifen zu können
     */
    public static void loadDataFromDatabase(DatabaseLoadListener listener, String email) {
        loadAppointments(email);
        listener.onDatabaseLoadComplete();
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
}