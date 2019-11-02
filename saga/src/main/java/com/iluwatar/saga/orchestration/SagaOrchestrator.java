package com.iluwatar.saga.orchestration;

import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class SagaOrchestrator {

    private ExecutorService executor;
    private AtomicBoolean isNormalFlow;
    private AtomicBoolean isFinished;
    private AtomicInteger currentState;
    private final Saga saga;

    public SagaOrchestrator(Saga saga) {
        this.saga = saga;
        this.isNormalFlow = new AtomicBoolean(true);
        this.isFinished = new AtomicBoolean(false);
        this.currentState = new AtomicInteger(0);
        this.executor = Executors.newFixedThreadPool(20);
    }

    public <K> Saga.Result kickOff(K value) {

    }


}
