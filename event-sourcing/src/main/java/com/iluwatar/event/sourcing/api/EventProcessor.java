package com.iluwatar.event.sourcing.api;

/**
 * Created by serdarh on 06.08.2017.
 */
public interface EventProcessor {
    void process(DomainEvent domainEvent);
    void setPrecessorJournal(ProcessorJournal precessorJournal);
    void addExternalEventListener(ExternalEventListener externalEventListener);
    void recover();
}
