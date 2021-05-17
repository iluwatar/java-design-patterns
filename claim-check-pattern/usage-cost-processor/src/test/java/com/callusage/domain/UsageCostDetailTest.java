package com.callusage.domain;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;

public class UsageCostDetailTest {
	
	private UsageCostDetail usageCostDetail;

	@Before
	public void setUp() throws Exception {
		this.usageCostDetail = new UsageCostDetail();
		this.usageCostDetail.setUserId("Sam");
		this.usageCostDetail.setCallCost(1.0);
		this.usageCostDetail.setDataCost(1.0);
		
	}

	@Test
	public void testHashCode() {
		assertNotNull(this.usageCostDetail.hashCode());
	}

	@Test
	public void testGetUserId() {
		assertEquals("Sam", this.usageCostDetail.getUserId());
	}

	@Test
	public void testGetCallCost() {
		assertEquals(1.0, this.usageCostDetail.getCallCost(),0);
	}

	@Test
	public void testGetDataCost() {
		assertEquals(1.0, this.usageCostDetail.getDataCost(),0);
	}

	@Test
	public void testSetUserId() {
		try {
			this.usageCostDetail.setUserId("Marry");
		} catch (Exception e) {
			fail("Setting userid failed: "+e.getMessage());
		}
	}

	@Test
	public void testSetCallCost() {
		try {
			this.usageCostDetail.setCallCost(1.0);
		} catch (Exception e) {
			fail("Setting call cost failed: "+e.getMessage());
		}
	}

	@Test
	public void testSetDataCost() {
		try {
			this.usageCostDetail.setDataCost(1.0);
		} catch (Exception e) {
			fail("Setting data cost failed: "+e.getMessage());
		}
	}

	@Test
	public void testEqualsObject() {
		assertEquals(true,this.usageCostDetail.equals(this.usageCostDetail));
	}

	@Test
	public void testCanEqual() {
		assertEquals(true,this.usageCostDetail.canEqual(this.usageCostDetail));
	}

	@Test
	public void testToString() {
		assertNotNull(this.usageCostDetail.toString());
	}

	@Test
	public void testUsageCostDetail() {
		assertNotNull(new UsageCostDetail());
	}

}
