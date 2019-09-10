package com.iluwatar.leaderelection.ring;

import com.iluwatar.leaderelection.Instance;
import com.iluwatar.leaderelection.Message;
import com.iluwatar.leaderelection.MessageManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.PriorityQueue;
import java.util.Queue;

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
        if (message instanceof RingMessage) {
            switch (((RingMessage) message).getType()) {
                case ELECTION:
                    LOGGER.info("Instance " + localID + ": Election Message handling...");
                    this.handleElectionMessage();
                    break;
                case LEADER
                        :
                    LOGGER.info("Instance " + localID + ": Leader Message handling...");
                    this.handleLeaderMessage();
                    break;
                case HEARTBEAT_INVOKE:
                    LOGGER.info("Instance " + localID + ": Heartbeat Message handling...");
                    this.handleHeartbeatMessage();
                    break;
            }
        }
    }

    private void handleHeartbeatMessage() {
        boolean isLeaderAlive = messageManager.sendHeartbeatMessage(this.leaderID);
        if (isLeaderAlive) {
            messageManager.sendHeartbeatInvokeMessage(this.localID);
        } else {
            messageManager.sendElectionMessage(this.localID, String.valueOf(localID));
        }
    }

    private void handleElectionMessage() {

    }

    private void handleLeaderMessage() {

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
