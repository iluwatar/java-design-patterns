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
package domainapp.dom.modules.simple;

import java.util.List;

import org.apache.isis.applib.DomainObjectContainer;
import org.apache.isis.applib.Identifier;
import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.ActionLayout;
import org.apache.isis.applib.annotation.BookmarkPolicy;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.DomainServiceLayout;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.ParameterLayout;
import org.apache.isis.applib.annotation.SemanticsOf;
import org.apache.isis.applib.query.QueryDefault;
import org.apache.isis.applib.services.eventbus.ActionDomainEvent;
import org.apache.isis.applib.services.i18n.TranslatableString;

/**
 * Domain Service for Simple Objects
 */
@DomainService(repositoryFor = SimpleObject.class)
@DomainServiceLayout(menuOrder = "10")
public class SimpleObjects {
  // endregion

  // region > injected services

  @javax.inject.Inject
  DomainObjectContainer container;

  // endregion

  // region > title
  public TranslatableString title() {
    return TranslatableString.tr("Simple Objects");
  }

  // endregion

  // region > listAll (action)
  @Action(semantics = SemanticsOf.SAFE)
  @ActionLayout(bookmarking = BookmarkPolicy.AS_ROOT)
  @MemberOrder(sequence = "1")
  public List<SimpleObject> listAll() {
    return container.allInstances(SimpleObject.class);
  }

  // endregion

  // region > findByName (action)
  @Action(semantics = SemanticsOf.SAFE)
  @ActionLayout(bookmarking = BookmarkPolicy.AS_ROOT)
  @MemberOrder(sequence = "2")
  public List<SimpleObject> findByName(@ParameterLayout(named = "Name") final String name) {
    return container.allMatches(new QueryDefault<>(SimpleObject.class, "findByName", "name", name));
  }

  // endregion

  /**
   * Create Domain Event on SimpleObjects
   */
  // region > create (action)
  public static class CreateDomainEvent extends ActionDomainEvent<SimpleObjects> {
    public CreateDomainEvent(final SimpleObjects source, final Identifier identifier,
        final Object... arguments) {
      super(source, identifier, arguments);
    }
  }

  /**
   * Create simple object
   */
  @Action(domainEvent = CreateDomainEvent.class)
  @MemberOrder(sequence = "3")
  public SimpleObject create(@ParameterLayout(named = "Name") final String name) {
    final SimpleObject obj = container.newTransientInstance(SimpleObject.class);
    obj.setName(name);
    container.persistIfNotAlready(obj);
    return obj;
  }

}
