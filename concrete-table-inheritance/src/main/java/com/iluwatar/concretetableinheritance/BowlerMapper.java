package com.iluwatar.concretetableinheritance;

import java.util.List;
import org.apache.ibatis.annotations.Param;

/**
 * The BowlerMapper class is a mapper for the Bowler entity.
 */

public interface BowlerMapper {

  /**
   * lists all the Bowlers.
   *
   * @return all the Bowlers.
   */
  List<Player> listBowlers();

  /**
   * finds the Bowler.
   *
   * @param playerId the playerId of the Bowler.
   * @return a certain bowler.
   */
  Bowler findBowler(int playerId);

  /**
   * updates the Bowler.
   *
   * @param bowler a Bowler.
   */
  void updateBowler(Bowler bowler);

  /**
   * inserts a Bowler.
   *
   * @param bowler a Bowler.
   */
  void insertBowler(Bowler bowler);

  /**
   * deletes a Bowler.
   *
   * @param playerId the playerId of the Bowler.
   */
  void deleteBowler(int playerId);

  /**
   * creates the bowler table.
   *
   * @param tableName the name of the table.
   */
  void createNewTable(@Param("tableName") String tableName);

  /**
   * drops the bowler table.
   *
   * @param tableName the name of the table.
   */
  void dropTable(@Param("tableName") String tableName);
}
