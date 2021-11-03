package com.iluwatar.notification;

import java.util.ArrayList;
import java.util.List;

public class Notification {
    private List<Error> errors = new ArrayList();

    public List<Error> getErrors() {
         return this.errors;
    }

    public void setErrors(Error error) {
        this.errors.add(error);
    }

    public boolean hasErrors() {
       return 0 != this.errors.size();
    }


}
