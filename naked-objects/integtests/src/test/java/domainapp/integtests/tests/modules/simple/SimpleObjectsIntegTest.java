/*
 * The MIT License
 * Copyright © 2014-2021 Ilkka Seppälä
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

import com.google.common.base.Throwables;
import domainapp.dom.modules.simple.SimpleObjects;
import domainapp.fixture.modules.simple.SimpleObjectsTearDown;
import domainapp.fixture.scenarios.RecreateSimpleObjects;
import domainapp.integtests.tests.SimpleAppIntegTest;
import java.sql.SQLIntegrityConstraintViolationException;
import javax.inject.Inject;
import org.apache.isis.applib.fixturescripts.FixtureScript;
import org.apache.isis.applib.fixturescripts.FixtureScripts;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Test;

/**
 * Fixture Pattern Integration Test
 */
public class SimpleObjectsIntegTest extends SimpleAppIntegTest {

  @Inject
  FixtureScripts fixtureScripts;

  @Inject
  SimpleObjects simpleObjects;

  @Test
  public void testListAll() {

    // given
    var fs = new RecreateSimpleObjects();
    fixtureScripts.runFixtureScript(fs, null);
    nextTransaction();

    // when
    final var all = wrap(simpleObjects).listAll();

    // then
    assertEquals(fs.getSimpleObjects().size(), all.size());

    var simpleObject = wrap(all.get(0));
    assertEquals(fs.getSimpleObjects().get(0).getName(), simpleObject.getName());
  }

  @Test
  public void testListAllWhenNone() {

    // given
    FixtureScript fs = new SimpleObjectsTearDown();
    fixtureScripts.runFixtureScript(fs, null);
    nextTransaction();

    // when
    final var all = wrap(simpleObjects).listAll();

    // then
    assertEquals(0, all.size());
  }

  @Test
  public void testCreate() {

    // given
    FixtureScript fs = new SimpleObjectsTearDown();
    fixtureScripts.runFixtureScript(fs, null);
    nextTransaction();

    // when
    wrap(simpleObjects).create("Faz");

    // then
    final var all = wrap(simpleObjects).listAll();
    assertEquals(1, all.size());
  }

  @Test
  public void testCreateWhenAlreadyExists() {

    // given
    FixtureScript fs = new SimpleObjectsTearDown();
    fixtureScripts.runFixtureScript(fs, null);
    nextTransaction();
    wrap(simpleObjects).create("Faz");
    nextTransaction();

    // then
    expectedExceptions
        .expectCause(causalChainContains(SQLIntegrityConstraintViolationException.class));

    // when
    wrap(simpleObjects).create("Faz");
    nextTransaction();
  }

  @SuppressWarnings("SameParameterValue")
  private static Matcher<? extends Throwable> causalChainContains(final Class<?> cls) {
    return new TypeSafeMatcher<>() {
      @Override
      @SuppressWarnings("UnstableApiUsage")
      protected boolean matchesSafely(Throwable item) {
        final var causalChain = Throwables.getCausalChain(item);
        return causalChain.stream().map(Throwable::getClass).anyMatch(cls::isAssignableFrom);
      }

      @Override
      public void describeTo(Description description) {
        description.appendText("exception with causal chain containing " + cls.getSimpleName());
      }
    };
  }
}
