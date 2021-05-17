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
		
		UsageDetail usageDetail1 = new UsageDetail();
		usageDetail1.setData(1);
		usageDetail1.setDuration(1);
		usageDetail1.setUserId("Marry");
		MessageData<UsageDetail> messageData1 = new MessageData<UsageDetail>(this.usageDetail);

		assertEquals(this.messageData.hashCode(), messageData1.hashCode());
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
	public void testNotEqualsObject1() {
		assertEquals(false,this.messageData.equals(new Object()));
	}
	
	@Test
	public void testNotEqualsObject2() {
		UsageDetail usageDetail1 = new UsageDetail();
		usageDetail1.setData(12);
		usageDetail1.setDuration(1);
		usageDetail1.setUserId("Marry");
		MessageData<UsageDetail> messageData1 = new MessageData<UsageDetail>(usageDetail1);
		
		assertEquals(false,this.messageData.equals(messageData1));
	}
	
	@Test
	public void testNotEqualsObject3() {
		UsageDetail usageDetail1 = new UsageDetail();
		usageDetail1.setData(1);
		usageDetail1.setDuration(12);
		usageDetail1.setUserId("Marry");
		MessageData<UsageDetail> messageData1 = new MessageData<UsageDetail>(usageDetail1);
		
		assertEquals(false,this.messageData.equals(messageData1));
	}
	
	@Test
	public void testNotEqualsObject4() {
		UsageDetail usageDetail1 = new UsageDetail();
		usageDetail1.setData(1);
		usageDetail1.setDuration(1);
		usageDetail1.setUserId("Marry Com");
		MessageData<UsageDetail> messageData1 = new MessageData<UsageDetail>(usageDetail1);
		
		assertEquals(false,this.messageData.equals(messageData1));
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
