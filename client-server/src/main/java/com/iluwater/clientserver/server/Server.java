package com.iluwater.clientserver.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    /**Server socket.*/
    private ServerSocket socket = null;
    /**Port number.*/
    private int port;
    /**State of the connection.*/
    private boolean start = false;
    /**
     * Constructor for Server.
     * @param portNumber is the port number
     */
    public Server(final int portNumber) {
        this.port = portNumber;
    }
    /**
     * startServer function.
     */
    public void startServer() throws IOException {
        this.socket = new ServerSocket(this.port);
        System.out.printf("Server > Server is listening on port %d %n", port);
        // loop indefinately until program is stopped.
        this.start = true;
        try {
            while (this.start) {
                // accept connection
                Socket client = socket.accept();
                Thread cThred = new Thread(new ServerThread(client));
                cThred.start();
            }
        } finally {
            if (null != this.socket) {
                this.socket.close();
            }
        }
    }
    /**
     * stopServer function.
     */
    public void stopServer() {
        this.start = false;
    }
    /**
     * get method for private attribute.
     * @return socket
     */
    public ServerSocket getSocket() {
        try {
            return new ServerSocket();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    /**
     * set method for private attribute.
     */
    public void setSocket() {
        try {
            this.socket = new ServerSocket();
        } catch (IOException e) {
            e.printStackTrace();
        }
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
     * @param portNumber is the port number.
     */
    public void setPort(final int portNumber) {
        this.port = portNumber;
    }
}
