package com.iluwater.clientserver;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.iluwater.clientserver.RunnableClient;
import com.iluwater.clientserver.RunnableServer;

public class RunnableClientTest {
    /**Output.*/
    private final ByteArrayOutputStream outputStreamCaptor =
            new ByteArrayOutputStream();
    /**
     * Set up server before each testing.
     */
    @BeforeEach
    public void setUp() throws UnsupportedEncodingException {
        final int portNo = 1129;
        System.setOut(new PrintStream(outputStreamCaptor, true, "UTF-8"));
        RunnableServer server = new RunnableServer(portNo);
        server.start();
    }

    @Test
    void constructorTest() {
        final int portNo = 1122;
        RunnableClient runnableClient = new RunnableClient(
                "client 1", "localhost", portNo);
        assertEquals("client 1", runnableClient.getClientName());
        assertEquals("localhost", runnableClient.getHostname());
        assertEquals(portNo, runnableClient.getPort());
    }

    @Test
    void constructorClientAppTest() throws InterruptedException {
        final int portNo = 1129;
        final int timeout = 1000;
        RunnableClient runnableClient = new RunnableClient(
                "client 1", "localhost", portNo);
        ExecutorService executor = Executors.newCachedThreadPool();
        executor.execute(runnableClient);
        executor.awaitTermination(timeout, TimeUnit.MILLISECONDS);
        //runnableClient.start();

        //String val = outputStreamCaptor.toString();
        //assertEquals("Client Started", val);

        //assertTrue(runnableClient.getClientStarted());
    }
}
