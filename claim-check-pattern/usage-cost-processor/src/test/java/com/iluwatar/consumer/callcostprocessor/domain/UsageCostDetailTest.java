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

package com.iluwatar.consumer.callcostprocessor.domain;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import com.iluwatar.consumer.callcostprocessor.domain.UsageCostDetail;

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
