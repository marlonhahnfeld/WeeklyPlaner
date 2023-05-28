package com.example.weeklyplaner;

import static org.junit.Assert.*;
import static com.example.weeklyplaner.DatabaseOp.*;

import org.junit.Test;

import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DatabaseOpTest {

    @Test
    public void testCreateDatabaseConnection() {
        assertFalse(isConnected());
        createDatabaseConnection();
        assertTrue(isConnected());
        closeDatabaseConnection();
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
}
