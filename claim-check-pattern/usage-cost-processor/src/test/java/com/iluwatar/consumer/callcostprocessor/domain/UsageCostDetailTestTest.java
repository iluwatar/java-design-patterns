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

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.iluwatar.consumer.callcostprocessor.domain.UsageCostDetail;

import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;


public class UsageCostDetailTestTest {
	
	private UsageCostDetail usageCostDetail;
    
	@Before
	public void setUp() throws Exception {
		this.usageCostDetail = new UsageCostDetail();
		this.usageCostDetail.setUserId("Sam");
		this.usageCostDetail.setCallCost(1.0);
		this.usageCostDetail.setDataCost(1.0);
	}

	@Test
	public void testHashCode() {
		
		UsageCostDetail usageCostDetail1 = new UsageCostDetail();
		usageCostDetail1.setUserId("Sam");
		usageCostDetail1.setCallCost(1.0);
		usageCostDetail1.setDataCost(1.0);
		
		assertEquals(this.usageCostDetail.hashCode(), usageCostDetail1.hashCode());
	}

	@Test
	public void testGetUserId() {
		assertEquals("Sam", this.usageCostDetail.getUserId());
	}

	@Test
	public void testGetCallCost() {
		assertEquals(1.0, this.usageCostDetail.getCallCost(),0);
	}

	@Test
	public void testGetDataCost() {
		assertEquals(1.0, this.usageCostDetail.getDataCost(),0);
	}

	@Test
	public void testSetUserId() {
		try {
			this.usageCostDetail.setUserId("Marry");
		} catch (Exception e) {
			fail("Setting userid failed: "+e.getMessage());
		}
	}

	@Test
	public void testSetCallCost() {
		try {
			this.usageCostDetail.setCallCost(1.0);
		} catch (Exception e) {
			fail("Setting call cost failed: "+e.getMessage());
		}
	}

	@Test
	public void testSetDataCost() {
		try {
			this.usageCostDetail.setDataCost(1.0);
		} catch (Exception e) {
			fail("Setting data cost failed: "+e.getMessage());
		}
	}

	@Test
	public void testEqualsObject() {
		EqualsVerifier.forClass( UsageCostDetail.class )
        .suppress( Warning.STRICT_INHERITANCE ).suppress(Warning.NONFINAL_FIELDS)
        .verify();
		assertEquals(true,this.usageCostDetail.equals(this.usageCostDetail));
	}

    @Test
    public void testNotEqualsObject() {
        assertEquals(false,this.usageCostDetail.equals(null));
    }
    
    @Test
    public void testNotEqualsObject1() {
        assertEquals(false,this.usageCostDetail.equals(new Object()));
    }
    
	@Test
	public void testCanEqual() {
		assertEquals(true,this.usageCostDetail.canEqual(this.usageCostDetail));
	}

	@Test
	public void testToString() {
		assertNotNull(this.usageCostDetail.toString());
	}

	@Test
	public void testUsageCostDetail() {
		assertNotNull(new UsageCostDetail());
	}
}
