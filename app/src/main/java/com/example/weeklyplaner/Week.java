package com.example.weeklyplaner;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import androidx.appcompat.app.AppCompatActivity;

public class Week extends AppCompatActivity implements View.OnClickListener {
    private ImageButton BackButton;
    private Button button1;
    private Button button2;
    private Button button3;
    private Button button4;
    private Button button5;
    private Button button6;
    private Button button7;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mo_so);

        BackButton = findViewById(R.id.SortButton);
        BackButton.setOnClickListener(this);

        button1 = findViewById(R.id.Montag);
        button2 = findViewById(R.id.Dienstag);
        button3 = findViewById(R.id.Mittwoch);
        button4 = findViewById(R.id.Donnerstag);
        button5 = findViewById(R.id.Freitag);
        button6 = findViewById(R.id.Samstag);
        button7 = findViewById(R.id.Sonntag);

        button1.setOnClickListener(this);
        button2.setOnClickListener(this);
        button3.setOnClickListener(this);
        button4.setOnClickListener(this);
        button5.setOnClickListener(this);
        button6.setOnClickListener(this);
        button7.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        Intent intent;
        int id = v.getId();

        if (id == R.id.Montag || id == R.id.Dienstag || id == R.id.Mittwoch ||
                id == R.id.Donnerstag || id == R.id.Freitag || id == R.id.Samstag ||
                id == R.id.Sonntag) {
            intent = new Intent(this, SpecificDay.class);
            startActivity(intent);
        } else if (id == R.id.SortButton) {
            onBackPressed();
        }
    }
}