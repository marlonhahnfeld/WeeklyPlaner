package com.example.weeklyplaner;

import static com.example.weeklyplaner.DatabaseOp.deleteAppointment;
import static com.example.weeklyplaner.DatabaseOp.getHighestIDFromDB;
import static com.example.weeklyplaner.DatabaseOp.saveAppointment;
import static com.example.weeklyplaner.Utils.getSpecificTerminliste;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Objects;

import items.Termin;

public class TerminDetailsActivity extends AppCompatActivity implements View.OnClickListener,
        AdapterView.OnItemSelectedListener {
    private EditText terminNameTextView;
    private EditText terminBeschreibungTextView;
    private Spinner terminPrioSpinner;
    private ImageButton backButton;
    private Button editButton, deleteButton;
    private int terminId;
    private String terminDatum;
    private DatePickerDialog datePickerDialog;
    private Button buttonDatePicker;
    private int terminTag;
    private DatabaseOp databaseOp;

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

        databaseOp = new DatabaseOp();

        backButton = findViewById(R.id.backButtonAddActivity);
        backButton.setOnClickListener(this);
        editButton = findViewById(R.id.editButton);
        editButton.setOnClickListener(this);
        deleteButton = findViewById(R.id.deleteButton);
        deleteButton.setOnClickListener(this);
        buttonDatePicker = findViewById(R.id.buttonDatepickerDetailsActivity);
        initDatePicker();
        buttonDatePicker.setText(getTodaysDay());
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, LocalDate.now().getDayOfMonth() -
                (LocalDate.now().getDayOfWeek().getValue() - DayOfWeek.MONDAY.getValue()));
        calendar.set(Calendar.MONTH, LocalDate.now().getMonthValue() - 1);
        calendar.set(Calendar.YEAR, LocalDate.now().getYear());
        //default ausgewähltes datum beim Öffnen von datum für den datum spinner
        datePickerDialog.getDatePicker().setMinDate(calendar.getTimeInMillis());

        // Finde die TextViews in deinem Layout
        terminNameTextView = findViewById(R.id.editTextTerminNameDetailsActivity);
        terminBeschreibungTextView = findViewById(R.id.editTextBeschreibungDetailsActivity);

        // Erhalte die Ã¼bergebenen Daten aus dem Intent
        Intent intent = getIntent();
        terminId = intent.getIntExtra("termin_id", -1);
        String terminName = intent.getStringExtra("termin_name");
        String terminBeschreibung = intent.getStringExtra("termin_beschreibung");
        String terminPrio = intent.getStringExtra("termin_prio");
        terminDatum = intent.getStringExtra("termin_datum");

        terminPrioSpinner = findViewById(R.id.prioListeDetailsActivity);
        ArrayAdapter<CharSequence> prioListSpinnerAdapter =
                ArrayAdapter.createFromResource(this, R.array.numbers,
                        R.layout.selected_item);
        prioListSpinnerAdapter
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        terminPrioSpinner.setAdapter(prioListSpinnerAdapter);
        terminPrioSpinner.setSelection(getSpinnerIndex(terminPrio,
                getResources().getStringArray(R.array.numbers)));
        terminPrioSpinner.setOnItemSelectedListener(this);
        terminNameTextView.setText(terminName);
        terminBeschreibungTextView.setText(terminBeschreibung);
        buttonDatePicker.setText(terminDatum);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.backButtonAddActivity) {
            onBackPressed();
        } else if (id == R.id.editButton) {
            deleteCurrentAppointment();
            EditText terminNameEditText = findViewById(R.id.editTextTerminNameDetailsActivity);
            String terminName_new = terminNameEditText.getText().toString();
            EditText beschreibungEditText = findViewById(R.id.editTextBeschreibungDetailsActivity);
            String beschreibung_new = beschreibungEditText.getText().toString();
            String prio_new = String.valueOf(terminPrioSpinner.getSelectedItem());
            LocalDate datum = LocalDate.of(datePickerDialog.getDatePicker().getYear(),
                    datePickerDialog.getDatePicker().getMonth() + 1,
                    datePickerDialog.getDatePicker().getDayOfMonth());

            getHighestIDFromDB(LoginScreen.email, maxID -> {
                int saveCounter = maxID + 1;
                Termin termin = new Termin(terminName_new, beschreibung_new, prio_new,
                        datum, saveCounter, false);
                Log.d("Weeklyplanner", String.valueOf(termin));
                saveAppointment(LoginScreen.email, terminName_new, beschreibung_new,
                        prio_new, datum, termin.getId(), false);
                Intent intent = getIntent();
                int termin_montastag = intent.getIntExtra("termin_montastag", -1);
                if (termin_montastag < (MainActivity.currentDate.getDayOfMonth() - (MainActivity.currentDate.getDayOfWeek().getValue()-1))){
                    getSpecificTerminliste(8).add(termin);
                } else if (termin_montastag > (MainActivity.currentDate.getDayOfMonth() + (DayOfWeek.SUNDAY.getValue() - MainActivity.currentDate.getDayOfWeek().getValue()))){
                    getSpecificTerminliste(9).add(termin);
                }
                else {
                    getSpecificTerminliste(datum.getDayOfWeek().getValue()).add(termin);
                }
                SpecificDay.refresh_needed = true;
                onBackPressed();
            });
        } else if (id == R.id.deleteButton) {
            // TODO NEXT RELEASE: POPUP ARE YOU SURE
            deleteCurrentAppointment();
            onBackPressed();
        }
    }

    public void deleteCurrentAppointment() {
        Intent intent = getIntent();
        int termin_montastag = intent.getIntExtra("termin_montastag", -1);
        LocalDate date = LocalDate.parse(terminDatum);
        if (termin_montastag < (MainActivity.currentDate.getDayOfMonth() - (MainActivity.currentDate.getDayOfWeek().getValue()-1))){
            terminTag = 8;
        } else if (termin_montastag > (MainActivity.currentDate.getDayOfMonth() + (DayOfWeek.SUNDAY.getValue() - MainActivity.currentDate.getDayOfWeek().getValue()))){
            terminTag = 9;
        }
        else {
            terminTag = date.getDayOfWeek().getValue();
        }
        ArrayList<Termin> termine =  getSpecificTerminliste(terminTag);
        for (Termin termin : getSpecificTerminliste(terminTag)) {
            if (termin.getId() == terminId) {
                deleteAppointment(termin.getId(), LoginScreen.email);
                getSpecificTerminliste(terminTag).remove(termin);
                break;
            }
        }
    }

    private void initDatePicker() {
        DatePickerDialog.OnDateSetListener dateSetListener = (view, year, month, dayOfMonth) -> {
            month = month + 1;
            String date = makeDateString(dayOfMonth, month, year);
            buttonDatePicker.setText(date);
        };
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);
        int style = AlertDialog.THEME_HOLO_LIGHT;
        datePickerDialog = new DatePickerDialog(this, style, dateSetListener,
                day, month, year);
    }

    private String makeDateString(int dayOfMonth, int month, int year) {
        return dayOfMonth + " " + getMonthFormat(month) + " " + year;
    }

    private String getMonthFormat(int month) {
        switch (month) {
            case 1:
                return "Januar";
            case 2:
                return "Februar";
            case 3:
                return "März";
            case 4:
                return "April";
            case 5:
                return "Mai";
            case 6:
                return "Juni";
            case 7:
                return "Juli";
            case 8:
                return "August";
            case 9:
                return "September";
            case 10:
                return "Oktober";
            case 11:
                return "November";
            case 12:
                return "Dezember";
            default:
                return null;
        }
    }

    public void openDatePicker(View view) {
        datePickerDialog.show();
    }

    private String getTodaysDay() {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH) + 1;
        int day = cal.get(Calendar.DAY_OF_MONTH);
        return makeDateString(day, month, year);

    }
}