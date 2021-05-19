package com.iluwatar.producer.calldetails.domain;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;

import com.iluwatar.producer.calldetails.domain.UsageDetail;

public class UsageDetailTestTest {

	private UsageDetail usageDetail;
	
	public UsageDetailTestTest() {
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

		UsageDetail usageDetail1 = new UsageDetail();
		usageDetail1.setData(1);
		usageDetail1.setDuration(1);
		usageDetail1.setUserId("Marry");
		
		assertEquals(this.usageDetail.hashCode(), usageDetail1.hashCode());
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
	public void testToString() {
		assertNotNull(this.usageDetail.toString());
	}

	@Test
	public void testUsageDetail() {
		assertNotNull(new UsageDetail());
	}
}