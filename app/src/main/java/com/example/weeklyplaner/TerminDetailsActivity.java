package com.example.weeklyplaner;

import static com.example.weeklyplaner.DatabaseOp.deleteAppointment;
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
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.time.LocalDate;
import java.util.Calendar;

import items.Termin;

public class TerminDetailsActivity extends AppCompatActivity implements View.OnClickListener,
        AdapterView.OnItemSelectedListener {
    private EditText terminNameTextView;
    private EditText terminBeschreibungTextView;
    private Spinner terminPrioSpinner;
    private ImageButton backButton;
    private Button editButton, deleteBtton;
    private int terminId;
    // private String terminTag;
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

        backButton = findViewById(R.id.imageButton);
        backButton.setOnClickListener(this);
        editButton = findViewById(R.id.EditButton_details);
        editButton.setOnClickListener(this);
        deleteBtton = findViewById(R.id.DeleteButton_details);
        deleteBtton.setOnClickListener(this);

        buttonDatePicker = findViewById(R.id.datepicker);
        initDatePicker();
        buttonDatePicker.setText(getTodaysDay());
        Log.d("TerminDetails", buttonDatePicker.getText().toString());
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, LocalDate.now().getDayOfMonth());
        calendar.set(Calendar.MONTH, LocalDate.now().getMonthValue() - 1);
        calendar.set(Calendar.YEAR, LocalDate.now().getYear());
        //default ausgewähltes datum beim öffnen von datum für den datum spinner
        datePickerDialog.getDatePicker().setMinDate(calendar.getTimeInMillis());

        // Finde die TextViews in deinem Layout
        terminNameTextView = findViewById(R.id.Terminname_edit_text_details);
        terminBeschreibungTextView = findViewById(R.id.Beschreibung_edit_text_details);

        // Erhalte die Ã¼bergebenen Daten aus dem Intent
        Intent intent = getIntent();
        terminId = intent.getIntExtra("termin_id", -1);
        String terminName = intent.getStringExtra("termin_name");
        String terminBeschreibung = intent.getStringExtra("termin_beschreibung");
        String terminPrio = intent.getStringExtra("termin_prio");
        String terminDatum = intent.getStringExtra("termin_datum");
        //terminTag = intent.getIntExtra("termin_tag", 0);


        terminPrioSpinner = findViewById(R.id.PrioListe_details);
        ArrayAdapter<CharSequence> prioListSpinnerAdapter = ArrayAdapter.createFromResource(this,
                R.array.numbers, android.R.layout.simple_spinner_item);
        prioListSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        terminPrioSpinner.setAdapter(prioListSpinnerAdapter);
        terminPrioSpinner.setSelection(getSpinnerIndex(terminPrio, getResources().getStringArray(R.array.numbers)));
        terminPrioSpinner.setOnItemSelectedListener(this);
        terminNameTextView.setText(terminName);
        terminBeschreibungTextView.setText(terminBeschreibung);
        buttonDatePicker.setText(terminDatum);
        Log.d("TerminDetails", buttonDatePicker.getText().toString());
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
            LocalDate datum = LocalDate.of(datePickerDialog.getDatePicker().getYear(), datePickerDialog.getDatePicker().getMonth() + 1, datePickerDialog.getDatePicker().getDayOfMonth());

            Add.saveCounter = Add.saveCounter + 1;

            Termin termin = new Termin(terminName_new, beschreibung_new, prio_new, datum, Add.saveCounter);


            saveAppointment(LoginScreen.email, terminName_new, beschreibung_new, prio_new,
                    datum, termin.getId());

            //if (tag_new != terminTag) {
            // zur richtigen liste zuordnen
            getSpecificTerminliste(datum.getDayOfWeek().getValue()).add(termin);
            // } else {
            //    getSpecificTerminliste(terminDatum).add(termin);
            // }
            onBackPressed();
            // TODO NEXT RELEASE: POPUP ARE YOU SURE
        } else if (id == R.id.DeleteButton_details) {
            deleteCurrentTermin();
            onBackPressed();
        }
    }

    public void deleteCurrentTermin() {
        // LocalDate datum = LocalDate.of(datePickerDialog.getDatePicker().getYear(), datePickerDialog.getDatePicker().getMonth() + 1, datePickerDialog.getDatePicker().getDayOfMonth());
        for (Termin termin : getSpecificTerminliste(terminTag)) {
            if (termin.getId() == terminId) {
                deleteAppointment(termin.getId(), LoginScreen.email);
                getSpecificTerminliste(terminTag).remove(termin);
                break;
            }
        }
    }

    private void initDatePicker() {
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month = month + 1;
                String date = makeDateString(dayOfMonth, month, year);
                buttonDatePicker.setText(date);

            }
        };
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);


        int style = AlertDialog.THEME_HOLO_LIGHT;

        datePickerDialog = new DatePickerDialog(this, style, dateSetListener, day, month, year);


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