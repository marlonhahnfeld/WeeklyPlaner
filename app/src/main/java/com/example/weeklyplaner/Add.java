package com.example.weeklyplaner;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class Add extends AppCompatActivity {

    private ImageButton imageButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        imageButton = findViewById(R.id.imageButton);
        imageButton.setOnClickListener(v -> openBack());
    }
//hi
    public void openBack(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}