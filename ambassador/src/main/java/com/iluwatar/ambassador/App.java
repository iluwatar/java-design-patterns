package com.iluwatar.ambassador;

public class App {

    /**
     * Entry point
     */
    public static void main(String[] args) {

        Client host1 = new Client();
        Client host2 = new Client();

        host1.useService(12);
        host2.useService(73);

        host1.useNewService(12);
        host2.useNewService(73);
    }
}
