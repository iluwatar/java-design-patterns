package com.hungrydev399.publishsubscribe;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.AfterAll;
import com.hungrydev399.publishsubscribe.jms.JmsUtil;

/**
 * Base class for JMS-related tests.
 * 
 * Provides:
 * - Common JMS broker initialization
 * - Resource cleanup after tests
 * - Shared configuration for all JMS tests
 * 
 * Usage:
 * - Extend this class in any test that needs JMS functionality
 * - Ensures consistent JMS lifecycle across test classes
 * - Prevents connection/broker issues between tests
 */
public abstract class TestBase {
    @BeforeAll
    static void initializeJms() {
        JmsUtil.initialize();
    }

    @AfterAll
    static void cleanupJms() {
        JmsUtil.closeConnection();
    }
}
