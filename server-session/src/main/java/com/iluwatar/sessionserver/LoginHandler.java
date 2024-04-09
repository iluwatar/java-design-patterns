package com.iluwatar.sessionserver;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class LoginHandler implements HttpHandler {

    private Map<String, Integer> sessions;
    private Map<String, Instant> sessionCreationTimes;

    public LoginHandler(Map<String, Integer> sessions, Map<String, Instant> sessionCreationTimes) {
        this.sessions = sessions;
        this.sessionCreationTimes = sessionCreationTimes;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        // Generate session ID
        String sessionID = UUID.randomUUID().toString();

        // Store session data (simulated)
        int newUser = sessions.size() + 1;
        sessions.put(sessionID, newUser);
        sessionCreationTimes.put(sessionID, Instant.now());

        // Set session ID as cookie
        exchange.getResponseHeaders().add("Set-Cookie", "sessionID=" + sessionID);

        // Send response
        String response = "Login successful!\n" +
                "Session ID: " + sessionID;
        exchange.sendResponseHeaders(200, response.length());
        OutputStream os = exchange.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }
}
