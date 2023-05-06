package com.example.weeklyplaner;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Date;
import java.time.DayOfWeek;
import java.time.LocalDate;

import items.Termin;
import items.TerminListe;


public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private Button button_for_days;
    private ImageButton addButton;


    @RequiresApi(api = Build.VERSION_CODES.TIRAMISU)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button_for_days = findViewById(R.id.MoSoButton);
        button_for_days.setOnClickListener(this);

        addButton = findViewById(R.id.AddButton);
        addButton.setOnClickListener(this);


       // LocalDate currentDate = LocalDate.now();
       // DayOfWeek dayOfWeek = currentDate.getDayOfWeek();
        // Buttons generieren nach Erstellung
        if (getIntent().getFlags() == getIntent().FLAG_GRANT_READ_URI_PERMISSION){
        int buttonCount = getIntent().getIntExtra("buttonCount", 0);
        for (int i = 0; i < buttonCount; i++) {
            Button newButton = new Button(this);
            TerminListe terminListe = (getIntent().getSerializableExtra("terminliste",TerminListe.class));
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
//        Button myButton = new Button(context);
//        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(
//                RelativeLayout.LayoutParams.WRAP_CONTENT,
//                RelativeLayout.LayoutParams.WRAP_CONTENT
//        );
//        myButton.setLayoutParams(layoutParams);
//        myButton.setText("Button Text");
//        layoutParams.addRule(RelativeLayout.BELOW, terminListe.getLastTermin().getId());
//        button_for_days.getId();
//        myButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                // Code, der bei Klick auf den Button ausgeführt werden soll
//            }
//        });
//        layoutParams.addView(myButton);



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