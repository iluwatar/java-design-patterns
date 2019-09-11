package com.iluwatar.leaderelection.ring;

import com.iluwatar.leaderelection.Instance;
import com.iluwatar.leaderelection.Message;
import com.iluwatar.leaderelection.MessageManager;

import java.util.Arrays;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.stream.Collectors;

public class RingInstance implements Instance, Runnable {

    private MessageManager messageManager;
    private Queue<Message> messageQueue;
    private final int localID;
    private int leaderID;
    private boolean alive;

    public RingInstance(MessageManager messageManager, int localID, int leaderID) {
        this.messageManager = messageManager;
        this.messageQueue = new ConcurrentLinkedQueue<>();
        this.localID = localID;
        this.leaderID = leaderID;
        this.alive = true;
    }

    @Override
    public void run() {
        while (true) {
            if (!messageQueue.isEmpty()) {
                this.processMessage(messageQueue.remove());
            }
            System.out.flush();
        }
    }

    @Override
    public void onMessage(Message message) {
        messageQueue.offer(message);
    }

    private void processMessage(Message message) {
        switch (message.getType()) {
            case ELECTION:
                System.out.println("Instance " + localID + " - Election Message handling...");
                this.handleElectionMessage(message);
                break;
            case LEADER:
                System.out.println("Instance " + localID + " - Leader Message handling...");
                this.handleLeaderMessage(message);
                break;
            case HEARTBEAT_INVOKE:
                System.out.println("Instance " + localID + " - Heartbeat Message handling...");
                this.handleHeartbeatMessage(message);
                break;
        }
    }

    private void handleHeartbeatMessage(Message message) {
        boolean isLeaderAlive = messageManager.sendHeartbeatMessage(this.leaderID);
        if (isLeaderAlive) {
            System.out.println("Instance " + localID + "- Leader is alive.");
            messageManager.sendHeartbeatInvokeMessage(this.localID);
        } else {
            System.out.println("Instance " + localID + "- Leader is not alive. Start election.");
            messageManager.sendElectionMessage(this.localID, String.valueOf(localID));
        }
    }

    private void handleElectionMessage(Message message) {
        String content = message.getContent();
        System.out.println("Instance " + localID + " - Election Message: " + content);
        List<Integer> candidateList =
                Arrays.stream(content.trim().split(","))
                        .map(Integer::valueOf)
                        .sorted()
                        .collect(Collectors.toList());
        if (candidateList.contains(this.localID)) {
            System.out.println("Instance " + localID + " - New leader should be " + candidateList.get(0) + ". Start leader notification.");
            messageManager.sendLeaderMessage(this.localID, candidateList.get(0));
        } else {
            content += "," + localID;
            messageManager.sendElectionMessage(this.localID, content);
        }
    }

    private void handleLeaderMessage(Message message) {
        int newLeaderID = Integer.valueOf(message.getContent());
        if (this.leaderID != newLeaderID) {
            System.out.println("Instance " + localID + " - Update leaderID");
            this.leaderID = newLeaderID;
            messageManager.sendLeaderMessage(this.localID, newLeaderID);
        } else {
            System.out.println("Instance " + localID + " - Leader update done. Start heartbeat.");
            messageManager.sendHeartbeatInvokeMessage(this.localID);
        }
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
