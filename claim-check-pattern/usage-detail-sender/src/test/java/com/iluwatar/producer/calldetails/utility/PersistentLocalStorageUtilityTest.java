package com.iluwatar.producer.calldetails.utility;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;

import com.iluwatar.producer.calldetails.domain.Message;
import com.iluwatar.producer.calldetails.domain.MessageData;
import com.iluwatar.producer.calldetails.domain.MessageHeader;
import com.iluwatar.producer.calldetails.domain.UsageDetail;
import com.iluwatar.producer.calldetails.interfaces.IPersistentCommonStorageUtility;
import com.iluwatar.producer.calldetails.utility.PersistentLocalStorageUtility;

public class PersistentLocalStorageUtilityTest {

	
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
