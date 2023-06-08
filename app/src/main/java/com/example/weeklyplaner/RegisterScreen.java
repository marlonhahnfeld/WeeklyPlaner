package com.example.weeklyplaner;

import static com.example.weeklyplaner.DatabaseOp.doesUserExist;
import static com.example.weeklyplaner.DatabaseOp.registerNewUser;

import static items.UI_Items.setEditTextUnderlineColor;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.os.Bundle;
import android.text.Html;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegisterScreen extends AppCompatActivity implements View.OnClickListener {
    private ImageButton backButton;
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

        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextPassword);
        editTextPassword2 = findViewById(R.id.editTextPassword2);
        backButton = findViewById(R.id.backToLoginScreenButton);
        registerButton = findViewById(R.id.registerButton);

        backButton.setOnClickListener(this);
        registerButton.setOnClickListener(this);

        setEditTextUnderlineColor(editTextEmail, Color.BLUE);
        setEditTextUnderlineColor(editTextPassword, Color.BLUE);
        setEditTextUnderlineColor(editTextPassword2, Color.BLUE);
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        int id = v.getId();

        editTextEmail.clearFocus();
        editTextPassword.clearFocus();
        editTextPassword2.clearFocus();
        editTextEmail.getBackground().setColorFilter(Color.BLACK, PorterDuff.Mode.SRC_IN);
        editTextPassword.getBackground().setColorFilter(Color.BLACK, PorterDuff.Mode.SRC_IN);
        editTextPassword2.getBackground().setColorFilter(Color.BLACK, PorterDuff.Mode.SRC_IN);

        if (id == R.id.backToLoginScreenButton) {
            onBackPressed();
        } else if (id == R.id.registerButton) {
            email = editTextEmail.getText().toString();
            password = editTextPassword.getText().toString();
            password2 = editTextPassword2.getText().toString();
            if (checkInput(email, password)) {
                doesUserExist(email, exists -> {
                    if (exists) {
                        AlertDialog.Builder builder =
                                new AlertDialog.Builder(RegisterScreen.this);
                        builder.setTitle("Achtung");
                        builder.setMessage("E-Mail bereits vergeben :(");
                        builder.setPositiveButton("OK", (dialog, which) -> {
                            dialog.dismiss();
                        });
                        builder.show();
                    } else {
                        registerNewUser(email, password);
                        AlertDialog.Builder builder =
                                new AlertDialog.Builder(RegisterScreen.this);
                        builder.setTitle("\uD83C\uDF89 Registrierung erfolgreich \uD83C\uDF89");
                        builder.setMessage("Herzlichen Glückwunsch! " +
                                "Sie wurden erfolgreich registriert.");
                        builder.setPositiveButton("OK", (dialog, which) -> {
                            final Intent intent1 = new Intent(RegisterScreen.this,
                                    LoginScreen.class);
                            startActivity(intent1);
                            dialog.dismiss();
                        });
                        builder.show();
                    }
                });

            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (v instanceof EditText) {
                Rect outRect = new Rect();
                v.getGlobalVisibleRect(outRect);
                if (!outRect.contains((int) event.getRawX(), (int) event.getRawY())) {
                    v.clearFocus();
                    InputMethodManager imm =
                            (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            }
        }
        return super.onTouchEvent(event);
    }

    /**
     * Methode um den Input des Users zu überprüfen
     *
     * @param email    die der User nutzen möchte
     * @param password die der User nutzen möchte
     * @return true, wenn alle Anforderungen eingehalten wurden -> false, wenn nicht
     */
    public boolean checkInput(String email, String password) {
        TextView minLetters = findViewById(R.id.min8SignsLongTextView);
        TextView min1Sign = findViewById(R.id.min1SignTextView);
        TextView min1UpperAndLower = findViewById(R.id.min1UpperAndLowerLetterTextView);
        TextView min1Figure = findViewById(R.id.min1FigureTextView);

        boolean length = false;
        boolean containsDigit = false;
        boolean containsSign = false;
        boolean containsUpperAndLower = false;

        Pattern emailPattern = Pattern.compile("^[a-zA-Z0-9]+@[a-zA-Z]+\\.[a-zA-Z]{2,6}$");
        Matcher emailMatcher = emailPattern.matcher(email);
        boolean emailMatchFound = emailMatcher.find();
        boolean passwordsMatch = password.equals(password2);

        if (!emailMatchFound) {
            String emailFormat = "1) Email darf <b>Groß-, Kleinbuchstaben & Zahlen</b> " +
                    "enthalten!<br><br>" +
                    "2) Email darf <b>keine</b> Sonderzeichen enthalten!<br><br>" +
                    "3) Email muss ein <b>@-Zeichen</b> enthalten!<br><br>" +
                    "4) Email muss mit einem <b>abschließenden Punkt</b> enden!";

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Falsche E-Mail Formatierung");
            builder.setMessage(Html.fromHtml(emailFormat, Html.FROM_HTML_MODE_LEGACY));
            builder.setPositiveButton("OK", (dialog, which) -> dialog.dismiss()).show();
        }


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

        if (!passwordsMatch) {
            editTextPassword.setTextColor(Color.RED);
            editTextPassword2.setTextColor(Color.RED);
        } else {
            editTextPassword.setTextColor(Color.BLACK);
            editTextPassword2.setTextColor(Color.BLACK);
        }

        return emailMatchFound && length && containsDigit && containsSign &&
                containsUpperAndLower && passwordsMatch;
    }

    /**
     * Methode um zu überprüfen, ob ein String UpperCase Buchstaben enthält
     *
     * @param s der zu übergebenden String
     * @return true, wenn ein UpperCase Buchstabe gefunden wird
     */
    public static boolean containsUpperCaseLetter(String s) {
        for (char c : s.toCharArray()) {
            if (Character.isUpperCase(c)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Methode um zu überprüfen, ob ein String ein LowerCase Buchstaben enthält
     *
     * @param s der zu übergebende String
     * @return true, wenn ein LowerCase Buchstabe gefunden wird
     */
    public static boolean containsLowerCaseLetter(String s) {
        for (char c : s.toCharArray()) {
            if (Character.isLowerCase(c)) {
                return true;
            }
        }
        return false;
    }
}