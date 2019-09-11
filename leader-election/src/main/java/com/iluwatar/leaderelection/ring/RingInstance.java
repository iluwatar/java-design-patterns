package com.iluwatar.leaderelection.ring;

import com.iluwatar.leaderelection.Instance;
import com.iluwatar.leaderelection.Message;
import com.iluwatar.leaderelection.MessageManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.stream.Collectors;

public class RingInstance implements Instance, Runnable {

    private static final Logger LOGGER = LoggerFactory.getLogger(RingInstance.class);

    private MessageManager messageManager;

    private Queue<Message> messageQueue;

    private final int localID;

    private int leaderID;

    private boolean alive;

    public RingInstance(MessageManager messageManager, int localID, int leaderID) {
        this.messageManager = messageManager;
        this.messageQueue = new PriorityQueue<>();
        this.localID = localID;
        this.leaderID = leaderID;
        this.alive = true;
    }

    @Override
    public void run() {
        while (true) {
            if (!messageQueue.isEmpty()) {
                this.processMessage(messageQueue.poll());
            }
        }
    }

    @Override
    public void onMessage(Message message) {
        messageQueue.offer(message);
    }

    private void processMessage(Message message) {
        switch (message.getType()) {
            case ELECTION:
                LOGGER.info("Instance " + localID + ": Election Message handling...");
                this.handleElectionMessage(message);
                break;
            case LEADER:
                LOGGER.info("Instance " + localID + ": Leader Message handling...");
                this.handleLeaderMessage(message);
                break;
            case HEARTBEAT_INVOKE:
                LOGGER.info("Instance " + localID + ": Heartbeat Message handling...");
                this.handleHeartbeatMessage(message);
                break;
        }
    }

    private void handleHeartbeatMessage(Message message) {
        boolean isLeaderAlive = messageManager.sendHeartbeatMessage(this.leaderID);
        if (isLeaderAlive) {
            LOGGER.info("Instance " + localID + ": Leader is alive.");
            messageManager.sendHeartbeatInvokeMessage(this.localID);
        } else {
            LOGGER.info("Instance " + localID + ": Leader is not alive. Start election.");
            messageManager.sendElectionMessage(this.localID, String.valueOf(localID));
        }
    }

    private void handleElectionMessage(Message message) {
        String content = message.getContent();
        List<Integer> candidateList =
                Arrays.stream(content.trim().split(","))
                        .map(Integer::valueOf)
                        .sorted()
                        .collect(Collectors.toList());
        if (candidateList.contains(this.localID)) {
            messageManager.sendLeaderMessage(this.localID, candidateList.get(0));
        } else {
            content += "," + localID;
            messageManager.sendElectionMessage(this.localID, content);
        }
    }

    private void handleLeaderMessage(Message message) {

    }

    @Override
    public boolean isAlive() {
        return alive;
    }

    @Override
    public void setAlive(boolean alive) {
        this.alive = alive;
    }
}
