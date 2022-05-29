package com.iluwater.clientserver.client;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.UnknownHostException;

import com.iluwater.clientserver.server.ServerCommand;

public class Client {
    /**Client socket.*/
    private Socket socket = null;
    /**Input.*/
    private BufferedReader bReader = null;
    /**Output.*/
    private DataOutputStream dOut = null;
    /**Name of client.*/
    private String clientName;
    /**Hostname of client.*/
    private String hostname;
    /**Port number.*/
    private int port;
    /**
     * Constructor for Client.
     * @param nameOfClient is the name of client
     * @param hostnameOfClient is the hostname of client
     * @param portNumber is the port number
     */
    public Client(final String nameOfClient,
                  final String hostnameOfClient,
                  final int portNumber) {
        this.clientName = nameOfClient;
        this.hostname = hostnameOfClient;
        this.port = portNumber;
    }
    /**
     * Client get connection.
     */
    public void connect() throws UnknownHostException, IOException {
        this.socket = new Socket(this.hostname, this.port);
        this.bReader = new BufferedReader(
                new InputStreamReader(socket.getInputStream(), "utf-8"));
        this.dOut = new DataOutputStream(socket.getOutputStream());

        System.out.printf("Client %s > Connected%n", clientName);
    }
    /**
     * Client sent command after get connected.
     * @param serverCommand is the server command
     */
    public void sendCommand(final ServerCommand serverCommand)
            throws Exception {
        if (null == dOut) {
            throw new Exception("Client is not running");
        }
        try {
            this.dOut.writeByte(1);
            this.dOut.writeUTF(serverCommand.toString());
            this.dOut.flush();

            String reqStr = this.bReader.readLine();
            if (null == reqStr) {
                return;
            }
            System.out.printf("Client %s > Response recieved: "
                    + "%s%n", this.clientName, reqStr);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    /**
     * Client sent multiple commands after get connected.
     * @param content is the contents of command
     */
    public void sendMultiPartCommand(final String[] content) throws Exception {
        if (null == dOut) {
            throw new Exception("Client is not running");
        }
        try {
            this.dOut.writeByte(2);
            for (int i = 0; i < content.length; i++) {
                this.dOut.writeUTF(content[i]);
            }
            this.dOut.flush();

            String reqStr = this.bReader.readLine();
            if (null == reqStr) {
                return;
            }
            System.out.printf("Client %s > Response recieved: "
                    + "%s%n", this.clientName, reqStr);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    /**
     * Client get disconnected.
     */
    public void disconnect() throws Exception {
        if (null == dOut) {
            throw new Exception("Client is not running");
        }

        try {
            this.dOut.writeByte(-1);
            this.dOut.flush();

            String reqStr = this.bReader.readLine();
            if (null == reqStr) {
                return;
            }
            System.out.printf("Client %s > Response recieved: "
                    + "%s%n", this.clientName, reqStr);

        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (null != socket) {
                try {
                    socket.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (null != bReader) {
                try {
                    bReader.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (null != dOut) {
                try {
                    dOut.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
    /**
     * get method for private attribute.
     * @return clientName
     */
    public String getClientName() {
        return clientName;
    }
    /**
     * set method for private attribute.
     * @param nameOfClient is the name of client
     */
    public void setClientName(final String nameOfClient) {
        this.clientName = nameOfClient;
    }
    /**
     * get method for private attribute.
     * @return hostname
     */
    public String getHostname() {
        return hostname;
    }
    /**
     * set method for private attribute.
     * @param hostnameOfClient is the hostname of client
     */
    public void setHostname(final String hostnameOfClient) {
        this.hostname = hostnameOfClient;
    }
    /**
     * get method for private attribute.
     * @return port
     */
    public int getPort() {
        return port;
    }
    /**
     * set method for private attribute.
     * @param portNumber is the port number
     */
    public void setPort(final int portNumber) {
        this.port = portNumber;
    }
}
