package com.example.weeklyplaner;


import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

import items.Termin;


public class Utils extends AppCompatActivity {
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();}

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
            // Weitere Cases für andere Termin-IDs
            default:
                System.err.println("Something went wrong"); break;
        }
        return null;
    }


}
