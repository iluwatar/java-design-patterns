package com.callusage.domain;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;

public class MessageDataTest {

	private UsageDetail usageDetail;
	private MessageData<UsageDetail> messageData;
	
	@Before
	public void setUp() throws Exception {
		
		this.usageDetail = new UsageDetail();
		this.usageDetail.setData(1);
		this.usageDetail.setDuration(1);
		this.usageDetail.setUserId("Marry");
		
		this.messageData = new MessageData<UsageDetail>(this.usageDetail);
	}

	@Test
	public void testHashCode() {
		assertNotNull(this.messageData.hashCode());
	}

	@Test
	public void testGetData() {
		assertNotNull(this.messageData.getData());
	}

	@Test
	public void testSetData() {
		try {
			this.messageData.setData(this.usageDetail);
		} catch (Exception e) {
			fail("Setting data failed: "+e.getMessage());
		}
	}

	@Test
	public void testEqualsObject() {
		assertEquals(true,this.messageData.equals(this.messageData));
	}

	@Test
	public void testCanEqual() {
		assertEquals(true,this.messageData.canEqual(this.messageData));
	}

	@Test
	public void testToString() {
		assertNotNull(this.messageData.toString());
	}

	@Test
	public void testMessageData() {
		assertNotNull(new MessageData<UsageDetail>(this.usageDetail));
	}

}
