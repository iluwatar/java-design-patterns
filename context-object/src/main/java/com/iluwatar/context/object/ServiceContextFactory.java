package com.iluwatar.context.object;

/**
 * An interface to create context objects passed through layers.
 */
public class ServiceContextFactory {

    public static ServiceContext createContext() {
        return new ServiceContext();
    }
}
