package com.example.weeklyplaner;

import static com.example.weeklyplaner.DatabaseOp.getHighestID;
import static com.example.weeklyplaner.DatabaseOp.saveAppointment;
import static com.example.weeklyplaner.Utils.getSpecificTerminliste;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Calendar;

import items.Termin;

public class Add extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {

    private ImageButton backButton;
    private Button saveButton;
    private Spinner prioListSpinner, daySpinner;

    public static int saveCounter;
    private DatePickerDialog datePickerDialog;
    private Button buttonDatePicker;

    private static DatabaseOp databaseOp;


    //TOD
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        databaseOp = new DatabaseOp();

        buttonDatePicker = findViewById(R.id.datepicker);
        initDatePicker();
        //buttonDatePicker.setText(getTodaysDay());
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, LocalDate.now().getDayOfMonth() - (LocalDate.now().getDayOfWeek().getValue() - DayOfWeek.MONDAY.getValue()));
        calendar.set(Calendar.MONTH, LocalDate.now().getMonthValue() - 1);
        calendar.set(Calendar.YEAR, LocalDate.now().getYear());
        //default ausgewähltes datum beim Öffnen von datum für den datum spinner
        datePickerDialog.getDatePicker().setMinDate(calendar.getTimeInMillis());


        backButton = findViewById(R.id.imageButton);
        backButton.setOnClickListener(this);
        saveButton = findViewById(R.id.SaveButton);
        saveButton.setOnClickListener(this);

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

            getHighestID(LoginScreen.email, maxID -> {
                int saveCounter = maxID + 1;
                LocalDate datum = LocalDate.of(datePickerDialog.getDatePicker().getYear(),
                        datePickerDialog.getDatePicker().getMonth() + 1,
                        datePickerDialog.getDatePicker().getDayOfMonth());
                Termin termin = new Termin(terminName, beschreibung, prio, datum, saveCounter);
                Log.d("Weeklyplanner", String.valueOf(termin));
                saveAppointment(LoginScreen.email, terminName, beschreibung, prio, datum,
                        termin.getId());
                getSpecificTerminliste(datum.getDayOfWeek().getValue()).add(termin);
                SpecificDay.refresh_needed = true;
                onBackPressed();
            });
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