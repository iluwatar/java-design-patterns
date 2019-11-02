/*
 * The MIT License
 * Copyright © 2014-2019 Ilkka Seppälä
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

package domainapp.integtests.tests.modules.simple;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import javax.inject.Inject;

import org.apache.isis.applib.DomainObjectContainer;
import org.apache.isis.applib.fixturescripts.FixtureScripts;
import org.apache.isis.applib.services.wrapper.DisabledException;
import org.apache.isis.applib.services.wrapper.InvalidException;
import org.junit.Before;
import org.junit.Test;

import domainapp.dom.modules.simple.SimpleObject;
import domainapp.fixture.scenarios.RecreateSimpleObjects;
import domainapp.integtests.tests.SimpleAppIntegTest;

/**
 * Test Fixtures with Simple Objects
 */
public class SimpleObjectIntegTest extends SimpleAppIntegTest {

  @Inject
  FixtureScripts fixtureScripts;
  @Inject
  DomainObjectContainer container;

  RecreateSimpleObjects fs;
  SimpleObject simpleObjectPojo;
  SimpleObject simpleObjectWrapped;
  
  private static final String NEW_NAME = "new name";

  @Before
  public void setUp() throws Exception {
    // given
    fs = new RecreateSimpleObjects().setNumber(1);
    fixtureScripts.runFixtureScript(fs, null);

    simpleObjectPojo = fs.getSimpleObjects().get(0);

    assertNotNull(simpleObjectPojo);
    simpleObjectWrapped = wrap(simpleObjectPojo);
  }
  
  @Test
  public void testNameAccessible() throws Exception {
    // when
    final String name = simpleObjectWrapped.getName();
    // then
    assertEquals(fs.names.get(0), name);
  }
  
  @Test
  public void testNameCannotBeUpdatedDirectly() throws Exception {

    // expect
    expectedExceptions.expect(DisabledException.class);

    // when
    simpleObjectWrapped.setName(NEW_NAME);
  }
  
  @Test
  public void testUpdateName() throws Exception {

    // when
    simpleObjectWrapped.updateName(NEW_NAME);

    // then
    assertEquals(NEW_NAME, simpleObjectWrapped.getName());
  }
  
  @Test
  public void testUpdateNameFailsValidation() throws Exception {

    // expect
    expectedExceptions.expect(InvalidException.class);
    expectedExceptions.expectMessage("Exclamation mark is not allowed");

    // when
    simpleObjectWrapped.updateName(NEW_NAME + "!");
  }
  
  @Test
  public void testInterpolatesName() throws Exception {

    // given
    final String name = simpleObjectWrapped.getName();

    // when
    final String title = container.titleOf(simpleObjectWrapped);

    // then
    assertEquals("Object: " + name, title);
  }
}
