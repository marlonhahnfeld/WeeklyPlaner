package com.example.weeklyplaner;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;

public class SpecificDay extends AppCompatActivity {
    ImageButton imageButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_specific_day);
        imageButton = findViewById(R.id.BackButton);
        imageButton.setOnClickListener(v -> openBack());
    }

    public void openBack(){
        Intent intent = new Intent(this, MoSo.class);
        startActivity(intent);
    }
}