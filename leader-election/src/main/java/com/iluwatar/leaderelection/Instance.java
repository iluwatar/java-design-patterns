package com.iluwatar.leaderelection;

public interface Instance {

    boolean isAlive();

    void setAlive(boolean alive);

    void onMessage(Message message);

}
