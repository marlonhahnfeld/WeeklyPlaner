package com.example.weeklyplaner;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class RegisterScreen extends AppCompatActivity {

   private ImageButton registerToLoginButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_screen);
        registerToLoginButton = (ImageButton) findViewById(R.id.registerToLoginButton);

        registerToLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterScreen.this,LoginScreen.class);
                startActivity(intent);
            }
        });

    }
}