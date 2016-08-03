package com.iluwatar.servicestub;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

public class PortfolioTest {

	
	/**
     * Verify that the portfolio is not null.
     */
	@Test
    public void testPortfolio()
    {
		final Portfolio portfolio = new Portfolio();
		assertNotNull( portfolio );
    }
}
