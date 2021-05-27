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

import org.junit.Before;
import org.junit.Test;

import com.iluwatar.consumer.callcostprocessor.domain.UsageDetail;

import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;

public class UsageDetailTestTest {

	private UsageDetail usageDetail;
	
	public UsageDetailTestTest() {
	}
	
	@Before
	public void setUp() throws Exception {
		this.usageDetail = new UsageDetail();
		this.usageDetail.setData(1);
		this.usageDetail.setDuration(1);
		this.usageDetail.setUserId("Marry");
	}

	@Test
	public void testHashCode() {

		UsageDetail usageDetail1 = new UsageDetail();
		usageDetail1.setData(1);
		usageDetail1.setDuration(1);
		usageDetail1.setUserId("Marry");
		
		assertEquals(this.usageDetail.hashCode(), usageDetail1.hashCode());
	}

	@Test
	public void testGetUserId() {
		assertEquals("Marry", this.usageDetail.getUserId());
	}

	@Test
	public void testGetDuration() {
		assertEquals(1, this.usageDetail.getDuration());
	}

	@Test
	public void testGetData() {
		assertEquals(1, this.usageDetail.getData());
	}

	@Test
	public void testSetUserId() {
		try {
			this.usageDetail.setUserId("Marry");
		} catch (Exception e) {
			fail("Setting userid failed: "+e.getMessage());
		}
	}

	@Test
	public void testSetDuration() {
		try {
			this.usageDetail.setDuration(1);
		} catch (Exception e) {
			fail("Setting duration failed: "+e.getMessage());
		}
	}

	@Test
	public void testSetData() {
		try {
			this.usageDetail.setData(1);
		} catch (Exception e) {
			fail("Setting data failed: "+e.getMessage());
		}
	}

	@Test
	public void testEqualsObject() {
		EqualsVerifier.forClass( UsageDetail.class )
        .suppress( Warning.STRICT_INHERITANCE ).suppress(Warning.NONFINAL_FIELDS)
        .verify();
		assertEquals(true,this.usageDetail.equals(this.usageDetail));
	}
	
	@Test
	public void testNotEqualsObject() {
		assertEquals(false,this.usageDetail.equals(null));
	}
	
	@Test
	public void testNotEqualsObject1() {
		assertEquals(false,this.usageDetail.equals(new Object()));
	}

	@Test
	public void testCanEqual() {
		assertEquals(true,this.usageDetail.canEqual(this.usageDetail));
	}

	@Test
	public void testToString() {
		assertNotNull(this.usageDetail.toString());
	}

	@Test
	public void testUsageDetail() {
		assertNotNull(new UsageDetail());
	}
}
