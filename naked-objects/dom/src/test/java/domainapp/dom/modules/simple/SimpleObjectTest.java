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
package domainapp.dom.modules.simple;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Before;
import org.junit.Test;

/**
 * Test for SimpleObject
 */
public class SimpleObjectTest {

  SimpleObject simpleObject;

  @Before
  public void setUp() throws Exception {
    simpleObject = new SimpleObject();
  }
  
  @Test
  public void testName() throws Exception {
    // given
    String name = "Foobar";
    assertNull(simpleObject.getName());

    // when
    simpleObject.setName(name);

    // then
    assertEquals(name, simpleObject.getName());
  }

}
