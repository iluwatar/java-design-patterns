package com.iluwater.notification;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

public class Notification {
    @Getter
    private List<NotificationError> errors = new ArrayList<>();

    protected Notification() {
        //
    }

    public boolean hasErrors() {
        return this.errors.size() != 0;
    }

    public void addError(NotificationError error) {
        this.errors.add(error);
    }
}
