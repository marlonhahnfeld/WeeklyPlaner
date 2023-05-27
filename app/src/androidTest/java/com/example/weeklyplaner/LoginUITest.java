package com.example.weeklyplaner;

import androidx.test.espresso.Espresso;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.assertion.ViewAssertions;
import androidx.test.espresso.matcher.RootMatchers;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.filters.LargeTest;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
@LargeTest
public class LoginUITest {

    @Rule
    public ActivityScenarioRule<LoginScreen> activityRule =
            new ActivityScenarioRule<>(LoginScreen.class);

    @Test
    public void testClickRegisterButton() {
        Espresso.onView(ViewMatchers.withId(R.id.registerTextButton))
                .perform(ViewActions.click());
        Espresso.onView(ViewMatchers.withId(R.id.RegisterActivityLayout))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
    }

    @Test
    public void testLoginButton_validEmail() {
        String validEmail = "ummar@hh.de";
        String validPassword = "Marlon123!";

        Espresso.onView(ViewMatchers.withId(R.id.editTextEmail)).
                perform(ViewActions.typeText(validEmail), ViewActions.closeSoftKeyboard());
        Espresso.onView(ViewMatchers.withId(R.id.editTextPassword)).
                perform(ViewActions.typeText(validPassword), ViewActions.closeSoftKeyboard());

        Espresso.onView(ViewMatchers.withId(R.id.loginButton)).perform(ViewActions.click());
        Espresso.onView(ViewMatchers.withId(R.id.MainActivityLayout))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
    }

    @Test
    public void testLoginButton_invalidEmail() {
        String invalidEmail = "invalidEmail@hh.de";
        String validPassword = "Marlon123!";

        // Aktion: Eingabe der ungültigen E-Mail und des gültigen Passworts
        Espresso.onView(ViewMatchers.withId(R.id.editTextEmail))
                .perform(ViewActions.typeText(invalidEmail), ViewActions.closeSoftKeyboard());
        Espresso.onView(ViewMatchers.withId(R.id.editTextPassword))
                .perform(ViewActions.typeText(validPassword), ViewActions.closeSoftKeyboard());

        // Aktion: Klick auf den Anmeldebutton
        Espresso.onView(ViewMatchers.withId(R.id.loginButton)).perform(ViewActions.click());

        // Überprüfung: Dialog anzeigen und Button "OK" klicken
        Espresso.onView(ViewMatchers.withText("Falsche Email oder Passwort"))
                .inRoot(RootMatchers.isDialog())
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
        Espresso.onView(ViewMatchers.withText("OK"))
                .inRoot(RootMatchers.isDialog())
                .perform(ViewActions.click());

        // Überprüfung, ob nach OK klick LoginScreen angezeigt wird
        Espresso.onView(ViewMatchers.withId(R.id.LoginScreenActivityLayout))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
    }

    @Test
    public void testLoginButton_invalidPassword() {
        String validEmail = "ummar@hh.de";
        String invalidPassword = "ummar123!";

        // Aktion: Eingabe der gültigen E-Mail und des ungültigen Passworts
        Espresso.onView(ViewMatchers.withId(R.id.editTextEmail))
                .perform(ViewActions.typeText(validEmail), ViewActions.closeSoftKeyboard());
        Espresso.onView(ViewMatchers.withId(R.id.editTextPassword))
                .perform(ViewActions.typeText(invalidPassword), ViewActions.closeSoftKeyboard());

        // Aktion: Klick auf den Anmeldebutton
        Espresso.onView(ViewMatchers.withId(R.id.loginButton)).perform(ViewActions.click());

        // Überprüfung: Dialog anzeigen und Button "OK" klicken
        Espresso.onView(ViewMatchers.withText("Falsche Email oder Passwort"))
                .inRoot(RootMatchers.isDialog())
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
                .perform(ViewActions.click());
        Espresso.onView(ViewMatchers.withText("OK"))
                .inRoot(RootMatchers.isDialog())
                .perform(ViewActions.click());

        // Überprüfung, ob nach OK klick LoginScreen angezeigt wird
        Espresso.onView(ViewMatchers.withId(R.id.LoginScreenActivityLayout))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
    }
}
