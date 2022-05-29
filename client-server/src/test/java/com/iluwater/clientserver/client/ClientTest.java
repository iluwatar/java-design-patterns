package com.iluwater.clientserver.client;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

import com.iluwater.clientserver.client.Client;

public class ClientTest {

    @Test
    void constructorTest() {
        final int portNo = 1123;
        Client client = new Client("client A", "localhost", portNo);
        assertNotNull(client);
        assertEquals("client A", client.getClientName());
        assertEquals("localhost", client.getHostname());
        assertEquals(portNo, client.getPort());
    }

    @Test
    void clientStartTest() {
        final int portNo = 1123;
        Client client = new Client("client A", "localhost", portNo);
        assertNotNull(client);
    }


}
