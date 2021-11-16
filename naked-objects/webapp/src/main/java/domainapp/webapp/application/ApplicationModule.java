package domainapp.webapp.application;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import domainapp.modules.simple.SimpleModule;

@Configuration
@Import(SimpleModule.class)
@ComponentScan
public class ApplicationModule {

}
