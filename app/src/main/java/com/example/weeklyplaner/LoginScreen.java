package com.example.weeklyplaner;

import static com.example.weeklyplaner.DatabaseOp.closeDatabaseConnection;
import static com.example.weeklyplaner.DatabaseOp.createDatabaseConnection;
import static com.example.weeklyplaner.DatabaseOp.createTableIfNotExists;
import static com.example.weeklyplaner.DatabaseOp.isConnected;
import static com.example.weeklyplaner.DatabaseOp.loadDataFromDatabase;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class LoginScreen extends AppCompatActivity implements View.OnClickListener {

    private TextView registerButton;
    private Button loginButton;
    private EditText editTextEmail;
    private EditText editTextPassword;
    public static String email;
    private String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);

        registerButton = findViewById(R.id.registerTextButton);
        registerButton.setOnClickListener(this);

        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextPassword);

        loginButton = findViewById(R.id.loginButton);
        loginButton.setOnClickListener(this);

        createDatabaseConnection();
        if (isConnected()) {
            System.out.println("Verbunden");
        }
        createTableIfNotExists();
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        int id = v.getId();

        if (id == R.id.registerTextButton) {
            intent = new Intent(this, RegisterScreen.class);
            startActivity(intent);
        } else if (id == R.id.loginButton) {
            email = editTextEmail.getText().toString();
            password = editTextPassword.getText().toString();
            if (checkLogInData(email, password)) {
                loadDataFromDatabase(() -> {
                    Intent intent1 = new Intent(LoginScreen.this, MainActivity.class);
                    startActivity(intent1);
                }, email);
                closeDatabaseConnection();
                if (!isConnected()) {
                    System.out.println("Verbindung getrennt");
                }

            }
        }
    }

    /**
     * Methode um Login Daten mithilfe der Datenbank zu überprüfen
     *
     * @param email    des Users
     * @param password des Users
     * @return true, wenn eine Übereinstimmung in der Datenbank gefunden wird
     */
    public boolean checkLogInData(String email, String password) {
        try {
            Statement statement = DatabaseOp.getConnection().createStatement();
            String query = "SELECT * FROM LOGIN WHERE email = '" + email +
                    "' AND passwort = '" + password + "'";
            ResultSet resultSet = statement.executeQuery(query);

            if (resultSet.next()) {
                resultSet.close();
                statement.close();
                return true;
            } else {
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Falsche Email oder Passwort");
                builder.setMessage("Bitte geben Sie Ihre gültige Email oder Passwort ein.");
                builder.setPositiveButton("OK", (dialog, which) -> dialog.dismiss()).show();
            }
            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}