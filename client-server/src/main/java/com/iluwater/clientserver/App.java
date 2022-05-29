package com.iluwater.clientserver;

public final class App {

    private App() { }
    /**
     * Program main entry point.
     *
     * @param args program runtime arguments
     */
    public static void main(final String[] args) throws Exception {
        final int portNo = 11223;
        RunnableServer server = new RunnableServer(portNo);
        server.start();
        RunnableClient clientA = new RunnableClient(
                "App", "localhost", portNo);
        clientA.start();

        RunnableClient clientJamie = new RunnableClient(
                "Jamie", "localhost", portNo);
        clientJamie.start();
    }
}
