package com.iluwatar.component;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import java.awt.event.KeyEvent;
import lombok.extern.slf4j.Slf4j;

/**
 * Tests GameObject class.
 * src/main/java/com/iluwatar/component/GameObject.java
 */
@Slf4j
class GameObjectTest {
    GameObject playerTest;
    GameObject npcTest;
    @BeforeEach
    public void initEach() {
        //creates player & npc objects for testing
        //note that velocity and coordinates are initialised to 0 in GameObject.java
        playerTest = GameObject.createPlayer();
        npcTest = GameObject.createNpc();
    }

    /**
     * Tests the create methods - createPlayer() and createNPC().
     */
    @Test
    void objectTest(){
        LOGGER.info("objectTest:");
        assertEquals("player",playerTest.getName());
        assertEquals("npc",npcTest.getName());
    }

    /**
     * Tests the input component with varying key event inputs.
     * Targets the player game object.
     */
    @Test
    void eventInputTest(){
        LOGGER.info("eventInputTest:");
        playerTest.update(KeyEvent.KEY_LOCATION_LEFT);
        assertEquals(-1, playerTest.getVelocity());
        assertEquals(-1, playerTest.getCoordinate());

        playerTest.update(KeyEvent.KEY_LOCATION_RIGHT);
        playerTest.update(KeyEvent.KEY_LOCATION_RIGHT);
        assertEquals(1, playerTest.getVelocity());
        assertEquals(0, playerTest.getCoordinate());

        LOGGER.info(Integer.toString(playerTest.getCoordinate()));
        LOGGER.info(Integer.toString(playerTest.getVelocity()));

        GameObject p2 = GameObject.createPlayer();
        p2.update(KeyEvent.KEY_LOCATION_LEFT);
        //in the case of an unknown, object stats are set to default
        p2.update(KeyEvent.KEY_LOCATION_UNKNOWN);
        assertEquals(-1, p2.getVelocity());
    }

    /**
     * Tests the demo component interface.
     */
    @Test
    void npcDemoTest(){
        LOGGER.info("npcDemoTest:");
        npcTest.demoUpdate();
        assertEquals(2, npcTest.getVelocity());
        assertEquals(2, npcTest.getCoordinate());
    }
}
