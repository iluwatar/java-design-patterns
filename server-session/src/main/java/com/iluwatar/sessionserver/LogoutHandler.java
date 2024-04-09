package com.iluwatar.sessionserver;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;
import java.time.Instant;
import java.util.Map;

public class LogoutHandler implements HttpHandler {

    private Map<String, Integer> sessions;
    private Map<String, Instant> sessionCreationTimes;

    public LogoutHandler(Map<String, Integer> sessions, Map<String, Instant> sessionCreationTimes) {
        this.sessions = sessions;
        this.sessionCreationTimes = sessionCreationTimes;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        // Get session ID from cookie
        String sessionID = exchange.getRequestHeaders().getFirst("Cookie").replace("sessionID=", "");
        String currentSessionID = sessions.get(sessionID) == null ? null : sessionID;

        // Send response

        String response = "";
        if(currentSessionID == null) {
            response += "Session has already expired!";
        } else {
            response = "Logout successful!\n" +
                    "Session ID: " + currentSessionID;
        }

        //Remove session
        sessions.remove(sessionID);
        sessionCreationTimes.remove(sessionID);
        exchange.sendResponseHeaders(200, response.length());
        OutputStream os = exchange.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }
}
