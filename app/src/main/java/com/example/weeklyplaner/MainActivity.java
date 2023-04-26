package com.example.weeklyplaner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;


public class MainActivity extends AppCompatActivity {
    private Button button;
    private ImageButton imageButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button = findViewById(R.id.MoSoButton);
        button.setOnClickListener(v -> openMoSo());

        imageButton = findViewById(R.id.AddButton);
        imageButton.setOnClickListener(v -> openAdd());

        imageButton = findViewById(R.id.SortButton);
        imageButton.setOnClickListener(v -> openSort());
    }

    public void openMoSo(){
        Intent intent = new Intent(this, MoSo.class);
        startActivity(intent);
    }

    public void openAdd(){
        Intent intent = new Intent(this, Add.class);
        startActivity(intent);
    }

    public void openSort(){
        Intent intent = new Intent(this, Sort.class);
        startActivity(intent);
    }

}