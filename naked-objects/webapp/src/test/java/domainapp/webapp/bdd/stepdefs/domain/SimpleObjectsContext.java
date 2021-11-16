package domainapp.webapp.bdd.stepdefs.domain;

import org.springframework.stereotype.Service;

import org.apache.isis.applib.services.wrapper.InvalidException;

import lombok.Data;

import domainapp.modules.simple.dom.so.SimpleObject;
import io.cucumber.spring.ScenarioScope;

@Service
@ScenarioScope
@Data
public class SimpleObjectsContext {

    private SimpleObject simpleObject;
    private String originalName;
    private InvalidException ex;

}
