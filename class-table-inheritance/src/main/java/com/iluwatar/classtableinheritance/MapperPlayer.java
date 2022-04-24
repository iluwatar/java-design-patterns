package com.iluwatar.classtableinheritance;

import java.util.List;

/**
 * It's a mapper abstract class which can relate mybatis and xml file.
 */

public interface MapperPlayer {
  /**
   * this is list player function.
   *
   * @return all the player.
   */
  List<Player> listplayer();
  /**
   * this is list football function.
   *
   * @return player meet the condition.
   */

  List<Footballer> listFootballPlayer();
  /**
   * this is list cricketer player function.
   *
   * @return all the cricketer.
   */

  List<Cricketer> listCricketer();
  /**
   * this is list bowler  function.
   *
   * @return bowler.
   */

  List<Bowler> listBowler();
  /**
   * this is get player function.
   *
   * @param name input.
   * @return some polayer.
   */

  Player getPlayer(String name);
  /**
   * this is insert player function.
   *
   * @param player player.
   * @return insert number.
   */

  int insertPlayer(Player player);
  /**
   * this is delete player function.
   *
   * @param name input.
   * @return delete number.
   */

  int deletePlayer(String name);
  /**
   * this is get Footballer function.
   *
   * @param name footballer name.
   * @return footballer.
   */

  Footballer getFootballer(String name);
  /**
   * this is insert Footballer function.
   *
   * @param player aim.
   * @return number.
   */

  int insertFootballer(Footballer player);
  /**
   * this is update Footballer function.
   *
   * @param footballer aim
   * @return number.
   */

  int updateFootballer(Footballer footballer);
  /**
   * this is delete Footballer function.
   *
   * @param name aim
   * @return footballer's name.
   */

  int deleteFootballer(String name);
  /**
   * this is get Cricketer function.
   *
   * @param id id.
   * @return crocleter id.
   */

  Cricketer getCricketer(double id);
  /**
   * this is insert Cricketer function.
   *
   * @param player aim.
   * @return Cricketer.
   */

  int insertCricketer(Cricketer player);
  /**
   * this is update Cricketer function.
   *
   * @param cricketer aim.
   * @return Cricketer number id.
   */

  int updateCricketer(Cricketer cricketer);
  /**
   * this is delete Cricketer function.
   *
   * @param id aim.
   * @return cricketer number id.
   */

  int deleteCricketer(double id);
  /**
   * this is delete Cricketer function.
   *
   * @param id aim.
   * @return Bowler.
   */

  Bowler getBowler(double id);
  /**
   * this is update Bowler function.
   *
   * @param player aim.
   * @return Bowler id.
   */

  int insertBowler(Bowler player);
  /**
   * this is update Bowler  function.
   *
   * @param bowler aim.
   * @return Bowler id.
   */

  int updateBowler(Bowler bowler);
  /**
   * this is delete  Bowler function.
   *
   * @param id aim.
   * @return id.
   */

  int deleteBowler(double id);
}
