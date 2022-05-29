package com.iluwater.clientserver;

import com.iluwater.clientserver.server.ServerApp;

public class RunnableServer implements Runnable {
    /**Thread for communication.*/
    private transient Thread thread;
    /**Port number.*/
    private int port;
    /**
     * Constructor for RunnableServer.
     * @param portNumber is the port number
     */
    public RunnableServer(final int portNumber) {
        this.port = portNumber;
    }
    /**
     * Run function.
     */
    @Override
    public void run() {
        try {
            ServerApp.main(new String[] {Integer.toString(this.port)});
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * Start function.
     */
    public void start() {
        if (thread == null) {
            thread = new Thread(this);
            thread.start();
        }
    }
    /**
     * get method for private attribute.
     * @return port
     */
    public int getPort() {
        return port;
    }

}
