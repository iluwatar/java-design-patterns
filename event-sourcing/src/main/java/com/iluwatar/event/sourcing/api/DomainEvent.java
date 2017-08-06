package com.iluwatar.event.sourcing.api;

import java.io.Serializable;

/**
 * Created by serdarh on 06.08.2017.
 */
public abstract class DomainEvent implements Serializable {
    private final long sequenceId;
    private final long createdTime;
    private boolean replica = false;
    private final String eventClassName;

    public DomainEvent(long sequenceId, long createdTime, String eventClassName) {
        this.sequenceId = sequenceId;
        this.createdTime = createdTime;
        this.eventClassName = eventClassName;
    }

    public long getSequenceId() {
        return sequenceId;
    }

    public long getCreatedTime() {
        return createdTime;
    }

    public boolean isReplica() {
        return replica;
    }

    public void setReplica(boolean replica) {
        this.replica = replica;
    }

    public abstract void process();

    public String getEventClassName() {
        return eventClassName;
    }
}
