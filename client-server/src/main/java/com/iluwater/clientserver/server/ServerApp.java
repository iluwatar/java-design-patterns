package com.iluwater.clientserver.server;

import java.security.InvalidParameterException;

public final class ServerApp {

    private ServerApp() { }
    /**
     * Program entry point for server.
     *
     * @param strings program runtime arguments
     */
    public static void main(final String[] strings) throws Exception {
        System.out.println("Server > Start Server!!!");
        // Validate Input, expecting port number
        int port = 0;
        try {
            port = Integer.parseInt(strings[0].trim());
        } catch (Exception ex) {
            showHelp();
            throw new InvalidParameterException("Missing port number");
        }

        // Start ServerSocket
        Server server = new Server(port);
        server.startServer();
    }

    private static void showHelp() {
        System.out.print("Server > java Server <port>");
    }
}
