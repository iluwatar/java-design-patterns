package com.jasciiz.pluginpattern;


/**
 * example 1 of Plugin
 *
 */

public class plugin1 implements Plugin {

    public plugin1(){};

    @Override
    public void init() {
        System.out.println("hello plugin1");
    }

    @Override
    public void run() {
        System.out.println("plugin1 running");
    }
}
