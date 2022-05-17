package com.iluwater.coarsegrainedlock;

import com.iluwater.coarsegrainedlock.decisions.*;
import com.iluwater.coarsegrainedlock.entity.Player;

/**
 * The coarse-grained lock pattern uses a single lock to cover multiple objects.
 * It not only simplifies the locking action itself but also frees you from having
 * to load all the members of a group in order to lock them.
 *
 * In the following example, {@link App} simulates a scenario where a famous basketball
 * player Lebron James joins different teams and wins champions. The problem here is that
 * when Lebron James is playing hard during the season to win the championship, he cannot
 * become a free agent to join other teams. Therefore, coarse-grained lock could be used
 * to prevent address from being updated when Lebron James is playing during the season.
 */

public class App {

  /**
   * This method simulates the Lebron James example.
   *
   * @param args program argument
   */
  public static void main(String[] args) {
    var lebronJames=new Player("James","Lebron","Cleveland","Ohio");
    var miami=new Miami(lebronJames);
    var winChampion=new WinChampion(lebronJames);
    var cleveland=new Cleveland(lebronJames);
    var losAngeles=new LosAngeles(lebronJames);
    winChampion.start();
    miami.start();
    cleveland.start();
    losAngeles.start();
  }
}
