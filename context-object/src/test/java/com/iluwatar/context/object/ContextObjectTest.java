package com.iluwatar.context.object;

import app.layer.AppLayerManager;
import io.layer.ContextObject;
import io.layer.ContextObjectType;
import io.layer.IoLayerManager;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * ContextObject unit tests
 */
public class ContextObjectTest {
    private IoLayerManager _ioLayerManager;
    private AppLayerManager _appLayerManger;

    @Before
    public void setUp() throws Exception {
        _ioLayerManager = new IoLayerManager();
        _appLayerManger = new AppLayerManager();
    }

    @Test
    public void ioManagerShouldCreateObjectWithLayerContext(){
        ContextObject testObj = _ioLayerManager.createContextObject(ContextObjectType.UserContextObject);
        Assert.assertFalse(testObj.equals(null));
    }

    @Test
    public void ioManagerShouldMutateObjectWithLayerContext(){
        ContextObject testObj = _ioLayerManager.createContextObject(ContextObjectType.UserContextObject);
        _ioLayerManager.addContextInfo(testObj);
        // the user IP is not populated intentionally
        Assert.assertFalse(testObj.getEndpointInfo().equals(""));
    }

    @Test
    public void ioManagerShouldPassMutatedObjectToUpperLayer(){
        ContextObject testObj = _ioLayerManager.createContextObject(ContextObjectType.UserContextObject);
        _ioLayerManager.addContextInfo(testObj);
        _ioLayerManager.passContextToUpperLayer(_appLayerManger, testObj);
        ContextObject appLayerObj = _appLayerManger.getContextObject();
        Assert.assertFalse(appLayerObj.getEndpointInfo().equals(""));
    }

    @Test
    public void appManagerShouldMutateObjectWithLayerContext(){
        ContextObject testObj = _ioLayerManager.createContextObject(ContextObjectType.UserContextObject);
        _ioLayerManager.addContextInfo(testObj);
        _ioLayerManager.passContextToUpperLayer(_appLayerManger, testObj);
        _appLayerManger.addContextInfo();
        // the current username is not populated intentionally
        Assert.assertFalse(_appLayerManger.getContextObject().getEnvironmentUserName().equals(""));
    }

    @Test
    public void appManagerShouldReturnMutateObjectAsResponse(){
        ContextObject testObj = _ioLayerManager.createContextObject(ContextObjectType.UserContextObject);
        _ioLayerManager.addContextInfo(testObj);
        _ioLayerManager.passContextToUpperLayer(_appLayerManger, testObj);
        _appLayerManger.addContextInfo();
        try {
            _appLayerManger.returnResponse();
        }catch (NullPointerException npe){ // context object is recycled
            Assert.assertTrue(npe.toString().equals("java.lang.NullPointerException"));
        }
    }

    @After
    public void tearDown() throws Exception {

    }
}
