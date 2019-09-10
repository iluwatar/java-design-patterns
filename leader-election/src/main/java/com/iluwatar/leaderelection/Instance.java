package com.iluwatar.leaderelection;

public interface Instance {

    boolean isAlive();

    void onMessage(Message message);

}
