package com.iluwatar.producer.calldetails.domain;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import com.iluwatar.producer.calldetails.domain.UsageDetail;

@RunWith(Parameterized.class)
public class UsageDetailTest {
	
	@Parameters(name = "{index}: fun({0},{1},\"{2}\")={3}")
    public static Iterable<Object[]> data() {
        return Arrays.asList(new Object[][] { 
                 { 10, 1, "Sam", false }, { 1, 10, "Sam", false }, { 1, 1, "Sam Com", false }
           });
    }

    private UsageDetail usageDetail;
	private String userId;
	private long data;
	private long duration;
    private boolean expected;
    
    public UsageDetailTest(long data, long duration, String userId, boolean expected) {
    	this.userId = userId;
    	this.data = data;
    	this.duration = duration;
    	this.expected = expected;
    	
    	this.usageDetail = new UsageDetail();
		this.usageDetail.setUserId("Sam");
		this.usageDetail.setData(1);
		this.usageDetail.setDuration(1);
    }
    
    @Test
	public void testNotEqualsObject() {
		UsageDetail tempUsageDetail = new UsageDetail();
		tempUsageDetail.setData(this.data);
		tempUsageDetail.setDuration(this.duration);
		tempUsageDetail.setUserId(this.userId);
		assertEquals(this.expected,this.usageDetail.equals(tempUsageDetail));
	}
}
