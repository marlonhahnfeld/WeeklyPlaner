package com.example.weeklyplaner;

import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static org.junit.Assert.assertNotNull;

import android.content.Intent;
import android.widget.ImageButton;

import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.intent.Intents;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

@RunWith(RobolectricTestRunner.class)
public class MainActivityTest {

    @Before
    public void setUp() {
        // Initialize the Intents class before the test
        Intents.init();
    }

    @After
    public void tearDown() {
        // Release the resources used by the Intents class after the test
        Intents.release();
    }

    @Test
    public void testAddButton() {
        // Launch the MainActivity using ActivityScenario
        ActivityScenario<MainActivity> scenario = ActivityScenario.launch(MainActivity.class);

        // Interact with the UI components inside the MainActivity
        scenario.onActivity(activity -> {
            ImageButton addButton = activity.findViewById(R.id.AddButton);
            assertNotNull("Add Button is null", addButton);

            // Simulate a click on the Add Button
            addButton.performClick();

            // Verify that the expected Activity is launched
            Intent expectedIntent = new Intent(activity, Add.class);
            intended(hasComponent(expectedIntent.getComponent()));
        });
    }
}



