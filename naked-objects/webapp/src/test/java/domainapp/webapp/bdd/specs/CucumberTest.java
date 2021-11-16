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
package domainapp.webapp.bdd.specs;

import io.cucumber.junit.platform.engine.Cucumber;

/**
 * Cucumber will scan the package of a class annotated with @Cucumber for feature files.
 * <p>
 * Make sure this class name ends with Test, as Surefire when bundled with Apache Isis 
 * filters JUnit tests also by class name. 
 */
@Cucumber
public class CucumberTest {

    // See:
    // https://github.com/cucumber/cucumber-jvm/issues/1149
    // https://github.com/cucumber/cucumber-jvm/tree/master/junit-platform-engine

}
