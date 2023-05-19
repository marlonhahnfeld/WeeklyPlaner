package com.example.weeklyplaner;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class TerminDetailsActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {
    private EditText terminNameTextView;
    private EditText terminBeschreibungTextView;
    private Spinner terminPrioSpinner;
    private Spinner terminTagSpinner;

    private ImageButton backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_termin_details_screen);
        backButton = findViewById(R.id.imageButton);
        backButton.setOnClickListener(this);

        // Finde die TextViews in deinem Layout
        terminNameTextView = findViewById(R.id.Terminname_edit_text_details);
        terminBeschreibungTextView = findViewById(R.id.Beschreibung_edit_text_details);


        // Erhalte die übergebenen Daten aus dem Intent
        Intent intent = getIntent();
        String terminName = intent.getStringExtra("termin_name");
        String terminBeschreibung = intent.getStringExtra("termin_beschreibung");
        String terminPrio = intent.getStringExtra("termin_prio");
        String terminTag = intent.getStringExtra("termin_tag");


        terminTagSpinner = findViewById(R.id.TagesSpinner_details);
        ArrayAdapter<CharSequence> daySpinnerAdapter = ArrayAdapter.createFromResource(this,
                R.array.wochentage, android.R.layout.simple_spinner_item);
        daySpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        switch (terminTag) {
//            case "Montag":
//                terminTagSpinner.setSelection(0);
//                break;
//            case "Dienstag":
//                terminTagSpinner.setSelection(1);
//                break;
//            case "Mittwoch":
//                terminTagSpinner.setSelection(2);
//                break;
//            case "Donnerstag":
//                terminTagSpinner.setSelection(3);
//                break;
//            case "Freitag":
//                terminTagSpinner.setSelection(4);
//                break;
//            case "Samstag":
//                terminTagSpinner.setSelection(5);
//                break;
//            case "Sonntag":
//                terminTagSpinner.setSelection(6);
//                break;
//        }
        terminTagSpinner.setAdapter(daySpinnerAdapter);
        terminTagSpinner.setOnItemSelectedListener(this);


        terminPrioSpinner = findViewById(R.id.PrioListe_details);
        ArrayAdapter<CharSequence> prioListSpinnerAdapter = ArrayAdapter.createFromResource(this,
                R.array.numbers, android.R.layout.simple_spinner_item);
        prioListSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //terminPrioSpinner.setSelection(terminPrio.charAt(terminPrio.length() - 1) - 1);
        terminPrioSpinner.setAdapter(prioListSpinnerAdapter);
        terminPrioSpinner.setOnItemSelectedListener(this);
        terminNameTextView.setText(terminName);
        terminBeschreibungTextView.setText(terminBeschreibung);


    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if (parent == terminTagSpinner) {
            String selectedTag = parent.getItemAtPosition(position).toString();
            switch (selectedTag) {
                case "Montag":
                    terminTagSpinner.setSelection(0);
                    break;
                case "Dienstag":
                    terminTagSpinner.setSelection(1);
                    break;
                case "Mittwoch":
                    terminTagSpinner.setSelection(2);
                    break;
                case "Donnerstag":
                    terminTagSpinner.setSelection(3);
                    break;
                case "Freitag":
                    terminTagSpinner.setSelection(4);
                    break;
                case "Samstag":
                    terminTagSpinner.setSelection(5);
                    break;
                case "Sonntag":
                    terminTagSpinner.setSelection(6);
                    break;
            }
        } else if (parent == terminPrioSpinner) {
            String selectedPrio = parent.getItemAtPosition(position).toString();
            // Code zur Festlegung der Auswahl des Prioritäts-Spinners
        }
    }


    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }

    @Override
    public void onClick(View v) {

        int id = v.getId();
        if (id == R.id.imageButton) {
            onBackPressed();
        }
    }
}
