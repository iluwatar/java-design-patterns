package domainapp.webapp.bdd.stepdefs.domain;

import java.util.UUID;

import javax.inject.Inject;

import static org.junit.jupiter.api.Assertions.fail;

import org.apache.isis.applib.services.wrapper.InvalidException;

import domainapp.modules.simple.dom.so.SimpleObjects;
import domainapp.webapp.bdd.CucumberTestAbstract;
import io.cucumber.java.en.When;

public class SimpleObjectsWhenStepDef extends CucumberTestAbstract {

    @Inject protected SimpleObjects simpleObjects;
    @Inject protected SimpleObjectsContext context;

    @When("^.*create (?:a|another) .*simple object$")
    public void create_a_simple_object() {

        wrap(simpleObjects).create(UUID.randomUUID().toString());
    }

    @When("^.*modify the name to '(.*)'")
    public void modify_the_name_to(String newName) {
        wrap(context.getSimpleObject()).updateName(newName);
    }

    @When("^.*attempt to change the name to '(.*)'")
    public void attempt_to_change_the_name_to(String newName) {
        try {
            wrap(context.getSimpleObject()).updateName(newName);
            fail();
        } catch(InvalidException ex) {
            this.context.setEx(ex);
        }
    }

}
