package com.iluwatar.concretetableinheritance;

import java.util.List;
import org.apache.ibatis.annotations.Param;

/**
 * The CricketerMapper class is a mapper for the Cricketer entity.
 */

public interface CricketerMapper {

  /**
   * lists all the Cricketers.
   *
   * @return all the Cricketers.
   */
  List<Player> listCricketers();

  /**
   * finds the Cricketer.
   *
   * @param playerId the playerId of the Cricketer.
   * @return a certain cricketer.
   */
  Cricketer findCricketer(int playerId);

  /**
   * inserts a Cricketer.
   *
   * @param cricketer a Cricketer.
   */
  void insertCricketer(Cricketer cricketer);

  /**
   * updates a Cricketer.
   *
   * @param cricketer a Cricketer.
   */
  void updateCricketer(Cricketer cricketer);

  /**
   * deletes a Cricketer.
   *
   * @param playerId the playerId of the Cricketer.
   */
  void deleteCricketer(int playerId);

  /**
   * creates the cricketer table.
   *
   * @param tableName the name of the table.
   */
  void createNewTable(@Param("tableName") String tableName);

  /**
   * drops the cricketer table.
   *
   * @param tableName the name of the table.
   */
  void dropTable(@Param("tableName") String tableName);
}
