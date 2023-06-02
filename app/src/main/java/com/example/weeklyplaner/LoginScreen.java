package com.example.weeklyplaner;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import static com.example.weeklyplaner.DatabaseOp.loadAppointments;
import static com.example.weeklyplaner.DatabaseOp.loadDataFromDatabase;

public class LoginScreen extends AppCompatActivity implements View.OnClickListener {

    private TextView registerButton;
    private Button loginButton;
    private EditText editTextEmail;
    private EditText editTextPassword;
    public static String email;
    private String password;
    private DatabaseOp databaseOp;

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

        databaseOp = new DatabaseOp();

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

            databaseOp.checkLogInData(email, password, new DatabaseOp.LoginCallback() {
                @Override
                public void onLoginSuccess() {
                    loadDataFromDatabase(() -> {
                        Intent intent1 =
                                new Intent(LoginScreen.this, MainActivity.class);
                        startActivity(intent1);
                    }, email);
                }

                @Override
                public void onLoginFailure(String message) {
                    // Zeige eine Fehlermeldung an, dass die E-Mail oder das Passwort falsch ist
                    Toast.makeText(LoginScreen.this, message, Toast.LENGTH_SHORT).show();
                }
            });
        }
    }


}
