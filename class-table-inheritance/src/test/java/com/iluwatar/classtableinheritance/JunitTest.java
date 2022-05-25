package com.iluwatar.classtableinheritance;

import java.util.List;
import java.util.stream.Stream;
import org.apache.ibatis.session.SqlSession;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * This is a test class which tests the class table inheritance pattern.
 */
public class JunitTest {
  /**
   * Sqlsession can operate on sql because one operation means a session.
   */
  private transient SqlSession sqlSession;
  /**
   * It can deal with player.xml to complete mapper function.
   */
  private transient MapperPlayer playerMapper;

  /**
   * Temp player which will be used by some methods, just variables but are used usually.
   */
  private final String playertemp = "player1";

  /**
 * Before building a tool to initialize the playermapper, initialize related variables.
*/
  @Before
  public void before() {
    sqlSession = Mybatis3Utils.getCurrentSqlSession();
    playerMapper = sqlSession.getMapper(MapperPlayer.class);

  }
  /**
     * Close the tool class Mybatis3Utils.
     */

  @After
  public void after() {
    Mybatis3Utils.closeCurrentSession();
  }
  /**
     * Test inserting function related to player, footballer, bowler and circketer
     * to see whether they are inserted to database correctly.
     */

  @Test
  public void testInsert() {
    final Player player = new Player();
    player.setName(playertemp);
    playerMapper.insertPlayer(player);

    final Footballer footballer = new Footballer();
    footballer.setClub("footballerclub");
    footballer.setName("footballer1");
    playerMapper.insertFootballer(footballer);

    final Bowler bowler = new Bowler();
    bowler.setBowlingAvarage(23);
    bowler.setName("bowler1");
    playerMapper.insertBowler(bowler);

    final Cricketer cricketer = new Cricketer();
    cricketer.setName("cricketer1");
    cricketer.setBattingAvarage(23);
    playerMapper.insertCricketer(cricketer);
    sqlSession.commit();
    Assert.assertEquals("player1 is true", playertemp, player.getName());
    Assert.assertEquals("footballerclub is true", "footballerclub", footballer.getClub());
    Assert.assertEquals("cricketer1 is true", "cricketer1", cricketer.getName());
    Assert.assertEquals("bowler1 is true", "bowler1", bowler.getName());
  }
  /**
     * Test selecting function to see whether the messages about the player, bowler and circketer
     * are fetched from database correctly or not.
     */

  @Test
  public void testSelect() {

    final Stream<Player> stream = playerMapper.listplayer().stream();
    final Stream<Bowler> bowlerStream = playerMapper.listBowler().stream();
    final Stream<Cricketer> cricketerStream = playerMapper.listCricketer().stream();
    Assert.assertTrue("play equal", stream.anyMatch(x -> x.getName().equals(playertemp)));
    Assert.assertTrue("bowler equal",
        bowlerStream.anyMatch(x -> x.getName().equals("bowler1")));
    Assert.assertTrue("cricketer equal",
        cricketerStream.anyMatch(x -> x.getName().equals("cricketer1")));
  }
  /**
     * Test updating some messages about name, id and so on,
     * which are related to bowler, circketer and footballer.
     *
     */

  @SuppressWarnings("checkstyle:WhitespaceAfter")
  @Test
  public void testUpdate() {
    final Bowler bowler = new Bowler();
    bowler.setName("bowler1");
    bowler.setBattingAvarage(11);
    bowler.setBowlingAvarage(16);
    playerMapper.updateBowler(bowler);

    final Cricketer cricketer = new Cricketer();
    cricketer.setName("cricketer1");
    cricketer.setBattingAvarage(3);
    playerMapper.updateCricketer(cricketer);

    final Footballer footballer = new Footballer();
    footballer.setName("footballer1");
    footballer.setClub("zzz");
    playerMapper.updateFootballer(footballer);
    sqlSession.commit();
    Assert.assertEquals("cricketer1 is true", "cricketer1", cricketer.getName());
    Assert.assertEquals("bowler1 is true", "bowler1", bowler.getName());
    Assert.assertEquals("footballer1 is true", "footballer1", footballer.getName());
  }
  /**
     * Test delete function related to player, bowler, cricketer,
     * footballer to see if related player is deleted correctly.
     */

  @Test
  public void testDelete() {
    playerMapper.deletePlayer(playertemp);
    playerMapper.deleteBowler(16);
    playerMapper.deleteCricketer(3);
    playerMapper.deleteFootballer("footballer1");
    sqlSession.commit();
    final List<Player> listplayer = playerMapper.listplayer();
    final Stream<Player> footballer1 =
        listplayer.stream().filter(x -> x.getName().equals("footballer1"));
    Assert.assertFalse("delete sucessful",
        footballer1.anyMatch(x -> x.getName().equals("footballer1")));
  }
}
