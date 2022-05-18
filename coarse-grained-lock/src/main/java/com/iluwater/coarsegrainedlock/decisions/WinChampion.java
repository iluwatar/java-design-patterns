package com.iluwater.coarsegrainedlock.decisions;

import com.iluwater.coarsegrainedlock.entity.Player;

/**
 * A thread representing that a player is playing hard to win champion.
 */
public class WinChampion extends Thread {
  private final Player player;

  /**
   * Construct function.
   *
   * @param player The player that is playing hard
   */
  public WinChampion(Player player) {
    this.player = player;
  }

  /**
   * Runnable function of the thread.
   */
  public void run() {
    for (int i = 0; i < 3; i++) {
      player.winChampion();
    }
  }
}
