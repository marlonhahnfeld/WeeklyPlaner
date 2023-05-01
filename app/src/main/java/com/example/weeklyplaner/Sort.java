package com.example.weeklyplaner;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

public class Sort extends AppCompatActivity implements View.OnClickListener {
    private ImageButton BackButton;
    private ImageButton addButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sort);

        BackButton = findViewById(R.id.SortButton);
        BackButton.setOnClickListener(this);
        addButton = findViewById(R.id.AddButton);
        addButton.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        Intent intent;
        int id = v.getId();

        if (id == R.id.SortButton) {
            onBackPressed();
        } else if (id == R.id.AddButton) {
            intent = new Intent(this, Add.class);
            startActivity(intent);
        }
    }
}