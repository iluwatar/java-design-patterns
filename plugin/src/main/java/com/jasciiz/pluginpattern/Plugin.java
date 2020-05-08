package com.jasciiz.pluginpattern;

/**
 * The interface which implement by plugins
 *
 */
public interface Plugin {
    public abstract void init();

    public abstract void run();
}
