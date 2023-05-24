package com.example.weeklyplaner;

import static com.example.weeklyplaner.Utils.getSpecificTerminliste;

import android.content.Intent;
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

public class TerminDetailsActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {
    private EditText terminNameTextView;
    private EditText terminBeschreibungTextView;
    private Spinner terminPrioSpinner;
    private Spinner terminTagSpinner;
    private ImageButton backButton;
    private Button editButton, deleteBtton;
    private int terminId;
    private String terminTag;

    private int getSpinnerIndex(String value, String[] array) {
        for (int i = 0; i < array.length; i++) {
            if (array[i].equalsIgnoreCase(value)) {
                return i;
            }
        }
        return 1; // Default selection index
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_termin_details_screen);
        backButton = findViewById(R.id.imageButton);
        backButton.setOnClickListener(this);
        editButton = findViewById(R.id.EditButton_details);
        editButton.setOnClickListener(this);
        deleteBtton = findViewById(R.id.DeleteButton_details);
        deleteBtton.setOnClickListener(this);

        // Finde die TextViews in deinem Layout
        terminNameTextView = findViewById(R.id.Terminname_edit_text_details);
        terminBeschreibungTextView = findViewById(R.id.Beschreibung_edit_text_details);

        // Erhalte die übergebenen Daten aus dem Intent
        Intent intent = getIntent();
        terminId = intent.getIntExtra("termin_id", -1);
        String terminName = intent.getStringExtra("termin_name");
        String terminBeschreibung = intent.getStringExtra("termin_beschreibung");
        String terminPrio = intent.getStringExtra("termin_prio");
        terminTag = intent.getStringExtra("termin_tag");

        terminTagSpinner = findViewById(R.id.TagesSpinner_details);
        ArrayAdapter<CharSequence> daySpinnerAdapter = ArrayAdapter.createFromResource(this,
                R.array.wochentage, android.R.layout.simple_spinner_item);
        daySpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        terminTagSpinner.setAdapter(daySpinnerAdapter);
        terminTagSpinner.setSelection(getSpinnerIndex(terminTag, getResources().getStringArray(R.array.wochentage)));
        terminTagSpinner.setOnItemSelectedListener(this);

        terminPrioSpinner = findViewById(R.id.PrioListe_details);
        ArrayAdapter<CharSequence> prioListSpinnerAdapter = ArrayAdapter.createFromResource(this,
                R.array.numbers, android.R.layout.simple_spinner_item);
        prioListSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        terminPrioSpinner.setAdapter(prioListSpinnerAdapter);
        terminPrioSpinner.setSelection(getSpinnerIndex(terminPrio, getResources().getStringArray(R.array.numbers)));
        terminPrioSpinner.setOnItemSelectedListener(this);
        terminNameTextView.setText(terminName);
        terminBeschreibungTextView.setText(terminBeschreibung);
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
        } else if (id == R.id.EditButton_details) {
            deleteCurrentTermin();
            EditText terminNameEditText = findViewById(R.id.Terminname_edit_text_details);
            String terminName_new = terminNameEditText.getText().toString();
            EditText beschreibungEditText = findViewById(R.id.Beschreibung_edit_text_details);
            String beschreibung_new = beschreibungEditText.getText().toString();
            String prio_new = String.valueOf(terminPrioSpinner.getSelectedItem());
            String tag_new = String.valueOf(terminTagSpinner.getSelectedItem());
            Termin termin = new Termin(terminName_new, beschreibung_new, prio_new, tag_new);
            if (tag_new != terminTag) {
                // zur richtigen liste zuordnen
                getSpecificTerminliste(tag_new).add(termin);
            } else {
                getSpecificTerminliste(terminTag).add(termin);
            }
            onBackPressed();
            // TODO NEXT RELEASE: POPUP ARE YOU SURE
        } else if (id == R.id.DeleteButton_details) {
            deleteCurrentTermin();
            onBackPressed();
        }
    }

    public void deleteCurrentTermin() {
        for (Termin termin : getSpecificTerminliste(terminTag)) {

            if (termin.getId() == terminId) {
                getSpecificTerminliste(terminTag).remove(termin);
                break;
            }
        }
    }
}
