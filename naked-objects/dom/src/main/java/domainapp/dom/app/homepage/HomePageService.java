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

import org.apache.isis.applib.DomainObjectContainer;
import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.HomePage;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.annotation.SemanticsOf;

/**
 * HomePage Domain Service
 * @see HomePageViewModel linked view to HomePage
 */
@DomainService(nature = NatureOfService.VIEW_CONTRIBUTIONS_ONLY)
public class HomePageService {

  // endregion

  // region > injected services

  @javax.inject.Inject
  DomainObjectContainer container;

  // endregion

  // region > homePage (action)

  @Action(semantics = SemanticsOf.SAFE)
  @HomePage
  public HomePageViewModel homePage() {
    return container.injectServicesInto(new HomePageViewModel());
  }

}
