package com.iluwatar.notification;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ErrorProvider {

    public void setError(String arg, String message){
        LOGGER.error(message + " " + arg);
    }
}
