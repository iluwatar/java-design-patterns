package com.iluwater.component.component;

import com.iluwater.component.GameObject;
import com.iluwater.component.model.Component;

/**
 * HealthComponent tracks the health of the game object.
 */
public class HealthComponent extends Component {
    /**
     * Get name of HealthComponent.
     */
    public HealthComponent() {
        super("Health Component");
    }
    /**
     * Update HealthComponent.
     */
    @Override
    public void update(final GameObject gObj) {
        final float greatHealth = 90;
        final float dangerHealth = 10;
        if (gObj.getHealth() > greatHealth) {
            System.out.printf(
                    "HealthComponent - %s is in great health [%.2f]%n",
                    gObj.getName(), gObj.getHealth());
        } else if (gObj.getHealth() < dangerHealth) {
            System.out.printf(
                    "HealthComponent - %s is in danger health [%.2f]%n",
                    gObj.getName(), gObj.getHealth());
        } else {
            System.out.printf(
                    "HealthComponent - %s can still take some hit [%.2f]%n",
                    gObj.getName(), gObj.getHealth());
        }
    }
}
