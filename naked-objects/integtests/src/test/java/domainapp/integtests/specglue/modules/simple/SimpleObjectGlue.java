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

package domainapp.integtests.specglue.modules.simple;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.When;
import domainapp.dom.modules.simple.SimpleObjects;
import java.util.UUID;
import org.apache.isis.core.specsupport.specs.CukeGlueAbstract;

/**
 * Test Simple Object Operations
 */
public class SimpleObjectGlue extends CukeGlueAbstract {

  @Given("^there are.* (\\d+) simple objects$")
  public void thereAreNumSimpleObjects(int n) {
    try {
      final var findAll = service(SimpleObjects.class).listAll();
      assertThat(findAll.size(), is(n));
      putVar("list", "all", findAll);

    } finally {
      assertMocksSatisfied();
    }
  }

  @When("^I create a new simple object$")
  public void createNewSimpleObject() {
    service(SimpleObjects.class).create(UUID.randomUUID().toString());
  }

}
