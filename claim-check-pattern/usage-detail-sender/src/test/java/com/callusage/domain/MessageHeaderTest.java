package com.callusage.domain;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;

public class MessageHeaderTest {

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
		assertNotNull(this.messageHeader.hashCode());
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
	public void testNotEqualsObject2() {
		MessageHeader tempMessageHeader = new MessageHeader();
		tempMessageHeader.setDataFileName("input1.json");
		tempMessageHeader.setDataLocation("C://tmp");
		tempMessageHeader.setOperataionName("Cost Calculator");
		assertEquals(false,this.messageHeader.equals(tempMessageHeader));
	}
	@Test
	public void testNotEqualsObject3() {
		MessageHeader tempMessageHeader = new MessageHeader();
		tempMessageHeader.setDataFileName("input.json");
		tempMessageHeader.setDataLocation("C://tmpp");
		tempMessageHeader.setOperataionName("Cost Calculator");
		assertEquals(false,this.messageHeader.equals(tempMessageHeader));
	}
	@Test
	public void testNotEqualsObject4() {
		MessageHeader tempMessageHeader = new MessageHeader();
		tempMessageHeader.setDataFileName("input.json");
		tempMessageHeader.setDataLocation("C://tmp");
		tempMessageHeader.setOperataionName("Cost Calculatorr");
		assertEquals(false,this.messageHeader.equals(tempMessageHeader));
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
