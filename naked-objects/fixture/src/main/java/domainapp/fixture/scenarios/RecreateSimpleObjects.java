/*
 * Licensed to the Apache Software Foundation (ASF) under one or more contributor license
 * agreements. See the NOTICE file distributed with this work for additional information regarding
 * copyright ownership. The ASF licenses this file to you under the Apache License, Version 2.0 (the
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

package domainapp.fixture.scenarios;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import com.google.common.collect.Lists;

import org.apache.isis.applib.fixturescripts.FixtureScript;

import domainapp.dom.modules.simple.SimpleObject;
import domainapp.fixture.modules.simple.SimpleObjectCreate;
import domainapp.fixture.modules.simple.SimpleObjectsTearDown;


/**
 * Create a bunch of simple Objects
 */
public class RecreateSimpleObjects extends FixtureScript {

  public final List<String> names = Collections.unmodifiableList(Arrays.asList("Foo", "Bar", "Baz",
      "Frodo", "Froyo", "Fizz", "Bip", "Bop", "Bang", "Boo"));

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
    final int paramNumber = defaultParam("number", ec, 3);

    // validate
    if (paramNumber < 0 || paramNumber > names.size()) {
      throw new IllegalArgumentException(String.format("number must be in range [0,%d)",
          names.size()));
    }

    //
    // execute
    //
    ec.executeChild(this, new SimpleObjectsTearDown());

    for (int i = 0; i < paramNumber; i++) {
      final SimpleObjectCreate fs = new SimpleObjectCreate().setName(names.get(i));
      ec.executeChild(this, fs.getName(), fs);
      simpleObjects.add(fs.getSimpleObject());
    }
  }
}
