/*
 * The MIT License
 * Copyright © 2014-2019 Ilkka Seppälä
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
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
 * Domain Service for Simple Objects.
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
   * Create Domain Event on SimpleObjects.
   */
  // region > create (action)
  public static class CreateDomainEvent extends ActionDomainEvent<SimpleObjects> {
    public CreateDomainEvent(final SimpleObjects source, final Identifier identifier,
                             final Object... arguments) {
      super(source, identifier, arguments);
    }
  }

  /**
   * Create simple object.
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
