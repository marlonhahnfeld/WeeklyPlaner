package com.example.weeklyplaner;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;
import java.util.TimeZone;

public class Week extends AppCompatActivity implements View.OnClickListener {
    private ImageButton BackButton;
    private Button button_montag;
    private TextView doneMo;
    private TextView notDoneMo;
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

        BackButton = findViewById(R.id.backButtonWeekActivity);
        BackButton.setOnClickListener(this);

        button_montag = findViewById(R.id.montag);
        doneMo = findViewById(R.id.doneMo);
        notDoneMo = findViewById(R.id.notDoneMo);
        button_dienstag = findViewById(R.id.dienstag);
        button_mittwoch = findViewById(R.id.mittwoch);
        button_donnerstag = findViewById(R.id.donnerstag);
        button_freitag = findViewById(R.id.freitag);
        button_samstag = findViewById(R.id.samstag);
        button_sonntag = findViewById(R.id.sonntag);

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
        int[] buttonIds = {R.id.montag, R.id.dienstag, R.id.mittwoch, R.id.donnerstag,
                R.id.freitag, R.id.samstag, R.id.sonntag};
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

        if (id == R.id.montag || id == R.id.dienstag || id == R.id.mittwoch ||
                id == R.id.donnerstag || id == R.id.freitag || id == R.id.samstag ||
                id == R.id.sonntag) {
            intent = new Intent(this, SpecificDay.class);
            Button clickedButton = findViewById(id);
            intent.putExtra("button_text", clickedButton.getText());
            startActivity(intent);
        } else if (id == R.id.backButtonWeekActivity) {
            onBackPressed();
        }
    }
}