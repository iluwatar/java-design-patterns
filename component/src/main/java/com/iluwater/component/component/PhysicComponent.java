package com.iluwater.component.component;

import com.iluwater.component.GameObject;
import com.iluwater.component.model.Component;

/**
 * PhysicComponent contains graphic and animations for the game object.
 */
public class PhysicComponent extends Component {
    /**
     * Get name of PhysicComponent.
     */
    public PhysicComponent() {
        super("Physic Component");
    }
    /**
     * Update PhysicComponent.
     */
    @Override
    public void update(final GameObject gObj) {
        System.out.printf("PhysicComponent - %s is in position %s%n",
                gObj.getName(), gObj.getPosition().toString());
    }
}
