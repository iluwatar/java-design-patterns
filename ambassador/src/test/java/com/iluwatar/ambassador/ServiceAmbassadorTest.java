package com.iluwatar.ambassador;

import org.junit.jupiter.api.Test;

public class ServiceAmbassadorTest {

    @Test
    public void test() {
        ServiceAmbassador ambassador = new ServiceAmbassador();
        long result = ambassador.doRemoteFunction(10);
        assert result == 100 || result == -1;
    }
}
