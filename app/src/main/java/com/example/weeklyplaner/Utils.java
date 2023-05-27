package com.example.weeklyplaner;


import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

import items.Termin;


public class Utils extends AppCompatActivity {

    // When back button is pressed, finish current activity and go back to previous activity

    /**
     * Methode zum Beenden des aktuellen Threads und somit die Rückkehr zum vorherigen
     */
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    public static ArrayList<Termin> getSpecificTerminliste(String tag) {
        switch (tag) {
            case "Montag":
                return (MainActivity.montag_terminliste);
            case "Dienstag":
                return (MainActivity.dienstag_terminliste);
            case "Mittwoch":
                return (MainActivity.mittwoch_terminliste);
            case "Donnerstag":
                return (MainActivity.donnerstag_terminliste);
            case "Freitag":
                return (MainActivity.freitag_terminliste);
            case "Samstag":
                return (MainActivity.samstag_terminliste);
            case "Sonntag":
                return (MainActivity.sonntag_terminliste);
            // Weitere Cases für andere Termin-IDs
            default:
                break;
        }
        return null;
    }


}
