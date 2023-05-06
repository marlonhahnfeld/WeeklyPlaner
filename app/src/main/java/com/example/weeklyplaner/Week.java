package com.example.weeklyplaner;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import androidx.appcompat.app.AppCompatActivity;

public class Week extends AppCompatActivity implements View.OnClickListener {
    private ImageButton BackButton;
    private Button montag;
    private Button dienstag;
    private Button mittwoch;
    private Button donnerstag;
    private Button freitag;
    private Button samstag;
    private Button sonntag;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mo_so);

        BackButton = findViewById(R.id.SortButton);
        BackButton.setOnClickListener(this);

        montag = findViewById(R.id.Montag);
        dienstag = findViewById(R.id.Dienstag);
        mittwoch = findViewById(R.id.Mittwoch);
        donnerstag = findViewById(R.id.Donnerstag);
        freitag = findViewById(R.id.Freitag);
        samstag = findViewById(R.id.Samstag);
        sonntag = findViewById(R.id.Sonntag);

        montag.setOnClickListener(this);
        dienstag.setOnClickListener(this);
        mittwoch.setOnClickListener(this);
        donnerstag.setOnClickListener(this);
        freitag.setOnClickListener(this);
        samstag.setOnClickListener(this);
        sonntag.setOnClickListener(this);

    }
    @Override
    public void onClick(View v) {
        Intent intent;
        int id = v.getId();


        if (id == R.id.Montag || id == R.id.Dienstag || id == R.id.Mittwoch ||
                id == R.id.Donnerstag || id == R.id.Freitag || id == R.id.Samstag ||
                id == R.id.Sonntag) {
            intent = new Intent(this, SpecificDay.class);
            if (id == R.id.Montag) {
                intent.putExtra("button_text", montag.getText());
            } else if (id == R.id.Dienstag) {
                intent.putExtra("button_text", dienstag.getText());
            } else if (id == R.id.Mittwoch) {
                intent.putExtra("button_text", mittwoch.getText());
            } else if (id == R.id.Donnerstag) {
                intent.putExtra("button_text", donnerstag.getText());
            } else if (id == R.id.Freitag) {
                intent.putExtra("button_text", freitag.getText());
            } else if (id == R.id.Samstag) {
                intent.putExtra("button_text", samstag.getText());
            } else if (id == R.id.Sonntag) {
                intent.putExtra("button_text", sonntag.getText());
            }
            startActivity(intent);
        } else if (id == R.id.SortButton) {
            onBackPressed();
        }
    }
}