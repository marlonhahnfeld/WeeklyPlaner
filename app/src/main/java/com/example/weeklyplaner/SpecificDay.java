package com.example.weeklyplaner;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

public class SpecificDay extends AppCompatActivity implements View.OnClickListener {
    ImageButton imageButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_specific_day);

        imageButton = findViewById(R.id.BackButton);
        imageButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        int id = v.getId();

        if (id == R.id.BackButton) {
            intent = new Intent(this, MoSo.class);
            startActivity(intent);
        }
    }
}