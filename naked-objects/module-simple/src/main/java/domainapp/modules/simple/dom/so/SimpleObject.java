package domainapp.modules.simple.dom.so;

import java.util.Comparator;

import javax.inject.Inject;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.VersionStrategy;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.CommandReification;
import org.apache.isis.applib.annotation.DomainObject;
import org.apache.isis.applib.annotation.DomainObjectLayout;
import org.apache.isis.applib.annotation.Publishing;
import org.apache.isis.applib.services.message.MessageService;
import org.apache.isis.applib.services.repository.RepositoryService;
import org.apache.isis.applib.services.title.TitleService;
import org.apache.isis.applib.jaxb.PersistentEntityAdapter;

import static org.apache.isis.applib.annotation.SemanticsOf.IDEMPOTENT;
import static org.apache.isis.applib.annotation.SemanticsOf.NON_IDEMPOTENT_ARE_YOU_SURE;

import domainapp.modules.simple.SimpleModule;
import domainapp.modules.simple.types.Name;
import domainapp.modules.simple.types.Notes;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.val;

@javax.jdo.annotations.PersistenceCapable(identityType=IdentityType.DATASTORE, schema = "simple")
@javax.jdo.annotations.DatastoreIdentity(strategy=IdGeneratorStrategy.IDENTITY, column="id")
@javax.jdo.annotations.Version(strategy= VersionStrategy.DATE_TIME, column="version")
@javax.jdo.annotations.Unique(name="SimpleObject_name_UNQ", members = {"name"})
@DomainObject()
@DomainObjectLayout()
@XmlJavaTypeAdapter(PersistentEntityAdapter.class)
@ToString(onlyExplicitlyIncluded = true)
public class SimpleObject implements Comparable<SimpleObject> {

    public static SimpleObject withName(String name) {
        val simpleObject = new SimpleObject();
        simpleObject.setName(name);
        return simpleObject;
    }

    public static class ActionDomainEvent extends SimpleModule.ActionDomainEvent<SimpleObject> {}

    @Inject RepositoryService repositoryService;
    @Inject TitleService titleService;
    @Inject MessageService messageService;

    private SimpleObject() {
    }

    public String title() {
        return "Object: " + getName();
    }

    @Name
    @Getter @Setter @ToString.Include
    private String name;

    @Notes
    @Getter @Setter
    private String notes;


    public static class UpdateNameActionDomainEvent extends SimpleObject.ActionDomainEvent {}
    @Action(semantics = IDEMPOTENT,
            command = CommandReification.ENABLED, publishing = Publishing.ENABLED,
            associateWith = "name", domainEvent = UpdateNameActionDomainEvent.class)
    public SimpleObject updateName(
            @Name final String name) {
        setName(name);
        return this;
    }

    public String default0UpdateName() {
        return getName();
    }

    public static class DeleteActionDomainEvent extends SimpleObject.ActionDomainEvent {}
    @Action(semantics = NON_IDEMPOTENT_ARE_YOU_SURE, domainEvent = DeleteActionDomainEvent.class)
    public void delete() {
        final String title = titleService.titleOf(this);
        messageService.informUser(String.format("'%s' deleted", title));
        repositoryService.removeAndFlush(this);
    }

    private final static Comparator<SimpleObject> comparator =
            Comparator.comparing(SimpleObject::getName);

    @Override
    public int compareTo(final SimpleObject other) {
        return comparator.compare(this, other);
    }

}