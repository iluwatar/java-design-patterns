package com.iluwatar.notification;

public class App {
    /**
     * Program entry point.
     *
     * @param args command line args
     */
    public static void main(String[] args) {
        FormRegisterCourse form = new FormRegisterCourse("CSE427", "Fall21", "Engineering");
        form.Submit();
    }
}
