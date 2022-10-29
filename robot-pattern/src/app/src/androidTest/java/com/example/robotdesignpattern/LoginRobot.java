package com.example.robotdesignpattern;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

public class LoginRobot {

    public LoginRobot username(String user) {
        onView(withId(R.id.edt_email)).perform(typeText(user), closeSoftKeyboard());
        return this;
    }

    public LoginRobot password(String pass){
        onView(withId(R.id.edt_pass)).perform(typeText(pass), closeSoftKeyboard());
        return this;
    }

    public LoginRobot login(){
        onView(withId(R.id.btn_login)).perform(click());
        return this;
    }

    public LoginRobot resultFail(){
        onView(withId(R.id.tv_result)).check(matches(withText("LOGIN FAILED")));
        return this;

    }

    public LoginRobot resultSuccess(){
        onView(withId(R.id.tv_result)).check(matches(withText("LOGIN SUCCESS!")));
        return this;

    }




}