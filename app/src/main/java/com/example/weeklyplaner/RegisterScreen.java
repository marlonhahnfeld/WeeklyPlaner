package com.example.weeklyplaner;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class RegisterScreen extends AppCompatActivity implements View.OnClickListener {

    private TextView dbStatusTextView;
    private ImageButton backToLoginScreenButton;
    private EditText editTextEmail;
    private EditText editTextPassword;
    private String email;
    private String password;
    private static final String DB_DRIVER = "org.h2.Driver";
    private static final String DB_CONNECTION = "jdbc:h2:mem:testdb";
    private static final String DB_USER = "sa";
    private static final String DB_PASSWORD = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_screen);

        backToLoginScreenButton = findViewById(R.id.backToLoginScreenButton);
        backToLoginScreenButton.setOnClickListener(this);

        editTextEmail = findViewById(R.id.editTextEmailAddress);

        editTextPassword = findViewById(R.id.editTextPassword);

        dbStatusTextView = findViewById(R.id.dbStatusTextView);


        // Datenbank Verbindung
        Connection connection = null;
        try {
            connection = getDBConnection();
            dbStatusTextView.setText("You are successfully connected to your Database");
        } catch (SQLException e) {
            dbStatusTextView.setText("Error: " + e.getMessage());
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                dbStatusTextView.setText("Error: " + e.getMessage());
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
        }
    }

    public static Connection getDBConnection() throws SQLException {
        Connection connection = null;
        try {
            Class.forName(DB_DRIVER);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            ;
        }
        connection = DriverManager.getConnection(DB_CONNECTION, DB_USER, DB_PASSWORD);
        return connection;
    }
}