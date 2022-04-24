package com.iluwater.component;

import java.util.ArrayList;

import com.iluwater.component.model.Component;
import com.iluwater.component.model.Vector2d;

public class GameObject {
    /**list of this object contains. */
    private final ArrayList<Component> components;
    /**name of object. */
    private String name;
    /**position of object. */
    private Vector2d position;
    /**health of object. */
    private float health;

    /**
     * Constructor for GameObject.
     * @param objectName is the name of object
     * @param objectHealth is the health of object
     * @param objectPosition is the position of object
     */
    public GameObject(final String objectName,
                      final float objectHealth,
                      final Vector2d objectPosition) {
        this.name = objectName;
        this.health = objectHealth;
        this.position = new Vector2d(
                objectPosition.getX(),
                objectPosition.getY());
        this.components = new ArrayList<>();
    }
    /**
     * Get Specific Component.
     * @param <T> specified type of object
     * @param componentClass
     * @return null
     */
    public <T extends Component> T getComponent(final Class<T> componentClass) {
        for (Component c : this.components) {
            if (componentClass.isAssignableFrom(c.getClass())) {
                try {
                    // component found. return component.
                    return componentClass.cast(c);
                } catch (ClassCastException e) {
                    e.printStackTrace();
                    Runtime.getRuntime().exit(0);
                }
            }
        }
        return null; // No matching found. return null.
    }
    /**
     * Add Specific Component.
     * @param c
     */
    public void addComponent(final Component c) {
        this.components.add(c);
    }

    /**
     * Update components for objects.
     */
    public void update() {
        for (Component c: components) {
            c.update(this);
        }
    }

    /**
     * get method for private attribute.
     * @return name
     */
    public String getName() {
        return name;
    }
    /**
     * set method for private attribute.
     * @param objectName
     */
    public void setName(final String objectName) {
        this.name = objectName;
    }
    /**
     * get method for private attribute.
     * @return position
     */
    public Vector2d getPosition() {
        return new Vector2d(position.getX(), position.getY());
    }
    /**
     * set method for private attribute.
     * @param objectPosition
     */
    public void setPosition(final Vector2d objectPosition) {
        this.position = new Vector2d(
                objectPosition.getX(),
                objectPosition.getY());
    }
    /**
     * get method for private attribute.
     * @return health
     */
    public float getHealth() {
        return health;
    }
    /**
     * set method for private attribute.
     * @param objectHealth
     */
    public void setHealth(final float objectHealth) {
        this.health = objectHealth;
    }
}
