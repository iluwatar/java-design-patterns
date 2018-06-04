package com.iluwatar.ambassador;

import org.junit.jupiter.api.Test;

public class ClientTest {

    @Test
    public void test() {

        Client client = new Client();
        long result = client.useService(10);
        assert result == 100 || result == -1;

    }
}
