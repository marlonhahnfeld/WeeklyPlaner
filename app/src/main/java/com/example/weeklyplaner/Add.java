package com.example.weeklyplaner;

import static com.example.weeklyplaner.DatabaseOp.getHighestIDFromDB;
import static com.example.weeklyplaner.DatabaseOp.saveAppointment;
import static com.example.weeklyplaner.Utils.getSpecificTerminliste;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
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
import java.util.ArrayList;
import java.util.Calendar;

import items.Termin;

// TODO: NULL Werte abfangen
public class Add extends AppCompatActivity implements View.OnClickListener,
        AdapterView.OnItemSelectedListener {
    private ImageButton backButton;
    private Button saveButton;
    private Spinner prioListSpinner;
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

        buttonDatePicker = findViewById(R.id.buttonDatepickerDetailsActivity);
        initDatePicker();
        //buttonDatePicker.setText(getTodaysDay());
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, LocalDate.now().getDayOfMonth() -
                (LocalDate.now().getDayOfWeek().getValue() - DayOfWeek.MONDAY.getValue()));
        calendar.set(Calendar.MONTH, LocalDate.now().getMonthValue() - 1);
        calendar.set(Calendar.YEAR, LocalDate.now().getYear());
        //default ausgewähltes datum beim Öffnen von datum für den datum spinner
        datePickerDialog.getDatePicker().setMinDate(calendar.getTimeInMillis());

        backButton = findViewById(R.id.backButtonDetailsActivity);
        backButton.setOnClickListener(this);
        saveButton = findViewById(R.id.saveButton);
        saveButton.setOnClickListener(this);

        prioListSpinner = findViewById(R.id.prioListe);
        ArrayAdapter<CharSequence> prioListSpinnerAdapter =
                ArrayAdapter.createFromResource(this, R.array.numbers,
                        android.R.layout.simple_spinner_item);
        prioListSpinnerAdapter
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
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
        ArrayList<Termin>[] terminListe = MainActivity.terminListe;
        LocalDate currentDate = LocalDate.now();
        if (id == R.id.backButtonDetailsActivity) {
            onBackPressed();
        } else if (id == R.id.saveButton) {
            EditText terminNameEditText = findViewById(R.id.editTextTerminName);
            String terminName = terminNameEditText.getText().toString();
            EditText beschreibungEditText = findViewById(R.id.editTextBeschreibung);
            String beschreibung = beschreibungEditText.getText().toString();
            String prio = String.valueOf(prioListSpinner.getSelectedItem());

            getHighestIDFromDB(LoginScreen.email, maxID -> {
                int saveCounter = maxID + 1;
                LocalDate datum = LocalDate.of(datePickerDialog.getDatePicker().getYear(),
                        datePickerDialog.getDatePicker().getMonth() + 1,
                        datePickerDialog.getDatePicker().getDayOfMonth());
                Termin termin = new Termin(terminName, beschreibung, prio, datum, saveCounter,
                        false);
                Log.d("Weeklyplanner", String.valueOf(termin));
                saveAppointment(LoginScreen.email, terminName, beschreibung, prio, datum,
                        termin.getId(), false);
                if (/* Fall 1: Tag kleiner  */  ((((datum.getDayOfMonth() < (currentDate.getDayOfMonth() - (currentDate.getDayOfWeek().getValue() - 1)))) && ((((datum.getMonth().getValue()) <= currentDate.getMonth().getValue()) && (datum.getYear() <= currentDate.getYear())) || datum.getYear() < currentDate.getYear())) ||
                        /* Fall 2: Monat kleiner */  ((datum.getMonth().getValue() < currentDate.getMonth().getValue()) && (datum.getYear() <= currentDate.getYear())) ||
                        /* Fall 3: Jahr kleiner */  (datum.getYear() < currentDate.getYear()))) {
                    getSpecificTerminliste(8).add(termin);
                } else if ((    /* Fall 1: Tag größer  */  ((((datum.getDayOfMonth() > (currentDate.getDayOfMonth() + (DayOfWeek.SUNDAY.getValue() - currentDate.getDayOfWeek().getValue())))) && ((((datum.getMonth().getValue()) >= currentDate.getMonth().getValue()) && (datum.getYear() >= currentDate.getYear())) || datum.getYear() > currentDate.getYear())) ||
                        /* Fall 2: Monat größer */  ((datum.getMonth().getValue() > currentDate.getMonth().getValue()) && (datum.getYear() >= currentDate.getYear())) ||
                        /* Fall 3: Jahr größer */  (datum.getYear() > currentDate.getYear()))
                )) {
                    getSpecificTerminliste(9).add(termin);
                } else {
                    getSpecificTerminliste(datum.getDayOfWeek().getValue()).add(termin);
                }
                SpecificDay.refresh_needed = true;
                onBackPressed();
            });
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (v instanceof EditText) {
                Rect outRect = new Rect();
                v.getGlobalVisibleRect(outRect);
                if (!outRect.contains((int) event.getRawX(), (int) event.getRawY())) {
                    v.clearFocus();
                    InputMethodManager imm =
                            (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            }
        }
        return super.onTouchEvent(event);
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

        datePickerDialog =
                new DatePickerDialog(this, style, dateSetListener, day, month, year);
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