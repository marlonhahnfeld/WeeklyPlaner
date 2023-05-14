package com.example.weeklyplaner;


import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;


public class Utils extends AppCompatActivity {

    // When back button is pressed, finish current activity and go back to previous activity

    /**
     * Methode zum beenden des aktuellen Threads und somitgen Rückkehr zum vorherigen
     */
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }


}
