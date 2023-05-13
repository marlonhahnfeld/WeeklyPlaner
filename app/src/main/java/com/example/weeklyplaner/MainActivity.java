package com.example.weeklyplaner;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RelativeLayout;

import java.util.ArrayList;

import items.Termin;
import items.TerminListe;


public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private Button button_for_days;
    private ImageButton addButton;
    private RecyclerView recyclerView;
    public static ArrayList<Termin> heute_terminliste = new ArrayList<>();


    @RequiresApi(api = Build.VERSION_CODES.TIRAMISU)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button_for_days = findViewById(R.id.MoSoButton);
        button_for_days.setOnClickListener(this);

        addButton = findViewById(R.id.AddButton);
        addButton.setOnClickListener(this);

        Log.v("HIERERERERER", "ARE WEEEEEEEEEEEEEEEEEEEEEEE");
        //here

       // setUpTermine();
        recyclerView = findViewById(R.id.TerminlisteRecyclerView);
        Termin_RecyclerView_Adapter adapter = new Termin_RecyclerView_Adapter(this, heute_terminliste);
        recyclerView.setAdapter(adapter);
       // recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


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

    private void setUpTermine(){
        heute_terminliste.add(new Termin("Termin2", "hallo2","2",2));
        heute_terminliste.add(new Termin("Termin3", "hallo3","3",3));
        heute_terminliste.add(new Termin("Termin1", "hallo1","1",1));
    }

}