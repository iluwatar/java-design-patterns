package com.iluwater.clientserver;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import com.iluwater.clientserver.RunnableServer;

public class RunnableServerTest {
    @Test
    void constructorTest() {
        final int portNo = 2233;
        RunnableServer server = new RunnableServer(portNo);
        server.start();
        assertEquals(portNo, server.getPort());
    }
}
