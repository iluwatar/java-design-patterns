package com.iluwatar;

import org.junit.Assert;
import org.junit.Test;

public class RectangleTest {

	@Test
	public void test() {
		Assert.assertTrue(new Rectangle(0,0,1,1).intersectsWith(new Rectangle(0,0,1,1)));
		Assert.assertTrue(new Rectangle(0,0,1,1).intersectsWith(new Rectangle(-1,-5,7,8)));
		Assert.assertFalse(new Rectangle(0,0,1,1).intersectsWith(new Rectangle(2,2,3,3)));
		Assert.assertFalse(new Rectangle(0,0,1,1).intersectsWith(new Rectangle(-2,-2,-1,-1)));
	}
}
