package com.example.weeklyplaner;

import androidx.test.espresso.Espresso;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.assertion.ViewAssertions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class AddUITest {

    @Rule
    public ActivityScenarioRule<Add> activityRule =
            new ActivityScenarioRule<>(Add.class);

    @Test
    public void testEditTextFields() {
        String terminName = "Kevin B-Day";
        String terminBeschreibung = "Treffpunkt: 9 Uhr vor Uni";

        Espresso.onView(ViewMatchers.withId(R.id.Terminname_edit_text))
                .perform(ViewActions.typeText(terminName));
        Espresso.onView(ViewMatchers.withId(R.id.Terminname_edit_text))
                .check(ViewAssertions.matches(ViewMatchers.withText(terminName)));

        Espresso.onView(ViewMatchers.withId(R.id.Beschreibung_edit_text))
                .perform(ViewActions.typeText(terminBeschreibung));
        Espresso.onView(ViewMatchers.withId(R.id.Beschreibung_edit_text))
                .check(ViewAssertions.matches(ViewMatchers.withText(terminBeschreibung)));
    }

    @Test
    public void spinnerEdit(){
        String tag = "Freitag";
        String prio = "Priorität 3";

        Espresso.onView(ViewMatchers.withId(R.id.TagesSpinner)).perform(ViewActions.click());
        Espresso.onView(ViewMatchers.withText("Freitag")).perform(ViewActions.click());
    }

}
