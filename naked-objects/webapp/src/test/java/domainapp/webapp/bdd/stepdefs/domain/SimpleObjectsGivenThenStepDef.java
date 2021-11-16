package domainapp.webapp.bdd.stepdefs.domain;

import java.util.List;

import javax.inject.Inject;

import org.assertj.core.api.Assertions;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import lombok.val;

import domainapp.modules.simple.dom.so.SimpleObject;
import domainapp.modules.simple.dom.so.SimpleObjects;
import domainapp.webapp.bdd.CucumberTestAbstract;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;

public class SimpleObjectsGivenThenStepDef extends CucumberTestAbstract {

    @Inject protected SimpleObjects simpleObjects;
    @Inject protected SimpleObjectsContext context;

    @Given("a simple object")
    public void a_simple_object() {
        val simpleObject = wrap(simpleObjects).listAll().get(0);
        context.setSimpleObject(simpleObject);
        context.setOriginalName(simpleObject.getName());
    }

    @Given("^there (?:is|are).* (\\d+) simple object[s]?$")
    public void there_are_N_simple_objects(int n) {
        final List<SimpleObject> list = wrap(simpleObjects).listAll();
        assertThat(list.size(), is(n));
    }

    @Then("the name is now {string}")
    public void the_name_is_now(String name) {
        Assertions.assertThat(context.getSimpleObject().getName()).isEqualTo(name);
    }

    @Then("the name is unchanged")
    public void the_name_is_unchanged() {
        Assertions.assertThat(context.getSimpleObject().getName()).isEqualTo(context.getOriginalName());
    }

    @Then("a warning is raised")
    public void a_warning_is_raised() {
        Assertions.assertThat(context.getEx()).isNotNull();
    }


}
