package com.example.weeklyplaner;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

// Todo: Abspeichern des User-inputs in die Datenbank
// Todo: RegEx für die Überprüfung des User-inputs
// * Erste Pseudo Daten wurden abgespeichert -> Wird aber entfernt und überarbeitet
// Todo: Farbliche Visualisierung der Anforderungen -> Rot: Not Done; Green: Success
public class RegisterScreen extends AppCompatActivity implements View.OnClickListener {
    private TextView dbStatusTextView;
    private TextView dbTestView;
    private ImageButton backToLoginScreenButton;
    private Button registerButton;
    private EditText editTextEmail;
    private EditText editTextPassword;
    private String email;
    private String password;
    private static final String DB_DRIVER = "org.h2.Driver";
    private static final String DB_CONNECTION = "jdbc:h2:mem:testdb";
    private static final String DB_USER = "sa";
    private static final String DB_PASSWORD = "";
    private static Statement statement;
    private static Connection connection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_screen);

        backToLoginScreenButton = findViewById(R.id.backToLoginScreenButton);
        backToLoginScreenButton.setOnClickListener(this);

        registerButton = findViewById(R.id.registerButton);
        registerButton.setOnClickListener(this);

        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextPassword);

        dbStatusTextView = findViewById(R.id.dbStatusTextView);
        dbTestView = findViewById(R.id.textView2);

        email = editTextEmail.getText().toString();
        password = editTextPassword.getText().toString();

        // Creating the Database Connection + The Table
        try {
            connection = getDBConnection();
            dbStatusTextView.setText(R.string.successfullyConnected);
            createTable();
        } catch (SQLException e) {
            dbStatusTextView.setText(String.format("Error: %s", e.getMessage()));
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                dbStatusTextView.setText(String.format("Error: %s", e.getMessage()));
            }
        }
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        int id = v.getId();

        if (id == R.id.backToLoginScreenButton) {
            intent = new Intent(this, LoginScreen.class);
            startActivity(intent);
        } else if (id == R.id.registerButton) {
            register();
        }
    }

    public static Connection getDBConnection() throws SQLException {
        Connection connection;
        try {
            Class.forName(DB_DRIVER);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        connection = DriverManager.getConnection(DB_CONNECTION, DB_USER, DB_PASSWORD);
        return connection;
    }

    public void createTable() {
        try {
            connection = getDBConnection();
            statement = connection.createStatement();
            String sqlQuery = "CREATE TABLE IF NOT EXISTS " +
                    "LOGIN (email VARCHAR(50), passwort VARCHAR(50));";
            statement.execute(sqlQuery);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (statement != null) {
                    statement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void register() {
        try {
            connection = getDBConnection();
            statement = connection.createStatement();
            String sqlQuery = "INSERT INTO LOGIN VALUES ('wwm@hh.de', 'fredbob')";
            statement.execute(sqlQuery);
            sqlQuery = "SELECT * FROM LOGIN";
            ResultSet resultSet = statement.executeQuery(sqlQuery);
            while (resultSet.next()) {
                String email = resultSet.getString("email");
                String password = resultSet.getString("passwort");
                System.out.println(email + password);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (statement != null) {
                    statement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}