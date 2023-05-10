package com.example.weeklyplaner;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class RegisterScreen extends AppCompatActivity implements View.OnClickListener {

    private ImageButton backToLoginScreenButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_screen);

        backToLoginScreenButton = findViewById(R.id.backToLoginScreenButton);
        backToLoginScreenButton.setOnClickListener(this);

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
}