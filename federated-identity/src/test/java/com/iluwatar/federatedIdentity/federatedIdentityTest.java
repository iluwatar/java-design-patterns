package com.iluwater.federatedIdentity;

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.net.ConnectException;

import static org.junit.Assert.assertEquals;

public class federatedIdentityTest {
    Consumer consumer;
    IdP idP;
    Server server;

    @Before
    public void init(){
        consumer = new Consumer();
        idP = new IdP();
        server = new Server();
        idP.listener();
        server.listener();
    }

    @Test
    public void test1(){
        assertEquals(true, server.getIdP());
        consumer.register();
        assertEquals(true, consumer.getClaim());
        assertEquals(true, consumer.logIn());
        consumer.run();
    }

    @Test
    public void test2(){
        server.getIdP();
        assertEquals(false, consumer.getClaim());
    }

    @Test
    public void test3(){
        server.getIdP();
        consumer.register();
        assertEquals("no log in",consumer.run());
    }
}
