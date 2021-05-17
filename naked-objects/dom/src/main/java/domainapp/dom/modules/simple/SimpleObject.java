/*
 * The MIT License
 * Copyright © 2014-2021 Ilkka Seppälä
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

import javax.jdo.JDOHelper;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.VersionStrategy;
import org.apache.isis.applib.DomainObjectContainer;
import org.apache.isis.applib.Identifier;
import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.BookmarkPolicy;
import org.apache.isis.applib.annotation.DomainObject;
import org.apache.isis.applib.annotation.DomainObjectLayout;
import org.apache.isis.applib.annotation.Editing;
import org.apache.isis.applib.annotation.Parameter;
import org.apache.isis.applib.annotation.ParameterLayout;
import org.apache.isis.applib.annotation.Property;
import org.apache.isis.applib.annotation.Title;
import org.apache.isis.applib.services.eventbus.ActionDomainEvent;
import org.apache.isis.applib.services.i18n.TranslatableString;
import org.apache.isis.applib.util.ObjectContracts;

/**
 * Definition of a Simple Object.
 */
@javax.jdo.annotations.PersistenceCapable(identityType = IdentityType.DATASTORE, schema = "simple",
    table = "SimpleObject")
@javax.jdo.annotations.DatastoreIdentity(
    strategy = javax.jdo.annotations.IdGeneratorStrategy.IDENTITY, column = "id")
@javax.jdo.annotations.Version(strategy = VersionStrategy.VERSION_NUMBER, column = "version")
@javax.jdo.annotations.Queries({
    @javax.jdo.annotations.Query(name = "find", value = "SELECT "
        + "FROM domainapp.dom.modules.simple.SimpleObject "),
    @javax.jdo.annotations.Query(name = "findByName", value = "SELECT "
        + "FROM domainapp.dom.modules.simple.SimpleObject " + "WHERE name.indexOf(:name) >= 0 ")})
@javax.jdo.annotations.Unique(name = "SimpleObject_name_UNQ", members = {"name"})
@DomainObject
@DomainObjectLayout(bookmarking = BookmarkPolicy.AS_ROOT, cssClassFa = "fa-flag")
public class SimpleObject implements Comparable<SimpleObject> {

  // endregion

  // region > name (property)

  private String name;

  // region > identificatiom
  public TranslatableString title() {
    return TranslatableString.tr("Object: {name}", "name", getName());
  }

  @javax.jdo.annotations.Column(allowsNull = "false", length = 40)
  @Title(sequence = "1")
  @Property(editing = Editing.DISABLED)
  public String getName() {
    return name;
  }

  public void setName(final String name) {
    this.name = name;
  }

  // endregion

  // region > updateName (action)

  /**
   * Event used to update the Name in the Domain.
   */
  public static class UpdateNameDomainEvent extends ActionDomainEvent<SimpleObject> {
    public UpdateNameDomainEvent(final SimpleObject source, final Identifier identifier,
                                 final Object... arguments) {
      super(source, identifier, arguments);
    }
  }

  @Action(domainEvent = UpdateNameDomainEvent.class)
  public SimpleObject updateName(
      @Parameter(maxLength = 40) @ParameterLayout(named = "New name") final String name) {
    setName(name);
    return this;
  }

  public String default0UpdateName() {
    return getName();
  }

  public TranslatableString validateUpdateName(final String name) {
    return name.contains("!") ? TranslatableString.tr("Exclamation mark is not allowed") : null;
  }

  // endregion

  // region > version (derived property)
  public Long getVersionSequence() {
    return (Long) JDOHelper.getVersion(this);
  }

  // endregion

  // region > compareTo

  @Override
  public int compareTo(final SimpleObject other) {
    return ObjectContracts.compare(this, other, "name");
  }

  // endregion

  // region > injected services

  @javax.inject.Inject
  @SuppressWarnings("unused")
  private DomainObjectContainer container;

  // endregion


}
