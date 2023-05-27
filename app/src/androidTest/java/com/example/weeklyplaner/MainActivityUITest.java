package com.example.weeklyplaner;

import androidx.test.espresso.Espresso;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.assertion.ViewAssertions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class MainActivityUITest {

    @Rule
    public ActivityScenarioRule<MainActivity> activityRule =
            new ActivityScenarioRule<>(MainActivity.class);

    @Test
    public void clickAddButton_opensAddActivity() {
        Espresso.onView(ViewMatchers.withId(R.id.AddButton)).perform(ViewActions.click());
        Espresso.onView(ViewMatchers.withId(R.id.AddActivityLayout))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
    }

    @Test
    public void clickMoSoButton_opensAddActivity() {
        Espresso.onView(ViewMatchers.withId(R.id.MoSoButton)).perform(ViewActions.click());
        Espresso.onView(ViewMatchers.withId(R.id.WeekActivityLayout))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
    }

    @Test
    public void clickSortButton_opensActivity() {
        Espresso.onView(ViewMatchers.withId(R.id.SortButton)).perform(ViewActions.click());
        Espresso.onView(ViewMatchers.withId(R.id.SortActivityLayout))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
    }
}