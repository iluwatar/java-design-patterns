package com.iluwatar.gatekeeper;


import static org.junit.jupiter.api.Assertions.*;


import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import com.iluwatar.gatekeeper.utils.InMemoryAppender;

import java.util.List;

class GateKeeperTest {
    private InMemoryAppender appender;

    @BeforeEach
    public void setUp() {
        appender = new InMemoryAppender(GateKeeper.class);
    }

    @AfterEach
    public void tearDown() {
        appender.stop();
    }

    @Test
    void TestValidateAndForwardRequest(){
        final var requests= List.of(
                new LoginRequest("login","account1","password1"),
                new HackRequest("suspicious action")
        );
        var gateKeeper=new GateKeeper();
        requests.forEach(gateKeeper::validateAndForwardRequest);
        assertTrue(appender.logContains("----------------------------"));
        assertTrue(appender.logContains("Start the request: login"));
        assertTrue(appender.logContains("Accept the request!"));
        assertTrue(appender.logContains("End the request: login"));
        assertTrue(appender.logContains("----------------------------"));
        assertTrue(appender.logContains("Start the request: suspicious action"));
        assertTrue(appender.logContains("Refuse the request!"));
        assertTrue(appender.logContains("End the request: suspicious action"));
    }
}
