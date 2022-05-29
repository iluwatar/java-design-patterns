package com.iluwater.clientserver;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Date;
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
    void constructorClientAppTest() throws InterruptedException, UnknownHostException {
        final int portNo = 1129;
        final int timeout = 1000;
        RunnableClient runnableClient = new RunnableClient(
                "client 1", "localhost", portNo);
        ExecutorService executor = Executors.newCachedThreadPool();
        executor.execute(runnableClient);
        executor.awaitTermination(timeout, TimeUnit.MILLISECONDS);
        runnableClient.start();

        String val = outputStreamCaptor.toString();
        Date date = new Date();
        date.setSeconds(date.getSeconds() - 1);
        String res = "Server > Start Server!!!\r\n" +
                "Server > Server is listening on port 1129 \r\n" +
                "ClientApp > Start Client!!!\r\n" +
                "Client client 1 > Connected\r\n" +
                "Server > Client Connection Accepted.\r\n" +
                "Server > Request and Request ready\r\n" +
                "Server Command: Date \r\n" +
                "Client client 1 > Response recieved: " + date.toString() + "\r\n" +
                "Server Command: Version \r\n" +
                "Client client 1 > Response recieved: " + InetAddress.getLocalHost() + "\r\n" +
                "Server Multi-part Message:\r\n" +
                "Server Multi-part Message 0: This is client app. \r\n" +
                "Server Multi-part Message 1:  will convert to .  \r\n" +
                "Server Multi-part Message 2:  all Upper Case \r\n" +
                "Client client 1 > Response recieved: THIS IS CLIENT APP. WILL CONVERT TO .  ALL UPPER CASE\r\n" +
                "Server > client disconnected\r\n";
        assertEquals(res, val);

        //assertTrue(runnableClient.getClientStarted());
    }
}
