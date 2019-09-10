package com.iluwatar.leaderelection;

public interface MessageManager {

    boolean sendHeartbeatMessageToLeader(int leaderID);

}
