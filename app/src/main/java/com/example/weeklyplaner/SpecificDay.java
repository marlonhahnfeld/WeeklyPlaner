package com.example.weeklyplaner;

import static com.example.weeklyplaner.Utils.getSpecificTerminliste;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import items.Termin;

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
        specificDay_TerminListe_RecyclerView.setAdapter(new Termin_RecyclerView_Adapter(this, getSpecificTerminliste(day)));
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
        adapter = new Termin_RecyclerView_Adapter(this, getSpecificTerminliste(heutigerButton.getText().toString()));
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