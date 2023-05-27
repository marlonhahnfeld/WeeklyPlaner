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
public class RegisterScreenUITest {

    @Rule
    public ActivityScenarioRule<RegisterScreen> activityRule =
            new ActivityScenarioRule<>(RegisterScreen.class);

    @Test
    public void testBackButton() {
        Espresso.onView(ViewMatchers.withId(R.id.backToLoginScreenButton))
                .perform(ViewActions.click());
        Espresso.onView(ViewMatchers.withId(R.id.LoginScreenActivityLayout))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
    }

    @Test
    public void testInvalidEmailFormat() throws InterruptedException {
        String invalidEmail = "invalid!EmailFormat@gmail?.de";
        String validPassword = "Marlon123!";

        Espresso.onView(ViewMatchers.withId(R.id.editTextEmail))
                .perform(ViewActions.typeText(invalidEmail), ViewActions.closeSoftKeyboard());

        Espresso.onView(ViewMatchers.withId(R.id.editTextPassword))
                .perform(ViewActions.typeText(validPassword), ViewActions.closeSoftKeyboard());

        Espresso.onView(ViewMatchers.withId(R.id.editTextPassword2))
                .perform(ViewActions.typeText(validPassword), ViewActions.closeSoftKeyboard());

        Espresso.onView(ViewMatchers.withId(R.id.registerButton))
                .perform(ViewActions.click());

        Thread.sleep(1000);

        Espresso.onView(ViewMatchers.withId(R.id.RegisterActivityLayout))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
    }

    @Test
    public void testPasswordFormat() {
        String email = "testMail@hh.de";
        String passwordOhne8Zeichen = "Ummar1!";
        String passwordOhneGrossBuchstaben = "marlon123!";
        String passwordOhneZahl = "Marlonnnn!";
        String passwordOhneSonderzeichen = "Marlon1234";
        String passwordRichtig = "Marlon123!";
        String passwordFalsch = "Marlon1234!";

        Espresso.onView(ViewMatchers.withId(R.id.editTextEmail))
                .perform(ViewActions.typeText(email), ViewActions.closeSoftKeyboard());

        // * Fall 1 → Länge des Passworts nicht eingehalten
        Espresso.onView(ViewMatchers.withId(R.id.editTextPassword))
                .perform(ViewActions.typeText(passwordOhne8Zeichen),
                        ViewActions.closeSoftKeyboard());

        Espresso.onView(ViewMatchers.withId(R.id.editTextPassword2))
                .perform(ViewActions.typeText(passwordOhne8Zeichen),
                        ViewActions.closeSoftKeyboard());

        Espresso.onView(ViewMatchers.withId(R.id.registerButton))
                .perform(ViewActions.click());

        Espresso.onView(ViewMatchers.withId(R.id.min8SignsLongTextView))
                .check(ViewAssertions.matches(ViewMatchers.hasTextColor(R.color.red)));

        // * Fall 2 → Passwort enthält keinen Großbuchstaben
        Espresso.onView(ViewMatchers.withId(R.id.editTextPassword))
                .perform(ViewActions.clearText());

        Espresso.onView(ViewMatchers.withId(R.id.editTextPassword2))
                .perform(ViewActions.clearText());

        Espresso.onView(ViewMatchers.withId(R.id.editTextPassword))
                .perform(ViewActions.typeText(passwordOhneGrossBuchstaben),
                        ViewActions.closeSoftKeyboard());

        Espresso.onView(ViewMatchers.withId(R.id.editTextPassword2))
                .perform(ViewActions.typeText(passwordOhneGrossBuchstaben),
                        ViewActions.closeSoftKeyboard());

        Espresso.onView(ViewMatchers.withId(R.id.registerButton))
                .perform(ViewActions.click());

        Espresso.onView(ViewMatchers.withId(R.id.min1UpperAndLowerLetterTextView))
                .check(ViewAssertions.matches(ViewMatchers.hasTextColor(R.color.red)));

        // * Fall 3 → Passwort enthält keine Zahl
        Espresso.onView(ViewMatchers.withId(R.id.editTextPassword))
                .perform(ViewActions.clearText());

        Espresso.onView(ViewMatchers.withId(R.id.editTextPassword2))
                .perform(ViewActions.clearText());

        Espresso.onView(ViewMatchers.withId(R.id.editTextPassword))
                .perform(ViewActions.typeText(passwordOhneZahl),
                        ViewActions.closeSoftKeyboard());

        Espresso.onView(ViewMatchers.withId(R.id.editTextPassword2))
                .perform(ViewActions.typeText(passwordOhneZahl),
                        ViewActions.closeSoftKeyboard());

        Espresso.onView(ViewMatchers.withId(R.id.registerButton))
                .perform(ViewActions.click());

        Espresso.onView(ViewMatchers.withId(R.id.min1FigureTextView))
                .check(ViewAssertions.matches(ViewMatchers.hasTextColor(R.color.red)));

        // * Fall 4 → Passwort enthält kein Sonderzeichen
        Espresso.onView(ViewMatchers.withId(R.id.editTextPassword))
                .perform(ViewActions.clearText());

        Espresso.onView(ViewMatchers.withId(R.id.editTextPassword2))
                .perform(ViewActions.clearText());

        Espresso.onView(ViewMatchers.withId(R.id.editTextPassword))
                .perform(ViewActions.typeText(passwordOhneSonderzeichen),
                        ViewActions.closeSoftKeyboard());

        Espresso.onView(ViewMatchers.withId(R.id.editTextPassword2))
                .perform(ViewActions.typeText(passwordOhneSonderzeichen),
                        ViewActions.closeSoftKeyboard());

        Espresso.onView(ViewMatchers.withId(R.id.registerButton))
                .perform(ViewActions.click());

        Espresso.onView(ViewMatchers.withId(R.id.min1SignTextView))
                .check(ViewAssertions.matches(ViewMatchers.hasTextColor(R.color.red)));

        // * Fall 5 → Schreibweise der Passwörter stimmen nicht überein
        Espresso.onView(ViewMatchers.withId(R.id.editTextPassword))
                .perform(ViewActions.clearText());

        Espresso.onView(ViewMatchers.withId(R.id.editTextPassword2))
                .perform(ViewActions.clearText());

        Espresso.onView(ViewMatchers.withId(R.id.editTextPassword))
                .perform(ViewActions.typeText(passwordRichtig),
                        ViewActions.closeSoftKeyboard());

        Espresso.onView(ViewMatchers.withId(R.id.editTextPassword2))
                .perform(ViewActions.typeText(passwordFalsch),
                        ViewActions.closeSoftKeyboard());

        Espresso.onView(ViewMatchers.withId(R.id.registerButton))
                .perform(ViewActions.click());

        Espresso.onView(ViewMatchers.withId(R.id.editTextPassword))
                .check(ViewAssertions.matches(ViewMatchers.hasTextColor(R.color.red)));

        Espresso.onView(ViewMatchers.withId(R.id.editTextPassword2))
                .check(ViewAssertions.matches(ViewMatchers.hasTextColor(R.color.red)));
    }
}
