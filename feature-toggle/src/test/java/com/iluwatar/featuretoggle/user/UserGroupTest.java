/**
 * The MIT License
 * Copyright (c) 2014-2016 Ilkka Seppälä
 * <p>
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * <p>
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * <p>
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package com.iluwatar.featuretoggle.user;

import static junit.framework.TestCase.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 * Test User Group specific feature
 */
public class UserGroupTest {

  @Test
  public void testAddUserToFreeGroup() throws Exception {
    User user = new User("Free User");
    UserGroup.addUserToFreeGroup(user);
    assertFalse(UserGroup.isPaid(user));
  }

  @Test
  public void testAddUserToPaidGroup() throws Exception {
    User user = new User("Paid User");
    UserGroup.addUserToPaidGroup(user);
    assertTrue(UserGroup.isPaid(user));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testAddUserToPaidWhenOnFree() throws Exception {
    User user = new User("Paid User");
    UserGroup.addUserToFreeGroup(user);
    UserGroup.addUserToPaidGroup(user);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testAddUserToFreeWhenOnPaid() throws Exception {
    User user = new User("Free User");
    UserGroup.addUserToPaidGroup(user);
    UserGroup.addUserToFreeGroup(user);
  }
}
