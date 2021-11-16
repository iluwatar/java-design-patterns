---
layout: pattern
title: Notification
folder: notification
permalink: /patterns/notification/
categories: Behavioural
language: en
tags:
- Notification
---

## Intent

A Notification is an object that collects information about errors during validation of data. When an error appears the Notification is sent back to the view to display further information about the errors.


## Explanation

Real world example

> There's a form to register for a course. We need to validate the inputs to make sure 
> the inputs are valid.

In plain words

> An object that collects together information about errors and other information and communicates it to the presentation.

**Programmatic Example**

Walking through our registration example, here's the `FormRegistrationCourse`.

```java
package com.iluwatar.notification;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class FormRegisterCourse {
    private RegisterCourseDTO course;
    private CourseService service;

    private ErrorProvider errorProvider;
    private String courseID;
    private String semester;
    private String department;

    /**
     * Creates a form for registering a course.
     *
     * @param courseID the course to be added.
     * @param semester  semester of this course
     * @param department department of this course
     */
    public FormRegisterCourse(String courseID, String semester, String department) {

        // instantiates the form
        ...
    }

    /**
     * Submits a form for registering a course.
     *
     * @return "Registration Succeeded" if customer is successfully added,
     *         "Not registered, see errors" if customer already exists.
     */
    public String submit() {
        // submit the form to RegisterCourse
    }

}

  ...
}
```

Here's the `RegisterCourse` which implements `ServerCommand`. It 
keeps a simple map of customers in memory while `DBCustomerDao` is the real RDBMS implementation.

```java
public class RegisterCourse extends ServerCommand {

    protected RegisterCourse(RegisterCourseDTO course) {
        super(course);
    }

    /**
     * Runs this service to validate registration forms, if no errors reported, save to backend
     */
    public void run() {
        validate();
        if (!super.getNotification().hasErrors()) {
            // RegisterCourseInBackendSystems();
        }
    }

    private void validate() {
        // checks for input errors. If errors are present, save it to the Notification object
    }
```

Finally here's our Notification object.

```java
public class Notification {
    private List<Error> errors = new ArrayList();

    protected Notification() {}

    public List<Error> getErrors() {
        return this.errors;
    }

    public void setErrors(Error error) {
        this.errors.add(error);
    }

    public boolean hasErrors() {
        return this.errors.size() != 0;
    }


}
```

Here's the full example in action.

```java
final String courseID = "CSE427";
final String semester = "Fall21";
final String department = "Engineering";
final FormRegisterCourse form = new FormRegisterCourse(courseID, semester, department);
form.submit();
```
The program output:
```java
22:25:50.947 [main] INFO com.iluwatar.notification.FormRegisterCourse - Registration Succeeded
```

Here's the an example with errors.

```java
final String courseID = "";
final String semester = "";
final String department = "Engineering";
final FormRegisterCourse form = new FormRegisterCourse(courseID, semester, department);
form.submit();
```
The program output:
```java
        22:26:30.471 [main] ERROR com.iluwatar.notification.ErrorProvider - Course ID is missing 
        22:26:30.474 [main] ERROR com.iluwatar.notification.ErrorProvider - Semester is missing
        22:26:30.474 [main] INFO com.iluwatar.notification.FormRegisterCourse - Not registered, see errors
```

## Class diagram

![alt text](./etc/notification.urm.png "Notification")

## Applicability

Use the Notification pattern in any of the following situation:

* The most obvious alternative to using Notification is for the domain to use exception handling to indicate errors. Such an approach has the domain throw an exception if a validation check fails. The problem with this is that it only indicates the first validation error. It's usually more helpful to show every validation error, particular if validation requires a round trip to a remote domain layer.

## Credits

* [Notification](https://martinfowler.com/eaaDev/Notification.html)
