package com.iluwatar.notification;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ErrorProvider {

    public ErrorProvider () {}

    /**
     * set an Error message.
     *
     * @param courseID the course which contains error.
     * @param message the error message.
     */
    public void setError(String courseID, String message){
        LOGGER.error(message + " " + courseID);
    }
}
