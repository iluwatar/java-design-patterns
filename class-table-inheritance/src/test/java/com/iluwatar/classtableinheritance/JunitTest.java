package com.iluwatar.classtableinheritance;


import org.apache.ibatis.session.SqlSession;
import org.junit.After;
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
   * can deal with Player.xml.
   */
  private MapperPlayer playerMapper;

  /**
 * before build a tool.
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
  }
  /**
     * test update function related to bowler circketer footballer .
     */

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
  }
}
