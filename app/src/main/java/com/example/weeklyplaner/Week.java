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
        setContentView(R.layout.activity_week);

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

        highlightTodayButton();

    }

    private void highlightTodayButton() {
        int[] buttonIds = {R.id.Montag, R.id.Dienstag, R.id.Mittwoch, R.id.Donnerstag,
                R.id.Freitag, R.id.Samstag, R.id.Sonntag};
        Button[] buttons = {button_montag, button_dienstag, button_mittwoch,
                button_donnerstag, button_freitag, button_samstag, button_sonntag};

        for (int i = 0; i < buttonIds.length; i++) {
            if (dayOfWeek == (Calendar.MONDAY + i)) {
                buttons[i].setBackgroundColor(Color.parseColor("#7b68ee"));
                break;
            }
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
            Button clickedButton = findViewById(id);
            intent.putExtra("button_text", clickedButton.getText());
            startActivity(intent);
        } else if (id == R.id.SortButton) {
            onBackPressed();
        }
    }
}