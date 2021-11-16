package domainapp.webapp.integtests.metamodel;

import java.io.IOException;

import javax.inject.Inject;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import org.apache.isis.applib.services.registry.ServiceRegistry;
import org.apache.isis.applib.services.swagger.SwaggerService;
import org.apache.isis.testing.integtestsupport.applib.swagger.SwaggerExporter;
import org.apache.isis.testing.integtestsupport.applib.validate.DomainModelValidator;
import org.apache.isis.viewer.restfulobjects.jaxrsresteasy4.IsisModuleViewerRestfulObjectsJaxrsResteasy4;

import lombok.val;

import domainapp.webapp.integtests.ApplicationIntegTestAbstract;

@SpringBootTest(
        classes = {
                ApplicationIntegTestAbstract.AppManifest.class,
                IsisModuleViewerRestfulObjectsJaxrsResteasy4.class
        }
)
class SwaggerExport_IntegTest extends ApplicationIntegTestAbstract {
    
    @Inject ServiceRegistry serviceRegistry;

    @Test
    void export() throws IOException {
        val swaggerExporter = new SwaggerExporter(serviceRegistry);
        swaggerExporter.export(SwaggerService.Visibility.PRIVATE, SwaggerService.Format.JSON);
    }
}