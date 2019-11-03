package com.iluwatar.saga.orchestration;

import com.iluwatar.saga.ServiceDiscoveryService;
import org.junit.Assert;
import org.junit.Test;

public class SagaOrchestratorTest {

    @Test
    public void execute() {
        SagaOrchestrator sagaOrchestrator = new SagaOrchestrator(newSaga(), serviceDiscovery());
        Saga.Result badOrder = sagaOrchestrator.execute("bad_order");
        Saga.Result crashedOrder = sagaOrchestrator.execute("crashed_order");

        Assert.assertEquals(badOrder, Saga.Result.ROLLBACK);
        Assert.assertEquals(crashedOrder, Saga.Result.CRASHED);
    }

    private static Saga newSaga() {
        return Saga
                .create()
                .chapter("init an order")
                .chapter("booking a Fly")
                .chapter("booking a Hotel")
                .chapter("withdrawing Money");
    }

    private static ServiceDiscoveryService serviceDiscovery() {
        return
                new ServiceDiscoveryService()
                        .discover(new OrderService())
                        .discover(new FlyBookingService())
                        .discover(new HotelBookingService())
                        .discover(new WithdrawMoneyService());
    }
}