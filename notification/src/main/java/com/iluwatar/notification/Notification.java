package com.iluwatar.notification;

import java.util.ArrayList;

public class Notification {
    private ArrayList errors = new ArrayList();

    public ArrayList getErrors() {
         return this.errors;

    }
    public void setErrors(String error) {
        this.errors.add(error);
    }
    public boolean hasErrors() {
       return 0 != this.errors.size();
    }
}
