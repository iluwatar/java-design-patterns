package com.iluwatar.event.sourcing.processor;

import com.iluwatar.event.sourcing.api.DomainEvent;
import com.iluwatar.event.sourcing.api.EventProcessor;
import com.iluwatar.event.sourcing.api.ExternalEventListener;
import com.iluwatar.event.sourcing.api.ProcessorJournal;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by serdarh on 06.08.2017.
 */
public class DomainEventProcessor implements EventProcessor {

    private ProcessorJournal precessorJournal;
    private List<ExternalEventListener> externalEventListeners = new ArrayList<>();

    @Override
    public void process(DomainEvent domainEvent) {
            externalEventListeners.forEach(externalEventListener -> externalEventListener.notify(domainEvent));
            domainEvent.process();
            precessorJournal.write(domainEvent);
    }

    @Override
    public void setPrecessorJournal(ProcessorJournal precessorJournal) {
        this.precessorJournal = precessorJournal;
    }

    @Override
    public void addExternalEventListener(ExternalEventListener externalEventListener) {
        externalEventListeners.add(externalEventListener);
    }

    @Override
    public void recover() {
        DomainEvent domainEvent;
        while(true) {
            domainEvent = precessorJournal.readNext();
            if(domainEvent==null){
                break;
            }else{
                domainEvent.process();
            }
        }
    }
}
