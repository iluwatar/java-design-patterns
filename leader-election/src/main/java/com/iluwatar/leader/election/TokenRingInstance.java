package com.iluwatar.leader.election;

public class TokenRingInstance implements Instance, Runnable {

    private MessageManager messageManager;

    private int localID;

    private int leaderID;

    private boolean alive;

    public TokenRingInstance() {
    }

    public TokenRingInstance(int localID, int leaderID) {
        this.localID = localID;
        this.leaderID = leaderID;
        this.alive = true;
    }

    public TokenRingInstance(MessageManager messageManager, int localID, int leaderID, boolean alive) {
        this.messageManager = messageManager;
        this.localID = localID;
        this.leaderID = leaderID;
        this.alive = alive;
    }

    @Override
    public void run() {
        while (true) {
            boolean isLeaderAlive = messageManager.sendHealthCheckMessageToLeader(this.leaderID);
            if (isLeaderAlive) {
                System.out.println(localID + " alive");
            } else {
                System.out.println(localID + " not alive");

            }
        }
    }

    @Override
    public boolean checkIfLeaderIsAlive() {
        boolean isAlive = messageManager.sendHealthCheckMessageToLeader(leaderID);
        return isAlive;
    }

    public int getLocalID() {
        return localID;
    }

    public void setLocalID(int localID) {
        this.localID = localID;
    }

    public int getLeaderID() {
        return leaderID;
    }

    public void setLeaderID(int leaderID) {
        this.leaderID = leaderID;
    }

    public MessageManager getMessageManager() {
        return messageManager;
    }

    public void setMessageManager(MessageManager messageManager) {
        this.messageManager = messageManager;
    }

    @Override
    public boolean isAlive() {
        return alive;
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }
}
