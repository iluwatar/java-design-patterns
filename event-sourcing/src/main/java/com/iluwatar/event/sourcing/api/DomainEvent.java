package com.iluwatar.event.sourcing.api;

import java.io.Serializable;

/**
 * Created by serdarh on 06.08.2017.
 */
public abstract class DomainEvent implements Serializable {
    private final long sequenceId;
    private final long createdTime;
    private boolean realTime = true;
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

    public boolean isRealTime() {
        return realTime;
    }

    public void setRealTime(boolean realTime) {
        this.realTime = realTime;
    }

    public abstract void process();

    public String getEventClassName() {
        return eventClassName;
    }
}
