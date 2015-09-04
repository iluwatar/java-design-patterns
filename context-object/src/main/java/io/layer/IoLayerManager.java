package io.layer;

import app.layer.AppLayerManager;
import app.layer.ContextObjectFactory;
import app.layer.UserContextObjectFactory;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * Responsibility to create Context Object, add context information and
 * upcall  the next Layer passing Context information
 */
public class IoLayerManager {
    private ContextObjectFactory _factory;

    public ContextObject createContextObject(ContextObjectType type){
        ContextObject object = null;
        switch(type){
            case UserContextObject:
                _factory = new UserContextObjectFactory();
                object = _factory.create(type);
                break;
            default:
                throw new IllegalArgumentException("This context object:" + type + ", is not supported yet!");
        }

        return object;
    }

    // mutate object to the required by the next layer state
    public ContextObject addContextInfo(ContextObject object){
        try {
            object.setEndpoint(InetAddress.getLocalHost().getHostAddress());
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }

        return object;
    }

    public void passContextToUpperLayer(AppLayerManager appLayerManager, ContextObject object){
        appLayerManager.receiveLowerLayerContextObject(object);
    }

    public void sendResponse(ContextObject object){
        // send response to client using endpoint context info
        _factory.recycle();
    }
}
