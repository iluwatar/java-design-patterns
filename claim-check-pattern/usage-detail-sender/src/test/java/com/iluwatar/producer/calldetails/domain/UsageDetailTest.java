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
