package app.layer;

import app.layer.ContextObjectFactory;
import io.layer.ContextObject;
import io.layer.ContextObjectType;
import io.layer.UserContextObject;

/**
 * Define how the factory method creates a context object.
 */
public class UserContextObjectFactory implements ContextObjectFactory {
    @Override
    public ContextObject create(ContextObjectType type) {
        ContextObject object = null;

        switch (type) {
            case UserContextObject:
                object = new UserContextObject();
                break;
            default:
                throw new IllegalArgumentException("This context object:" + type + ", is not supported yet!");
        }

        return object;
    }

    // To minimize memory allocations, recycle the context object after processing the request
    @Override
    public void recycle() {
        //depending from the strategy this method can be used to cache the Context object
        System.gc();
        System.runFinalization();
    }
}
