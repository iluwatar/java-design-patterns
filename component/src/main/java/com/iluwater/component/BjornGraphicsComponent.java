package com.iluwater.component;

import static org.slf4j.LoggerFactory.getLogger;

import org.slf4j.Logger;

/**
 * BjornGraphicsComponent is a class for our main game star
 * This class creat a Graphics component for Bjorn.
 */

public class BjornGraphicsComponent implements GraphicsComponent {
    private static final Logger LOGGER = getLogger(BjornGraphicsComponent.class);

    /**
     * This method is a logger for Bjorn when happens a Graphics update.
     * In real scenario, there will be code for Graphics Update.
     *
     * @param gameObject is a object in the game, here it is Bjorn
     */
    @Override
    public void update(GameObject gameObject) {
        LOGGER.info("positive:" + gameObject.getPositionOFx() + "," + gameObject.getPositionOFy());
    }
}
