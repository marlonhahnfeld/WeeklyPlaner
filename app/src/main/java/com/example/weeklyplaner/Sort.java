package com.example.weeklyplaner;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

public class Sort extends AppCompatActivity implements View.OnClickListener {
    private ImageButton BackButton;
    private ImageButton addButton;
    private ImageButton filterButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sort);

        BackButton = findViewById(R.id.backButtonWeekActivity);
        BackButton.setOnClickListener(this);
        addButton = findViewById(R.id.addButton);
        addButton.setOnClickListener(this);
        filterButton = findViewById(R.id.SortButtonFilter);
        filterButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        int id = v.getId();

        if (id == R.id.backButtonWeekActivity) {
            onBackPressed();
        } else if (id == R.id.addButton) {
            intent = new Intent(this, Add.class);
            startActivity(intent);
        }
    }
}