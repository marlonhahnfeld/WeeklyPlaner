package com.example.weeklyplaner;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;
import java.util.TimeZone;

public class Week extends AppCompatActivity implements View.OnClickListener {
    private ImageButton BackButton;
    private Button button_montag;
    private Button button_dienstag;
    private Button button_mittwoch;
    private Button button_donnerstag;
    private Button button_freitag;
    private Button button_samstag;
    private Button button_sonntag;



    TimeZone german_timezone = TimeZone.getTimeZone("Europe/Berlin");
    Calendar calendar = Calendar.getInstance(german_timezone);

    int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mo_so);

        BackButton = findViewById(R.id.SortButton);
        BackButton.setOnClickListener(this);

        button_montag = findViewById(R.id.Montag);
        button_dienstag = findViewById(R.id.Dienstag);
        button_mittwoch = findViewById(R.id.Mittwoch);
        button_donnerstag = findViewById(R.id.Donnerstag);
        button_freitag = findViewById(R.id.Freitag);
        button_samstag = findViewById(R.id.Samstag);
        button_sonntag = findViewById(R.id.Sonntag);

        button_montag.setOnClickListener(this);
        button_dienstag.setOnClickListener(this);
        button_mittwoch.setOnClickListener(this);
        button_donnerstag.setOnClickListener(this);
        button_freitag.setOnClickListener(this);
        button_samstag.setOnClickListener(this);
        button_sonntag.setOnClickListener(this);

        if (dayOfWeek == Calendar.MONDAY) {
            button_montag.setBackgroundColor(Color.MAGENTA);
        } else if (dayOfWeek == Calendar.TUESDAY) {
            button_dienstag.setBackgroundColor(Color.MAGENTA);
        } else if (dayOfWeek == Calendar.WEDNESDAY) {
            button_mittwoch.setBackgroundColor(Color.MAGENTA);
        } else if (dayOfWeek == Calendar.THURSDAY) {
            button_donnerstag.setBackgroundColor(Color.MAGENTA);
        } else if (dayOfWeek == Calendar.FRIDAY) {
            button_freitag.setBackgroundColor(Color.MAGENTA);
        } else if (dayOfWeek == Calendar.SATURDAY) {
            button_samstag.setBackgroundColor(Color.MAGENTA);
        } else if (dayOfWeek == Calendar.SUNDAY) {
            button_sonntag.setBackgroundColor(Color.MAGENTA);
        }

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