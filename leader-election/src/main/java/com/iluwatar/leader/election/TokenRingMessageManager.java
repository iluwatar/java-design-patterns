package com.iluwatar.leader.election;

import java.util.Map;

public class TokenRingMessageManager implements MessageManager {

    private Map<Integer, Instance> instanceMap;

    public boolean sendHealthCheckMessageToLeader(int leaderID) {
        Instance leaderInstance = instanceMap.get(leaderID);
        boolean alive = leaderInstance.isAlive();
        return alive;
    }

    public Map<Integer, Instance> getInstanceMap() {
        return instanceMap;
    }

    public void setInstanceMap(Map<Integer, Instance> instanceMap) {
        this.instanceMap = instanceMap;
    }
}
