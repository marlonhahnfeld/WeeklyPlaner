package com.example.weeklyplaner;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class  Add extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {

    private ImageButton BackButton;
    private ImageButton SaveButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        BackButton = findViewById(R.id.imageButton);
        BackButton.setOnClickListener(this);
        //SaveButton = findViewById(R.id.SaveButton);
        //SaveButton.setOnClickListener(this);

        EditText editText_Terminname = findViewById(R.id.Terminname_edit_text);
        String userInputText_Terminname = editText_Terminname.getText().toString(); // TODO: hier ist der userinput für Terminname gespeichert, eventuell global für klasse machen
        EditText editText_Beschreibung = findViewById(R.id.Beschreibung_edit_text);
        String userInputText_Beschreibung = editText_Beschreibung.getText().toString(); // TODO: hier ist der userinput für Beschreibung gespeichert, eventuell global für klasse machen

        // Spinner noch das Android Icon wegnehmen bei Betätigung
        Spinner spinner_PrioListe = findViewById(R.id.PrioListe);
        ArrayAdapter<CharSequence> adapter_PrioListe = ArrayAdapter.createFromResource(this,
                R.array.numbers, android.R.layout.simple_spinner_item);
        adapter_PrioListe.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_PrioListe.setAdapter(adapter_PrioListe);
        spinner_PrioListe.setOnItemSelectedListener(this);
    }

// TODO: Spinner-Methoden (PrioListe) noch auskapseln in Utils, OnClick-Methoden
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
        Intent intent;
        int id = v.getId();

        if (id == R.id.imageButton) {
            onBackPressed();
       }
//        else if (id == R.id.SaveButton) {
//             //Todo: gespeicherte Daten verwalten
               //Todo: Save-Button kollidiert mit etwas -> hier kommentierter Code und in xml kommentierter Code
//            onBackPressed();
//        }
    }
}