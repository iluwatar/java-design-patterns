package com.iluwatar.concretetableinheritance;

import java.util.List;
import java.util.stream.Stream;
import org.apache.ibatis.session.SqlSession;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Test for all the players.
 */
class PlayerTest {
  /**
   * The sql session.
   */
  private SqlSession sqlSession;

  /**
   * The player mapper.
   */
  private PlayerMapper playerMapper;

  /**
   * instantiates a sql session and a player mapper and
   * creates three tables before each test.
  */
  @BeforeEach
  /* default */ void before() {
    sqlSession = Mybatis3Utils.getCurrentSqlSession();
    playerMapper = new PlayerMapper(sqlSession);
    playerMapper.createNewTable();
  }

  /**
   * drops three tables and
   * closes the sql session.
   */
  @AfterEach
  /* default */ void after() {
    playerMapper.dropTable();
    Mybatis3Utils.closeCurrentSession();
  }

  /**
   * tests insert functions of bowler, cricketer, and footballer.
   */
  @Test
  /* default */ void testInsert() {
    final Bowler bowler = new Bowler();
    bowler.setPlayerId(1);
    bowler.setName("bowler1");
    bowler.setBowlingAverage(23);
    bowler.setBattingAverage(23);
    playerMapper.insertPlayer(bowler);

    final Cricketer cricketer = new Cricketer();
    cricketer.setPlayerId(2);
    cricketer.setName("cricketer");
    cricketer.setBattingAverage(23);
    playerMapper.insertPlayer(cricketer);
    sqlSession.commit();

    final Footballer footballer = new Footballer();
    footballer.setPlayerId(3);
    footballer.setClub("club");
    String newName = "footBaller";
    footballer.setName(newName);
    playerMapper.insertPlayer(footballer);

    final Player player = playerMapper.findPlayer(3);
    Assertions.assertEquals(newName, player.getName(), "get footballer");
  }

  /**
   * tests update functions of bowler, cricketer, and footballer.
   */
  @SuppressWarnings("checkstyle:WhitespaceAfter")
  @Test
  /* default */ void testUpdate() {

    Bowler bowler = new Bowler();
    bowler.setPlayerId(1);
    String name = "bowler2";
    bowler.setName(name);
    bowler.setBowlingAverage(23);
    bowler.setBattingAverage(23);
    playerMapper.insertPlayer(bowler);

    Cricketer cricketer = new Cricketer();
    cricketer.setPlayerId(2);
    cricketer.setName("cricketer");
    cricketer.setBattingAverage(23);
    playerMapper.insertPlayer(cricketer);
    sqlSession.commit();

    Footballer footballer = new Footballer();
    footballer.setPlayerId(3);
    footballer.setClub("club");
    footballer.setName("footballer1");
    playerMapper.insertPlayer(footballer);

    bowler = new Bowler();
    bowler.setPlayerId(1);
    bowler.setName(name);
    bowler.setBowlingAverage(23);
    bowler.setBattingAverage(11);
    playerMapper.updatePlayer(bowler);

    cricketer = new Cricketer();
    cricketer.setPlayerId(2);
    cricketer.setName("cricketer");
    cricketer.setBattingAverage(3);
    playerMapper.updatePlayer(cricketer);

    footballer = new Footballer();
    footballer.setPlayerId(3);
    footballer.setName("footballer2");
    footballer.setClub("club");
    playerMapper.updatePlayer(footballer);
    sqlSession.commit();

    final Player player = playerMapper.findPlayer(1);
    Assertions.assertEquals(name, player.getName(), "update the batting average of bowler");
  }

  /**
   * test delete functions of bowler, cricketer, and footballer.
   */
  @Test
  /* default */ void testDelete() {
    playerMapper.deletePlayer(1);
    playerMapper.deletePlayer(2);
    playerMapper.deletePlayer(3);
    sqlSession.commit();
    final List<Player> listPlayers = playerMapper.listPlayers("footballer");
    final Stream<Player> footballer = listPlayers.stream().filter(
        x -> "footballer".equals(x.getName()));
    Assertions.assertFalse(footballer.anyMatch(
        x -> "footballer".equals(x.getName())));
  }
}
