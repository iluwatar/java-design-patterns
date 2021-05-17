package com.callusage.domain;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;

public class MessageTest {

	private MessageHeader messageHeader;
	private UsageDetail usageDetail;
	private MessageData<UsageDetail> messageData;
	private Message<UsageDetail> message;

	@Before
	public void setUp() throws Exception {
		this.messageHeader = new MessageHeader();
		this.messageHeader.setDataFileName("input.json");
		this.messageHeader.setDataLocation("C://tmp");
		this.messageHeader.setOperataionName("Cost Calculator");
		
		this.usageDetail = new UsageDetail();
		this.usageDetail.setData(1);
		this.usageDetail.setDuration(1);
		this.usageDetail.setUserId("Marry");
		
		this.messageData = new MessageData<UsageDetail>(this.usageDetail);
		
		this.message = new Message<UsageDetail>(this.messageHeader, this.messageData);
	}

	@Test
	public void testHashCode() {
		assertNotNull(this.message.hashCode());
	}

	@Test
	public void testGetMessageHeader() {
		assertNotNull(this.message.getMessageHeader());
	}

	@Test
	public void testGetMessageData() {
		assertNotNull(this.message.getMessageData());
	}

	@Test
	public void testSetMessageHeader() {
		try {
			this.message.setMessageHeader(this.messageHeader);
		} catch (Exception e) {
			fail("Setting message header failed: "+e.getMessage());
		}
	}
	
	

	@Test
	public void testSetMessageData() {
			this.message.setMessageData(this.messageData);
		
	}

	@Test
	public void testEqualsObject() {
		assertEquals(true,this.message.equals(this.message));
	}
	
	@Test
	public void testNotEqualsObject() {
		assertEquals(false,this.message.equals(null));
	}
	
	@Test
	public void testNotEqualsObject1() {
		assertEquals(false,this.message.equals(new Message<UsageDetail>(this.messageHeader,null)));
	}
	
	@Test
	public void testNotEqualsObject2() {
		assertEquals(false,this.message.equals(new Message<UsageDetail>(null,this.messageData)));
	}

	@Test
	public void testCanEqual() {
		assertEquals(true,this.message.canEqual(this.message));
	}

	@Test
	public void testToString() {
		assertNotNull(this.message.toString());
	}

	@Test
	public void testMessage() {
		assertNotNull(new Message<UsageDetail>(this.messageHeader, this.messageData));
	}

}
