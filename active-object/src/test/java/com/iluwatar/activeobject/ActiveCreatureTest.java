package com.iluwatar.activeobject;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
public class ActiveCreatureTest {
	
	@Test
	public void executionTest() throws InterruptedException {
		ActiveCreature orc = new Orc("orc1");
		assertEquals("orc1",orc.name());
		assertEquals(orc.getStatus(),0);
		orc.eat();
		orc.roam();
		orc.kill(0);
	}
	

}
