package com.example.weeklyplaner;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class SpecificDay extends AppCompatActivity implements View.OnClickListener {

    private ImageButton BackButton;
    private ImageButton filterButton;
    private ImageButton addButton;
    public Button heutigerButton;
    static boolean refresh_needed = false;

    public RecyclerView specificDay_TerminListe_RecyclerView;

    @Override
    protected void onResume() {
        super.onResume();

        // Refresh the activity here
        refreshSpecificDay();
    }

    private void refreshSpecificDay() {
        // Refresh the specific day's data here
        String day = heutigerButton.getText().toString();
        switch (day) {
            case "Montag":
                specificDay_TerminListe_RecyclerView.setAdapter(new Termin_RecyclerView_Adapter(this, MainActivity.montag_terminliste));
                break;
            case "Dienstag":
                specificDay_TerminListe_RecyclerView.setAdapter(new Termin_RecyclerView_Adapter(this, MainActivity.dienstag_terminliste));
                break;
            case "Mittwoch":
                specificDay_TerminListe_RecyclerView.setAdapter(new Termin_RecyclerView_Adapter(this, MainActivity.mittwoch_terminliste));
                break;
            case "Donnerstag":
                specificDay_TerminListe_RecyclerView.setAdapter(new Termin_RecyclerView_Adapter(this, MainActivity.donnerstag_terminliste));
                break;
            case "Freitag":
                specificDay_TerminListe_RecyclerView.setAdapter(new Termin_RecyclerView_Adapter(this, MainActivity.freitag_terminliste));
                break;
            case "Samstag":
                specificDay_TerminListe_RecyclerView.setAdapter(new Termin_RecyclerView_Adapter(this, MainActivity.samstag_terminliste));
                break;
            case "Sonntag":
                specificDay_TerminListe_RecyclerView.setAdapter(new Termin_RecyclerView_Adapter(this, MainActivity.sonntag_terminliste));
                break;
            default:
                // Handle unexpected day name here
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_specific_day);

        BackButton = findViewById(R.id.BackButton);
        BackButton.setOnClickListener(this);

        filterButton = findViewById(R.id.SortButton);
        filterButton.setOnClickListener(this);

        addButton = findViewById(R.id.AddButton);
        addButton.setOnClickListener(this);

        heutigerButton = findViewById(R.id.HeutigerButton);
        String buttonText = getIntent().getStringExtra("button_text");
        heutigerButton.setText(buttonText);

        specificDay_TerminListe_RecyclerView = findViewById(R.id.TerminlisteRecyclerView);
        Termin_RecyclerView_Adapter adapter = null;
        switch (heutigerButton.getText().toString()) {
            case "Montag":
                adapter = new Termin_RecyclerView_Adapter(this, MainActivity.montag_terminliste);
                break;
            case "Dienstag":
                adapter = new Termin_RecyclerView_Adapter(this, MainActivity.dienstag_terminliste);
                break;
            case "Mittwoch":
                adapter = new Termin_RecyclerView_Adapter(this, MainActivity.mittwoch_terminliste);
                break;
            case "Donnerstag":
                adapter = new Termin_RecyclerView_Adapter(this, MainActivity.donnerstag_terminliste);
                break;
            case "Freitag":
                adapter = new Termin_RecyclerView_Adapter(this, MainActivity.freitag_terminliste);
                break;
            case "Samstag":
                adapter = new Termin_RecyclerView_Adapter(this, MainActivity.samstag_terminliste);
                break;
            case "Sonntag":
                adapter = new Termin_RecyclerView_Adapter(this, MainActivity.sonntag_terminliste);
                break;
        }
        specificDay_TerminListe_RecyclerView.setAdapter(adapter);
        specificDay_TerminListe_RecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        int id = v.getId();

        if (id == R.id.BackButton) {
            onBackPressed();
        }
        else if (id == R.id.SortButton) {
            intent = new Intent(this, Sort.class);
            startActivity(intent);
        } else if (id == R.id.AddButton) {
            intent = new Intent(this, Add.class);
            startActivity(intent);
        }
    }
}