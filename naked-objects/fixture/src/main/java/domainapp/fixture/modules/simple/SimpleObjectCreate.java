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

package domainapp.fixture.modules.simple;

import domainapp.dom.modules.simple.SimpleObject;
import domainapp.dom.modules.simple.SimpleObjects;
import org.apache.isis.applib.fixturescripts.FixtureScript;

/**
 * Fixture to create a simple object.
 */
public class SimpleObjectCreate extends FixtureScript {

  // endregion


  // region > simpleObject (output)
  private SimpleObject simpleObject;

  @javax.inject.Inject
  private SimpleObjects simpleObjects;

  // region > name (input)
  private String name;

  /**
   * Name of the object (required).
   */
  public String getName() {
    return name;
  }

  public SimpleObjectCreate setName(final String name) {
    this.name = name;
    return this;
  }

  /**
   * The created simple object (output).
   */
  public SimpleObject getSimpleObject() {
    return simpleObject;
  }

  // endregion

  @Override
  protected void execute(final ExecutionContext ec) {

    String paramName = checkParam("name", ec, String.class);

    this.simpleObject = wrap(simpleObjects).create(paramName);

    // also make available to UI
    ec.addResult(this, simpleObject);
  }

}
