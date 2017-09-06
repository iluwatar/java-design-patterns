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
package domainapp.dom.app.homepage;

import java.util.List;

import org.apache.isis.applib.annotation.ViewModel;

import domainapp.dom.modules.simple.SimpleObject;
import domainapp.dom.modules.simple.SimpleObjects;

/**
 * Model linked to the HomePage
 * The underlying layout is specified by json
 * @see HomePageService - Service Linked to the HomePage
 */
@ViewModel
public class HomePageViewModel {

  // endregion

  // region > injected services

  @javax.inject.Inject
  SimpleObjects simpleObjects;

  // endregion

  // region > title
  public String title() {
    return getObjects().size() + " objects";
  }

  // endregion

  // region > object (collection)
  @org.apache.isis.applib.annotation.HomePage
  public List<SimpleObject> getObjects() {
    return simpleObjects.listAll();
  }

}
