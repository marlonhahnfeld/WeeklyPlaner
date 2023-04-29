package com.example.weeklyplaner;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

public class Add extends AppCompatActivity implements View.OnClickListener {

    private ImageButton imageButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        imageButton = findViewById(R.id.imageButton);
        imageButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        int id = v.getId();

        if (id == R.id.imageButton) {
            intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
    }
}