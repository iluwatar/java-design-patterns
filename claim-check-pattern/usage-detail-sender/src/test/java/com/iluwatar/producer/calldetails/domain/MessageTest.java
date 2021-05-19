package com.iluwatar.producer.calldetails.domain;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;

import com.iluwatar.producer.calldetails.domain.Message;
import com.iluwatar.producer.calldetails.domain.MessageData;
import com.iluwatar.producer.calldetails.domain.MessageHeader;
import com.iluwatar.producer.calldetails.domain.UsageDetail;

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
		
		MessageHeader messageHeader1 = new MessageHeader();
		messageHeader1.setDataFileName("input.json");
		messageHeader1.setDataLocation("C://tmp");
		messageHeader1.setOperataionName("Cost Calculator");
		
		UsageDetail usageDetail1 = new UsageDetail();
		usageDetail1.setData(1);
		usageDetail1.setDuration(1);
		usageDetail1.setUserId("Marry");
		
		MessageData<UsageDetail> messageData1 = new MessageData<UsageDetail>(usageDetail);
		
		Message message1 = new Message<UsageDetail>(messageHeader1, messageData1);
		
		assertEquals(this.message.hashCode(), message1.hashCode());
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
		try {
			this.message.setMessageData(this.messageData);
		}catch(Exception e) {
			fail("Setting message body failed: "+e.getMessage());
		}
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
	public void testToString() {
		assertNotNull(this.message.toString());
	}

	@Test
	public void testMessage() {
		assertNotNull(new Message<UsageDetail>(this.messageHeader, this.messageData));
	}

}
