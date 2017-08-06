package com.iluwatar.event.sourcing.service;

/**
 * Created by serdarh on 06.08.2017.
 */
public class SequenceIdGenerator {
    private static long sequenceId = 0L;

    public static long nextSequenceId(){
        sequenceId++;
        return sequenceId;
    }

}
