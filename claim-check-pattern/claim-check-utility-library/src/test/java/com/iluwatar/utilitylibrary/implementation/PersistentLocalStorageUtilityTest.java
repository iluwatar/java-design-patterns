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

package com.iluwatar.utilitylibrary.implementation;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;

import com.iluwatar.utilitylibrary.domain.Message;
import com.iluwatar.utilitylibrary.domain.MessageData;
import com.iluwatar.utilitylibrary.domain.MessageHeader;
import com.iluwatar.utilitylibrary.interfaces.IPersistentCommonStorageUtility;

import lombok.Data;

public class PersistentLocalStorageUtilityTest {

	@Data
	class UsageDetail {

	  private String userId;

	  private long duration;

	  private long data;

	}
	
	private UsageDetail usageDetail;
	private MessageHeader messageHeader;
	private Message<UsageDetail> messageForUsageDetail;
	
	private IPersistentCommonStorageUtility persistentCommonStorageUtility; 
	
	@Before
	public void setUp() throws Exception {
 
		this.messageHeader = new MessageHeader();
		this.messageHeader.setDataFileName("input.json");
		this.messageHeader.setDataLocation("C://tmp");
		this.messageHeader.setOperataionName("Testing of PersistentLocalStorageUtility class");
		
		this.usageDetail = new UsageDetail();
		this.usageDetail.setUserId("Alan");
		this.usageDetail.setData(1);
		this.usageDetail.setDuration(10);
		
		this.messageForUsageDetail = new Message<>(messageHeader, new MessageData<UsageDetail>(this.usageDetail));
		
		this.persistentCommonStorageUtility = new PersistentLocalStorageUtility();
		
		
	}
	
	@Test
	public void testDropMessageToPersistentStorage() {
		
		try {
			this.persistentCommonStorageUtility.dropMessageToPersistentStorage(this.messageForUsageDetail);
		}catch(Exception e) {

			fail("File drop failed : "+e.getStackTrace());
		}
	}

	@Test
	public void testReadMessageFromPersistentStorage() {
		
		this.persistentCommonStorageUtility.dropMessageToPersistentStorage(this.messageForUsageDetail);
		
		try {
			Message message = this.persistentCommonStorageUtility.readMessageFromPersistentStorage(this.messageHeader);
			assertNotNull(message);
		}catch(Exception e) {

			fail("File read failed : "+e.getStackTrace());
		}
	}

	

}
