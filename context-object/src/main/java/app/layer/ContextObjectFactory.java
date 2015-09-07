package app.layer;

import io.layer.ContextObject;
import io.layer.ContextObjectType;

/**
 * Assure that factories instantiates a context object instance only after determining its type
 */
public interface ContextObjectFactory {
    ContextObject create(ContextObjectType type);
    void recycle();
}
