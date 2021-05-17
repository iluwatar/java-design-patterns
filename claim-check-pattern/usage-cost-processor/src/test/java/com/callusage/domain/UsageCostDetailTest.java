package com.callusage.domain;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

@RunWith(Parameterized.class)
public class UsageCostDetailTest {
	
	@Parameters(name = "{index}: fun({0},{1},\"{2}\")={3}")
    public static Iterable<Object[]> data() {
        return Arrays.asList(new Object[][] { 
                 { 10, 1, "Sam", false }, { 1, 10, "Sam", false }, { 1, 1, "Sam Com", false }
           });
    }

    private UsageCostDetail usageCostDetail;
	private String userId;
	private double callCost;
	private double dataCost;
    private boolean expected;
    
    public UsageCostDetailTest(double callCost, double dataCost, String userId, boolean expected) {
    	this.userId = userId;
    	this.callCost = callCost;
    	this.dataCost = dataCost;
    	this.expected = expected;
    	this.usageCostDetail = new UsageCostDetail();
		this.usageCostDetail.setUserId("Sam");
		this.usageCostDetail.setCallCost(1.0);
		this.usageCostDetail.setDataCost(1.0);
    }
    
    @Test
    public void testNotEqualsObject() {
    	UsageCostDetail tempusageCostDetail = new UsageCostDetail();
        tempusageCostDetail.setCallCost(this.callCost);
        tempusageCostDetail.setDataCost(this.dataCost);
        tempusageCostDetail.setUserId(this.userId);
        System.out.println(this.usageCostDetail.equals(tempusageCostDetail));
        assertEquals(this.expected,this.usageCostDetail.equals(tempusageCostDetail));
    }

    
}
