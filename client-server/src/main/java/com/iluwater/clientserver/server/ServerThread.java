package com.iluwater.clientserver.server;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.OutputStreamWriter;
import java.io.DataInputStream;

import java.net.InetAddress;
import java.net.Socket;
import java.util.Date;

public class ServerThread extends Thread {
    /**Client socket.*/
    private Socket client = null;
    /**Response stream.*/
    private PrintWriter response;

    /**
     * Constructor for ServerThread.
     * @param clientSocket is the client socket
     */

    @edu.umd.cs.findbugs.annotations.SuppressFBWarnings("EI_EXPOSE_REP2")
    public ServerThread(final Socket clientSocket) {
        this.client = clientSocket;
    }
    /**
     * Run function.
     */
    public void run() {
        System.out.println("Server > Client Connection Accepted.");
        try {
            // Start request and response stream
            this.response = new PrintWriter(new OutputStreamWriter(
                    this.client.getOutputStream(), "utf-8"), true);
            System.out.println("Server > Request and Request ready");

            // Ready to accept request
            DataInputStream dIn = new DataInputStream(
                    this.client.getInputStream());

            boolean done = false;
            while (!done) {
                byte messageType = dIn.readByte();

                switch (messageType) {
                    case 1: // Type A - Get Server Info
                        String command = dIn.readUTF();
                        ServerCommand serverCommand =
                                ServerCommand.valueOf(command);
                        System.out.printf("Server Command: %s %n",
                                serverCommand.toString());
                        switch (serverCommand) {
                            case Version:
                                this.response.println(
                                        InetAddress.getLocalHost());
                                break;
                            case Date:
                                this.response.println(new Date().toString());
                                break;
                            case Hostname:
                                this.response.println(InetAddress.
                                        getLocalHost().getHostName());
                                break;
                            default:
                                this.response.println("Not supported");
                                break;
                        }
                        break;
                    case 2: // Type B - Multi-part Message
                        System.out.printf("Server Multi-part Message:%n");
                        StringBuilder sb = new StringBuilder();
                        int i = 0;
                        while (dIn.available() > 0) {
                            String tmp = dIn.readUTF();
                            System.out.printf("Server Multi-part Message "
                                    + "%d: %s %n", i++, tmp);
                            sb.append(tmp);
                        }

                        this.response.println(sb.toString().toUpperCase());
                        break;
                    default:
                        done = true;
                }
            }
            dIn.close();

        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            // attempt to close connection
            try {
                this.client.close();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            System.out.println("Server > client disconnected");
        }
    }
}
