package com.iluwatar.classtableinheritance;

import java.util.List;
import java.util.stream.Stream;
import org.apache.ibatis.session.SqlSession;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * This is test class.
 */
public class JunitTest {
  /**
   * sqlsession can operate on sql.
   */
  private SqlSession sqlSession;
  /**
   * can deal with player.xml.
   */
  private MapperPlayer playerMapper;


  /**
 * before build a tool to initialize the playermapper.
*/
  @Before
  public void before() {
    sqlSession = Mybatis3Utils.getCurrentSqlSession();
    playerMapper = sqlSession.getMapper(MapperPlayer.class);

  }
  /**
     * close the tool class.
     */

  @After
  public void after() {
    Mybatis3Utils.closeCurrentSession();
  }
  /**
     * test insert function of player, footballer, bowler circketer.
     */

  @Test
  public void testinsert() {
    Player a = new Player();
    a.setName("player1");
    playerMapper.insertPlayer(a);

    Footballer footballer = new Footballer();
    footballer.setClub("footballerclub");
    footballer.setName("footballer1");
    playerMapper.insertFootballer(footballer);

    Bowler bowler = new Bowler();
    bowler.setBowlingAvarage(23);
    bowler.setName("bowler1");
    playerMapper.insertBowler(bowler);

    Cricketer cricketer = new Cricketer();
    cricketer.setName("cricketer1");
    cricketer.setBattingAvarage(23);
    playerMapper.insertCricketer(cricketer);
    sqlSession.commit();
    Assert.assertEquals("cricketer1 is true", "cricketer1", cricketer.getName());
  }
  /**
     * test select of list function.
     */

  @Test
  public void testselect() {

    playerMapper.listplayer().forEach(x -> {
      System.out.println(x.getName());
    });
    playerMapper.listFootballPlayer().forEach(x -> {
      System.out.println(x.getName() + ' ' + x.getClub());
    });
    playerMapper.listCricketer().forEach(x -> {
      System.out.println(x.getName() + " " + x.getBattingAvarage());
    });
    playerMapper.listBowler().forEach(x -> {
      System.out.println(x.getName() + " " + x.getBattingAvarage() + ' ' + x.getBowlingAvarage());
    });
    Stream<Player> stream = playerMapper.listplayer().stream();
    Assert.assertTrue(stream.anyMatch(x -> x.getName().equals("player1")));
  }
  /**
     * test update function related to bowler circketer footballer .
     */

  @SuppressWarnings("checkstyle:WhitespaceAfter")
  @Test
  public void testupdate() {
    Bowler bowler = new Bowler();
    bowler.setName("bowler1");
    bowler.setBattingAvarage(11);
    bowler.setBowlingAvarage(16);
    playerMapper.updateBowler(bowler);

    Cricketer cricketer = new Cricketer();
    cricketer.setName("cricketer1");
    cricketer.setBattingAvarage(3);
    playerMapper.updateCricketer(cricketer);

    Footballer footballer = new Footballer();
    footballer.setName("footballer1");
    footballer.setClub("zzz");
    playerMapper.updateFootballer(footballer);
    sqlSession.commit();
    Assert.assertEquals("cricketer1 is true", "cricketer1", cricketer.getName());
  }
  /**
     * test delete related to player, bowler, cricketer, footballer.
     */

  @Test
  public void testdelete() {
    playerMapper.deletePlayer("player1");
    playerMapper.deleteBowler(16);
    playerMapper.deleteCricketer(3);
    playerMapper.deleteFootballer("footballer1");
    sqlSession.commit();
    List<Player> listplayer = playerMapper.listplayer();
    Stream<Player> footballer1 = listplayer.stream().filter(x -> x.getName().equals("footballer1"));
    Assert.assertFalse(footballer1.anyMatch(x -> x.getName().equals("footballer1")));
  }
}
