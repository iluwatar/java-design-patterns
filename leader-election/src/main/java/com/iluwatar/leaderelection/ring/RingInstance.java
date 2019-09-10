package com.iluwatar.leaderelection.ring;

import com.iluwatar.leaderelection.Instance;
import com.iluwatar.leaderelection.Message;
import com.iluwatar.leaderelection.MessageManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RingInstance implements Instance {

    private static final Logger LOGGER = LoggerFactory.getLogger(RingInstance.class);

    private MessageManager messageManager;

    private final int localID;

    private int leaderID;

    private boolean alive;

    public RingInstance(MessageManager messageManager, int localID, int leaderID, boolean alive) {
        this.messageManager = messageManager;
        this.localID = localID;
        this.leaderID = leaderID;
        this.alive = alive;
    }

    @Override
    public void onMessage(Message message) {
        if (message instanceof RingMessage) {
            switch (((RingMessage) message).getType()) {
                case Election:
                    LOGGER.info("Instance " + localID + ": Election Message Received");
                    this.handleElectionMessage();
                    break;
                case Leader:
                    LOGGER.info("Instance " + localID + ": Leader Message Received");
                    this.handleLeaderMessage();
                    break;
                case Heartbeat:
                    LOGGER.info("Instance " + localID + ": Heartbeat Message Received");
                    this.handleHeartbeatMessage();
                    break;
            }
        }
    }

    private void handleHeartbeatMessage() {
        boolean isLeaderAlive = messageManager.sendHeartbeatMessageToLeader(this.leaderID);
        if (isLeaderAlive) {

        } else {

        }
    }

    private void handleElectionMessage() {

    }

    private void handleLeaderMessage() {

    }

    private boolean isLeader() {
        return localID == leaderID;
    }

    @Override
    public boolean isAlive() {
        return alive;
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }
}
