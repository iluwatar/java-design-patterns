package com.iluwatar.event.sourcing.api;

/**
 * Created by serdarh on 06.08.2017.
 */
public interface ProcessorJournal {
    void write(DomainEvent domainEvent);
    void reset();
    DomainEvent readNext();
}
