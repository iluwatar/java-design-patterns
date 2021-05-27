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

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;

public class MessageHeaderTestTest {

	private MessageHeader messageHeader;
	@Before
	public void setUp() throws Exception {
		this.messageHeader = new MessageHeader();
		this.messageHeader.setDataFileName("input.json");
		this.messageHeader.setDataLocation("C://tmp");
		this.messageHeader.setOperataionName("Cost Calculator");
	}

	@Test
	public void testHashCode() {
		MessageHeader messageHeader1 = new MessageHeader();
		messageHeader1.setDataFileName("input.json");
		messageHeader1.setDataLocation("C://tmp");
		messageHeader1.setOperataionName("Cost Calculator");
		
		assertEquals(this.messageHeader.hashCode(), messageHeader1.hashCode());
	}

	@Test
	public void testGetDataLocation() {
		assertNotNull(this.messageHeader.getDataLocation());
	}

	@Test
	public void testGetDataFileName() {
		assertNotNull(this.messageHeader.getDataFileName());
	}

	@Test
	public void testGetOperataionName() {
		assertNotNull(this.messageHeader.getOperataionName());
	}

	@Test
	public void testSetDataLocation() {
		try {
			this.messageHeader.setDataLocation("C://tmp");
		} catch (Exception e) {
			fail("Setting data location failed: "+e.getMessage());
		}
	}

	@Test
	public void testSetDataFileName() {
		try {
			this.messageHeader.setDataFileName("input.json");
		} catch (Exception e) {
			fail("Setting data file name failed: "+e.getMessage());
		}
	}

	@Test
	public void testSetOperataionName() {
		try {
			this.messageHeader.setOperataionName("Cost Calculator");
		} catch (Exception e) {
			fail("Setting data location failed: "+e.getMessage());
		}
	}

	@Test
	public void testEqualsObject() {
		
		EqualsVerifier.forClass( MessageHeader.class )
        .suppress( Warning.STRICT_INHERITANCE ).suppress(Warning.NONFINAL_FIELDS)
        .verify();
		
		assertEquals(true,this.messageHeader.equals(this.messageHeader));
	}
	@Test
	public void testNotEqualsObject() {
		assertEquals(false,this.messageHeader.equals(null));
	}
	@Test
	public void testNotEqualsObject1() {
		assertEquals(false,this.messageHeader.equals(new Object()));
	}

	@Test
	public void testCanEqual() {
		assertEquals(true,this.messageHeader.canEqual(this.messageHeader));
	}

	@Test
	public void testToString() {
		assertNotNull(this.messageHeader.toString());
	}

	@Test
	public void testMessageHeader() {
		assertNotNull(new MessageHeader());
	}


}
