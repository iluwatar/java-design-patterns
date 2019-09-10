package com.iluwatar.leaderelection;

public interface MessageManager {

    boolean sendHeartbeatMessage(int leaderID);

    void sendElectionMessage(int currentID, String content);

    void sendLeaderMessage(int currentID, int leaderID);

    void sendHeartbeatInvokeMessage(int currentID);

}
