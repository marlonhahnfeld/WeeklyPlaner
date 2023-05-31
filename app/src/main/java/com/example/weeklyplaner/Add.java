package com.example.weeklyplaner;

import static com.example.weeklyplaner.DatabaseOp.*;

import static com.example.weeklyplaner.Utils.getSpecificTerminliste;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import items.Termin;

public class Add extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {

    private ImageButton backButton;
    private Button saveButton;
    private Spinner prioListSpinner, daySpinner;

    public static int saveCounter;

    //TOD
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        createDatabaseConnection();

        if (isConnected()) {
            System.out.println("Verbunden -> Add Activity");
        }

        backButton = findViewById(R.id.imageButton);
        backButton.setOnClickListener(this);
        saveButton = findViewById(R.id.SaveButton);
        saveButton.setOnClickListener(this);

        daySpinner = findViewById(R.id.TagesSpinner);
        ArrayAdapter<CharSequence> daySpinnerAdapter = ArrayAdapter.createFromResource(this,
                R.array.wochentage, android.R.layout.simple_spinner_item);
        daySpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        daySpinner.setAdapter(daySpinnerAdapter);
        daySpinner.setOnItemSelectedListener(this);

        prioListSpinner = findViewById(R.id.PrioListe);
        ArrayAdapter<CharSequence> prioListSpinnerAdapter = ArrayAdapter.createFromResource(this,
                R.array.numbers, android.R.layout.simple_spinner_item);
        prioListSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        prioListSpinner.setAdapter(prioListSpinnerAdapter);
        prioListSpinner.setOnItemSelectedListener(this);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String text = parent.getItemAtPosition(position).toString();
        Toast.makeText(parent.getContext(), text, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        if (id == R.id.imageButton) {
            onBackPressed();
        } else if (id == R.id.SaveButton) {
            EditText terminNameEditText = findViewById(R.id.Terminname_edit_text);
            String terminName = terminNameEditText.getText().toString();
            EditText beschreibungEditText = findViewById(R.id.Beschreibung_edit_text);
            String beschreibung = beschreibungEditText.getText().toString();
            String prio = String.valueOf(prioListSpinner.getSelectedItem());
            String tag = String.valueOf(daySpinner.getSelectedItem());

            saveCounter = getSpecificTerminliste(tag).get(getSpecificTerminliste(tag).size() - 1).getId() + 1;
            Termin termin = new Termin(terminName, beschreibung, prio, tag, saveCounter);
            saveCounter++;

            saveAppointment(termin.getId(), LoginScreen.email, terminName, beschreibung, prio, tag);
            getSpecificTerminliste(tag).add(termin);

            closeDatabaseConnection();
            SpecificDay.refresh_needed = true;
            onBackPressed();
        }
    }
}