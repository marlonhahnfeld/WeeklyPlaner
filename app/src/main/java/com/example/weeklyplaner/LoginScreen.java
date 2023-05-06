package com.example.weeklyplaner;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class LoginScreen extends AppCompatActivity {

    TextView login_to_register_text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);

        login_to_register_text = (TextView) findViewById(R.id.login_to_register_text);

        login_to_register_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            Intent intent = new Intent(LoginScreen.this,RegisterScreen.class);
            startActivity(intent);
            }
        });




    }

}