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

import static org.assertj.core.api.Assertions.assertThat;

import domainapp.dom.modules.simple.SimpleObject;
import domainapp.fixture.scenarios.RecreateSimpleObjects;
import domainapp.integtests.tests.SimpleAppIntegTest;
import javax.inject.Inject;
import org.apache.isis.applib.DomainObjectContainer;
import org.apache.isis.applib.fixturescripts.FixtureScripts;
import org.apache.isis.applib.services.wrapper.DisabledException;
import org.apache.isis.applib.services.wrapper.InvalidException;
import org.junit.Before;
import org.junit.Test;

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

  @Before
  public void setUp() throws Exception {
    // given
    fs = new RecreateSimpleObjects().setNumber(1);
    fixtureScripts.runFixtureScript(fs, null);

    simpleObjectPojo = fs.getSimpleObjects().get(0);

    assertThat(simpleObjectPojo).isNotNull();
    simpleObjectWrapped = wrap(simpleObjectPojo);
  }
  
  @Test
  public void testNameAccessible() throws Exception {
    // when
    final String name = simpleObjectWrapped.getName();
    // then
    assertThat(name).isEqualTo(fs.names.get(0));
  }
  
  @Test
  public void testNameCannotBeUpdatedDirectly() throws Exception {

    // expect
    expectedExceptions.expect(DisabledException.class);

    // when
    simpleObjectWrapped.setName("new name");
  }
  
  @Test
  public void testUpdateName() throws Exception {

    // when
    simpleObjectWrapped.updateName("new name");

    // then
    assertThat(simpleObjectWrapped.getName()).isEqualTo("new name");
  }
  
  @Test
  public void testUpdateNameFailsValidation() throws Exception {

    // expect
    expectedExceptions.expect(InvalidException.class);
    expectedExceptions.expectMessage("Exclamation mark is not allowed");

    // when
    simpleObjectWrapped.updateName("new name!");
  }
  
  @Test
  public void testInterpolatesName() throws Exception {

    // given
    final String name = simpleObjectWrapped.getName();

    // when
    final String title = container.titleOf(simpleObjectWrapped);

    // then
    assertThat(title).isEqualTo("Object: " + name);
  }
}
