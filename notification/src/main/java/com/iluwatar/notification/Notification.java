package com.iluwatar.notification;

import java.util.ArrayList;
import java.util.List;

public class Notification {
    private List errors = new ArrayList();

    public List getErrors() {
         return this.errors;
    }

    public void setErrors(String error) {
        this.errors.add(error);
    }

    public boolean hasErrors() {
       return 0 != this.errors.size();
    }

    class Error{
        private String message;
        public Error(String message) {
            this.message = message;
        }
    }

}
