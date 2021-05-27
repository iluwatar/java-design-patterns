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

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import lombok.Data;
import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;

public class MessageDataTestTest {
	
	@Data
	class UsageDetail {

	  private String userId;

	  private long duration;

	  private long data;

	}
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
		
		EqualsVerifier.forClass( MessageData.class )
        .suppress( Warning.STRICT_INHERITANCE ).suppress(Warning.NONFINAL_FIELDS)
        .verify();
		assertEquals(true,this.messageData.equals(this.messageData));
	}
	
	@Test
	public void testNotEqualsObject1() {
		assertEquals(false,this.messageData.equals(new Object()));
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
