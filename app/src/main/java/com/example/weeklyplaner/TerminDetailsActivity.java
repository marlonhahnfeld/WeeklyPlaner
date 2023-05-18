package com.example.weeklyplaner;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class TerminDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_termin_details_screen);

        // Hier können Sie den Termin und seine Details basierend auf der übergebenen Termin-ID anzeigen
        int terminId = getIntent().getIntExtra("termin_id", -1);
        if (terminId != -1) {
            // Termin abrufen und Details anzeigen
        } else {
            // Termin-ID nicht verfügbar, behandeln Sie den Fehler
        }
    }
}
