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

package domainapp.fixture.scenarios;

import com.google.common.collect.Lists;
import domainapp.dom.modules.simple.SimpleObject;
import domainapp.fixture.modules.simple.SimpleObjectCreate;
import domainapp.fixture.modules.simple.SimpleObjectsTearDown;
import java.util.List;
import org.apache.isis.applib.fixturescripts.FixtureScript;


/**
 * Create a bunch of simple Objects.
 */
public class RecreateSimpleObjects extends FixtureScript {

  public final List<String> names = List.of(
      "Foo",
      "Bar",
      "Baz",
      "Frodo",
      "Froyo",
      "Fizz",
      "Bip",
      "Bop",
      "Bang",
      "Boo"
  );

  // region > number (optional input)
  private Integer number;

  // endregion

  // region > simpleObjects (output)
  private final List<SimpleObject> simpleObjects = Lists.newArrayList();

  public RecreateSimpleObjects() {
    withDiscoverability(Discoverability.DISCOVERABLE);
  }

  /**
   * The number of objects to create, up to 10; optional, defaults to 3.
   */
  public Integer getNumber() {
    return number;
  }

  public RecreateSimpleObjects setNumber(final Integer number) {
    this.number = number;
    return this;
  }

  /**
   * The simpleobjects created by this fixture (output).
   */
  public List<SimpleObject> getSimpleObjects() {
    return simpleObjects;
  }

  // endregion

  @Override
  protected void execute(final ExecutionContext ec) {

    // defaults
    final var paramNumber = defaultParam("number", ec, 3);

    // validate
    if (paramNumber < 0 || paramNumber > names.size()) {
      throw new IllegalArgumentException(String.format("number must be in range [0,%d)",
          names.size()));
    }

    //
    // execute
    //
    ec.executeChild(this, new SimpleObjectsTearDown());

    for (var i = 0; i < paramNumber; i++) {
      final var fs = new SimpleObjectCreate().setName(names.get(i));
      ec.executeChild(this, fs.getName(), fs);
      simpleObjects.add(fs.getSimpleObject());
    }
  }
}
