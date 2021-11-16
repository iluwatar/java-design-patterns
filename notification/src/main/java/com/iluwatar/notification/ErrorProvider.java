package com.iluwatar.notification;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ErrorProvider {

    public ErrorProvider () {}

    /**
     * set an Error message.
     *
     * @param error the error to display.
     *
     */
    public String setError(Error error){
        LOGGER.error(error.getErrorMessage());
        return error.getErrorMessage();
    }
}
