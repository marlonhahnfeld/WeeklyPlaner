package com.example.weeklyplaner;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class LoginScreen extends AppCompatActivity implements View.OnClickListener {

    TextView loginToRegisterTextButton;

    private Button senden_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);

        loginToRegisterTextButton = findViewById(R.id.registerTextButton);
        loginToRegisterTextButton.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        Intent intent;
        int id = v.getId();

        if (id == R.id.registerTextButton) {
            intent = new Intent(this, RegisterScreen.class);
            startActivity(intent);
        }
    }


}