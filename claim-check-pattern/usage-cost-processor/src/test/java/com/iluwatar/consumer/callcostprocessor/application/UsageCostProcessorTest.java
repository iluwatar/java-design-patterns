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

package com.iluwatar.consumer.callcostprocessor.application;


import static org.junit.Assert.fail;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.iluwatar.consumer.callcostprocessor.application.UsageCostProcessor;
import com.iluwatar.consumer.callcostprocessor.domain.UsageCostDetail;
import com.iluwatar.consumer.callcostprocessor.domain.UsageDetail;
import com.iluwatar.utilitylibrary.domain.Message;
import com.iluwatar.utilitylibrary.domain.MessageData;
import com.iluwatar.utilitylibrary.domain.MessageHeader;
import com.iluwatar.utilitylibrary.interfaces.IPersistentCommonStorageUtility;

public class UsageCostProcessorTest {
	
	@Mock
	private IPersistentCommonStorageUtility<UsageDetail> persistentCommonStorageUtilityForUsageDetail;
	@Mock
	private IPersistentCommonStorageUtility<UsageCostDetail> persistentCommonStorageUtilityForUsageCostDetail;
	
	@InjectMocks
	private UsageCostProcessor usageCostProcessor;
	
	private MessageHeader messageHeader;
	private UsageDetail usageDetail;
	private Message<UsageDetail> messageUsageDetail;
	private Message<UsageCostDetail> messageUsageCostDetail;
	
	
	@Before
	public void setUp() throws Exception {
		
		MockitoAnnotations.initMocks(this);
		
		this.messageHeader = new MessageHeader();
		this.messageHeader.setDataFileName(null);
		this.messageHeader.setDataLocation(null);
		this.messageHeader.setOperataionName(null);
		
		this.usageDetail = new UsageDetail();
		this.usageDetail.setUserId("Alan");
		this.usageDetail.setData(1);
		this.usageDetail.setDuration(10);
		
		
		this.messageUsageDetail = new Message<>(messageHeader, new MessageData<UsageDetail>(this.usageDetail));

		this.messageUsageCostDetail = new Message<>(messageHeader, null);
		
		
	}

	@Test
	public void testProcessUsageCostNotThrowingAnyException() {
		
		when(this.persistentCommonStorageUtilityForUsageDetail.readMessageFromPersistentStorage(messageHeader))
				.thenReturn(this.messageUsageDetail);
		
		doNothing().when(this.persistentCommonStorageUtilityForUsageCostDetail).dropMessageToPersistentStorage(messageUsageCostDetail);
		
		try {
			this.usageCostProcessor.processUsageCost(messageHeader);
		} catch (Exception e) {
			fail("Exception thrown: "+e.getMessage());
		}
		
		
	}

}
