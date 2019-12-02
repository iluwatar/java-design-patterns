package com.iluwatar.api.gateway;

import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class AppTest {
    @Test
    public void testApp() throws IOException {
        String[] args = {};
        App.main(args);
    }

    @Test
    public void testImageClientImpl() {
        ImageClient imageClient = new ImageClientImpl();

        assertNull(imageClient.getImagePath());
    }

    @Test
    public void testPriceClientImpl() {
        PriceClient priceClient = new PriceClientImpl();

        assertNull(priceClient.getPrice());
    }
}
