package com.callusage.application;


import static org.junit.Assert.fail;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.callusage.domain.Message;
import com.callusage.domain.MessageData;
import com.callusage.domain.MessageHeader;
import com.callusage.domain.UsageCostDetail;
import com.callusage.domain.UsageDetail;
import com.callusage.interfaces.IPersistentCommonStorageUtility;

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
