package app.layer;

import io.layer.ContextObject;
import io.layer.ContextObjectType;
import io.layer.IoLayerManager;

/**
 *  With Context object we can treat the application specific data independently from the
 *  underlying protocol/presentation-technology. In this example we have user context specific
 *  info shared between layers.
 */
public class App {
    /**
     * Program entry point
     * @param args command line args
     */
    public static void main(String[] args) {
        // IO layer is the lowest one, so his responsibility is to create and mutate the
        // Context object according to the context
        IoLayerManager ioLayerManager = new IoLayerManager();
        ContextObject testObj = ioLayerManager.createContextObject(ContextObjectType.UserContextObject);
        ioLayerManager.addContextInfo(testObj);

        // App layer is the next and last layer, after using and mutating the Context object
        // he recycles it, after returning the response
        AppLayerManager appLayerManger = new AppLayerManager();
        ioLayerManager.passContextToUpperLayer(new AppLayerManager(), testObj);
        appLayerManger.addContextInfo();

        System.out.println("Current context username: " + appLayerManger.getContextObject().getEnvironmentUserName());
    }
}
