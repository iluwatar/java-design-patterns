package domainapp.modules.simple.fixture;

import org.apache.isis.applib.services.registry.ServiceRegistry;
import org.apache.isis.testing.fixtures.applib.api.PersonaWithBuilderScript;
import org.apache.isis.testing.fixtures.applib.api.PersonaWithFinder;
import org.apache.isis.testing.fixtures.applib.setup.PersonaEnumPersistAll;

import domainapp.modules.simple.dom.so.SimpleObject;
import domainapp.modules.simple.dom.so.SimpleObjects;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum SimpleObject_persona 
implements PersonaWithBuilderScript<SimpleObjectBuilder>, PersonaWithFinder<SimpleObject> {

    FOO("Foo"),
    BAR("Bar"),
    BAZ("Baz"),
    FRODO("Frodo"),
    FROYO("Froyo"),
    FIZZ("Fizz"),
    BIP("Bip"),
    BOP("Bop"),
    BANG("Bang"),
    BOO("Boo");

    private final String name;

    @Override
    public SimpleObjectBuilder builder() {
        return new SimpleObjectBuilder().setName(name);
    }

    @Override
    public SimpleObject findUsing(final ServiceRegistry serviceRegistry) {
        SimpleObjects simpleObjects = serviceRegistry.lookupService(SimpleObjects.class).orElse(null);
        return simpleObjects.findByNameExact(name);
    }

    public static class PersistAll
    extends PersonaEnumPersistAll<SimpleObject_persona, SimpleObject> {

        public PersistAll() {
            super(SimpleObject_persona.class);
        }
    }
}
