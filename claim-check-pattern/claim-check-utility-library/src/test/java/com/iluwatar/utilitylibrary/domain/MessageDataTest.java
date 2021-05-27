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

package com.iluwatar.utilitylibrary.domain;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import lombok.Data;

@RunWith(Parameterized.class)
public class MessageDataTest {
	@Data
	class UsageDetail {

	  private String userId;

	  private long duration;

	  private long data;

	}
	
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
