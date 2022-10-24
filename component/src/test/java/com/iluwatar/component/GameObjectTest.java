package com.iluwatar.component;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import java.awt.event.KeyEvent;

/**
 * Tests GameObject class.
 * src/main/java/com/iluwatar/component/GameObject.java
 */
public class GameObjectTest {

    //creates player & npc objects for testing
    GameObject playerTest = GameObject.createPlayer();
    GameObject npcTest = GameObject.createNpc();

    //note that velocity and coordinates are initialised to 0 in GameObject.java

    /**
     * Tests the create methods - createPlayer() and createNPC().
     */
    @Test
    void objectTest(){
        System.out.println();
        assert(playerTest.name.equals("player"));
        assert(npcTest.name.equals("npc"));
    }

    /**
     * Tests the input component with varying key event inputs.
     * Targets the player game object.
     */
    @Test
    void eventInputTest(){
        playerTest.update(KeyEvent.KEY_LOCATION_LEFT);
        assertEquals(-1, playerTest.velocity);
        assertEquals(-1, playerTest.coordinate);


        playerTest.update(KeyEvent.KEY_LOCATION_RIGHT);
        playerTest.update(KeyEvent.KEY_LOCATION_RIGHT);
        assertEquals(1, playerTest.velocity);
        assertEquals(0, playerTest.coordinate);

        System.out.println(playerTest.coordinate);
        System.out.println(playerTest.velocity);


        GameObject p2 = GameObject.createPlayer();
        p2.update(KeyEvent.KEY_LOCATION_LEFT);
        //in the case of an unknown, object stats are set to default
        p2.update(KeyEvent.KEY_LOCATION_UNKNOWN);
        assertEquals(0, p2.coordinate);
        assertEquals(0, p2.velocity);
    }

    /**
     * Tests the demo component interface.
     */
    @Test
    void npcDemoTest(){
        npcTest.demoUpdate();
        assertEquals(2, npcTest.velocity);
        assertEquals(2, npcTest.coordinate);
    }
}
