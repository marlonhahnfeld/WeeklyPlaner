package com.example.weeklyplaner;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class MoSo extends AppCompatActivity {
    private ImageButton imageButton;
    private Button button1;
    private Button button2;
    private Button button3;
    private Button button4;
    private Button button5;
    private Button button6;
    private Button button7;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mo_so);

        imageButton = findViewById(R.id.SortButton);
        imageButton.setOnClickListener(v -> openBack());

        button1 = findViewById(R.id.Montag);
        button2 = findViewById(R.id.Dienstag);
        button3 = findViewById(R.id.Mittwoch);
        button4 = findViewById(R.id.Donnerstag);
        button5 = findViewById(R.id.Freitag);
        button6 = findViewById(R.id.Samstag);
        button7 = findViewById(R.id.Sonntag);

        button1.setOnClickListener(v -> openSpecificDay());
        button2.setOnClickListener(v -> openSpecificDay());
        button3.setOnClickListener(v -> openSpecificDay());
        button4.setOnClickListener(v -> openSpecificDay());
        button5.setOnClickListener(v -> openSpecificDay());
        button6.setOnClickListener(v -> openSpecificDay());
        button7.setOnClickListener(v -> openSpecificDay());
    }

    public void openBack(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void openSpecificDay(){
        Intent intent = new Intent(this, SpecificDay.class);
        startActivity(intent);
    }

}