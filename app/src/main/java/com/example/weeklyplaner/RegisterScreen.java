package com.example.weeklyplaner;

import static com.example.weeklyplaner.DatabaseOp.*;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;


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
            registerNewUser();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        closeDatabaseConnection();
    }


}