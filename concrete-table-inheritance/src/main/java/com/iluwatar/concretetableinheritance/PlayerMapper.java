package com.iluwatar.concretetableinheritance;

import java.util.List;
import org.apache.ibatis.session.SqlSession;

/**
 * The PlayerMapper interface.
 */
public class PlayerMapper {

  /**
   * The mapper for bowler.
   */
  private final BowlerMapper bowlerMapper;

  /**
   * The mapper for cricketer.
   */
  private final CricketerMapper cricketerMapper;

  /**
   * The mapper for footballer.
   */
  private final FootballerMapper footballerMapper;

  /**
   * Instantiates a new player mapper.
   *
   * @param sqlSession the sql session.
   */
  public PlayerMapper(final SqlSession sqlSession) {
    this.bowlerMapper = sqlSession.getMapper(BowlerMapper.class);
    this.cricketerMapper = sqlSession.getMapper(CricketerMapper.class);
    this.footballerMapper = sqlSession.getMapper(FootballerMapper.class);
  }

  /**
   * inserts a player into the database according to its type.
   *
   * @param player the player to be inserted.
   */
  public void insertPlayer(final Player player) {
    if (player instanceof Bowler) {
      bowlerMapper.insertBowler((Bowler) player);
    } else if (player instanceof Cricketer) {
      cricketerMapper.insertCricketer((Cricketer) player);
    } else if (player instanceof Footballer) {
      footballerMapper.insertFootballer((Footballer) player);
    }
  }

  /**
   * updaates a player in the database according to its type.
   *
   * @param player the player to be updated.
   */
  public void updatePlayer(final Player player) {
    if (player instanceof Bowler) {
      bowlerMapper.updateBowler((Bowler) player);
    } else if (player instanceof Cricketer) {
      cricketerMapper.updateCricketer((Cricketer) player);
    } else if (player instanceof Footballer) {
      footballerMapper.updateFootballer((Footballer) player);
    }
  }

  /**
   * deletes a player from the database according to its type.
   *
   * @param playerId the id of the player to be deleted.
   */
  public void deletePlayer(final int playerId) {
    if (bowlerMapper.findBowler(playerId) != null) {
      bowlerMapper.deleteBowler(playerId);
    }
    if (cricketerMapper.findCricketer(playerId) != null) {
      cricketerMapper.deleteCricketer(playerId);
    }
    if (footballerMapper.findFootballer(playerId) != null) {
      footballerMapper.deleteFootballer(playerId);
    }
  }

  /**
   * gets the player according to its id.
   *
   * @param playerId the id of the player.
   * @return the player.
   */
  public Player findPlayer(final int playerId) {
    Player player = null;
    if (bowlerMapper.findBowler(playerId) != null) {
      player = bowlerMapper.findBowler(playerId);
    }
    if (cricketerMapper.findCricketer(playerId) != null) {
      player = cricketerMapper.findCricketer(playerId);
    }
    if (footballerMapper.findFootballer(playerId) != null) {
      player = footballerMapper.findFootballer(playerId);
    }
    return player;
  }

  /**
   * gets the players according to its type.
   *
   * @param type the type of the player.
   * @return the players.
   */
  public List<Player> listPlayers(final String type) {
    List<Player> players = null;
    switch (type) {
      case "bowler":
        players = bowlerMapper.listBowlers();
        break;
      case "cricketer":
        players = cricketerMapper.listCricketers();
        break;
      case "footballer":
        players = footballerMapper.listFootballers();
        break;
      default:
        break;
    }
    return players;
  }

  /**
   * creates three tables in the database.
   */
  public void createNewTable() {
    bowlerMapper.createNewTable("bowler");
    cricketerMapper.createNewTable("cricketer");
    footballerMapper.createNewTable("footballer");
  }

  /**
   * drops three tables in the database.
   */
  public void dropTable() {
    bowlerMapper.dropTable("bowler");
    cricketerMapper.dropTable("cricketer");
    footballerMapper.dropTable("footballer");
  }
}
