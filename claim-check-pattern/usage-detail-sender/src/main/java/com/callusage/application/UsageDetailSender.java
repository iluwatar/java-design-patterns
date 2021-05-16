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
import com.callusage.domain.UsageDetail;
import com.callusage.interfaces.IPersistentCommonStorageUtility;

import java.util.Random;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

/**
 * 
 * This class is producer class which send message header to 
 * Kafka topic and drop message to persistent store after each five seconds.
 * Message header contains message location and body contains actual data 
 * such as call duration and data.
 */
@EnableScheduling
@EnableBinding(Source.class)
public class UsageDetailSender {

	@Autowired
	private Source source;

	@Autowired
	private IPersistentCommonStorageUtility persistentcommonStorageUtility;

	public static void setCount(long count) {
		UsageDetailSender.count = count;
	}

	private static long count = 1;
	private String[] users = {"Sam", "Alex", "Jane", "Tony", "Cobb"};

	@Scheduled(fixedDelay = 5000)
	public void sendEvents() {
		UsageDetail usageDetail = new UsageDetail();
		usageDetail.setUserId(this.users[new Random().nextInt(5)]+"-"+String.valueOf(count));
		count+=1;
		usageDetail.setDuration(new Random().nextInt(300));
		usageDetail.setData(new Random().nextInt(700));
		
		MessageHeader messageHeader = new MessageHeader();
		messageHeader.setDataLocation(System.getenv("LOCALAPPDATA")+"\\claim-check-pattern\\"+
				UUID.randomUUID().toString());
		messageHeader.setDataFileName("input.json");
		messageHeader.setOperataionName("Call-Cost-Calculation");
		
		MessageData<UsageDetail> messageData = new MessageData<UsageDetail>(usageDetail);
		
		Message<UsageDetail> message = new Message<UsageDetail>(messageHeader, messageData);
		
		this.persistentcommonStorageUtility.dropMessageToPersistentStorage(message);
		
		this.source.output().send(MessageBuilder.withPayload(messageHeader).build());
	}
}
