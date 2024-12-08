package com.hungrydev399.publishsubscribe.jms;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import javax.jms.JMSException;
import javax.jms.Session;

public class JmsUtilTest {
    
    @Test
    void shouldCreateSessionWithoutClientId() throws JMSException {
        Session session = JmsUtil.createSession();
        assertNotNull(session);
    }

    @Test
    void shouldCreateSessionWithClientId() throws JMSException {
        Session session = JmsUtil.createSession("test-client");
        assertNotNull(session);
    }

    @Test
    void shouldCloseConnectionGracefully() {
        JmsUtil.closeConnection();
        // Verify no exceptions thrown
        assertTrue(true);
    }
}
