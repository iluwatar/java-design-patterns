/**
 * Licensed to the Apache Software Foundation (ASF) under one or more contributor license
 * agreements. See the NOTICE file distributed with this work for additional information regarding
 * copyright ownership. The ASF licenses this file to You under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance with the License. You may obtain a
 * copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */
package domainapp.integtests.specglue.modules.simple;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.When;
import domainapp.dom.modules.simple.SimpleObject;
import domainapp.dom.modules.simple.SimpleObjects;
import java.util.List;
import java.util.UUID;
import org.apache.isis.core.specsupport.specs.CukeGlueAbstract;

/**
 * Test Simple Object Operations
 */
public class SimpleObjectGlue extends CukeGlueAbstract {

  @Given("^there are.* (\\d+) simple objects$")
  public void thereAreNumSimpleObjects(int n) throws Throwable {
    try {
      final List<SimpleObject> findAll = service(SimpleObjects.class).listAll();
      assertThat(findAll.size(), is(n));
      putVar("list", "all", findAll);

    } finally {
      assertMocksSatisfied();
    }
  }

  @When("^I create a new simple object$")
  public void createNewSimpleObject() throws Throwable {
    service(SimpleObjects.class).create(UUID.randomUUID().toString());
  }

}
