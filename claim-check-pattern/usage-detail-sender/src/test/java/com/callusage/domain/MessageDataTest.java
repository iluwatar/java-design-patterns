package com.callusage.domain;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

@RunWith(Parameterized.class)
public class MessageDataTest {
	
	@Parameters(name = "{index}: fun({0},{1},\"{2}\")={3}")
    public static Iterable<Object[]> data() {
        return Arrays.asList(new Object[][] { 
                 { 10, 1, "Sam", false }, { 1, 10, "Sam", false }, { 1, 1, "Sam Com", false }
           });
    }
    private String userId;
	private long duration;
	private long data;
	private UsageDetail usageDetail;
	private MessageData<UsageDetail> messageData;
	
	public MessageDataTest(long data, long duration, String userId, boolean expected) {
		this.userId = userId;
		this.data = data;
		this.duration = duration;
		
		this.usageDetail = new UsageDetail();
		this.usageDetail.setUserId("Sam");
		this.usageDetail.setData(1);
		this.usageDetail.setDuration(1);
		
		this.messageData = new MessageData<>(this.usageDetail);
	}
	
	@Test
	public void testNotEqualsObject() {
		UsageDetail usageDetail1 = new UsageDetail();
		usageDetail1.setData(this.data);
		usageDetail1.setDuration(this.duration);
		usageDetail1.setUserId(this.userId);
		MessageData<UsageDetail> messageData1 = new MessageData<UsageDetail>(usageDetail1);
		
		assertEquals(false,this.messageData.equals(messageData1));
	}


}
