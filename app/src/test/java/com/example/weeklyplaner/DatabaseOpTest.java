package com.example.weeklyplaner;

import static org.junit.Assert.*;
import static com.example.weeklyplaner.DatabaseOp.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

// TODO: Fehlende Methoden hinzufügen wie bspw. registerNewUser, saveAppointment usw.
public class DatabaseOpTest {

    @Before
    public void setUp() {
        createDatabaseConnection();
    }

    @Test
    public void testCreateDatabaseConnection() {
        assertTrue(isConnected());
        closeDatabaseConnection();
        assertFalse(isConnected());
    }

    @Test
    public void testCloseDatabaseConnection() {
        assertTrue(isConnected());
        closeDatabaseConnection();
        assertFalse(isConnected());
    }

    @Test
    public void testDoesTableExists() throws SQLException {
        assertTrue(doesTableExists());
        if (isConnected()) {
            DatabaseMetaData metaData = getConnection().getMetaData();
            ResultSet resultSet = metaData.getTables(null, null,
                    "NON_EXISTENT_TABLE", null);
            boolean tableExists = resultSet.next();
            resultSet.close();
            assertFalse(tableExists);
        }
    }

    @Test
    public void testRegisterNewUser() throws SQLException {
        String testAccount = "testAccount@hh.de";
        Statement statement = getConnection().createStatement();

        // Hinzufügen des neu erstellten Kontos
        registerNewUser(testAccount, "Marlon123!");

        // Abfrage, ob das neu erstellte Konto in der Datenbank drinnen ist
        String sqlQuery = "SELECT (email) FROM LOGIN WHERE email = '" + testAccount + "';";
        ResultSet resultSet = statement.executeQuery(sqlQuery);
        assertTrue(resultSet.next());

        // Löschen des Kontos
        sqlQuery = "DELETE FROM LOGIN WHERE email = '" + testAccount + "';";
        statement.execute(sqlQuery);
        sqlQuery = "SELECT (email) FROM LOGIN WHERE email = '" + testAccount + "';";
        resultSet = statement.executeQuery(sqlQuery);
        assertFalse(resultSet.next());

        statement.close();
        resultSet.close();
    }

    @After
    public void closeUp() {
        closeDatabaseConnection();
    }
}
