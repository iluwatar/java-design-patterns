package com.iluwater.component;

import static org.slf4j.LoggerFactory.getLogger;
import org.slf4j.Logger;


public class BjornPhysicsComponent implements PhysicsComponent{

    private static final Logger LOGGER = getLogger(BjornPhysicsComponent.class);

    @Override
    public void update(GameObject gameObject) {
        if(gameObject.getPositionOFx() == gameObject.getPositionOFy()){
            LOGGER.info("Your position is pretty good, keep it!");
        }
    }
}
