package com.iluwatar.leaderelection.ring;

import com.iluwatar.leaderelection.Instance;
import com.iluwatar.leaderelection.Message;
import com.iluwatar.leaderelection.MessageManager;
import com.iluwatar.leaderelection.MessageType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class RingMessageManager implements MessageManager {

    private static final Logger LOGGER = LoggerFactory.getLogger(RingMessageManager.class);

    private Map<Integer, Instance> instanceMap;

    @Override
    public boolean sendHeartbeatMessage(int leaderID) {
        Instance leaderInstance = instanceMap.get(leaderID);
        boolean alive = leaderInstance.isAlive();
        return alive;
    }

    @Override
    public void sendElectionMessage(int currentID, String content) {
        Instance nextInstance = this.findNextInstance(currentID);
        Message electionMessage = new RingMessage(MessageType.ELECTION, content);
        nextInstance.onMessage(electionMessage);
    }

    @Override
    public void sendLeaderMessage(int currentID, int leaderID) {

    }

    @Override
    public void sendHeartbeatInvokeMessage(int currentID) {
        Instance nextInstance = this.findNextInstance(currentID);
        Message heartbeatInvokeMessage = new RingMessage(MessageType.HEARTBEAT_INVOKE, "");
        nextInstance.onMessage(heartbeatInvokeMessage);
    }

    private Instance findNextInstance(int currentID) {
        Instance result = null;
        List<Integer> candidateSet =
                instanceMap.keySet()
                        .stream()
                        .filter((i) -> i > currentID && instanceMap.get(i).isAlive())
                        .sorted()
                        .collect(Collectors.toList());
        if (candidateSet.isEmpty()) {
            int index = instanceMap.keySet()
                            .stream()
                            .filter((i) -> instanceMap.get(i).isAlive())
                            .sorted()
                            .collect(Collectors.toList())
                            .get(0);
            result = instanceMap.get(index);
        } else {
            int index = candidateSet.get(0);
            result = instanceMap.get(index);
        }
        return result;
    }
}
