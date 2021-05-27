/*
 * The MIT License
 * Copyright © 2014-2021 Ilkka Seppälä
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package com.iluwatar.utilitylibrary.domain;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;
import org.junit.Before;
import org.junit.Test;
import lombok.Data;
import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;

public class MessageTest {
	@Data
	class UsageDetail {

	  private String userId;

	  private long duration;

	  private long data;

	}
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
		
		EqualsVerifier.forClass( Message.class )
        .suppress( Warning.STRICT_INHERITANCE ).suppress(Warning.NONFINAL_FIELDS)
        .verify();
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
