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
    // this BackButton can have multiple targets, make it more flexible my remembering previous Layout
    private ImageButton BackButton;
    private ImageButton filterButton;
    private ImageButton addButton;
    public Button heutigerButton;
    static boolean refresh_needed = false;

    @Override
    protected void onResume() {
        super.onResume();

        // Refresh the activity here
        refreshSpecificDay();
    }

    private void refreshSpecificDay() {
            // Refresh the specific day's data here
            // For example, update the RecyclerView adapter with the latest data
            if (heutigerButton.getText().equals("Montag")) {
                Termin_RecyclerView_Adapter adapter = new Termin_RecyclerView_Adapter(this, MainActivity.montag_terminliste);
                specificDay_TerminListe_RecyclerView.setAdapter(adapter);
            } else if (heutigerButton.getText().equals("Dienstag")) {
                Termin_RecyclerView_Adapter adapter = new Termin_RecyclerView_Adapter(this, MainActivity.dienstag_terminliste);
                specificDay_TerminListe_RecyclerView.setAdapter(adapter);
            } else if (heutigerButton.getText().equals("Mittwoch")) {
                Termin_RecyclerView_Adapter adapter = new Termin_RecyclerView_Adapter(this, MainActivity.mittwoch_terminliste);
                specificDay_TerminListe_RecyclerView.setAdapter(adapter);
            } else if (heutigerButton.getText().equals("Donnerstag")) {
                Termin_RecyclerView_Adapter adapter = new Termin_RecyclerView_Adapter(this, MainActivity.donnerstag_terminliste);
                specificDay_TerminListe_RecyclerView.setAdapter(adapter);
            } else if (heutigerButton.getText().equals("Freitag")) {
                Termin_RecyclerView_Adapter adapter = new Termin_RecyclerView_Adapter(this, MainActivity.freitag_terminliste);
                specificDay_TerminListe_RecyclerView.setAdapter(adapter);
            } else if (heutigerButton.getText().equals("Samstag")) {
                Termin_RecyclerView_Adapter adapter = new Termin_RecyclerView_Adapter(this, MainActivity.samstag_terminliste);
                specificDay_TerminListe_RecyclerView.setAdapter(adapter);
            } else if (heutigerButton.getText().equals("Sonntag")) {
                Termin_RecyclerView_Adapter adapter = new Termin_RecyclerView_Adapter(this, MainActivity.sonntag_terminliste);
                specificDay_TerminListe_RecyclerView.setAdapter(adapter);
            }
        }


    public RecyclerView specificDay_TerminListe_RecyclerView;
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
        this.heutigerButton.setText(buttonText);
        //
        if (heutigerButton.getText().equals("Montag")) {
            specificDay_TerminListe_RecyclerView = findViewById(R.id.TerminlisteRecyclerView);
            Termin_RecyclerView_Adapter adapter = new Termin_RecyclerView_Adapter(this, MainActivity.montag_terminliste);
            specificDay_TerminListe_RecyclerView.setAdapter(adapter);
            // recyclerView.setHasFixedSize(true);
            specificDay_TerminListe_RecyclerView.setLayoutManager(new LinearLayoutManager(this));
        } else if (heutigerButton.getText().equals("Dienstag")) {
            specificDay_TerminListe_RecyclerView = findViewById(R.id.TerminlisteRecyclerView);
            Termin_RecyclerView_Adapter adapter = new Termin_RecyclerView_Adapter(this, MainActivity.dienstag_terminliste);
            specificDay_TerminListe_RecyclerView.setAdapter(adapter);
            // recyclerView.setHasFixedSize(true);
            specificDay_TerminListe_RecyclerView.setLayoutManager(new LinearLayoutManager(this));
        } else if (heutigerButton.getText().equals("Mittwoch")) {
            specificDay_TerminListe_RecyclerView = findViewById(R.id.TerminlisteRecyclerView);
            Termin_RecyclerView_Adapter adapter = new Termin_RecyclerView_Adapter(this, MainActivity.mittwoch_terminliste);
            specificDay_TerminListe_RecyclerView.setAdapter(adapter);
            // recyclerView.setHasFixedSize(true);
            specificDay_TerminListe_RecyclerView.setLayoutManager(new LinearLayoutManager(this));
        } else if (heutigerButton.getText().equals("Donnerstag")) {
            specificDay_TerminListe_RecyclerView = findViewById(R.id.TerminlisteRecyclerView);
            Termin_RecyclerView_Adapter adapter = new Termin_RecyclerView_Adapter(this, MainActivity.donnerstag_terminliste);
            specificDay_TerminListe_RecyclerView.setAdapter(adapter);
            // recyclerView.setHasFixedSize(true);
            specificDay_TerminListe_RecyclerView.setLayoutManager(new LinearLayoutManager(this));
        } else if (heutigerButton.getText().equals("Freitag")) {
            specificDay_TerminListe_RecyclerView = findViewById(R.id.TerminlisteRecyclerView);
            Termin_RecyclerView_Adapter adapter = new Termin_RecyclerView_Adapter(this, MainActivity.freitag_terminliste);
            specificDay_TerminListe_RecyclerView.setAdapter(adapter);
            // recyclerView.setHasFixedSize(true);
            specificDay_TerminListe_RecyclerView.setLayoutManager(new LinearLayoutManager(this));
        } else if (heutigerButton.getText().equals("Samstag")) {
            specificDay_TerminListe_RecyclerView = findViewById(R.id.TerminlisteRecyclerView);
            Termin_RecyclerView_Adapter adapter = new Termin_RecyclerView_Adapter(this, MainActivity.samstag_terminliste);
            specificDay_TerminListe_RecyclerView.setAdapter(adapter);
            // recyclerView.setHasFixedSize(true);
            specificDay_TerminListe_RecyclerView.setLayoutManager(new LinearLayoutManager(this));
        } else if (heutigerButton.getText().equals("Sonntag")) {
            specificDay_TerminListe_RecyclerView = findViewById(R.id.TerminlisteRecyclerView);
            Termin_RecyclerView_Adapter adapter = new Termin_RecyclerView_Adapter(this, MainActivity.sonntag_terminliste);
            specificDay_TerminListe_RecyclerView.setAdapter(adapter);
            // recyclerView.setHasFixedSize(true);
            specificDay_TerminListe_RecyclerView.setLayoutManager(new LinearLayoutManager(this));
        }

    }

    // TODO: setText() bei MoSo würde Text mit richtigen Buttonnamen ändern, funktioniert jedoch aktuell nicht aufgrund Kollidierung (?)
    public Button getHeutigerButton(){
        return heutigerButton;
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