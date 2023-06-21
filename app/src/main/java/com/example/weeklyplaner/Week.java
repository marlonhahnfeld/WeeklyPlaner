package com.example.weeklyplaner;

import static com.example.weeklyplaner.Utils.getSpecificTerminlisteInCurrentWeek;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class Week extends AppCompatActivity implements View.OnClickListener {
    private ImageButton backButton;
    private TextView weekDate;
    private TimeZone german_timezone = TimeZone.getTimeZone("Europe/Berlin");
    private Calendar calendar = Calendar.getInstance(german_timezone);
    private int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
    public static Button[] buttons = new Button[7];
    public static TextView[] doneTextViews = new TextView[7];


    private Button openAppointments_button;
    private Button futureAppointments_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_week);

        weekDate = findViewById(R.id.weekDate);
        backButton = findViewById(R.id.backButtonWeekActivity);
        backButton.setOnClickListener(this);

        weekDate.setText(updateWeekDateText());

        openAppointments_button = findViewById(R.id.openAppointments);
        openAppointments_button.setOnClickListener(this);

        futureAppointments_button = findViewById(R.id.futureAppointments);
        futureAppointments_button.setOnClickListener(this);



        int[] buttonIds = {R.id.montag, R.id.dienstag, R.id.mittwoch, R.id.donnerstag,
                R.id.freitag, R.id.samstag, R.id.sonntag};
        int[] doneIds = {R.id.doneMo, R.id.doneDi, R.id.doneMi, R.id.doneDo,
                R.id.doneFr, R.id.doneSa, R.id.doneSo};

        for (int i = 0; i < 7; i++) {
            buttons[i] = findViewById(buttonIds[i]);
            buttons[i].setOnClickListener(this);
            doneTextViews[i] = findViewById(doneIds[i]);
        }


        for (int i = 0; i < 7; i++) {
            animateButtonFromRight(buttons[i], doneTextViews[i], i);
        }

        animateButtonFromRight(findViewById(R.id.openAppointments));

        highlightTodayButton();
    }

    private String updateWeekDateText() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.", Locale.getDefault());

        calendar.set(Calendar.DAY_OF_WEEK, calendar.getFirstDayOfWeek() + 1);
        Date firstDate = calendar.getTime();

        calendar.add(Calendar.DAY_OF_WEEK, 6);
        Date lastDate = calendar.getTime();

        return dateFormat.format(firstDate) + " - " + dateFormat.format(lastDate);
    }

    /**
     * Methode um die Buttons mit ihrem Text aus dem Array von rechts einfliegen zu lassen
     *
     * @param button   der Button der animiert eingeflogen werden soll
     * @param doneText die Anzahl der erledigten Tasks die mit dem Button zsm. kommen
     * @param delay    der Wert der Verzögerung, sodass eine Treppe entstehen kann
     */
    private void animateButtonFromRight(View button, TextView doneText, int delay) {
        int screenWidth = getResources().getDisplayMetrics().widthPixels;
        button.setTranslationX(screenWidth);
        doneText.setTranslationX(screenWidth);

        ObjectAnimator buttonAnimator =
                ObjectAnimator.ofFloat(button, "translationX", 0f);
        ObjectAnimator doneTextAnimator =
                ObjectAnimator.ofFloat(doneText, "translationX", 0f);

        int animationDuration = 500 + (delay * 100);
        buttonAnimator.setDuration(animationDuration);
        doneTextAnimator.setDuration(animationDuration);

        buttonAnimator.start();
        doneTextAnimator.start();
    }

    private void animateButtonFromRight(View button) {
        button.setTranslationX(getResources().getDisplayMetrics().widthPixels);
        ObjectAnimator animator =
                ObjectAnimator.ofFloat(button, "translationX", 0f);
        animator.setDuration(1200);
        animator.start();
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateTextViews();
    }

    private void updateTextViews() {
        for (int i = 0; i < 7; i++) {
            String doneText = "Done: " + Termin_RecyclerView_Adapter.howManyDone(i + 1)
                    + "/" + howManyItemsIn(i + 1);
            doneTextViews[i].setText(doneText);
        }
    }

    public static int howManyItemsIn(int day) {
        return getSpecificTerminlisteInCurrentWeek(day).size();
    }

    private void highlightTodayButton() {
        for (int i = 0; i < buttons.length; i++) {
            if (dayOfWeek == (Calendar.MONDAY + i)) {
                buttons[i].setBackgroundColor(Color.parseColor("#7b68ee"));
                break;
            } else {
                buttons[i].setBackgroundColor(Color.LTGRAY);
            }
        }
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        int id = v.getId();
        if (id == R.id.montag || id == R.id.dienstag || id == R.id.mittwoch ||
                id == R.id.donnerstag || id == R.id.freitag || id == R.id.samstag ||
                id == R.id.sonntag) {
            intent = new Intent(this, SpecificDay.class);
            Button clickedButton = findViewById(id);
            intent.putExtra("button_text", clickedButton.getText());
            startActivity(intent);
        } else if (id == R.id.backButtonWeekActivity) {
            onBackPressed();
        } else if (id == R.id.openAppointments) {
            intent = new Intent(this, SpecificDay.class);
            intent.putExtra("button_text", openAppointments_button.getText());
            startActivity(intent);
        } else if (id == R.id.futureAppointments) {
            intent = new Intent(this, SpecificDay.class);
            intent.putExtra("button_text", futureAppointments_button.getText());
            startActivity(intent);
        }
    }
}