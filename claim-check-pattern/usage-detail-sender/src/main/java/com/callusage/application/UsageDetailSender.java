package com.callusage.application;


import java.util.Random;
import java.util.UUID;

import com.callusage.domain.Message;
import com.callusage.domain.MessageData;
import com.callusage.domain.MessageHeader;
import com.callusage.domain.UsageDetail;
import com.callusage.interfaces.IPersistentCommonStorageUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

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
