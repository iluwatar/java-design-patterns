/*
 *  Licensed to the Apache Software Foundation (ASF) under one
 *  or more contributor license agreements.  See the NOTICE file
 *  distributed with this work for additional information
 *  regarding copyright ownership.  The ASF licenses this file
 *  to you under the Apache License, Version 2.0 (the
 *  "License"); you may not use this file except in compliance
 *  with the License.  You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing,
 *  software distributed under the License is distributed on an
 *  "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 *  KIND, either express or implied.  See the License for the
 *  specific language governing permissions and limitations
 *  under the License.
 */
package domainapp.integtests.tests.modules.simple;

import static org.junit.Assert.assertEquals;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;

import javax.inject.Inject;

import org.apache.isis.applib.fixturescripts.FixtureScript;
import org.apache.isis.applib.fixturescripts.FixtureScripts;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Test;

import com.google.common.base.Throwables;

import domainapp.dom.modules.simple.SimpleObject;
import domainapp.dom.modules.simple.SimpleObjects;
import domainapp.fixture.modules.simple.SimpleObjectsTearDown;
import domainapp.fixture.scenarios.RecreateSimpleObjects;
import domainapp.integtests.tests.SimpleAppIntegTest;

/**
 * Fixture Pattern Integration Test
 */
public class SimpleObjectsIntegTest extends SimpleAppIntegTest {

  @Inject
  FixtureScripts fixtureScripts;
  @Inject
  SimpleObjects simpleObjects;

  @Test
  public void testListAll() throws Exception {

    // given
    RecreateSimpleObjects fs = new RecreateSimpleObjects();
    fixtureScripts.runFixtureScript(fs, null);
    nextTransaction();

    // when
    final List<SimpleObject> all = wrap(simpleObjects).listAll();

    // then
    assertEquals(fs.getSimpleObjects().size(), all.size());

    SimpleObject simpleObject = wrap(all.get(0));
    assertEquals(fs.getSimpleObjects().get(0).getName(), simpleObject.getName());
  }
  
  @Test
  public void testListAllWhenNone() throws Exception {

    // given
    FixtureScript fs = new SimpleObjectsTearDown();
    fixtureScripts.runFixtureScript(fs, null);
    nextTransaction();

    // when
    final List<SimpleObject> all = wrap(simpleObjects).listAll();

    // then
    assertEquals(0, all.size());
  }
  
  @Test
  public void testCreate() throws Exception {

    // given
    FixtureScript fs = new SimpleObjectsTearDown();
    fixtureScripts.runFixtureScript(fs, null);
    nextTransaction();

    // when
    wrap(simpleObjects).create("Faz");

    // then
    final List<SimpleObject> all = wrap(simpleObjects).listAll();
    assertEquals(1, all.size());
  }
  
  @Test
  public void testCreateWhenAlreadyExists() throws Exception {

    // given
    FixtureScript fs = new SimpleObjectsTearDown();
    fixtureScripts.runFixtureScript(fs, null);
    nextTransaction();
    wrap(simpleObjects).create("Faz");
    nextTransaction();

    // then
    expectedExceptions.expectCause(causalChainContains(SQLIntegrityConstraintViolationException.class));

    // when
    wrap(simpleObjects).create("Faz");
    nextTransaction();
  }
  
  private static Matcher<? extends Throwable> causalChainContains(final Class<?> cls) {
    return new TypeSafeMatcher<Throwable>() {
      @Override
      protected boolean matchesSafely(Throwable item) {
        final List<Throwable> causalChain = Throwables.getCausalChain(item);
        for (Throwable throwable : causalChain) {
          if (cls.isAssignableFrom(throwable.getClass())) {
            return true;
          }
        }
        return false;
      }

      @Override
      public void describeTo(Description description) {
        description.appendText("exception with causal chain containing " + cls.getSimpleName());
      }
    };
  }
}
