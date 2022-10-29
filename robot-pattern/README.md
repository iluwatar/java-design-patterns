---
layout: pattern
title: Robot Pattern
folder: robot-pattern
permalink: /patterns/pipeline/
categories: Testing
language: en
tags:
 - UI testing
---

## Intent

Robot design pattern intends to separate the "how" of UI testing from the "what". When testing the UI, 
the purpose should be to test how the view works, and not explicitly test the fields and their values. 

## Explanation

Robot design pattern suggests building a "testing robot" for each UI screen, which is responsible for 
finding the relevant buttons and fields on the screen. The test itself should just specify the values and buttons 
to be tested - not their locations or logistical details. 
 
Real world example

> Suppose a developer wants to test a login screen, they can write a test which specifies the username 
and password, along with with the button to be clicked. They don't have to specify the variables 
being tested or make assertion statements - the robot should abstract all those details from the 
developer       

In plain words

> Robot design pattern abstracts the "what" of UI testing and lets the developer focus on 
the "how"

Wikipedia says

> The Robot Pattern was designed by Jake Wharton at Square back in 2016. The power of this pattern
is its ability to create an abstraction layer in order to interact with the UI in a declarative mode.
Once created, it is then possible to perform multiple tests in order to verify our use cases without
boilerplate code, as well as without maintenance problems related to a refactor.


**Programmatic Example**

For a login screen, a standard automated UI test for email and password assertion 
would look like - 

```java
onView(withId(R.id.edt_email)).perform(typeText(user), closeSoftKeyboard());
onView(withId(R.id.edt_pass)).perform(typeText(pass), closeSoftKeyboard());
onView(withId(R.id.btn_login)).perform(click());
onView(withId(R.id.tv_result)).check(matches(withText("LOGIN FAILED")));
```

However, the aim of the robot pattern is to abstract the technicalities and represent 
the test as this instead - 

```java 
new LoginRobot().username("navdeepgill@anu.edu.au").password("sunflowers").login().resultSuccess();```

## Class diagram

![alt text](./etc/robot-pattern.png “Robot pattern class diagram")

## Applicability

Use the Robot design pattern when you want to

* Build automated UI tests for an entire app 
* Unsure about the exact location and variables of the UI elements and want 
to maintain the longevity of the tests 


## Credits

* [UI testing with Espresso](https://guides.codepath.com/android/ui-testing-with-espresso)
* [UI testing in Android](https://codingwithmitch.com/blog/ui-testing-with-espresso-android/)
