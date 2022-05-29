package com.iluwater.clientserver.server;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.net.Socket;

import org.junit.jupiter.api.Test;

import com.iluwater.clientserver.RunnableServer;
import com.iluwater.clientserver.server.Server;

public class ServerTest {

    @Test
    void clientStartTest() throws IOException {
        final int portNo = 2233;
        RunnableServer server = new RunnableServer(portNo);
        server.start();
        assertEquals(portNo, server.getPort());
        Socket client = null;
        try {
            client = new Socket("localhost", server.getPort());
            assertTrue(client.isConnected());
        } finally {
            if (null != client) {
                client.close();
            }
        }
    }
}

