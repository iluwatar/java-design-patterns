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

package domainapp.dom.modules.simple;

import com.google.common.collect.Lists;

import static org.junit.Assert.assertEquals;

import java.util.List;
import org.apache.isis.applib.DomainObjectContainer;
import org.apache.isis.core.unittestsupport.jmocking.JUnitRuleMockery2;
import org.apache.isis.core.unittestsupport.jmocking.JUnitRuleMockery2.Mode;
import org.jmock.Expectations;
import org.jmock.Sequence;
import org.jmock.auto.Mock;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

/**
 * Test for SimpleObjects
 */
public class SimpleObjectsTest {

  @Rule
  public JUnitRuleMockery2 context = JUnitRuleMockery2.createFor(Mode.INTERFACES_AND_CLASSES);

  @Mock
  DomainObjectContainer mockContainer;

  SimpleObjects simpleObjects;

  @Before
  public void setUp() throws Exception {
    simpleObjects = new SimpleObjects();
    simpleObjects.container = mockContainer;
  }
  
  @Test
  public void testCreate() throws Exception {

    // given
    final SimpleObject simpleObject = new SimpleObject();

    final Sequence seq = context.sequence("create");
    context.checking(new Expectations() {
      {
        oneOf(mockContainer).newTransientInstance(SimpleObject.class);
        inSequence(seq);
        will(returnValue(simpleObject));

        oneOf(mockContainer).persistIfNotAlready(simpleObject);
        inSequence(seq);
      }
    });

    // when
    String objectName = "Foobar";
    final SimpleObject obj = simpleObjects.create(objectName);

    // then
    assertEquals(simpleObject, obj);
    assertEquals(objectName, obj.getName());
  }
  
  @Test
  public void testListAll() throws Exception {

    // given
    final List<SimpleObject> all = Lists.newArrayList();

    context.checking(new Expectations() {
      {
        oneOf(mockContainer).allInstances(SimpleObject.class);
        will(returnValue(all));
      }
    });

    // when
    final List<SimpleObject> list = simpleObjects.listAll();

    // then
    assertEquals(all, list);
  }

}
