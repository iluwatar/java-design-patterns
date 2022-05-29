package com.iluwater.clientserver.client;

import java.security.InvalidParameterException;

import com.iluwater.clientserver.server.ServerCommand;

public final class ClientJamie {

    private ClientJamie() { }
    /**
     * Program entry point for second client.
     *
     * @param args program runtime arguments
     */
    public static void main(final String[] args) throws Exception {
        System.out.println("Client Jamie > Start Client!!!");
        // Validate Input, expecting Hostname and Port number
        String clientName = "";
        String hostname = "";
        int port = 0;
        try {
            clientName = args[0].trim();
            hostname = args[1].trim().toLowerCase();
            port = Integer.parseInt(args[2].trim());
        } catch (Exception ex) {
            showHelp();
            throw new InvalidParameterException(
                    "Client Jamie > Missing port number");
        }
        Client clientClass = new Client(clientName, hostname, port);
        clientClass.connect();
        clientClass.sendCommand(ServerCommand.Hostname);
        clientClass.sendCommand(ServerCommand.Knowledge);
        clientClass.sendMultiPartCommand(new String[] {
                "jamie requesting multi",
                "-part", "message that is too",
                "long to fit in 1 command"});
        clientClass.disconnect();
    }

    private static void showHelp() {
        System.out.print("Client > java Client <hostname> <port>");
    }
}
