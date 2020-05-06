package com.iluwater.component;

import static org.slf4j.LoggerFactory.getLogger;

import org.slf4j.Logger;

public class BjornGraphicsComponent implements GraphicsComponent {
    private static final Logger LOGGER = getLogger(BjornGraphicsComponent.class);

    @Override
    public void update(GameObject gameObject) {
        LOGGER.info("positive:" + gameObject.getPositionOFx() + "," + gameObject.getPositionOFy());
    }
}
