package domainapp.webapp.bdd.stepdefs.infrastructure;

import org.apache.isis.applib.annotation.OrderPrecedence;

import domainapp.webapp.integtests.ApplicationIntegTestAbstract;

public class BootstrapStepDef extends ApplicationIntegTestAbstract {

    @io.cucumber.java.Before(order= OrderPrecedence.FIRST)
    public void bootstrap() {
        // empty
    }

}
