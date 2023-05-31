package com.example.weeklyplaner;

import static com.example.weeklyplaner.DatabaseOp.closeDatabaseConnection;
import static com.example.weeklyplaner.DatabaseOp.createDatabaseConnection;
import static com.example.weeklyplaner.DatabaseOp.createTables;
import static com.example.weeklyplaner.DatabaseOp.doesTableExists;
import static com.example.weeklyplaner.DatabaseOp.getConnection;
import static com.example.weeklyplaner.DatabaseOp.isConnected;
import static com.example.weeklyplaner.DatabaseOp.registerNewUser;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseOpTest {
    private static final String testAccount = "testAccount@hh.de";
    private static final String testPassword = "Marlon123!";

    @Test
    public void testCreateDatabaseConnection() {
        createDatabaseConnection();
        assertTrue(isConnected());
        closeDatabaseConnection();
        assertFalse(isConnected());
    }

    @Test
    public void testCloseDatabaseConnection() {
        createDatabaseConnection();
        assertTrue(isConnected());
        closeDatabaseConnection();
        assertFalse(isConnected());
    }

    @Test
    public void testDoesTableExists() throws SQLException {
        createDatabaseConnection();
        createTables();
        assertTrue(doesTableExists());
        if (isConnected()) {
            DatabaseMetaData metaData = getConnection().getMetaData();
            ResultSet resultSet = metaData.getTables(null, null,
                    "NON_EXISTENT_TABLE", null);
            boolean tableExists = resultSet.next();
            resultSet.close();
            assertFalse(tableExists);
        }
        closeDatabaseConnection();
    }

    @Test
    public void testRegisterNewUser() throws SQLException {
        createDatabaseConnection();
        Statement statement = getConnection().createStatement();
        ResultSet resultSet;

        String sqlQuery = "DELETE FROM TERMINE WHERE email = '" + testAccount + "';";
        statement.execute(sqlQuery);
        sqlQuery = "DELETE FROM LOGIN WHERE email = '" + testAccount + "';";
        statement.execute(sqlQuery);

        sqlQuery = "SELECT * FROM LOGIN WHERE email = '" + testAccount + "';";
        resultSet = statement.executeQuery(sqlQuery);
        assertFalse(resultSet.next());

        registerNewUser(testAccount, testPassword);

        sqlQuery = "SELECT * FROM LOGIN WHERE email = '" + testAccount + "';";
        resultSet = statement.executeQuery(sqlQuery);
        assertTrue(resultSet.next());

        sqlQuery = "DELETE FROM TERMINE WHERE email = '" + testAccount + "';";
        statement.execute(sqlQuery);
        sqlQuery = "DELETE FROM LOGIN WHERE email = '" + testAccount + "';";
        statement.execute(sqlQuery);

        statement.close();
        resultSet.close();

    }
    @After
    public void closeUp() {

        closeDatabaseConnection();
    }
}

//    @Test
//    public void testSaveAppointment() throws SQLException {
//        Statement statement = getConnection().createStatement();
//
//        String sqlQuery = "DELETE FROM TERMINE WHERE email = '" + testAccount + "';";
//        statement.execute(sqlQuery);
//        sqlQuery = "DELETE FROM LOGIN WHERE email = '" + testAccount + "';";
//        statement.execute(sqlQuery);
//
//        registerNewUser(testAccount, testPassword);
////        saveAppointment(testAccount, "TestAppointment", "",
////                "Priorität 1", "Montag");
//
//        sqlQuery = "SELECT name FROM TERMINE WHERE email = '" + testAccount + "';";
//        ResultSet resultSet = statement.executeQuery(sqlQuery);
//
//        if (resultSet.next()) {
//            String actual = resultSet.getString("name");
//            assertEquals("TestAppointment", actual);
//        }
//
//        sqlQuery = "DELETE FROM TERMINE WHERE email = '" + testAccount + "';";
//        statement.execute(sqlQuery);
//        sqlQuery = "DELETE FROM LOGIN WHERE email = '" + testAccount + "';";
//        statement.execute(sqlQuery);
//
//        resultSet.close();
//        statement.close();
//    }

//    @Test
//    public void testLoadAppointment() throws SQLException {
//        Statement statement = getConnection().createStatement();
//        String sqlQuery = "DELETE FROM TERMINE WHERE email = '" + testAccount + "';";
//        statement.execute(sqlQuery);
//        sqlQuery = "DELETE FROM LOGIN WHERE email = '" + testAccount + "';";
//        statement.execute(sqlQuery);
//
//        assertTrue(getSpecificTerminliste("Montag").isEmpty());
//
//        registerNewUser(testAccount, testPassword);
////        saveAppointment(testAccount, "TestAppointment", "",
////                "Priorität 1", "Montag");
//        loadAppointments(testAccount);
//
//        assertFalse(getSpecificTerminliste("Montag").isEmpty());
//
//        sqlQuery = "DELETE FROM TERMINE WHERE email = '" + testAccount + "';";
//        statement.execute(sqlQuery);
//        sqlQuery = "DELETE FROM LOGIN WHERE email = '" + testAccount + "';";
//        statement.execute(sqlQuery);
//
//        statement.close();
//    }
