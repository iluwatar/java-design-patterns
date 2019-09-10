package com.iluwatar.leader.election;

public interface Instance {

    boolean checkIfLeaderIsAlive();

    boolean isAlive();

}
