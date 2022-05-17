package com.iluwatar.concretetableinheritance;


import java.util.List;
import org.apache.ibatis.annotations.Param;

/**
 * The FootballerMapper class is a mapper for the Footballer entity.
 */

public interface FootballerMapper {

  /**
   * lists all the Footballers.
   *
   * @return all the Footballers.
   */
  List<Player> listFootballers();

  /**
   * finds the Footballer.
   *
   * @param playerId the playerId of the Footballer.
   * @return a certain footballer.
   */
  Footballer findFootballer(int playerId);

  /**
   * inserts a Footballer.
   *
   * @param footballer a Footballer.
   */
  void insertFootballer(Footballer footballer);

  /**
   * updates a Footballer.
   *
   * @param footballer a Footballer.
   */
  void updateFootballer(Footballer footballer);

  /**
   * deletes a Footballer.
   *
   * @param playerId the playerId of the Footballer.
   */
  void deleteFootballer(int playerId);

  /**
   * creates the footballer table.
   *
   * @param tableName the name of the table.
   */
  void createNewTable(@Param("tableName") String tableName);

  /**
   * drops the footballer table.
   *
   * @param tableName the name of the table.
   */
  void dropTable(@Param("tableName") String tableName);
}
