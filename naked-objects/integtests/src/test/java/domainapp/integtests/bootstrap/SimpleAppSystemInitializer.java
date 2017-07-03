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
package domainapp.integtests.bootstrap;

import org.apache.isis.core.commons.config.IsisConfiguration;
import org.apache.isis.core.integtestsupport.IsisSystemForTest;
import org.apache.isis.objectstore.jdo.datanucleus.DataNucleusPersistenceMechanismInstaller;
import org.apache.isis.objectstore.jdo.datanucleus.IsisConfigurationForJdoIntegTests;

/**
 * Initializer for the Simple App
 */
public final class SimpleAppSystemInitializer {

  private SimpleAppSystemInitializer() {
  }

  /**
   * Init test system
   */
  public static void initIsft() {
    IsisSystemForTest isft = IsisSystemForTest.getElseNull();
    if (isft == null) {
      isft = new SimpleAppSystemBuilder().build().setUpSystem();
      IsisSystemForTest.set(isft);
    }
  }

  private static class SimpleAppSystemBuilder extends IsisSystemForTest.Builder {

    public SimpleAppSystemBuilder() {
      withLoggingAt(org.apache.log4j.Level.INFO);
      with(testConfiguration());
      with(new DataNucleusPersistenceMechanismInstaller());

      // services annotated with @DomainService
      withServicesIn("domainapp");
    }

    private static IsisConfiguration testConfiguration() {
      final IsisConfigurationForJdoIntegTests testConfiguration =
          new IsisConfigurationForJdoIntegTests();

      testConfiguration.addRegisterEntitiesPackagePrefix("domainapp.dom.modules");
      return testConfiguration;
    }
  }
}
