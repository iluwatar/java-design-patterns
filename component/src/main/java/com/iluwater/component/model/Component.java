package com.iluwater.component.model;

import com.iluwater.component.GameObject;

// Generic Component class
public abstract class Component {
    /**name of component. */
    private String name;
    /**
     * Constructor for Component.
     * @param componentName is the name of component
     */
    public Component(final String componentName) {
        this.name = componentName;
    }
    /**
     * get method for private attribute.
     * @return name
     */
    public String getName() {
        return name;
    }
    /**
     * Update components for objects.
     * @param gObj
     */
    public void update(final GameObject gObj) {
    }
}
