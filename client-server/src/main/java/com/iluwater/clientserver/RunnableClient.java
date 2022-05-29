package com.iluwater.clientserver;

import com.iluwater.clientserver.client.ClientApp;
import com.iluwater.clientserver.client.ClientJamie;

public class RunnableClient implements Runnable {
    /**Thread for communication.*/
    private transient Thread thread;
    /**Name of client.*/
    private String clientName;
    /**Hostname of client.*/
    private String hostname;
    /**Port number.*/
    private int port;
    /**
     * Constructor for RunnableClient.
     * @param nameOfClient is the name of client
     * @param hostnameOfClient is the hostname of client
     * @param portNumber is the port number
     */
    public RunnableClient(final String nameOfClient,
                          final String hostnameOfClient,
                          final int portNumber) {
        this.clientName = nameOfClient;
        this.hostname = hostnameOfClient;
        this.port = portNumber;
    }
    /**
     * Run function.
     */
    @Override
    public void run() {
        try {
            if (this.clientName.equals("Jamie")) {
                ClientJamie.main(new String[] {
                        this.clientName,
                        this.hostname,
                        Integer.toString(this.port)});
            } else {
                ClientApp.main(new String[] {
                        this.clientName,
                        this.hostname,
                        Integer.toString(this.port)});
            }
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
     * @return clientName
     */
    public String getClientName() {
        return clientName;
    }
    /**
     * get method for private attribute.
     * @return hostname
     */
    public String getHostname() {
        return hostname;
    }
    /**
     * get method for private attribute.
     * @return port
     */
    public int getPort() {
        return port;
    }
}
