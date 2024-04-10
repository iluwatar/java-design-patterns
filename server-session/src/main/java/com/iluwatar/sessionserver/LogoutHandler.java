package com.iluwatar.sessionserver;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.OutputStream;
import java.time.Instant;
import java.util.Map;

@Slf4j
public class LogoutHandler implements HttpHandler {

    private Map<String, Integer> sessions;
    private Map<String, Instant> sessionCreationTimes;

    public LogoutHandler(Map<String, Integer> sessions, Map<String, Instant> sessionCreationTimes) {
        this.sessions = sessions;
        this.sessionCreationTimes = sessionCreationTimes;
    }

    @Override
    public void handle(HttpExchange exchange) {
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
        if(currentSessionID != null)
          LOGGER.info("User " + sessions.get(currentSessionID) + " deleted!");
        else
          LOGGER.info("User already deleted!");
        sessions.remove(sessionID);
        sessionCreationTimes.remove(sessionID);

        try {
          exchange.sendResponseHeaders(200, response.length());
        } catch(IOException e) {
          LOGGER.error("An error has occurred: ", e);
        }

        try(OutputStream os = exchange.getResponseBody()) {
          os.write(response.getBytes());
        } catch(IOException e) {
          LOGGER.error("An error has occurred: ", e);
        }
    }
}
