package com.callusage.domain;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;

public class UsageDetailTest {

	private UsageDetail usageDetail;
	
	public UsageDetailTest() {
	}
	
	@Before
	public void setUp() throws Exception {
		this.usageDetail = new UsageDetail();
		this.usageDetail.setData(1);
		this.usageDetail.setDuration(1);
		this.usageDetail.setUserId("Marry");
	}

	@Test
	public void testHashCode() {

		assertNotNull(this.usageDetail.hashCode());
	}

	@Test
	public void testGetUserId() {
		assertEquals("Marry", this.usageDetail.getUserId());
	}

	@Test
	public void testGetDuration() {
		assertEquals(1, this.usageDetail.getDuration());
	}

	@Test
	public void testGetData() {
		assertEquals(1, this.usageDetail.getData());
	}

	@Test
	public void testSetUserId() {
		try {
			this.usageDetail.setUserId("Marry");
		} catch (Exception e) {
			fail("Setting userid failed: "+e.getMessage());
		}
	}

	@Test
	public void testSetDuration() {
		try {
			this.usageDetail.setDuration(1);
		} catch (Exception e) {
			fail("Setting duration failed: "+e.getMessage());
		}
	}

	@Test
	public void testSetData() {
		try {
			this.usageDetail.setData(1);
		} catch (Exception e) {
			fail("Setting data failed: "+e.getMessage());
		}
	}

	@Test
	public void testEqualsObject() {
		assertEquals(true,this.usageDetail.equals(this.usageDetail));
	}
	
	@Test
	public void testNotEqualsObject() {
		assertEquals(false,this.usageDetail.equals(null));
	}
	
	@Test
	public void testNotEqualsObject1() {
		assertEquals(false,this.usageDetail.equals(new Object()));
	}
	
	@Test
	public void testNotEqualsObject2() {
		UsageDetail tempUsageDetail = new UsageDetail();
		tempUsageDetail.setData(10);
		tempUsageDetail.setDuration(1);
		tempUsageDetail.setUserId("Marry");
		assertEquals(false,this.usageDetail.equals(tempUsageDetail));
	}
	
	@Test
	public void testNotEqualsObject3() {
		UsageDetail tempUsageDetail = new UsageDetail();
		tempUsageDetail.setData(1);
		tempUsageDetail.setDuration(10);
		tempUsageDetail.setUserId("Marry");
		assertEquals(false,this.usageDetail.equals(tempUsageDetail));
	}
	
	@Test
	public void testNotEqualsObject4() {
		UsageDetail tempUsageDetail = new UsageDetail();
		tempUsageDetail.setData(1);
		tempUsageDetail.setDuration(1);
		tempUsageDetail.setUserId("Marry Com");
		assertEquals(false,this.usageDetail.equals(tempUsageDetail));
	}

	@Test
	public void testCanEqual() {
		assertEquals(true,this.usageDetail.canEqual(this.usageDetail));
	}

	@Test
	public void testToString() {
		assertNotNull(this.usageDetail.toString());
	}

	@Test
	public void testUsageDetail() {
		assertNotNull(new UsageDetail());
	}
}
