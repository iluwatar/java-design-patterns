package domainapp.webapp.bdd.stepdefs.fixtures;

import javax.inject.Inject;

import org.apache.isis.applib.annotation.OrderPrecedence;
import org.apache.isis.testing.fixtures.applib.fixturescripts.FixtureScripts;

import domainapp.webapp.application.fixture.scenarios.DomainAppDemo;

public class DomainAppDemoStepDef {

    @Inject private FixtureScripts fixtureScripts;

    @io.cucumber.java.Before(value="@DomainAppDemo", order= OrderPrecedence.MIDPOINT)
    public void runDomainAppDemo() {
        
        fixtureScripts.runFixtureScript(new DomainAppDemo(), null); // <1>

    }

}
