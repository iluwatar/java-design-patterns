package domainapp.webapp.application.fixture;

import org.springframework.stereotype.Service;

import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.testing.fixtures.applib.fixturescripts.FixtureScripts;
import org.apache.isis.testing.fixtures.applib.fixturespec.FixtureScriptsSpecification;
import org.apache.isis.testing.fixtures.applib.fixturespec.FixtureScriptsSpecificationProvider;

import domainapp.webapp.application.fixture.scenarios.DomainAppDemo;

/**
 * Specifies where to find fixtures, and other settings.
 */
@Service
public class DomainAppFixtureScriptsSpecificationProvider implements FixtureScriptsSpecificationProvider {
    @Override
    public FixtureScriptsSpecification getSpecification() {
        return FixtureScriptsSpecification
                .builder(DomainAppFixtureScriptsSpecificationProvider.class)
                .with(FixtureScripts.MultipleExecutionStrategy.EXECUTE)
                .withRunScriptDefault(DomainAppDemo.class)
                .withRecreate(DomainAppDemo.class)
                .build();
    }
}
