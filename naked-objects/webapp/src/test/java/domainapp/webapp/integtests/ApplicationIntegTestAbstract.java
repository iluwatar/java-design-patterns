package domainapp.webapp.integtests;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;

import org.apache.isis.core.config.presets.IsisPresets;
import org.apache.isis.core.runtimeservices.IsisModuleCoreRuntimeServices;
import org.apache.isis.persistence.jdo.datanucleus5.IsisModuleJdoDataNucleus5;
import org.apache.isis.security.bypass.IsisModuleSecurityBypass;
import org.apache.isis.testing.fixtures.applib.IsisModuleTestingFixturesApplib;
import org.apache.isis.testing.integtestsupport.applib.IsisIntegrationTestAbstract;

import domainapp.webapp.application.ApplicationModule;
import domainapp.webapp.bdd.stepdefs.BddStepDefsModule;

@SpringBootTest(
    // we use a slightly different AppManifest compared to the production webapp (defined below)
    classes = ApplicationIntegTestAbstract.AppManifest.class,
    properties = {
            // "logging.level.io.cucumber.core.runner.Runner=DEBUG"
    }
)
@TestPropertySource({
        IsisPresets.H2InMemory_withUniqueSchema,
        IsisPresets.DataNucleusAutoCreate,
        IsisPresets.UseLog4j2Test,
})
@ContextConfiguration
public abstract class ApplicationIntegTestAbstract extends IsisIntegrationTestAbstract {

    /**
     * Compared to the production app manifest <code>domainapp.webapp.AppManifest</code>,
     * here we in effect disable security checks, and we exclude any web/UI modules.
     */
    @Configuration
    @Import({
        IsisModuleCoreRuntimeServices.class,
        IsisModuleJdoDataNucleus5.class,
        IsisModuleSecurityBypass.class,
        IsisModuleTestingFixturesApplib.class,

        BddStepDefsModule.class,
        ApplicationModule.class,
    })
    public static class AppManifest {
    }

}
