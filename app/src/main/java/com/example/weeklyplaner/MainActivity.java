package com.example.weeklyplaner;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.TimeZone;

import items.Termin;


public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private Button button_for_days;
    private ImageButton addButton;
    private RecyclerView recyclerView;
    private static ArrayList<Termin>[] terminListe = new ArrayList[7];
    private Termin_RecyclerView_Adapter adapter;
    private Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("Europe/Berlin"));
    public static ArrayList<Termin> montag_terminliste = new ArrayList<>();
    public static ArrayList<Termin> dienstag_terminliste = new ArrayList<>();
    public static ArrayList<Termin> mittwoch_terminliste = new ArrayList<>();
    public static ArrayList<Termin> donnerstag_terminliste = new ArrayList<>();
    public static ArrayList<Termin> freitag_terminliste = new ArrayList<>();
    public static ArrayList<Termin> samstag_terminliste = new ArrayList<>();
    public static ArrayList<Termin> sonntag_terminliste = new ArrayList<>();


    // REFRESH PAGE CODE
    @Override
    protected void onResume() {
        super.onResume();
        refreshMainActivity();
    }

    private void refreshMainActivity() {
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
        adapter = new Termin_RecyclerView_Adapter(this, terminListe[dayOfWeek - 1]);
        recyclerView.setAdapter(adapter);
    }

    @RequiresApi(api = Build.VERSION_CODES.TIRAMISU)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button_for_days = findViewById(R.id.MoSoButton);
        button_for_days.setOnClickListener(this);

        addButton = findViewById(R.id.AddButton);
        addButton.setOnClickListener(this);
        recyclerView = findViewById(R.id.TerminlisteRecyclerView);

        terminListe[0] = sonntag_terminliste;
        terminListe[1] = montag_terminliste;
        terminListe[2] = dienstag_terminliste;
        terminListe[3] = mittwoch_terminliste;
        terminListe[4] = donnerstag_terminliste;
        terminListe[5] = freitag_terminliste;
        terminListe[6] = samstag_terminliste;

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        refreshMainActivity();
    }


    @Override
    public void onClick(View v) {
        Intent intent;
        int id = v.getId();

        if (id == R.id.MoSoButton) {
            intent = new Intent(this, Week.class);
            startActivity(intent);
        } else if (id == R.id.AddButton) {
            intent = new Intent(this, Add.class);
            startActivity(intent);
        }
    }



}