package com.iluwatar.templatemethod;

import org.junit.Before;
import org.junit.Test;

/**
 * @author KALAIARASAN
 *
 *         Junit Test case for Template Pattern
 */
public class TemplateMethodTest {

	private HalflingThief thief;

	@Before
	public void setup() {
		this.thief = new HalflingThief(new HitAndRunMethod());
	}

	@Test
	public void testStealMethod() {
		thief.steal();
		thief.changeMethod(new SubtleMethod());
		thief.steal();
	}

}
