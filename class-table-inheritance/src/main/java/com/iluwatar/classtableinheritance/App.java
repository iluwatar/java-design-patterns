/*
 * Permission is hereby granted, free of charge, to any person obtaining a copy.
 */

package com.iluwatar.classtableinheritance;

import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSession;

/**
 * Player is the base class. Cricketer extends Player.
 * Footballer extends Player. Bowler extneds Cricketer.
 * It totally relates to four classes. Each class has its own database table.
 * However，due to the inheritance relation, each class can reduce their workload.
 * Finally, they can implement their work by a tool class.
 */
@Slf4j
public final class App {

  /**
   * constructor.
   */

  private App() { }
  /**
   * This is main entrance.
   *
   * @param args input args.
   */

  public static void main(final String[] args) {

    SqlSession sqlSession;
    MapperPlayer playerMapper;
    sqlSession = Mybatis3Utils.getCurrentSqlSession();
    playerMapper = sqlSession.getMapper(MapperPlayer.class);

    final Player player = new Player();
    player.setName("player1");
    playerMapper.insertPlayer(player);
    sqlSession.commit();

    final Footballer footballer = new Footballer();
    footballer.setClub("footballerclub");
    footballer.setName("footballer1");
    playerMapper.insertFootballer(footballer);
    sqlSession.commit();

    final Bowler bowler = new Bowler();
    bowler.setName("bowler1");
    playerMapper.insertBowler(bowler);
    sqlSession.commit();

    final Cricketer cricketer = new Cricketer();
    cricketer.setName("cricketer1");
    playerMapper.insertCricketer(cricketer);
    sqlSession.commit();

    playerMapper.listplayer().forEach(x -> {
      LOGGER.info(x.getName());
    });
    playerMapper.listFootballPlayer().forEach(x -> {
      LOGGER.info(x.getName() + ' ' + x.getClub());
    });
    playerMapper.listCricketer().forEach(x -> {
      LOGGER.info(x.getName() + " " + x.getBattingAvarage());
    });
    for (final Bowler x : playerMapper.listBowler()) {
      LOGGER.info(x.getName()
          + " " + x.getBattingAvarage() + x.getBowlingAvarage());
    }
    sqlSession.close();
  }
}
