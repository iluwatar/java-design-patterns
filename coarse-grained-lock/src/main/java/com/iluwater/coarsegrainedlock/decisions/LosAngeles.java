package com.iluwater.coarsegrainedlock.decisions;

import com.iluwater.coarsegrainedlock.entity.Player;

/**
 * A thread representing that a player is going to LosAngeles.
 */
public class LosAngeles extends Thread{
  private final Player player;

  /**
   * Construct function.
   *
   * @param player The player that is going to LosAngeles
   */
  public LosAngeles(Player player){
    this.player=player;
  }

  /**
   * Runnable function of the thread.
   */
  public void run(){
    player.updateAddress("LosAngeles","California");
  }
}
