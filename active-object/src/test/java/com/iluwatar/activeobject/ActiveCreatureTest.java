package com.iluwatar.activeobject;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
class ActiveCreatureTest {
	
	@Test
	void executionTest() throws InterruptedException {
		ActiveCreature orc = new Orc("orc1");
		assertEquals("orc1",orc.name());
		assertEquals(0,orc.getStatus());
		orc.eat();
		orc.roam();
		orc.kill(0);
	}
	

}
