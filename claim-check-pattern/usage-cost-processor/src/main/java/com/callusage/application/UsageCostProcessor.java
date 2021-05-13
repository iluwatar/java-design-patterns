package com.callusage.application;

import com.callusage.domain.*;
import com.callusage.interfaces.IPersistentCommonStorageUtility;
import com.callusage.utility.PersistentLocalStorageUtility;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Processor;

@EnableBinding(Processor.class)
public class UsageCostProcessor {
	
	@Autowired
	IPersistentCommonStorageUtility persistentCommonStorageUtility;
	
	private double ratePerSecond = 0.1;

	private double ratePerMB = 0.05;

	@StreamListener(Processor.INPUT)
	public void processUsageCost(MessageHeader inputMessageHeader) {
		
		Message<UsageDetail> inputMessage = this.persistentCommonStorageUtility.readMessageFromPersistentStorage(inputMessageHeader);
		UsageDetail usageDetail = (UsageDetail)inputMessage.getMessageData().getData();
		
		// Calculate call cost
		UsageCostDetail usageCostDetail = new UsageCostDetail();
		usageCostDetail.setUserId(usageDetail.getUserId());
		usageCostDetail.setCallCost(usageDetail.getDuration() * this.ratePerSecond);
		usageCostDetail.setDataCost(usageDetail.getData() * this.ratePerMB);

		// Create message to drop
		MessageHeader messageHeader = new MessageHeader();
		messageHeader.setOperataionName(inputMessage.getMessageHeader().getOperataionName());
		String dataLocation = inputMessage.getMessageHeader().getDataLocation();
		messageHeader.setDataLocation(dataLocation);
		messageHeader.setDataFileName("output.json");
		MessageData<UsageCostDetail> messageData = new MessageData<UsageCostDetail>(usageCostDetail);
		Message<UsageCostDetail> message = new Message<UsageCostDetail>(messageHeader,messageData);

		// Drop message to common storage
		
		persistentCommonStorageUtility.dropMessageToPersistentStorage(message);
	}
}
