package com.callusage.application;

import static org.junit.Assert.fail;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.MessageBuilder;
import com.callusage.domain.MessageHeader;
import com.callusage.domain.UsageDetail;
import com.callusage.interfaces.IPersistentCommonStorageUtility;

public class UsageDetailSenderTest {

	@Mock
	private IPersistentCommonStorageUtility persistentCommonStorageUtility;
	
	@Mock 
	private MessageChannel messageChannel;
	
	@Mock 
	private Source source;
	
	@InjectMocks
	private UsageDetailSender usageDetailSender;
	
	private MessageHeader messageHeader;
	private UsageDetail usageDetail;
//	private Message<UsageDetail> messageUsageDetail;
//	private Message<UsageDetail> messageUsageCostDetail;
	
	
	@Before
	public void setUp() throws Exception {
		
		MockitoAnnotations.initMocks(this);
		
		this.messageHeader = new MessageHeader();
		this.messageHeader.setDataFileName("input.json");
		this.messageHeader.setDataLocation(null);
		this.messageHeader.setOperataionName(null);
		
		this.usageDetail = new UsageDetail();
		this.usageDetail.setUserId("Alan");
		this.usageDetail.setData(1);
		this.usageDetail.setDuration(10);
		
		
//		this.messageUsageDetail = new Message<>(messageHeader, new MessageData<UsageDetail>(this.usageDetail));

//		this.messageUsageCostDetail = new Message<>(messageHeader, null);
		
	}



	@Test
	public void testSetCount() {
		
		try {
			UsageDetailSender.setCount(0);
		}catch(Exception e) {
		     fail("SetCount method failed: "+e.getStackTrace());
		}
	}

	@Test
	public void testSendEvents() {

		doNothing().when(this.persistentCommonStorageUtility).dropMessageToPersistentStorage(null);
		
		
		try {
			when(this.source.output()).thenReturn(this.messageChannel);
			
			when(this.messageChannel.send(MessageBuilder.withPayload(this.messageHeader).build())).thenReturn(true);
			
			this.usageDetailSender.sendEvents();
			
		} catch (Exception e) {
			fail("Sending Events to kafka topic failed: "+e.getMessage());
		}
		
	}

}
