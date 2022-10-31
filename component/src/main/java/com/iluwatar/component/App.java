package com.iluwatar.component;

import java.awt.event.KeyEvent;
import lombok.extern.slf4j.Slf4j;

/**
 * The component design pattern is a common game design structure. This pattern is often
 * used to reduce duplication of code as well as to improve maintainability.
 * In this implementation, component design pattern has been used to provide two game
 * objects with varying component interfaces (features). As opposed to copying and
 * pasting same code for the two game objects, the component interfaces allow game
 * objects to inherit these components from the component classes.
 *
 * <p>The implementation has decoupled graphic, physics and input components from
 * the player and NPC objects. As a result, it avoids the creation of monolithic java classes.
 *
 * <p>The below example in this App class demonstrates the use of the component interfaces
 * for separate objects (player & NPC) and updating of these components as per the
 * implementations in GameObject class and the component classes.
 */
@Slf4j
public final class App {
  /**
   * Program entry point.
   *
   * @param args args command line args.
   */
  public static void main(String[] args) {
    final var player = GameObject.createPlayer();
    final var npc = GameObject.createNpc();


    LOGGER.info("Player Update:");
    player.update(KeyEvent.KEY_LOCATION_LEFT);
    LOGGER.info("NPC Update:");
    npc.demoUpdate();
  }
}
