package com.iluwatar.producer.calldetails.domain;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.iluwatar.producer.calldetails.domain.MessageHeader;

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
	public void testToString() {
		assertNotNull(this.messageHeader.toString());
	}

	@Test
	public void testMessageHeader() {
		assertNotNull(new MessageHeader());
	}


}
