package com.example.weeklyplaner;

import static com.example.weeklyplaner.DatabaseOp.loadAppointments;
import static items.UI_Items.animateNonVisible2Visible;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import datenbank_listener.LoginListener;

public class LoginScreen extends AppCompatActivity implements View.OnClickListener {
    private ImageView logoView;
    private TextView registerButton;
    private Button loginButton;
    private TextView titleTextView;
    private EditText editTextEmail;
    private EditText editTextPassword;
    public static String email;
    private String password;
    private DatabaseOp databaseOp;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);

        logoView = findViewById(R.id.logoView);
        titleTextView = findViewById(R.id.weeklyPlaner);
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextPassword);
        registerButton = findViewById(R.id.registerTextButton);
        loginButton = findViewById(R.id.loginButton);

        registerButton.setOnClickListener(this);
        loginButton.setOnClickListener(this);

        long dauer = 1250;

        animateNonVisible2Visible(logoView, dauer);
        animateNonVisible2Visible(titleTextView, dauer);
        animateNonVisible2Visible(findViewById(R.id.text_email), dauer);
        animateNonVisible2Visible(editTextEmail, dauer);
        animateNonVisible2Visible(findViewById(R.id.text_password), dauer);
        animateNonVisible2Visible(editTextPassword, dauer);
        animateNonVisible2Visible(registerButton, dauer);
        animateNonVisible2Visible(loginButton, dauer);

        databaseOp = new DatabaseOp();
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        editTextEmail.clearFocus();
        editTextPassword.clearFocus();

        if (id == R.id.registerTextButton) {
            Intent intent = new Intent(this, RegisterScreen.class);
            startActivity(intent);
        } else if (id == R.id.loginButton) {
            email = editTextEmail.getText().toString();
            password = editTextPassword.getText().toString();

            databaseOp.checkLogInData(email, password, new LoginListener() {
                @Override
                public void onLoginSuccess() {
                    loadAppointments(email, () -> {
                        Intent intent =
                                new Intent(LoginScreen.this, MainActivity.class);
                        startActivity(intent);
                    });
                }

                @Override
                public void onLoginFailure(String message) {
                    Toast.makeText(LoginScreen.this, message, Toast.LENGTH_SHORT).show();
                }
            });
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
}
