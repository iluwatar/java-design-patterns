package com.iluwatar.leader.election;

public interface MessageManager {

    boolean sendHealthCheckMessageToLeader(int leaderID);

}
