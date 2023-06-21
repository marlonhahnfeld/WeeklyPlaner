package com.example.weeklyplaner;

import androidx.appcompat.app.AppCompatActivity;

import java.time.LocalDate;
import java.time.temporal.WeekFields;
import java.util.ArrayList;
import java.util.Locale;

import items.Termin;

public class Utils extends AppCompatActivity {
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    public static ArrayList<Termin> getSpecificTerminliste(int wochenTag) {
        switch (wochenTag) {
            case 1:
                return (MainActivity.montag_terminliste);
            case 2:
                return (MainActivity.dienstag_terminliste);
            case 3:
                return (MainActivity.mittwoch_terminliste);
            case 4:
                return (MainActivity.donnerstag_terminliste);
            case 5:
                return (MainActivity.freitag_terminliste);
            case 6:
                return (MainActivity.samstag_terminliste);
            case 7:
                return (MainActivity.sonntag_terminliste);
            default:
                System.err.println("Something went wrong");
                return null;
        }
    }

    public static ArrayList<Termin> getSpecificTerminlisteInCurrentWeek(int wochenTag) {
        ArrayList<Termin> terminliste = getSpecificTerminliste(wochenTag);
        ArrayList<Termin> terminlisteInCurrentWeek = new ArrayList<>();

        int currentWeek = getCurrentCalendarWeek();

        for (Termin termin : terminliste) {
            if (termin.getWeek() == currentWeek) {
                terminlisteInCurrentWeek.add(termin);
            }
        }

        return terminlisteInCurrentWeek;
    }

    private static int getCurrentCalendarWeek() {
        LocalDate currentDate = LocalDate.now();
        WeekFields weekFields = WeekFields.of(Locale.getDefault());
        return currentDate.get(weekFields.weekOfWeekBasedYear());
    }

}
