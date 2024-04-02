/*
 * This project is licensed under the MIT license. Module model-view-viewmodel is using ZK framework licensed under LGPL (see lgpl-3.0.txt).
 *
 * The MIT License
 * Copyright © 2014-2022 Ilkka Seppälä
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
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
