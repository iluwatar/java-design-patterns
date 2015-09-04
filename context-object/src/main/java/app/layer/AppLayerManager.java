package app.layer;

import io.layer.ContextObject;
import io.layer.IoLayerManager;

/**
 * Responsibility is to add/uses service context information in the context object
 * and to pass it to the next layer if not the last layer
 */
public class AppLayerManager {
    ContextObject _contextObject;

    public ContextObject getContextObject(){
        return this._contextObject;
    }

    public void receiveLowerLayerContextObject(ContextObject object){
        this._contextObject = object;
    }

    // mutate object to the required by the next layer state,
    // if not the last layer
    public ContextObject addContextInfo(){
        // for Windows environments
        _contextObject.setEnvironmentUserName(System.getProperty("user.name"));

        return _contextObject;
    }

    public void returnResponse(){
        IoLayerManager manager = new IoLayerManager();
        manager.sendResponse(_contextObject);
    }
}
