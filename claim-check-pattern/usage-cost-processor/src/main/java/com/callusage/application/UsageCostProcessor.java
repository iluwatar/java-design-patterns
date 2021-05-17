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

package com.callusage.application;


import com.callusage.domain.Message;
import com.callusage.domain.MessageData;
import com.callusage.domain.MessageHeader;
import com.callusage.domain.UsageCostDetail;
import com.callusage.domain.UsageDetail;
import com.callusage.interfaces.IPersistentCommonStorageUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;

import org.springframework.cloud.stream.messaging.Processor;
import static org.springframework.cloud.stream.messaging.Processor.INPUT;

/**
 * 
 * Enum for storing call rates
 *
 */
enum Rate{
	RATE_PER_SECOND(0.1),
	RATE_PER_MB(0.05);

	private double rateValue;
	Rate(double rateValue) {
		this.rateValue = rateValue;
	}
	public double getRateValue() {
		return this.rateValue;
	}
}
/**
 * The class UsageCostProcessor will read messageHeader from 
 * Kafka topic usage-detail. It will calculate call price using call details
 * and drop data to persistent storage.
 */
@EnableBinding(Processor.class)
public class UsageCostProcessor {
	
	@Autowired
	IPersistentCommonStorageUtility<UsageDetail> persistentCommonStorageUtilityForUsageDetail;

	@Autowired
	IPersistentCommonStorageUtility<UsageCostDetail> persistentCommonStorageUtilityForUsageCostDetail;
	
	private double ratePerSecond = Rate.RATE_PER_SECOND.getRateValue();

	private double ratePerMB = Rate.RATE_PER_MB.getRateValue();

	@StreamListener(INPUT)
	public void processUsageCost(MessageHeader inputMessageHeader) {
		
		Message<UsageDetail> inputMessage = this.persistentCommonStorageUtilityForUsageDetail.readMessageFromPersistentStorage(inputMessageHeader);
		var usageDetail = inputMessage.getMessageData().getData();
		
		// Calculate call cost
		var usageCostDetail = new UsageCostDetail();
		usageCostDetail.setUserId(usageDetail.getUserId());
		usageCostDetail.setCallCost(usageDetail.getDuration() * this.ratePerSecond);
		usageCostDetail.setDataCost(usageDetail.getData() * this.ratePerMB);

		// Create message to drop
		var messageHeader = new MessageHeader();
		messageHeader.setOperataionName(inputMessage.getMessageHeader().getOperataionName());
		String dataLocation = inputMessage.getMessageHeader().getDataLocation();
		messageHeader.setDataLocation(dataLocation);
		messageHeader.setDataFileName("output.json");
		MessageData<UsageCostDetail> messageData = new MessageData<>(usageCostDetail);
		Message<UsageCostDetail> message = new Message<>(messageHeader,messageData);

		// Drop message to common storage
		
		this.persistentCommonStorageUtilityForUsageCostDetail.dropMessageToPersistentStorage(message);
	}
}
