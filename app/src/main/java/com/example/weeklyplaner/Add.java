package com.example.weeklyplaner;

import android.content.Intent;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import items.Termin;
import items.TerminListe;

public class  Add extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {

    private ImageButton BackButton;
    private Button SaveButton;
    private Spinner spinner_PrioListe,spinner_wann;
    private int save_counter = 0;
    public static TerminListe TerminListe = new TerminListe();
    private TextView montag,dienstag,mittwoch,donnerstag,freitag,samstag,sonntag;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        BackButton = findViewById(R.id.imageButton);
        BackButton.setOnClickListener(this);
        SaveButton = findViewById(R.id.SaveButton);
        SaveButton.setOnClickListener(this);



       // Wochentage drop down menu
        spinner_wann = findViewById(R.id.TagesSpinner);
        ArrayAdapter<CharSequence> adapter_TagesSpinner = ArrayAdapter.createFromResource(this,
                R.array.wochentage, android.R.layout.simple_spinner_item);
        adapter_TagesSpinner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_wann.setAdapter(adapter_TagesSpinner);
        spinner_wann.setOnItemSelectedListener(this);

        // Spinner noch das Android Icon wegnehmen bei Betätigung
        spinner_PrioListe = findViewById(R.id.PrioListe);
        ArrayAdapter<CharSequence> adapter_PrioListe = ArrayAdapter.createFromResource(this,
                R.array.numbers, android.R.layout.simple_spinner_item);
        adapter_PrioListe.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_PrioListe.setAdapter(adapter_PrioListe);
        spinner_PrioListe.setOnItemSelectedListener(this);
       // String prompt = String.valueOf(spinner_PrioListe.getSelectedItem());
       // SaveButton.setText(prompt);
        //spinner_PrioListe.setPointerIcon(android.R.drawable.ic_menu_sort_by_size);
      //@android:drawable/ic_menu_sort_by_size


    }


// TODO: Spinner-Methoden (PrioListe) noch auskapseln into Utils, OnClick-Methoden
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
       // Intent intent;
        int id = v.getId();

        if (id == R.id.imageButton) {
            onBackPressed();
       }


        else if (id == R.id.SaveButton) {
            // gespeicherte Daten verwalten & Terminliste mit Termin anlegen
            //intent = new Intent(this, MainActivity.class);
            EditText editText_Terminname = findViewById(R.id.Terminname_edit_text);
            String userInputText_Terminname = editText_Terminname.getText().toString(); // TODO: hier ist der userinput für Terminname gespeichert, eventuell global für klasse machen
            EditText editText_Beschreibung = findViewById(R.id.Beschreibung_edit_text);
            String userInputText_Beschreibung = editText_Beschreibung.getText().toString(); // TODO: hier ist der userinput für Beschreibung gespeichert, eventuell global für klasse machen
            String userInput_SpinnerPrio = String.valueOf(spinner_PrioListe.getSelectedItem());
            String userInput_Wochentag = String.valueOf(spinner_wann.getSelectedItem());



//TODO BEIM ERZEUGEN VON TERMIN INNERHALB SPECIFICDAY WIRD NICHT AKTUALISIERT
            Termin termin = new Termin(userInputText_Terminname, userInputText_Beschreibung, userInput_SpinnerPrio, save_counter, userInput_Wochentag);
            save_counter++;
            //MainActivity.heute_terminliste.add(termin);

            if(userInput_Wochentag.equals("Montag")){
                MainActivity.montag_terminliste.add(termin);
            }else if(userInput_Wochentag.equals("Dienstag")){
                MainActivity.dienstag_terminliste.add(termin);
            }else if(userInput_Wochentag.equals("Mittwoch")){
                MainActivity.mittwoch_terminliste.add(termin);
            }else if(userInput_Wochentag.equals("Donnerstag")){
                MainActivity.donnerstag_terminliste.add(termin);
            }else if (termin.tag.equals("Freitag")){
                MainActivity.freitag_terminliste.add(termin);
            }else if (termin.tag.equals("Samstag")){
                MainActivity.samstag_terminliste.add(termin);
            }else {
                MainActivity.sonntag_terminliste.add(termin);
            }

            SpecificDay.refresh_needed = true;

           // String prompt = new String(termin.getTerminname()+termin.getBeschreibung()+termin.getPrio());
           // SaveButton.setText(prompt);
           // intent.putExtra("buttonCount", save_counter);
           // intent.putExtra("terminliste", termine);
           // intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

            //startActivity(intent);
            onBackPressed();
       }
    }
}