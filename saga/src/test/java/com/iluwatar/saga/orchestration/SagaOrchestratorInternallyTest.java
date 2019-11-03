package com.iluwatar.saga.orchestration;

import com.iluwatar.saga.ServiceDiscoveryService;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class SagaOrchestratorInternallyTest {

    private List<String> records = new ArrayList<>();

    @Test
    public void execute() {
        SagaOrchestrator sagaOrchestrator = new SagaOrchestrator(newSaga(), serviceDiscovery());
        Saga.Result result = sagaOrchestrator.execute(1);
        Assert.assertEquals(result, Saga.Result.ROLLBACK);
        Assert.assertArrayEquals(
                records.toArray(new String[]{}),
                new String[]{"+1","+2","+3","+4","-4","-3","-2","-1"});
    }

    private static Saga newSaga() {
        return Saga
                .create()
                .chapter("1")
                .chapter("2")
                .chapter("3")
                .chapter("4");
    }

    private ServiceDiscoveryService serviceDiscovery() {
        return
                new ServiceDiscoveryService()
                        .discover(new Service1())
                        .discover(new Service2())
                        .discover(new Service3())
                        .discover(new Service4());
    }

    class Service1 extends OrchestrationService<Integer> {

        @Override
        public String getName() {
            return "1";
        }

        @Override
        public ChapterResult<Integer> process(Integer value) {
            records.add("+1");
            return ChapterResult.success(value);
        }

        @Override
        public ChapterResult<Integer> rollback(Integer value) {
            records.add("-1");
            return ChapterResult.success(value);
        }
    }

    class Service2 extends OrchestrationService<Integer> {

        @Override
        public String getName() {
            return "2";
        }
        @Override
        public ChapterResult<Integer> process(Integer value) {
            records.add("+2");
            return ChapterResult.success(value);
        }

        @Override
        public ChapterResult<Integer> rollback(Integer value) {
            records.add("-2");
            return ChapterResult.success(value);
        }
    }

    class Service3 extends OrchestrationService<Integer> {

        @Override
        public String getName() {
            return "3";
        }
        @Override
        public ChapterResult<Integer> process(Integer value) {
            records.add("+3");
            return ChapterResult.success(value);
        }

        @Override
        public ChapterResult<Integer> rollback(Integer value) {
            records.add("-3");
            return ChapterResult.success(value);
        }
    }

    class Service4 extends OrchestrationService<Integer> {

        @Override
        public String getName() {
            return "4";
        }
        @Override
        public ChapterResult<Integer> process(Integer value) {
            records.add("+4");
            return ChapterResult.failure(value);
        }

        @Override
        public ChapterResult<Integer> rollback(Integer value) {
            records.add("-4");
            return ChapterResult.success(value);
        }
    }
}