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

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegisterScreen extends AppCompatActivity implements View.OnClickListener {
    private TextView dbStatusTextView;
    private TextView dbTestView;
    private ImageButton backToLoginScreenButton;
    private Button registerButton;
    private EditText editTextEmail;
    private EditText editTextPassword;
    private EditText editTextPassword2;
    private String email;
    private String password;
    private String password2;

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
        editTextPassword2 = findViewById(R.id.editTextPassword2);

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
            password2 = editTextPassword2.getText().toString();
            if (checkInput(email, password)) {
                registerNewUser(email, password);
                intent = new Intent(this, LoginScreen.class);
                startActivity(intent);
            }
        }
    }

    /**
     * * Methode, um Input des Users zu überprüfen
     *
     * @param password -> Passwort des Users
     * @return true → Wenn Bedingungen eingehalten wurden, ansonsten false
     */
    public boolean checkInput(String email, String password) {
        TextView minLetters = findViewById(R.id.textView4);
        TextView min1Figure = findViewById(R.id.textView7);
        TextView min1Sign = findViewById(R.id.textView5);
        TextView min1UpperAndLower = findViewById(R.id.textView6);

        boolean length = false;
        boolean containsDigit = false;
        boolean containsSign = false;
        boolean containsUpperAndLower = false;

        Pattern emailPattern = Pattern.compile("^[a-zA-Z0-9]+@[a-zA-Z]+\\.[a-zA-Z]{2,5}$");
        Matcher emailMatcher = emailPattern.matcher(email);
        boolean emailMatchFound = emailMatcher.find();
        boolean passwordsMatch = password.equals(editTextPassword2.getText().toString());

        if (password.length() <= 8) {
            minLetters.setTextColor(Color.RED);
        } else {
            minLetters.setTextColor(Color.GREEN);
            length = true;
        }

        for (char c : password.toCharArray()) {
            if (Character.isDigit(c)) {
                containsDigit = true;
                break;
            }
        }
        if (containsDigit) {
            min1Figure.setTextColor(Color.GREEN);
        } else {
            min1Figure.setTextColor(Color.RED);
        }

        for (char c : password.toCharArray()) {
            if (!Character.isLetter(c)) {
                if (Character.isDigit(c)) {
                    continue;
                }
                containsSign = true;
                break;
            }
        }
        if (containsSign) {
            min1Sign.setTextColor(Color.GREEN);
        } else {
            min1Sign.setTextColor(Color.RED);
        }

        if (containsLowerCaseLetter(password) && containsUpperCaseLetter(password)) {
            containsUpperAndLower = true;
            min1UpperAndLower.setTextColor(Color.GREEN);
        } else {
            min1UpperAndLower.setTextColor(Color.RED);
        }

        // Überprüfe, ob die E-Mail-Adresse und die Passwort-Bestätigung übereinstimmen
        boolean emailAndConfirmationMatch = email.equals(editTextEmail.getText().toString());

        // Überprüfe, ob alle Überprüfungskriterien erfüllt sind
        boolean isValid = emailMatchFound && length && containsDigit && containsSign &&
                containsUpperAndLower && passwordsMatch && emailAndConfirmationMatch;

        // Markiere die EditText-Felder rot, wenn die Passwortbestätigung fehlschlägt
        if (!passwordsMatch) {
            editTextPassword.setTextColor(Color.RED);
            editTextPassword2.setTextColor(Color.RED);
        } else {
            editTextPassword.setTextColor(Color.BLACK);
            editTextPassword2.setTextColor(Color.BLACK);
        }

        return isValid;
    }

    public static boolean containsUpperCaseLetter(String s) {
        for (char c : s.toCharArray()) {
            if (Character.isUpperCase(c)) {
                return true;
            }
        }
        return false;
    }

    public static boolean containsLowerCaseLetter(String s) {
        for (char c : s.toCharArray()) {
            if (Character.isLowerCase(c)) {
                return true;
            }
        }
        return false;
    }
}