package com.iluwatar.notification;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ErrorProviderTest {

    @Test
    void testErrorProviderTest() {
        ErrorProvider provider = new ErrorProvider();
        Error error = new Error("Error");
        String err = provider.setError(error);
        assertEquals(err, error.getErrorMessage());
    }






}
