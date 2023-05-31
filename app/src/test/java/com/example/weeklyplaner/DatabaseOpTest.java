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
//
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
        closeDatabaseConnection();
    }
}
