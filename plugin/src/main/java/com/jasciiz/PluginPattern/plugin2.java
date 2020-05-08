package com.jasciiz.PluginPattern;

/**
 * example 1 of Plugin
 *
 */
public class plugin2 implements Plugin {

    public plugin2(){};

    @Override
    public void init() {
        System.out.println("hello plugin2");
    }

    @Override
    public void run() {
        System.out.println("plugin2 running");
    }
}
