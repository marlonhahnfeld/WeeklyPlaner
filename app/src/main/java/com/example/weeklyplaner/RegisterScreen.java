package com.example.weeklyplaner;

import static com.example.weeklyplaner.DatabaseOp.*;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class RegisterScreen extends AppCompatActivity implements View.OnClickListener {
    private TextView dbStatusTextView;
    private TextView dbTestView;
    private ImageButton backToLoginScreenButton;
    private Button registerButton;
    private EditText editTextEmail;
    private EditText editTextPassword;
    private String email;
    private String password;

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

        createDatabaseConnection();
        if (isConnected()) {
            dbStatusTextView.setText(R.string.successfullyConnected);
        }
        createTableIfNotExists();
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        int id = v.getId();

        if (id == R.id.backToLoginScreenButton) {
            intent = new Intent(this, LoginScreen.class);
            startActivity(intent);
        } else if (id == R.id.registerButton) {
            email = editTextEmail.getText().toString();
            password = editTextPassword.getText().toString();
            if (checkInput(email, password)) {
                registerNewUser(email, password);
            }
        }
    }

    /**
     * * Methode, um Input des Users überprüfen
     * Todo: Farbliche Erkennung, während Laufzeit. Aktuell nur nach Button Click!
     * Todo: RegEx Ausdruck für die Email Adresse des Users!
     *
     * @param email    -> Email des Users
     * @param password -> Passwort des Users
     * @return true → Wenn Bedingungen eingehalten wurden, ansonsten false
     */
    public boolean checkInput(String email, String password) {
        TextView minLetters = findViewById(R.id.textView4);
        if (password.length() <= 8) {
            minLetters.setTextColor(Color.RED);
            return false;
        } else {
            minLetters.setTextColor(Color.BLACK);
        }
        return true;
    }
}