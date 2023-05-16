package com.example.weeklyplaner;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RelativeLayout;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.TimeZone;

import items.Termin;
import items.TerminListe;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Button button_for_days;
    private ImageButton addButton;
    private RecyclerView recyclerView;
    public static ArrayList<Termin> montag_terminliste = new ArrayList<>();
    public static ArrayList<Termin> dienstag_terminliste = new ArrayList<>();
    public static ArrayList<Termin> mittwoch_terminliste = new ArrayList<>();
    public static ArrayList<Termin> donnerstag_terminliste = new ArrayList<>();
    public static ArrayList<Termin> freitag_terminliste = new ArrayList<>();
    public static ArrayList<Termin> samstag_terminliste = new ArrayList<>();
    public static ArrayList<Termin> sonntag_terminliste = new ArrayList<>();
    Termin_RecyclerView_Adapter adapter;
    TimeZone german_timezone = TimeZone.getTimeZone("Europe/Berlin");
    Calendar calendar = Calendar.getInstance(german_timezone);

    // REFRESH PAGE CODE
    @Override
    protected void onResume() {
        super.onResume();

        // Refresh the activity here
        refreshMainActivity();
    }

    private void refreshMainActivity() {
        // Refresh the specific day's data here
        // For example, update the RecyclerView adapter with the latest data
        if (calendar.get(Calendar.DAY_OF_WEEK) == 2) {
            adapter = new Termin_RecyclerView_Adapter(this, MainActivity.montag_terminliste);
            recyclerView.setAdapter(adapter);
        } else if (calendar.get(Calendar.DAY_OF_WEEK) == 3) {
            adapter = new Termin_RecyclerView_Adapter(this, MainActivity.dienstag_terminliste);
            recyclerView.setAdapter(adapter);
        } else if (calendar.get(Calendar.DAY_OF_WEEK) == 4) {
            adapter = new Termin_RecyclerView_Adapter(this, MainActivity.mittwoch_terminliste);
            recyclerView.setAdapter(adapter);
        } else if (calendar.get(Calendar.DAY_OF_WEEK) == 5) {
            adapter = new Termin_RecyclerView_Adapter(this, MainActivity.donnerstag_terminliste);
            recyclerView.setAdapter(adapter);
        } else if (calendar.get(Calendar.DAY_OF_WEEK) == 6) {
            adapter = new Termin_RecyclerView_Adapter(this, MainActivity.freitag_terminliste);
            recyclerView.setAdapter(adapter);
        } else if (calendar.get(Calendar.DAY_OF_WEEK) == 7) {
            adapter = new Termin_RecyclerView_Adapter(this, MainActivity.samstag_terminliste);
            recyclerView.setAdapter(adapter);
        } else if (calendar.get(Calendar.DAY_OF_WEEK) == 1) {
            adapter = new Termin_RecyclerView_Adapter(this, MainActivity.sonntag_terminliste);
            recyclerView.setAdapter(adapter);
        }
    }
//

    @RequiresApi(api = Build.VERSION_CODES.TIRAMISU)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button_for_days = findViewById(R.id.MoSoButton);
        button_for_days.setOnClickListener(this);

        addButton = findViewById(R.id.AddButton);
        addButton.setOnClickListener(this);

        // Log.v("HIERERERERER", "ARE WEEEEEEEEEEEEEEEEEEEEEEE");
        //here

        // setUpTermine();
        recyclerView = findViewById(R.id.TerminlisteRecyclerView);

        if (calendar.get(Calendar.DAY_OF_WEEK) == 1) {
            adapter = new Termin_RecyclerView_Adapter(this, sonntag_terminliste);
        } else if (calendar.get(Calendar.DAY_OF_WEEK) == 2) {
            adapter = new Termin_RecyclerView_Adapter(this, montag_terminliste);

        } else if (calendar.get(Calendar.DAY_OF_WEEK) == 3) {
            adapter = new Termin_RecyclerView_Adapter(this, dienstag_terminliste);

        } else if (calendar.get(Calendar.DAY_OF_WEEK) == 4) {
            adapter = new Termin_RecyclerView_Adapter(this, mittwoch_terminliste);

        } else if (calendar.get(Calendar.DAY_OF_WEEK) == 5) {
            adapter = new Termin_RecyclerView_Adapter(this, donnerstag_terminliste);

        } else if (calendar.get(Calendar.DAY_OF_WEEK) == 6) {
            adapter = new Termin_RecyclerView_Adapter(this, freitag_terminliste);

        } else {
            adapter = new Termin_RecyclerView_Adapter(this, samstag_terminliste);

        }
        recyclerView.setAdapter(adapter);
        // recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        // Buttons generieren nach Erstellung
        if (getIntent().getFlags() == getIntent().FLAG_GRANT_READ_URI_PERMISSION) {
            int buttonCount = getIntent().getIntExtra("buttonCount", 0);
            for (int i = 0; i < buttonCount; i++) {
                Button newButton = new Button(this);
                TerminListe terminListe = (getIntent().getSerializableExtra("terminliste", TerminListe.class));
                newButton.setText(terminListe.getLastTermin().getTerminname());
                RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                        RelativeLayout.LayoutParams.WRAP_CONTENT,
                        RelativeLayout.LayoutParams.WRAP_CONTENT
                );
                RelativeLayout relativeLayout = findViewById(R.id.MainActivityLayout);
                params.addRule(RelativeLayout.BELOW, relativeLayout.getChildAt(i - 1).getId());
                this.addContentView(newButton, params);
            }
        }


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