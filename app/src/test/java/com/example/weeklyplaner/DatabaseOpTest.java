package com.example.weeklyplaner;

import static org.junit.Assert.*;
import static com.example.weeklyplaner.DatabaseOp.*;
import static com.example.weeklyplaner.Utils.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseOpTest {
    private static final String testAccount = "testAccount@hh.de";
    private static final String testPassword = "Marlon123!";

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
