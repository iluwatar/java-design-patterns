package com.iluwatar.component;

import java.awt.event.KeyEvent;

/**
 * TODO: Intro for component pattern.
 */
public class App {
  /**
   * Program entry point.
   *
   * @param args args command line args.
   */
  public static void main(String[] args) {
    final GameObject player = GameObject.createPlayer();
    final GameObject npc = GameObject.createNpc();


    System.out.println("Player Update:");
    player.update(KeyEvent.KEY_LOCATION_LEFT);
    System.out.println("NPC Update:");
    npc.demoUpdate();
  }
}
