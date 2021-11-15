package com.iluwatar.notification;

import java.util.ArrayList;
import java.util.List;

public class Notification {
    private List<Error> errors = new ArrayList();

    protected Notification() {}

    /**
     * Get errors of this Notification.
     *
     * @return the list of errors in this Notification
     */
    public List<Error> getErrors() {
         return this.errors;
    }

    /**
     * Set errors in this Notification.
     *
     * @param error the error to be added.
     */
    public void setErrors(Error error) {
        this.errors.add(error);
    }

    /**
     * Check errors.
     *
     * @return true if this Notification has error, false if errors do not exist.
     */
    public boolean hasErrors() {
       return this.errors.size() != 0;
    }


}
