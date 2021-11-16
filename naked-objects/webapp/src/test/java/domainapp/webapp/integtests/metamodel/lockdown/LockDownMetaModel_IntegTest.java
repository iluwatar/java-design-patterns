package domainapp.webapp.integtests.metamodel.lockdown;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;

import org.approvaltests.namer.StackTraceNamer;
import org.approvaltests.reporters.DiffReporter;
import org.approvaltests.reporters.UseReporter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.apache.isis.applib.services.jaxb.JaxbService;
import org.apache.isis.applib.services.metamodel.MetaModelService;
import org.apache.isis.schema.metamodel.v2.DomainClassDto;
import org.apache.isis.schema.metamodel.v2.MetamodelDto;

import static org.approvaltests.Approvals.getReporter;
import static org.approvaltests.Approvals.verify;
import static org.assertj.core.api.Assumptions.assumeThat;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;

import domainapp.webapp.integtests.ApplicationIntegTestAbstract;
import domainapp.webapp.util.CurrentVsApprovedApprovalTextWriter;

class LockDownMetaModel_IntegTest extends ApplicationIntegTestAbstract {

    @Inject MetaModelService metaModelService;
    @Inject JaxbService jaxbService;

    @BeforeEach
    public void setUp() throws Exception {
        assumeThat(System.getProperty("metamodel.lockdown")).isNotNull();
    }

    @UseReporter(DiffReporter.class)
    @Test
    void compare() throws Exception {

        // when
        MetamodelDto metamodelDto =
                metaModelService.exportMetaModel(
                        new MetaModelService.Config()
                        .withIgnoreNoop()
                        .withIgnoreAbstractClasses()
                        .withIgnoreBuiltInValueTypes()
                        .withIgnoreInterfaces()
                        .withPackagePrefix("domainapp")
                        );

        // then
        final List<DomainClassDto> domainClassDto = metamodelDto.getDomainClassDto();
        final List<Error> errors = new ArrayList<>();
        for (final DomainClassDto domainClass : domainClassDto) {
            try {
                verifyClass(domainClass);
            } catch (Error e) {
                errors.add(e);
            }
        }
        if(!errors.isEmpty()) {
            final String message = errors.stream().map(x -> x.getMessage()).collect(Collectors.joining("\n"));
            throw new Error(message);
        }
    }

    private void verifyClass(final DomainClassDto domainClass) {
        String asXml = jaxbService.toXml(domainClass);
        verify(
                new CurrentVsApprovedApprovalTextWriter(asXml, "xml"),
                new StackTraceNamer() {
                    @Override public String getApprovalName() {
                        return domainClass.getId();
                    }
                }, getReporter());
    }

}