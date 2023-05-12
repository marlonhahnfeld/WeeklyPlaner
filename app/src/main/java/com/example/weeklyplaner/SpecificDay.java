package com.example.weeklyplaner;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

public class SpecificDay extends AppCompatActivity implements View.OnClickListener {
    // this BackButton can have multiple targets, make it more flexible my remembering previous Layout
    private ImageButton BackButton;
    private ImageButton filterButton;
    private ImageButton addButton;
    public static Button HeutigerButton;

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

    }

    // TODO: setText() bei MoSo würde Text mit richtigen Buttonnamen ändern, funktioniert jedoch aktuell nicht aufgrund Kollidierung (?)
    public static Button getHeutigerButton() {
        return HeutigerButton;
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        int id = v.getId();

        if (id == R.id.BackButton) {
            onBackPressed();
        } else if (id == R.id.SortButton) {
            intent = new Intent(this, Sort.class);
            startActivity(intent);
        } else if (id == R.id.AddButton) {
            intent = new Intent(this, Add.class);
            startActivity(intent);
        }
    }
}