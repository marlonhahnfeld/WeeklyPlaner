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

import java.util.Calendar;
import java.util.TimeZone;

import items.Termin;

public class Week extends AppCompatActivity implements View.OnClickListener {
    private ImageButton BackButton;
    TimeZone german_timezone = TimeZone.getTimeZone("Europe/Berlin");
    Calendar calendar = Calendar.getInstance(german_timezone);
    int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
    public static Button[] buttons = new Button[7];
    public static TextView[] doneTextViews = new TextView[7];
    public static TextView[] notDoneTextViews = new TextView[7];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_week);

        BackButton = findViewById(R.id.backButtonWeekActivity);
        BackButton.setOnClickListener(this);

        int[] buttonIds = {R.id.montag, R.id.dienstag, R.id.mittwoch, R.id.donnerstag,
                R.id.freitag, R.id.samstag, R.id.sonntag};
        int[] doneIds = {R.id.doneMo, R.id.doneDi, R.id.doneMi, R.id.doneDo,
                R.id.doneFr, R.id.doneSa, R.id.doneSo};
        int[] notDoneIds = {R.id.notDoneMo, R.id.notDoneDi, R.id.notDoneMi, R.id.notDoneDo,
                R.id.notDoneFr, R.id.notDoneSa, R.id.notDoneSo};

        for (int i = 0; i < 7; i++) {
            buttons[i] = findViewById(buttonIds[i]);
            buttons[i].setOnClickListener(this);
            doneTextViews[i] = findViewById(doneIds[i]);
            notDoneTextViews[i] = findViewById(notDoneIds[i]);
        }

        for (int i = 0; i < 7; i++) {
            String doneText;
            String notDoneText;
            doneText = "Done: " + howManyDone(i + 1);
            notDoneText = "Not Done: " + (howManyItemsIn(i + 1) -
                    howManyDone(i + 1));
            doneTextViews[i].setText(doneText);
            notDoneTextViews[i].setText(notDoneText);
        }

        for (int i = 0; i < 7; i++) {
            animateButtonFromRight(buttons[i], doneTextViews[i], notDoneTextViews[i], i);
        }

        animateButtonFromRight(findViewById(R.id.openAppointments));

        highlightTodayButton();
    }

    /**
     * Methode um die Buttons mit ihrem Text aus dem Array von rechts einfliegen zu lassen
     *
     * @param button      der Button der animiert eingeflogen werden soll
     * @param doneText    die Anzahl der erledigten Tasks die mit dem Button zsm. kommen
     * @param notDoneText die Anzahl der nicht erledigten Tasks die mit dem Button zsm. kommen
     * @param delay       der Wert der Verzögerung, sodass eine Treppe entstehen kann
     */
    private void animateButtonFromRight(View button, TextView doneText, TextView notDoneText,
                                        int delay) {
        int screenWidth = getResources().getDisplayMetrics().widthPixels;
        button.setTranslationX(screenWidth);
        doneText.setTranslationX(screenWidth);
        notDoneText.setTranslationX(screenWidth);

        ObjectAnimator buttonAnimator =
                ObjectAnimator.ofFloat(button, "translationX", 0f);
        ObjectAnimator doneTextAnimator =
                ObjectAnimator.ofFloat(doneText, "translationX", 0f);
        ObjectAnimator notDoneTextAnimator =
                ObjectAnimator.ofFloat(notDoneText, "translationX", 0f);

        int animationDuration = 500 + (delay * 100);
        buttonAnimator.setDuration(animationDuration);
        doneTextAnimator.setDuration(animationDuration);
        notDoneTextAnimator.setDuration(animationDuration);

        buttonAnimator.start();
        doneTextAnimator.start();
        notDoneTextAnimator.start();
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
            String doneText = "Done: " + Termin_RecyclerView_Adapter.howManyDone(i + 1);
            String notDoneText = "Not Done: " +
                    (Termin_RecyclerView_Adapter.howManyItemsIn(i + 1) -
                            Termin_RecyclerView_Adapter.howManyDone(i + 1));
            doneTextViews[i].setText(doneText);
            notDoneTextViews[i].setText(notDoneText);
        }
    }

    public static int howManyItemsIn(int day) {
        return getSpecificTerminlisteInCurrentWeek(day).size();
    }

    public static int howManyDone(int day) {
        int done = 0;
        for (Termin t : getSpecificTerminlisteInCurrentWeek(day)) {
            if (t.isChecked()) {
                done++;
            }
        }
        return done;
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
        }
    }
}