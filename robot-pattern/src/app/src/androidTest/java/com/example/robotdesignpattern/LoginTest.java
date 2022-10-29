package com.example.robotdesignpattern;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
public class LoginTest {

    @Rule
    public ActivityTestRule<LoginScreen> activityRule
            = new ActivityTestRule<>(LoginScreen.class);

    @Test
    public void loginFailed() {
        new LoginRobot().username("navdeepgill@anu.edu.au").password("roses").login().resultFail();
    }

    @Test
    public void loginSuccess() {
        new LoginRobot().username("navdeepgill@anu.edu.au").password("sunflowers").login().resultSuccess();

    }

}
