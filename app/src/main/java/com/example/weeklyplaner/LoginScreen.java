package com.example.weeklyplaner;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class LoginScreen extends AppCompatActivity implements View.OnClickListener {

    TextView login_to_register_text;

    private Button senden_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);

        login_to_register_text = (TextView) findViewById(R.id.login_to_register_text);

        senden_button = findViewById(R.id.button_login_screen);
        senden_button.setOnClickListener(this);

        login_to_register_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            Intent intent = new Intent(LoginScreen.this,RegisterScreen.class);
            startActivity(intent);
            }
        });




    }

    @Override
    public void onClick(View v) {
        Intent intent;
        int id = v.getId();

        if (id == R.id.button_login_screen) {
            intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        } else if (id == R.id.SortButton) {
            onBackPressed();
        }
    }

}