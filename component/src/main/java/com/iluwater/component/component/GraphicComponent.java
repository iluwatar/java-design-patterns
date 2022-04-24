package com.iluwater.component.component;

import com.iluwater.component.GameObject;
import com.iluwater.component.model.Component;

/**
 * GraphicComponent contains graphic and animations for the game object.
 */
public class GraphicComponent extends Component {
    /**
     * Get name of GraphicComponent.
     */
    public GraphicComponent() {
        super("Graphic Component");
    }
    /**
     * Update GraphicComponent.
     */
    @Override
    public void update(final GameObject gObj) {
        final float greatHealth = 90;
        final float dangerHealth = 10;
        if (gObj.getHealth() > greatHealth) {
            System.out.printf(
                    "GraphicComponent - %s shows healthy graphic%n",
                    gObj.getName());
        } else if (gObj.getHealth() < dangerHealth) {
            System.out.printf(
                    "GraphicComponent - %s shows unheathy graphic - red!%n",
                    gObj.getName());
        } else {
            System.out.printf(
                    "GraphicComponent - %s shows normal graphic%n",
                    gObj.getName());
        }
    }
}
