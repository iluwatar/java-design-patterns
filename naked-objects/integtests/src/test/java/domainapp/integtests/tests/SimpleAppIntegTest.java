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
package domainapp.integtests.tests;

import org.junit.BeforeClass;

import org.apache.isis.core.integtestsupport.IntegrationTestAbstract;
import org.apache.isis.core.integtestsupport.scenarios.ScenarioExecutionForIntegration;

import domainapp.integtests.bootstrap.SimpleAppSystemInitializer;

/**
 * SimpleApp Integration Tests will implement this Abstract Class.
 */
public abstract class SimpleAppIntegTest extends IntegrationTestAbstract {

  @BeforeClass
  public static void initClass() {
    org.apache.log4j.PropertyConfigurator.configure("logging.properties");
    SimpleAppSystemInitializer.initIsft();

    // instantiating will install onto ThreadLocal
    new ScenarioExecutionForIntegration();
  }
}
