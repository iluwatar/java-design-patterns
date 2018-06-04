package com.iluwatar.ambassador;

import org.junit.jupiter.api.Test;

public class RemoteServiceTest {

    @Test
    public void test() {

        RemoteService remoteService = RemoteService.getRemoteService();
        long result = remoteService.doRemoteFunction(10);

        assert result == 100 || result == -1;
    }

}
